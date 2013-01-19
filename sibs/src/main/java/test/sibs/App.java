package test.sibs;

import dli_contacts.Contact;
import dli_contacts.ContactsConnector;
import dli_contacts.sapconnector.main_sapkonnektor;
import dli_contacts.sibs.gui.ChooseContactFrame;

import dli_contacts.sibs.gui.EditContactFrame;
import java.util.List;
import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        
        main_sapkonnektor.main(args);
        
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


//        Contact filter = new Contact();
//        EditContactFrame frame = new EditContactFrame("Google Kontakt suchen", filter);
//        frame.setDoValidation(false);
//        frame.initializeWindow();
//
//        synchronized (frame) {
//            try {
//                frame.wait();
//            } catch (InterruptedException ex) {
//                System.out.println(ex);
//            }
//            filter = frame.getContact();
//        }
//
//        List<Contact> glist = null;
//        try {
//            glist = gCon.getGoogleContacts(filter);
//        } catch (AuthenticationException ex) {
//            System.out.println(ex);
//        } catch (ServiceException | IOException ex) {
//            System.out.println(ex);
//        }
//
//        ChooseContactFrame frame2 = new ChooseContactFrame("Google Kontakt auswählen", glist);
//        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame2.initializeWindow();

//        EditContactFrame frame = new EditContactFrame("Test Kontakt bearbeiten", filter);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setDoValidation(true);
//        frame.initializeWindow();

        Contact sapfilter = new Contact();
        EditContactFrame sapframe = new EditContactFrame("SAP Kontakt suchen", sapfilter);
        sapframe.setDoValidation(false);
        sapframe.initializeWindow();

        synchronized (sapframe) {
            try {
                sapframe.wait();
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            System.out.println(sapframe.getContact().getDataString());
            sapfilter = sapframe.getContact();
        }
        
        List<Contact> saplist = null;
        saplist = gCon.getSapContacts(sapfilter);
        
        ChooseContactFrame frame3 = new ChooseContactFrame("SAP Kontakt auswählen", saplist);
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.initializeWindow();

    }
}
