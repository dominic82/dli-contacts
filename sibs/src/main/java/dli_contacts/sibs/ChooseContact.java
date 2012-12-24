package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import java.util.List;

import dli_contacts.Contact;
import dli_contacts.sibs.gui.ChooseContactFrame;

@SIBClass("chooseContact")
public class ChooseContact implements Executable {

    public final String[] BRANCHES = {"ok", "error", "cancel"};
    public ContextKey contact = new ContextKey("contact");
    public ContextKey contactList = new ContextKey("contactList");

    @Override
    public String trace(ExecutionEnvironment env) {

        try {
            List<Contact> list = (List<Contact>) env.get(contactList);
            ChooseContactFrame frame = new ChooseContactFrame("Kontakt auswählen", list);

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
