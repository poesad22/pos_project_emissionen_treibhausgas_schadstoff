# Developer Notes – Umweltbundesamt Open Data API

> **Zweck:** Kurzdokumentation der drei verwendeten Datenquellen für die Spring Boot Schadstoff-Datenbank.  
> **Lizenz:** CC-BY-4.0 (Umweltbundesamt Österreich / Agrarforschung Austria)  
> **Base URL:** `https://opendata.api.agrarforschung.at/umweltbundesamt/`

---

## Überblick

Alle drei Endpunkte sind REST-APIs des **Umweltbundesamts Österreich**, bereitgestellt über das Open-Data-Portal der Agrarforschung Austria. Die Daten sind öffentlich zugänglich und werden jährlich aktualisiert. Sie decken zwei thematische Bereiche ab:

- **Treibhausgase** (CO₂, CH₄, N₂O, F-Gase etc.) – nach zwei verschiedenen Berichtsschemata
- **Luftschadstoffe** (NOx, SO₂, NH₃, PM etc.) – nach EU-Berichtsformat

---

## Endpunkte

### 1. `treibhausgas_emissionen_nach_ksg` – Treibhausgase nach Klimaschutzgesetz

**Berichtsrahmen:** Österreichisches Klimaschutzgesetz (KSG) – nationale Sektoreinteilung  
**Schadstoff:** ausschließlich `THG` (Treibhausgase gesamt, CO₂-Äquivalent)  
**Zeitreihe:** 1990–2023 (NowCast)

**JSON-Felder:**

| Feld | Typ | Beispielwert | Beschreibung |
|---|---|---|---|
| `region` | String | `"Österreich"` | Immer AT/Österreich |
| `schadstoff` | String | `"THG"` | Treibhausgas (aggregiert) |
| `einheit` | String | `"t CO2-äq"` | Tonnen CO₂-Äquivalent |
| `sektor` | String | `"Energie & Industrie"` | KSG-Sektor (österr. Systematik) |
| `einteilung` | String | `"Gesamt"` | Unterkategorie des Sektors |
| `quelle` | String | `"NowCast (1990-2023)"` | Datenquelle / Erhebungsmethode |
| `datenstand` | String (ISO) | `"2024-07-31"` | Letztes Update der Quelle |
| `jahr` | Integer | `1990` | Berichtsjahr |
| `werte` | Double | `36407538.53` | Emissionsmenge |


---

### 2. `treibhausgas_emissionen_nach_crt` – Treibhausgase nach CRT (UNFCCC)

**Berichtsrahmen:** Common Reporting Tables (CRT) – internationale IPCC/UNFCCC-Systematik  
**Schadstoff:** `THG` (CO₂-Äquivalent)  
**Zeitreihe:** 1990–2022

**JSON-Felder:**

| Feld | Typ | Beispielwert | Beschreibung |
|---|---|---|---|
| `region` | String | `"AT"` | Länderkürzel |
| `schadstoff` | String | `"THG"` | Treibhausgas (aggregiert) |
| `einheit` | String | `"t CO2-äq"` | Tonnen CO₂-Äquivalent |
| `crf_code` | String | `"0"` | IPCC/CRF-Sektorcode |
| `crf_sektor` | String | `"Total (without LULUCF)"` | Sektorbezeichnung nach IPCC |
| `quelle` | String | `"OLI 2023 (1990-2022)"` | Datenquelle |
| `datenstand` | String (ISO) | `"2024-01-15"` | Letztes Update |
| `jahr` | Integer | `1990` | Berichtsjahr |
| `werte` | Double | `79082673.168` | Emissionsmenge |


---

### 3. `luftschadstoff_emissionen_nach_nfr` – Luftschadstoffe nach NFR

**Berichtsrahmen:** Nomenclature For Reporting (NFR) – EU NEC-Richtlinie / CLRTAP  
**Schadstoffe:** NOx, SO₂, NH₃, CO, NMVOC, PM2.5, PM10, TSP, Schwermetalle u.a.  
**Zeitreihe:** 1990–2022

**JSON-Felder:**

