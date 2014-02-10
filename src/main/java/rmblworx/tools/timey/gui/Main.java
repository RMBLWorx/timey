package rmblworx.tools.timey.gui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static final String CONFIG_FILENAME = "Timey.config.xml";

	private ResourceBundle i18n;

	public void start(final Stage stage) {
		try {
			new ConfigStorage().loadConfig(CONFIG_FILENAME);

			String locale = "de";
			i18n = ResourceBundle.getBundle(getClass().getPackage().getName() + ".TimeyGui_i18n", new Locale(locale));

			final FXMLLoader loader = new FXMLLoader(getClass().getResource("TimeyGui.fxml"), i18n);
			final Parent root = (Parent) loader.load();
			final Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(i18n.getString("application.title"));
			stage.setResizable(false);
			stage.show();

			final TimeyController timeyController = (TimeyController) loader.getController();
			timeyController.setStage(stage);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() throws Exception {
		new ConfigStorage().saveConfig(CONFIG_FILENAME);
		System.exit(0);
	}

	public static void main(final String[] args) {
		launch(args);
	}

}
