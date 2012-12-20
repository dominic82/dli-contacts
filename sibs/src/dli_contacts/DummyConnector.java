package dli_contacts;

import java.util.ArrayList;
import java.util.List;

import dli_contacts.Contact.ContactType;

public class DummyConnector {
	
	private Contact person1;
	private Contact person2;
	private Contact person3;
	
	public DummyConnector() {
		person1.setFirstname("Dominic");
		person1.setLastname("Wirkner");
		person1.setStreet("Unter den Bäumen");
		person1.setZipcode("11111");
		person1.setCity("Waldhausen");
		person1.setPhone("123 456 789");
		person1.setEmail("dw@mail.de");
		person1.setType(ContactType.CUSTOMER);
		
		person2.setFirstname("Thorben");
		person2.setLastname("Seeland");
		person2.setStreet("Hinter den sieben Bergen");
		person2.setZipcode("22222");
		person2.setCity("Bei den sieben Zwergen");
		person2.setPhone("123 456 789");
		person2.setEmail("ts@mail.de");
		person1.setType(ContactType.SUPPLIER);
		
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

	public List<Contact> getSapContacts(Contact contact) {
		List<Contact> result = new ArrayList<Contact>();
		
		result.add(person1);
		result.add(person2);
		result.add(person3);
		
		System.out.println("DummyConnector: get SAP-Contacts");
		
		return result;
	}
	
	public List<Contact> getGoogleContacts(Contact contact) {
		List<Contact> result = new ArrayList<Contact>();
		
		result.add(person1);
		result.add(person2);
		result.add(person3);
		
		System.out.println("DummyConnector: get Google-Contacts");
		
		return result;
	}
	
	public void addGoogleContact(Contact contact) {
		
		System.out.println("DummyConnector: add Google-Contact");
		System.out.println("Contact-Data: " + contact.getFirstname() + " " + contact.getLastname());
	}
}
