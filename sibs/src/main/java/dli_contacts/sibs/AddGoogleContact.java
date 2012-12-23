package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

@SIBClass("addGoogleContact")
public class AddGoogleContact implements Executable {

    public final String[] BRANCHES = {"default", "error"};
    public ContextKey contact = new ContextKey("contact");

    @Override
    public String trace(ExecutionEnvironment env) {

        try {

            return "default";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }

    }
}
