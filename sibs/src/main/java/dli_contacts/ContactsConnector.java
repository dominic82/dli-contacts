package dli_contacts;

import com.google.gdata.util.ServiceException;
import dli_contacts.googleconnector.DLI_GoogleContactsConnector;

import com.google.gdata.util.AuthenticationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author dominic
 */
public class ContactsConnector {

    /**
     * Search Contacts in the SAP-Database
     * @param filter Contact-onject to specify the search
     * @return List of matching Contact-objects
     */
    public List<Contact> getSapContacts(Contact filter) {
        List<Contact> result = new ArrayList<Contact>();

        // TODO (SAP) Einbinden der externen Klasse und Ausführung
        System.out.println("ContactsConnector: get SAP Contacts");

        return result;
    }

    /**
     * Search Contacts in the Google-Database
     * @param filter Contact-onject to specify the search
     * @return List of matching Contact-objects
     */
    public List<Contact> getGoogleContacts(Contact filter) {
        List<Contact> result = new ArrayList<Contact>();
        System.out.println("ContactsConnector: get Google Contacts");
        try {
            DLI_GoogleContactsConnector gc = new DLI_GoogleContactsConnector();
            result = gc.fetchContacts(filter);
        } catch (ServiceException ex) {
            Logger.getLogger(ContactsConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContactsConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Add the Data of a Contact-object to the Google-Database
     * @param contact contact-object to save
     */
    public void addGoogleContact(Contact contact) {
        System.out.println("ContactsConnector: add Google Contact");
        try {
            DLI_GoogleContactsConnector gc = new DLI_GoogleContactsConnector();
            gc.createContact(contact);
        } catch (IOException ex) {
            Logger.getLogger(ContactsConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(ContactsConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
