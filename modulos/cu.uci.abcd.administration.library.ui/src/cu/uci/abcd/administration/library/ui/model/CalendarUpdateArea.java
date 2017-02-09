package cu.uci.abcd.administration.library.ui.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.communFragment.RegisterCalendarFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.CalendarViewController;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CalendarUpdateArea extends BaseEditableArea {

	private ViewController controller;
	private RegisterCalendarFragment registerCalendarFragment;
	private Composite parentComposite;
	private Map<String, Control> controlsMaps;
	private Calendar calendar;
	private int valueDayType;
	private CRUDTreeTable table;
	private Button saveBtn;
	private int dimension;
	@SuppressWarnings("unused")
	private Library library;
	//Composite msg;
	List<Date> listDate = new ArrayList<Date>();
	public CalendarUpdateArea(CRUDTreeTable table, ViewController controller, int valueDayType) {
		this.controller = controller;
		this.valueDayType = valueDayType;
		this.table = table;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		dimension = parent.getParent().getParent().getBounds().width;
		addComposite(parent);

		Calendar calendarToUpdate = (Calendar) entity.getRow();
		registerCalendarFragment = new RegisterCalendarFragment(controller, dimension, 2);
		parentComposite = (Composite) registerCalendarFragment.createUIControl(parent);
		controlsMaps = registerCalendarFragment.getControls();
		calendar = ((CalendarViewController) controller).getCalendarById(calendarToUpdate.getCalendarID());
		loadDataCalendar(calendar);
		//msg = registerCalendarFragment.getMsg();
		return parentComposite;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				

				if (registerCalendarFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(
							//msg, 
							MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
				if (registerCalendarFragment.getValidator().decorationFactory.AllControlDecorationsHide()) {
				
					
					
					if(registerCalendarFragment.getRangeButton().getSelection() &&  ( Auxiliary.getDate(registerCalendarFragment.getDayDateTime()).after(Auxiliary.getDate(registerCalendarFragment.getDayToDateTime())) || Auxiliary.getDate(registerCalendarFragment.getDayDateTime()).equals(Auxiliary.getDate(registerCalendarFragment.getDayToDateTime()))  )  ){
						RetroalimentationUtils.showErrorShellMessage(
								MessageUtil.unescape(AbosMessages.get().LABEL_END_DATE_GREATER));
						registerCalendarFragment.getDayDateTime().setBackground(new Color(registerCalendarFragment.getDayDateTime().getDisplay(), 255, 204, 153));
						registerCalendarFragment.getDayToDateTime().setBackground(new Color(registerCalendarFragment.getDayToDateTime().getDisplay(), 255, 204, 153));
					}else{
						registerCalendarFragment.getDayDateTime().setBackground(null);
						registerCalendarFragment.getDayToDateTime().setBackground(null);
					
						
						
					
					
				DateTime dayDateTime = (DateTime) controlsMaps
						.get("dayDateTime");
				DateTime dayToDateTime = null;
				if( registerCalendarFragment.getRangeButton().getSelection() ) {
					dayToDateTime = (DateTime) controlsMaps
							.get("dayToDateTime");
					
				}
				
				Text titleText = (Text) controlsMaps.get("titleText");
				Text descriptionText = (Text) controlsMaps
						.get("descriptionText");
				
				//Date date = new Date(dayDateTime.getYear() - 1900, dayDateTime
						//.getMonth(), dayDateTime.getDay());
				String title = titleText.getText().replaceAll(" +", " ").trim();
				String description = descriptionText.getText().replaceAll(" +", " ").trim();
				Nomenclator daytype1 = null;
				Nomenclator daytype2 = null;
				
			
				daytype1 = ((CalendarViewController) controller)
							.getAllManagementLibraryViewController()
							.getLibraryService()
							.findNomenclatorById(Nomenclator.DAY_TYPE_ALL_YEAR);
			
				daytype2 = ((CalendarViewController) controller)
							.getAllManagementLibraryViewController()
							.getLibraryService()
							.findNomenclatorById(Nomenclator.DAY_TYPE_THIS_YEAR);
	
				Library library = (Library) SecurityUtils
						.getService().getPrincipal()
						.getByKey("library");
				
				boolean flag = false;
				
				if(valueDayType == 2){
					
					List<Calendar> thisYear = ((CalendarViewController) controller).findCalendarByLibraryAndDayType(daytype2, library.getLibraryID());
					List<Calendar> allYear = ((CalendarViewController) controller).findCalendarByLibraryAndDayType(daytype1, library.getLibraryID());
					
					flag = exist(dayDateTime, dayToDateTime, thisYear, true, MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST_IN_THIS_YEAR), MessageUtil.unescape(AbosMessages.get().ELEMENT_RANGE_EXIST_IN_THIS_YEAR))
							||
					exist(dayDateTime, dayToDateTime, allYear, MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST_IN_ALL_YEAR), MessageUtil.unescape(AbosMessages.get().ELEMENT_RANGE_EXIST_IN_ALL_YEAR));
					
				}else{
					if( valueDayType == 1 ){
						
						List<Calendar> allYear = ((CalendarViewController) controller).findCalendarByLibraryAndDayType(daytype1, library.getLibraryID());
						List<Calendar> thisYear = ((CalendarViewController) controller).findCalendarByLibraryAndDayType(daytype2, library.getLibraryID());
						
						
						flag = exist(dayDateTime, dayToDateTime, allYear, MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST_IN_ALL_YEAR), MessageUtil.unescape(AbosMessages.get().ELEMENT_RANGE_EXIST_IN_ALL_YEAR)) ||
						exist(dayDateTime, dayToDateTime, thisYear, MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST_IN_THIS_YEAR), MessageUtil.unescape(AbosMessages.get().ELEMENT_RANGE_EXIST_IN_THIS_YEAR));
						
						
					}
				}
				if(!flag){
				Nomenclator daytype = daytype2;
				if (valueDayType == 1) {
					daytype = daytype1;
				}
				
				for (int i = 0; i < listDate.size(); i++) {
					//Calendar calendar = new Calendar();
					calendar.setCalendarDay(listDate.get(i));
					if (valueDayType == 2) {
						Integer year = Integer.parseInt(new SimpleDateFormat("yyyy").format(listDate.get(i)));
						calendar.setYear(year);
						
					}
					calendar.setDaytype(daytype);
					calendar.setDescription(description);
					calendar.setLibrary(library);
					calendar.setCalendarName(title);
					calendar.setDescription(description);
					((CalendarViewController) controller).saveCalendar(calendar);
					calendar = null;
				}
				
				table.getPaginator().goToFirstPage();
				table.destroyEditableArea();

				Composite viewSmg = table.getParent();
				
				RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
						.unescape(cu.uci.abos.core.l10n.AbosMessages
								.get().MSG_INF_UPDATE_DATA));
				refresh();
				}
				}
			}else {
				RetroalimentationUtils.showErrorShellMessage(
						//msg, 
						MessageUtil
						.unescape(cu.uci.abos.core.l10n.AbosMessages
								.get().MSG_ERROR_INCORRECT_DATA));
			}
			}
		
				/*
				if (registerCalendarFragment.getValidator().decorationFactory.AllControlDecorationsHide()) {

					DateTime dayDateTime = (DateTime) controlsMaps.get("dayDateTime");
					Text titleText = (Text) controlsMaps.get("titleText");
					Text descriptionText = (Text) controlsMaps.get("descriptionText");
					@SuppressWarnings("deprecation")
					Date date = new Date(dayDateTime.getYear() - 1900, dayDateTime.getMonth(), dayDateTime.getDay());
					String title = titleText.getText();
					String description = descriptionText.getText();
					Nomenclator daytype = null;
					if (valueDayType == 1) {
						daytype = ((CalendarViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById( Nomenclator.DAY_TYPE_ALL_YEAR);
					}
					if (valueDayType == 2) {
						daytype = ((CalendarViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById( Nomenclator.DAY_TYPE_THIS_YEAR);
					}
					//if (valueDayType == 3) {
						//daytype = ((CalendarViewController) controller)
							//	.getAllManagementLibraryViewController()
								//.getLibraryService()
								//.findNomenclatorById(Nomenclator.DAY_TYPE_THIS_WEEK);
					//}
					calendar.setCalendarDay(date);
					calendar.setDaytype(daytype);
					calendar.setDescription(description);
					calendar.setCalendarName(title);
					@SuppressWarnings("unused")
					Calendar calendarSaved = ((CalendarViewController) controller)
							.saveCalendar(calendar);
					table.getPaginator().goToFirstPage();
					calendar = null;
					table.destroyEditableArea();
					
					RetroalimentationUtils.showInformationMessage(MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
					
					
				}else{
					RetroalimentationUtils.showErrorMessage(MessageUtil.unescape(AbosMessages.get().LABEL_FIELDEMPTY));
				
				}
				*/
			}
		});
		return parent;

	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public String getID() {
		return "updateCalendarID";
	}
	
	@Override
	public void l10n() {
		saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		saveBtn.getParent().layout(true, true);
		saveBtn.getParent().redraw();
		saveBtn.getParent().update();
		registerCalendarFragment.l10n();
	}

	private void loadDataCalendar(Calendar calendar) {
		((Button) controlsMaps.get("rangeButton")).setVisible(false);
		
		((Label) controlsMaps.get("dayToLabel")).setVisible(false);
		
		((DateTime) controlsMaps.get("dayToDateTime")).setVisible(false);
		
		
		
		((Text) controlsMaps.get("titleText")).setText(calendar.getCalendarName() + "");
		((Text) controlsMaps.get("descriptionText")).setText(calendar.getDescription() + "");
		java.util.Date utilDate = new java.util.Date(calendar.getCalendarDay().getTime());
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(utilDate));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(utilDate));
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(utilDate));
		((DateTime) controlsMaps.get("dayDateTime")).setDate(year, month - 1, day);
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean exist(DateTime dayDateTime, DateTime dayToDateTime, List<Calendar> list, boolean yearCheck, String singleSmg, String rangeMsg){
		listDate.clear();
		Date temp = new Date(dayDateTime.getYear() - 1900, dayDateTime.getMonth(), dayDateTime.getDay());
		if( dayToDateTime==null ){
			listDate.add(temp);
		}else{
		Date dayToDate = new Date(dayToDateTime.getYear() - 1900, dayToDateTime.getMonth(), dayToDateTime.getDay());
		  while(temp.compareTo(dayToDate)<=0){
			  listDate.add(temp);
			  temp = new Date(temp.getTime() + 1 * 24 * 60 * 60 * 1000);
		  }
		}
		
		
		for (int j = 0; j < listDate.size(); j++) {
		for (int i = 0; i < list.size(); i++) {
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Calendar cal1 = java.util.Calendar.getInstance();
			cal.setTime(list.get(i).getCalendarDay());
			cal1.setTime(listDate.get(j));
			//int tata = cal1.get(java.util.Calendar.DAY_OF_MONTH);
			//java.util.Calendar.getInstance().setTime(listDate.get(j));
			if( list.get(i).getCalendarID()!=calendar.getCalendarID()) {
					if (
                       (yearCheck) ? 
                    cal.get(java.util.Calendar.MONTH) + 1 == listDate.get(j).getMonth() + 1
					&& cal.get(java.util.Calendar.DAY_OF_MONTH) == cal1.get(java.util.Calendar.DAY_OF_MONTH)
					&& cal.get(java.util.Calendar.YEAR) == listDate.get(j).getYear()+1900
					: cal.get(java.util.Calendar.MONTH) + 1 == listDate.get(j).getMonth() + 1
					&& cal.get(java.util.Calendar.DAY_OF_MONTH) == cal1.get(java.util.Calendar.DAY_OF_MONTH)
							) {
						
						if( dayToDateTime==null ){
						RetroalimentationUtils.showErrorShellMessage(
								//msg, 
								singleSmg);
						}else{
							RetroalimentationUtils.showErrorShellMessage(
									//msg, 
									rangeMsg);
						}
						return true;
					}
		}
			
			
		}
	}
		return false;
	}
	
	public boolean exist(DateTime dayDateTime, DateTime dayToDateTime, List<Calendar> list, String singleSmg, String rangeMsg ){
		return exist(dayDateTime, dayToDateTime, list, false, singleSmg, rangeMsg);
	}

}
