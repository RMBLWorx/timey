package rmblworx.tools.timey.gui;

import java.util.Locale;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import org.loadui.testfx.GuiTest;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Basisklasse für JavaFX-basierte GUI-Tests mit {@link https://github.com/TestFX/TestFX}.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public abstract class JavaFxGuiTest extends GuiTest {

	/**
	 * Ob der Elternknoten von einem Container umhüllt werden soll.
	 */
	protected static final boolean WRAP_IN_CONTAINER = true;

	/**
	 * Sprache für GUI-Tests.
	 */
	public static final Locale TEST_LOCALE = Locale.GERMAN;

	static {
		// Standardsprache für alle GUI-Tests setzen (wichtig z. B. als Fallback auf Travis)
		Locale.setDefault(TEST_LOCALE);
	}

	/**
	 * Legt einen Container um den Knoten, um die in ihm enthaltenen Steuerelemente während der Ausführung von Tests nicht am
	 * Bildschirmrand zu platzieren.
	 * Dient als Workaround für <a href="https://github.com/TestFX/TestFX/issues/136">TestFX/TestFX #136 auf GitHub</a>.
	 * @param root zu umhüllender Elternknoten
	 * @return Container als neuer Elternknoten
	 */
	protected final Parent wrapInContainer(final Parent root) {
		final BorderPane container = new BorderPane();
		container.setCenter(root);
		container.setPadding(new Insets(150, 0, 0, 200));

		return container;
	}

}
