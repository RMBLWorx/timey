/**
 * 
 */
package rmblworx.tools.timey;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Diese Thread-sichere Implementierung dient der Zeitmessung in Millisekunden.
 * 
 * @author mmatthies
 */
final class TimerRunnable implements Runnable {
        /**
         * Wertobjekt das die Zeit fuer die GUI kapselt und liefert.
         */
        private TimeDescriptor timeDescriptor;
        /**
         * Differenz zwischen Startzeit und aktueller Zeit.
         */
        private long timeDelta;
        /**
         * Von dieser Timerimplementierung verwendete Lock-Mechanismus.
         */
        private Lock lock = new ReentrantLock();
        /**
         * Beschreibt wann der Startvorgang ausgeloest wurde in Millisekunden.
         */
        private final long timeStarted;
        /**
         * Beschreibt die vergangene Zeit in Millisekunden.
         */
        private final long timePassed;
        /**
         * Logger.
         */
        private final Logger log = LogManager.getLogger(TimerRunnable.class);

        /**
         * @param descriptor
         *                Referenz auf das Wertobjekt das die Zeit in
         *                Millisekunden an die konsumierende Implementierung
         *                liefern soll.
         * @param passedTime
         *                Vergangene Zeit in Millisekunden.
         */
        public TimerRunnable(final TimeDescriptor descriptor,
                        final long passedTime) {
                this.log.entry();

                this.timeDescriptor = descriptor;
                this.timePassed = passedTime;
                this.timeStarted = System.currentTimeMillis();

                this.log.exit();
        }

        @Override
        public void run() {
                this.log.entry();

                this.lock.lock();
                computeTime();
                this.lock.unlock();

                this.log.exit();
        }

        /**
         * Berechnet die Stopzeit und schreibt den Wert in Millisekunden in das
         * Wertobjekt.
         */
        private void computeTime() {
                this.log.entry();

                // this.lock.lock();
                this.timeDelta = 0;
                long currentTimeMillis = System.currentTimeMillis();
                // synchronized (this) {

                // synchronized (this.timeDescriptor) {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat();
                dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                dateFormatter.applyPattern("HH:mm:ss.SSS");
                timeDelta = currentTimeMillis - timeStarted;
                this.log.debug("current (UTC): "
                                + dateFormatter.format(currentTimeMillis));

                this.timeDescriptor.setMilliSeconds(timePassed + timeDelta);
                // this.lock.unlock();
                // }
                // }
                this.log.exit();
        }
}
