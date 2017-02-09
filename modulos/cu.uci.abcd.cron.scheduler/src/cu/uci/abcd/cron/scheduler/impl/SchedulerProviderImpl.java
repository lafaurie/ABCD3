package cu.uci.abcd.cron.scheduler.impl;

import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import cu.uci.abcd.cron.scheduler.SchedulerProvider;

public class SchedulerProviderImpl implements InitializingBean, DisposableBean, SchedulerProvider {

	private Scheduler scheduler;

	@Override
	public void destroy() throws Exception {
		if (scheduler.isStarted())
			scheduler.shutdown();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		StdSchedulerFactory sf = new StdSchedulerFactory();
		sf.initialize(getProperties());
		scheduler = sf.getScheduler();
		if (!scheduler.isStarted()) {
			scheduler.start();
		}
	}

	private Properties getProperties() {
		Properties props = new Properties();
		props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, "ABCD");
		props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID, "AUTO");

		// Thread pooling ?
		props.put("org.quartz.threadPool.class", org.quartz.simpl.SimpleThreadPool.class.getName());
		props.put("org.quartz.threadPool.threadCount", "1");
		props.put("org.quartz.threadPool.threadPriority", "5");
		props.put("org.quartz.jobStore.misfireThreshold","60000");
		props.put("org.quartz.jobStore.class","org.quartz.simpl.RAMJobStore");

		return props;
	}

	@Override
	public Scheduler getScheduler() {
		return scheduler;
	}
}
