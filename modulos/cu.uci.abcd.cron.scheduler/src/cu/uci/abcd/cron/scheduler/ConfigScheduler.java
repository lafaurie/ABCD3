package cu.uci.abcd.cron.scheduler;

import java.util.Map;

import org.quartz.Scheduler;

public abstract class ConfigScheduler {
	
	protected Scheduler scheduler;
	protected String cronExpression;

	public abstract void createJob();

	public void bind(SchedulerProvider provider, Map<?, ?> properties) {
		this.scheduler = provider.getScheduler();
		createJob();
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
