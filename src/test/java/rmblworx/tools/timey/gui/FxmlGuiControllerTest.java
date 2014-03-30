package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Basisklasse für FXML-basierte GUI-Controller-Tests.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
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
