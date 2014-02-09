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
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TimeyController {

	private static final boolean MINIMIZE_TO_TRAY = false;

	private Stage stage;
	private TrayIcon trayIcon;

	private boolean minimizedFirstTime = true;

	@FXML
	private ResourceBundle resources;

	@FXML
	void initialize() {
		if (MINIMIZE_TO_TRAY) {
			Platform.runLater(new Runnable() {
				public void run() {
					createTrayIcon(stage);
				}
			});
		}
	}

	public void setStage(final Stage stage) {
		this.stage = stage;
	}

	private void createTrayIcon(final Stage stage) {
		if (SystemTray.isSupported()) {
			Platform.setImplicitExit(false);
			final SystemTray tray = SystemTray.getSystemTray();
			final Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("clock.png"));

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

			final MenuItem showItem = new MenuItem(resources.getString("trayMenu.show.label"));
			showItem.addActionListener(showListener);
			popup.add(showItem);

			final MenuItem closeItem = new MenuItem(resources.getString("trayMenu.close.label"));
			closeItem.addActionListener(closeListener);
			popup.add(closeItem);

			trayIcon = new TrayIcon(image, resources.getString("application.title"), popup);
			trayIcon.addActionListener(showListener);
			try {
				tray.add(trayIcon);
			} catch (final AWTException e) {
				e.printStackTrace();
			}
		}
	}

	private void showProgramIsMinimizedMessage() {
		if (minimizedFirstTime) {
			trayIcon.displayMessage(resources.getString("trayMenu.appMinimized.caption"), resources.getString("trayMenu.appMinimized.text"), TrayIcon.MessageType.INFO);
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

}
