package dli_googleContactsConnector.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.Case;

import dli_contacts.Contact;
import dli_googleContactsConnector.shared.FieldVerifier;

import com.google.gdata.client.Query;
import com.google.gdata.client.Service;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.City;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.PostCode;
import com.google.gdata.data.extensions.Street;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.XmlBlob;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DLI_GoogleContactsConnector implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private static String customerGroupURL = "http://www.google.com/m8/feeds/groups/tseelandho@web.de/base/Customer";
	private static String supplierGroupURL = "http://www.google.com/m8/feeds/groups/tseelandho@web.de/base/Supplier";
	private static String employeeGroupURL = "http://www.google.com/m8/feeds/groups/tseelandho@web.de/base/Employee";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private String userId;

	public static ContactEntry createContact(ContactsService myService,
			Contact contactInfo) throws ServiceException, IOException {
		if (!isValid(contactInfo)) {
			return null;
		}
		// Create the entry to insert
		ContactEntry contact = new ContactEntry();
		contact.setTitle(new PlainTextConstruct(contactInfo.getFirstname()
				+ contactInfo.getLastname()));
		contact.setContent(new PlainTextConstruct(""));

		// Name
		Name name = new Name();
		name.setFamilyName(new FamilyName(contactInfo.getLastname(), null));
		name.setGivenName(new GivenName(contactInfo.getFirstname(), null));
		contact.setName(name);

		// Email >> es kann NICHT NUR eine geben
		Email primaryMail = new Email();
		primaryMail.setAddress(contactInfo.getEmail());
		primaryMail.setRel("http://schemas.google.com/g/2005#home");// Email Typ
		primaryMail.setPrimary(true);
		contact.addEmailAddress(primaryMail);

		// Telefon >> es kann NICHT NUR eine geben
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setPhoneNumber(contactInfo.getPhone());
		phoneNumber.setPrimary(true);
		contact.addPhoneNumber(phoneNumber);

		// Adresse >> es kann NICHT NUR eine geben
		StructuredPostalAddress adresse = new StructuredPostalAddress();
		adresse.setCity(new City(contactInfo.getCity()));
		adresse.setPostcode(new PostCode(contactInfo.getZipcode()));
		adresse.setStreet(new Street(contactInfo.getStreet()));
		adresse.setPrimary(true);
		contact.addStructuredPostalAddress(adresse);

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

		// Ask the service to insert the new entry
		URL postUrl = new URL(
				"https://www.google.com/m8/feeds/contacts/liz@gmail.com/full");
		return myService.insert(postUrl, contact);
	}

	/**
	 * This method will authenticate the user credentials passed to it and
	 * returns an instance of ContactService class.
	 * 
	 * @throws AuthenticationException
	 */
	public ContactsService authenticateId(String userid, String password)
			throws AuthenticationException {

		ContactsService contactService = null;
		contactService = new ContactsService("DLI IdesMigration");
		contactService.setUserCredentials(userid, password);
		this.userId = userid;
		return contactService;

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
	public List<Contact> fetchContacts(Contact filter, Service myService)
			throws IOException, ServiceException {

		// Create query and submit a request
		URL feedUrl = new URL(
				"https://www.google.com/m8/feeds/contacts/liz@gmail.com/full");
		Query myQuery = new Query(feedUrl);
		
		String groupId = null;
		switch (filter.getType()) {
		case CUSTOMER:
			groupId = "Customer";
			break;
		case SUPPLIER:
			groupId = "Supplier";
			break;
		case EMPLOYEE:
			groupId = "Employee";
			break;

		default:
			break;
		}
		myQuery.setStringCustomParameter("group", groupId);
		
		//submit request
		ContactFeed resultFeed = myService.query(myQuery, ContactFeed.class);
		
		//sort out
		List<ContactEntry> ceResults = resultFeed.getEntries();
		List<Contact> results = new ArrayList<Contact>();
		for(ContactEntry ce : ceResults){
			Contact accepted = makeContact(ce);
			if(filter(filter,accepted)){
				results.add(accepted);
			}
		}

		return results;
	}

	private boolean filter(Contact filter, Contact accepted) {
		// TODO Auto-generated method stub
		return false;
	}

	private Contact makeContact(ContactEntry ce) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * This method will print details of all the contacts available in that
	 * particular Google account.
	 */
	public void printAllContacts(ContactsService myService)
			throws ServiceException, IOException {

		// Request the feed
		URL feedUrl = new URL(
				"https://www.google.com/m8/feeds/contacts/default/full");

		ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);

		// Print the results
		System.out.println(resultFeed.getTitle().getPlainText());
		for (int i = 0; i < resultFeed.getEntries().size(); i++) {
			ContactEntry entry = resultFeed.getEntries().get(i);
			if (entry.hasName()) {
				Name name = entry.getName();
				if (name.hasFullName()) {
					String fullNameToDisplay = name.getFullName().getValue();
					if (name.getFullName().hasYomi()) {
						fullNameToDisplay += " ("
								+ name.getFullName().getYomi() + ")";
					}
					System.out.println("\t\t" + fullNameToDisplay);
				} else {
					System.out.println("\t\t (no full name found)");
				}

				if (name.hasNamePrefix()) {
					System.out
							.println("\t\t" + name.getNamePrefix().getValue());
				} else {
					System.out.println("\t\t (no name prefix found)");
				}
				if (name.hasGivenName()) {
					String givenNameToDisplay = name.getGivenName().getValue();
					if (name.getGivenName().hasYomi()) {
						givenNameToDisplay += " ("
								+ name.getGivenName().getYomi() + ")";
					}
					System.out.println("\t\t" + givenNameToDisplay);
				} else {
					System.out.println("\t\t (no given name found)");
				}

				if (name.hasAdditionalName()) {
					String additionalNameToDisplay = name.getAdditionalName()
							.getValue();
					if (name.getAdditionalName().hasYomi()) {
						additionalNameToDisplay += " ("
								+ name.getAdditionalName().getYomi() + ")";
					}
					System.out.println("\t\t" + additionalNameToDisplay);
				} else {
					System.out.println("\t\t (no additional name found)");
				}

				if (name.hasFamilyName()) {
					String familyNameToDisplay = name.getFamilyName()
							.getValue();
					if (name.getFamilyName().hasYomi()) {
						familyNameToDisplay += " ("
								+ name.getFamilyName().getYomi() + ")";
					}
					System.out.println("\t\t" + familyNameToDisplay);
				} else {
					System.out.println("\t\t (no family name found)");
				}

				if (name.hasNameSuffix()) {
					System.out
							.println("\t\t" + name.getNameSuffix().getValue());
				} else {
					System.out.println("\t\t (no name suffix found)");
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

	public void createContact(ContactsService myService, String fullName,
			String givenName, String familyName, String notes, String emailId,
			String favSports) throws ServiceException, IOException {
		// Create the entry to insert
		ContactEntry contact = new ContactEntry();
		Name name = new Name();
		final String NO_YOMI = null;
		name.setFullName(new FullName(fullName, NO_YOMI));
		name.setGivenName(new GivenName(givenName, NO_YOMI));
		name.setFamilyName(new FamilyName(familyName, NO_YOMI));
		contact.setName(name);
		contact.setContent(new PlainTextConstruct(notes));

		Email primaryMail = new Email();
		primaryMail.setAddress(emailId);
		primaryMail.setRel("http://schemas.google.com/g/2005#home");
		primaryMail.setPrimary(true);
		contact.addEmailAddress(primaryMail);

		ExtendedProperty sportsProperty = new ExtendedProperty();
		sportsProperty.setName("sports");
		sportsProperty.setValue(favSports);
		contact.addExtendedProperty(sportsProperty);

		// Ask the service to insert the new entry
		URL postUrl = new URL(
				"https://www.google.com/m8/feeds/contacts/default/full");
		myService.insert(postUrl, contact);
	}

	public static void main(String ar[]) {
		try {

			DLI_GoogleContactsConnector googleContactsAccess = new DLI_GoogleContactsConnector();

			ContactsService contactSrv = googleContactsAccess.authenticateId(
					"username", "password");

			googleContactsAccess.printAllContacts(contactSrv);

			googleContactsAccess.createContact(contactSrv, "fullname",
					"givenname", "familyname", "notes", "example@example.com",
					"fav sports");

		} catch (MalformedURLException ex) {
			System.out.println(ex);
		} catch (IOException ex) {
			System.out.println(ex);
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
	public static void printDateMinQueryResults(ContactsService myService,
			DateTime startTime) throws ServiceException, IOException {
		// Create query and submit a request
		URL feedUrl = new URL(
				"https://www.google.com/m8/feeds/contacts/liz@gmail.com/full");
		Query myQuery = new Query(feedUrl);
		myQuery.setUpdatedMin(startTime);
		ContactFeed resultFeed = myService.query(myQuery, ContactFeed.class);

		// Print the results
		System.out.println(resultFeed.getTitle().getPlainText()
				+ " contacts updated on or after " + startTime);
		for (int i = 0; i < resultFeed.getEntries().size(); i++) {
			ContactEntry entry = resultFeed.getEntries().get(i);
			System.out.println("\t" + entry.getTitle().getPlainText());
			System.out.println("\t" + entry.getUpdated().toStringRfc822());
		}
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

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// TODO Implementieren
	}
}
