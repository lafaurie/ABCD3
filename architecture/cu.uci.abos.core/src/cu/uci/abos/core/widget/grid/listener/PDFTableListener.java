package cu.uci.abos.core.widget.grid.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ReportType;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.URLUtil;
import cu.uci.abos.core.widget.grid.TreeTable;
import cu.uci.abos.reports.CollectEntities;
import cu.uci.abos.reports.DocumentParam;
import cu.uci.abos.reports.Entity;
import cu.uci.abos.reports.EntityParam;
import cu.uci.abos.reports.PDFReportGenerator;
import cu.uci.abos.reports.ReportParam;
import cu.uci.abos.reports.TabularParam;

public class PDFTableListener extends SelectionAdapter {

	private final List<String> criterias;
	private final TreeTable table;
	private final String name;
	private final String header;
	private static final long serialVersionUID = 1L;

	public PDFTableListener(List<String> criterias, TreeTable table, String name, String header) {
		this.criterias = criterias;
		this.table = table;
		this.name = name;
		this.header = header;
	}

	public void widgetSelected(SelectionEvent e) {
		if (table.getCellValues().length==0) {
			RetroalimentationUtils.showInformationShellMessage(AbosMessages.get().MSG_EMPTY_GRID);
		} else {
			List<ReportParam> params= new LinkedList<>();
			if (criterias!=null && !criterias.isEmpty()) {
				EntityParam ep = new EntityParam();
				ep.setHeader(MessageUtil.unescape(AbosMessages.get().SEARCH_CRITERIA));
				List<Entity>items = new LinkedList<>();
				for (Iterator<String> iterator = criterias.iterator(); iterator.hasNext();) {
					items.add(new Entity(iterator.next(), iterator.next()));
				}
				ep.setItems(items);
				CollectEntities ec = new CollectEntities();
				List<EntityParam> p = new ArrayList<>();
				p.add(ep);
				ec.setItems(p);
				params.add(ec);
			}
			//CRUDTreeTable myTable = (CRUDTreeTable) table;
			//int size = myTable.getPaginator().getPageSize();
			//int currentPage = myTable.getPaginator().getCurrentPage();
			//myTable.getPaginator().setPageSize(myTable.getPaginator().getTotalElements());
			//myTable.getPaginator().goToFirstPage();
			String listOfMatches = header==null?MessageUtil.unescape(AbosMessages.get().LIST_OF_MATCHES):header;
			TabularParam tab= new TabularParam(listOfMatches, table.getColumnHeaders(), table.getCellValues());
			
			params.add(tab);
			DocumentParam param = new DocumentParam(name, params);
			URLUtil.generateDownloadReport(ReportType.PDF, ServiceProviderUtil.getService(PDFReportGenerator.class).generateReport(ServiceProviderUtil.getService(PDFReportGenerator.class).getStyle(), name, ServiceProviderUtil.getService(PDFReportGenerator.class).getReportTemplate(), param));
			
			//myTable.getPaginator().setPageSize(size);
			//myTable.getPaginator().goToPage(currentPage);
		}
		
	}
}