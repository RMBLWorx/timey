package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import javafx.scene.control.Label;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.categories.TestFX;

import rmblworx.tools.timey.ITimey;
import rmblworx.tools.timey.TimeyFacade;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Integrationstests für die Optionen-GUI.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@Category(TestFX.class)
public class OptionsIntegrationTest extends FxmlGuiTest {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final String getFxmlFilename() {
		return "Options.fxml";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final ITimey setUpFacade() {
		return new TimeyFacade();
	}

	/**
	 * Testet die Anzeige der Versionskennung.
	 */
	@Test
	public final void testShowVersion() {
		final Label appVersionLabel = (Label) stage.getScene().lookup("#appVersionLabel");
		final String version = appVersionLabel.getText();

		assertNotNull(version);
		assertNotEquals("", version);
		assertNotEquals("${project.version}", version);
	}

}
