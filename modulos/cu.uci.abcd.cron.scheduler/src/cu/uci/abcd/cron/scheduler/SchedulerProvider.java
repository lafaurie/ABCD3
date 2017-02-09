package cu.uci.abcd.cron.scheduler;

import org.quartz.Scheduler;

public interface SchedulerProvider {
	
	Scheduler getScheduler();
}
