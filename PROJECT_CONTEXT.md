# PROJECT CONTEXT — Z-CES CONTA

> Long-term project memory for Claude Code. Keep this file concise, accurate and current. It should survive context compaction and new Claude Code sessions. Detailed history belongs in `CHANGELOG.md`; technical decisions belong in `DECISIONS.md`.

---

## 0. HOW CLAUDE CODE MUST USE THIS FILE

At the beginning of every fresh context/session:
1. read this file,
2. read `TASKS.md`, `PROGRESS.md`, `SESSION_HANDOFF.md` and `DECISIONS.md`,
3. inspect the current codebase and git status,
4. continue from the highest-priority unfinished task rather than restarting the project.

When context is getting long, save progress to the files above before compaction. Do not stop a task early only because the current chat/context is long.

Update this file only when stable project intent, architecture, current state or hard rules change. Do not append conversational notes here.

If this file conflicts with the user's latest explicit instruction, the user's instruction wins. Then update this file to match.

---

## 1. PROJECT OVERVIEW

- **App name:** Z-CES CONTA
- **Displayed subtitle:** none. Never display “Construction Assistant”; it was only an explanation of the name.
- **Platform:** Android-first native app; final deliverable must include an installable APK.
- **One-line purpose:** A simple offline field application for site managers to capture structured construction-site entries, photos, BOQ-linked quantities, general daily facts and delays, then edit, review and export/share the project as a portable data package.
- **Primary users:** vodja gradbišča / site manager using an Android phone on site.
- **V1 success:** A site manager can create/import a project, record daily evidence in seconds, use the device camera, edit entries later, see timeline/Gantt, and export/share a single entry, a day or the complete project with all photos and structured data. A full project export can be restored into Z-CES CONTA.

### Future purpose, not V1 output
The structured source data must later support:
- Gradbeni dnevnik,
- Obračunski list,
- Gradbena knjiga,
- Situacije,
- Končni obračun.

V1 does not automatically generate these official documents.

---

## 2. CURRENT STATE

- **Specification state:** product concept and XML/export contracts defined.
- **Implementation state:** Android application code has not yet been completed in this handoff package.
- **Reference inputs available:** NAMA BOQ Excel files, construction evidence/manual PDF, UI reference image, sample NAMA XML, XML schema and sample entry.
- **Immediate goal:** create a working native Android project, implement core V1 phase-by-phase, then produce a tested installable APK.
- **Known environment dependency:** APK compilation requires a compatible JDK, Android SDK and Gradle/Android Gradle Plugin environment. Claude must detect what is installed rather than inventing versions.

---

## 3. HARD PRODUCT DECISIONS

1. App UI name is exactly **Z-CES CONTA**.
2. No user-facing registration or email/password login in V1.
3. First launch asks only: **“Kako naj vas aplikacija označuje?”** and stores the author name locally.
4. V1 is local/offline-first; no server, cloud database or custom API is required.
5. Projects can be created manually or imported from Z-CES XML.
6. Imported XML can contain Project Info + BOQ + Gantt schedule + task↔BOQ mappings.
7. `+ NOV VNOS` begins with exactly two conceptual choices: **POZICIJA IZ POPISA** or **SPLOŠNO / DNEVNO**.
8. BOQ-linked entry types: executed work, repair required, additional work, problem/delay, delivery, hidden work/control, note.
9. General/daily types: workforce, weather, delay/lost time, general event, instruction, general problem, HSE, other.
10. Camera capture uses the phone's camera app through Android system APIs; gallery remains available.
11. Every photo belongs to one Entry and therefore keeps project/date/context.
12. Every Entry can be edited after save. Preserve `created_at`, `updated_at`, `revision`.
13. Field `executed_work` quantity is **Evidentirano**, not automatically approved/certified/invoiced.
14. Missing data is never guessed.
15. Gantt is a read-only site-tracking view in V1, not an MS Project replacement.
16. Gantt may show field progress from executed quantity / contract quantity when a task is mapped to a BOQ position.
17. V1 does not automatically move Gantt dates because of delays.
18. Share/export is core: single entry, day, full project.
19. Full project export must include project XML, entries, photos, manifest and human-readable timeline, and must be re-importable/restorable.
20. Export/share is also the V1 backup/portability mechanism.

