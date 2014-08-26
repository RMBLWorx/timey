package rmblworx.tools.timey.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Hilfsmethoden zum Umgang mit Threads.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class ThreadHelper {

	/**
	 * Ob Threads erfasst werden sollen. Sinnvoll für Tests.
	 */
	private boolean trackThreads = false;

	/**
	 * Derzeit laufende Threads. Wichtig, um in Tests darauf warten zu können, dass sie beendet sind, bevor Annahmen überprüft werden.
	 */
	private final List<Thread> runningThreads = new ArrayList<>();

	/**
	 * Führt den Task in einem separaten Thread aus, um die Anwendung nicht zu blockieren.
	 * @param task Task
	 * @param exceptionHandler Behandlung von Exceptions
	 */
	public void run(final Runnable task, final Thread.UncaughtExceptionHandler exceptionHandler) {
		final Thread thread = new Thread(task);

		if (trackThreads) {
			runningThreads.add(thread);
		}

		thread.setDaemon(true);
		thread.setUncaughtExceptionHandler(exceptionHandler);
		thread.start();
	}

	public final void setTrackThreads(final boolean enabled) {
		trackThreads = enabled;
	}

	public final void waitForThreads() throws InterruptedException {
		for (int i = runningThreads.size() - 1; i >= 0; --i) {
			runningThreads.get(i).join();
			runningThreads.remove(i);
		}
	}

}
