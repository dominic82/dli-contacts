package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import dli_contacts.Contact;
import dli_contacts.ContactsConnector;
import dli_contacts.DummyConnector;
import java.util.List;

@SIBClass("searchGoogleContact")
public class SearchGoogleContact implements Executable {

    public final String[] BRANCHES = {"error", "found", "not found"};
    public ContextKey contact = new ContextKey("contact");
    public ContextKey contactList = new ContextKey("contactList");

    @Override
    public String trace(ExecutionEnvironment env) {

        try {
            Contact filter = (Contact) env.get(contact);
            
            ContactsConnector con = new DummyConnector();
            List<Contact> list = con.getGoogleContacts(filter);
            
            env.put(contactList, list);
            System.out.println("SearchGoogleContact: got " + list.size() + " results");
            
            if (list.isEmpty()) {
                return "not found";
            }
            if (list.size() > 0) {
                return "found";
            }
            return "error";

        } catch (Exception e) {
            System.out.println(e);
            return "error";
        }

    }
}
