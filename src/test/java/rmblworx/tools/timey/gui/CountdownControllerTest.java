package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.utils.FXTestUtils;

import rmblworx.tools.timey.gui.component.TimePicker;

/**
 * GUI-Tests für den Countdown.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
@Category(TimeyGuiTest.class)
public class CountdownControllerTest extends FxmlGuiTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "Countdown.fxml";
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
		FXTestUtils.awaitEvents();

		// Zustand der Schaltflächen testen
		assertFalse(countdownStartButton.isVisible());
		assertFalse(countdownStartButton.isDisabled());

		assertTrue(countdownStopButton.isVisible());
		assertFalse(countdownStopButton.isDisabled());
		assertTrue(countdownStopButton.isFocused());

		// Countdown stoppen
		countdownStopButton.fire();
		FXTestUtils.awaitEvents();

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

		// Zeit setzen
		countdownTimePicker.setTime(DateTimeUtil.getCalendarForString("00:00:10"));

		// Countdown starten
		countdownStartButton.fire();
		FXTestUtils.awaitEvents();

		// verbleibende Zeit muss angezeigt sein
		assertEquals("00:00:10", countdownTimeLabel.getText());

		assertFalse(countdownTimePicker.isVisible());
		assertTrue(countdownTimeLabel.isVisible());

		// Countdown stoppen
		countdownStopButton.fire();
		FXTestUtils.awaitEvents();

		// verbleibende Zeit muss stimmen
		assertEquals(10000, countdownTimePicker.getTime().getTimeInMillis());

		assertTrue(countdownTimePicker.isVisible());
		assertFalse(countdownTimeLabel.isVisible());
	}

}
