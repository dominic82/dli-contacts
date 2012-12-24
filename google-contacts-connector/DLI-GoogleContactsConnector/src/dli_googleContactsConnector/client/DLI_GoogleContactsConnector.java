package dli_googleContactsConnector.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.Query;
import com.google.gdata.client.Service;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.City;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.PostCode;
import com.google.gdata.data.extensions.Street;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import dli_contacts.Contact;
import dli_contacts.Contact.ContactType;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DLI_GoogleContactsConnector {
	private static String customerGroupURL = "http://www.google.com/m8/feeds/groups/dli.ides.api%40gmail.com/base/3abf361e0913da63";
	private static String supplierGroupURL = "http://www.google.com/m8/feeds/groups/dli.ides.api%40gmail.com/base/2aada2220eaad8d4r";
	private static String employeeGroupURL = "http://www.google.com/m8/feeds/groups/dli.ides.api%40gmail.com/base/587c880e884cdacb";

	private static String customer = "Customer";
	private static String supplier = "Supplier";
	private static String employee = "Employee";

	private final String username = "dli.ides.api@gmail.com";
	private final String password = "DLIP455w0rd!";
	private final String servicename = "dli-google-connector";
	private final String contactsURL = "https://www.google.com/m8/feeds/contacts/dli.ides.api@gmail.com/full";
private final String groupsURL = "https://www.google.com/m8/feeds/groups/dli.ides.api@gmail.com/base"; 
	
	private ContactsService myService;

	public DLI_GoogleContactsConnector() throws AuthenticationException {
		authenticateId();
	}

	public ContactEntry createContact(Contact contactInfo) throws IOException, ServiceException {
		return createContact(contactsURL, contactInfo, myService);

	}

	public static ContactEntry createContact(String contactsURL,
			Contact contactInfo, Service myService) throws IOException, ServiceException {
System.out.println("createContact gestartet");
		if (!isValid(contactInfo)) {
System.out.println("contact nicht valid");
			return null;
		}
System.out.println("create entry");
		// Create the entry to insert
		ContactEntry contact = new ContactEntry();
		contact.setTitle(new PlainTextConstruct(contactInfo.getFirstname()
				+ contactInfo.getLastname()));

		// Name
		Name name = new Name();
		name.setFamilyName(new FamilyName(contactInfo.getLastname(), null));
		name.setGivenName(new GivenName(contactInfo.getFirstname(), null));
		contact.setName(name);

		// Email >> es kann NICHT NUR eine geben
		if(contactInfo.getEmail()!=null){
		Email primaryMail = new Email();
		primaryMail.setAddress(contactInfo.getEmail());
		primaryMail.setRel("http://schemas.google.com/g/2005#home");// Email Typ
		primaryMail.setPrimary(true);
		contact.addEmailAddress(primaryMail);
		}
		// Telefon >> es kann NICHT NUR eine geben
		if(contactInfo.getPhone()!=null){
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setPhoneNumber(contactInfo.getPhone());
		phoneNumber.setPrimary(true);
		phoneNumber.setLabel("Primär");
		contact.addPhoneNumber(phoneNumber);
		}
		// Adresse >> es kann NICHT NUR eine geben
		if((contactInfo.getCity()!=null)||(contactInfo.getStreet()!=null)||(contactInfo.getZipcode()!=null)){
		StructuredPostalAddress adresse = new StructuredPostalAddress();
		adresse.setCity(new City(contactInfo.getCity()));
		adresse.setPostcode(new PostCode(contactInfo.getZipcode()));
		adresse.setStreet(new Street(contactInfo.getStreet()));
		adresse.setPrimary(true);
		adresse.setLabel("Primär");
		contact.addStructuredPostalAddress(adresse);
		}

		// TODO Firma
		/*
		 * favouriteFlower.setName("Organisation");
		 * favouriteFlower.setValue(contactInfo.getOrganisation);
		 * contact.addExtendedProperty(favouriteFlower);
		 */
		// Gruppe setzen
		String groupURL = null;
		switch (contactInfo.getType()) {
		case CUSTOMER:
			groupURL = customerGroupURL;
			break;
		case SUPPLIER:
			groupURL = supplierGroupURL;
			break;
		case EMPLOYEE:
			groupURL = employeeGroupURL;
			break;

		default:
			break;
		}
		contact.addGroupMembershipInfo(new GroupMembershipInfo(false, groupURL));

System.out.println("entry ready");
		// Ask the service to insert the new entry
		URL postUrl = null;
			postUrl = new URL(contactsURL);
			return myService.insert(postUrl, contact);
	}

	public ContactsService authenticateId() {

		myService = authenticateId(username, password, servicename);
		return myService;

	}

	/**
	 * This method will authenticate the user credentials passed to it and
	 * returns an instance of ContactService class.
	 * 
	 * @throws AuthenticationException
	 */
	public static ContactsService authenticateId(String username,
			String password, String servicename) {
		ContactsService myService;
		myService = new ContactsService(servicename);
		try {
			myService.setUserCredentials(username, password);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return myService;

	}

	public List<Contact> fetchContacts(Contact filter) throws ServiceException, IOException {
		
			return fetchContacts(contactsURL, filter, myService);
		
	}

	/**
	 * Sucht die Kontakte, die mit dem filter übereinstimmen
	 * 
	 * @param filter
	 * @param myService
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	public static List<Contact> fetchContacts(String contactsURL,
			Contact filter, ContactsService myService)throws ServiceException, IOException {
		// Create query and submit a request
		URL feedUrl = null;
			feedUrl = new URL(contactsURL);
		Query myQuery = new Query(feedUrl);
		ContactFeed resultFeed = null;
		if (filter.getType() != null) {
			String groupId = null;
			switch (filter.getType()) {
			case CUSTOMER:
				groupId = customerGroupURL;
				break;
			case SUPPLIER:
				groupId = supplierGroupURL;
				break;
			case EMPLOYEE:
				groupId = employeeGroupURL;
				break;

			default:
				break;
			}
			myQuery.setStringCustomParameter("group", groupId);

			// submit request
				resultFeed = myService.query(myQuery, ContactFeed.class);
			
		} else {
			
				resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
			
		}
		// sort out
		System.out.println("sort out");
		List<ContactEntry> ceResults = resultFeed.getEntries();
		List<Contact> results = new ArrayList<Contact>();
		for (ContactEntry ce : ceResults) {
			Contact accepted = makeContact(ce);
			if (filterContact(filter, accepted)) {
				results.add(accepted);
			}
		}

		return results;
	}

	private static boolean filterContact(Contact filter, Contact accepted) {
		boolean city = (filter.getCity() == null);
		if (!city)
			city = accepted.getCity().contains(filter.getCity());

		boolean email = (filter.getEmail() == null);
		if (!email)
			email = accepted.getEmail().contains(filter.getEmail());

		boolean firstname = (filter.getFirstname() == null);
		if (!firstname)
			firstname = accepted.getFirstname().contains(filter.getFirstname());

		boolean lastname = (filter.getLastname() == null);
		if (!lastname)
			lastname = accepted.getLastname().contains(filter.getLastname());

		boolean phone = (filter.getPhone() == null);
		if (!phone)
			phone = accepted.getPhone().contains(filter.getPhone());

		boolean street = (filter.getStreet() == null);
		if (!street)
			street = accepted.getStreet().contains(filter.getStreet());

		return city && email && firstname && lastname && phone && street;
	}

	private static Contact makeContact(ContactEntry ce) {
		Contact result = new Contact();
//		result.setCity("");
//		result.setEmail("");
//		result.setFirstname("");
//		result.setLastname("");
//		result.setPhone("");
//		result.setStreet("");
//		result.setZipcode("");
//		result.setType(ContactType.CUSTOMER);
		if (ce.hasName()) {
			Name name = ce.getName();
			if (name.hasGivenName())
				result.setFirstname(name.getGivenName().getValue());
			if (name.hasFamilyName())
				result.setLastname(name.getFamilyName().getValue());
		}

		for (Email email : ce.getEmailAddresses()) {
			if (email.getPrimary()) {
				result.setEmail(email.getAddress());
			}

		}

		for (StructuredPostalAddress adress : ce.getStructuredPostalAddresses()) {
			if (adress.getPrimary()) {
				result.setCity(adress.getCity().getValue());
				result.setStreet(adress.getStreet().getValue());
				result.setZipcode(adress.getPostcode().getValue());
			}
		}

		for (PhoneNumber phone : ce.getPhoneNumbers()) {
			if (phone.getPrimary()) {
				result.setPhone(phone.getPhoneNumber());
			}
		}

		for (GroupMembershipInfo group : ce.getGroupMembershipInfos()) {
			if (group.getHref().contentEquals(customerGroupURL)) {
				result.setType(ContactType.CUSTOMER);
			}
			if (group.getHref().contentEquals(supplierGroupURL)) {
				result.setType(ContactType.SUPPLIER);
			}
			if (group.getHref().contentEquals(employeeGroupURL)) {
				result.setType(ContactType.EMPLOYEE);
			}
		}
		return result;
	}

	private static boolean isValid(Contact c) {
		boolean cs = false;
		cs = (c.getLastname() != null);
		if (cs) {
			cs = !(c.getLastname().isEmpty());
		}
		cs = cs || (c.getFirstname() != null);
		if (cs) {
			cs = cs || (!c.getFirstname().isEmpty());
		}
		// TODO gleiches für die Firma nochmal
		return cs;
	}

	private static String toStringWithContact(Contact c) {
		if (c == null) {
			return "Contact ist null\n";
		}

		String vorname = "first name:\t";
		if (c.getFirstname() == null) {
			vorname += "null" + "\n";
		} else {
			vorname += c.getFirstname() + "\n";
		}

		String nachname = "last name: \t";
		if (c.getLastname() == null) {
			nachname += "null" + "\n";
		} else {
			nachname += c.getLastname() + "\n";
		}

		String email = "email: \t\t";
		if (c.getEmail() == null) {
			email += "null" + "\n";
		} else {
			email += c.getEmail() + "\n";
		}

		String phone = "phone: \t\t";
		if (c.getPhone() == null) {
			phone += "null" + "\n";
		} else {
			phone += c.getPhone() + "\n";
		}

		String street = "street: \t";
		if (c.getStreet() == null) {
			street += "null" + "\n";
		} else {
			street += c.getStreet() + "\n";
		}

		String postal = "zipcode: \t";
		if (c.getZipcode() == null) {
			postal += "null" + "\n";
		} else {
			postal += c.getZipcode() + "\n";
		}

		String city = "city: \t\t";
		if (c.getCity() == null) {
			city += "null" + "\n";
		} else {
			city += c.getCity() + "\n";
		}

		String type = "type: \t\t";
		if (c.getType() == null) {
			type += "null" + "\n";
		} else {
			switch (c.getType()) {
			case CUSTOMER:
				type += customer + "\n";
				break;
			case SUPPLIER:
				type += supplier + "\n";
				break;
			case EMPLOYEE:
				type += employee + "\n";
				break;
			}
		}

		String result = vorname + nachname + email + phone + street + postal
				+ city + type;

		return result;
	}

	/*
	 * This method will print details of all the contacts available in that
	 * particular Google account.
	 */
	public static void printAllContacts(ContactsService myService)
			throws ServiceException, IOException {
		// Request the feed
		URL feedUrl = new URL(
				"https://www.google.com/m8/feeds/contacts/default/full");
		ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
		// Print the results
		System.out.println(resultFeed.getTitle().getPlainText());
		for (ContactEntry entry : resultFeed.getEntries()) {
			if (entry.hasName()) {
				Name name = entry.getName();
				if (name.hasFullName()) {
					String fullNameToDisplay = name.getFullName().getValue();
					if (name.getFullName().hasYomi()) {
						fullNameToDisplay += " ("
								+ name.getFullName().getYomi() + ")";
					}
					System.out.println("\\\t\\\t" + fullNameToDisplay);
				} else {
					System.out.println("\\\t\\\t (no full name found)");
				}
				if (name.hasNamePrefix()) {
					System.out.println("\\\t\\\t"
							+ name.getNamePrefix().getValue());
				} else {
					System.out.println("\\\t\\\t (no name prefix found)");
				}
				if (name.hasGivenName()) {
					String givenNameToDisplay = name.getGivenName().getValue();
					if (name.getGivenName().hasYomi()) {
						givenNameToDisplay += " ("
								+ name.getGivenName().getYomi() + ")";
					}
					System.out.println("\\\t\\\t" + givenNameToDisplay);
				} else {
					System.out.println("\\\t\\\t (no given name found)");
				}
				if (name.hasAdditionalName()) {
					String additionalNameToDisplay = name.getAdditionalName()
							.getValue();
					if (name.getAdditionalName().hasYomi()) {
						additionalNameToDisplay += " ("
								+ name.getAdditionalName().getYomi() + ")";
					}
					System.out.println("\\\t\\\t" + additionalNameToDisplay);
				} else {
					System.out.println("\\\t\\\t (no additional name found)");
				}
				if (name.hasFamilyName()) {
					String familyNameToDisplay = name.getFamilyName()
							.getValue();
					if (name.getFamilyName().hasYomi()) {
						familyNameToDisplay += " ("
								+ name.getFamilyName().getYomi() + ")";
					}
					System.out.println("\\\t\\\t" + familyNameToDisplay);
				} else {
					System.out.println("\\\t\\\t (no family name found)");
				}
				if (name.hasNameSuffix()) {
					System.out.println("\\\t\\\t"
							+ name.getNameSuffix().getValue());
				} else {
					System.out.println("\\\t\\\t (no name suffix found)");
				}
			} else {
				System.out.println("\t (no name found)");
			}
			System.out.println("Email addresses:");
			for (Email email : entry.getEmailAddresses()) {
				System.out.print(" " + email.getAddress());
				if (email.getRel() != null) {
					System.out.print(" rel:" + email.getRel());
				}
				if (email.getLabel() != null) {
					System.out.print(" label:" + email.getLabel());
				}
				if (email.getPrimary()) {
					System.out.print(" (primary) ");
				}
				System.out.print("\n");
			}
			System.out.println("IM addresses:");
			for (Im im : entry.getImAddresses()) {
				System.out.print(" " + im.getAddress());
				if (im.getLabel() != null) {
					System.out.print(" label:" + im.getLabel());
				}
				if (im.getRel() != null) {
					System.out.print(" rel:" + im.getRel());
				}
				if (im.getProtocol() != null) {
					System.out.print(" protocol:" + im.getProtocol());
				}
				if (im.getPrimary()) {
					System.out.print(" (primary) ");
				}
				System.out.print("\n");
			}
			System.out.println("Groups:");
			for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
				String groupHref = group.getHref();
				System.out.println("  Id: " + groupHref);
			}
			System.out.println("Extended Properties:");
			for (ExtendedProperty property : entry.getExtendedProperties()) {
				if (property.getValue() != null) {
					System.out.println("  " + property.getName() + "(value) = "
							+ property.getValue());
				} else if (property.getXmlBlob() != null) {
					System.out.println("  " + property.getName()
							+ "(xmlBlob)= " + property.getXmlBlob().getBlob());
				}
			}
			Link photoLink = entry.getContactPhotoLink();
			String photoLinkHref = photoLink.getHref();
			System.out.println("Photo Link: " + photoLinkHref);
			if (photoLink.getEtag() != null) {
				System.out.println("Contact Photo's ETag: "
						+ photoLink.getEtag());
			}
			System.out.println("Contact's ETag: " + entry.getEtag());
		}

	}

	/* This method will add a contact to that particular Google account */

	public static void main(String ar[]) {
		System.out.println("main gestartet");
		test();
	}

	/**
	 * 
	 */
	private static void test() {
		try {

			DLI_GoogleContactsConnector googleContactsAccess = new DLI_GoogleContactsConnector();
System.out.println("DLI_GoogleContactsConnector erstellt und authentifiziert");

printAllGroups(googleContactsAccess.groupsURL, googleContactsAccess.myService);

			Contact contact = new Contact();
			contact.setFirstname("Markus");
			contact.setLastname("Marzotko");
			contact.setEmail("zabc@def.gh");
			contact.setPhone("023331234567890");
			contact.setStreet("Otto-Hahn-Str. 6");
			contact.setType(ContactType.CUSTOMER);
System.out.println("Contact erstellt\n\n" + toStringWithContact(contact));			
			googleContactsAccess.createContact(contact);
System.out.println("Contact hinzugefuegt");
			Contact filter = new Contact();
//			filter.setFirstname("Thorben");
System.out.println("Filter erstellt");
System.out.println(toStringWithContact(filter));
			List<Contact> contacts = googleContactsAccess.fetchContacts(filter);
System.out.println(contacts.size() + " Kontakte runtergeladen");
			for (Contact c : contacts) {
				System.out.println(toStringWithContact(c));
			}
System.out.println("printAllContacts");
			DLI_GoogleContactsConnector.printAllContacts(googleContactsAccess.myService);


		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * Beispielcode für die suche in Google
	 * 
	 * @param myService
	 * @param startTime
	 * @throws ServiceException
	 * @throws IOException
	 */
	public static void printAllGroups(String groupsURL,
			ContactsService myService) {
		// Request the feed
		URL feedUrl = null;
		try {
			feedUrl = new URL(groupsURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ContactGroupFeed resultFeed = null;
		try {
			resultFeed = myService.getFeed(feedUrl, ContactGroupFeed.class);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		for (ContactGroupEntry groupEntry : resultFeed.getEntries()) {
			System.out.println("Atom Id: " + groupEntry.getId());
			System.out.println("Group Name: "
					+ groupEntry.getTitle().getPlainText());
			System.out.println("Last Updated: " + groupEntry.getUpdated());

			System.out.println("Extended Properties:");
			for (ExtendedProperty property : groupEntry.getExtendedProperties()) {
				if (property.getValue() != null) {
					System.out.println("  " + property.getName() + "(value) = "
							+ property.getValue());
				} else if (property.getXmlBlob() != null) {
					System.out.println("  " + property.getName()
							+ "(xmlBlob) = " + property.getXmlBlob().getBlob());
				}
			}
			System.out.println("Self Link: "
					+ groupEntry.getSelfLink().getHref());
			if (!groupEntry.hasSystemGroup()) {
				// System groups do not have an edit link
				System.out.println("Edit Link: "
						+ groupEntry.getEditLink().getHref());
				System.out.println("ETag: " + groupEntry.getEtag());
			}
			if (groupEntry.hasSystemGroup()) {
				System.out.println("System Group Id: "
						+ groupEntry.getSystemGroup().getId());
			}
		}
	}
}
