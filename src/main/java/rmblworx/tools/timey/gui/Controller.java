package rmblworx.tools.timey.gui;

import java.util.Vector;

/**
 * Basisklasse für GUI-Controller.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public abstract class Controller {

	/**
	 * In der für diesen Controller verantwortlichen GUI eingebettete Controller.
	 */
	private Vector<Controller> embeddedControllers = new Vector<>();

	private GuiHelper guiHelper;

	public final void addEmbeddedController(final Controller embeddedController) {
		embeddedControllers.add(embeddedController);
	}

	public final Vector<Controller> getEmbeddedControllers() {
		return embeddedControllers;
	}

	public final void setGuiHelper(final GuiHelper guiHelper) {
		this.guiHelper = guiHelper;
		passGuiHelperToEmbeddedControllers();
	}

	public final GuiHelper getGuiHelper() {
		return guiHelper;
	}

	/**
	 * Macht die GuiHelper-Instanz auch allen eingebetteten Controllern bekannt.
	 */
	private synchronized void passGuiHelperToEmbeddedControllers() {
		for (final Controller embeddedController : embeddedControllers) {
			embeddedController.setGuiHelper(guiHelper);
		}
	}

}
