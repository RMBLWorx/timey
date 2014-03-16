package rmblworx.tools.timey.gui;

import rmblworx.tools.timey.ITimey;
import rmblworx.tools.timey.TimeyFacade;

/**
 * Verwaltung der Fassade.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public final class FacadeManager {

	/**
	 * Instanz der Fassade.
	 */
	private static ITimey instance;

	/**
	 * @return Instanz der Fassade
	 */
	public static synchronized ITimey getFacade() {
		if (instance == null) {
			instance = new TimeyFacade();
		}

		return instance;
	}

	/**
	 * Privater Konstruktor.
	 */
	private FacadeManager() {
	}

}
