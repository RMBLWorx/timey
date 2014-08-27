package rmblworx.tools.timey.gui;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.loadui.testfx.GuiTest;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Hilfsklasse zur Analyse von Problemen während der Ausführung von Tests.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public final class AnalyzeTestHelper {

	/**
	 * Erzeugt einen Screenshot und gibt dessen Inhalt Base64-kodiert auf der Konsole aus.
	 * Äußerst nützlich, um nur auf Travis fehlschlagende Tests zu analysieren.
	 * @throws IOException
	 */
	public static void printBase64EncodedScreenshotContent() throws IOException {
		final File screenshot = GuiTest.captureScreenshot();
		System.out.println(String.format("Base64-encoded content of %s:", screenshot.getAbsolutePath()));
		System.out.println(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(screenshot)));
		System.out.println();
	}

	/**
	 * Base64-dekodiert {@code content} und schreibt den Inhalt in die Datei.
	 * Äußerst nützlich, um den über die Travis-Konsole ausgegebenen Screenshot-Inhalt wiederherzustellen.
	 * @param path Pfad zur Datei
	 * @param content Base64-kodierter Dateiinhalt
	 * @throws IOException
	 */
	public static void writeBase64EncodedScreenshotContentToFile(final String path, final String content) throws IOException {
		FileUtils.writeByteArrayToFile(new File(path), Base64.getDecoder().decode(content));
	}

	public static void main(final String[] args) throws IOException {
//		writeBase64EncodedScreenshotContentToFile("r:/screenshot.png", "");
	}

	/**
	 * Instanziierung verhindern.
	 */
	private AnalyzeTestHelper() {
	}

}
