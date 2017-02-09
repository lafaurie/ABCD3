package cu.uci.abcd.statistic.impl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dao.common.LoanObjectDAO;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.dao.specification.StatisticSpecification;
import cu.uci.abcd.dao.statistic.ReportDAO;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionAND;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionOR;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.domain.statistic.Report;
import cu.uci.abcd.statistic.IComplexIndicator;
import cu.uci.abcd.statistic.IIndicator;
import cu.uci.abcd.statistic.IReport;

public class ReportImpl implements IReport {

	private Map<String, IComplexIndicator> complexIndicators;
	private IJisisDataProvider jisisDataProvider;
	private ReportDAO reportDAO;
	private LoanObjectDAO loanObjectDAO;
	private IIndicator indicator;
	private final String databaseName = "marc21";

	public static final String ADQUISITIONS_DATABASE_NAME = "Registro_De_Adquisicion";
	public static final String PURCHASE_ACQUISITION_TYPE = "Compra";
	public static final String EXCHANGE_ACQUISITION_TYPE = "Canje";
	public static final String DONATED_ACQUISITION_TYPE = "Donación";

	public ReportImpl() {
		super();
		complexIndicators = new HashMap<String, IComplexIndicator>();
		register();
	}

	public IIndicator getIndicator() {
		return indicator;
	}

	public void setIndicator(IIndicator indicator) {
		this.indicator = indicator;
	}

	public void bind(ReportDAO reportDAO, Map<?, ?> properties) {
		this.reportDAO = reportDAO;
	}
	
	public void bind(LoanObjectDAO loanObjectDao, Map<?, ?> properties) {
		this.loanObjectDAO = loanObjectDao;
	}

	public void bind(IJisisDataProvider dataProvider, Map<?, ?> properties) {
		this.jisisDataProvider = dataProvider;
	}

	@Override
	public Report addReport(Report report) {
		return reportDAO.save(report);
	}

	@Override
	public Report editReport(Long id) {
		// TODO FIXME OIGRES HACER ESTE METODO
		return null;
	}

	@Override
	public Report findReportByName(String nameReport) {
		return reportDAO.findReportsByReportName(nameReport);
	}

	@Override
	public void deleteReport(Long id) {
		reportDAO.delete(id);
	}

	@Override
	public Collection<Report> findAllReport() {
		return (Collection<Report>) reportDAO.findAll();
	}

	@Override
	public Report generateReportValues(Report report, Object... parameters) {
		for (Indicator ind : report.getIndicators()) {
			try {
				indicator.getIndicatorValue(ind, parameters);
			} catch (Exception e) {
				indicator.getIndicatorValues(ind, parameters);
			}
			if (complexIndicators.containsKey(ind.getIndicatorId())) {
				ind.setValue(complexIndicators.get(ind.getIndicatorId()).execute(report, parameters));
			}
		}
		return report;
	}

	@Override
	public Page<Report> findAllReportByIndicator(Specification<Report> specification, Pageable pageable) {
		// TODO FIXME OIGRES HACER ESTE METODO
		return null;
	}

	public Page<Report> listAllReport(String nameReport, int page, int size, int direction, String orderByString) {
		return reportDAO.findAll(StatisticSpecification.searchReport(nameReport), PageSpecification.getPage(page, size, direction, orderByString));
	}

