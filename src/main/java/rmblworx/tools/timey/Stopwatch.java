/**
 */
package rmblworx.tools.timey;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "Receiver" implementation.
 * <ul>
 * <li>knows how to perform the operations associated with carrying out a
 * request. Any class may serve as a Receiver.</li>
 * </ul>
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class Stopwatch implements IStopwatch {
        /**
         * Logger.
         */
        private final Logger log = LogManager.getLogger(Stopwatch.class);
        /**
         * Die genutzte Zeitmessimplementierung.
         */
        private SimpleTimer timer;
        /**
         * Wertobjekt das die Zeit fuer die GUI kapselt und liefert.
         */
        private TimeDescriptor timeDescriptor = new TimeDescriptor(0);

        /**
         * Konstruktor welcher eine Instanz dieses Receiver erzeugt.
         */
        public Stopwatch() {
        }

        @Override
        public TimeDescriptor startStopwatch() {
                this.log.entry();

                TimeDescriptor result;

                this.log.debug("Action: startStopwatch");
                if (this.timer == null) {
                        this.timer = new SimpleTimer(this.timeDescriptor);
                }

                result = this.timer.startStopwatch(1, 1, TimeUnit.MILLISECONDS);

                return this.log.exit(result);
        }

        @Override
        public Boolean stopStopwatch() {
                this.log.entry();

                this.log.debug("Action: stopStopwatch");
                this.timer.stopStopwatch();

                return this.log.exit(Boolean.TRUE);
        }

        @Override
        public Boolean resetStopwatch() {
                this.log.entry();

                this.log.debug("Action: resetStopwatch");
                this.timer = null;

                return this.log.exit(Boolean.TRUE);
        }

}
