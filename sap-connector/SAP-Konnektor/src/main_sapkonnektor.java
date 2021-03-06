import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync;
import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress;
import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.ProcessingConditions;
import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryResponseIn;
import com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressResponseMessageSync;
import com.sap.xi.appl.se.global.EmailURI;
import com.sap.xi.appl.se.global.PartyID;
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
import com.sap.xi.ea_hr.se.global.EmplERPSimplElmntsQrySSelByEmplTtl;
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
		
		//tk.setFirstname("Anja");
		//tk.setLastname("Braunstein");
		//tk.setCity("Karlsruhe");
		//tk.setZipcode("76133");
		//tk.setStreet("Bahnhofstrasse 21");
		
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

	}

	public static List<Contact> fetchContact(Contact filter) {
		
		//Bestimmung der Art des Kontakts und R�ckgabe einer Kontaktliste, die nach dem
		//�bergebenden Kontakt ausgefiltert wurde
		
		switch (filter.getType()) {
		case CUSTOMER:
			if(!filter.getFirstname().isEmpty()||!filter.getLastname().isEmpty()){
				List<Contact> Kontaktliste = new LinkedList<Contact>();
				return Kontaktliste;
			}
			return getCustomerIDs(filter);
			// Beim Customer reicht eine Methode...
			// CustomerBasicDataByIDResponseMessageSync cushilfsObjekt = new
			// CustomerBasicDataByIDResponseMessageSync();
			// cushilfsObjekt = getCustomerIDs(filter);
			// return getCustomerData(cushilfsObjekt);

		case SUPPLIER:
			//Spezialfall Supplier wird Vorname und Nachname angegeben
			if(!filter.getFirstname().isEmpty()||!filter.getLastname().isEmpty()){
				List<Contact> Kontaktliste = new LinkedList<Contact>();
				return Kontaktliste;
			}
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

		// Leere Liste abfangen und zur�ckgeben
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

		// 2 Strings, um zusammengeschriebenen Namen zu trennen:
		
		String teilName1;
		String teilName2;
		int charIndex;
		int maxIndex;

		// Schleife die f�r alle Eintr�ge den Webservice mit der entsprechenden
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
			if(empIDList.getEmployee().get(i).getPersonFormattedName()!= null){
			
			maxIndex = 	empIDList.getEmployee().get(i).getPersonFormattedName().length();
			if(maxIndex > 0){
			
			if(empIDList.getEmployee().get(i).getPersonFormattedName().contains(" ")){	
			charIndex = empIDList.getEmployee().get(i).getPersonFormattedName().indexOf(" ");
			teilName1 = empIDList.getEmployee().get(i).getPersonFormattedName().substring(0, charIndex);
			if(charIndex + 1 <= maxIndex){
			teilName2 = empIDList.getEmployee().get(i).getPersonFormattedName().substring(charIndex + 1);
			// Nachname setzen
			kontaktEintrag.setLastname(teilName2);
			}
			// Vorname setzen		
			kontaktEintrag.setFirstname(teilName1);
			  }
			 }
			}
			
			if(result.getEmployee()!=null ){
			
			// Firma setzen
			if(result.getEmployee().getAddress().get(0).getAddress().getDepartmentName()!= null)
			kontaktEintrag.setCompany(result.getEmployee().getAddress().get(0).getAddress().getDepartmentName());
						

			
			// Stadt und Postleitzahl setzen
			
			
			if(result.getEmployee().getAddress().get(0)
					.getAddress().getPhysicalAddress().getCityName()!= null)
			kontaktEintrag.setCity(result.getEmployee().getAddress().get(0)
					.getAddress().getPhysicalAddress().getCityName());
			if(result.getEmployee().getAddress().get(0)
			.getAddress().getPhysicalAddress().getStreetPostalCode()!=null)
			kontaktEintrag.setZipcode(result.getEmployee().getAddress().get(0)
			.getAddress().getPhysicalAddress().getStreetPostalCode());
			
			// Stra�e und Hausnummer setzen
			if(result.getEmployee().getAddress().get(0)
					.getAddress().getPhysicalAddress().getStreetName()!=null);
			kontaktEintrag.setStreet(result.getEmployee().getAddress().get(0)
					.getAddress().getPhysicalAddress().getStreetName());
			
			}
			
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

			// Kontakt hinzuf�gen
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
		// umweg �ber die IDs gehen
		List<Contact> Kontaktliste = new LinkedList<Contact>();
			
		
		CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync kundeAnfrage = new CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync();
		CustomerSelectionByNameAndAddress kundeFilter = new CustomerSelectionByNameAndAddress();
		
		com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation add1 = new com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation();
		com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation.Address add2 = new com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation.Address();
		com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation.Address.PhysicalAddress add3 = new com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.AddressInformation.Address.PhysicalAddress();
		com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.Common com1 = new com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.Common();
		com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.Common.Name com2 = new com.sap.xi.appl.se.global.CustomerERPAddressBasicDataByNameAndAddressQueryMessageSync.CustomerSelectionByNameAndAddress.Common.Name();

		// Hier die Werte der inneren Klasse des zu sendenden Objekts mit den
		// Werten von Dominiks
		// Contacts bef�llen

		// Adressdaten setzen
		add3.setCountryCode("DE");
		add3.setCityName(filter.getCity());
		add3.setStreetPostalCode(filter.getZipcode());
		add3.setStreetName(filter.getStreet());

		// Firmennamen setzen
		com2.setFirstLineName(filter.getCompany());

		// Alles gesetzte Zeugs nach oben �bergeben
		com1.setName(com2);
		add2.setPhysicalAddress(add3);
		add1.setAddress(add2);
		kundeFilter.setAddressInformation(add1);
		kundeFilter.setCommon(com1);

		// Alles dem sendenden Objekt hinzuf�gen
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

		// Verbindung herstellen, StandardMessageFault f�r SAP notwendig
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
			
			kontaktEintrag.setZipcode(resultDaten.getCustomer().get(i)
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getStreetPostalCode());
			
			

			// Stra�e und Hausnummer setzen

			kontaktEintrag.setStreet(resultDaten.getCustomer().get(i)
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getStreetName());

			// Email und Telefon
			// Hier keine Kommunikationsdaten vorhanden

			// Kontakt hinzuf�gen
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
		// Contacts bef�llen

		lieferantFilter.setSupplierAddressCountryCode("DE");

		lieferantFilter.setSupplierName1(filter.getCompany());
		lieferantFilter.setSupplierAddressCityName(filter.getCity());
		lieferantFilter.setSupplierAddressStreetName(filter.getStreet());
		lieferantFilter.setSupplierAddressStreetPostalCode(filter.getZipcode());

		EmailURI dummerSAPEmailTyp = new EmailURI();
		dummerSAPEmailTyp.setValue(filter.getEmail());
		lieferantFilter.setSupplierAddressEMailAddress(dummerSAPEmailTyp);

		lieferantFilter.setSupplierAddressPhoneNumber(filter.getPhone());

		// Alles dem sendenden Objekt hinzuf�gen
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

		// Verbindung herstellen, StandardMessageFault f�r SAP notwendig
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

		// Ergebnis zur�ckgeben
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
		// Contacts bef�llen
		// Suche nur in Deutschland durchf�hren

		/*
		EmplERPSimplElmntsQrySSelByEmplTtl titeltest = new EmplERPSimplElmntsQrySSelByEmplTtl();
		filterWert.setValue("Dr.");
		titeltest.setLowerBoundaryEmployeeTitle(filterWert);
		mitarbeiterFilter.setSelectionByEmployeeTitle(titeltest);
		*/
		
		if(!filter.getLastname().isEmpty()){
		filterWert.setValue(filter.getLastname());
		empName.setLowerBoundaryEmployeeFamilyName(filterWert);
		mitarbeiterFilter.setSelectionByEmployeeFamilyName(empName);
		}
		if(!filter.getFirstname().isEmpty()){
		filterWert.setValue(filter.getFirstname());
		empVorname.setLowerBoundaryEmployeeGivenName(filterWert);
		mitarbeiterFilter.setSelectionByEmployeeGivenName(empVorname);
		}
		if(!filter.getCity().isEmpty()){
		filterWert.setValue(filter.getCity());
		empStadt.setLowerBoundaryEmployeeHomeAddressCityName(filterWert);
		mitarbeiterFilter.setSelectionByEmployeeHomeAddressCityName(empStadt);
		
		
		}
		if(!filter.getZipcode().isEmpty()){
		empPLZ.setLowerBoundaryEmployeeHomeAddressPostalCode(filter
				.getZipcode());
		mitarbeiterFilter.setSelectionByEmployeeHomeAddressPostalCode(empPLZ);
		}
		if(!filter.getStreet().isEmpty()){
		empStrasse.setLowerBoundaryEmployeeHomeAddressStreetName(filter
				.getStreet());
		mitarbeiterFilter
				.setSelectionByEmployeeHomeAddressStreetName(empStrasse);
		}
			
		
		// Alles dem sendenden Objekt hinzuf�gen
		mitarbeiterAnfrage
				.setEmployeeSimpleSelectionByElements(mitarbeiterFilter);
		
		//Maximale Anzahl der Eintr�ge auf 100 begrenzen
		WITHOUTLASTRETURNEDQueryProcessingConditions prozessAnfrage = new WITHOUTLASTRETURNEDQueryProcessingConditions();
		
		prozessAnfrage.setQueryHitsMaximumNumberValue(10000);
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
	

		// Verbindung herstellen, StandardMessageFault f�r SAP notwendig
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
		// Ergebnis zur�ckgeben
		//TODO Test l�schen
		if(result.getEmployee().isEmpty()){
			System.out.println("Problem");
		}
		//TODO Test l�schen
		System.out.println(result.getResponseProcessingConditions().getReturnedQueryHitsNumberValue());
		
		
		return result;
	}

	private static List<Contact> getSupplierData(
			SupplierSimpleByNameAndAddressResponseMessageSync supplierIDList) {

		Contact kontaktEintrag = null;

		List<Contact> Kontaktliste = new LinkedList<Contact>();
		int anzahlEintraege;
		anzahlEintraege = supplierIDList.getSupplier().size();

		// Leere Supplierliste abfangen und zur�ckgeben
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

		// Schleife die f�r alle Eintr�ge den Webservice mit der entsprechenden
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
					.getAddressInformation().getAddress().getPhysicalAddress().getStreetPostalCode());
		
			
			
			// Stra�e und Hausnummer setzen

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
			// Nulls l�schen
			kontaktEintrag = entferneNulls(kontaktEintrag);
			//Kontakt hinzuf�gen
			Kontaktliste.add(kontaktEintrag);
		}

		return Kontaktliste;
	}

	public static Contact entferneNulls(Contact kontaktDaten) {
		
		//Die Methode entfernt alle Nullzeiger aus dem Contact Objekt und setzt daf�r leere Strings ein
		
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
	
	
}


