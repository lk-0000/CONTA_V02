# DECISIONS.md

## D1 — Manual dependency injection instead of Hilt/Dagger
V1's scope (single module, no multi-feature DI graph needed) doesn't justify Hilt.
`ZCesContaApplication` holds lazily-constructed singletons; screens get a
`SimpleViewModelFactory { ... }` closure. Revisit only if the app grows enough modules/
scopes that manual wiring becomes error-prone.

## D2 — Dates stored as ISO-8601 strings, not epoch millis, on `ProjectEntity`/`ScheduleTaskEntity`
The XSD types these as `xs:date` (calendar date, no time/zone). Storing as `"YYYY-MM-DD"`
text keeps Room queries/sorting simple (lexicographic = chronological) and avoids timezone
ambiguity that epoch-millis-at-midnight would introduce. `EntryEntity` additionally stores
`entryDateTime: Long` for true chronological ordering within a day (DANES/VSE).

## D3 — Safety-yellow is a rare accent, orange is the interactive/brand color
`Barva.txt` (logo analysis) gives orange/black/white as the three brand colors.
`PROJECT_CONTEXT.md` §5 separately asks for "restrained safety-yellow accents." Resolved
by treating `CesOrange` as the Material `primary` (buttons, active states) and
`SafetyYellow` as reserved for rare high-attention flags (e.g. HSE / delay), not used in
the base color scheme yet — to be applied per-component as those screens are built.

## D4 — BOQ position identity is the XML `id` attribute, never description text
Per `CLAUDE.md` §Architecture. `BoqPositionEntity.id` is exactly the XSD `position/@id`
value (e.g. `"NAMA-PAS-B3-301-A"`). `positionCode`/`subpositionCode` are kept as separate
columns purely for search/display, never as identity.

## D5 — EntryType is a single flat enum, not two separate tables/sealed hierarchies
Both BOQ-linked and general/daily types share one `entries` table with nullable
type-specific columns, distinguished by `EntryType.isBoqLinked`. Simpler queries for
DANES/VSE (one table, one ORDER BY) outweigh the looser column typing. Revisit if the
type-specific column count grows large enough to make `EntryEntity` unwieldy.

## D6 — Gradle wrapper JAR fetched from `raw.githubusercontent.com`, not generated locally
The bootstrap sandbox can't reach `services.gradle.org` to run `gradle wrapper`, but
`raw.githubusercontent.com` is allow-listed, so the wrapper files were pulled directly from
the public `gradle/gradle` GitHub repo. This is the standard, unmodified wrapper — no
functional difference from one generated via `gradle wrapper --gradle-version 8.13`.

## D7 — CI (GitHub Actions) chosen as the path to an actual APK, not local build-in-sandbox
The sandbox cannot install an Android SDK or reach Google's Maven repo (see
`SESSION_HANDOFF.md`). Rather than hand back only source code, `.github/workflows/
build-debug-apk.yml` was added so a human with a free GitHub account can get a real,
installable `app-debug.apk` without installing Android Studio locally.
