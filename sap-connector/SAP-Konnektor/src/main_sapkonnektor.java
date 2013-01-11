import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import com.sap.xi.appl.se.global.EmailURI;
import com.sap.xi.appl.se.global.PartyID;
import com.sap.xi.appl.se.global.ServiceECCSUPPLIERBASICDATABYIDQRDEFAULTPROFILE;
import com.sap.xi.appl.se.global.ServiceECCSUPPLIERSNAQRDEFAULTPROFILE;
import com.sap.xi.appl.se.global.StandardMessageFault;
import com.sap.xi.appl.se.global.SupplierBasicDataByIDQueryMessageSync;
import com.sap.xi.appl.se.global.SupplierBasicDataByIDQueryMessageSync.SupplierBasicDataSelectionByID;
import com.sap.xi.appl.se.global.SupplierBasicDataByIDQueryResponseIn;
import com.sap.xi.appl.se.global.SupplierBasicDataByIDResponseMessageSync;
import com.sap.xi.appl.se.global.SupplierBasicDataByIDResponseMessageSync.Supplier.BasicData.CommunicationData.Address;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryMessageSync;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryMessageSync.SupplierSimpleSelectionByNameAndAddress;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryResponseIn;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressResponseMessageSync;

import dli_contacts.Contact;

public class main_sapkonnektor {

