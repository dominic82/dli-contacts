package test.sibs;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import dli_contacts.Contact;
import dli_contacts.ContactsConnector;
import dli_contacts.sibs.gui.ChooseContactFrame;

import dli_contacts.sibs.gui.EditContactFrame;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Main startet...");

        ContactsConnector gCon = new ContactsConnector();

//        Contact newContact = new Contact();
//        newContact.setFirstname("Testvorname");
//        newContact.setLastname("Testnachname");
//        newContact.setCompany("Testfirma");
//        newContact.setStreet("Teststr 24");
//        newContact.setZipcode("12345");
//        newContact.setCity("Teststadt");
//        newContact.setPhone("0190 222 222");
//        newContact.setEmail("test@test.de");
//        newContact.setType(Contact.ContactType.SUPPLIER);
//        try {
//            gCon.addGoogleContact(newContact);
//        } catch (AuthenticationException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ServiceException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }


        Contact filter = new Contact();
        EditContactFrame frame = new EditContactFrame("Test Kontakt suchen", filter);
        frame.setDoValidation(false);
        frame.initializeWindow();

        synchronized (frame) {
            try {
                frame.wait();
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            filter = frame.getContact();
        }

        System.out.println("Gruppe: " + filter.getType().toString());

        List<Contact> glist = null;
        try {
            glist = gCon.getGoogleContacts(filter);
        } catch (AuthenticationException ex) {
            System.out.println(ex);
        } catch (ServiceException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        ChooseContactFrame frame2 = new ChooseContactFrame("Test Kontakt auswählen", glist);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.initializeWindow();

//        EditContactFrame frame = new EditContactFrame("Test Kontakt bearbeiten", filter);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setDoValidation(true);
//        frame.initializeWindow();


    }
}
