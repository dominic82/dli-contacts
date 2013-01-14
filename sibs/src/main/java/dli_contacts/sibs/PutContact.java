package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import dli_contacts.Contact;

/**
 * 
 * @author dominic
 */
@SIBClass("putContact")
public class PutContact implements Executable {

    /**
     * Possible Output-Branches of the jabc-SIB
     */
    public final String[] BRANCHES = {"default", "error"};
    /**
     * Context Variable where to save the contact
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
            Contact person = new Contact();
            env.put(contact, person);
            return "default";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }

    }
}
