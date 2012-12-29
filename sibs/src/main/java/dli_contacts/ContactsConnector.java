package dli_contacts;

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

        // TODO (Google) Einbinden der externen Klasse und Ausführung
        System.out.println("ContactsConnector: get Google Contacts");

        return result;
    }

    /**
     * Add the Data of a Contact-object to the Google-Database
     * @param contact contact-object to save
     */
    public void addGoogleContact(Contact contact) {

        // TODO (Google) Einbinden der externen Klasse und Ausführung
        System.out.println("ContactsConnector: add Google Contact");

    }
}
