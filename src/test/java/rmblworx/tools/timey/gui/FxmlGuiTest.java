package rmblworx.tools.timey.gui;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.loadui.testfx.GuiTest;

import rmblworx.tools.timey.ITimey;

/**
 * Basisklasse für FXML-basierte GUI-Tests mit {@link https://github.com/SmartBear/TestFX}.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public abstract class FxmlGuiTest extends GuiTest {

	/**
	 * Sprache für GUI-Tests.
	 */
	public static final Locale TEST_LOCALE = Locale.GERMAN;

	static {
		// Standardsprache für alle GUI-Tests setzen (wichtig z. B. als Fallback auf Travis)
		Locale.setDefault(TEST_LOCALE);
	}

	/**
	 * Mit der GUI verbundener Controller.
	 */
	private Controller controller;

	/**
	 * @return Name der FXML-Datei zum Laden der GUI
	 */
	protected abstract String getFxmlFilename();

	/**
	 * Lädt die GUI aus der FXML-Datei.
	 * @return Elternknoten der GUI-Elemente
	 */
	protected final Parent getRootNode() {
		final GuiHelper guiHelper = new GuiHelper();
		guiHelper.setFacade(mock(ITimey.class)); // Fassade für Tests mocken
		final ResourceBundle i18n = guiHelper.getResourceBundle(TEST_LOCALE);
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource(getFxmlFilename()), i18n);
			final Parent root = (Parent) loader.load();
			controller = loader.getController();
			controller.setGuiHelper(guiHelper);
			controllerLoaded();
			return root;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Wird aufgerufen, sobald der Controller geladen wurde.
	 */
	protected void controllerLoaded() {
	}

	/**
	 * @return mit der GUI verbundener Controller
	 */
	protected final Controller getController() {
		return controller;
	}

}
