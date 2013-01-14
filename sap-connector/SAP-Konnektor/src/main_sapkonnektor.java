import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import com.sap.xi.appl.se.global.CustomerBasicDataByIDResponseMessageSync;
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
			CustomerBasicDataByIDResponseMessageSync cushilfsObjekt = new CustomerBasicDataByIDResponseMessageSync();
			cushilfsObjekt = getCustomerIDs(filter);
			return getCustomerData(cushilfsObjekt);

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
		EmployeeID empID = new EmployeeID ();

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

		// Aufbau

		// Schleife die für alle Einträge den Webservice mit der entsprechenden
		// ID losschickt und die empfangenen Daten
		// in die Kontaktliste schreibt
		for (int i = 0; i < anzahlEintraege; i++) {

			kontaktEintrag = new Contact();
			kontaktEintrag.setType(Contact.ContactType.EMPLOYEE);

			// Supplier ID in PartyID Objekt eintragen
			empID.setValue(empIDList.getEmployee().get(i).getID());
			empAnfrageID.setEmployeeID(empID);
			empAnfrage
					.setEmployeeAddressSelectionByEmployee(empAnfrageID);

			// Hochreichen
			try {
				result = bindungDaten
						.employeeAddressByEmployeeQueryResponseIn(empAnfrage);
			}  catch (SOAPFaultException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (com.sap.xi.ea_hr.se.global.StandardMessageFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// SAPID setzen
			kontaktEintrag.setSapId(empIDList.getEmployee().get(i).getID());
			//Vorname setzen
			if(result.getEmployee().getAddress().get(i).getAddress().getPersonName().getGivenName()!= null){
				kontaktEintrag.setFirstname(result.getEmployee().getAddress().get(i).getAddress().getPersonName().getGivenName());
			}
			//Nachname setzen
			if(result.getEmployee().getAddress().get(i).getAddress().getPersonName().getFamilyName()!= null){
				kontaktEintrag.setLastname(result.getEmployee().getAddress().get(i).getAddress().getPersonName().getFamilyName());
			}
			// Firma setzen
			if(result.getEmployee().getAddress().get(i).getAddress().getDepartmentName()!= null){
				kontaktEintrag.setCompany(result.getEmployee().getAddress().get(i).getAddress().getDepartmentName());
			}

			// Stadt und Postleitzahl setzen
			if(result.getEmployee().getAddress().get(i).getAddress().getPhysicalAddress().getCityName()!= null){
				kontaktEintrag.setCity(result.getEmployee().getAddress().get(i).getAddress().getPhysicalAddress().getCityName());
			}
			if(result.getEmployee().getAddress().get(i).getAddress().getPhysicalAddress().getRegionCode()!= null){
				kontaktEintrag.setZipcode(result.getEmployee().getAddress().get(i).getAddress().getPhysicalAddress().getRegionCode().getValue());
			}
			

			// Straße und Hausnummer setzen
			if(result.getEmployee().getAddress().get(i).getAddress().getPhysicalAddress().getStreetName()!= null){
				kontaktEintrag.setStreet(result.getEmployee().getAddress().get(i).getAddress().getPhysicalAddress().getStreetName());
			}

			// Email und Telefon
			if(result.getEmployee().getAddress().get(i).getAddress().getCommunication().getEmail().size()>0){
				kontaktEintrag.setEmail(result.getEmployee().getAddress().get(i).getAddress().getCommunication().getEmail().get(0).getURI().getValue());
			}
			if(result.getEmployee().getAddress().get(i).getAddress().getCommunication().getTelephone().size()>0){
				kontaktEintrag.setEmail(result.getEmployee().getAddress().get(i).getAddress().getCommunication().getTelephone().get(0).getNumber().getSubscriberID());
			}
			
			
			
			
			// Kontakt hinzufügen
			Kontaktliste.add(kontaktEintrag);
		}
		
		// TODO Auto-generated method stub
		return Kontaktliste;
	}

	private static List<Contact> getCustomerData(
			CustomerBasicDataByIDResponseMessageSync cushilfsObjekt) {
		// TODO Auto-generated method stub
		return null;
	}

	private static CustomerBasicDataByIDResponseMessageSync getCustomerIDs(
			Contact filter) {
		// TODO Auto-generated method stub
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

	private static EmplERPSimplElmntsRspMsgS getEmployeeIDs(Contact filter) {

		EmplERPSimplElmntsQryMsgS mitarbeiterAnfrage = new EmplERPSimplElmntsQryMsgS();
		EmplERPSimplElmntsQrySSel mitarbeiterFilter = new EmplERPSimplElmntsQrySSel();
		MEDIUMName filterWert = new MEDIUMName();
		
		//Ein paar EmployeeObjekte bauen:
		EmplERPSimplElmntsQrySSelByEmplGvnName empVorname = new EmplERPSimplElmntsQrySSelByEmplGvnName();
		EmplERPSimplElmntsQrySSelByEmplFamName empName = new EmplERPSimplElmntsQrySSelByEmplFamName();
		EmplERPSimplElmntsQrySSelByHomeAddrCityName empStadt = new EmplERPSimplElmntsQrySSelByHomeAddrCityName();
		EmplERPSimplElmntsQrySSelByHomeAddrPostlCode empPLZ	= new EmplERPSimplElmntsQrySSelByHomeAddrPostlCode();
		EmplERPSimplElmntsQrySSelByHomeAddrStName empStrasse = new EmplERPSimplElmntsQrySSelByHomeAddrStName();
		
		// Hier die Werte der inneren Klasse des zu sendenden Objekts mit den
		// Werten von Dominiks
		// Contacts befüllen
		// TODO Testfall herausnehmen
		
		
		if (filter.getFirstname() == "Test") {
			filterWert.setValue("a");
			empName.setLowerBoundaryEmployeeFamilyName(filterWert);
			mitarbeiterFilter.setSelectionByEmployeeFamilyName(empName);
		} else {
			filterWert.setValue(filter.getLastname());
			empName.setLowerBoundaryEmployeeFamilyName(filterWert);
			mitarbeiterFilter.setSelectionByEmployeeFamilyName(empName);
			filterWert.setValue(filter.getFirstname());
			empVorname.setLowerBoundaryEmployeeGivenName(filterWert);
			mitarbeiterFilter.setSelectionByEmployeeGivenName(empVorname);
			filterWert.setValue(filter.getCity());
			empStadt.setLowerBoundaryEmployeeHomeAddressCityName(filterWert);
			mitarbeiterFilter.setSelectionByEmployeeHomeAddressCityName(empStadt);
			
			empPLZ.setLowerBoundaryEmployeeHomeAddressPostalCode(filter.getZipcode());
			mitarbeiterFilter.setSelectionByEmployeeHomeAddressPostalCode(empPLZ);
			
			empStrasse.setLowerBoundaryEmployeeHomeAddressStreetName(filter.getStreet());
			mitarbeiterFilter.setSelectionByEmployeeHomeAddressStreetName(empStrasse);
		}
		// Alles dem sendenden Objekt hinzufügen
		mitarbeiterAnfrage.setEmployeeSimpleSelectionByElements(mitarbeiterFilter);

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
		} catch (com.sap.xi.ea_hr.se.global.StandardMessageFault e) { //catch hier mit anderem messagefault
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Ergebnis zurückgeben
		
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
			if(supplierIDList.getSupplier().get(i)
					.getBasicData().getCommon().getName().getFirstLineName()!= null){
			kontaktEintrag.setCompany(supplierIDList.getSupplier().get(i)
					.getBasicData().getCommon().getName().getFirstLineName());
			}
			// Stadt und Postleitzahl setzen
			if(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getCityName()!= null){
			kontaktEintrag.setCity(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getCityName());
			}
			if(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getCompanyPostalCode()!= null){
			kontaktEintrag.setZipcode(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getCompanyPostalCode());
			}
			
			// Straße und Hausnummer setzen
			if(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getStreetName()!= null){
			kontaktEintrag.setStreet(result.getSupplier().getBasicData()
					.getAddressInformation().getAddress().getPhysicalAddress()
					.getStreetName());
			}
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
