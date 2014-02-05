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

###verwendete Technologien
