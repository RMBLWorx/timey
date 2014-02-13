/**
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Testklasse zum Pruefen ob das setzen der Alarmzeit richtig gesetzt wird.
 *
 * @author mmatthies
 */
public class InitSetTimeCommandTest {
        /**
         * Empfaenger fuer das Alarm-Kommando.
         */
        private Alarm alarm;
        /**
         * Client fuer das Alarm-Kommando.
         */
        private AlarmClient client;

        /**
         * @throws java.lang.Exception
         *                 Exception
         */
        @Before
        public final void setUp() throws Exception {
                this.alarm = new Alarm();
                this.client = new AlarmClient(alarm);
        }

        /**
         * @throws java.lang.Exception
         *                 Exception.
         */
        @After
        public final void tearDown() throws Exception {
                this.alarm = null;
                this.client = null;
        }

        /**
         * Test method for
         * {@link rmblworx.tools.timey.AlarmClient
         * #initSetTimeCommand(rmblworx.tools.timey.vo.TimeDescriptor)}
         * .
         */
        @Test
        public final void testInitSetTimeCommand() {
                int expectedMilliseconds = 0;

                TimeDescriptor expectedDescriptor = new TimeDescriptor(
                                expectedMilliseconds);

                TimeDescriptor actualDescriptor = this.client
                                .initSetTimeCommand(expectedDescriptor);

                assertEquals(expectedMilliseconds,
                                actualDescriptor.getMilliSeconds());
        }
}
