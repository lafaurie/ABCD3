package cu.uci.abos.demo;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.responsive.*;


public class Administracion implements EntryPoint {

	
	
	

	/**
	 * @wbp.parser.entryPoint
	 */
	
	@Override
	public int createUI() 
	{
		Display display = new Display();
		Shell shell = new Shell(display, SWT.NONE);
		
		
		RowLayout layout=new RowLayout(SWT.BORDER );		
		layout.wrap=true;
		layout.marginLeft=0;
		layout.marginTop=0;
		
		layout.marginBottom=0;
		
		
		shell.setLayout(layout);
		
		
		shell.setFullScreen(true);

		Composite parent = new Composite(shell, SWT.NONE);
		
		
		parent.setLayout(new ScaleLayout(new ScaleProvider(parent)));
		
		
		configure(parent);
		   
		Composite menu=new Composite(shell,SWT.None);
		menu.setData(RWT.CUSTOM_VARIANT,"customComposite");	
		
		
		menu.setLayout(new ScaleLayout(new ScaleProvider(shell)));
		
		createMenu(menu);
		
		
		Composite principal=new Composite(shell, SWT.None);
		principal.setData(RWT.CUSTOM_VARIANT,"princpalComposite");
		
		principal.setLayout(new RowLayout(SWT.VERTICAL));		
	
	
		 RowData data1=new RowData(244, 465);
		 
		
		 
		 
		 Label label=new Label(principal, SWT.NORMAL);		 
		 label.setLayoutData(data1);
		
		 
		 
		
		
		 
		 Composite menupie=new Composite(shell,SWT.BORDER);
		 menupie.setData(RWT.CUSTOM_VARIANT,"footerComposite");			
	     menupie.setLayout( new ScaleLayout(new ScaleProvider(shell)));  	     
		 createfooter(menupie);
		shell.layout();
		shell.open();
		
		return 0;
	}
	
	
	
	private void createfooter(Composite parent) 
	{
		String texto="Universidad de las Ciencias Informáticas. Facultad 2, Centro de Informatización de la Gestión Documental (CIGED).  2014";
		ScaleData child1 = createChild(parent,"customLabel","Label", 0,texto);
		ScaleData child2 = createChild(parent,"customLabel","Label", 0,"");
		child1.on(Scale.SUPER_WIDE).setWidth(833).setHeight(30).setMargin(50, 8, 0,0 );
		child2.on(Scale.SUPER_WIDE).setWidth(110).setHeight(20).setMargin(400, 8, 0,0 );
		
		
		
	}

	private static Color getSystemColor(int colorIndex) {
		return Display.getCurrent().getSystemColor(colorIndex);
	}
	
	
	

	
	
	
	private void createMenu(Composite parent)
	{
		String []Archivo={"Abrir","Exportar","Configuracion"};
		ScaleData child1 = createChild(parent,Archivo,"Archivo");
		
		ScaleData child8=createChild(parent,"Editar","Separator", SWT.ARROW_DOWN,"");
		
		
		ScaleData child2 = createChild(parent,"Editar","Button", SWT.ARROW_DOWN,"");
		
		ScaleData child9=createChild(parent,"Editar","Separator", SWT.ARROW_DOWN,"");		
		
		ScaleData child3 = createChild(parent,"Administrar","Button",SWT.ARROW_DOWN,"");
		
		ScaleData child10=createChild(parent,"Editar","Separator", SWT.ARROW_DOWN,"");		
		
		
		String []Vista={"Minima","Avanzada","Personalizar.."};
		ScaleData child4 = createChild(parent,Vista,"Vista");
		ScaleData child5 = createChild(parent,"","Text",SWT.NORMAL,"");
		
		ScaleData child6 = createChild(parent,"","Button",SWT.SEARCH,"");
		
		
		ScaleData child7 = createChild(parent);
		
		
		child1.on(Scale.SUPER_WIDE).setWidth(100).setHeight(20).setMargin(10, 6, 0,0 );
		child8.on(Scale.SUPER_WIDE).setHeight(35).setMargin(10, 0, 0,0 );
		child2.on(Scale.SUPER_WIDE).setWidth(100).setHeight(20).setMargin(10, 6, 0,0 );
		child9.on(Scale.SUPER_WIDE).setHeight(35).setMargin(10, 0, 0,0 );
		child3.on(Scale.SUPER_WIDE).setWidth(100).setHeight(20).setMargin(10, 6, 0,0 );
		child10.on(Scale.SUPER_WIDE).setHeight(35).setMargin(10, 0, 0,0 );
		child4.on(Scale.SUPER_WIDE).setWidth(85).setHeight(20).setMargin(10, 6, 0,0 );
		child5.on(Scale.SUPER_WIDE).setWidth(275).setHeight(13).setMargin(525 ,6, 0,0 );
		child6.on(Scale.SUPER_WIDE).setWidth(42).setHeight(22).setMargin(3 ,6, 0,0 );
		
		
		
	    child7.on(Scale.SUPER_WIDE).setHeight(30).setMargin(20, 6, 0, 0);
		
		
		
		
		
		
	}
	private void configure(Composite parent) 
	{ 
		
		
		
		ScaleData child1 = createChild(parent, "foo/icon.png", 297,44,"");
	    ScaleData child2 = createChild(parent, "administracion/grafica.png",329,60,"");	
	    String []Idiomas={"Español","Italiano","Ingles"};
	    ScaleData child3 = createChild(parent,Idiomas,"Idioma");
	    String []Ayuda={"Manual","Acerca de.."};
	    ScaleData child4 = createChild(parent,Ayuda,"Ayuda");	   
	    ScaleData child5 = createChild(parent, "administracion/user.png",46,46,"border:1px solid black;border-radius:4px");
	    String []User={"Perfil","Desconectarse"};
	    ScaleData child6 = createChild(parent,User,"Administrador");
	    ScaleData child7 = createChild(parent);
	     
	    new Button(parent, SWT.NORMAL).setText("Very Wide Button 2");
	    
	  
	    
		
		//
		/*ScaleData child3 = createChild(parent, "control3", SWT.COLOR_BLUE);
		ScaleData child4 = createChild(parent, "control4", SWT.COLOR_CYAN);
		ScaleData child5 = createChild(parent, "control5", SWT.COLOR_YELLOW);*/

		child1.on(Scale.SUPER_WIDE).setWidth(309).setHeight(50).setMargin(10, 8, 0,0 );
		child2.on(Scale.SUPER_WIDE).setWidth(329).setHeight(60).setMargin(5, 0, 0, 0);		
		child3.on(Scale.SUPER_WIDE).setWidth(100).setHeight(20).setMargin(250, 30, 3, 0);
		child4.on(Scale.SUPER_WIDE).setWidth(100).setHeight(20).setMargin(10, 30, 3, 0);		
	    child5.on(Scale.SUPER_WIDE).setMargin(15, 5, 3, 0);
	    child6.on(Scale.SUPER_WIDE).setWidth(140).setHeight(20).setMargin(10, 30, 3, 10);
	    child7.on(Scale.SUPER_WIDE).setWidth(10);
		
		
		

		
	}
	static ScaleData createChild(Composite parent, String[] values, String Name)
	{
		return new ScaleData(createChildControl(parent, values, Name));
	}
	
