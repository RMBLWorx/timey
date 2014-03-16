package rmblworx.tools.timey.gui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.junit.BeforeClass;
import org.loadui.testfx.GuiTest;

/**
 * Basisklasse für FXML-basierte GUI-Tests mit {@link https://github.com/SmartBear/TestFX}.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public abstract class FxmlGuiTest extends GuiTest {

	/**
	 * Mit der GUI verbundener Controller.
	 */
	private Object controller;

	/**
	 * Zeitaufwendiges Laden der Fassade einmalig vor allen Tests, um Timing-Probleme beim Start der einzelnen Tests zu vermeiden.
	 */
	@BeforeClass
	public static final void loadFacade() {
		FacadeManager.getFacade();
	}

	/**
	 * @return Name der FXML-Datei zum Laden der GUI
	 */
	protected abstract String getFxmlFilename();

	/**
	 * Lädt die GUI aus der FXML-Datei.
	 * @return Elternknoten der GUI-Elemente
	 */
	protected final Parent getRootNode() {
		final ResourceBundle i18n = new GuiHelper().getResourceBundle(Locale.GERMAN);
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource(getFxmlFilename()), i18n);
			final Parent root = (Parent) loader.load();
			controller = loader.getController();
			return root;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return mit der GUI verbundener Controller
	 */
	protected Object getController() {
		return controller;
	}

}
