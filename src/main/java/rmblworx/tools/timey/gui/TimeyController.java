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
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rmblworx.tools.timey.event.TimeyEvent;
import rmblworx.tools.timey.event.TimeyEventListener;
import rmblworx.tools.timey.gui.config.ConfigManager;

/**
 * Controller für die Timey-GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class TimeyController extends Controller implements TimeyEventListener {

	/**
	 * Intervall für blinkendes Tray-Symbol in ms.
	 */
	private static final int TRAY_ICON_BLINK_INTERVAL = 500;

	/**
	 * Fenster der Anwendung.
	 */
	private Stage stage;

	/**
	 * Ob die Anwendung erstmalig minimiert wurde.
	 */
	private boolean minimizedFirstTime = true;

	/**
	 * Ob das Tray-Symbol blinken soll.
	 */
	private boolean trayIconBlinking = false;

	@FXML
	private ResourceBundle resources;

	@FXML
	private TabPane timeyTabs;

	@FXML
	private StopwatchController stopwatchController;

	@FXML
	private CountdownController countdownController;

	@FXML
	private AlarmController alarmController;

	@FXML
	private OptionsController optionsController;

	@FXML
	private void initialize() {
		assert timeyTabs != null : "fx:id='timeyTabs' was not injected";
		assert stopwatchController != null : "controller for fx:id='stopwatch' was not injected";
		assert countdownController != null : "controller for fx:id='countdown' was not injected";
		assert alarmController != null : "controller for fx:id='alarm' was not injected";
		assert optionsController != null : "controller for fx:id='options' was not injected";

		addEmbeddedController(stopwatchController);
		addEmbeddedController(countdownController);
		addEmbeddedController(alarmController);
		addEmbeddedController(optionsController);

		// zuletzt geöffneten Tab aktivieren
		timeyTabs.getSelectionModel().select(ConfigManager.getCurrentConfig().getActiveTab());

		timeyTabs.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(final ObservableValue<? extends Number> property, final Number oldValue, final Number newValue) {
				ConfigManager.getCurrentConfig().setActiveTab(newValue.intValue());
			}
		});

		Platform.runLater(new Runnable() {
			public void run() {
				createTrayIcon();
			}
		});

		final TimeyEventListener eventListener = this;
		Platform.runLater(new Runnable() {
			public void run() {
				getGuiHelper().getFacade().addEventListener(eventListener);
			}
		});
	}

	public void setStage(final Stage stage) {
		this.stage = stage;
	}

	/**
	 * Erzeugt das Symbol im System-Tray.
	 */
	private void createTrayIcon() {
		if (SystemTray.isSupported()) {
			Platform.setImplicitExit(false);
			final SystemTray tray = SystemTray.getSystemTray();
			final Image clock = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/clock.png"));
			final Image clockWarning = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/clock_error.png"));

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(final WindowEvent event) {
					if (ConfigManager.getCurrentConfig().isMinimizeToTray()) {
						hide();
					} else {
						exit();
					}
				}
			});

			stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(final ObservableValue<? extends Boolean> property, final Boolean oldValue, final Boolean newValue) {
					if (Boolean.TRUE.equals(newValue)) {
						hide();
					}
				}
			});

			final PopupMenu popup = new PopupMenu();

			final MenuItem showItem = new MenuItem(resources.getString("trayMenu.show.label"));
			showItem.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent event) {
					show();
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

			final TrayIcon trayIcon = new TrayIcon(clock, resources.getString("application.title"), popup);

			new Timer().schedule(new TimerTask() {
				public void run() {
					// aufhören zu blinken, nachdem Fenster wieder sichtbar gemacht wurde
					if (isAppVisible()) {
						trayIconBlinking = false;
					}

					trayIcon.setImage(trayIconBlinking && trayIcon.getImage() == clock ? clockWarning : clock);
				}
			}, 0, TRAY_ICON_BLINK_INTERVAL);

			trayIcon.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent event) {
					show();
				}
			});
			trayIcon.addMouseListener(new MouseListener() {
				public void mouseClicked(final MouseEvent event) {
					if (event.getButton() == MouseEvent.BUTTON1) {
						if (stage.isIconified()) {
							show();
						} else {
							hide();
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
				getGuiHelper().setTrayIcon(trayIcon);
			} catch (final AWTException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Blendet beim erstmaligen Minimieren der Anwendung einen entsprechenden Hinweis ein.
	 */
	private void showProgramIsMinimizedMessage() {
		if (minimizedFirstTime) {
			getGuiHelper().showTrayMessage(resources.getString("trayMenu.appMinimized.title"),
					resources.getString("trayMenu.appMinimized.text"));
			minimizedFirstTime = false;
		}
	}

	/**
	 * Blendet die Anwendung aus.
	 */
	private void hide() {
		Platform.runLater(new Runnable() {
			public void run() {
				if (SystemTray.isSupported() && ConfigManager.getCurrentConfig().isMinimizeToTray()) {
					stage.hide();
					stage.setIconified(true);
					showProgramIsMinimizedMessage();
				}
			}
		});
	}

	/**
	 * Blendet die Anwendung ein.
	 */
	private void show() {
		Platform.runLater(new Runnable() {
			public void run() {
				stage.show();
				stage.toFront();
				stage.setIconified(false);
				trayIconBlinking = false;
			}
		});
	}

	/**
	 * @return ob das Fenster der Anwendung direkt sichtbar ist
	 */
	private boolean isAppVisible() {
		return !stage.isIconified() && stage.isFocused();
	}

	/**
	 * Beendet die Anwendung.
	 */
	private void exit() {
		Platform.exit();
	}

	/**
	 * {@inheritDoc}
	 */
	public final void handleEvent(final TimeyEvent event) {
		// blinken, falls Fenster bei Eintreten des Ereignisses nicht sichtbar ist
		trayIconBlinking = !isAppVisible();
	}

}
