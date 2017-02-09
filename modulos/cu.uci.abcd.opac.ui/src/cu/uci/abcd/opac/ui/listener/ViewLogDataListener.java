package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.record.Field;

import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.contribution.ViewLog;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;

public class ViewLogDataListener implements Listener {

	private static final long serialVersionUID = 1L;

	private ServiceProvider serviceProvider;
	private RecordIsis record;
	private ViewLog viewLog;

	public ViewLogDataListener(ServiceProvider serviceProvider, RecordIsis record) {
		this.serviceProvider = serviceProvider;
		this.record = record;
	}

	@Override
	public void handleEvent(Event arg0) {

		final ContributorService pageService = serviceProvider.get(ContributorService.class);

		viewLog = (ViewLog) ((OpacContributorServiceImpl) pageService).getContributorMap().get("ViewLogID");

		try {

			viewLog.setRecord(record);

			try {

				viewLog.title = record.getTitle();
				Field titleField = (Field) record.getRecord().getField(245);
				viewLog.title = titleField.getSubfield("a");

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				viewLog.autor = (record.getRecord().getField(100).getStringOccurrence(0));
				Field authorField = (Field) record.getRecord().getField(100);
				viewLog.autor = authorField.getSubfield("a");

			} catch (Exception e) {
				e.printStackTrace();
			}

			viewLog.controlNumber = record.getRecord().getField(1).getStringFieldValue();

			try {

				viewLog.publication = " ";

				viewLog.publication = record.getPublication();

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				viewLog.date = " ";

				String field = record.getRecord().getField(260).getStringFieldValue();
				viewLog.date = field.substring(field.length() - 4, field.length());

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				viewLog.fisicalDetail = record.getRecord().getField(300).getStringFieldValue();

			} catch (Exception e) {
				viewLog.fisicalDetail = "";
			}

			try {
				viewLog.topics = record.getRecord().getField(650).getSubfield(0, "^a");
			} catch (Exception e) {
				viewLog.topics = "";
			}

			try {
				viewLog.description = record.getRecord().getField(500).getStringOccurrence(0);
			} catch (Exception e) {
				viewLog.description = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		pageService.selectContributor("ViewLogID");

	}
}