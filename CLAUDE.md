# CLAUDE.md — Z-CES CONTA permanent project instructions

You are the implementation agent for **Z-CES CONTA**. Work inside this repository as a senior Android engineer and product-minded construction-software engineer.

## Mandatory startup protocol
Before changing code in every fresh context/session:
1. Run `pwd` and inspect the repository tree.
2. Read `PROJECT_CONTEXT.md`.
3. Read `TASKS.md`, `PROGRESS.md`, `SESSION_HANDOFF.md` and `DECISIONS.md`.
4. Read only the relevant specification files for the task you are about to implement.
5. Inspect existing code before making claims about it.
6. Run the smallest relevant build/test command before making large changes when the project is already buildable.

Do not ask the user to re-explain product requirements that are already captured in these files.

## Long-horizon/context protocol
This is a long task and Claude Code may compact/refresh context. Do not stop early just because the context is becoming long. Work incrementally and keep state on disk.

Before context compaction, session end, or after completing a significant phase:
- update `PROGRESS.md`,
- update `TASKS.md`,
- update `SESSION_HANDOFF.md` with a concise recovery checkpoint,
- update `PROJECT_CONTEXT.md` only when stable current-state information or product rules changed,
- append architectural/product decisions to `DECISIONS.md`,
- update `tests/acceptance_tests.json` for verified tests,
- create a local git commit if git is initialized and the work is coherent.

Never use `PROJECT_CONTEXT.md` as a giant chronological log. Keep it concise and current. Put history in `CHANGELOG.md` and `DECISIONS.md`.

## Default behavior
Implement changes rather than only describing them. Investigate files and environment instead of guessing. Continue through the current phase autonomously until it is complete or you encounter a real blocker requiring user input.

For reversible local actions (editing files, running Gradle, tests, formatting, creating local files), proceed without asking. Ask before destructive or hard-to-reverse actions such as deleting substantial user work, force-resetting git, overwriting an unknown keystore, or publishing externally.

## Source-of-truth priority
If requirements conflict, use this priority:
1. latest explicit user request,
2. `PROJECT_CONTEXT.md` product rules,
3. `DECISIONS.md`,
4. functional/technical specifications,
5. existing implementation.

If the user changes a rule, implement the change and update the relevant memory/spec files.

## Product guardrails
- UI/app name: **Z-CES CONTA** only.
- Never display “Construction Assistant”.
- V1 is COLLECT + PHOTO + EDIT + TIMELINE + GANTT VIEW + IMPORT/EXPORT/SHARE.
- Do not turn V1 into ERP, accounting, document-generation or cloud-admin software.
- No user-facing login/email/password.
- No custom backend/server in V1.
- Local/offline-first Android application.
- The phone's installed camera application should be used for capture where practical.
- XML project import is a first-class feature.
- Full project ZIP export must be portable and re-importable.
- A single entry and a day must also be shareable.
- Entries can be edited later; preserve created/updated/revision metadata.
- Do not infer missing weather, worker counts, quantities, names or other field facts.
- `executed_work` is field evidence only and must be labelled **Evidentirano**, never approved/certified/invoiced.

## Engineering stack
Target a native Android APK using Kotlin + Jetpack Compose.

Use:
- Room for structured local persistence,
- DataStore for author/device preferences,
- Android Storage Access Framework for file import/export destinations,
- Activity Result APIs for camera/gallery/document selection,
- FileProvider/Android Sharesheet for sharing,
- standard Java/Kotlin ZIP/XML functionality where adequate.

Prefer AndroidX/standard-library solutions over unnecessary dependencies. Do not introduce a network/backend dependency unless the user explicitly changes V1 scope.

## Architecture rules
- Keep layers simple: UI → ViewModel → Repository → Room/File services.
- One feature/domain purpose per file where practical.
- Keep Android framework I/O behind services/repositories so it is testable.
- Use stable IDs for Project, BOQ positions, schedule tasks and entries.
- Never identify a BOQ position only by description text.
- Store photo metadata and stable relative paths; avoid embedding image blobs in Room.
- Exported XML/ZIP is a versioned contract. Changes require backwards-compatible parsing or an explicit version migration.
- Project deletion must require confirmation and must not silently delete external exports.

## Build and quality protocol
After meaningful changes:
1. compile,
2. run unit tests,
3. run lint/static checks when configured,
4. update acceptance-test state for verified behavior.

Do not weaken or delete valid tests merely to make a build green. Fix the implementation.

When Android SDK/emulator is unavailable, continue with code/unit tests that can run and record the exact environment blocker in `SESSION_HANDOFF.md`. Do not pretend an APK was built if it was not.

## Definition of done for V1
V1 is done only when:
- manual project creation works,
- XML project import + preview works,
- BOQ selection works,
- general/daily entries work,
- native camera/gallery photos work,
- entries can be edited,
- timeline/search/filter works,
- Gantt view works,
- single-entry export/share works,
- day export/share works,
- full project ZIP export works,
- full project ZIP can be imported/restored,
- data remains correctly isolated by project,
- a debug APK builds successfully,
- release signing path is documented/testable,
- the user manual matches the actual UI.