	static ScaleData createChild(Composite parent, String label,int width, int height, String properties)
	{
		return new ScaleData(createChildControlRow(parent, label, width, height, properties));
	}
	
	static ScaleData createChild(Composite parent,  String Name,String control, int Type,String mensaje)
	{
		ScaleData data = null;
		switch (control) 
		{
		
		case "Button":
			data=new ScaleData(createChildControlButton(parent, Name, Type));
			break;
		case "Text":
			data=new ScaleData(createChildControlText(parent, Name, Type));
			break;
		case "Label":
			data=new ScaleData(createChildControlLabel(parent, Name, mensaje));
			break;
		case"Separator": data=new ScaleData(createChildControlSeparator(parent));			
			break;

		default:
			break;
		}
		
		return data;
		
		
	}
	
	
	static ScaleData createChild(Composite parent)
	{
		return new ScaleData(Margin(parent));
	}
	
	
	 static Control Margin(Composite parent)
	{
         RowData data1=new RowData();
      
		
		
	    Label label1=new Label(parent,SWT.WRAP);
	    label1.setLayoutData(data1);
	    return label1;
	    
	}

	
	 
	
	 
	
	static Control createChildControl(Composite parent, String[] values, String Name) 
	{
		
        RowData data=new RowData();
        
		
		
		Combo comboDropDown = new Combo(parent, SWT.DROP_DOWN);
		
		comboDropDown.setLayoutData(data);
		
	    
	
	   
		
		comboDropDown.setText(Name);		 
		comboDropDown.setItems(values);
		return comboDropDown;
	}
	
	static Control createChildControlButton(Composite parent, String Name,int Type) 
	{
		
        RowData data=new RowData();
        
        
		
		
		Button boton=new Button(parent, Type);
		boton.setLayoutData(data);
	    
	
	   
		
		boton.setText(Name);
		return boton;
	}
	
	static Control createChildControlText(Composite parent, String Name, int Type) 
	{
		
        RowData data=new RowData();
        
		
		Text text=new Text(parent, Type);
	    text.setText(Name);
		text.setLayoutData(data);
		return text;
	}
	
	
    static Control createChildControlLabel(Composite parent,String ccs,String texto)
    {
       RowData data1=new RowData();
		
		
	    Label label1=new Label(parent,SWT.WRAP);
	    
	    label1.setLayoutData(data1);
	    
	    label1.setData( RWT.CUSTOM_VARIANT, ccs );
	    
	    //label1.setForeground(getSystemColor(SWT.COLOR_WHITE));
	    
	    label1.setText(texto);
	    
	    return label1;
	    
    	
	    
    }	
	
	static Control createChildControlRow(Composite parent, String label,int width, int height, String properties)
	{
		RowData data1=new RowData();
		
		
	    Label label1=new Label(parent,SWT.WRAP);
	    label1.setLayoutData(data1);
	    label1.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
	    String src = RWT.getResourceManager().getLocation( label ); 
	    
	    label1.setText("<img width='"+width+"' height='"+height+"' src='" + src + "' style='"+properties+"'></img> ");
		return label1;
	}
	
	static Control createChildControlSeparator(Composite parent)
	{
        RowData data1=new RowData();
		
		
	    Label label1=new Label(parent,SWT.SEPARATOR|SWT.SHADOW_OUT);
	    
	    label1.setLayoutData(data1);    
	    
	    
	    return label1;
		
	}
	
		
	
	
	
	
	

}
