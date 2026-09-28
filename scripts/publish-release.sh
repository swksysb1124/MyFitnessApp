#!/usr/bin/env bash

set -euo pipefail

usage() {
    cat <<'EOF'
Usage: ./scripts/publish-release.sh <versionName> [--yes]

Example:
  ./scripts/publish-release.sh 1.2.0

Run only after the version PR has been merged to develop. This script creates
and pushes the matching v<versionName> tag, which triggers Google Play
production publication. Use --yes to skip the interactive confirmation.
EOF
}

if [[ "${1:-}" == "-h" || "${1:-}" == "--help" ]]; then
    usage
    exit 0
fi

if [[ "$#" -lt 1 || "$#" -gt 2 ]]; then
    usage >&2
    exit 2
fi

version_name="$1"
auto_confirm=false
if [[ "$#" -eq 2 ]]; then
    if [[ "$2" != "--yes" ]]; then
        usage >&2
        exit 2
    fi
    auto_confirm=true
fi

if [[ ! "$version_name" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
    echo "versionName must use semantic version format, for example 1.2.0." >&2
    exit 2
fi

version_is_greater() {
    local left_major left_minor left_patch
    local right_major right_minor right_patch
    IFS=. read -r left_major left_minor left_patch <<< "$1"
    IFS=. read -r right_major right_minor right_patch <<< "$2"

    if (( 10#$left_major != 10#$right_major )); then
        (( 10#$left_major > 10#$right_major ))
        return
    fi
    if (( 10#$left_minor != 10#$right_minor )); then
        (( 10#$left_minor > 10#$right_minor ))
        return
    fi
    (( 10#$left_patch > 10#$right_patch ))
}

repo_root="$(git rev-parse --show-toplevel)"
cd "$repo_root"

if [[ "$(git branch --show-current)" != "develop" ]]; then
    echo "Switch to the develop branch before publishing a release." >&2
    exit 1
fi

if [[ -n "$(git status --porcelain)" ]]; then
    echo "The working tree must be clean before publishing a release." >&2
    exit 1
fi

git fetch origin develop --tags
if [[ "$(git rev-parse HEAD)" != "$(git rev-parse origin/develop)" ]]; then
    echo "Local develop is not at origin/develop. Pull the latest develop and retry." >&2
    exit 1
fi

release_tag="v$version_name"
gradle_file="app/build.gradle.kts"
current_version_name="$(sed -nE 's/^[[:space:]]*versionName[[:space:]]*=[[:space:]]*"([^"]+)".*/\1/p' "$gradle_file")"
version_code="$(sed -nE 's/^[[:space:]]*versionCode[[:space:]]*=[[:space:]]*([0-9]+).*/\1/p' "$gradle_file")"

if [[ "$current_version_name" != "$version_name" ]]; then
    echo "Requested version $version_name does not match Gradle versionName ($current_version_name)." >&2
    exit 1
fi

if [[ ! "$version_code" =~ ^[1-9][0-9]*$ ]] || (( version_code > 2100000000 )); then
    echo "Could not read a valid Android versionCode from $gradle_file." >&2
    exit 1
fi

if ! git cat-file -e "HEAD:.github/workflows/google-play-release.yml" ||
    ! git cat-file -e "HEAD:docs/releases/$release_tag.md" ||
    ! git cat-file -e "HEAD:distribution/whatsnew/whatsnew-zh-TW"; then
    echo "The release workflow, generated release diff, and Play notes must be committed to develop." >&2
    exit 1
fi

release_notes_length="$(git show "HEAD:distribution/whatsnew/whatsnew-zh-TW" |
    wc -m | tr -d '[:space:]')"
if (( release_notes_length > 500 )); then
    echo "Google Play release notes exceed 500 characters; edit them before publishing." >&2
    exit 1
fi

if git show-ref --verify --quiet "refs/tags/$release_tag"; then
    echo "Release tag $release_tag already exists locally." >&2
    exit 1
fi

remote_tag="$(git ls-remote --tags origin "refs/tags/$release_tag")"
if [[ -n "$remote_tag" ]]; then
    echo "Release tag $release_tag already exists on origin." >&2
    exit 1
fi

last_release_tag=""
highest_version_code=0
while IFS= read -r tag; do
    [[ "$tag" =~ ^v[0-9]+\.[0-9]+\.[0-9]+$ ]] || continue
    tagged_gradle_file="$(git show "$tag:$gradle_file")"
    tagged_version_code="$(printf '%s\n' "$tagged_gradle_file" |
        sed -nE 's/^[[:space:]]*versionCode[[:space:]]*=[[:space:]]*([0-9]+).*/\1/p')"
    if [[ "$tagged_version_code" =~ ^[0-9]+$ ]] &&
        (( 10#$tagged_version_code > highest_version_code )); then
        highest_version_code="$tagged_version_code"
    fi
    if [[ -z "$last_release_tag" ]] ||
        version_is_greater "${tag#v}" "${last_release_tag#v}"; then
        last_release_tag="$tag"
    fi
done < <(git tag --merged origin/develop --list 'v*')

if [[ -z "$last_release_tag" ]]; then
    echo "No previous semantic version tags found on develop." >&2
    exit 1
fi

if (( 10#$version_code <= highest_version_code )); then
    echo "versionCode ($version_code) must exceed the highest tagged code ($highest_version_code)." >&2
    exit 1
fi

if ! version_is_greater "$version_name" "${last_release_tag#v}"; then
    echo "Requested version $release_tag must be newer than $last_release_tag." >&2
    exit 1
fi

if [[ "$auto_confirm" != true ]]; then
    printf 'This will push %s and trigger a Google Play Production 10%% rollout. Type %s to continue: ' \
        "$release_tag" "$release_tag"
    IFS= read -r confirmation
    if [[ "$confirmation" != "$release_tag" ]]; then
        echo "Confirmation did not match; no tag was created or pushed." >&2
        exit 1
    fi
fi

git tag -a "$release_tag" -m "Release $release_tag" origin/develop
git push origin "refs/tags/$release_tag"

echo "Pushed $release_tag. Monitor the Google Play Release workflow in GitHub Actions."
