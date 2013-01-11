package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import dli_contacts.Contact;
import dli_contacts.ContactsConnector;
import java.util.List;

/**
 * 
 * @author dominic
 */
@SIBClass("searchSapContact")
public class SearchSapContact implements Executable {

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
        try {
            Contact filter = (Contact) env.get(contact);

            ContactsConnector con = new ContactsConnector();
            List<Contact> list = con.getSapContacts(filter);

            env.put(contactList, list);
            if (list.isEmpty()) {
                return "not found";
            }
            if (list.size() > 0) {
                return "found";
            }
            return "error";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "error";
        }

    }
}
