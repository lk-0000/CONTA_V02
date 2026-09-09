# Z-CES CONTA — BUILD APK NOW

This repository contains a GitHub Actions workflow that builds an Android APK without Android Studio.

## Build in GitHub

1. Create a new GitHub repository.
2. Upload/push the entire contents of this folder, including the hidden `.github` folder.
3. Open the repository → **Actions**.
4. Select **Build Z-CES CONTA APK**.
5. Click **Run workflow**.
6. Wait for the build to finish successfully.
7. Open the completed run and download the artifact **Z-CES-CONTA-APK**.
8. Unzip the artifact. It contains `Z-CES-CONTA-v1-debug.apk`.
9. Transfer the APK to the Android phone and open it. Android may ask you to allow installation from the app/source you used to open it.

## Important

The uploaded source project is currently an early skeleton. According to its own `PROGRESS.md` / `TASKS.md`, the current implemented UI includes onboarding, project list, and manual project creation. Camera capture, full entry flows, XML import, Gantt, timeline and export/share are not yet implemented in this source snapshot.

The APK produced by this workflow therefore reflects exactly the code currently present in the project.
