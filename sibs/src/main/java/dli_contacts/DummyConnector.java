package dli_contacts;

import java.util.ArrayList;
import java.util.List;

import dli_contacts.Contact.ContactType;

/**
 * 
 * @author dominic
 */
public class DummyConnector extends ContactsConnector {

    private Contact person1 = new Contact();
    private Contact person2 = new Contact();
    private Contact person3 = new Contact();

    /**
     * Initialize some DummyObjects
     */
    public DummyConnector() {
        person1.setGoogleId("g1");
        person1.setSapId("s1");
        person1.setFirstname("Dominic");
        person1.setLastname("Wirkner");
        person1.setCompany("Picknicker AG");
        person1.setStreet("Unter den Bäumen");
        person1.setZipcode("11111");
        person1.setCity("Waldhausen");
        person1.setPhone("123 456 789");
        person1.setEmail("dw@mail.de");
        person1.setType(ContactType.CUSTOMER);

        person2.setGoogleId("g2");
        person2.setSapId("s2");
        person2.setFirstname("Thorben");
        person2.setLastname("Seeland");
        person2.setCompany("LoL Enterprises");
        person2.setStreet("Hinter den sieben Bergen");
        person2.setZipcode("22222");
        person2.setCity("Bei den sieben Zwergen");
        person2.setPhone("123 456 789");
        person2.setEmail("ts@mail.de");
        person2.setType(ContactType.SUPPLIER);

        person3.setGoogleId("g3");
        person3.setSapId("s3");
        person3.setFirstname("Markus");
        person3.setLastname("Marzotko");
        person3.setCompany("SAP Consulting");
        person3.setStreet("nächste links");
        person3.setZipcode("33333");
        person3.setCity("Weiss der Geier");
        person3.setPhone("123 456 789");
        person3.setEmail("mm@mail.de");
        person3.setType(ContactType.EMPLOYEE);

        System.out.println("DummyConnector: started...");
    }

    public Contact getPerson1() {
        return person1;
    }

    public Contact getPerson2() {
        return person2;
    }

    public Contact getPerson3() {
        return person3;
    }

    /**
     * Search Contacts in the SAP-Database (DummyMethod)
     * @param filter Contact-onject to specify the search
     * @return List of matching Contact-objects
     */
    @Override
    public List<Contact> getSapContacts(Contact filter) {
        List<Contact> list = new ArrayList<Contact>();

        list.add(person1);
        list.add(person2);
        list.add(person3);

        System.out.println("DummyConnector: get SAP-Contacts");

        return list;
    }

    /**
     * Search Contacts in the Google-Database (DummyMethod)
     * @param filter Contact-onject to specify the search
     * @return List of matching Contact-objects
     */
    @Override
    public List<Contact> getGoogleContacts(Contact filter) {
        List<Contact> list = new ArrayList<Contact>();

        list.add(person1);
        list.add(person2);
        list.add(person3);

        System.out.println("DummyConnector: get Google-Contacts");

        return list;
    }

    /**
     * Add the Data of a Contact-object to the Google-Database (DummyMethod)
     * @param contact contact-object to save
     */
    @Override
    public void addGoogleContact(Contact contact) {

        System.out.println("DummyConnector: add Google-Contact");
    }
}
