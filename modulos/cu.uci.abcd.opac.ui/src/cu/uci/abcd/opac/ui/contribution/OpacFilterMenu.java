package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionOR;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.controller.ConsultMaterialsController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public class OpacFilterMenu implements PlatformContributor {
	public static final String MAIN_MENU_CONTROL = OpacFilterMenu.class.getName() + "#MAINMENU";
	public static final int MAIN_MENU_WIDTH = 170;

	private ValidatorUtils validator;
	private ServiceProvider serviceProvider;
	private ViewController controller;

	private MainContent mainContent;

	private List<Option> filterOptions;
	private Option option;
	private OptionOR optionOR;

	private List<Library> libraries = new ArrayList<Library>();
	private List<Library> selectedLibraries = new ArrayList<Library>();
	private Library currentLibrary;

	private ExpandBar filterExpandBar;
	private Composite result;
	private Composite leftLogo;
	private Composite filters;
	private Composite materialTypeCompo;
	private Composite locationCompo;
	private Composite dateCompo;

	private ExpandItem materialTypeExpItem;
	private ExpandItem locationExpItem;
	private ExpandItem dateExpItem;

	private Label filtersName;

	private Button bookCheck;
	private Button referenceWorksCheck;
	private Button serialPublicationsCheck;
	private Button thesisCheck;
	private Button nonBookMaterialsCheck;

	private Button allLibraryCheck;
	private Button libraryCheck;
	private Button lastLibrary;

	private Button addDate;
	private Image addDateImage;

	private int before;
	private int after;

	private Text customRange1;
	private Text customRange2;

	private Label sinceLabel;
	private Label untilLabel;

	private int recordTypeGroup = 0;

	private Map<String, Control> controls;

	public OpacFilterMenu(ServiceProvider serviceProvider) {
		super();
		this.serviceProvider = serviceProvider;
		this.controls = new HashMap<String, Control>();

	}

	public void update() {
		result.layout(true, true);
		result.redraw();
		result.update();
	}

	public Composite getLeftLogo() {
		return leftLogo;
	}

	public Composite getFilters() {
		return filters;
	}

	@Override
	public Control createUIControl(final Composite parent) {

		ContributorService pageService = this.serviceProvider.get(ContributorService.class);

		filterOptions = new ArrayList<Option>();

		mainContent = (MainContent) ((OpacContributorServiceImpl) pageService).getContributorMap().get("MainContentID");

		validator = new ValidatorUtils(new CustomControlDecoration());

		result = new Composite(parent, SWT.V_SCROLL);
		result.setLayout(new FormLayout());
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		leftLogo = new Composite(result, SWT.V_SCROLL);
		leftLogo.setLayout(new FormLayout());
		leftLogo.setData(RWT.CUSTOM_VARIANT, "workspace_content");

		result.setBackground(leftLogo.getBackground());

		FormDatas.attach(leftLogo).atTop().atRight().atBottom();

		filters = new Composite(result, SWT.V_SCROLL);
		filters.setLayout(new FormLayout());
		filters.setData(RWT.CUSTOM_VARIANT, "workspace_content");

		filters.setVisible(false);

		FormDatas.attach(filters).atTop().atLeft(-20).atBottom();

		Label picture = new Label(leftLogo, SWT.NONE);
		picture.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		String src = RWT.getResourceManager().getLocation("opac-main-izquierdo");
		picture.setText("<img  width='" + 130 + "' height='" + 220 + "' src='" + src + "'></img> ");

		FormDatas.attach(picture).atRight(-20).atBottom();

		filtersName = new Label(filters, 0);
		filtersName.setFont(new Font(result.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(filtersName).atTop(10).atLeft(10);

		filterExpandBar = new ExpandBar(filters, 0);
		FormDatas.attach(filterExpandBar).atTopTo(filtersName, 3).atLeft().withWidth(180).withHeight(800);

		materialTypeCompo = new Composite(filterExpandBar, 0);
		materialTypeCompo.setLayout(new FormLayout());

		materialTypeExpItem = new ExpandItem(filterExpandBar, 0);
		materialTypeExpItem.setControl(materialTypeCompo);
		materialTypeExpItem.setHeight(125);

		bookCheck = new Button(materialTypeCompo, SWT.CHECK);
		FormDatas.attach(bookCheck).atTop(3).atLeft(3);

		referenceWorksCheck = new Button(materialTypeCompo, SWT.CHECK);
		FormDatas.attach(referenceWorksCheck).atTopTo(bookCheck).atLeft(3);

		serialPublicationsCheck = new Button(materialTypeCompo, SWT.CHECK);
		FormDatas.attach(serialPublicationsCheck).atTopTo(referenceWorksCheck).atLeft(3);

		thesisCheck = new Button(materialTypeCompo, SWT.CHECK);
		FormDatas.attach(thesisCheck).atTopTo(serialPublicationsCheck).atLeft(3);

		nonBookMaterialsCheck = new Button(materialTypeCompo, SWT.CHECK);
		FormDatas.attach(nonBookMaterialsCheck).atTopTo(thesisCheck).atLeft(3);

		/**
		 * Location
		 */

		locationExpItem = new ExpandItem(filterExpandBar, 0);

		locationCompo = new Composite(filterExpandBar, SWT.V_SCROLL | SWT.H_SCROLL);
		locationCompo.setLayout(new FormLayout());

		allLibraryCheck = new Button(locationCompo, SWT.CHECK);
		allLibraryCheck.setSelection(true);
		FormDatas.attach(allLibraryCheck).atTop(5).atLeft(3);

		try {
			controller = mainContent.getController();
			libraries = ((ConsultMaterialsController) controller).findAllLibrary();
			findLibrary();
			printLibrary(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		locationExpItem.setControl(locationCompo);

		// if (locationCompo.computeSize(SWT.DEFAULT, SWT.DEFAULT).y < 140)
		locationExpItem.setHeight(locationCompo.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		// else
		// locationExpItem.setHeight(140);

		// ** Date ** \\

		dateCompo = new Composite(filterExpandBar, 0);
		dateCompo.setLayout(new FormLayout());

		dateExpItem = new ExpandItem(filterExpandBar, 0);
		dateExpItem.setControl(dateCompo);
		dateExpItem.setHeight(50);

		sinceLabel = new Label(dateCompo, SWT.NORMAL);
		FormDatas.attach(sinceLabel).atTop(5).atLeft(15);

		customRange1 = new Text(dateCompo, SWT.BORDER);
		FormDatas.attach(customRange1).atTopTo(sinceLabel).atLeft(5).withHeight(8).withWidth(32);
		this.controls.put("customRange1", customRange1);

		Label separatorLabel = new Label(dateCompo, SWT.NORMAL);
		separatorLabel.setText("_");
		FormDatas.attach(separatorLabel).atTopTo(sinceLabel, -2).atLeftTo(customRange1, 5);

		untilLabel = new Label(dateCompo, SWT.NORMAL);
		FormDatas.attach(untilLabel).atTop(5).atLeftTo(separatorLabel, 13);

		customRange2 = new Text(dateCompo, SWT.BORDER);
		FormDatas.attach(customRange2).atTopTo(untilLabel).atLeftTo(separatorLabel, 3).withHeight(8).withWidth(32);
		this.controls.put("customRange2", customRange2);

		addDate = new Button(dateCompo, SWT.NORMAL);
		addDateImage = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
		addDate.setImage(addDateImage);
		addDate.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(addDate).atTopTo(untilLabel, -5).atLeftTo(customRange2, 5);

		validator.applyValidator(customRange1, "customRange1", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(customRange1, "customRange2", DecoratorType.NUMBER_ONLY, true, 4);
		validator.applyValidator(customRange2, "customRange3", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(customRange2, "customRange4", DecoratorType.NUMBER_ONLY, true, 4);

		bookCheck.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {

					if (bookCheck.getSelection())
						addOption("3006", "Libro", recordTypeGroup);
					else
						removeOption("Libro");

					findWithFilters();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		referenceWorksCheck.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {

					if (referenceWorksCheck.getSelection())
						addOption("3006", "Obra de referencia", recordTypeGroup);
					else
						removeOption("Obra de referencia");

					findWithFilters();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		serialPublicationsCheck.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {

					if (serialPublicationsCheck.getSelection())
						addOption("3006", "Publicacion seriadas", recordTypeGroup);
					else
						removeOption("Publicacion seriadas");

					findWithFilters();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		thesisCheck.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {
					if (thesisCheck.getSelection())
						addOption("3006", "Tesis", recordTypeGroup);
					else
						removeOption("Tesis");

					findWithFilters();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		addDate.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else if (validator.decorationFactory.AllControlDecorationsHide()) {

					try {

						mainContent.after = Integer.parseInt(customRange1.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						
						mainContent.before = Integer.parseInt(customRange2.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}

					if (after > before)
						RetroalimentationUtils.showErrorShellMessage("El rango de fecha no es correcto.");
					else {					
    
						if (!mainContent.getFindByDate()) {
							addDateImage = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));
							addDate.setImage(addDateImage);	
							update();
							mainContent.setFindByDate(true);							

						} else {
							addDateImage = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
							addDate.setImage(addDateImage);
							update();
							mainContent.setFindByDate(false);
						}

						findWithFilters();

					}
				} else
					RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		allLibraryCheck.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {

					Control[] temp = locationCompo.getChildren();
					for (int i = 1; i < temp.length; i++)
						temp[i].dispose();

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (allLibraryCheck.getSelection()) {
					selectedLibraries.clear();
					selectedLibraries.addAll(libraries);
					printLibrary(true);
					l10n();

				} else {
					selectedLibraries.clear();
					printLibrary(false);
					l10n();

				}

				findWithFilters();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		if (libraries.size() > 6) {

			filterExpandBar.addExpandListener(new ExpandListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void itemExpanded(ExpandEvent arg0) {

					materialTypeExpItem.setExpanded(false);
					locationExpItem.setExpanded(false);
					dateExpItem.setExpanded(false);

					((ExpandItem) arg0.item).setExpanded(true);

				}

				@Override
				public void itemCollapsed(ExpandEvent arg0) {
				}
			});
		}

		if (libraries.size() < 6) {
			materialTypeExpItem.setExpanded(true);
			locationExpItem.setExpanded(true);
			dateExpItem.setExpanded(true);
		} else {
			materialTypeExpItem.setExpanded(true);
			locationExpItem.setExpanded(false);
			dateExpItem.setExpanded(false);
		}

		l10n();
		return result;
	}

	public void cleanFilters() {

		bookCheck.setSelection(false);
		referenceWorksCheck.setSelection(false);
		serialPublicationsCheck.setSelection(false);
		thesisCheck.setSelection(false);

		customRange1.setText("");
		customRange2.setText("");

	}

	@Override
	public String getID() {
		return MAIN_MENU_CONTROL;
	}

	@Override
	public void l10n() {

		filtersName.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTERS)));
		materialTypeExpItem.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_MATERIAL_TYPE)));
		bookCheck.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_MATERIAL_BOOKS)));
		referenceWorksCheck.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_MATERIAL_REFERENCE_WORKS)));
		serialPublicationsCheck.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_MATERIAL_SERIAL_PUBLICATION)));
		thesisCheck.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_MATERIAL_THESIS)));
		nonBookMaterialsCheck.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_MATERIAL_NON_BOOK_MATERIAL)));

		locationExpItem.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_LOCATION));
		allLibraryCheck.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_LOCATION_ALL_LIBRARY));

		dateExpItem.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_PUBLICATION_DATE)));
		sinceLabel.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_PUBLICATION_DATE_SINCE)));
		untilLabel.setText((MessageUtil.unescape(AbosMessages.get().LABEL_FILTER_PUBLICATION_DATE_UNTIL)));

		materialTypeExpItem.setExpanded(!materialTypeExpItem.getExpanded());
		materialTypeExpItem.setExpanded(!materialTypeExpItem.getExpanded());

		locationExpItem.setExpanded(!locationExpItem.getExpanded());
		locationExpItem.setExpanded(!locationExpItem.getExpanded());

		dateExpItem.setExpanded(!dateExpItem.getExpanded());
		dateExpItem.setExpanded(!dateExpItem.getExpanded());

		update();
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	private void printLibrary(boolean select) {

		lastLibrary = allLibraryCheck;

		for (int i = 0; i < libraries.size(); i++) {

			currentLibrary = libraries.get(i);
			libraryCheck = new Button(locationCompo, SWT.CHECK);
			libraryCheck.setText(currentLibrary.getLibraryName());
			libraryCheck.setSelection(select);
			FormDatas.attach(libraryCheck).atTopTo(lastLibrary).atLeft(10);

			libraryCheck.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent event) {

					if (((Button) event.widget).getSelection())
						for (int j = 0; j < libraries.size(); j++) {
							if (((Button) event.widget).getText() == libraries.get(j).getLibraryName()) {
								selectedLibraries.add(libraries.get(j));
								break;
							}

						}
					else
						for (int j = 0; j < selectedLibraries.size(); j++)
							if (((Button) event.widget).getText() == selectedLibraries.get(j).getLibraryName()) {
								selectedLibraries.remove(j);
								break;
							}

					findWithFilters();
					update();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			lastLibrary = libraryCheck;
		}

	}

	private void findWithFilters() {

		if (!mainContent.getAdvancesOptionsVisibility())
			if (mainContent.simpleSearch)
				mainContent.find();
			else
				mainContent.findAdvance();

	}

	public List<Option> getFilterOptions() {
		return filterOptions;
	}

	public void setFilterOptions(List<Option> filterOptions) {
		this.filterOptions = filterOptions;
	}

	private void addOption(String field, String term, int group) {

		if (filterOptions.isEmpty()) {
			option = new Option(field, term, group);
			filterOptions.add(option);
		} else {
			optionOR = new OptionOR(field, term, group);
			filterOptions.add(optionOR);
		}

	}

	private void removeOption(String term) {

		for (int i = 0; i < filterOptions.size(); i++)
			if (filterOptions.get(i).getTerm() == term)
				filterOptions.remove(i);

		if (!filterOptions.isEmpty() && filterOptions.get(0) instanceof OptionOR) {
			option = new Option(filterOptions.get(0).getField(), filterOptions.get(0).getTerm(), recordTypeGroup);
			filterOptions.set(0, option);
		}

	}

	public List<Library> getSelectedLibraries() {
		return selectedLibraries;
	}

	public void findLibrary() {
		selectedLibraries.clear();
		selectedLibraries.addAll(libraries);

		Control[] temp = locationCompo.getChildren();
		try {
			for (int i = 0; i < temp.length; i++)
				((Button) temp[i]).setSelection(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cleanYears() {
		customRange1.setText("");
		customRange2.setText("");
	}

	public void cleanMaterialType() {
		Control[] materials = materialTypeCompo.getChildren();

		for (int i = 0; i < materials.length; i++)
			((Button) materials[i]).setSelection(false);

	}

}
