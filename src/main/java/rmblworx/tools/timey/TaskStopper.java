package rmblworx.tools.timey;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

class TaskStopper implements Runnable {

	private final ScheduledFuture<?> future;
	private final ScheduledExecutorService scheduler;

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
