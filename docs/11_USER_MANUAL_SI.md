# Z-CES CONTA — Navodila za uporabo aplikacije V1

Ta navodila so namenjena vodji gradbišča. V1 je narejena tako, da se podatki zajamejo hitro na telefonu in se lahko projekt pozneje kot celota pošlje ali shrani.

## 1. Prvi zagon
Ko aplikacijo prvič odpreš, se prikaže:

**Kako naj vas aplikacija označuje?**

Vpiši svoje ime, npr. `Janez Novak`, in pritisni **ZAČNI**. To ni registracija in ne potrebuješ e-maila ali gesla. Ime se dodaja k tvojim vnosom.

## 2. Kako dodaš projekt
Na zaslonu PROJEKTI pritisni **+ DODAJ PROJEKT**.

Imaš tri možnosti:
- **ROČNO** – za preprost projekt brez pripravljenega XML,
- **UVOZI XML** – za nov projekt s popisom in Ganttom,
- **UVOZI PROJEKTNI ZIP** – za obnovo projekta, ki je bil prej izvožen iz Z-CES CONTA.

### 2.1 Ročno
Vpiši kodo, naziv, lokacijo, naročnika in datume. Popis in Gantt sta lahko prazna. Tako lahko začneš takoj delati splošne/dnevne vnose.

### 2.2 Uvoz XML
Pritisni **UVOZI XML** in iz telefona izberi `.xml` datoteko.

Aplikacija najprej samo prebere datoteko in pokaže predogled:
- naziv in kodo projekta,
- število popisnih pozicij,
- število Gantt aktivnosti,
- opozorila, če kaj manjka.

Če je predogled pravilen, pritisni **UVOZI PROJEKT**.

XML lahko vsebuje:
- Project Info,
- BOQ/popis del,
- Gantt aktivnosti,
- povezavo Gantt aktivnosti s popisno pozicijo.

Primer je v paketu: `sample/06_SAMPLE_PROJECT_NAMA.xml`.

## 3. Projektni zaslon
Ko odpreš projekt, so glavna dejanja:
- **+ NOV VNOS**,
- **HITRA FOTO**,
- **DANES**,
- **GANTT**.

Zavihki:
- **DANES** – današnje dogajanje,
- **VSE** – celotna časovnica,
- **POPIS** – uvožene pozicije,
- **GANTT** – terminski pregled,
- **IZVOZ** – share/backup.

## 4. Nov vnos — najpomembnejša izbira
Pritisni **+ NOV VNOS**.

Izberi:

### POZICIJA IZ POPISA
To izberi, če se zapis nanaša na konkretno postavko iz uvoženega popisa.

### SPLOŠNO / DNEVNO
To izberi za podatke, ki niso vezani na konkretno pozicijo: delavci, vreme, splošni zastoj, navodilo, HSE, drugo.

## 5. Vnos vezan na pozicijo
Izberi **POZICIJA IZ POPISA**. Pozicijo poišči po kodi ali opisu, npr. `3.01/b`.

Aplikacija sama pokaže znane podatke iz XML, npr. opis, enoto in pogodbeno količino. Tega ni potrebno ponovno prepisovati.

Nato izberi, kaj se je zgodilo:
- **IZVEDENO DELO**,
- **POTREBNO POPRAVITI**,
- **DODATNO DELO**,
- **PROBLEM / ZASTOJ**,
- **DOBAVA**,
- **KONTROLA / SKRITO DELO**,
- **OPOMBA**.

### Primer: izvedeno delo
Če je bilo narejenih 18,50 m²:
- Količina: `18,50`,
- enota se prikaže sama iz popisa,
- Lokacija: `Pasaža vzhod`,
- Delavci: po potrebi,
- Opis: npr. `Nadaljevala se je montaža. Dva panela ostajata odprta zaradi detajla vrat.`,
- dodaj fotografije,
- **SHRANI**.

Ta količina je v V1 označena kot **Evidentirano**. To še ni avtomatsko potrjena obračunska količina.

### Primer: potrebno popraviti
Izberi isto popisno pozicijo → **POTREBNO POPRAVITI** → napiši kaj/kje → fotografiraj → shrani. Količina ni obvezna.

### Primer: dodatno delo
Izberi povezano pozicijo, če obstaja → **DODATNO DELO** → opiši dodatno zahtevo → opcijsko vnesi ocenjeno količino → fotografiraj → shrani. Aplikacija dodatnega dela ne sme avtomatsko prišteti pogodbeni količini.

## 6. Splošni/dnevni podatki
Izberi **SPLOŠNO / DNEVNO**.

### DELOVNA SILA
Vpiši npr.:
- lastni delavci: 7,
- podizvajalci: 3.

Aplikacija pokaže skupaj 10.

