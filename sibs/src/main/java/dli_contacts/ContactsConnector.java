package dli_contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactsConnector {

    public List<Contact> getSapContacts(Contact filter) {
        List<Contact> result = new ArrayList<Contact>();

        System.out.println("ContactsConnector: get SAP Contacts");

        return result;
    }

    public List<Contact> getGoogleContacts(Contact filter) {
        List<Contact> result = new ArrayList<Contact>();

        System.out.println("ContactsConnector: get Google Contacts");

        return result;
    }

    public void addGoogleContact(Contact contact) {

        System.out.println("ContactsConnector: add Google Contact");

    }
}
