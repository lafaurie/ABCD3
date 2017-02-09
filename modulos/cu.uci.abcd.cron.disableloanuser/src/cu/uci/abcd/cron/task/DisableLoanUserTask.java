package cu.uci.abcd.cron.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cu.uci.abcd.circulation.ILoanUserService;

public class DisableLoanUserTask implements Job {
	private Logger logger = LoggerFactory.getLogger(DisableLoanUserTask.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ILoanUserService service = TaskUtils.getService(ILoanUserService.class);
		logger.info("*****DisableUserTask BEGIN *****");
		service.updateStateLoanUser();
		logger.info("*****DisableUserTask END *****");

	}

}
