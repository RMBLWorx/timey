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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TimeyController {

	private Stage stage;
	private TrayIcon trayIcon;

	private boolean minimizedFirstTime = true;

	@FXML
	private ResourceBundle resources;

	@FXML
	void initialize() {
		Platform.runLater(new Runnable() {
			public void run() {
				createTrayIcon(stage);
			}
		});
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
					if (Config.getInstance().isMinimizeToTray()) {
						hide(stage);
					} else {
						exit();
					}
				}
			});

			stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> property, Boolean oldValue, Boolean newValue) {
					if (Boolean.TRUE.equals(newValue)) {
						hide(stage);
					}
				}
			});

			final PopupMenu popup = new PopupMenu();

			final MenuItem showItem = new MenuItem(resources.getString("trayMenu.show.label"));
			showItem.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent event) {
					show(stage);
				}
			});
			popup.add(showItem);

			final MenuItem closeItem = new MenuItem(resources.getString("trayMenu.close.label"));
			closeItem.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent event) {
					exit();
				}
			});
			popup.add(closeItem);

			trayIcon = new TrayIcon(image, resources.getString("application.title"), popup);
			trayIcon.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent event) {
					show(stage);
				}
			});
			trayIcon.addMouseListener(new MouseListener() {
				public void mouseClicked(final MouseEvent event) {
					if (event.getButton() == MouseEvent.BUTTON1) {
						if (stage.isIconified()) {
							show(stage);
						} else {
							hide(stage);
						}
					}
				}

				public void mouseReleased(final MouseEvent event) {
				}

				public void mousePressed(final MouseEvent event) {
				}

				public void mouseExited(final MouseEvent event) {
				}

				public void mouseEntered(final MouseEvent event) {
				}
			});
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
				if (SystemTray.isSupported() && Config.getInstance().isMinimizeToTray()) {
					stage.hide();
					stage.setIconified(true);
					showProgramIsMinimizedMessage();
				}
			}
		});
	}

	private void show(final Stage stage) {
		Platform.runLater(new Runnable() {
			public void run() {
				stage.show();
				stage.toFront();
				stage.setIconified(false);
			}
		});
	}

	private void exit() {
		Platform.exit();
	}

}
