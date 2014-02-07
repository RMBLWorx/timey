package rmblworx.tools.timey.gui;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		try {
			String locale = "de";
			ResourceBundle i18n = ResourceBundle.getBundle("rmblworx.tools.timey.gui.TimeyGui_i18n", new Locale(locale)); 
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("TimeyGui.fxml"), i18n);
			Scene scene = new Scene(page); 
			stage.setScene(scene);
			stage.setTitle(i18n.getString("application.title"));
			stage.show(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
