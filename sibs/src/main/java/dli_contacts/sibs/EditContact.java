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
            System.out.println("EditContact: Dialog geöffnet");

            synchronized (this) {
                EditContactFrame frame = new EditContactFrame("Kontakt suchen", person, this);

                this.wait();

                EditContactFrame.ResultBranch result = frame.getResult();
                env.put(contact, frame.getContact());
                System.out.println("EditContact: Dialog geschlossen mit " + result);

                switch (result) {
                    case OK:
                        return "ok";
                    case CANCEL:
                    case UNKNOWN:
                    default:
                        return "cancel";
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }
}
