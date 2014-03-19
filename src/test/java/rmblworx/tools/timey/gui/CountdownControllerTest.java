package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicate;

import rmblworx.tools.timey.gui.component.TimePicker;

/**
 * GUI-Tests für den Countdown.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class CountdownControllerTest extends FxmlGuiTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "CountdownGui.fxml";
	}

	@Before
	public final void setUp() {
		scene = stage.getScene();
	}

	/**
	 * Testet den Zustand der Schaltflächen je nach Zustand des Countdowns.
	 */
	@Test
	public final void testStartStopButtonStates() {
		final Button countdownStartButton = (Button) scene.lookup("#countdownStartButton");
		final Button countdownStopButton = (Button) scene.lookup("#countdownStopButton");
		final TimePicker countdownTimePicker = (TimePicker) scene.lookup("#countdownTimePicker");

		// Zustand der Schaltflächen testen
		assertTrue(countdownStartButton.isVisible());
		assertTrue(countdownStartButton.isDisabled());
//		assertTrue(countdownStartButton.isFocused()); // muss nicht unbedingt der Fall sein

		assertFalse(countdownStopButton.isVisible());
		assertFalse(countdownStopButton.isDisabled());

		// Zeit setzen
		countdownTimePicker.setTime(DateTimeUtil.getCalendarForString("00:00:10"));

		// Zustand der Schaltflächen testen
		assertTrue(countdownStartButton.isVisible());
		assertFalse(countdownStartButton.isDisabled());

		// Countdown starten
		countdownStartButton.fire();

		// Zustand der Schaltflächen testen
		assertFalse(countdownStartButton.isVisible());
		assertFalse(countdownStartButton.isDisabled());

		assertTrue(countdownStopButton.isVisible());
		assertFalse(countdownStopButton.isDisabled());
		assertTrue(countdownStopButton.isFocused());

		// Countdown stoppen
		countdownStopButton.fire();

		// Zustand der Schaltflächen testen
		assertTrue(countdownStartButton.isVisible());
		assertFalse(countdownStartButton.isDisabled());
		assertTrue(countdownStartButton.isFocused());

		assertFalse(countdownStopButton.isVisible());
		assertFalse(countdownStopButton.isDisabled());

		// Zeit wieder auf 0 setzen
		countdownTimePicker.setTime(DateTimeUtil.getCalendarForString("00:00:00"));

		// Zustand der Schaltflächen testen
		assertTrue(countdownStartButton.isVisible());
		assertTrue(countdownStartButton.isDisabled());
	}

	/**
	 * Testet die Übertragung der Zeit zwischen TimePicker und Label.
	 */
	@Test
	public final void testTimeConversionBetweenPickerAndLabel() {
		final Button countdownStartButton = (Button) scene.lookup("#countdownStartButton");
		final Button countdownStopButton = (Button) scene.lookup("#countdownStopButton");
		final TimePicker countdownTimePicker = (TimePicker) scene.lookup("#countdownTimePicker");
		final Label countdownTimeLabel = (Label) scene.lookup("#countdownTimeLabel");

		final Calendar countdownTime = DateTimeUtil.getCalendarForString("00:00:10");
		final long timeLeftMax = 10000;
		final long timeLeftMin = 8000;

		// Zeit setzen
		countdownTimePicker.setTime(countdownTime);

		// Countdown starten
		countdownStartButton.fire();
		try {
			waitUntil(countdownTimeLabel, new Predicate<Label>() {
				public boolean apply(final Label countdownTimeLabel) {
					return countdownTimeLabel.isVisible();
				}
			}, 1);
		} catch (final RuntimeException e) {
			fail("label is not visible");
		}

		try {
			final long timeLeft = getTimeFormatter().parse(countdownTimeLabel.getText()).getTime();
			if (timeLeft < timeLeftMin || timeLeft > timeLeftMax) {
				fail(String.format("%d is not between %d and %d.", timeLeft, timeLeftMin, timeLeftMax));
			}
		} catch (final ParseException e) {
			fail(e.getLocalizedMessage());
		}

		assertFalse(countdownTimePicker.isVisible());
		assertTrue(countdownTimeLabel.isVisible());

		// Countdown stoppen
		countdownStopButton.fire();
		try {
			waitUntil(countdownTimeLabel, new Predicate<Label>() {
				public boolean apply(final Label countdownTimeLabel) {
					return !countdownTimeLabel.isVisible();
				}
			}, 1);
		} catch (final RuntimeException e) {
			fail("label is still visible");
		}

		// verbleibende Zeit muss im Toleranzbereich liegen
		final long timeLeft = countdownTimePicker.getTime().getTimeInMillis();
		if (timeLeft < timeLeftMin || timeLeft > timeLeftMax) {
			fail(String.format("%d is not between %d and %d.", timeLeft, timeLeftMin, timeLeftMax));
		}

		assertTrue(countdownTimePicker.isVisible());
		assertFalse(countdownTimeLabel.isVisible());
	}

	/**
	 * @return Formatierer für Zeit-Werte
	 */
	private SimpleDateFormat getTimeFormatter() {
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

		return dateFormatter;
	}

}
