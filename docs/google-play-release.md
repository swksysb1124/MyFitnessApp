# Google Play 發版流程

本文說明如何透過 GitHub Actions 發布 Android App 到 Google Play Production。Release workflow 已設定好；它會在推送符合格式的版本 tag 時自動執行。

> **重要：推送版本 tag 會觸發正式發布。** Workflow 成功後會將 AAB 上傳到 Google Play Production，並開始 10% staged rollout。不要用正式 tag 測試 workflow。

## 發版前確認

- Release workflow `.github/workflows/google-play-release.yml` 已合併到 `develop`。
- GitHub Repository Actions secrets 已設定：
  - `KEYSTORE_BASE64`
  - `KEYSTORE_PASSWORD`
  - `KEY_ALIAS`
  - `KEY_PASSWORD`
  - `PLAY_SERVICE_ACCOUNT_JSON`
- Secrets 值不會顯示在 GitHub 頁面；確認名稱存在不代表值一定正確。不要把 keystore、密碼或 service account JSON 放進 repository、commit 或聊天訊息。
- 到 Play Console 確認下一個 `versionCode` 大於曾上傳過的最高值。Android 不允許重複使用已上傳的 version code。

目前 repository 的版本欄位為 `versionName = "1.1.1"`、`versionCode = 4`，既有最新版本 tag 為 `v1.1.1`。這只是 repository 目前狀態；實際發版前仍須核對 Play Console 的版本紀錄。

## Release scripts

| Script | 用途 | 使用時機 | 主要輸出／動作 |
| --- | --- | --- | --- |
| `scripts/prepare-release.sh <versionName> <versionCode>` | 準備 release 分支、更新 Android 版本，並產生上次 release 之後的變更摘要與 Google Play 更新說明草稿。 | 從乾淨且最新的 `develop` 執行；建立版本 PR 前。 | 建立 `release/v<versionName>` 分支；更新 `app/build.gradle.kts`；產生 `docs/releases/v<versionName>.md` 和 `distribution/whatsnew/whatsnew-zh-TW`。不會 commit、push、建立 tag 或發布。 |
| `scripts/publish-release.sh <versionName> [--yes]` | 驗證已合併至 `develop` 的版本與發版檔案，建立並推送對應的 annotated tag。 | 版本 PR 合併後，確定要發布到 Google Play 時。 | 推送 `v<versionName>` tag 以觸發 Production 10% staged rollout；預設要求輸入完整 tag 確認。`--yes` 會略過互動確認。 |

## 發版步驟

以下以 `1.2.0` / `5` 為例。請先確認該版本號與 version code 尚未使用，再替換成實際值。準備發版時可使用 script 自動建立版本分支、更新 Gradle 版本，並產生完整的 release diff 和 Play Store 繁體中文 release notes。

### 1. 從最新 develop 執行 release preparation script

```bash
git switch develop
git pull --ff-only origin develop
./scripts/prepare-release.sh 1.2.0 5
```

Script 會確認工作目錄乾淨、位於最新的 `develop`，檢查版本號與 `versionCode`，並建立 `release/v1.2.0` 分支。它會更新 App 版本並產生：

- `docs/releases/v1.2.0.md`：自上次 release tag 以來的 commit 摘要、檔案變更統計與 GitHub compare link。
- `distribution/whatsnew/whatsnew-zh-TW`：Google Play 繁體中文更新說明草稿，由 commit 標題整理而成。

Script 只準備本地分支與檔案，不會 commit、push、建立 tag 或發布。請檢查 `docs/releases` 的 diff 摘要，並將 Play 更新說明校對、翻譯成適合使用者閱讀的繁體中文。Google Play 每語系更新說明最多 500 字元，script 超過時會顯示警告。

### 2. 本機驗證

```bash
./gradlew assembleDebug testDebugUnitTest
git diff --check
git diff -- app/build.gradle.kts
```

確認 Gradle task 成功，且版本與 release notes 都正確。

### 3. 推送版本分支並建立 PR

```bash
git add app/build.gradle.kts docs/releases/v1.2.0.md distribution/whatsnew/whatsnew-zh-TW
git commit -m "chore: release v1.2.0"
git push -u origin release/v1.2.0
```

在 GitHub 建立 PR，base 選 `develop`。等待 `Build and Unit Test` required check 通過，再合併 PR。

### 4. 更新本機 develop 並確認合併版本

```bash
git switch develop
git pull --ff-only origin develop
```

確認 `app/build.gradle.kts` 中的 `versionName`、`versionCode` 是準備發布的版本。

### 5. 建立並推送版本 tag

PR 合併後，在最新的 `develop` 執行 publish script：

```bash
./scripts/publish-release.sh 1.2.0
```

Script 會確認工作目錄乾淨、位於最新的 `develop`，檢查 Gradle 版本與 release 檔案，並確認 tag 尚未使用及 `versionCode` 遞增。它會要求輸入完整 tag（例如 `v1.2.0`）確認後，才建立 annotated tag 並只推送該 tag。若確定要在非互動環境執行，可以加上 `--yes`；這會略過確認提示。

> **推送 tag 就會開始 Google Play Production 發版。** 不要用正式 tag 測試，也不要重複使用 tag 或已上傳／用過的 `versionCode`。

### 6. 監看 GitHub Actions

在 repository 開啟 **Actions → Google Play Release**，確認執行成功。Workflow 會：

1. 檢查 tag 格式、tag commit 是否在 `develop` 歷史中，以及 Gradle `versionName` 是否與 tag 相符。
2. 檢查 `versionCode` 是否大於 workflow 能從 `develop` 歷史中找到的先前版本 tag。**仍須自行核對 Play Console**，確保沒有未標 tag 的已上傳版本使用更高的 code。
3. 在 GitHub-hosted Ubuntu runner 解碼 keystore，執行 `./gradlew :app:bundleRelease --stacktrace` 建置簽署的 AAB。
4. 將 `app/build/outputs/bundle/release/app-release.aab` 和 `distribution/whatsnew/whatsnew-zh-TW` 上傳至 Google Play Production，設定為 `inProgress`、10% staged rollout。

若版本或 tag 驗證、簽署、建置或 Play 上傳失敗，請查看失敗步驟的 Actions log。修正版本設定或憑證問題後，請使用未使用過且正確遞增的版本號與 tag；如果只是暫時性的 runner/API 錯誤，可從 Actions run 使用 **Re-run jobs**。

Workflow 目前直接將 AAB 上傳給 Google Play，沒有另外上傳成可下載的 GitHub Actions artifact。

### 7. 在 Play Console 確認 rollout

到 Play Console 確認 Production release 的處理／審核狀態與 10% rollout。觀察發布狀況後，再依需求於 Play Console 擴大 rollout。
