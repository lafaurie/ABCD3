package cu.uci.abcd.cron.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cu.uci.abcd.circulation.IPenaltyService;

public class PenaltyTask implements Job {
	
	private Logger logger = LoggerFactory.getLogger(PenaltyTask.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		IPenaltyService penalty = TaskUtils.getService(IPenaltyService.class);
		logger.info("*****PenaltyTask BEGIN *****");
		penalty.updateStatePenaltyTypeSuspension();
		logger.info("*****PenaltyTask BEGIN *****");
	}

}
