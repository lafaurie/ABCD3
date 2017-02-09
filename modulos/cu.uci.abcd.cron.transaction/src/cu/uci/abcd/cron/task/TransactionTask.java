package cu.uci.abcd.cron.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cu.uci.abcd.circulation.ITransactionService;

public class TransactionTask implements Job {
	private Logger logger = LoggerFactory.getLogger(TransactionTask.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ITransactionService service = TaskUtils.getService(ITransactionService.class);
		logger.info("*****TransactionTask BEGIN *****");
		service.registerPenaltyAutomatic();
		logger.info("*****TransactionTask END *****");

	}

}
