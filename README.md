timey
=====

#Zielsetzung
Es soll eine Anwendung entstehen die folgende Funktionalitäten mittels einem GUI
für alle Plattformen (Android, iOS, etc.) bietet:
* Stopuhr-Funktion
* Countdown-Funktion
* Alarm-Funktion

##Anforderungen
* Stoppuhr-Funktion
Der Nutzer kann mittels Betätigung eines Startknopfes die Uhr starten.
Bei erneutem Betätigen des gleichen Knopfes wird die Zeit angehalten.
Wird der Knopf erneutet betätigt, wird die zeit fortschreitend gemessen.
Über einen seperaten Knopf wird die Uhr zurückgesetzt.
Eine Zwischenzeitmessung wird dadurch realisiert, dass während der laufenden
Zeitmessung der Reset-Knopf bei Betätigung die Zeitanzeige anzeigt, jedoch die
Zeitmessung im Hintergrund fortfährt. Erst beim erneuten drücken des
Rest-Knopfes wird die aktuelle Zeitmessung eingeblendet. Wird stattdessen der
Start-Knopf im Zwischenzeitanzeigemodus betätigt, wird die Zeitmessung
unterbrochen - es bleibt aber die aktuelle Zwischenzeit eingeblendet. Wird in
diesem Zusammenhang der Rest-Knopf im Anschluss betätigt so wird die Zeitmessung
gestoppt und die Endzeit eingeblendet. Ein erneutes betätigen des Reset-Knopfes
setzt die Stoppuhr zurück.

###verwendete Technologien
