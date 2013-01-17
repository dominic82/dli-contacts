import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync;
import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress;
import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation.Address;
import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.ProcessingConditions;
import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryResponseIn;
import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressResponseMessageSync;
import com.sap.xi.appl.se.global.EmailURI;
import com.sap.xi.appl.se.global.PartyID;
import com.sap.xi.appl.se.global.RegionCode;
import com.sap.xi.appl.se.global.ServiceECCCUSTOMERADDRESSBASICDATAQRDEFAULTPROFILE;
import com.sap.xi.appl.se.global.ServiceECCSUPPLIERBASICDATABYIDQRDEFAULTPROFILE;
import com.sap.xi.appl.se.global.ServiceECCSUPPLIERSNAQRDEFAULTPROFILE;
import com.sap.xi.appl.se.global.StandardMessageFault;
import com.sap.xi.appl.se.global.SupplierBasicDataByIDQueryMessageSync;
import com.sap.xi.appl.se.global.SupplierBasicDataByIDQueryMessageSync.SupplierBasicDataSelectionByID;
import com.sap.xi.appl.se.global.SupplierBasicDataByIDQueryResponseIn;
import com.sap.xi.appl.se.global.SupplierBasicDataByIDResponseMessageSync;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryMessageSync;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryMessageSync.SupplierSimpleSelectionByNameAndAddress;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryResponseIn;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressResponseMessageSync;
import com.sap.xi.ea_hr.se.global.EmplERPSimplElmntsQryMsgS;
import com.sap.xi.ea_hr.se.global.EmplERPSimplElmntsQrySSel;
import com.sap.xi.ea_hr.se.global.EmplERPSimplElmntsQrySSelByEmplFamName;
import com.sap.xi.ea_hr.se.global.EmplERPSimplElmntsQrySSelByEmplGvnName;
import com.sap.xi.ea_hr.se.global.EmplERPSimplElmntsQrySSelByHomeAddrCityName;
import com.sap.xi.ea_hr.se.global.EmplERPSimplElmntsQrySSelByHomeAddrPostlCode;
import com.sap.xi.ea_hr.se.global.EmplERPSimplElmntsQrySSelByHomeAddrStName;
import com.sap.xi.ea_hr.se.global.EmplERPSimplElmntsRspMsgS;
import com.sap.xi.ea_hr.se.global.EmployeeAddressByEmployeeQueryMessage;
import com.sap.xi.ea_hr.se.global.EmployeeAddressByEmployeeQueryMessage.EmployeeAddressSelectionByEmployee;
import com.sap.xi.ea_hr.se.global.EmployeeAddressByEmployeeQueryResponseIn;
import com.sap.xi.ea_hr.se.global.EmployeeAddressByEmployeeResponseMessage;
import com.sap.xi.ea_hr.se.global.EmployeeERPSimpleByElementsQueryResponseIn;
import com.sap.xi.ea_hr.se.global.EmployeeID;
import com.sap.xi.ea_hr.se.global.MEDIUMName;
import com.sap.xi.ea_hr.se.global.ServiceECCEEERPSELQRDEFAULTPROFILE;
import com.sap.xi.ea_hr.se.global.ServiceECCEMPADDREMPQRDEFAULTPROFILE;
import com.sap.xi.ea_hr.se.global.WITHOUTLASTRETURNEDQueryProcessingConditions;

import dli_contacts.Contact;

public class main_sapkonnektor {