### VREME
Izberi obdobje in stanje, npr. jutro → dež. Temperatura je opcijska. Če je ne poznaš, pusti prazno.

### ZASTOJ / IZGUBA ČASA
Izberi razlog (vreme, material, načrt, dvigalo, naročnik, nadzor, varnost, drugo), vnesi od/do in prizadete delavce. Aplikacija lahko izračuna ure zastoja in izgubljene delovne ure.

Primer: 07:30–09:30, 6 delavcev → 2 h zastoja in 12 izgubljenih delovnih ur.

Po potrebi poveži zastoj z Gantt aktivnostjo.

## 7. Fotografiranje
V obrazcu pritisni **FOTOGRAFIRAJ**. Odpre se kamera telefona. Naredi fotografijo in jo potrdi.

Lahko narediš več fotografij. Pred shranjevanjem vidiš predoglede in lahko napačno fotografijo odstraniš. Na voljo je tudi izbira iz galerije.

Fotografija vedno ostane vezana na pravi projekt in vnos.

## 8. HITRA FOTO
Če želiš samo hitro zajeti stanje, na projektu uporabi **HITRA FOTO**. Aplikacija odpre kratek vnos s kamero in opisom; po potrebi ga lahko pozneje urediš in povežeš s pravilno pozicijo.

## 9. Urejanje kasneje
Odpri vnos iz DANES ali VSE. Prikažejo se podatki, fotografije, čas nastanka in zadnje spremembe.

Pritisni **UREDI**. Lahko popraviš opis, količino, lokacijo, povezano pozicijo in fotografije. Po uspešni spremembi aplikacija poveča `revision` in ohrani originalni čas ustvarjanja.

## 10. DANES in VSE
**DANES** pokaže kronološko vse današnje vnose.

**VSE** je timeline celotnega projekta. Uporabi iskanje in filtre, npr. delo, problem, dodatno delo, vreme, zastoj.

## 11. POPIS
POPIS je read-only pregled uvoženih pozicij. Pri poziciji lahko vidiš vsoto `executed_work` vnosov kot **Evidentirano**. To ni obračunska potrditev.

## 12. GANTT
GANTT pokaže uvožen plan. Horizontalno podrsaj po času. Pritisni aktivnost za podrobnosti.

Če je Gantt aktivnost povezana s popisno pozicijo, lahko aplikacija pokaže fizični napredek iz evidentiranih količin. Če so bili na aktivnost vezani zastoji, jih lahko vidiš pri podrobnostih. V1 datuma aktivnosti ne premika avtomatsko.

## 13. Kako deliš posamezen vnos
Odpri vnos → **DELI**.

Aplikacija pripravi paket z `entry.xml`, človeško berljivim `entry.html` in fotografijami. Nato se odpre običajni Android Share meni. Lahko izbereš aplikacijo, ki jo imaš na telefonu, npr. Gmail, Drive, OneDrive, Files ali drugo.

## 14. Kako deliš celoten dan
Na DANES/IZVOZ izberi **DAN** in datum. Aplikacija pripravi ZIP z vsemi vnosi in fotografijami tega dne.

## 15. Kako izvoziš celoten projekt
Odpri projekt → **IZVOZ** → **CEL PROJEKT** → **USTVARI IZVOZ**.

Projektni ZIP vsebuje:
- `project.xml`,
- `entries.xml`,
- `manifest.json`,
- `project_timeline.html`,
- vse fotografije po datumih.

Datoteko lahko:
- deliš preko Android Sharesheeta,
- shraniš na telefon/računalnik prek Files/USB,
- shraniš v Drive/OneDrive, če je ponudnik nameščen,
- pošlješ po e-mailu, če velikost priponke to dovoljuje.

Pri velikih projektih je bolj praktičen Drive/OneDrive/Files kot e-mail.

## 16. Kako preneseš projekt na drug telefon
Na prvem telefonu izvozi **CEL PROJEKT** kot ZIP.

Na drugem telefonu:
- odpri Z-CES CONTA,
- **+ DODAJ PROJEKT**,
- **UVOZI PROJEKTNI ZIP**,
- izberi ZIP,
- preveri predogled,
- **UVOZI KOT NOV PROJEKT**.

Aplikacija obnovi projekt, popis, Gantt, vnose in fotografije.

## 17. Backup pravilo
Ker je V1 lokalna/offline aplikacija brez centralnega serverja, je priporočljivo redno izvoziti projektni ZIP. Celoten projektni export je hkrati prenosljiva varnostna kopija.

## 18. Kaj bo prišlo kasneje
Iz teh strukturiranih podatkov se lahko v kasnejši verziji izdelujejo Gradbeni dnevnik, Obračunski list, Gradbena knjiga, Situacije in Končni obračun. V V1 tega ne mešamo z osnovnim terenskim zajemom.
