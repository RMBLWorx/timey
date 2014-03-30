package rmblworx.tools.timey.gui;

import java.io.File;

import javafx.scene.media.AudioClip;

/**
 * Spielt einen Sound ab.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class AudioPlayer {

	/**
	 * Spielt den Sound in einem separaten Thread ab.
	 * @param path Pfad zur Datei
	 * @param exceptionHandler Behandlung von Exceptions
	 */
	public void playInThread(final String path, final Thread.UncaughtExceptionHandler exceptionHandler) {
		/*
		 * Sound in separatem Thread abspielen, da die Anwendung bei nicht-vorhandener Datei sonst einige Zeit (ca. 5 Sekunden) nicht
		 * mehr reagieren würde, bevor eine Exception auftritt.
		 */
		final Thread thread = new Thread(new Runnable() {
			public void run() {
				new AudioClip(new File(path).toURI().toString()).play();
			}
		});
		thread.setDaemon(true);
		thread.setUncaughtExceptionHandler(exceptionHandler);
		thread.start();
	}

}
