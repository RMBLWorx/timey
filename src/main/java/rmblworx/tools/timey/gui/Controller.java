package rmblworx.tools.timey.gui;

import java.util.List;
import java.util.Vector;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Basisklasse für GUI-Controller.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public abstract class Controller {

	/**
	 * In der für diesen Controller verantwortlichen GUI eingebettete Controller.
	 */
	private final List<Controller> embeddedControllers = new Vector<>();

	private GuiHelper guiHelper;

	public final void addEmbeddedController(final Controller embeddedController) {
		embeddedControllers.add(embeddedController);
	}

	public final List<Controller> getEmbeddedControllers() {
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
