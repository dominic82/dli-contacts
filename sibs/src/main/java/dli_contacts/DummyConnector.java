package dli_contacts;

import java.util.ArrayList;
import java.util.List;

import dli_contacts.Contact.ContactType;

public class DummyConnector extends ContactsConnector {

    private Contact person1 = new Contact();
    private Contact person2 = new Contact();
    private Contact person3 = new Contact();

    public DummyConnector() {
        person1.setGoogleId("g1");
        person1.setSapId("s1");
        person1.setFirstname("Dominic");
        person1.setLastname("Wirkner");
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
        person2.setStreet("Hinter den sieben Bergen");
        person2.setZipcode("22222");
        person2.setCity("Bei den sieben Zwergen");
        person2.setPhone("123 456 789");
        person2.setEmail("ts@mail.de");
        person1.setType(ContactType.SUPPLIER);

        person3.setGoogleId("g3");
        person3.setSapId("s3");
        person3.setFirstname("Markus");
        person3.setLastname("Marzotko");
        person3.setStreet("nächste links");
        person3.setZipcode("33333");
        person3.setCity("Weiss der Geier");
        person3.setPhone("123 456 789");
        person3.setEmail("mm@mail.de");
        person1.setType(ContactType.EMPLOYEE);

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

    @Override
    public List<Contact> getSapContacts(Contact filter) {
        List<Contact> list = new ArrayList<Contact>();

        list.add(person1);
        list.add(person2);
        list.add(person3);

        System.out.println("DummyConnector: get SAP-Contacts");

        return list;
    }

    @Override
    public List<Contact> getGoogleContacts(Contact filter) {
        List<Contact> list = new ArrayList<Contact>();

        list.add(person1);
        list.add(person2);
        list.add(person3);

        System.out.println("DummyConnector: get Google-Contacts");

        return list;
    }

    @Override
    public void addGoogleContact(Contact contact) {

        System.out.println("DummyConnector: add Google-Contact");
    }
}
