package cu.uci.abcd.opac.contribution;

//import java.util.HashMap;
//import java.util.Map;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.opac.listener.FilterMenuListener;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IContributorService;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widget.advanced.query.ColorType;
import cu.uci.abos.widget.advanced.query.QueryComponent;

//import cu.uci.abos.util.api.IServiceProvider;

public class MainContent implements IContributor {

    // public Text searchBox;
    public Composite result;
    public Composite actionBar;
    // public Composite resultList;
    public Composite action;

    // private Map<String, Control> controls;

    public MainContent() {
	// this.controls = new HashMap<String, Control>();
    }

    /*
     * public Text getSearchBox() { return searchBox; }
     */
    public void update() {
	result.layout(true, true);
	action.layout(true, true);
	actionBar.layout(true, true);
	// resultList.layout(true,true);
    }

    Button createMenuButton(Composite parent, final IContributorService pageService, final String pageId, String buttonName) {

	parent.setLayout(new FormLayout());

	Button result = new Button(parent, SWT.PUSH);

	result.setText(buttonName);
	// result.setLayoutData(data1);
	result.addSelectionListener(new SelectionAdapter() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void widgetSelected(SelectionEvent evt) {
		pageService.selectContributor(pageId);
	    }
	});

	return result;
    }

    @Override
    public Control createUIControl(final Composite parent) {

	result = new Composite(parent, SWT.V_SCROLL);
	result.setData(RWT.CUSTOM_VARIANT, "workspace_content");
	result.setLayout(new FormLayout());

	FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);

	/*
	 * 
	 * searchBox=new Text(result, SWT.None); Integer
	 * width=(Integer)RWT.getApplicationContext().getAttribute("width");
	 * Integer halfPerCent=width*50/100; Integer searcBoxWidth=500; Integer
	 * half=halfPerCent-(searcBoxWidth/2);
	 */

	final Combo filterOne = new Combo(result, SWT.NORMAL);

	filterOne.setItems(new String[] { "Palabra Clave", "Titulo", "Autor", "Tema", "ISBN", "Series", "Signatura Topografica" });

	filterOne.setText("Palabra Clave");

	FormDatas.attach(filterOne).atTop(5).atLeft(50);

	/*
	 * 
	 * final Button searchButton=new Button(result, SWT.NONE);
	 * searchButton.setText("Buscar");
	 * FormDatas.attach(searchButton).atTop(5);
	 * searchButton.addListener(SWT.Selection, new FilterMenuListener());
	 */

	final Button advanceOptionBtn = new Button(result, SWT.CURSOR_SIZEALL);
	// advanceOptionBtn.setText("Avanzado");
	FormDatas.attach(advanceOptionBtn).atTop(5).atRight(50);

	// FormDatas.attach(searchBox).withHeight(16).withWidth(300).atTop(5).atLeftTo(filterOne,
	// 30).atRightTo(searchButton, 20);

	action = new Composite(result, SWT.NORMAL);
	action.setLayout(new FormLayout());
	action.setBackground(result.getBackground());
	FormDatas.attach(action).atLeft(0).atRight(0).atBottom(0);

	Button closeAdvance = new Button(action, SWT.PUSH);
	closeAdvance.setText("Cerrar");
	FormDatas.attach(closeAdvance).atTop(0).atBottom(0).atRight(20);

	Button newFindAdvance = new Button(action, SWT.PUSH);
	newFindAdvance.setText("Nueva Busqueda");
	FormDatas.attach(newFindAdvance).atTop(0).atBottom(0).atRightTo(closeAdvance, 20);

	Button findAdvance = new Button(action, SWT.PUSH);
	findAdvance.setText("Buscar");
	FormDatas.attach(findAdvance).atTop(0).atBottom(0).atRightTo(newFindAdvance, 20);

	final Text texta = new Text(result, SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL);
	FormDatas.attach(texta).withHeight(16).atTop(5).atLeftTo(filterOne, 20).atRightTo(advanceOptionBtn, 20);
	texta.addListener(SWT.Selection, new FilterMenuListener());

	final Group AdvancesOptions = new Group(result, SWT.NONE);
	AdvancesOptions.setLayout(new FormLayout());

	AdvancesOptions.setText("Opciones Avanzadas");

	FormDatas.attach(AdvancesOptions).atTop(0).atLeft(0).atRight(0).atBottomTo(action);

	Combo field1 = new Combo(AdvancesOptions, SWT.NORMAL);
	field1.setItems(new String[] { "Título", "Autor", "Otros" });
	field1.setText("Palabra clave");
	FormDatas.attach(field1).atTopTo(AdvancesOptions).atLeftTo(AdvancesOptions).withWidth(172);

	Text txtfield1 = new Text(AdvancesOptions, SWT.NORMAL);
	FormDatas.attach(txtfield1).atTopTo(AdvancesOptions, 0).atLeftTo(field1, 10).withWidth(150);

	Combo andOr1 = new Combo(AdvancesOptions, SWT.NORMAL);
	andOr1.setItems(new String[] { "AND", "OR", "NOT" });
	andOr1.setText("AND");
	FormDatas.attach(andOr1).atTopTo(AdvancesOptions).atLeftTo(txtfield1, 10);

	Combo field2 = new Combo(AdvancesOptions, SWT.NORMAL);
	field2.setItems(new String[] { "Título", "Autor", "Otros" });
	field2.setText("Palabra clave");
	FormDatas.attach(field2).atTopTo(field1, 10).atLeftTo(AdvancesOptions).withWidth(172);

	Text txtfield2 = new Text(AdvancesOptions, SWT.NORMAL);
	FormDatas.attach(txtfield2).atTopTo(field1, 10).atLeftTo(field2, 10).withWidth(150);

	Combo andOr2 = new Combo(AdvancesOptions, SWT.NORMAL);
	andOr2.setItems(new String[] { "AND", "OR", "NOT" });
	andOr2.setText("AND");
	FormDatas.attach(andOr2).atTopTo(field1, 10).atLeftTo(txtfield2, 10);

	QueryComponent.resetComponents();

	String[] values = { "autor", "registro" };

	QueryComponent abcd = new QueryComponent(AdvancesOptions, SWT.NORMAL, values, ColorType.Gray);

	FormDatas.attach(abcd).atTopTo(field2, 10).atLeftTo(AdvancesOptions);

	/*
	 * 
	 * 
	 * Label publicationYear = new Label(AdvancesOptions, SWT.NORMAL);
	 * publicationYear.setText("Año de Publicación");
	 * FormDatas.attach(publicationYear).atTopTo(AdvancesOptions, 5);
	 * 
	 * Text txtannoPubliaccion = new Text(AdvancesOptions, SWT.NORMAL);
	 * FormDatas.attach(txtannoPubliaccion).atTopTo(publicationYear,
	 * 5).withWidth(150);
	 * 
	 * 
	 * 
	 * Combo concat1 = new Combo(AdvancesOptions, SWT.NORMAL);
	 * concat1.setItems(new String [] {"and", "or", "no"});
	 * concat1.setText("and");
	 * FormDatas.attach(concat1).atTopTo(publicationYear,
	 * 5).atLeftTo(txtannoPubliaccion, 10).withWidth(80);
	 * 
	 * 
	 * Label tipoRegistro = new Label(AdvancesOptions, SWT.NORMAL);
	 * tipoRegistro.setText("Tipo de Registro");
	 * FormDatas.attach(tipoRegistro).atTopTo(AdvancesOptions,
	 * 5).atLeftTo(concat1, 10);
	 * 
	 * Text txtTipoRegistro = new Text(AdvancesOptions, SWT.NORMAL);
	 * FormDatas.attach(txtTipoRegistro).atTopTo(tipoRegistro,
	 * 5).withWidth(150).atLeftTo(concat1, 10);
	 * 
	 * 
	 * Combo concat2 = new Combo(AdvancesOptions, SWT.NORMAL);
	 * concat2.setItems(new String [] {"and", "or", "no"});
	 * concat2.setText("and");
	 * FormDatas.attach(concat2).atTopTo(tipoRegistro,
	 * 5).atLeftTo(txtTipoRegistro, 10).withWidth(80);
	 * 
	 * 
	 * Label ubicacionDispon = new Label(AdvancesOptions, SWT.NORMAL);
	 * ubicacionDispon.setText("Ubicación y Disponibilidad:");
	 * FormDatas.attach(ubicacionDispon).atTopTo(txtannoPubliaccion, 5);
	 * 
	 * Combo comUbicacionDisp = new Combo(AdvancesOptions, SWT.NORMAL);
	 * comUbicacionDisp.setItems(new String [] {"123", "456", "789"});
	 * FormDatas.attach(comUbicacionDisp).atTopTo(ubicacionDispon,
	 * 5).withWidth(170);
	 * 
	 * 
	 * Combo concat3 = new Combo(AdvancesOptions, SWT.NORMAL);
	 * concat3.setItems(new String [] {"and", "or", "no"});
	 * concat3.setText("and");
	 * FormDatas.attach(concat3).atTopTo(ubicacionDispon,
	 * 5).atLeftTo(comUbicacionDisp, 10).withWidth(80);
	 * 
	 * 
	 * 
	 * Label tipodeOrdenamiento = new Label(AdvancesOptions, SWT.NORMAL);
	 * tipodeOrdenamiento.setText("Tipo de Ordenamiento");
	 * FormDatas.attach(tipodeOrdenamiento).atTopTo(txtannoPubliaccion,
	 * 5).atLeftTo(concat3, 10);
	 * 
	 * Combo comTipodeOrden = new Combo(AdvancesOptions, SWT.NORMAL);
	 * comTipodeOrden.setItems(new String [] {"Por Fecha de Publicacion",
	 * "Por Nombre del Autor", "Por Nombre de Material"});
	 * FormDatas.attach(comTipodeOrden).atTopTo(tipodeOrdenamiento,
	 * 5).atLeftTo(concat3, 10).withWidth(170);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ///////....Sale si se seleccionan mas opciones....////
	 * 
	 * 
	 * 
	 * final Button moreOptionBtn = new Button(AdvancesOptions,
	 * SWT.CAP_SQUARE); moreOptionBtn.setText("Mas opciones");
	 * FormDatas.attach(moreOptionBtn).atBottom(30).atLeft(200);
	 * 
	 * 
	 * 
	 * 
	 * final Label tipoDeAudiencia = new Label(AdvancesOptions, SWT.NORMAL);
	 * tipoDeAudiencia.setText("Tipo de Audiencia");
	 * FormDatas.attach(tipoDeAudiencia).atTopTo(txtTipoRegistro,
	 * 10).atLeftTo(comTipodeOrden, 10);
	 * 
	 * final Combo comTipodeAudiencia = new Combo(AdvancesOptions,
	 * SWT.NORMAL); comTipodeAudiencia.setItems(new String []
	 * {"Por Fecha de Publicacion", "Por Nombre del Autor",
	 * "Por Nombre de Material"});
	 * FormDatas.attach(comTipodeAudiencia).atTopTo(tipoDeAudiencia,
	 * 5).atLeftTo(comTipodeOrden, 10).withWidth(130);
	 * 
	 * 
	 * final Label tipoDeContenido = new Label(AdvancesOptions, SWT.NORMAL);
	 * tipoDeContenido.setText("Tipo de Contenido");
	 * FormDatas.attach(tipoDeContenido).atTopTo(comUbicacionDisp,
	 * 10).atLeftTo(comTipodeAudiencia, 10);
	 * 
	 * final Combo comTipodeContenido = new Combo(AdvancesOptions,
	 * SWT.NORMAL); comTipodeContenido.setItems(new String []
	 * {"Por Fecha de Publicacion", "Por Nombre del Autor",
	 * "Por Nombre de Material"});
	 * FormDatas.attach(comTipodeContenido).atTopTo(tipoDeContenido,
	 * 5).atLeftTo(comTipodeAudiencia, 10).withWidth(130);
	 */

	// QueryComponent.children.get(0).

	/*
	 * 
	 * Label tipoDeFormato = new Label(areaMasOpciones, SWT.NORMAL);
	 * tipoDeFormato.setText("Tipo de Formato");
	 * FormDatas.attach(tipoDeFormato).atTopTo(tipoDeContenido,30);
	 * 
	 * Combo comTipodeFormato = new Combo(areaMasOpciones, SWT.NORMAL);
	 * comTipodeFormato.setItems(new String [] {"Por Fecha de Publicacion",
	 * "Por Nombre del Autor", "Por Nombre de Material"});
	 * FormDatas.attach(comTipodeFormato
	 * ).atTopTo(tipoDeContenido,25).atLeftTo
	 * (tipoDeFormato,15).withHeight(23).withWidth(210);
	 * 
	 * Label tipoDeConteniAdicio = new Label(areaMasOpciones, SWT.NORMAL);
	 * tipoDeConteniAdicio.setText("Tipo de Contenido Adicional");
	 * FormDatas.attach(tipoDeConteniAdicio).atTopTo(tipoDeFormato,30);
	 * 
	 * Combo comTipodeConteniAdicio = new Combo(areaMasOpciones,
	 * SWT.NORMAL); comTipodeConteniAdicio.setItems(new String []
	 * {"Por Fecha de Publicacion", "Por Nombre del Autor",
	 * "Por Nombre de Material"});
	 * FormDatas.attach(comTipodeConteniAdicio).atTopTo
	 * (tipoDeFormato,25).atLeftTo
	 * (tipoDeConteniAdicio,15).withHeight(23).withWidth(210);
	 */

	// ////////Barra de Accion//////////////////

	actionBar = new Composite(result, SWT.BORDER);
	actionBar.setBackground(result.getBackground());
	actionBar.setLayout(new FormLayout());

	FormDatas.attach(actionBar).atLeft(0).atRight(0).atTopTo(texta, 5).withHeight(25);

	final Label resultCont = new Label(actionBar, SWT.BACKGROUND);
	resultCont.setText(3 + " Resultado(s) encontrados.");
	resultCont.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
	FormDatas.attach(resultCont).atTop(5).atLeft(5);

	final Button allSelection = new Button(actionBar, SWT.Activate);
	allSelection.setText("Seleccionar Todo");
	FormDatas.attach(allSelection).atTop(5).atLeftTo(resultCont, 10).withHeight(15).withWidth(115);

	final Button allDesSelection = new Button(actionBar, SWT.SMOOTH);
	allDesSelection.setText("Deseleccionar Todo");
	FormDatas.attach(allDesSelection).atTop(5).atLeftTo(allSelection, 5).withHeight(15).withWidth(130);

	final Combo order = new Combo(actionBar, SWT.NONE);
	order.setText("Relevancia");
	order.setItems(new String[] { "Relevancia", "Autor", "Titulo" });
	FormDatas.attach(order).atTop(3).atRight(5).withHeight(18);

	// ////////Resultadosssss//////////////////

	/*
	 * 
	 * Composite a = ResultComponent(result, 1,
	 * "Ese Sol del Mundo Moral","Hard Davalos, Armando",
	 * "La Habana, 1988, Editorial Gente Nueva"
	 * ,"2005"," Copias disponibles: Biblioteca de la UCI (10)"
	 * ,"","Es un buen libro",actionBar);
	 * 
	 * 
	 * Composite b = ResultComponent(result, 2,
	 * "Aldabonazos","Vitier, Cintio",
	 * "La Habana, 1988, Editorial Gente Nueva"
	 * ,"2005"," Copias disponibles: Biblioteca de la UCI (10)"
	 * ,"","Muy buen libro",a);
	 * 
	 * 
	 * 
	 * Composite c = ResultComponent(result, 3,
	 * "Marketing World","Stringer, Jhon",
	 * "La Habana, 1988, Editorial Gente Nueva"
	 * ,"2005"," Copias disponibles: Biblioteca de la UCI (10)"
	 * ,"","Good book",b);
	 * 
	 * 
	 * 
	 * c.getBackground();
	 */

	/*
	 * 
	 * ResultComponent a = new ResultComponent(result, SWT.NORMAL, 1,
	 * "Ese Sol del Mundo Moral","Hard Davalos, Armando",
	 * "La Habana, 1988, Editorial Gente Nueva"
	 * ,"2005"," Copias disponibles: Biblioteca de la UCI (10)"
	 * ,"","Es un buen libro", actionBar);
	 * 
	 * 
	 * 
	 * FormDatas.attach(a).atTopTo(actionBar);
	 * 
	 * /*
	 * 
	 * ResultComponent b = new ResultComponent(result, SWT.NORMAL, 1,
	 * "Ese Sol del Mundo Moral","Hard Davalos, Armando",
	 * "La Habana, 1988, Editorial Gente Nueva"
	 * ,"2005"," Copias disponibles: Biblioteca de la UCI (10)"
	 * ,"","Es un buen libro", a);
	 */

	// ///// INICIALMENTE OCULTOS//////

	AdvancesOptions.setVisible(false);
	action.setVisible(false);

	/*
	 * 
	 * tipoDeAudiencia.setVisible(false);
	 * comTipodeAudiencia.setVisible(false);
	 * tipoDeContenido.setVisible(false);
	 * comTipodeContenido.setVisible(false);
	 */

	// ///LISENERRSSSS////////////////

	advanceOptionBtn.addSelectionListener(new SelectionListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void widgetSelected(SelectionEvent arg0) {

		texta.setVisible(false);
		filterOne.setVisible(false);
		advanceOptionBtn.setVisible(false);
		AdvancesOptions.setVisible(true);
		action.setVisible(true);

	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
		// do nothing
	    }
	});

	/*
	 * moreOptionBtn.addSelectionListener(new SelectionListener() { private
	 * static final long serialVersionUID = 1L;
	 * 
	 * @Override public void widgetSelected(SelectionEvent arg0) {
	 * 
	 * 
	 * tipoDeAudiencia.setVisible(true);
	 * comTipodeAudiencia.setVisible(true);
	 * tipoDeContenido.setVisible(true);
	 * comTipodeContenido.setVisible(true);
	 * 
	 * 
	 * 
	 * AdvancesOptions.setVisible(false); action.setVisible(false);
	 * searchButton.setVisible(true); searchBox.setVisible(true);
	 * filterOne.setVisible(true); advanceOptionBtn.setVisible(true);
	 * 
	 * 
	 * 
	 * }
	 * 
	 * @Override public void widgetDefaultSelected(SelectionEvent arg0) {
	 * //do nothing } });
	 */

	findAdvance.addSelectionListener(new SelectionListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void widgetSelected(SelectionEvent arg0) {

		AdvancesOptions.setVisible(false);
		action.setVisible(false);
		QueryComponent.resetComponents();
		texta.setVisible(true);
		filterOne.setVisible(true);
		advanceOptionBtn.setVisible(true);

	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
		// do nothing
	    }
	});

	closeAdvance.addSelectionListener(new SelectionListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void widgetSelected(SelectionEvent arg0) {

		AdvancesOptions.setVisible(false);
		action.setVisible(false);
		texta.setVisible(true);
		filterOne.setVisible(true);
		advanceOptionBtn.setVisible(true);

	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
		// do nothing
	    }
	});

	return result;

    }

    // ///// Componente

    /*
     * public Composite ResultComponent(final Composite parent, int resultNum,
     * String name, String autor, String publicationDetails, String
     * publicationDate, String disponibility, String tags, String description,
     * Composite anterior){ /* final IContributorService pageService =
     * serviceProvider .get(IContributorService.class);
     * 
     * 
     * 
     * resultList = new Composite(parent, SWT.NONE);
     * resultList.setBackground(parent.getBackground());
     * resultList.setLayout(new FormLayout());
     * 
     * FormDatas.attach(resultList).atLeft(0).atRight(0).atTopTo(anterior,
     * 5).withHeight(120);
     * 
     * 
     * final Button selectionChek = new Button(resultList, SWT.CHECK);
     * FormDatas.attach(selectionChek).atTop(0).atLeft(0);
     * 
     * 
     * final Label number = new Label(resultList, SWT.NORMAL);
     * number.setText(resultNum + ".");
     * FormDatas.attach(number).atTop(0).atLeftTo(selectionChek, 5);
     * 
     * 
     * final Label bookName = new Label(resultList, SWT.NORMAL);
     * bookName.setText(name); bookName.setFont(new Font( parent.getDisplay(),
     * "Arial", 10, SWT.BOLD ));
     * FormDatas.attach(bookName).atTop(0).atLeftTo(number, 10);
     * 
     * 
     * final Label by = new Label(resultList, SWT.NORMAL); by.setText("por");
     * FormDatas.attach(by).atTop(0).atLeftTo(bookName, 5);
     * 
     * 
     * 
     * final Label bookAutor = new Label(resultList, SWT.NORMAL);
     * bookAutor.setText(autor); bookAutor.setFont(new Font(
     * parent.getDisplay(), "Arial", 10, SWT.BOLD ));
     * FormDatas.attach(bookAutor).atTop(0).atLeftTo(by, 5);
     * 
     * 
     * final Label publicationLabel = new Label(resultList, SWT.NORMAL);
     * publicationLabel.setText("Publicacion:"); publicationLabel.setFont(new
     * Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
     * FormDatas.attach(publicationLabel).atTopTo(bookName, 2).atLeftTo(number,
     * 10);
     * 
     * final Label publicationContent = new Label(resultList, SWT.NORMAL);
     * publicationContent.setText(publicationDetails);
     * FormDatas.attach(publicationContent).atTopTo(bookName,
     * 2).atLeftTo(publicationLabel, 5);
     * 
     * 
     * 
     * final Label publicationDateLabel = new Label(resultList, SWT.NORMAL);
     * publicationDateLabel.setText("Fecha:"); publicationDateLabel.setFont(new
     * Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
     * FormDatas.attach(publicationDateLabel).atTopTo(publicationLabel,
     * 2).atLeftTo(number, 10);
     * 
     * final Label publicationDateContent = new Label(resultList, SWT.NORMAL);
     * publicationDateContent.setText(publicationDate);
     * FormDatas.attach(publicationDateContent).atTopTo(publicationLabel,
     * 2).atLeftTo(publicationDateLabel, 5);
     * 
     * 
     * final Label disponibilityLabel = new Label(resultList, SWT.NORMAL);
     * disponibilityLabel.setText("Disponibilidad:");
     * disponibilityLabel.setFont(new Font( parent.getDisplay(), "Arial", 10,
     * SWT.BOLD ));
     * FormDatas.attach(disponibilityLabel).atTopTo(publicationDateLabel,
     * 2).atLeftTo(number, 10);
     * 
     * final Label disponibilityContent = new Label(resultList, SWT.NORMAL);
     * disponibilityContent.setText(disponibility);
     * FormDatas.attach(disponibilityContent).atTopTo(publicationDateLabel,
     * 2).atLeftTo(disponibilityLabel, 5);
     * 
     * final Label tagsLabel = new Label(resultList, SWT.NORMAL);
     * tagsLabel.setText("Etiquetas:"); tagsLabel.setFont(new Font(
     * parent.getDisplay(), "Arial", 10, SWT.BOLD ));
     * FormDatas.attach(tagsLabel).atTopTo(disponibilityLabel,
     * 2).atLeftTo(number, 10);
     * 
     * 
     * 
     * final Label tagsContent = new Label(resultList, SWT.NORMAL);
     * tagsContent.setText(tags);
     * FormDatas.attach(tagsContent).atTopTo(disponibilityLabel,
     * 2).atLeftTo(tagsLabel, 5);
     * 
     * 
     * final Label descriptionLabel = new Label(resultList, SWT.NORMAL);
     * descriptionLabel.setText("Descripcion:"); descriptionLabel.setFont(new
     * Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
     * FormDatas.attach(descriptionLabel).atTopTo(tagsLabel, 2).atLeftTo(number,
     * 10);
     * 
     * final Label descriptionContent = new Label(resultList, SWT.NORMAL);
     * descriptionContent.setText(description);
     * FormDatas.attach(descriptionContent).atTopTo(tagsLabel,
     * 2).atLeftTo(descriptionLabel, 5);
     * 
     * 
     * /*
     * 
     * Button reservar=createMenuButton(resultList, pageService,
     * "CirculationID", "Reservar");
     * 
     * 
     * 
     * Label reservar = new Label( parent, SWT.NONE ); reservar.setData(
     * RWT.MARKUP_ENABLED, Boolean.TRUE ); String src =
     * RWT.getResourceManager().getLocation( "add.jpeg" ); reservar.setText(
     * "<img width='24' height='24' src='" + src + "'/> !" );
     * 
     * 
     * 
     * final Button reservar = new Button(resultList, SWT.SAVE);
     * reservar.setText("Reservar");
     * FormDatas.attach(reservar).atTopTo(descriptionLabel, 2).atLeftTo(number,
     * 10).withHeight(15).withWidth(60);
     * 
     * final Button saveInSelectionList = new Button(resultList, SWT.SAVE);
     * saveInSelectionList.setText("Añadir a Lista");
     * FormDatas.attach(saveInSelectionList).atTopTo(descriptionLabel,
     * 2).atLeftTo(reservar, 2).withHeight(15).withWidth(90);
     * 
     * final Button addToSelection = new Button(resultList, SWT.SAVE);
     * addToSelection.setText("Añadir a Seleccion");
     * FormDatas.attach(addToSelection).atTopTo(descriptionLabel,
     * 2).atLeftTo(saveInSelectionList, 2).withHeight(15).withWidth(115);
     * 
     * final Button downloads = new Button(resultList, SWT.SAVE);
     * downloads.setText("Descargar");
     * FormDatas.attach(downloads).atTopTo(descriptionLabel,
     * 2).atLeftTo(addToSelection, 2).withHeight(15).withWidth(70);
     * 
     * final Button materialRecommend = new Button(resultList, SWT.SAVE);
     * materialRecommend.setText("Recomendar");
     * FormDatas.attach(materialRecommend).atTopTo(descriptionLabel,
     * 2).atLeftTo(downloads, 2).withHeight(15).withWidth(85);
     * 
     * final Button ponderarMaterial = new Button(resultList, SWT.SAVE);
     * ponderarMaterial.setText("Ponderar");
     * FormDatas.attach(ponderarMaterial).atTopTo(descriptionLabel,
     * 2).atLeftTo(materialRecommend, 2).withHeight(15).withWidth(60);
     * 
     * final Button seeDescription = new Button(resultList, SWT.SAVE);
     * seeDescription.setText("Ver Descripcion");
     * FormDatas.attach(seeDescription).atTopTo(descriptionLabel,
     * 2).atLeftTo(ponderarMaterial, 2).withHeight(15).withWidth(100);
     * 
     * final Button seeDetails = new Button(resultList, SWT.SAVE);
     * seeDetails.setText("Ver Detalles");
     * FormDatas.attach(seeDetails).atTopTo(descriptionLabel,
     * 2).atLeftTo(seeDescription, 2).withHeight(15).withWidth(80);
     * 
     * 
     * 
     * final Label separador = new Label(resultList, SWT.NORMAL);
     * separador.setText
     * ("__________________________________________________________________________"
     * +
     * "__________________________________________________________________________________"
     * +
     * "__________________________________________________________________________________"
     * ); FormDatas.attach(separador).atTopTo(reservar, -5).atLeft(0);
     * 
     * 
     * 
     * return resultList;
     * 
     * }
     */
    @Override
    public String getID() {
	// TODO Auto-generated method stub
	return "MainContentID";
    }

    @Override
    public void l10n() {
	// TODO Auto-generated method stub

    }

    @Override
    public String contributorName() {
	// TODO Auto-generated method stub
	return "Inicio";
    }

    @Override
    public boolean canClose() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void setViewController(IViewController controller) {
	// TODO Auto-generated method stub

    }

}
