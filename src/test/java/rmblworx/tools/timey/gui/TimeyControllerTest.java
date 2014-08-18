package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.awt.SystemTray;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.categories.TestFX;

import rmblworx.tools.timey.event.TimeyEvent;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * GUI-Tests für das Hauptfenster.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@Category(TestFX.class)
public class TimeyControllerTest extends FxmlGuiControllerTest {

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "Timey.fxml";
	}

	/**
	 * {@inheritDoc}
	 */
	protected final void controllerLoaded() {
		((TimeyController) getController()).setStage(stage);
	}

	/**
	 * Testet das Hinzufügen des Tray-Symbols.
	 */
	@Test
	public final void testTrayIconAdded() {
		if (!SystemTray.isSupported()) {
			// Test überspringen
			return;
		}

		assertEquals(1, SystemTray.getSystemTray().getTrayIcons().length);
	}

	/**
	 * Testet die Verarbeitung eines Ereignisses.
	 */
	@Test
	public final void testHandleEvent() {
		final TimeyController controller = (TimeyController) getController();

		// Ereignis auslösen
		controller.handleEvent(mock(TimeyEvent.class));
		waitForThreads();

		// TODO sicherstellen, dass Ereignis verarbeitet wird
	}

}
