package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import java.util.List;

import dli_contacts.Contact;
import dli_contacts.sibs.gui.ChooseContactFrame;

/**
 * 
 * @author dominic
 */
@SIBClass("chooseContact")
public class ChooseContact implements Executable {

    /**
     * Possible Output-Branches of the jabc-SIB
     */
    public final String[] BRANCHES = {"ok", "error", "cancel"};
    /**
     * Context Variable where to save the chosen contact
     */
    public ContextKey contact = new ContextKey("contact");
    /**
     * Context Variable of the list of contact-objects to choose from
     */
    public ContextKey contactList = new ContextKey("contactList");
    /**
     * Title of the displayed Swing-Frame
     */
    public String title = "Kontakt auswählen";

    /**
     * This method is called by the Tracer in jabc
     * @param env Execution-context in jabc
     * @return chosen output-branch
     */
    @Override
    public String trace(ExecutionEnvironment env) {

        try {
            List<Contact> list = (List<Contact>) env.get(contactList);
            ChooseContactFrame frame = new ChooseContactFrame(title, list);

            synchronized (frame) {

                frame.wait();

                ChooseContactFrame.ResultBranch result = frame.getResult();

                System.out.println("ChooseContact: Dialog geschlossen mit " + result);

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