	/**
	 * @param args
	 */
	public static void main(String argv[]) {
		
		Contact tk = new Contact();
		
		List<Contact> lk = new LinkedList<Contact>();
		
		tk.setType(Contact.ContactType.EMPLOYEE);
		
		tk.setFirstname("Bahnhofstrasse 21");
		
		lk = fetchContact(tk);
		if(lk.size()>0){
		System.out.println(lk.get(0).getFirstname());
		System.out.println(lk.get(0).getLastname());
		System.out.println(lk.get(0).getCity());
		System.out.println(lk.get(0).getZipcode());
		System.out.println(lk.get(0).getCompany());
		System.out.println(lk.get(0).getStreet());
		}
		else{
			System.out.println("Liste leer");
		}
		//Kommentierten Code für Sondertestzwecke lassen
		
		/*
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
		supSelection.setSupplierAddressCountryCode("DE");
		suppquery.setSupplierSimpleSelectionByNameAndAddress(supSelection);

		SupplierSimpleByNameAndAddressResponseMessageSync result = null;

		ServiceECCSUPPLIERSNAQRDEFAULTPROFILE service = new ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();

		SupplierSimpleByNameAndAddressQueryResponseIn binding = service
				.getBindingTHTTPAHTTPECCSUPPLIERSNAQRDEFAULTPROFILE();
		BindingProvider bp = (BindingProvider) binding;
		// Map<String, Object> reqCont = bp.getRequestContext();

		// bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
		// "Webaddresse");
		
		 * bp.getRequestContext()
		 * .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
		 * "http://erp.esworkplace.sap.com/sap/bc/srt/pm/sap/ecc_supplierbasicdatabyidqr/800/default_profile/2/binding_t_http_a_http_ecc_supplierbasicdatabyidqr_default_profile"
		 * );
		 
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
		
		*/
	}

	public static Contact entferneNulls(Contact kontaktDaten) {
		
		//Die Methode entfernt alle Nullzeiger aus dem Contact Objekt und setzt dafür leere Strings ein
		
		if (kontaktDaten.getFirstname() == null) {
			kontaktDaten.setFirstname("");
		}
		if (kontaktDaten.getLastname() == null) {
			kontaktDaten.setLastname("");
		}
		if (kontaktDaten.getCity() == null) {
			kontaktDaten.setCity("");
		}
		if (kontaktDaten.getCompany() == null) {
			kontaktDaten.setCompany("");
		}
		if (kontaktDaten.getZipcode() == null) {
			kontaktDaten.setZipcode("");
		}
		if (kontaktDaten.getStreet() == null) {
			kontaktDaten.setStreet("");
		}
		if (kontaktDaten.getPhone() == null) {
			kontaktDaten.setPhone("");
		}
		if (kontaktDaten.getEmail() == null) {
			kontaktDaten.setEmail("");
		}
		if (kontaktDaten.getSapId() == null) {
			kontaktDaten.setSapId("");
		}
		return kontaktDaten;

	}

	public static List<Contact> fetchContact(Contact filter) {
		
		//Bestimmung der Art des Kontakts und Rückgabe einer Kontaktliste, die nach dem
		//übergebenden Kontakt ausgefiltert wurde
		
		switch (filter.getType()) {
		case CUSTOMER:
			return getCustomerIDs(filter);
			// Beim Customer reicht eine Methode...
			// CustomerBasicDataByIDResponseMessageSync cushilfsObjekt = new
			// CustomerBasicDataByIDResponseMessageSync();
			// cushilfsObjekt = getCustomerIDs(filter);
			// return getCustomerData(cushilfsObjekt);

		case SUPPLIER:

			SupplierSimpleByNameAndAddressResponseMessageSync suphilfsObjekt = new SupplierSimpleByNameAndAddressResponseMessageSync();
			suphilfsObjekt = getSupplierIDs(filter);
			return getSupplierData(suphilfsObjekt);

		case EMPLOYEE:
			EmplERPSimplElmntsRspMsgS emphilfsObjekt = new EmplERPSimplElmntsRspMsgS();
			emphilfsObjekt = getEmployeeIDs(filter);
			return getEmployeeData(emphilfsObjekt);

		}

		return null;

	}

