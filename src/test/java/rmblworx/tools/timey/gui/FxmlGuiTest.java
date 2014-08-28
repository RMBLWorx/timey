package rmblworx.tools.timey.gui;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.loadui.testfx.utils.FXTestUtils;

import rmblworx.tools.timey.ITimey;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Basisklasse für FXML-basierte GUI-Tests.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public abstract class FxmlGuiTest extends JavaFxGuiTest {

	/**
	 * Max. Zeit (in ms), die auf das Eintreten eines Ereignisses gewartet werden soll.
	 */
	protected static final int WAIT_FOR_EVENT = 5000;

	/**
	 * Mit der GUI verbundener Controller.
	 */
	private Controller controller;

	/**
	 * @return Name der FXML-Datei zum Laden der GUI
	 */
	protected abstract String getFxmlFilename();

	/**
	 * Lädt die GUI aus der FXML-Datei.
	 * @return Elternknoten der GUI-Elemente
	 */
	protected final Parent getRootNode() {
		final GuiHelper guiHelper = new GuiHelper();
		guiHelper.setFacade(mock(ITimey.class)); // Fassade für Tests mocken
		guiHelper.getThreadHelper().setTrackThreads(true); // Threads für Tests erfassen
		final ResourceBundle i18n = guiHelper.getResourceBundle(TEST_LOCALE);
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource(getFxmlFilename()), i18n);
			final Parent root = (Parent) loader.load();
			controller = loader.getController();
			controller.setGuiHelper(guiHelper);
			controllerLoaded();
			return WRAP_IN_CONTAINER ? wrapInContainer(root) : root;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Wird aufgerufen sobald der Controller geladen wurde.
	 */
	protected void controllerLoaded() {
	}

	/**
	 * @return mit der GUI verbundener Controller
	 */
	protected final Controller getController() {
		return controller;
	}

	/**
	 * {@inheritDoc}
	 */
	protected final Object getComponentWithFxmlFields() {
		return controller;
	}

	/**
	 * Wartet auf Beendigung aller erfassten Threads. Im Fall einer Exception schlägt der Test fehl.
	 */
	protected final void waitForThreads() {
		try {
			controller.getGuiHelper().getThreadHelper().waitForThreads();
		} catch (final InterruptedException e) {
			fail(e.getLocalizedMessage());
		}

		// zusätzlich auf Abarbeitung aller JavaFX-Ereignisse warten, die evtl. durch die Threads ausgelöst wurden
		FXTestUtils.awaitEvents();
	}

}
