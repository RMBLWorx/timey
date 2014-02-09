package rmblworx.tools.timey.gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private static final boolean MINIMIZE_TO_TRAY = false;

	private ResourceBundle i18n;
	private TrayIcon trayIcon;

	private boolean minimizedFirstTime = true;

	public void start(final Stage stage) {
		try {
			String locale = "de";
			i18n = ResourceBundle.getBundle(getClass().getPackage().getName() + ".TimeyGui_i18n", new Locale(locale));

			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("TimeyGui.fxml"), i18n);
			Scene scene = new Scene(page);
			stage.setScene(scene);
			stage.setTitle(i18n.getString("application.title"));
			stage.show();

			if (MINIMIZE_TO_TRAY) {
				createTrayIcon(stage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createTrayIcon(final Stage stage) throws AWTException {
		if (SystemTray.isSupported()) {
			Platform.setImplicitExit(false);
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("clock.png"));

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(final WindowEvent event) {
					hide(stage);
				}
			});

			final ActionListener closeListener = new ActionListener() {
				public void actionPerformed(final ActionEvent event) {
					System.exit(0);
				}
			};

			final ActionListener showListener = new ActionListener() {
				public void actionPerformed(final ActionEvent event) {
					Platform.runLater(new Runnable() {
						public void run() {
							stage.show();
							stage.toFront();
						}
					});
				}
			};

			final PopupMenu popup = new PopupMenu();

			final MenuItem showItem = new MenuItem(i18n.getString("trayMenu.show.label"));
			showItem.addActionListener(showListener);
			popup.add(showItem);

			final MenuItem closeItem = new MenuItem(i18n.getString("trayMenu.close.label"));
			closeItem.addActionListener(closeListener);
			popup.add(closeItem);

			trayIcon = new TrayIcon(image, i18n.getString("application.title"), popup);
			trayIcon.addActionListener(showListener);
			tray.add(trayIcon);
		}
	}

	private void showProgramIsMinimizedMessage() {
		if (minimizedFirstTime) {
			trayIcon.displayMessage("Timey wurde minimiert", "Per Doppelklick auf das Symbol wird es wieder angezeigt.\nPer Rechtsklick erscheint das Menü.", TrayIcon.MessageType.INFO);
			minimizedFirstTime = false;
		}
	}

	private void hide(final Stage stage) {
		Platform.runLater(new Runnable() {
			public void run() {
				if (SystemTray.isSupported()) {
					stage.hide();
					showProgramIsMinimizedMessage();
				} else {
					System.exit(0);
				}
			}
		});
	}

	public static void main(final String[] args) {
		launch(args);
	}

}
