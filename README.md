# MyFitnessApp

This is a personal project that implement a helper app to help people schedule their fitness plan.

This app has been publish in Google Play. [運動小幫手](https://play.google.com/store/apps/details?id=studio.jasonsu.myfitness&hl=zh_TW)

## Releasing to Google Play

To publish a production release, update `versionName` and increment `versionCode` in `app/build.gradle.kts`, merge the change into `develop`, then push a matching semantic version tag such as `v1.2.0` to that commit. The release workflow builds a signed Android App Bundle and starts a 10% staged rollout on Google Play.

Configure these GitHub Actions repository secrets for signing and Play Console access: `KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`, and `PLAY_SERVICE_ACCOUNT_JSON`.
