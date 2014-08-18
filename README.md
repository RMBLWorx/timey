# timey

## Zielsetzung

Es soll eine Anwendung entstehen, die folgende Funktionalitäten
mittels einem GUI für alle Plattformen (Android, iOS, etc.) bietet:
* Stoppuhr-Funktion
* Countdown-Funktion
* Alarm-Funktion

## Anforderungen

* Stoppuhr-Funktion: Der Nutzer kann mittels Betätigung eines
Knopfes (im Folgenden START betitelt) die Uhr starten. Bei erneutem Betätigen von START
wird die Zeitnahme unterbrochen. Wird START erneut betätigt, wird die Zeit
fortschreitend gemessen. Über einen separaten Knopf wird die Uhr zurückgesetzt (im
Folgenden RESET betitelt).
Ein RESET während der fortschreitenden, ununterbrochenen Zeitmessung ist zu ermöglichen.
In diesem Falle hat die Stoppuhr ihre Zeitmessung bei 0 Sekunden selbstständig ihre Arbeit
fortzusetzen. Dies gilt nicht für die Zwischenzeitmessung.
Eine Zwischenzeitmessung wird durch einen extra dafür vorgesehenen Knopf (TIME) realisiert.
Wird TIME während laufender Zeitmessung einmal betätigt, so ist die Zeitmessung im
Hintergrund fortschreitend durchzuführen und dem Nutzer der bei TIME-Betätigung von der
Uhr zu zwischenspeichernde Zeitwert anzuzeigen bis eine andere Nutzeraktion eintritt. Während
dieser Zeit befindet sich die Uhr im Zwischenzeit-Modus, im Folgenden TIME-MODE genannt.
Wird im TIME-MODE START betätigt, so ist im die im Hintergrund laufende Zeitmessung zu
unterbrechen. Durch START kann der Nutzer die Zeitmessung im Hintergrund fortsetzen lassen.
Die Stoppuhr befindet sich noch im TIME-MODE.
Wird jedoch anstelle START vom Nutzer ein RESET ausgelöst, so verlässt die Stoppuhr
den TIME-MODE, wird komplett zurückgesetzt und startet nicht selbstständig die Zeitnahme
sondern wartet auf eine Nutzerinteraktion.
Befindet sich die Uhr im TIME-MODE und wird nach fortschreitender Zeitmessung mittels START
die Zeitmessung unterbrochen so kann der Nutzer mittels TIME sich die letzte, im
Hintergrund gemessene, Zeit anzeigen lassen. Die vorher angezeigte Zwischenzeit geht unwiderruflich
verloren. Mittels START kann der Nutzer die Zeitnahme im Vordergrund fortsetzen lassen oder
mit RESET die Uhr komplett zurücksetzen lassen wobei die Uhr den TIME-MODE verlässt.

* Countdown-Funktion: Hier hat der Nutzer die Möglichkeit, eine Zeit einzustellen
(Stunden:Minuten:Sekunden). Nach Betätigung des Start-Knopfes beginnt die Uhr
die Zeit bis auf Null herunterzuzählen und gibt dann ein Signal aus. Optional
kann ein visueller Effekt konfiguriert werden, der in diesem Fall ausgelöst wird.
Ein eigener Alarmsound soll festgelegt werden können.

* Alarm-Funktion: Der Nutzer kann einen Zeitpunkt definieren
(Tag, Monat, Jahr, Stunde, Minuten, Sekunden), an welchem ein Alarmsignal ertönen soll,
wenn dieser Zeitpunkt erreicht wird. Mit einem Ok-Knopf kann der Nutzer den
Alarm abschalten. Mehrere Alarme können parallel definiert und zur
Unterscheidung benannt werden. Ein eigener Alarmsound soll festgelegt werden
können.

## verwendete Technologien

* Java 7
* JavaFX 2.2
* Maven 3

## verwendete Frameworks/Bibliotheken

* Spring (Spring-AOP, Spring-Context, Spring-Core, Spring-TX, Spring-Test)
* Hibernate
* Apache Commons Lang
* SLF4J
* Log4j
* CGlib
* AspectJWeaver
* JUnit
* TestFX
* Mockito
