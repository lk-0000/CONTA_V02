# Kako uporabiš paket v Claude Code

## Možnost A — Claude Code dela nad mapo/repozitorijem
To je priporočeno.

1. Razširi `Z-CES_CONTA_CLAUDE_CODE_ANDROID_APK_PACKAGE.zip`.
2. Odpri mapo `Z-CES_CONTA_CLAUDE_CODE_PACKAGE` kot delovno mapo v Claude Code.
3. Datoteke `CLAUDE.md`, `PROJECT_CONTEXT.md`, `TASKS.md`, `PROGRESS.md`, `SESSION_HANDOFF.md` in `DECISIONS.md` morajo ostati v korenu projekta.
4. Kot prvo sporočilo prilepi `01_FIRST_PROMPT_COPY_PASTE.txt`.
5. Claude naj začne s PHASE 0 in dejansko ustvarja/ureja kodo v isti mapi.

Ko Claude ustvari Android projekt, ne premikaj memory/spec datotek iz repozitorija. Naj ostanejo zraven kode.

## Možnost B — Claude Code spletna stran / ročno nalaganje datotek
Če delaš v spletnem vmesniku, kjer mu moraš datoteke naložiti:

Najprej naloži vsaj:
- `CLAUDE.md`,
- `PROJECT_CONTEXT_FOR_CLAUDE_CODE.txt` (ali `PROJECT_CONTEXT.md`),
- `01_FIRST_PROMPT_COPY_PASTE.txt`,
- `03_BUILD_PHASES.md`,
- `04_FUNCTIONAL_SPEC.md`,
- `05_ANDROID_ARCHITECTURE.md`,
- `06_DATA_MODEL.md`,
- `07_XML_FORMAT_SPEC.md`,
- `08_EXPORT_SHARE_SPEC.md`,
- `09_UI_SCREEN_FLOW.md`,
- `10_GANTT_SPEC.md`,
- `12_ACCEPTANCE_TESTS.md`,
- sample XML/XSD.

Če spletni Claude Code omogoča upload celotne mape/repozitorija, naloži celotno razširjeno mapo.

## Prvo sporočilo
Prilepi vsebino:
`01_FIRST_PROMPT_COPY_PASTE.txt`

Ne piši mu ponovno celotne ideje v prostem tekstu. Namen package datotek je, da ostane specifikacija konsistentna tudi po daljšem delu.

## Ko se odpre nov chat/context
Prilepi samo:

```text
Read CLAUDE.md, PROJECT_CONTEXT.md, TASKS.md, PROGRESS.md, SESSION_HANDOFF.md and DECISIONS.md. Inspect git status/log and current code. Continue from the highest-priority unfinished task. Do not restart completed work. Implement and verify, and persist state before context compaction.
```

## Kako dobiš APK
Ko je implementacija končana, Claude mora zagnati:

```bash
./gradlew assembleDebug
```

in preveriti obstoj:

```text
app/build/outputs/apk/debug/app-debug.apk
```

To datoteko lahko kopiraš na Android telefon in namestiš. Za podpisan release APK uporabi `13_BUILD_APK_AND_INSTALL.md`.

## Pomembno
Če okolje Claude Code spletne strani nima Android SDK-ja, Claude lahko še vedno izdela kodo, teste in Gradle projekt, APK pa bo treba zadnjič zgraditi v okolju z Android SDK (npr. lokalni računalnik/Android Studio ali drug build runner). Claude ne sme trditi, da je APK zgrajen, dokler dejansko ne preveri datoteke.
