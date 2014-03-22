package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.utils.FXTestUtils;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * GUI-Tests für die Stoppuhr.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
@Category(TimeyGuiTest.class)
public class StopwatchControllerTest extends FxmlGuiTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "Stopwatch.fxml";
	}

	@Before
	public final void setUp() {
		scene = stage.getScene();
	}

	/**
	 * Testet den Zustand der Schaltflächen je nach Zustand der Stoppuhr.
	 */
	@Test
	public final void testStopwatchStartStopResetButtonStates() {
		final Button stopwatchStartButton = (Button) scene.lookup("#stopwatchStartButton");
		final Button stopwatchStopButton = (Button) scene.lookup("#stopwatchStopButton");
		final Button stopwatchResetButton = (Button) scene.lookup("#stopwatchResetButton");

		// Zustand der Schaltflächen testen
		assertTrue(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());
//		assertTrue(stopwatchStartButton.isFocused()); // muss nicht unbedingt der Fall sein

		assertFalse(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());

		// Stoppuhr starten
		stopwatchStartButton.fire();
		FXTestUtils.awaitEvents();

		// Zustand der Schaltflächen testen
		assertFalse(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());

		assertTrue(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());
		assertTrue(stopwatchStopButton.isFocused());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());

		// Stoppuhr stoppen
		stopwatchStopButton.fire();
		FXTestUtils.awaitEvents();

		// Zustand der Schaltflächen testen
		assertTrue(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());
		assertTrue(stopwatchStartButton.isFocused());

		assertFalse(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());

		// Stoppuhr zurücksetzen
		stopwatchResetButton.fire();
		FXTestUtils.awaitEvents();

		// Zustand der Schaltflächen testen
		assertTrue(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());
		assertTrue(stopwatchStartButton.isFocused());

		assertFalse(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());
	}

	/**
	 * Testet die Darstellung der Zeit mit und ohne Millisekunden-Anteil.
	 */
	@Test
	public final void testStopwatchTimeLabelMilliseconds() {
		final CheckBox stopwatchShowMillisecondsCheckbox = (CheckBox) scene.lookup("#stopwatchShowMillisecondsCheckbox");
		final Label stopwatchTimeLabel = (Label) scene.lookup("#stopwatchTimeLabel");

		// Ausgangszustand
		assertTrue(stopwatchShowMillisecondsCheckbox.isSelected());
		assertEquals("00:00:00.000", stopwatchTimeLabel.getText());

		// Millisekunden-Anteil ausblenden
		stopwatchShowMillisecondsCheckbox.fire();
		FXTestUtils.awaitEvents();
		assertEquals("00:00:00", stopwatchTimeLabel.getText());

		// Millisekunden-Anteil wieder ausblenden
		stopwatchShowMillisecondsCheckbox.fire();
		FXTestUtils.awaitEvents();
		assertEquals("00:00:00.000", stopwatchTimeLabel.getText());
	}

	/**
	 * Testet die Anzeige der gemessenen Zeit.
	 */
	@Test
	public final void testStopwatchStartStopTimeMeasured() {
		final Button stopwatchStartButton = (Button) scene.lookup("#stopwatchStartButton");
		final Button stopwatchStopButton = (Button) scene.lookup("#stopwatchStopButton");
		final Label stopwatchTimeLabel = (Label) scene.lookup("#stopwatchTimeLabel");

		// Stoppuhr starten
		when(getController().getGuiHelper().getFacade().startStopwatch()).thenReturn(new TimeDescriptor(50));
		stopwatchStartButton.fire();
		FXTestUtils.awaitEvents();

		// Stoppuhr stoppen
		stopwatchStopButton.fire();
		FXTestUtils.awaitEvents();

		// gemessene Zeit muss angezeigt sein
		assertEquals("00:00:00.050", stopwatchTimeLabel.getText());

		// Stoppuhr wieder starten, um zweite (additive) Messung zu berücksichtigen
		when(getController().getGuiHelper().getFacade().startStopwatch()).thenReturn(new TimeDescriptor(200));
		stopwatchStartButton.fire();
		FXTestUtils.awaitEvents();

		// Stoppuhr wieder stoppen
		stopwatchStopButton.fire();
		FXTestUtils.awaitEvents();

		// gemessene Zeit muss angezeigt sein
		assertEquals("00:00:00.200", stopwatchTimeLabel.getText());
	}

	/**
	 * Testet die Funktionalität der Zurücksetzen-Schaltfläche.
	 */
	@Test
	public final void testStopwatchReset() {
		final Button stopwatchStartButton = (Button) scene.lookup("#stopwatchStartButton");
		final Button stopwatchStopButton = (Button) scene.lookup("#stopwatchStopButton");
		final Button stopwatchResetButton = (Button) scene.lookup("#stopwatchResetButton");
		final Label stopwatchTimeLabel = (Label) scene.lookup("#stopwatchTimeLabel");

		// Stoppuhr zurücksetzen, ohne sie vorher gestartet zu haben
		stopwatchResetButton.fire();
		FXTestUtils.awaitEvents();

		// angezeigte Zeit muss zurückgesetzt sein
		assertEquals("00:00:00.000", stopwatchTimeLabel.getText());

		// Stoppuhr starten
		when(getController().getGuiHelper().getFacade().startStopwatch()).thenReturn(new TimeDescriptor(9876));
		stopwatchStartButton.fire();
		FXTestUtils.awaitEvents();

		// gemessene Zeit muss angezeigt sein
		assertEquals("00:00:09.876", stopwatchTimeLabel.getText());

		// Stoppuhr stoppen
		stopwatchStopButton.fire();
		FXTestUtils.awaitEvents();

		// Stoppuhr zurücksetzen
		stopwatchResetButton.fire();
		FXTestUtils.awaitEvents();

		// angezeigte Zeit muss zurückgesetzt sein
		assertEquals("00:00:00.000", stopwatchTimeLabel.getText());
	}

	/**
	 * Testet die Funktionalität der Zurücksetzen-Schaltfläche während die Stoppuhr läuft.
	 */
	@Test
	public final void testStopwatchResetWhileRunning() {
		final Button stopwatchStartButton = (Button) scene.lookup("#stopwatchStartButton");
		final Button stopwatchStopButton = (Button) scene.lookup("#stopwatchStopButton");
		final Button stopwatchResetButton = (Button) scene.lookup("#stopwatchResetButton");
		final Label stopwatchTimeLabel = (Label) scene.lookup("#stopwatchTimeLabel");

		// Stoppuhr starten
		when(getController().getGuiHelper().getFacade().startStopwatch()).thenReturn(new TimeDescriptor(50));
		stopwatchStartButton.fire();
		FXTestUtils.awaitEvents();

		// Stoppuhr zurücksetzen
		stopwatchResetButton.fire();
		FXTestUtils.awaitEvents();

		// gemessene Zeit muss angezeigt sein
		assertEquals("00:00:00.050", stopwatchTimeLabel.getText());

		// Stoppuhr stoppen
		stopwatchStopButton.fire();
		FXTestUtils.awaitEvents();
	}

}
