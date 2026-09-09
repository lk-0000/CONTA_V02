# MASTER PROMPT ZA CLAUDE CODE — Z-CES CONTA V1 / ANDROID APK

<role>
Deluj kot senior Android/Kotlin arhitekt in implementator za terensko gradbeno aplikacijo. Tvoja naloga ni samo napisati načrt, ampak dejansko ustvariti, zagnati, testirati in popraviti kodo v repozitoriju do delujočega Android APK-ja.
</role>

<product>
Ime aplikacije je samo **Z-CES CONTA**. Nikjer v UI ne prikazuj “Construction Assistant”.

Z-CES CONTA V1 je preprosta offline terenska aplikacija za vodjo gradbišča. Vodja mora lahko:
- ročno ustvari projekt ali uvozi XML,
- pri projektu uvozi projektne podatke, BOQ/popis in Gantt,
- naredi nov vnos vezan na pozicijo ali splošen/dnevni vnos,
- uporabi kamero telefona in doda več fotografij,
- shrani vnos lokalno,
- vnos kasneje uredi,
- vidi DANES in celoten timeline,
- vidi popis in read-only Gantt,
- deli posamezen vnos, dan ali celoten projekt,
- izvozi celoten projekt kot ZIP z XML/HTML/JSON in vsemi slikami,
- uvozi/obnovi tak projektni ZIP na drugem telefonu.

V1 NE generira še Gradbenega dnevnika, Obračunskega lista, Gradbene knjige, Situacij ali Končnega obračuna. Podatki morajo biti strukturirani tako, da se to lahko doda kasneje.
</product>

<hard_constraints>
1. Android native: Kotlin + Jetpack Compose.
2. Cilj je installable APK.
3. Brez user-facing login/registracije/email gesla.
4. Ob prvem zagonu samo vprašaj: “Kako naj vas aplikacija označuje?” in ime shrani lokalno.
5. Brez serverja/custom backenda/cloud baze v V1.
6. Uporabi Room + DataStore + lokalne datoteke.
7. Fotografije naj odprejo sistemsko kamero naprave preko Activity Result API; omogoči tudi galerijo.
8. Fotografije niso BLOB-i v Roomu; datoteke hrani ločeno, DB hrani metadata/relative path.
9. Vnosi so urejljivi; ohrani created_at, updated_at, revision.
10. Manjkajočih podatkov ne ugibaj.
11. executed_work količina je “Evidentirano”, ne “Potrjeno/obračunano”.
12. Gantt je read-only pregled, brez avtomatskega prestavljanja planov.
13. XML in full-project ZIP sta versioned portable contracta.
14. Full-project ZIP mora biti re-importable.
15. Ne dodajaj funkcij iz prihodnjih verzij, če niso potrebne za V1.
</hard_constraints>

<stack>
- Kotlin
- Jetpack Compose / Material 3
- Room
- DataStore Preferences
- Navigation Compose
- ViewModel + Coroutines/Flow
- Android Storage Access Framework
- Activity Result contracts (`TakePicture`, document/media picker)
- FileProvider + Android Sharesheet
- XmlPullParser/XmlSerializer or equally lightweight Android-standard XML solution
- java.util.zip ZipInputStream/ZipOutputStream
- JUnit/unit tests; Compose/UI tests where environment permits
</stack>

<environment_rule>
Najprej odkrij, kaj je dejansko nameščeno: Java/JDK, Android SDK, platforme, Gradle. Ne ugibaj AGP/SDK verzij. Izberi kompatibilne stabilne verzije glede na okolje. Če Android SDK manjka, pripravi projekt in navodila/setup skripte, jasno zapiši blocker, vendar ne trdi, da je APK zgrajen.
</environment_rule>

<data_model>
Implementiraj Room entitete/relacije za:
- Project
- BoqPosition
- ScheduleTask
- Entry
- EntryPhoto

Author/device profile shrani v DataStore.

Stable external IDs iz XML se morajo ohraniti. BOQ in ScheduleTask vedno pripadata Projectu. Entry lahko opcijsko referencira BOQ in/ali task istega projekta.
</data_model>

<new_entry_flow>
Prvi korak + NOV VNOS ima dve veliki izbiri:

A) POZICIJA IZ POPISA
- izberi BOQ pozicijo iz trenutnega projekta,
- searchable po kodi in opisu,
- pokaži kodo/opis/enoto/pogodbeno količino/ceno read-only,
- nato tip:
  - IZVEDENO DELO (`executed_work`)
  - POTREBNO POPRAVITI (`repair_required`)
  - DODATNO DELO (`additional_work`)
  - PROBLEM / ZASTOJ (`problem_delay`)
  - DOBAVA (`delivery`)
  - KONTROLA / SKRITO DELO (`hidden_work_control`)
  - OPOMBA (`note`)

B) SPLOŠNO / DNEVNO
- DELOVNA SILA (`workforce`)
- VREME (`weather`)
- ZASTOJ / IZGUBA ČASA (`delay`)
- SPLOŠNI DOGODEK (`general_event`)
- NAVODILO (`instruction`)
- PROBLEM (`general_problem`)
- HSE (`hse`)
- DRUGO (`other`)
</new_entry_flow>

