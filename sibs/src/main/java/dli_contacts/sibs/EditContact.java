package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import dli_contacts.Contact;
import dli_contacts.sibs.gui.EditContactFrame;

@SIBClass("editContact")
public class EditContact implements Executable {

    public final String[] BRANCHES = {"ok", "cancel", "error"};
    public ContextKey contact = new ContextKey("contact");

    @Override
    public String trace(ExecutionEnvironment env) {

        try {
            Contact person = (Contact) env.get(contact);
            EditContactFrame frame = new EditContactFrame("Kontakt suchen", person);

            synchronized (frame) {

                frame.wait();

                EditContactFrame.ResultBranch result = frame.getResult();
                
                System.out.println("EditContact: Dialog geschlossen mit " + result);

                switch (result) {
                    case OK:
                        env.put(contact, frame.getContact());
                        return "ok";
                    case UNKNOWN:
                        return "error";
                    case CANCEL:
                    default:
                        return "cancel";
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return "error";
        }
    }
}
