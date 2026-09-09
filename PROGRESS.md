# PROGRESS.md

## 2026-09-09
- Bootstrapped the Android Gradle project (Kotlin + Compose + Room + DataStore) from scratch
  inside a Claude.ai chat sandbox that has no Android SDK, no Gradle, no `javac`, and
  network egress restricted away from Google Maven / Gradle's distribution server.
  Confirmed the blocker with `curl` (403 on `dl.google.com`, `services.gradle.org`,
  `repo.maven.apache.org`) before proceeding — see `SESSION_HANDOFF.md` for detail.
- Implemented full Room schema (`Project`, `BoqPosition`, `ScheduleTask` + predecessors,
  `Entry`, `EntryPhoto`) matching `sample/ZCES_CONTA_project.xsd`, plus DAOs and
  `ZCesDatabase`.
- Implemented `AuthorPreferences` (DataStore) for the first-launch name prompt.
- Implemented onboarding screen, projects list screen (empty state + list), manual
  add-project dialog, and Compose Navigation wiring between them.
- Applied brand theme from `docs/Barva.txt` (#FF751F orange / black / white, restrained
  safety-yellow accent) and generated a launcher icon from
  `docs/LOGO_ZNOTRAJ_APLIKACIJE.png`.
- Fetched a working Gradle wrapper (`gradlew`, `gradlew.bat`, `gradle-wrapper.jar`) from
  `raw.githubusercontent.com`, which the sandbox's network allow-list does permit.
- Added `.github/workflows/build-debug-apk.yml` so the debug APK can be built on GitHub's
  own (unrestricted) runners and downloaded as a workflow artifact, since local/sandbox
  build is not possible here.
- Did **not** build or verify an actual `.apk` file — no such claim should be made until a
  human runs the GitHub Actions workflow (or a local build) and confirms the artifact.

Next: Roadmap step 4 — core entry flows (POZICIJA IZ POPISA / SPLOŠNO / DNEVNO) without
photos yet.
