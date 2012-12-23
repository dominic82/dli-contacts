package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import dli_contacts.Contact;

@SIBClass("editContact")
public class EditContact implements Executable {

    public final String[] BRANCHES = {"default", "error"};
    public ContextKey contact = new ContextKey("contact");

    @Override
    public String trace(ExecutionEnvironment env) {

        try {

            Contact person = (Contact) env.get(contact);
            System.out.println("EditContact: Name = " + person.getFirstname());

            return "default";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }
}
