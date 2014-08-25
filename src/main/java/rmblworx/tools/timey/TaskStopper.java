package rmblworx.tools.timey;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
class TaskStopper implements Runnable {

	/**
	 * Referenz auf die zu stoppende Implementierung.
	 */
	private final ScheduledFuture<?> future;
	/**
	 * Referenz auf den Scheduleservice die den zu stoppenden Task verwaltet.
	 */
	private final ScheduledExecutorService scheduler;

	/**
	 * Erweiterter Konstruktor.
	 * 
	 * @param scheduler
	 *            Referenz auf den ExecutorService welcher den zu stoppenden Task verwaltet.
	 * @param taskToStopFuture
	 *            Referenz auf den zu stoppenden Task.
	 */
	public TaskStopper(final ScheduledExecutorService scheduler, final ScheduledFuture<?> taskToStopFuture) {
		this.future = taskToStopFuture;
		this.scheduler = scheduler;
	}

	@Override
	public void run() {
		this.future.cancel(false);
		this.scheduler.shutdown();
	}
}