---

## 4. TECHNICAL ARCHITECTURE

- **Language:** Kotlin.
- **UI:** Jetpack Compose + Material 3.
- **Persistence:** Room SQLite.
- **Preferences:** DataStore.
- **Architecture:** simple MVVM + repositories/services.
- **Camera:** Activity Result `TakePicture` / device camera app, with FileProvider URI.
- **Gallery:** Activity Result document/media picker supporting multiple images.
- **XML import:** Android Storage Access Framework + safe XML parser.
- **Project ZIP import:** Storage Access Framework + validated unzip into app-controlled storage + transactional database restore.
- **Export:** XML + static HTML + JSON manifest + photos packaged with `ZipOutputStream`.
- **Share:** FileProvider + Android Sharesheet.
- **Photos:** stored as files, not DB BLOBs; Room stores metadata/relative path.
- **Gantt:** mobile-friendly Compose implementation, read-only, horizontally scrollable.
- **Backend/network:** none in V1.

### Versioning rules
- XML root version starts at `1.0`.
- Project export manifest includes `export_version`.
- Parsers must validate stable IDs and project isolation.

---

## 5. UX / VISUAL CONVENTIONS

- Mobile-first; usable with one hand on a construction site.
- Large touch targets and clear Slovenian labels.
- Professional, clean construction aesthetic.
- Light background, dark/navy text, restrained safety-yellow accents.
- Avoid decorative complexity and dense admin dashboards.
- Minimize typing and duplicate entry.
- Auto-fill unit and known BOQ data after position selection.
- Date/time default automatically, but allow editing when needed.
- Saving must show success only after persistence actually succeeds.

Primary navigation inside a project:
`DANES | VSE | POPIS | GANTT | IZVOZ`

Primary project actions:
`+ NOV VNOS | HITRA FOTO | DANES | GANTT`

---

## 6. DATA INTEGRITY RULES

- Project A data/photos must never appear in Project B.
- BOQ/task foreign keys must belong to the current project.
- XML duplicate stable IDs produce an explicit import error/warning.
- Optional missing XML fields stay null/blank.
- Decimal values in XML use dot separator.
- Display can use Slovenian decimal formatting.
- Editing an entry updates the same logical Entry and increments revision; do not silently duplicate.
- Deleting an entry requires confirmation.
- Full-project import must prevent path traversal/zip-slip and reject unsafe archive paths.
- Imported project files must be validated before committing database changes.

---

## 7. NON-GOALS FOR V1

Do not add unless the user explicitly changes scope:
- user accounts/roles/organization admin,
- cloud sync,
- web dashboard,
- automatic official Gradbeni dnevnik generation,
- certified quantities/approval workflows,
- invoices/accounting,
- Situacija generation,
- Končni obračun generation,
- messaging/WhatsApp integration,
- AI backend,
- critical path engine,
- automatic schedule rescheduling.

---

## 8. ROADMAP / ORDER OF IMPLEMENTATION

1. Environment check + Android project bootstrap + CI-like local build command.
2. Room schema + repositories + local author profile.
3. Projects + manual creation/edit/archive.
4. Core Entry flows without photos.
5. Camera/gallery + photo persistence.
6. Entry detail/edit/delete/revision.
7. XML import preview + commit + BOQ selection.
8. Timeline/search/filters + Today.
9. Gantt.
10. Entry/day/full project export/share.
11. Full project ZIP re-import/restore.
12. QA/data integrity/performance.
13. Build debug APK and install-test.
14. Prepare signed release APK workflow.
15. Update user manual to exact shipped UI.

---

## 9. DEFINITION OF DONE

The project is not “done” merely because screens exist. Done means the acceptance tests pass, the project builds, an APK is produced, the APK installs on a real/emulated Android device, XML import works with the included NAMA sample, camera photos stay attached to the correct entry, edits persist, and a full project can be exported and restored without losing entries/photos.

---

## 10. MEMORY MAINTENANCE

After each meaningful implementation session:
- update current implementation status in section 2 if needed,
- update roadmap only if priorities changed,
- append history to `CHANGELOG.md`,
- update `PROGRESS.md`, `TASKS.md` and `SESSION_HANDOFF.md`,
- record stable decisions in `DECISIONS.md`.

Last prepared: 2026-09-09.
