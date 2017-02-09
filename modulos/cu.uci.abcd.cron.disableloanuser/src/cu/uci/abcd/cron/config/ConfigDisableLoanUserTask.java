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
import cu.uci.abcd.cron.task.DisableLoanUserTask;

public class ConfigDisableLoanUserTask extends ConfigScheduler implements DisposableBean {
	

	@Override
	public void destroy() throws Exception {
		scheduler.deleteJob(new JobKey("disableLoanUserTask", "abcd"));
	}

	
	public void createJob()  {
		JobDetail job = newJob(DisableLoanUserTask.class).withIdentity("disableLoanUserTask", "abcd").build();
		Trigger trigger = newTrigger().withIdentity("disableLoanUserTaskTrigger", "abcd").startNow().withSchedule(cronSchedule(cronExpression)).build();
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
}
