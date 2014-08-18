package rmblworx.tools.timey.gui;

import java.awt.TrayIcon;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.SwingUtilities;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Hilfsmethoden zum Umgang mit Hinweisen.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class MessageHelper {

	/**
	 * Ob Hinweise unterdrückt werden sollen. Sinnvoll für Tests.
	 */
	private boolean suppressMessages = false;

	public final void setSuppressMessages(final boolean suppress) {
		suppressMessages = suppress;
	}

	/**
	 * Zeigt einen Hinweis im System-Tray oder, falls dieser nicht verfügbar ist, in einem modalen Dialog an.
	 * @param caption Titel
	 * @param text Text
	 * @param trayIcon TrayIcon
	 * @param i18n ResourceBundle
	 */
	public void showTrayMessageWithFallbackToDialog(final String caption, final String text, final TrayIcon trayIcon,
			final ResourceBundle i18n) {
		if (trayIcon != null) {
			showTrayMessage(caption, text, trayIcon);
		} else {
			showDialogMessage(caption, text, i18n);
		}
	}

	/**
	 * Zeigt einen Hinweis im System-Tray an.
	 * @param caption Titel
	 * @param text Text
	 * @param trayIcon TrayIcon
	 */
	public final void showTrayMessage(final String caption, final String text, final TrayIcon trayIcon) {
		if (trayIcon == null) {
			throw new RuntimeException("There's no system tray icon.");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
			}
		});
	}

	/**
	 * Zeigt einen Hinweis in einem modalen Dialog an.
	 * @param title Fenstertitel
	 * @param text Text
	 * @param i18n ResourceBundle
	 */
	public void showDialogMessage(final String title, final String text, final ResourceBundle i18n) {
		if (suppressMessages) {
			return;
		}

		Platform.runLater(new Runnable() {
			public void run() {
				try {
					final Stage stage = new Stage(StageStyle.UTILITY);
					final Parent root = FXMLLoader.load(getClass().getResource("MessageDialog.fxml"), i18n);

					final Label message = (Label) root.lookup("#message");
					message.setText(text);

					final Button okButton = (Button) root.lookup("#okButton");
					okButton.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(final ActionEvent event) {
							stage.close();
						}
					});

					stage.setScene(new Scene(root));
					stage.setTitle(title);
					stage.setResizable(false);
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.showAndWait();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
