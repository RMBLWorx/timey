package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.categories.TestFX;

import rmblworx.tools.timey.ITimey;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * GUI-Tests für die Stoppuhr.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@Category(TestFX.class)
public class StopwatchControllerTest extends FxmlGuiControllerTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	// GUI-Elemente
	private Label stopwatchTimeLabel;
	private Button stopwatchStartButton;
	private Button stopwatchStopButton;
	private ToggleButton stopwatchSplitTimeButton;
	private Button stopwatchResetButton;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final String getFxmlFilename() {
		return "Stopwatch.fxml";
	}

	@Before
	public final void setUp() {
		scene = stage.getScene();

		stopwatchTimeLabel = (Label) scene.lookup("#stopwatchTimeLabel");
		stopwatchStartButton = (Button) scene.lookup("#stopwatchStartButton");
		stopwatchStopButton = (Button) scene.lookup("#stopwatchStopButton");
		stopwatchSplitTimeButton = (ToggleButton) scene.lookup("#stopwatchSplitTimeButton");
		stopwatchResetButton = (Button) scene.lookup("#stopwatchResetButton");
	}

	@Test
	public final void testInitializedFields() throws IllegalAccessException {
		super.testFxmlInitializedFields();
	}

	/**
	 * Testet den Zustand der Schaltflächen je nach Zustand der Stoppuhr.
	 */
	@Test
	public final void testStopwatchButtonStates() {
		final ITimey facade = getController().getGuiHelper().getFacade();

		// Zustand der Schaltflächen testen
		assertTrue(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());

		assertFalse(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());

		assertTrue(stopwatchSplitTimeButton.isVisible());
		assertTrue(stopwatchSplitTimeButton.isDisabled());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());

		// Stoppuhr starten
		click(stopwatchStartButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).startStopwatch();

		// Zustand der Schaltflächen testen
		assertFalse(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());

		assertTrue(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());
		assertTrue(stopwatchStopButton.isFocused());

		assertTrue(stopwatchSplitTimeButton.isVisible());
		assertFalse(stopwatchSplitTimeButton.isDisabled());
		assertFalse(stopwatchSplitTimeButton.isSelected());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());

		// Zwischenzeit aktivieren
		click(stopwatchSplitTimeButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).toggleTimeModeInStopwatch();

		// Zustand der Schaltflächen testen
		assertFalse(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());

		assertTrue(stopwatchStopButton.isVisible());
		assertTrue(stopwatchStopButton.isDisabled());

		assertTrue(stopwatchSplitTimeButton.isVisible());
		assertFalse(stopwatchSplitTimeButton.isDisabled());
		assertTrue(stopwatchSplitTimeButton.isSelected());
		assertTrue(stopwatchSplitTimeButton.isFocused());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());

		// Zwischenzeit deaktivieren
		click(stopwatchSplitTimeButton);
		verify(facade, timeout(WAIT_FOR_EVENT).times(2)).toggleTimeModeInStopwatch(); // zweiter Aufruf

		// Zustand der Schaltflächen testen
		assertFalse(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());

		assertTrue(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());

		assertTrue(stopwatchSplitTimeButton.isVisible());
		assertFalse(stopwatchSplitTimeButton.isDisabled());
		assertFalse(stopwatchSplitTimeButton.isSelected());
		assertTrue(stopwatchSplitTimeButton.isFocused());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());

		// Stoppuhr stoppen
		click(stopwatchStopButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).stopStopwatch();

		// Zustand der Schaltflächen testen
		assertTrue(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());
		assertTrue(stopwatchStartButton.isFocused());

		assertFalse(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());

		assertTrue(stopwatchSplitTimeButton.isVisible());
		assertTrue(stopwatchSplitTimeButton.isDisabled());
		assertFalse(stopwatchSplitTimeButton.isSelected());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());

		// Stoppuhr zurücksetzen
		click(stopwatchResetButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).resetStopwatch();

		// Zustand der Schaltflächen testen
		assertTrue(stopwatchStartButton.isVisible());
		assertFalse(stopwatchStartButton.isDisabled());
		assertTrue(stopwatchStartButton.isFocused());

		assertFalse(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());

		assertTrue(stopwatchSplitTimeButton.isVisible());
		assertTrue(stopwatchSplitTimeButton.isDisabled());
		assertFalse(stopwatchSplitTimeButton.isSelected());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());
	}

	/**
	 * Testet die Darstellung der Zeit mit und ohne Millisekunden-Anteil.
	 */
	@Test
	public final void testStopwatchTimeLabelMilliseconds() {
		final CheckBox stopwatchShowMillisecondsCheckbox = (CheckBox) scene.lookup("#stopwatchShowMillisecondsCheckbox");

		// Ausgangszustand
		assertTrue(stopwatchShowMillisecondsCheckbox.isSelected());
		assertEquals("00:00:00.000", stopwatchTimeLabel.getText());

		// Millisekunden-Anteil ausblenden
		click(stopwatchShowMillisecondsCheckbox);
		assertEquals("00:00:00", stopwatchTimeLabel.getText());

		// Millisekunden-Anteil wieder einblenden
		click(stopwatchShowMillisecondsCheckbox);
		assertEquals("00:00:00.000", stopwatchTimeLabel.getText());
	}

	/**
	 * Testet die Anzeige der gemessenen Zeit.
	 */
	@Test
	public final void testStopwatchStartStopTimeMeasured() {
		final ITimey facade = getController().getGuiHelper().getFacade();

		// Stoppuhr starten
		when(facade.startStopwatch()).thenReturn(new TimeDescriptor(50));
		click(stopwatchStartButton);

		// Stoppuhr stoppen
		click(stopwatchStopButton);

		// gemessene Zeit muss angezeigt sein
		waitForThreads();
		assertEquals("00:00:00.050", stopwatchTimeLabel.getText());

		// Stoppuhr wieder starten, um zweite (additive) Messung zu berücksichtigen
		when(facade.startStopwatch()).thenReturn(new TimeDescriptor(200));
		click(stopwatchStartButton);

		// Stoppuhr wieder stoppen
		click(stopwatchStopButton);

		// gemessene Zeit muss angezeigt sein
		waitForThreads();
		assertEquals("00:00:00.200", stopwatchTimeLabel.getText());
	}

	/**
	 * Testet die Funktionalität der Zurücksetzen-Schaltfläche.
	 */
	@Test
	public final void testStopwatchReset() {
		final ITimey facade = getController().getGuiHelper().getFacade();

		// Stoppuhr zurücksetzen, ohne sie vorher gestartet zu haben
		click(stopwatchResetButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).resetStopwatch();

		// angezeigte Zeit muss zurückgesetzt sein
		assertEquals("00:00:00.000", stopwatchTimeLabel.getText());

		// Stoppuhr starten
		when(facade.startStopwatch()).thenReturn(new TimeDescriptor(9876));
		click(stopwatchStartButton);

		// gemessene Zeit muss angezeigt sein
		assertEquals("00:00:09.876", stopwatchTimeLabel.getText());

		// Stoppuhr stoppen
		click(stopwatchStopButton);

		// Stoppuhr zurücksetzen
		click(stopwatchResetButton);

		// angezeigte Zeit muss zurückgesetzt sein
		waitForThreads();
		assertEquals("00:00:00.000", stopwatchTimeLabel.getText());
	}

	/**
	 * Testet die Funktionalität der Zurücksetzen-Schaltfläche während die Stoppuhr läuft.
	 */
	@Test
	public final void testStopwatchResetWhileRunning() {
		final ITimey facade = getController().getGuiHelper().getFacade();

		// Stoppuhr starten
		when(facade.startStopwatch()).thenReturn(new TimeDescriptor(50));
		click(stopwatchStartButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).startStopwatch();

		// Stoppuhr zurücksetzen
		click(stopwatchResetButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).resetStopwatch();

		// gemessene Zeit muss angezeigt sein
		assertEquals("00:00:00.050", stopwatchTimeLabel.getText());

		// Stoppuhr stoppen
		click(stopwatchStopButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).stopStopwatch();
	}

	/**
	 * Testet die Funktionalität der Zurücksetzen-Schaltfläche während Zwischenzeit aktiv ist.
	 */
	@Test
	public final void testStopwatchResetWhileRunningWithSplitTime() {
		final ITimey facade = getController().getGuiHelper().getFacade();

		// Stoppuhr starten
		click(stopwatchStartButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).startStopwatch();

		// Zwischenzeit aktivieren
		click(stopwatchSplitTimeButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).toggleTimeModeInStopwatch();

		// Stoppuhr zurücksetzen
		click(stopwatchResetButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).resetStopwatch();

		// Zustand der Schaltflächen testen
		assertTrue(stopwatchStopButton.isVisible());
		assertFalse(stopwatchStopButton.isDisabled());

		assertTrue(stopwatchSplitTimeButton.isVisible());
		assertFalse(stopwatchSplitTimeButton.isDisabled());
		assertFalse(stopwatchSplitTimeButton.isSelected());

		assertTrue(stopwatchResetButton.isVisible());
		assertFalse(stopwatchResetButton.isDisabled());

		// Stoppuhr stoppen
		click(stopwatchStopButton);
		verify(facade, timeout(WAIT_FOR_EVENT)).stopStopwatch();
	}

}
