package cu.uci.ldap.ejemploLDAPNovell;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

/**
 * Clase para Buscar Usuarios el Filtro es el usuario que se desea buscar.
 * */
public class Buscar {
	
	 private String searchBase = "OU=UCI Domain Users, DC=uci, DC=cu";//o=utpl,c=ec
     private int searchScope = LDAPConnection.SCOPE_SUB;
     private String filtro;
     private LDAPSearchResults searchResults;
      /**
     * Metodo para buscar un usuario dentro del servidor LDAP
     * @param LDAPConnection lc
     * @param String strFiltro
     */
 
     @SuppressWarnings("unchecked")
	public Buscar(LDAPConnection lc, String strFiltro) {
          filtro = "(uid="+ strFiltro + ")";
          try {
               searchResults = lc.search(searchBase, searchScope, filtro, null, false);
               //Recorre Todos los Usuarios de la Base
               while (searchResults.hasMore()) {
                    LDAPEntry nextEntry = null;
                    try {
                         nextEntry = searchResults.next();
                    } catch (LDAPException e) {
                         System.out.println("Error: " + e.toString());
                         continue;
                    }
                    LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
                    Iterator<LDAPAttribute> allAttributes = attributeSet.iterator();
                    //Recorre los atributos del usuario
                    while (allAttributes.hasNext()) {
                         LDAPAttribute attribute = (LDAPAttribute) allAttributes.next();
                         String attributeName = attribute.getName();
                         Enumeration<String> allValues = attribute.getStringValues();
                         if (allValues != null) {
                              while (allValues.hasMoreElements()) {
                                   String value = (String) allValues.nextElement();
                                   System.out.println(attributeName + ":  " + value);
                              }
                         }
                    }
                    System.out.println("------------------------------");
                    lc.disconnect();
               }
          } catch (LDAPException ex) {
               Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE,null, ex);
          }
     }
     
     

}
