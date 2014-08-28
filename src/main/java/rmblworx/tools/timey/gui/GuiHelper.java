package rmblworx.tools.timey.gui;

import java.awt.TrayIcon;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import rmblworx.tools.timey.ITimey;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Hilfsmethoden zum Umgang mit der GUI.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class GuiHelper {

	/**
	 * Spielt Sounds ab.
	 */
	private AudioPlayer audioPlayer = new AudioPlayer();

	/**
	 * Zeigt Hinweise an.
	 */
	private MessageHelper messageHelper = new MessageHelper();

	/**
	 * Erzeugt und startet Threads.
	 */
	private ThreadHelper threadHelper = new ThreadHelper();

	/**
	 * Fassade.
	 */
	private ITimey facade;

	/**
	 * Symbol im System-Tray.
	 */
	private TrayIcon trayIcon;

	public void setAudioPlayer(final AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	public final void setMessageHelper(final MessageHelper messageHelper) {
		this.messageHelper = messageHelper;
	}

	public final MessageHelper getMessageHelper() {
		return messageHelper;
	}

	public final void setThreadHelper(final ThreadHelper threadHelper) {
		this.threadHelper = threadHelper;
	}

	public final ThreadHelper getThreadHelper() {
		return threadHelper;
	}

	public final void setFacade(final ITimey facade) {
		this.facade = facade;
	}

	public final ITimey getFacade() {
		return facade;
	}

	public final void setTrayIcon(final TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}

	/**
	 * @param locale Sprache
	 * @return ResourceBundle für die jeweilige Sprache
	 */
	public final ResourceBundle getResourceBundle(final Locale locale) {
		return ResourceBundle.getBundle(getClass().getPackage().getName() + ".Timey_i18n", locale);
	}

	/**
	 * Zeigt einen Hinweis im System-Tray oder, falls dieser nicht verfügbar ist, in einem modalen Dialog an.
	 * @param caption Titel
	 * @param text Text
	 * @param i18n ResourceBundle
	 */
	public final void showTrayMessageWithFallbackToDialog(final String caption, final String text, final ResourceBundle i18n) {
		messageHelper.showTrayMessageWithFallbackToDialog(caption, text, trayIcon, i18n);
	}

	/**
	 * Zeigt einen Hinweis im System-Tray an.
	 * @param caption Titel
	 * @param text Text
	 */
	public final void showTrayMessage(final String caption, final String text) {
		messageHelper.showTrayMessage(caption, text, trayIcon);
	}

	/**
	 * Zeigt einen Hinweis in einem modalen Dialog an.
	 * @param title Fenstertitel
	 * @param text Text
	 * @param i18n ResourceBundle
	 */
	public final void showDialogMessage(final String title, final String text, final ResourceBundle i18n) {
		messageHelper.showDialogMessage(title, text, i18n);
	}

	/**
	 * Spielt den Sound in einem separaten Thread ab und zeigt einen evtl. auftretenden Fehler in einem Dialog an.
	 * Falls {@code path == null} oder leer, macht die Methode nichts.
	 * @param path Pfad zur Datei
	 * @param i18n ResourceBundle
	 */
	public final void playSoundInThread(final String path, final ResourceBundle i18n) {
		if (path == null || path.length() == 0) {
			return;
		}

		audioPlayer.playInThread(threadHelper, path, new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(final Thread thread, final Throwable exception) {
				messageHelper.showDialogMessage(i18n.getString("messageDialog.error.title"),
						String.format(i18n.getString("sound.play.error"), exception.getLocalizedMessage()), i18n);
			}
		});
	}

	/**
	 * Führt den Task in einem separaten Thread aus, um die Anwendung nicht zu blockieren.
	 * @param task Task
	 * @param i18n ResourceBundle
	 */
	public final void runInThread(final Task<Void> task, final ResourceBundle i18n) {
		threadHelper.run(task, new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(final Thread thread, final Throwable exception) {
				messageHelper.showDialogMessage(i18n.getString("messageDialog.error.title"), exception.getLocalizedMessage(), i18n);
			}
		});
	}

}
