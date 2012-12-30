package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;
import dli_contacts.Contact;
import dli_contacts.ContactsConnector;

/**
 * 
 * @author dominic
 */
@SIBClass("addGoogleContact")
public class AddGoogleContact implements Executable {

    /**
     * Possible Output-Branches of the jabc-SIB
     */
    public final String[] BRANCHES = {"default", "error"};
    
    /**
     * Context Variable where to find the contact-object to save
     */
    public ContextKey contact = new ContextKey("contact");

    /**
     * This method is called by the Tracer in jabc
     * @param env Execution-context in jabc
     * @return chosen output-branch
     */
    @Override
    public String trace(ExecutionEnvironment env) {

        try {
            Contact person = (Contact) env.get(contact);

            ContactsConnector con = new ContactsConnector();
            con.addGoogleContact(person);

            System.out.println("AddGoogleContact: added Contact '" + person.getFirstname() + " " + person.getLastname() + "'");

            return "default";

        } catch (Exception e) {
            System.out.println(e);
            return "error";
        }

    }
}