	/**
	 * @param args
	 */
	public static void main(String argv[]) {

		// BindingProvider bs = (BindingProvider) ws;

		// Binding ha = bs.getBinding();

		// Testcode
		SupplierSimpleByNameAndAddressQueryMessageSync suppquery = new SupplierSimpleByNameAndAddressQueryMessageSync();
		SupplierSimpleSelectionByNameAndAddress supSelection = new SupplierSimpleSelectionByNameAndAddress();

		// supSelection.setXXX();
		// Hier die Werte des zu sendenden Objekts mit den Werten von Dominiks
		// Contacts befüllen
		// supSelection.setSupplierName1("");
		// supSelection.setSupplierName2("Sa");
		supSelection.setSupplierAddressCountryCode("US");
		suppquery.setSupplierSimpleSelectionByNameAndAddress(supSelection);

		SupplierSimpleByNameAndAddressResponseMessageSync result = null;

		ServiceECCSUPPLIERSNAQRDEFAULTPROFILE service = new ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();

		SupplierSimpleByNameAndAddressQueryResponseIn binding = service
				.getBindingTHTTPAHTTPECCSUPPLIERSNAQRDEFAULTPROFILE();
		BindingProvider bp = (BindingProvider) binding;
		// Map<String, Object> reqCont = bp.getRequestContext();

		// bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
		// "Webaddresse");
		/*
		 * bp.getRequestContext()
		 * .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
		 * "http://erp.esworkplace.sap.com/sap/bc/srt/pm/sap/ecc_supplierbasicdatabyidqr/800/default_profile/2/binding_t_http_a_http_ecc_supplierbasicdatabyidqr_default_profile"
		 * );
		 */
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
				"S0008266219");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				"Fleischgans85");

		try {
			result = binding
					.supplierSimpleByNameAndAddressQueryResponseIn(suppquery);
		} catch (StandardMessageFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SOAPFaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// result.getSupplier().get(0).getBasicData().getCommon().getName();

		result.getSupplier().get(8).getBasicData().getCommon().getName()
				.getFirstLineName();

		System.out.println(result.getSupplier().get(8).getBasicData()
				.getCommon().getName().getFirstLineName());

		System.out.println(result.getSupplier().get(8).getID().getValue());

		Contact xta = new Contact();

		// ContactType = SUPPLIER oder CUSTOMER oder EMPLOYEE

		xta.setType(Contact.ContactType.SUPPLIER);

		List<Contact> Kontaktliste = null;

		Kontaktliste = getSupplierData(result);

		if (Kontaktliste.isEmpty()) {
		} else {
			System.out.println(Kontaktliste.get(0).getStreet());
		}
	}

	public static List<Contact> fetchContact(Contact filter) {

		switch (filter.getType()) {
		case CUSTOMER:

			break;
		case SUPPLIER:

			SupplierSimpleByNameAndAddressResponseMessageSync hilfsObjekt = new SupplierSimpleByNameAndAddressResponseMessageSync();
			hilfsObjekt = getSupplierIDs(filter);
			return getSupplierData(hilfsObjekt);

		case EMPLOYEE:

			break;
		}

		return null;

	}

	private static SupplierSimpleByNameAndAddressResponseMessageSync getSupplierIDs(
			Contact filter) {

		SupplierSimpleByNameAndAddressQueryMessageSync lieferantAnfrage = new SupplierSimpleByNameAndAddressQueryMessageSync();
		SupplierSimpleSelectionByNameAndAddress lieferantFilter = new SupplierSimpleSelectionByNameAndAddress();

		// Hier die Werte der inneren Klasse des zu sendenden Objekts mit den
		// Werten von Dominiks
		// Contacts befüllen
		// TODO Testfall herausnehmen
		if (filter.getFirstname() == "Test") {
			lieferantFilter.setSupplierAddressCountryCode("US");
		} else {
			lieferantFilter.setSupplierName1(filter.getFirstname());
			lieferantFilter.setSupplierAddressCityName(filter.getCity());
			lieferantFilter.setSupplierAddressStreetName(filter.getStreet());
			lieferantFilter.setSupplierAddressStreetPostalCode(filter
					.getZipcode());

			EmailURI dummerSAPEmailTyp = new EmailURI();
			dummerSAPEmailTyp.setValue(filter.getEmail());
			lieferantFilter.setSupplierAddressEMailAddress(dummerSAPEmailTyp);

			lieferantFilter.setSupplierAddressPhoneNumber(filter.getPhone());
		}
		// Alles dem sendenden Objekt hinzufügen
		lieferantAnfrage
				.setSupplierSimpleSelectionByNameAndAddress(lieferantFilter);

		// Verbindungs und Antwortobjekte bauen
		SupplierSimpleByNameAndAddressResponseMessageSync result = null;

		ServiceECCSUPPLIERSNAQRDEFAULTPROFILE verbindungsObjekt = new ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();

		SupplierSimpleByNameAndAddressQueryResponseIn bindungDaten = verbindungsObjekt
				.getBindingTHTTPAHTTPECCSUPPLIERSNAQRDEFAULTPROFILE();

		BindingProvider bindungDatenCast = (BindingProvider) bindungDaten;

		// Username und Passwort setzen (Webaddresse schon im Objekt enthalten)
		bindungDatenCast.getRequestContext().put(
				BindingProvider.USERNAME_PROPERTY, "S0008266219");
		bindungDatenCast.getRequestContext().put(
				BindingProvider.PASSWORD_PROPERTY, "Fleischgans85");

		// Verbindung herstellen, StandardMessageFault für SAP notwendig
		try {
			result = bindungDaten
					.supplierSimpleByNameAndAddressQueryResponseIn(lieferantAnfrage);
		} catch (StandardMessageFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SOAPFaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Ergebnis zurückgeben
		return result;
	}

	private static SupplierSimpleByNameAndAddressResponseMessageSync getEmployeeIDs(
			Contact filter) {

		// EmplERPSimplElmntsQryMsgS angestellterAnfrage = new
		// EmplERPSimplElmntsQryMsgS();
		// EmplERPSimplElmntsQrySSel angestellterFilter = new
		// EmplERPSimplElmntsQrySSel();

		// Hier die Werte der inneren Klasse des zu sendenden Objekts mit den
		// Werten von Dominiks
		// Contacts befüllen
		/*
		 * angestellterFilter.setSupplierName1(filter.getFirstname());
		 * angestellterFilter.setSupplierAddressCityName(filter.getCity());
		 * lieferantFilter.setSupplierAddressStreetName(filter.getStreet());
		 * lieferantFilter
		 * .setSupplierAddressStreetPostalCode(filter.getZipcode());
		 * 
		 * EmailURI dummerSAPEmailTyp = new EmailURI();
		 * dummerSAPEmailTyp.setValue(filter.getEmail());
		 * lieferantFilter.setSupplierAddressEMailAddress(dummerSAPEmailTyp);
		 * 
		 * lieferantFilter.setSupplierAddressPhoneNumber(filter.getPhone());
		 * 
		 * //Alles dem sendenden Objekt hinzufügen
		 * lieferantAnfrage.setSupplierSimpleSelectionByNameAndAddress
		 * (lieferantFilter);
		 * 
		 * 
		 * //Verbindungs und Antwortobjekte bauen
		 * SupplierSimpleByNameAndAddressResponseMessageSync result = null;
		 * 
		 * ServiceECCSUPPLIERSNAQRDEFAULTPROFILE verbindungsObjekt = new
		 * ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();
		 * 
		 * SupplierSimpleByNameAndAddressQueryResponseIn bindungDaten =
		 * verbindungsObjekt
		 * .getBindingTHTTPAHTTPECCSUPPLIERSNAQRDEFAULTPROFILE();
		 * BindingProvider bindungDatenCast = (BindingProvider) bindungDaten;
		 * 
		 * 
		 * // Username und Passwort setzen (Webaddresse schon im Objekt
		 * enthalten) bindungDatenCast.getRequestContext().put(BindingProvider.
		 * USERNAME_PROPERTY, "S0008266219");
		 * bindungDatenCast.getRequestContext(
		 * ).put(BindingProvider.PASSWORD_PROPERTY, "Fleischgans85");
		 * 
		 * // Verbindung herstellen, StandardMessageFault für SAP notwendig try
		 * { result = bindungDaten
		 * .supplierSimpleByNameAndAddressQueryResponseIn(lieferantAnfrage); }
		 * catch (StandardMessageFault e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (SOAPFaultException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		// Ergebnis zurückgeben
		SupplierSimpleByNameAndAddressResponseMessageSync result = null;
		return result;
	}

	private static List<Contact> getSupplierData(
			SupplierSimpleByNameAndAddressResponseMessageSync supplierIDList) {

		Contact kontaktEintrag = new Contact();
		kontaktEintrag.setType(Contact.ContactType.SUPPLIER);

		List<Contact> Kontaktliste = new LinkedList<Contact>();
		int anzahlEintraege;
		anzahlEintraege = supplierIDList.getSupplier().size();

		// Leere Supplierliste abfangen und zurückgeben
		if (anzahlEintraege == 0) {
			return Kontaktliste;
		}

		// Andere Objekte
		SupplierBasicDataByIDResponseMessageSync result = new SupplierBasicDataByIDResponseMessageSync();
		SupplierBasicDataByIDQueryMessageSync supplierAnfrage = new SupplierBasicDataByIDQueryMessageSync();
		SupplierBasicDataSelectionByID supplierAnfrageID = new SupplierBasicDataSelectionByID();
		PartyID supPartyID = new PartyID();

		// Verbindungsobjekte fertigbauen
		ServiceECCSUPPLIERBASICDATABYIDQRDEFAULTPROFILE verbindungsObjekt = new ServiceECCSUPPLIERBASICDATABYIDQRDEFAULTPROFILE();
		SupplierBasicDataByIDQueryResponseIn bindungDaten = verbindungsObjekt
				.getBindingTHTTPAHTTPECCSUPPLIERBASICDATABYIDQRDEFAULTPROFILE();

		BindingProvider bindungDatenCast = (BindingProvider) bindungDaten;

		// Username und Passwort setzen (Webaddresse schon im Objekt enthalten)
		bindungDatenCast.getRequestContext().put(
				BindingProvider.USERNAME_PROPERTY, "S0008266219");
		bindungDatenCast.getRequestContext().put(
				BindingProvider.PASSWORD_PROPERTY, "Fleischgans85");

		// Aufbau

		// Schleife die für alle Einträge den Webservice mit der entsprechenden
		// ID losschickt und die empfangenen Daten
		// in die Kontaktliste schreibt
		for (int i = 0; i < anzahlEintraege; i++) {

			// Supplier ID in PartyID Objekt eintragen
			supPartyID.setValue(supplierIDList.getSupplier().get(i).getID()
					.getValue());
			supplierAnfrageID.setSupplierID(supPartyID);
			supplierAnfrage
					.setSupplierBasicDataSelectionByID(supplierAnfrageID);

			// Hochreichen
			try {
				result = bindungDaten
						.supplierBasicDataByIDQueryResponseIn(supplierAnfrage);
			} catch (StandardMessageFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SOAPFaultException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// SAPID setzen
			kontaktEintrag.setSapId(supplierIDList.getSupplier().get(i).getID()
					.getValue());
			// Firma setzen
			kontaktEintrag.setCompany(supplierIDList.getSupplier().get(i)
					.getBasicData().getCommon().getName().getFirstLineName());
			// Stadt und Postleitzahl setzen
			kontaktEintrag.setCity(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getCityName());
			kontaktEintrag.setZipcode(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getCompanyPostalCode());
			// Straße und Hausnummer setzen
			kontaktEintrag.setStreet(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getStreetName());

			// Email und Telefon
			if (result.getSupplier().getBasicData().getCommunicationData()
					.getAddress().getCommunication().getTelephone().size() > 0) {
				kontaktEintrag.setPhone(result.getSupplier().getBasicData()
						.getCommunicationData().getAddress().getCommunication()
						.getTelephone().get(0).getNumber().getSubscriberID());
			}
			if (result.getSupplier().getBasicData().getCommunicationData()
					.getAddress().getCommunication().getEmail().size() > 0) {
				kontaktEintrag.setEmail(result.getSupplier().getBasicData()
						.getCommunicationData().getAddress().getCommunication()
						.getEmail().get(0).getAddress().getValue());
			}
			// Kontakt hinzufügen
			Kontaktliste.add(kontaktEintrag);
		}

		return Kontaktliste;
	}

}
