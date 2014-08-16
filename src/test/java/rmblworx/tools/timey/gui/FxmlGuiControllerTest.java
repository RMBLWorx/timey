package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Basisklasse für FXML-basierte GUI-Controller-Tests.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public abstract class FxmlGuiControllerTest extends FxmlGuiTest {

	/**
	 * Stellt sicher, dass dem Controller eine {@link GuiHelper}-Instanz bekannt ist.
	 */
	@Test
	public final void testGuiHelperIsSet() {
		assertNotNull(getController().getGuiHelper());
	}

}
