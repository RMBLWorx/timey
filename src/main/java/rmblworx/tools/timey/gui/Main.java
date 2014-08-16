package rmblworx.tools.timey.gui;

import java.awt.Toolkit;

import javafx.application.Application;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
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
