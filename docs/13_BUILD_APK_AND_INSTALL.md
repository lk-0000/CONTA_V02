# Z-CES CONTA — How to build and install the APK

## 1. What you need
A machine that can build Android apps needs:
- a compatible JDK,
- Android SDK/platform/build-tools,
- the repository's Gradle wrapper.

Android Studio is the easiest way to install/manage the Android SDK, but the final build can be run from a terminal.

Claude Code must first run `scripts/check_env.sh` and inspect the actual environment. Do not copy arbitrary old Gradle/Android versions blindly.

## 2. Debug APK — fastest installable result
From project root:

```bash
./gradlew clean assembleDebug
```

Expected output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Android debug APKs are signed by the debug signing configuration and are directly installable for testing.

### Install with USB/ADB
Enable Developer Options + USB debugging on the phone, connect USB, then:

```bash
adb devices
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Install manually
Copy `app-debug.apk` to the phone, open it in Files/Downloads and allow installation from that source when Android asks. The exact settings wording varies by Android manufacturer/version.

## 3. Release APK — for controlled distribution
For a release build you need your own signing key. Keep the key and passwords private and backed up. Do not commit them to git.

### Generate a keystore once
Example command:

```bash
keytool -genkeypair -v \
  -keystore zces-conta-release.jks \
  -alias zces-conta \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000
```

Choose/store strong passwords. Back up the `.jks` securely.

### Store local signing properties
Create a local `keystore.properties` (gitignored), conceptually:

```properties
storeFile=/ABSOLUTE/PATH/zces-conta-release.jks
storePassword=YOUR_STORE_PASSWORD
keyAlias=zces-conta
keyPassword=YOUR_KEY_PASSWORD
```

Claude Code should configure `app/build.gradle.kts` to read this file only when present. Secrets must not be hard-coded.

Then:

```bash
./gradlew clean assembleRelease
```

Expected signed output depends on Gradle config, commonly:

```text
app/build/outputs/apk/release/app-release.apk
```

Verify signing with Android build tools if available, e.g. `apksigner verify --verbose ...`.

## 4. If using Android Studio GUI
Open the project, let Gradle sync, then:
- for debug: Build APK(s), or run the app on a connected device,
- for release: use **Generate Signed App Bundle or APK** and select APK.

## 5. Canonical Claude Code finish checklist
Before saying “APK is ready”, Claude must report:
- build command used,
- whether it succeeded,
- exact APK filesystem path,
- APK size,
- whether install was tested and on what target,
- any remaining acceptance-test failures.

Claude must never claim an APK exists unless it verified the file.

## 6. Later Google Play option
Google Play normally uses a signed Android App Bundle (AAB), but V1's requested direct-install deliverable is APK. Do not block APK delivery on Play Store setup.
