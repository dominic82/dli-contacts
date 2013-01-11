package dli_contacts.sibs;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import dli_contacts.Contact;
import dli_contacts.ContactsConnector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author dominic
 */
@SIBClass("searchGoogleContact")
public class SearchGoogleContact implements Executable {

    /**
     * Possible Output-Branches of the jabc-SIB
     */
    public final String[] BRANCHES = {"error", "found", "not found"};
    /**
     * Context Variable where to find the filter (contact-object)
     */
    public ContextKey contact = new ContextKey("contact");
    /**
     * Context Variable where to save the result-list
     */
    public ContextKey contactList = new ContextKey("contactList");

    /**
     * This method is called by the Tracer in jabc
     * @param env Execution-context in jabc
     * @return chosen output-branch
     */
    @Override
    public String trace(ExecutionEnvironment env) {

        List<Contact> list = new ArrayList<Contact>();
        Contact filter = (Contact) env.get(contact);

        System.out.println(filter.getDataString());

        ContactsConnector con = new ContactsConnector();
        try {

//            System.setProperty("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");
//            System.setProperty("javax.xml.parsers.SAXParser", "org.apache.xerces.jaxp.SAXParserImpl");
//            System.setProperty("oracle.xml.parser.v2.SAXParser", "org.apache.xerces.jaxp.SAXParserImpl");

            list = con.getGoogleContacts(filter);
            
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getDebugInfo());
            System.out.println(ex.getExtendedHelp());
            System.out.println(ex.toString());
            ex.printStackTrace();
        } catch (ServiceException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getDebugInfo());
            System.out.println(ex.getExtendedHelp());
            System.out.println(ex.toString());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.toString());
            ex.printStackTrace();
        }


        env.put(contactList, list);
        if (list.isEmpty()) {
            return "not found";
        }
        if (list.size() > 0) {
            return "found";
        }
        return "error";



    }
}
