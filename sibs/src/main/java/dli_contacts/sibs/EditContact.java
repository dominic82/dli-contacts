package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import dli_contacts.Contact;
import dli_contacts.sibs.gui.EditContactFrame;

/**
 * 
 * @author dominic
 */
@SIBClass("editContact")
public class EditContact implements Executable {

    /**
     * Possible Output-Branches of the jabc-SIB
     */
    public final String[] BRANCHES = {"ok", "cancel", "error"};
    /**
     * Context Variable of the contact-object to edit
     */
    public ContextKey contact = new ContextKey("contact");
    /**
     * Title of the displayed Swing-Frame
     */
    public String title = "Kontakt bearbeiten";
    /**
     * Turn Form-Validation on/off
     */
    public boolean validate = false;

    /**
     * This method is called by the Tracer in jabc
     * @param env Execution-context in jabc
     * @return chosen output-branch
     */
    @Override
    public String trace(ExecutionEnvironment env) {

        try {
            Contact person = (Contact) env.get(contact);
            EditContactFrame frame = new EditContactFrame(title, person);
            frame.setDoValidation(validate);
            frame.initializeWindow();

            synchronized (frame) {

                frame.wait();

                EditContactFrame.ResultBranch result = frame.getResult();
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
            System.out.println(e.getMessage());
            return "error";
        }
    }
}
