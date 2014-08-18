package rmblworx.tools.timey.gui;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

}