<forms>
Executed work:
- quantity decimal,
- unit auto iz BOQ,
- location optional,
- workers optional,
- description,
- photos.

Repair required:
- location,
- description,
- photos.

Additional work:
- description,
- estimated quantity optional,
- unit if known,
- location,
- photos,
- status default `evidentirano`; never merge automatically into contract quantity.

Workforce:
- own workers,
- subcontractor workers,
- total calculated.

Weather:
- period (jutro/dopoldne/popoldne/celodnevno/custom),
- condition,
- temperature optional,
- description optional.

Delay:
- reason,
- start/end,
- hours calculated,
- affected workers,
- lost man-hours calculated,
- description,
- optional linked schedule task,
- photos optional.
</forms>

<camera>
Gumb FOTOGRAFIRAJ naj uporabi Android sistemsko kamero (ne graditi lastnega kompleksnega CameraX UI, če ni potrebno). Pred klicem kamere pripravi varno FileProvider URI. Po uspešnem posnetku prikaži thumbnail. Omogoči več posnetkov in odstranitev pred shranjevanjem. Omogoči galerijo kot alternativni vir.
</camera>

<xml_import>
Podpri `sample/06_SAMPLE_PROJECT_NAMA.xml` in `sample/ZCES_CONTA_project.xsd` koncept.

Import flow:
1. OpenDocument `.xml`.
2. Parse brez commit-a.
3. Validiraj root/version, duplicate IDs, datume, decimalne vrednosti, mappings.
4. Pokaži preview: project name/code, BOQ count, schedule task count, warnings.
5. Uporabnik potrdi UVOZI PROJEKT.
6. Transakcijsko shrani Project + BOQ + ScheduleTask.
7. Nikoli ne ugibaj manjkajočih opcijskih polj.
</xml_import>

<gantt>
Read-only mobilni Gantt:
- horizontal scroll,
- row per task,
- start/finish bar,
- milestone marker,
- today marker,
- task detail,
- day/week scale,
- mapped BOQ field progress = sum executed_work quantity / contract quantity, če je matematično smiselno.
- prikaz “Evidentirano na terenu”.
- brez critical-path engine in brez avtomatskih schedule sprememb.
</gantt>

<editing>
Entry detail ima UREDI / DELI / IZBRIŠI.
Edit mora omogočiti spremembo relevantnih polj, pozicije/task povezave in fotografij. Ob uspešnem save:
- isti Entry ID,
- `revision += 1`,
- `updated_at = now`,
- `created_at` ostane enak.
</editing>

<export_share>
Implementiraj tri scope:
1. single Entry,
2. day,
3. full Project.

Single Entry ZIP:
- entry.xml
- entry.html
- photos/

Day ZIP:
- day_entries.xml
- day_timeline.html
- photos/

Full Project ZIP:
- project.xml
- entries.xml
- manifest.json
- project_timeline.html
- photos/YYYY-MM-DD/...

HTML mora delovati po unzipu offline. Fotografije naj imajo relative paths.

Po generiranju uporabi Android Sharesheet. Omogoči tudi “Shrani datoteko” preko Storage Access Framework.

Full Project ZIP mora aplikacija znati ponovno uvoziti/obnoviti. Uvoz mora biti varen pred zip-slip/path traversal in mora validirati manifest/XML pred commitom.
</export_share>

<ui>
Slovenian labels, mobile-first, large touch targets. Light background, navy/dark text, restrained safety-yellow accents. Minimal clicks and typing.

Project tabs:
DANES | VSE | POPIS | GANTT | IZVOZ

Project home main actions:
+ NOV VNOS | HITRA FOTO | DANES | GANTT
</ui>

<testing>
Implementiraj testabilne parsers/repositories/export logic. Za vsako fazo zaženi relevantne teste in build. Ne odstranjuj veljavnih testov zato, da build uspe.

Posebej preveri:
- project isolation,
- XML duplicates,
- missing optional values,
- decimal quantities,
- edit/revision,
- photo ownership,
- ZIP path safety,
- export/import roundtrip.
</testing>

<long_horizon_protocol>
To je večfazna naloga. Ne poskušaj vsega narediti v enem nepreglednem koraku in ne zaključuj prezgodaj zaradi dolžine konteksta.

Stanje vzdržuj na disku:
- TASKS.md,
- PROGRESS.md,
- SESSION_HANDOFF.md,
- DECISIONS.md,
- tests/acceptance_tests.json.

Pred compaction/session end shrani checkpoint. Po novem kontekstu najprej preberi te datoteke in git log ter nadaljuj, ne restartaj.
</long_horizon_protocol>

<definition_of_done>
V1 je končan, ko acceptance test checklist uspe, `./gradlew assembleDebug` ustvari installable APK, APK se namesti in zažene, vključeni NAMA XML se uvozi, kamera deluje, edit deluje, timeline/Gantt delujeta, projekt se izvozi z vsemi slikami in se iz izvoženega ZIP-a uspešno obnovi.
</definition_of_done>
