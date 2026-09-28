#!/usr/bin/env bash

set -euo pipefail

usage() {
    cat <<'EOF'
Usage: ./scripts/prepare-release.sh <versionName> <versionCode>

Example:
  ./scripts/prepare-release.sh 1.2.0 5

Run from the latest, clean develop branch. This script creates a release
branch, updates app/build.gradle.kts, and generates release notes. It does not
commit, push, create a tag, or publish to Google Play.
EOF
}

if [[ "${1:-}" == "-h" || "${1:-}" == "--help" ]]; then
    usage
    exit 0
fi

if [[ "$#" -ne 2 ]]; then
    usage >&2
    exit 2
fi

version_name="$1"
version_code="$2"

if [[ ! "$version_name" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
    echo "versionName must use semantic version format, for example 1.2.0." >&2
    exit 2
fi

if [[ ! "$version_code" =~ ^[1-9][0-9]*$ ]] || (( version_code > 2100000000 )); then
    echo "versionCode must be a positive integer no greater than 2100000000." >&2
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
    echo "Switch to the develop branch before preparing a release." >&2
    exit 1
fi

if [[ -n "$(git status --porcelain)" ]]; then
    echo "The working tree must be clean before preparing a release." >&2
    exit 1
fi

git fetch origin develop --tags
if [[ "$(git rev-parse HEAD)" != "$(git rev-parse origin/develop)" ]]; then
    echo "Local develop is not at origin/develop. Pull the latest develop and retry." >&2
    exit 1
fi

gradle_file="app/build.gradle.kts"
current_version_name="$(sed -nE 's/^[[:space:]]*versionName[[:space:]]*=[[:space:]]*"([^"]+)".*/\1/p' "$gradle_file")"
current_version_code="$(sed -nE 's/^[[:space:]]*versionCode[[:space:]]*=[[:space:]]*([0-9]+).*/\1/p' "$gradle_file")"

if [[ ! "$current_version_name" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ || ! "$current_version_code" =~ ^[0-9]+$ ]]; then
    echo "Could not read a single numeric versionName and versionCode from $gradle_file." >&2
    exit 1
fi

if (( 10#$version_code <= 10#$current_version_code )); then
    echo "Requested versionCode ($version_code) must exceed the current code ($current_version_code)." >&2
    exit 1
fi

release_tag="v$version_name"
release_branch="release/$release_tag"

if git show-ref --verify --quiet "refs/tags/$release_tag" ||
    git ls-remote --exit-code --tags origin "refs/tags/$release_tag" >/dev/null 2>&1; then
    echo "Release tag $release_tag already exists." >&2
    exit 1
fi

if git show-ref --verify --quiet "refs/heads/$release_branch" ||
    [[ -n "$(git ls-remote --heads origin "$release_branch")" ]]; then
    echo "Release branch $release_branch already exists." >&2
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
    echo "No previous semantic version tags found on develop; cannot generate a release diff." >&2
    exit 1
fi

if (( 10#$version_code <= highest_version_code )); then
    echo "Requested versionCode ($version_code) must exceed the highest tagged code ($highest_version_code)." >&2
    exit 1
fi

if ! version_is_greater "$version_name" "$current_version_name" ||
    ! version_is_greater "$version_name" "${last_release_tag#v}"; then
    echo "Requested version $release_tag must be newer than the latest release $last_release_tag." >&2
    exit 1
fi

if [[ "$(grep -Ec '^[[:space:]]*versionCode[[:space:]]*=' "$gradle_file")" -ne 1 ||
    "$(grep -Ec '^[[:space:]]*versionName[[:space:]]*=' "$gradle_file")" -ne 1 ]]; then
    echo "Expected exactly one versionCode and one versionName setting in $gradle_file." >&2
    exit 1
fi

release_diff_path="docs/releases/$release_tag.md"
if [[ -e "$release_diff_path" ]]; then
    echo "Release diff file already exists: $release_diff_path" >&2
    exit 1
fi

commits_file="$(mktemp)"
trap 'rm -f "$commits_file"' EXIT
git log --no-merges --format='%s' "$last_release_tag..HEAD" > "$commits_file"

if [[ ! -s "$commits_file" ]]; then
    echo "No commits found since $last_release_tag; refusing to prepare an empty release." >&2
    exit 1
fi

git switch -c "$release_branch"

VERSION_NAME="$version_name" VERSION_CODE="$version_code" perl -0pi -e '
    s/^([ \t]*versionCode[ \t]*=[ \t]*)[0-9]+/${1}$ENV{VERSION_CODE}/m;
    s/^([ \t]*versionName[ \t]*=[ \t]*")[^"]+(".*)$/${1}$ENV{VERSION_NAME}$2/m;
' "$gradle_file"

mkdir -p docs/releases distribution/whatsnew
release_diff="$release_diff_path"
play_notes="distribution/whatsnew/whatsnew-zh-TW"
head_commit="$(git rev-parse --short HEAD)"
compare_url="https://github.com/swksysb1124/MyFitnessApp/compare/$last_release_tag...$head_commit"

{
    printf '# Release %s\n\n' "$release_tag"
    printf -- '- Previous release: `%s`\n' "$last_release_tag"
    printf -- '- Source commit: `%s`\n' "$head_commit"
    printf -- '- Compare: %s\n\n' "$compare_url"
    printf '## Changes\n\n'
    while IFS= read -r subject; do
        [[ "$subject" == chore:\ release\ v* ]] && continue
        printf -- '- %s\n' "$subject"
    done < "$commits_file"
    printf '\n## Changed files\n\n```text\n'
    git diff --stat "$last_release_tag...HEAD"
    printf '```\n'
} > "$release_diff"

{
    while IFS= read -r subject; do
        [[ "$subject" == chore:\ release\ v* ]] && continue
        printf -- '- %s\n' "$subject"
    done < "$commits_file"
} > "$play_notes"

if [[ ! -s "$play_notes" ]]; then
    printf 'Bug fixes and improvements.\n' > "$play_notes"
fi

play_notes_length="$(wc -m < "$play_notes" | tr -d '[:space:]')"
if (( play_notes_length > 500 )); then
    echo "Warning: Google Play notes contain $play_notes_length characters (limit: 500); edit $play_notes before merging." >&2
fi

cat <<EOF
Prepared release $release_tag on branch $release_branch.

Updated:
  $gradle_file
  $release_diff
  $play_notes

Next:
  1. Review/edit the release diff and Google Play notes.
  2. Run ./gradlew assembleDebug testDebugUnitTest.
  3. Commit and push this branch, then open a PR to develop.
  4. After the PR is merged, create and push tag $release_tag to the merged commit.

This script did not commit, push, create a tag, or publish to Google Play.
EOF
