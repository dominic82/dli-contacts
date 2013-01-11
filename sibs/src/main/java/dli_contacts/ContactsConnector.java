package dli_contacts;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import dli_contacts.googleconnector.DLI_GoogleContactsConnector;
import dli_contacts.sapconnector.main_sapkonnektor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        result = main_sapkonnektor.fetchContact(filter);
        System.out.println("Connector: SAP returned " + result.size() + " results");

        return result;
    }

    /**
     * Search Contacts in the Google-Database
     * @param filter Contact-onject to specify the search
     * @return List of matching Contact-objects
     */
    public List<Contact> getGoogleContacts(Contact filter) throws AuthenticationException, ServiceException, IOException {
        List<Contact> result = new ArrayList<Contact>();
        DLI_GoogleContactsConnector gc = new DLI_GoogleContactsConnector();
        result = gc.fetchContacts(filter);
        System.out.println("Connector: Google returned " + result.size() + " results");
        return result;
    }

    /**
     * Add the Data of a Contact-object to the Google-Database
     * @param contact contact-object to save
     */
    public void addGoogleContact(Contact contact) throws AuthenticationException, IOException, ServiceException {
        DLI_GoogleContactsConnector gc = new DLI_GoogleContactsConnector();
        gc.createContact(contact);
        System.out.println("Connector: added Google Contact '" + contact.getFirstname() + " " + contact.getLastname() + "'");
    }
}