	private void register() {
		complexIndicators.put("2.2", new IComplexIndicator() {

			@Override
			public Object execute(Report report, Object... params) {
				Long startDate = ((Date) params[1]).getTime();
				Long endDate = ((Date) params[2]).getTime();
				Long dif = endDate - startDate;
				Double days = Math.floor(dif / (1000 * 60 * 60 * 24));
				Double weeks = Math.floor(days / 7);
				return weeks;
			}
		});

		complexIndicators.put("2.3", new IComplexIndicator() {

			@Override
			public Object execute(Report report, Object... params) {
				Double totalHours = 0d;
				Long startDate = ((Date) params[1]).getTime();
				Long endDate = ((Date) params[2]).getTime();
				Long dif = endDate - startDate;
				Double days = Math.floor(dif / (1000 * 60 * 60 * 24));
				Double weeks = Math.floor(days / 7);

				for (Indicator ind : report.getIndicators()) {

					if (ind.getIndicatorId().equals("2.1")) {
						Double aux = (double) indicator.getIndicatorValue(ind, params).getValue();
						totalHours = weeks * aux;
					}
				}
				return totalHours;
			}
		});

		complexIndicators.put("5.8.1", new IComplexIndicator() {

			@Override
			public Object execute(Report report, Object... params) {
				/*
				 * int size = jisisDataProvider.totalRecords(databaseName,
				 * params[3].toString()); List<Record> recordIsis= new
				 * ArrayList<>(); recordIsis=
				 * jisisDataProvider.findByOptions(null, databaseName,
				 * params[3].toString()); Date term = params[1]; Date term1 =
				 * params[2];
				 * 
				 * 
				 * 
				 * for (int i = 0; i < size; i++) {
				 * 
				 * try {
				 * 
				 * Field cataloguingFieldAfter = (Field)
				 * recordIsis.get(i).getRecord().getField(960); String
				 * yearParser = cataloguingFieldAfter.getSubfield("a");
				 * 
				 * 
				 * } catch (Exception e) { e.printStackTrace(); } }
				 * 
				 * 
				 * return recordIsis;
				 * 
				 * }
				 */
				return null;
			}

		});

		complexIndicators.put("5.8.2", new IComplexIndicator() {

			@Override
			public Object execute(Report report, Object... params) {
				Long totalRecords = (long) 0;
				try {
					totalRecords = jisisDataProvider.totalRecords(databaseName, params[3].toString());

				} catch (Exception e) {
					e.printStackTrace();
				}
				return totalRecords;
			}

		});

		complexIndicators.put("5.2.2.1", new IComplexIndicator() {

			@Override
			public Object execute(Report report, Object... params) {
				List<Record> serialsInPeriod = getSerialsInPeriod(params);
				if (null == serialsInPeriod)
					return 0;
				List<Record> purchasedRecordsInPeriod = filterPurchasedRecords(serialsInPeriod, params[3].toString());
				return ((null == purchasedRecordsInPeriod) || (purchasedRecordsInPeriod.isEmpty())) ? 0 : purchasedRecordsInPeriod.size();
			}
		});

		complexIndicators.put("5.1.2.1.1", new IComplexIndicator() {

			@Override
			public Object execute(Report report, Object... params) {
				List<Record> recordsInPeriod = new ArrayList<>();

				Date startDate;
				try {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					startDate = new Date(df.parse(params[1].toString()).getTime());
					Date endDate = new Date(df.parse(params[2].toString()).getTime());

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					
					List<Option> options = null;
					
					try {
						while (startDate.compareTo(endDate) <= 0) {
							options = new ArrayList<>();
							
							df = new SimpleDateFormat("yyMMdd");
							Option optionCreationDate = new Option("8", df.format(startDate), 1);
							options.add(optionCreationDate);
							
							Option optionMonograph = new OptionAND("3007", "m", 1);
							options.add(optionMonograph);
							
							Option optionTextualMaterial = new Option("3006", "a", 2);
							OptionOR optionCartographicManuscriptMaterial = new OptionOR("3006", "f", 2);
							OptionOR optionTextualManuscriptMaterial = new OptionOR("3006", "t", 2);
							
							options.add(optionTextualMaterial);
							options.add(optionTextualManuscriptMaterial);
							options.add(optionCartographicManuscriptMaterial);
							
							recordsInPeriod.addAll(jisisDataProvider.findByOptions(options, databaseName, params[3].toString()));

							calendar.add(Calendar.DATE, 1);
							startDate = new Date(calendar.getTime().getTime());
						}

					} catch (JisisDatabaseException e) {
						e.printStackTrace();
					}
					return recordsInPeriod.size();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return "";
			}
		});
		
		complexIndicators.put("5.1.2.1.2", new IComplexIndicator() {
			
			@Override
			public Object execute(Report report, Object... params) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				Date startDate;
				try {
					startDate = new Date(df.parse(params[1].toString()).getTime());
					Date endDate = new Date(df.parse(params[2].toString()).getTime());
					
					return getMonographicVolumesInPeriod(startDate, endDate, params[3].toString()).size();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				return null;
			}
		});

		complexIndicators.put("5.1.2.2.1", new IComplexIndicator() {

			@Override
			public Object execute(Report report, Object... params) {
				Option purchaseOption = new Option("23", PURCHASE_ACQUISITION_TYPE);

				List<Option> options = new ArrayList<>();
				
				options.add(purchaseOption);
				
				try {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = new Date(df.parse(params[1].toString()).getTime());
					Date endDate = new Date(df.parse(params[2].toString()).getTime());
					
					List<Record> purchasedTitles = new ArrayList<>();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					
					df = new SimpleDateFormat("dd/MM/yyyy");
					while (startDate.compareTo(endDate) <=0 ) {
						OptionAND registrationDateOption = new OptionAND("20", df.format(startDate));
						options.add(registrationDateOption);
						purchasedTitles.addAll(jisisDataProvider.findByOptions(options, ADQUISITIONS_DATABASE_NAME, params[3].toString()));
						options.remove(registrationDateOption);
						calendar.add(Calendar.DATE, 1);
						startDate = new Date(calendar.getTime().getTime());
					}
					
					//filtering monographic text materials
					
					options.clear();
					
					Option optionMonograph = new Option("3007", "m", 1);
					options.add(optionMonograph);
					
					Option optionTextualMaterial = new Option("3006", "a", 2);
					OptionOR optionCartographicManuscriptMaterial = new OptionOR("3006", "f", 2);
					OptionOR optionTextualManuscriptMaterial = new OptionOR("3006", "t", 2);
					
					options.add(optionTextualMaterial);
					options.add(optionTextualManuscriptMaterial);
					options.add(optionCartographicManuscriptMaterial);
					
					List<Record> filtered = new ArrayList<>();
					
					for (Record record : purchasedTitles){
						try {
							String controlNumber = record.getField(1).getStringFieldValue();
							Option controlNumberOption = new Option("1", controlNumber, 3);
							options.add(controlNumberOption);
							
							filtered.addAll(jisisDataProvider.findByOptions(options, databaseName, params[3].toString()));
							
							options.remove(options.indexOf(controlNumberOption));
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					return filtered.size();
				} catch (JisisDatabaseException | ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		
		complexIndicators.put("5.1.2.2.2", new IComplexIndicator() {
			
			@Override
			public Object execute(Report report, Object... params) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				Date startDate;
				try {
					startDate = new Date(df.parse(params[1].toString()).getTime());
					Date endDate = new Date(df.parse(params[2].toString()).getTime());
					
					List<LoanObject> monographicVolumesInPeriod = getMonographicVolumesInPeriod(startDate, endDate, params[3].toString());
					
					// filtering purchased volumes
					List<Record> filtered = new ArrayList<>();
					for (LoanObject loanObject : monographicVolumesInPeriod){
						
						List<Option> options = new ArrayList<>();
						Option controlNumberOption = new Option("1", loanObject.getControlNumber());
						OptionAND purchaseopOption = new OptionAND("23", PURCHASE_ACQUISITION_TYPE);
						
						options.add(controlNumberOption);
						options.add(purchaseopOption);
						
						try {
							filtered.addAll(jisisDataProvider.findByOptions(options, ADQUISITIONS_DATABASE_NAME, params[3].toString()));
						} catch (JisisDatabaseException e) {
							e.printStackTrace();
						}
					}
					
					return filtered.size();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				return null;
			}
		});
		
		complexIndicators.put("5.1.2.3.1", new IComplexIndicator() {

			@Override
			public Object execute(Report report, Object... params) {
				Option exchangeOption = new Option("23", EXCHANGE_ACQUISITION_TYPE, 1);
				OptionOR donatedOption = new OptionOR("23", DONATED_ACQUISITION_TYPE, 1);

				List<Option> options = new ArrayList<>();
				
				options.add(exchangeOption);
				options.add(donatedOption);
				
				try {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = new Date(df.parse(params[1].toString()).getTime());
					Date endDate = new Date(df.parse(params[2].toString()).getTime());
					
					List<Record> purchasedTitles = new ArrayList<>();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					
					df = new SimpleDateFormat("dd/MM/yyyy");
					while (startDate.compareTo(endDate) <=0 ) {
						Option registrationDateOption = new Option("20", df.format(startDate),2);
						options.add(registrationDateOption);
						purchasedTitles.addAll(jisisDataProvider.findByOptions(options, ADQUISITIONS_DATABASE_NAME, params[3].toString()));
						options.remove(registrationDateOption);
						calendar.add(Calendar.DATE, 1);
						startDate = new Date(calendar.getTime().getTime());
					}
					
					//filtering monographic text materials
					
					options.clear();
					
					Option optionMonograph = new Option("3007", "m", 1);
					options.add(optionMonograph);
					
					Option optionTextualMaterial = new Option("3006", "a", 2);
					OptionOR optionCartographicManuscriptMaterial = new OptionOR("3006", "f", 2);
					OptionOR optionTextualManuscriptMaterial = new OptionOR("3006", "t", 2);
					
					options.add(optionTextualMaterial);
					options.add(optionTextualManuscriptMaterial);
					options.add(optionCartographicManuscriptMaterial);
					
					List<Record> filtered = new ArrayList<>();
					
					for (Record record : purchasedTitles){
						try {
							String controlNumber = record.getField(1).getStringFieldValue();
							Option controlNumberOption = new Option("1", controlNumber, 3);
							options.add(controlNumberOption);
							
							filtered.addAll(jisisDataProvider.findByOptions(options, databaseName, params[3].toString()));
							
							options.remove(options.indexOf(controlNumberOption));
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					return filtered.size();
				} catch (JisisDatabaseException | ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		
		complexIndicators.put("5.1.2.3.2", new IComplexIndicator() {
			
			@Override
			public Object execute(Report report, Object... params) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date startDate = new Date(df.parse(params[1].toString()).getTime());
					Date endDate = new Date(df.parse(params[2].toString()).getTime());
					
					List<LoanObject> monographicVolumesInPeriodList = getMonographicVolumesInPeriod(startDate, endDate, params[3].toString());
					
					// filtering purchased o exchanged records
					List<Record> titles = new ArrayList<>();
					for (LoanObject loanObject : monographicVolumesInPeriodList){
						String controlNumber = loanObject.getControlNumber();
						
						Option controlNumberOption = new Option("1", controlNumber, 1);
						Option exchangeOption = new Option("23", EXCHANGE_ACQUISITION_TYPE, 2);
						OptionOR donatedOption = new OptionOR("23", DONATED_ACQUISITION_TYPE, 2);

						List<Option> options = new ArrayList<>();
						
						options.add(controlNumberOption);
						options.add(exchangeOption);
						options.add(donatedOption);
						
						titles.addAll(jisisDataProvider.findByOptions(options, ADQUISITIONS_DATABASE_NAME, params[3].toString()));
					}
					
					return titles.size();
				} catch (ParseException | JisisDatabaseException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		
		complexIndicators.put("5.1.1.1", new IComplexIndicator() {
			
			@Override
			public Object execute(Report report, Object... params) {
				
				List<Option> options = new ArrayList<>();
				Option optionMonograph = new Option("3007", "m", 1);
				options.add(optionMonograph);
				
				Option optionTextualMaterial = new Option("3006", "a", 2);
				OptionOR optionCartographicManuscriptMaterial = new OptionOR("3006", "f", 2);
				OptionOR optionTextualManuscriptMaterial = new OptionOR("3006", "t", 2);
				
				options.add(optionTextualMaterial);
				options.add(optionTextualManuscriptMaterial);
				options.add(optionCartographicManuscriptMaterial);
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date startDate = new Date(df.parse(params[1].toString()).getTime());
					Date endDate = new Date(df.parse(params[2].toString()).getTime());
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					
					List<Record> titlesInPeriod = new ArrayList<>();
					while (startDate.compareTo(endDate) <= 0) {
						List<Record> monographies = jisisDataProvider.findByOptions(options, databaseName, params[3].toString());
						titlesInPeriod.addAll(getRecordsByCreationDate(startDate, monographies));
						calendar.add(Calendar.DATE, 1);
						startDate = new Date(calendar.getTime().getTime());
					}
					
					return titlesInPeriod.size();
				} catch (ParseException | JisisDatabaseException e) {
					e.printStackTrace();
				}
				
				return null;
			}
		});
		
		complexIndicators.put("5.1.1.2", new IComplexIndicator() {
			
			@Override
			public Object execute(Report report, Object... params) {
				List<LoanObject> volumesInPeriodList = loanObjectDAO.findAll(StatisticSpecification.searchVolumesAtEndOfPeriod());
				
				// filtering monographic materials in paper
				List<LoanObject> filtered = new ArrayList<>();
				
				for (LoanObject loanObject : volumesInPeriodList){
					String controlNumber = loanObject.getControlNumber();
					
					List<Option> options = new ArrayList<>();
					Option controlNumberOption = new Option("1", controlNumber, 1);
					options.add(controlNumberOption);
					
					Option optionMonograph = new OptionAND("3007", "m", 1);
					options.add(optionMonograph);
					
					Option optionTextualMaterial = new Option("3006", "a", 2);
					OptionOR optionCartographicManuscriptMaterial = new OptionOR("3006", "f", 2);
					OptionOR optionTextualManuscriptMaterial = new OptionOR("3006", "t", 2);
					
					options.add(optionTextualMaterial);
					options.add(optionTextualManuscriptMaterial);
					options.add(optionCartographicManuscriptMaterial);
					
					try {
						if (!jisisDataProvider.findByOptions(options, databaseName, params[3].toString()).isEmpty())
							filtered.add(loanObject);
					} catch (JisisDatabaseException e) {
						e.printStackTrace();
					}
				}
				
				return filtered.size();
				
			}
		});
	}
	
	private List<LoanObject> getMonographicVolumesInPeriod(Date startDate, Date endDate, String isisHome){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		
		// getting all volumes in period
		List<LoanObject> volumesInPeriodList = new ArrayList<>();
		
		while (startDate.compareTo(endDate) <= 0){
			volumesInPeriodList.addAll(loanObjectDAO.findAll(StatisticSpecification.searchVolumesInPeriod(startDate)));
			
			calendar.add(Calendar.DATE, 1);
			startDate = new Date(calendar.getTime().getTime());
		}
		
		// filtering monographic materials in paper
		List<LoanObject> filtered = new ArrayList<>();
		
		for (LoanObject loanObject : volumesInPeriodList){
			String controlNumber = loanObject.getControlNumber();
			
			List<Option> options = new ArrayList<>();
			Option controlNumberOption = new Option("1", controlNumber, 1);
			options.add(controlNumberOption);
			
			Option optionMonograph = new OptionAND("3007", "m", 1);
			options.add(optionMonograph);
			
			Option optionTextualMaterial = new Option("3006", "a", 2);
			OptionOR optionCartographicManuscriptMaterial = new OptionOR("3006", "f", 2);
			OptionOR optionTextualManuscriptMaterial = new OptionOR("3006", "t", 2);
			
			options.add(optionTextualMaterial);
			options.add(optionTextualManuscriptMaterial);
			options.add(optionCartographicManuscriptMaterial);
			
			try {
				if (!jisisDataProvider.findByOptions(options, databaseName, isisHome).isEmpty())
					filtered.add(loanObject);
			} catch (JisisDatabaseException e) {
				e.printStackTrace();
			}
		}
		
		return filtered;
	}
	
	private List<Record> getSerialsInPeriod(Object... params) {
		List<Record> serialsInPeriod = null;

		Date startDate;
		try {
			DateFormat df = new SimpleDateFormat("yy-MM-dd");

			startDate = new Date(df.parse(params[1].toString()).getTime());
			Date endDate = new Date(df.parse(params[2].toString()).getTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);

			while (startDate.before(endDate)) {
				try {
					List<Record> serials = jisisDataProvider.findByOptions(getOptionsForSerials(), databaseName, params[3].toString());
					if (null == serialsInPeriod)
						serialsInPeriod = getRecordsByCreationDate(startDate, serials);
					else
						serialsInPeriod.addAll(getRecordsByCreationDate(startDate, serials));
				} catch (JisisDatabaseException e) {
					e.printStackTrace();
				}

				calendar.add(Calendar.DATE, 1);
				startDate = new Date(calendar.getTime().getTime());
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		return serialsInPeriod;
	}

	private List<Record> filterPurchasedRecords(List<Record> records, String databaseHome) {
		List<Record> purchasedRecords = new ArrayList<>();
		for (Record record : records) {
			try {
				Option controlNumberOption = new Option("1", record.getField(1).getStringFieldValue());
				List<Option> options = new ArrayList<>();
				options.add(controlNumberOption);
				Record locatedRecord = jisisDataProvider.findByOptions(options, ADQUISITIONS_DATABASE_NAME, databaseHome).get(0);

				if (PURCHASE_ACQUISITION_TYPE.equals(locatedRecord.getField(23).getStringFieldValue()))
					purchasedRecords.add(locatedRecord);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return purchasedRecords;
	}

	private List<Option> getOptionsForSerials() {
		List<Option> options = new ArrayList<>();

		Option option3007 = new Option("3007", "s");
		options.add(option3007);

		return options;
	}

	private List<Record> getRecordsByCreationDate(Date creationDate, List<Record> records) {
		List<Record> result = new ArrayList<>();

		for (Record record : records) {
			try {
				String registration = record.getField(8).getStringFieldValue().substring(0, 6);
				SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
				java.util.Date parsed = format.parse(registration);
				Date registrationDate = new Date(parsed.getTime());

				if (creationDate.equals(registrationDate))
					result.add(record);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public Report viewReport(Long id) {
		return reportDAO.findOne(id);
	}
}
