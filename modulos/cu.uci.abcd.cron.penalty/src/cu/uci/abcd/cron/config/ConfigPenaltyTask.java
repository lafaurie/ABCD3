package cu.uci.abcd.cron.config;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.DisposableBean;

import cu.uci.abcd.cron.scheduler.ConfigScheduler;
import cu.uci.abcd.cron.task.PenaltyTask;

public class ConfigPenaltyTask extends ConfigScheduler implements DisposableBean {


	@Override
	public void destroy() throws Exception {
		scheduler.deleteJob(new JobKey("penaltyTask", "abcd"));
	}

	
	public void createJob() {
		JobDetail jobPenalty = newJob(PenaltyTask.class).withIdentity("penaltyTask", "abcd").build();
		Trigger triggerPenalty = newTrigger().withIdentity("penaltyTaskTrigger", "abcd").startNow().withSchedule(cronSchedule(cronExpression)).build();
		try {
			scheduler.scheduleJob(jobPenalty, triggerPenalty);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
