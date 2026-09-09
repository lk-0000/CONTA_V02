# Z-CES CONTA — how to get the APK onto your phone

This chat environment could not build the APK itself (no Android SDK, no Gradle, and its
network can't reach Google's Maven repo or Gradle's distribution server — see
`SESSION_HANDOFF.md` for the exact test that proved this). The code is real and ready to
build; you need an environment that actually has internet + an Android SDK to finish the
last step. Two ways to do that:

## Option A — GitHub Actions (recommended: no software install)
1. Unzip this package and push it to a **new GitHub repository** (github.com → New
   repository → follow the "push an existing folder" instructions it shows you).
   You need a free GitHub account, `git`, and 5 minutes — no Android Studio required.
2. Once pushed, GitHub automatically runs `.github/workflows/build-debug-apk.yml`
   (visible under the repo's **Actions** tab). It installs JDK 17 + the Android SDK on
   GitHub's own server and runs `./gradlew clean assembleDebug`.
3. Wait for the run to go green (a few minutes).
4. Open that run → scroll to **Artifacts** → download `zces-conta-debug-apk` → unzip it →
   you now have `app-debug.apk`.
5. Copy `app-debug.apk` to your phone (Drive/email/USB — any method) and open it. Android
   will ask to allow installing from that source; approve it. This is a debug build (self-
   signed for testing, per `docs/13_BUILD_APK_AND_INSTALL.md` §2) — that's expected.

If you don't already have `git`/a GitHub repo set up and want, tell me and I'll walk you
through the exact commands.

## Option B — Android Studio on your own machine
1. Install [Android Studio](https://developer.android.com/studio) (it bundles a compatible
   JDK + lets you install the Android SDK through its SDK Manager).
2. Unzip this package, open the folder in Android Studio, let Gradle sync.
3. **Build → Build App Bundle(s) / APK(s) → Build APK(s)**, or just run it on a connected
   phone/emulator with the green ▶ button.
4. APK lands at `app/build/outputs/apk/debug/app-debug.apk` — copy it to your phone as in
   Option A step 5.

## Important — what you'll actually see
This is an early skeleton (roadmap step ~3 of 15 in `PROJECT_CONTEXT.md`), not the full V1
feature set. Right now the APK will show:
- the first-launch "Kako naj vas aplikacija označuje?" name prompt,
- an empty **PROJEKTI** list with a **+** button that opens a manual "add project" dialog
  (code/naziv/lokacija/naročnik) and saves it into a local Room database.

Camera capture, the full "+ NOV VNOS" entry flow, XML import, Gantt, and export/share are
**not built yet** — see `TASKS.md` for exactly what's done vs. pending. If you'd like, I
can keep building the next roadmap steps (entry flows, camera, XML import...) in this same
chat before you do another CI build — just say so.
