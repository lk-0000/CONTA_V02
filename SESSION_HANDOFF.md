# SESSION_HANDOFF.md

## Environment blocker (read this first)
This project was bootstrapped inside an Anthropic Claude.ai chat sandbox, which:
- has no `javac` (JDK runtime only, no compiler), no Gradle, no Android SDK installed;
- has network egress locked to an allow-list that excludes `dl.google.com` (Android SDK,
  Google Maven / AndroidX / Compose / Room artifacts) and `services.gradle.org`
  (Gradle distribution). Confirmed via `curl` → HTTP 403 on all three.

**Consequence:** `./gradlew assembleDebug` cannot be run or verified inside that sandbox.
No APK was or could be built there. The Gradle wrapper itself (`gradlew`, `gradlew.bat`,
`gradle/wrapper/gradle-wrapper.jar`) *was* fetched successfully from
`raw.githubusercontent.com`, which is allow-listed, so the wrapper is real and complete —
only the actual dependency-download + compile step is blocked in that sandbox.

**Resolution path chosen:** `.github/workflows/build-debug-apk.yml` builds the debug APK
on GitHub-hosted runners (unrestricted network) and uploads it as a workflow artifact.
See `README.md` for the exact steps to get `app-debug.apk` onto a phone.

## What exists in this codebase right now
- Gradle project skeleton (AGP 8.13.0, Kotlin 2.1.20, Compose BOM 2026.08.00, Room 2.6.1,
  DataStore 1.1.1) — versions checked via web search at time of writing (Sept 2026); bump
  in `build.gradle.kts` / `app/build.gradle.kts` if newer stable versions exist by the
  time you build.
- Room schema (v1): `ProjectEntity`, `BoqPositionEntity`, `ScheduleTaskEntity` (+
  predecessors join table), `EntryEntity`, `EntryPhotoEntity`, matching
  `sample/ZCES_CONTA_project.xsd`. All 5 DAOs + `ZCesDatabase` implemented.
- `EntryType` enum with all BOQ-linked + general/daily types from `PROJECT_CONTEXT.md` #3.8-9.
- DataStore `AuthorPreferences` implementing the exact first-launch flow (Manual §1).
- Compose UI: onboarding name screen, projects list (empty state + list), manual
  "add project" dialog (Manual §2.1 ROČNO only — XML/ZIP import not wired yet), nav host.
- Brand theme (colors/typography) sourced from `docs/Barva.txt` and `PROJECT_CONTEXT.md` §5.
- Launcher icon generated from `docs/LOGO_ZNOTRAJ_APLIKACIJE.png` at all mipmap densities.

## What does NOT exist yet (do not claim otherwise)
Everything past Roadmap step 3 in `PROJECT_CONTEXT.md` §8:
- project home screen / DANES / VSE / POPIS / GANTT / IZVOZ tabs,
- the full "+ NOV VNOS" flow (BOQ-linked and general/daily entry forms),
- camera/gallery capture + photo persistence,
- entry edit/delete/revision UI,
- XML import (parser exists nowhere yet — only the sample file is copied into `sample/`),
- full project ZIP export/import,
- unit/instrumented tests,
- release signing verification.

## Immediate next task
Roadmap step 4: "Core Entry flows without photos" — build the "+ NOV VNOS" root choice
screen (POZICIJA IZ POPISA / SPLOŠNO / DNEVNO) and the general/daily entry forms first,
since they need no BOQ import to be testable end-to-end.

## How to resume in a real Claude Code session
Read `CLAUDE.md`, `PROJECT_CONTEXT.md`, `TASKS.md`, `PROGRESS.md`, `SESSION_HANDOFF.md`
(this file), `DECISIONS.md`, then inspect `git log`/`git status` and the code under
`app/src/main/java/com/zces/conta/`, and continue from "Immediate next task" above.
