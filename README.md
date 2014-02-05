timey
=====

#Zielsetzung
Es soll eine Anwendung entstehen die folgende Funktionalitäten mittels einem GUI
für alle Plattformen (Android, iOS, etc.) bietet:
* Stoppuhr-Funktion
* Countdown-Funktion
* Alarm-Funktion

##Anforderungen
* Stoppuhr-Funktion
Der Nutzer kann mittels Betätigung eines Startknopfes die Uhr starten.
Bei erneutem Betätigen des gleichen Knopfes wird die Zeit angehalten.
Wird der Knopf erneutet betätigt, wird die zeit fortschreitend gemessen.
Über einen seperaten Knopf wird die Uhr zurückgesetzt.
Eine Zwischenzeitmessung wird dadurch realisiert, dass während der laufenden
Zeitmessung der Reset-Knopf bei Betätigung die aktuelle Zeit nimmt und anzeigt,
jedoch wird die Zeitmessung im Hintergrund fortgeführt. Erst beim erneuten drücken des
Reset-Knopfes wird die aktuelle Zeitmessung wieder direkt eingeblendet. Wird stattdessen der
Start-Knopf im Zwischenzeitanzeigemodus betätigt, wird die Zeitmessung
unterbrochen - es bleibt aber die aktuelle Zwischenzeit eingeblendet. Wird in
diesem Zusammenhang der Reset-Knopf im Anschluss betätigt so wird die Zeitmessung
gestoppt und die Endzeit eingeblendet. Ein erneutes betätigen des Reset-Knopfes
setzt die Stoppuhr zurück.

* Countdown-Funktion
Hier hat der Nutzer die Möglichkeit eine Zeit einzustellen
(Stunden:Minuten:Sekunden). Nach Betätigung des Start-Knopfes beginnt die Uhr
die Zeit bis auf Null herunterzuzählen und gibt dann ein Signal aus. Optional
kann ein visueller Effekt konfiguriert werden der in diesem Fall ausgelöst wird.
Ein eigener Alarmsound soll festgelegt werden können.

Bevor der Nutzer den Countdown aktiv startet bietet im ein zusätzlicher
Reset-Knopf die Möglichkeit bei Fehleingabe erneut eine Zeit einzustellen.

* Alarm-Funktion
Der Nutzer kann einen Zeitpunkt definieren, also Tag,Monat,Jahr,Stunde,Minuten,Sekunden , an welchem ein
Alarmsignal ertönen soll wenn dieser Zeitpunkt erreicht wird. Mit einem Ok-Knopf
kann der Nutzer den Alarm abschalten.
Mehrere Alarme können parallel definiert und zur Unterscheidung benannt werden.
Ein eigener Alarmsound soll festgelegt werden können.

###verwendete Technologien