| Feld | Typ | Beispielwert | Beschreibung |
|---|---|---|---|
| `region` | String | `"AT"` | Länderkürzel |
| `schadstoff` | String | `"NOX"`, `"PM2.5"` | Schadstoffkürzel |
| `einheit` | String | `"kt"` | Kilotonnen |
| `nfr_code` | Integer | `0` | NFR-Sektorcode |
| `nfr_sektor` | String | `"Degreasing"` | Sektorbezeichnung nach NFR |
| `quelle` | String | `"OLI 2023 (1990-2022)"` | Datenquelle |
| `datenstand` | String (ISO) | `"2023-02-15"` | Letztes Update |
| `jahr` | Integer | `1990` | Berichtsjahr |
| `werte` | Double | `0.0` | Emissionsmenge (kann 0.0 sein) |

---
# Schadstoffe und Treibhausgase

| Kürzel | Name | Kategorie | Einheit | Beschreibung | Typische Quellen |
|---|---|---|---|---|---|
| THG | Treibhausgase gesamt | Treibhausgas | t CO2-äq | Gesamte klimawirksame Emissionen als CO₂-Äquivalent | Energie, Verkehr, Industrie, Landwirtschaft |
| CO2 | Kohlendioxid | Treibhausgas | t CO2-äq | Wichtigstes anthropogenes Treibhausgas | Fossile Brennstoffe, Verkehr |
| CH4 | Methan | Treibhausgas | t CO2-äq | Stark klimawirksames Gas | Landwirtschaft, Deponien |
| N2O | Lachgas | Treibhausgas | t CO2-äq | Treibhausgas aus Stickstoffprozessen | Landwirtschaft, Industrie |
| NOX | Stickoxide | Luftschadstoff | kt | Sammelbegriff für NO und NO₂ | Verkehr, Kraftwerke |
| SO2 | Schwefeldioxid | Luftschadstoff | kt | Gas aus schwefelhaltigen Brennstoffen | Kohle, Industrie |
| NH3 | Ammoniak | Luftschadstoff | kt | Gas aus landwirtschaftlichen Prozessen | Tierhaltung, Düngung |
| CO | Kohlenmonoxid | Luftschadstoff | kt | Giftiges Gas aus unvollständiger Verbrennung | Verkehr, Heizungen |
| NMVOC | Flüchtige organische Verbindungen | Luftschadstoff | kt | Organische Gase ohne Methan | Lösungsmittel, Lacke |
| PM10 | Feinstaub ≤ 10 µm | Feinstaub | kt | Gröbere Feinstaubpartikel | Verkehr, Industrie |
| PM2.5 | Feinstaub ≤ 2.5 µm | Feinstaub | kt | Sehr feine Partikel mit hoher Gesundheitsbelastung | Verbrennung, Verkehr |
| TSP | Total Suspended Particles | Feinstaub | kt | Gesamte Schwebstaubpartikel | Industrie, Verkehr |
| PAH | Polyzyklische aromatische Kohlenwasserstoffe | Organischer Schadstoff | t | Teilweise krebserregende Stoffgruppe | Holzfeuerung, Verkehr |
| PCB | Polychlorierte Biphenyle | Organischer Schadstoff | kg | Umweltgiftige Industriechemikalien | Alte Industrieanlagen |
| Cd | Cadmium | Schwermetall | t | Giftiges Schwermetall | Metallindustrie |
| Hg | Quecksilber | Schwermetall | t | Hochtoxisches Schwermetall | Kohlekraftwerke |
| Pb | Blei | Schwermetall | t | Giftiges Schwermetall | Industrie, Altlasten |

---

## Quellen

- [Umweltbundesamt Open Data](https://www.umweltbundesamt.at/umweltinformation/opendata)
- [data.gv.at – KSG Datensatz](https://www.data.gv.at/katalog/dataset/1b750209-8745-4794-93ce-1f7fc7587213)
- [data.gv.at – CRT Datensatz](https://www.data.gv.at/katalog/dataset/78bd7b69-c1a7-456b-8698-fac3b24f7aa5)