package rmblworx.tools.timey.gui;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.categories.TestFX;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * GUI-Tests für die Optionen.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@Category(TestFX.class)
public class OptionsControllerTest extends FxmlGuiControllerTest {

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "Options.fxml";
	}

	@Test
	public final void testInitializedFields() throws IllegalAccessException {
		super.testFxmlInitializedFields();
	}

}
