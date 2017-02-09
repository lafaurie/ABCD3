package cu.uci.abos.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;
import org.eclipse.rap.rwt.service.ResourceLoader;

public class DemoConfiguration implements ApplicationConfiguration {

	@Override
	public void configure(Application arg0) {
		Map<String, String> prop = new HashMap<String, String>();
		prop.put(WebClient.PAGE_TITLE, "RAP Login Form");
		arg0.addStyleSheet( "mio", "themes/custom.css" );		
		prop.put(WebClient.THEME_ID, "mio");
		arg0.addResource( "foo/icon.png", new ResourceLoader() {
			  @Override
			  public InputStream getResourceAsStream( String resourceName ) throws IOException {
			    return this.getClass().getClassLoader().getResourceAsStream( "themes/logo.png" );
			  }
			} );
		    
		arg0.addResource( "login/grafica.png", new ResourceLoader() {
			  @Override
			  public InputStream getResourceAsStream( String resourceName ) throws IOException {
			    return this.getClass().getClassLoader().getResourceAsStream( "themes/grafica_login.png" );
			  }
			} );
		arg0.addResource( "administracion/grafica.png", new ResourceLoader() {
			  @Override
			  public InputStream getResourceAsStream( String resourceName ) throws IOException {
			    return this.getClass().getClassLoader().getResourceAsStream( "themes/grafica.jpg" );
			  }
			} );
		arg0.addResource( "administracion/user.png", new ResourceLoader() {
			  @Override
			  public InputStream getResourceAsStream( String resourceName ) throws IOException {
			    return this.getClass().getClassLoader().getResourceAsStream( "themes/avatar_default.png" );
			  }
			} );
		arg0.addResource( "administracion/footer.png", new ResourceLoader() {
			  @Override
			  public InputStream getResourceAsStream( String resourceName ) throws IOException {
			    return this.getClass().getClassLoader().getResourceAsStream( "themes/mini.png" );
			  }
			} );
		
		
		arg0.addEntryPoint("/sitio", LoginEntryPoint.class, prop);
		
		arg0.addEntryPoint("/administrador", Administracion.class,prop);
		
		
	}

}