	private static List<Contact> getEmployeeData(
			EmplERPSimplElmntsRspMsgS empIDList) {

		Contact kontaktEintrag = null;

		List<Contact> Kontaktliste = new LinkedList<Contact>();
		int anzahlEintraege;
		anzahlEintraege = empIDList.getEmployee().size();

		// Leere Liste abfangen und zurückgeben
		if (anzahlEintraege == 0) {
			return Kontaktliste;
		}

		// Andere Objekte
		EmployeeAddressByEmployeeResponseMessage result = new EmployeeAddressByEmployeeResponseMessage();
		EmployeeAddressByEmployeeQueryMessage empAnfrage = new EmployeeAddressByEmployeeQueryMessage();
		EmployeeAddressSelectionByEmployee empAnfrageID = new EmployeeAddressSelectionByEmployee();
		EmployeeID empID = new EmployeeID();

		// Verbindungsobjekte fertigbauen
		ServiceECCEMPADDREMPQRDEFAULTPROFILE verbindungsObjekt = new ServiceECCEMPADDREMPQRDEFAULTPROFILE();
		EmployeeAddressByEmployeeQueryResponseIn bindungDaten = verbindungsObjekt
				.getBindingTHTTPAHTTPECCEMPADDREMPQRDEFAULTPROFILE();

		BindingProvider bindungDatenCast = (BindingProvider) bindungDaten;

		// Username und Passwort setzen (Webaddresse schon im Objekt enthalten)
		bindungDatenCast.getRequestContext().put(
				BindingProvider.USERNAME_PROPERTY, "S0008266219");
		bindungDatenCast.getRequestContext().put(
				BindingProvider.PASSWORD_PROPERTY, "Fleischgans85");

		// 2 Strings:
		
		String teilName1;
		String teilName2;
		int charIndex;

		// Schleife die für alle Einträge den Webservice mit der entsprechenden
		// ID losschickt und die empfangenen Daten
		// in die Kontaktliste schreibt
		for (int i = 0; i < anzahlEintraege; i++) {

			kontaktEintrag = new Contact();
			kontaktEintrag.setType(Contact.ContactType.EMPLOYEE);

			// Supplier ID in PartyID Objekt eintragen
			empID.setValue(empIDList.getEmployee().get(i).getID());
			empAnfrageID.setEmployeeID(empID);
			empAnfrage.setEmployeeAddressSelectionByEmployee(empAnfrageID);

			// Hochreichen
			try {
				result = bindungDaten
						.employeeAddressByEmployeeQueryResponseIn(empAnfrage);
			} catch (SOAPFaultException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (com.sap.xi.ea_hr.se.global.StandardMessageFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// SAPID setzen
			kontaktEintrag.setSapId(empIDList.getEmployee().get(i).getID());
			// Vorname setzen
			kontaktEintrag.setFirstname(empIDList.getEmployee().get(i).getPersonFormattedName());
			
			charIndex = empIDList.getEmployee().get(i).getPersonFormattedName().indexOf(" ");
			teilName1 = empIDList.getEmployee().get(i).getPersonFormattedName().substring(0, charIndex);
			teilName2 = empIDList.getEmployee().get(i).getPersonFormattedName().substring(charIndex + 1);
			
			// Nachname setzen
			kontaktEintrag.setLastname(teilName2);
			// Vorname setzen		
			kontaktEintrag.setFirstname(teilName1);
			
			// Firma setzen

			kontaktEintrag.setCompany(result.getEmployee().getAddress().get(0).getAddress().getDepartmentName());
						

			
			// Stadt und Postleitzahl setzen

			kontaktEintrag.setCity(result.getEmployee().getAddress().get(0)
					.getAddress().getPhysicalAddress().getCityName());
			
			com.sap.xi.ea_hr.se.global.RegionCode regCode = new com.sap.xi.ea_hr.se.global.RegionCode();
			/*
			regCode= result.getEmployee().getAddress().get(0)
					.getAddress().getPhysicalAddress().getRegionCode();
			kontaktEintrag.setZipcode(regCode.getValue());
			*/
			// Straße und Hausnummer setzen

			kontaktEintrag.setStreet(result.getEmployee().getAddress().get(0)
					.getAddress().getPhysicalAddress().getStreetName());

			// Email und Telefon
	/*		
			if (result.getEmployee().getAddress().get(0).getAddress()
					.getCommunication().getEmail().size() > 0) {
				kontaktEintrag.setEmail(result.getEmployee().getAddress()
						.get(0).getAddress().getCommunication().getEmail()
						.get(0).getURI().getValue());
			}
			if (result.getEmployee().getAddress().get(0).getAddress()
					.getCommunication().getTelephone().size() > 0) {
				kontaktEintrag.setEmail(result.getEmployee().getAddress()
						.get(0).getAddress().getCommunication().getTelephone()
						.get(0).getNumber().getSubscriberID());
			}
		*/	
			kontaktEintrag = entferneNulls(kontaktEintrag);

			// Kontakt hinzufügen
			Kontaktliste.add(kontaktEintrag);
		}

		// TODO Auto-generated method stub
		return Kontaktliste;
	}

	/*
	 * private static List<Contact> getCustomerData(
	 * CustomerBasicDataByIDResponseMessageSync cushilfsObjekt) {
	 * 
	 * // TODO Auto-generated method stub return null; }
	 */
	private static List<Contact> getCustomerIDs(Contact filter) {
		// Diese Methode holt sich die ntwendigen Daten direkt und muss keinen
		// umweg über die IDs gehen
		CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync kundeAnfrage = new CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync();
		CustomerSelectionByNameAndAddress kundeFilter = new CustomerSelectionByNameAndAddress();
		com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation add1 = new com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation();
		Address add2 = new Address();
		com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation.Address.PhysicalAddress add3 = new com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation.Address.PhysicalAddress();
		RegionCode kundePLZ = new RegionCode();

		com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.Common com1 = new com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.Common();
		com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.Common.Name com2 = new com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.Common.Name();

		// Hier die Werte der inneren Klasse des zu sendenden Objekts mit den
		// Werten von Dominiks
		// Contacts befüllen

		// Adressdaten setzen
		add3.setCountryCode("DE");
		add3.setCityName(filter.getCity());
		kundePLZ.setValue(filter.getZipcode());
		add3.setRegionCode(kundePLZ);
		add3.setStreetName(filter.getStreet());

		// Firmennamen setzen
		com2.setFirstLineName(filter.getCompany());

		// Alles gesetzte Zeugs nach oben übergeben
		com1.setName(com2);
		add2.setPhysicalAddress(add3);
		add1.setAddress(add2);
		kundeFilter.setAddressInformation(add1);
		kundeFilter.setCommon(com1);

		// Alles dem sendenden Objekt hinzufügen
		kundeAnfrage.setCustomerSelectionByNameAndAddress(kundeFilter);
		
		ProcessingConditions anfrageProz = new ProcessingConditions();
		
		
		kundeAnfrage.setProcessingConditions(anfrageProz);

		// Verbindungs und Antwortobjekte bauen
		CustomerERPAddressBasicDataByNameAndAddressResponseMessageSync resultDaten = null;

		ServiceECCCUSTOMERADDRESSBASICDATAQRDEFAULTPROFILE verbindungsObjekt = new ServiceECCCUSTOMERADDRESSBASICDATAQRDEFAULTPROFILE();

		CustomerERPAddressBasicDataByNameAndAddressQueryResponseIn bindungDaten = verbindungsObjekt
				.getBindingTHTTPAHTTPECCCUSTOMERADDRESSBASICDATAQRDEFAULTPROFILE();

		BindingProvider bindungDatenCast = (BindingProvider) bindungDaten;

		// Username und Passwort setzen (Webaddresse schon im Objekt enthalten)
		bindungDatenCast.getRequestContext().put(
				BindingProvider.USERNAME_PROPERTY, "S0008266219");
		bindungDatenCast.getRequestContext().put(
				BindingProvider.PASSWORD_PROPERTY, "Fleischgans85");

		// Verbindung herstellen, StandardMessageFault für SAP notwendig
		try {
			resultDaten = bindungDaten
					.customerERPAddressBasicDataByNameAndAddressQueryResponseIn(kundeAnfrage);
		} catch (StandardMessageFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SOAPFaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int anzahlEintraege = resultDaten.getCustomer().size();

		Contact kontaktEintrag;
		List<Contact> Kontaktliste = new LinkedList<Contact>();

		// Schleife auf dem gegebenen Objekt aufrufen

		for (int i = 0; i < anzahlEintraege; i++) {

			kontaktEintrag = new Contact();
			kontaktEintrag.setType(Contact.ContactType.CUSTOMER);

			// SAPID setzen
			kontaktEintrag.setSapId(resultDaten.getCustomer().get(i).getID()
					.getValue());
			// Firma setzen

			kontaktEintrag.setCompany(resultDaten.getCustomer().get(i)
					.getCommon().getName().getFirstLineName());
			
			kontaktEintrag.setFirstname(resultDaten.getCustomer().get(i)
					.getCommon().getName().getFirstLineName());

			// Stadt und Postleitzahl setzen

			kontaktEintrag.setCity(resultDaten.getCustomer().get(i)
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getCityName());



			// Straße und Hausnummer setzen

			kontaktEintrag.setStreet(resultDaten.getCustomer().get(i)
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getStreetName());

			// Email und Telefon
			// Hier keine Kommunikationsdaten vorhanden

			// Kontakt hinzufügen
			kontaktEintrag = entferneNulls(kontaktEintrag);
			Kontaktliste.add(kontaktEintrag);
		}

		return Kontaktliste;

	}

	private static SupplierSimpleByNameAndAddressResponseMessageSync getSupplierIDs(
			Contact filter) {

		SupplierSimpleByNameAndAddressQueryMessageSync lieferantAnfrage = new SupplierSimpleByNameAndAddressQueryMessageSync();
		SupplierSimpleSelectionByNameAndAddress lieferantFilter = new SupplierSimpleSelectionByNameAndAddress();

		// Hier die Werte der inneren Klasse des zu sendenden Objekts mit den
		// Werten von Dominiks
		// Contacts befüllen

		lieferantFilter.setSupplierAddressCountryCode("DE");

		lieferantFilter.setSupplierName1(filter.getCompany());
		lieferantFilter.setSupplierAddressCityName(filter.getCity());
		lieferantFilter.setSupplierAddressStreetName(filter.getStreet());
		lieferantFilter.setSupplierAddressStreetPostalCode(filter.getZipcode());

		EmailURI dummerSAPEmailTyp = new EmailURI();
		dummerSAPEmailTyp.setValue(filter.getEmail());
		lieferantFilter.setSupplierAddressEMailAddress(dummerSAPEmailTyp);

		lieferantFilter.setSupplierAddressPhoneNumber(filter.getPhone());

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

	private static EmplERPSimplElmntsRspMsgS getEmployeeIDs(Contact filter) {

		EmplERPSimplElmntsQryMsgS mitarbeiterAnfrage = new EmplERPSimplElmntsQryMsgS();
		EmplERPSimplElmntsQrySSel mitarbeiterFilter = new EmplERPSimplElmntsQrySSel();
		MEDIUMName filterWert = new MEDIUMName();

		// Ein paar EmployeeObjekte bauen:
		EmplERPSimplElmntsQrySSelByEmplGvnName empVorname = new EmplERPSimplElmntsQrySSelByEmplGvnName();
		EmplERPSimplElmntsQrySSelByEmplFamName empName = new EmplERPSimplElmntsQrySSelByEmplFamName();
		EmplERPSimplElmntsQrySSelByHomeAddrCityName empStadt = new EmplERPSimplElmntsQrySSelByHomeAddrCityName();
		EmplERPSimplElmntsQrySSelByHomeAddrPostlCode empPLZ = new EmplERPSimplElmntsQrySSelByHomeAddrPostlCode();
		EmplERPSimplElmntsQrySSelByHomeAddrStName empStrasse = new EmplERPSimplElmntsQrySSelByHomeAddrStName();

		// Hier die Werte der inneren Klasse des zu sendenden Objekts mit den
		// Werten von Dominiks
		// Contacts befüllen
		// Suche nur in Deutschland durchführen

		//mitarbeiterFilter.setEmploymentCountryCode("US");

		if(filter.getLastname()!= ""){
		filterWert.setValue(filter.getLastname());
		empName.setLowerBoundaryEmployeeFamilyName(filterWert);
		mitarbeiterFilter.setSelectionByEmployeeFamilyName(empName);
		}
		if(filter.getFirstname()!= ""){
		filterWert.setValue(filter.getFirstname());
		empVorname.setLowerBoundaryEmployeeGivenName(filterWert);
		mitarbeiterFilter.setSelectionByEmployeeGivenName(empVorname);
		}
		if(filter.getCity()!= ""){
		filterWert.setValue(filter.getCity());
		empStadt.setLowerBoundaryEmployeeHomeAddressCityName(filterWert);
		mitarbeiterFilter.setSelectionByEmployeeHomeAddressCityName(empStadt);
		
		}
		if(filter.getZipcode()!= ""){
		empPLZ.setLowerBoundaryEmployeeHomeAddressPostalCode(filter
				.getZipcode());
		mitarbeiterFilter.setSelectionByEmployeeHomeAddressPostalCode(empPLZ);
		}
		if(filter.getStreet()!= ""){
		empStrasse.setLowerBoundaryEmployeeHomeAddressStreetName(filter
				.getStreet());
		mitarbeiterFilter
				.setSelectionByEmployeeHomeAddressStreetName(empStrasse);
		}
			
		
		// Alles dem sendenden Objekt hinzufügen
		mitarbeiterAnfrage
				.setEmployeeSimpleSelectionByElements(mitarbeiterFilter);
		
		WITHOUTLASTRETURNEDQueryProcessingConditions prozessAnfrage = new WITHOUTLASTRETURNEDQueryProcessingConditions();
		
		prozessAnfrage.setQueryHitsMaximumNumberValue(100);
		mitarbeiterAnfrage.setQueryProcessingConditions(prozessAnfrage);
		
		
		
		// Verbindungs und Antwortobjekte bauen
		EmplERPSimplElmntsRspMsgS result = null;

		ServiceECCEEERPSELQRDEFAULTPROFILE verbindungsObjekt = new ServiceECCEEERPSELQRDEFAULTPROFILE();

		EmployeeERPSimpleByElementsQueryResponseIn bindungDaten = verbindungsObjekt
				.getBindingTHTTPAHTTPECCEEERPSELQRDEFAULTPROFILE();

		BindingProvider bindungDatenCast = (BindingProvider) bindungDaten;

		// Username und Passwort setzen (Webaddresse schon im Objekt enthalten)
		bindungDatenCast.getRequestContext().put(
				BindingProvider.USERNAME_PROPERTY, "S0008266219");
		bindungDatenCast.getRequestContext().put(
				BindingProvider.PASSWORD_PROPERTY, "Fleischgans85");
	

		// Verbindung herstellen, StandardMessageFault für SAP notwendig
		try {
			result = bindungDaten
					.employeeERPSimpleByElementsQueryResponseIn(mitarbeiterAnfrage);
		} catch (SOAPFaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fehler1");
		} catch (com.sap.xi.ea_hr.se.global.StandardMessageFault e) {
			// catch hier mit anderem messagefault
			e.printStackTrace();
			System.out.println("Fehler2");
		}
		// Ergebnis zurückgeben
		//TODO Test löschen
		if(result.getEmployee().isEmpty()){
			System.out.println("Problem");
		}
		
		System.out.println(result.getResponseProcessingConditions().getReturnedQueryHitsNumberValue());
		
		
		return result;
	}

	private static List<Contact> getSupplierData(
			SupplierSimpleByNameAndAddressResponseMessageSync supplierIDList) {

		Contact kontaktEintrag = null;

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

			kontaktEintrag = new Contact();
			kontaktEintrag.setType(Contact.ContactType.SUPPLIER);

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
			
			kontaktEintrag.setFirstname(supplierIDList.getSupplier().get(i)
					.getBasicData().getCommon().getName().getFirstLineName());

			// Stadt und Postleitzahl setzen

			kontaktEintrag.setCity(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getCityName());

			kontaktEintrag.setZipcode(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress().getRegionCode());

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
			// Nulls löschen
			kontaktEintrag = entferneNulls(kontaktEintrag);
			//Kontakt hinzufügen
			Kontaktliste.add(kontaktEintrag);
		}

		return Kontaktliste;
	}

}
