package cu.uci.abcd.opac.contribution;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import cu.uci.abos.util.api.FormDatas;


public class ResultComponent extends Composite {

	 
	public Composite resultList; 
	

	public ResultComponent(Composite parent, int style, int resultNum, String name, String autor, String publicationDetails, 
			String publicationDate, String disponibility, String tags, String description, Composite anterior) {
		super(parent,style);
		// TODO Auto-generated constructor stub
	
				
		resultList = new Composite(parent, SWT.NONE);
		resultList.setBackground(parent.getBackground());
		resultList.setLayout(new FormLayout());

		//FormDatas.attach(resultList).atLeft(0).atRight(0).atTopTo(anterior, 5).withHeight(120);
 
		

		final Button selectionChek = new Button(resultList, SWT.CHECK);		
		FormDatas.attach(selectionChek).atTop(0).atLeft(0);
		
				
		final Label number = new Label(resultList, SWT.NORMAL);
		number.setText(resultNum + ".");
		FormDatas.attach(number).atTop(0).atLeftTo(selectionChek, 5);
				
		
		final Label bookName = new Label(resultList, SWT.NORMAL);
		bookName.setText(name);
		bookName.setFont(new Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
		FormDatas.attach(bookName).atTop(0).atLeftTo(number, 10);
		
		
		final Label by = new Label(resultList, SWT.NORMAL);
		by.setText("por");		
		FormDatas.attach(by).atTop(0).atLeftTo(bookName, 5);
		
		
		
		final Label bookAutor = new Label(resultList, SWT.NORMAL);
		bookAutor.setText(autor);
		bookAutor.setFont(new Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
		FormDatas.attach(bookAutor).atTop(0).atLeftTo(by, 5);
		
		
		final Label publicationLabel = new Label(resultList, SWT.NORMAL);
		publicationLabel.setText("Publicacion:");
		publicationLabel.setFont(new Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
		FormDatas.attach(publicationLabel).atTopTo(bookName, 2).atLeftTo(number, 10);
		
		final Label publicationContent = new Label(resultList, SWT.NORMAL);
		publicationContent.setText(publicationDetails);
		FormDatas.attach(publicationContent).atTopTo(bookName, 2).atLeftTo(publicationLabel, 5);
		
		
		
		final Label publicationDateLabel = new Label(resultList, SWT.NORMAL);
		publicationDateLabel.setText("Fecha:");
		publicationDateLabel.setFont(new Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
		FormDatas.attach(publicationDateLabel).atTopTo(publicationLabel, 2).atLeftTo(number, 10);
		
		final Label publicationDateContent = new Label(resultList, SWT.NORMAL);
		publicationDateContent.setText(publicationDate);
		FormDatas.attach(publicationDateContent).atTopTo(publicationLabel, 2).atLeftTo(publicationDateLabel, 5);
		
		
		final Label disponibilityLabel = new Label(resultList, SWT.NORMAL);
		disponibilityLabel.setText("Disponibilidad:");
		disponibilityLabel.setFont(new Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
		FormDatas.attach(disponibilityLabel).atTopTo(publicationDateLabel, 2).atLeftTo(number, 10);
		
		final Label disponibilityContent = new Label(resultList, SWT.NORMAL);
		disponibilityContent.setText(disponibility);
		FormDatas.attach(disponibilityContent).atTopTo(publicationDateLabel, 2).atLeftTo(disponibilityLabel, 5);

		final Label tagsLabel = new Label(resultList, SWT.NORMAL);
		tagsLabel.setText("Etiquetas:");
		tagsLabel.setFont(new Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
		FormDatas.attach(tagsLabel).atTopTo(disponibilityLabel, 2).atLeftTo(number, 10);
 
			
		
		final Label tagsContent = new Label(resultList, SWT.NORMAL);
		tagsContent.setText(tags);
		FormDatas.attach(tagsContent).atTopTo(disponibilityLabel, 2).atLeftTo(tagsLabel, 5);
				
		
		final Label descriptionLabel = new Label(resultList, SWT.NORMAL);
		descriptionLabel.setText("Descripcion:");
		descriptionLabel.setFont(new Font( parent.getDisplay(), "Arial", 10, SWT.BOLD ));
		FormDatas.attach(descriptionLabel).atTopTo(tagsLabel, 2).atLeftTo(number, 10);

		final Label descriptionContent = new Label(resultList, SWT.NORMAL);
		descriptionContent.setText(description);
		FormDatas.attach(descriptionContent).atTopTo(tagsLabel, 2).atLeftTo(descriptionLabel, 5);
		
		
		/*
		 		  
		  Button reservar=createMenuButton(resultList, pageService, 
			"CirculationID", "Reservar");	
								
		*/   
		
		/*Label reservar = new Label( parent, SWT.NONE );
		reservar.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
		String src = RWT.getResourceManager().getLocation( "add.jpeg" );
		reservar.setText( "<img width='24' height='24' src='" + src + "'/> !" );*/
		
		final Button reservar = new Button(resultList, SWT.SAVE);
		reservar.setText("Reservar");
		FormDatas.attach(reservar).atTopTo(descriptionLabel, 2).atLeftTo(number, 10).withHeight(15).withWidth(60);

		final Button saveInSelectionList = new Button(resultList, SWT.SAVE);
		saveInSelectionList.setText("Añadir a Lista");
		FormDatas.attach(saveInSelectionList).atTopTo(descriptionLabel, 2).atLeftTo(reservar, 2).withHeight(15).withWidth(90);

		final Button addToSelection = new Button(resultList, SWT.SAVE);
		addToSelection.setText("Añadir a Seleccion");
		FormDatas.attach(addToSelection).atTopTo(descriptionLabel, 2).atLeftTo(saveInSelectionList, 2).withHeight(15).withWidth(115);

		final Button downloads = new Button(resultList, SWT.SAVE);
		downloads.setText("Descargar");
		FormDatas.attach(downloads).atTopTo(descriptionLabel, 2).atLeftTo(addToSelection, 2).withHeight(15).withWidth(70);
 
		final Button materialRecommend = new Button(resultList, SWT.SAVE);
		materialRecommend.setText("Recomendar");
		FormDatas.attach(materialRecommend).atTopTo(descriptionLabel, 2).atLeftTo(downloads, 2).withHeight(15).withWidth(85);

		final Button ponderarMaterial = new Button(resultList, SWT.SAVE);
		ponderarMaterial.setText("Ponderar");
		FormDatas.attach(ponderarMaterial).atTopTo(descriptionLabel, 2).atLeftTo(materialRecommend, 2).withHeight(15).withWidth(60);
		
		final Button seeDescription = new Button(resultList, SWT.SAVE);
		seeDescription.setText("Ver Descripcion");
		FormDatas.attach(seeDescription).atTopTo(descriptionLabel, 2).atLeftTo(ponderarMaterial, 2).withHeight(15).withWidth(100);
		
		final Button seeDetails = new Button(resultList, SWT.SAVE);
		seeDetails.setText("Ver Detalles");
		FormDatas.attach(seeDetails).atTopTo(descriptionLabel, 2).atLeftTo(seeDescription, 2).withHeight(15).withWidth(80);
		
		
		 
		final Label separador = new Label(resultList, SWT.NORMAL);
		separador.setText("__________________________________________________________________________"
				+ "__________________________________________________________________________________"
				+ "__________________________________________________________________________________");
		FormDatas.attach(separador).atTopTo(reservar, -5).atLeft(0);
		
		
		
	}
	
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

