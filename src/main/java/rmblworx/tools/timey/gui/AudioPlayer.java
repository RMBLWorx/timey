package rmblworx.tools.timey.gui;

import java.io.File;

import javafx.scene.media.AudioClip;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Spielt einen Sound ab.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class AudioPlayer {

	/**
	 * Spielt den Sound in einem separaten Thread ab.
	 * @param threadHelper ThreadHelper
	 * @param path Pfad zur Datei
	 * @param exceptionHandler Behandlung von Exceptions
	 */
	public void playInThread(final ThreadHelper threadHelper, final String path, final Thread.UncaughtExceptionHandler exceptionHandler) {
		/*
		 * Sound in separatem Thread abspielen, da die Anwendung bei nicht-vorhandener Datei sonst einige Zeit (ca. 5 Sekunden) nicht mehr
		 * reagieren würde, bevor eine Exception auftritt.
		 */
		threadHelper.run(new Runnable() {
			public void run() {
				new AudioClip(new File(path).toURI().toString()).play();
			}
		}, exceptionHandler);
	}

}
