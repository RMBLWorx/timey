package rmblworx.tools.timey.gui;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import rmblworx.tools.timey.TimeyFacade;
import rmblworx.tools.timey.gui.config.ConfigManager;
import rmblworx.tools.timey.gui.config.FileConfigStorage;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class TimeyApplication extends Application {

	/**
	 * Dateiname der Konfigurationsdatei (kann auch Pfad enthalten).
	 */
	private static final String CONFIG_FILENAME = "timey.config.xml";

	/**
	 * Startet die Anwendung.
	 * @param stage Fenster der Anwendung
	 * @throws IOException Fehler beim Laden der FXML-Datei.
	 */
	public final void start(final Stage stage) throws IOException {
		ConfigManager.setCurrentConfig(new FileConfigStorage().loadFromFile(CONFIG_FILENAME));

		final GuiHelper guiHelper = new GuiHelper();
		guiHelper.setFacade(new TimeyFacade());
		final ResourceBundle resources = guiHelper.getResourceBundle(ConfigManager.getCurrentConfig().getLocale());
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("Timey.fxml"), resources);
		final Parent root = (Parent) loader.load();
		stage.setScene(new Scene(root));
		stage.setTitle(resources.getString("application.title"));
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("img/clock.png")));
		stage.show();

		final TimeyController timeyController = loader.getController();
		timeyController.setGuiHelper(guiHelper);
		timeyController.setStage(stage);
	}

	/**
	 * Beendet die Anwendung.
	 */
	public final void stop() {
		/*
		 * Konfigurationsdatei nur speichern, wenn Werte geändert wurden.
		 * Nicht anhand der Abweichung zur Standardkonfiguration entscheiden, ob gespeichert werden soll, weil durch Änderungen auch
		 * zufälligerweise wieder die Werte der Standardkonfiguration gesetzt werden könnten und diese dann nicht für den nächsten Start
		 * übernommen werden würden.
		 */
		if (ConfigManager.getCurrentConfig().isChanged()) {
			new FileConfigStorage().saveToFile(ConfigManager.getCurrentConfig(), CONFIG_FILENAME);
		}

		System.exit(0);
	}

}
