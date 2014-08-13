package rmblworx.tools.timey.gui;

import java.awt.Toolkit;

import javafx.application.Application;

/**
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public final class Main {

	/**
	 * Einstiegspunkt der Anwendung.
	 * @param args Parameter
	 */
	public static void main(final String[] args) {
		// Laden von AWT noch vor JavaFX erzwingen, um TrayIcon auch unter Mac OS X zu ermöglichen
		System.setProperty("javafx.macosx.embedded", "true");
		Toolkit.getDefaultToolkit();

		// JavaFX-GUI starten
		Application.launch(TimeyApplication.class, args);
	}

	/**
	 * Privater Konstruktor.
	 */
	private Main() {
	}

}
