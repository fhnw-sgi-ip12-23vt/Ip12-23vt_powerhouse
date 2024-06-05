
# Powerhouse

## Readme
***
In diesem Readme werden auf alle wichtigen Elemente verwiesen.

## Überblick über das Projekt Powerhouse
***
Alle wichtigen Elemente der Software-Architektur, sowie alle Informationen zu verwendeter Hardware finden Sie in der Dokumentation. Diese ist folgendermassen aufgeteilt:

* [Hardware-Dokumentation](https://github.com/fhnw-sgi-ip12-23vt/Ip12-23vt_powerhouse/blob/f4988460a4c3d4b095565e1390c6c0ab0d1195cb/docu/hardware/Hardware.adoc)

* [Bedienungsanleitung Spiel](https://github.com/fhnw-sgi-ip12-23vt/Ip12-23vt_powerhouse/blob/d943952f26c9792b2c2e0c8939f13d876f54f816/docu/hardware/Bedienungsanleitung%20Spiel.adoc)

* [Softwaredokumentation (SAD)](https://github.com/fhnw-sgi-ip12-23vt/Ip12-23vt_powerhouse/blob/c4615cf55405ba9b3537b55cfcc192cdb2d660c0/docu/software(sad)/arc42-template.adoc)

* [Bedieungsanleitung Software](https://github.com/fhnw-sgi-ip12-23vt/Ip12-23vt_powerhouse/blob/d943952f26c9792b2c2e0c8939f13d876f54f816/docu/software(sad)/Bedienungsanleitung%20Software.adoc)

(überall Link einfügen)

In der **Hardwaredokumentation** sind alle Produkte aufgelistet, die für den Bau des Produktes nötig sind.

1. [Teileliste](https://github.com/fhnw-sgi-ip12-23vt/Ip12-23vt_powerhouse/blob/f4988460a4c3d4b095565e1390c6c0ab0d1195cb/docu/hardware/Hardware.adoc) mit allen Teilen und den Abmessungen
2. [3D-Druck-Dateien](https://github.com/fhnw-sgi-ip12-23vt/Ip12-23vt_powerhouse/blob/d943952f26c9792b2c2e0c8939f13d876f54f816/docu/hardware/3D-Objekte%20Dateien) mit einer Auflistung der Abmessungen 
3. [Kostenübersicht](https://github.com/fhnw-sgi-ip12-23vt/Ip12-23vt_powerhouse/blob/d943952f26c9792b2c2e0c8939f13d876f54f816/docu/hardware/Kosten%C3%BCbersicht.adoc)

#### Datenblätter
Die Datenblätter sind [hier](https://github.com/fhnw-sgi-ip12-23vt/Ip12-23vt_powerhouse/blob/d943952f26c9792b2c2e0c8939f13d876f54f816/docu/hardware/Datenbl%C3%A4tter) zu finden.

## Programmierkonventionen (Coding-Conventions)
***
Folgende Konventionen werden im Projekt Powerhouse eingehalten:
Namenskonventionen: Packagenamen sind immer klein,

* Klassen sind CamelCase, Variablen sind drinkingCamelCase, Konstanten und Enums sind in UPPER_CASE.
* 1 Tabulator/4 Leerschläge Einrückung pro Block.
* Keine Mehrfachdeklarationen pro Zeile.
* Öffnende Klammer auf der vorherigen Zeile, schliessende auf eine neue Zeile.
* Zeilen sind max. 120 Zeichen lang
* Verkettungen sind umgebrochen auf neue Zeilen mit 8 Leerzeichen Einrückung
* Klassengrösse: nicht grösser als 300 Zeilen

 Im Projekt Powerhouse wird eine [Checkstyle](https://checkstyle.sourceforge.io/) Konfiguration verwendet.


