package cu.uci.ldap.ejemploLDAPNovell;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;

public class Conexion {
	
	private int ldapPort;
    private int ldapVersion;
    private LDAPConnection lc;
    private String login;
    private String ldapHost = "10.0.0.3";//ldap://
	
	public Conexion() {
	}
	

	/**
     * Este método permite realizar la conexión al servidor de LDAP
     * Para el usuario manager*/
	
	public LDAPConnection ConexionUser(String strUser, String strPassword){
		login = "uid=" + strUser + ",OU=UCI Domain Users, DC=uci, DC=cu";
		//login = "uid=" + strUser + ",ou=UCI Domain Users,o=uci,c=cu";
        System.out.println("" + login);
        ldapPort = LDAPConnection.DEFAULT_PORT;
        System.out.println("puerto: " + ldapPort);
        ldapVersion = LDAPConnection.LDAP_V3;
        System.out.println("Vesion: " + ldapVersion);
        System.out.println("HOST: " + ldapHost);
        try {
             lc = new LDAPConnection();
             lc.connect(ldapHost, ldapPort);
             System.out.println("====Conectando al Servidor LDAP UCI====");
             lc.bind(ldapVersion, login, strPassword.getBytes("UTF8"));
        } catch (UnsupportedEncodingException ex) {
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null, ex);
        } catch (LDAPException ex) {
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null, ex);
        }
        return lc;
	}
	
	public void CerrarConLDAP(LDAPConnection lc) {
        try {
             lc.disconnect();
             System.out.println("Conexion Cerrada Correctamente...");
        } catch (LDAPException ex) {
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null, ex);
        }
   }
	
	public boolean ValidarPassWord(String strUser, String strPass) {
        boolean correct = false;
        String dnc = "uid=" + strUser + ",OU=UCI Domain Users, DC=uci, DC=cu";
        //String dnc = "uid=+" + strUser + ",ou=People,o=utpl,c=ec";
        try {
             lc.bind(LDAPConnection.LDAP_V3, dnc, strPass.getBytes("UTF8"));
             LDAPAttribute attr = new LDAPAttribute("userPassword", strPass);
             correct = lc.compare(dnc, attr);
             System.err.println(correct ? "El Password es correcto" : "El Password NO es correcto.\n");
        } catch (LDAPException ex) {
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null, ex);
        } catch (UnsupportedEncodingException ex) {
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null, ex);
        }
        return correct;
   }
	

}
