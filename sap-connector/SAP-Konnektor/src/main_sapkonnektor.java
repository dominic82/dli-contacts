import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import com.sap.xi.appl.se.global.EmailURI;
import com.sap.xi.appl.se.global.ServiceECCSUPPLIERSNAQRDEFAULTPROFILE;
import com.sap.xi.appl.se.global.StandardMessageFault;
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

	}

	
	
	
	
	
	
	
	
	
	
	
	public static List<Contact> fetchContact(Contact filter) {

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

		// TODO

		// result.getSupplier().get(0).getBasicData().getCommon().getName();

		result.getSupplier().get(8).getBasicData().getCommon().getName()
				.getFirstLineName();


		System.out.println(result.getSupplier().get(8).getBasicData()
				.getCommon().getName().getFirstLineName());


        
		Contact xta = new Contact();

		xta.getType();

		List<Contact> Kontaktliste = null;

		return Kontaktliste;
	}



public static SupplierSimpleByNameAndAddressResponseMessageSync getSupplierIDs(Contact filter){
	
	SupplierSimpleByNameAndAddressQueryMessageSync lieferantAnfrage = new SupplierSimpleByNameAndAddressQueryMessageSync();
	SupplierSimpleSelectionByNameAndAddress lieferantFilter = new SupplierSimpleSelectionByNameAndAddress();


	// Hier die Werte der inneren Klasse des zu sendenden Objekts mit den Werten von Dominiks
	// Contacts befüllen

	lieferantFilter.setSupplierName1(filter.getFirstname());
	lieferantFilter.setSupplierAddressCityName(filter.getCity());
	lieferantFilter.setSupplierAddressStreetName(filter.getStreet());
	lieferantFilter.setSupplierAddressStreetPostalCode(filter.getZipcode());
	
	EmailURI dummerSAPEmailTyp = new EmailURI();
	dummerSAPEmailTyp.setValue(filter.getEmail());
	lieferantFilter.setSupplierAddressEMailAddress(dummerSAPEmailTyp);
	
	lieferantFilter.setSupplierAddressPhoneNumber(filter.getPhone());

	//Alles dem sendenden Objekt hinzufügen
	lieferantAnfrage.setSupplierSimpleSelectionByNameAndAddress(lieferantFilter);
    
	
	//Verbindungs und Antwortobjekte bauen
	SupplierSimpleByNameAndAddressResponseMessageSync result = null;

	ServiceECCSUPPLIERSNAQRDEFAULTPROFILE verbindungsObjekt = new ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();

	SupplierSimpleByNameAndAddressQueryResponseIn bindungDaten = verbindungsObjekt
			.getBindingTHTTPAHTTPECCSUPPLIERSNAQRDEFAULTPROFILE();
	BindingProvider bindungDatenCast = (BindingProvider) bindungDaten;

	
	// Username und Passwort setzen (Webaddresse schon im Objekt enthalten)
	bindungDatenCast.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
			"S0008266219");
	bindungDatenCast.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
			"Fleischgans85");
    
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

    //Ergebnis zurückgeben
	return result;
}

public static SupplierSimpleByNameAndAddressResponseMessageSync getEmployeeIDs(Contact filter){
	
	SupplierSimpleByNameAndAddressQueryMessageSync lieferantAnfrage = new SupplierSimpleByNameAndAddressQueryMessageSync();
	SupplierSimpleSelectionByNameAndAddress lieferantFilter = new SupplierSimpleSelectionByNameAndAddress();


	// Hier die Werte der inneren Klasse des zu sendenden Objekts mit den Werten von Dominiks
	// Contacts befüllen

	lieferantFilter.setSupplierName1(filter.getFirstname());
	lieferantFilter.setSupplierAddressCityName(filter.getCity());
	lieferantFilter.setSupplierAddressStreetName(filter.getStreet());
	lieferantFilter.setSupplierAddressStreetPostalCode(filter.getZipcode());
	
	EmailURI dummerSAPEmailTyp = new EmailURI();
	dummerSAPEmailTyp.setValue(filter.getEmail());
	lieferantFilter.setSupplierAddressEMailAddress(dummerSAPEmailTyp);
	
	lieferantFilter.setSupplierAddressPhoneNumber(filter.getPhone());

	//Alles dem sendenden Objekt hinzufügen
	lieferantAnfrage.setSupplierSimpleSelectionByNameAndAddress(lieferantFilter);
    
	
	//Verbindungs und Antwortobjekte bauen
	SupplierSimpleByNameAndAddressResponseMessageSync result = null;

	ServiceECCSUPPLIERSNAQRDEFAULTPROFILE verbindungsObjekt = new ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();

	SupplierSimpleByNameAndAddressQueryResponseIn bindungDaten = verbindungsObjekt
			.getBindingTHTTPAHTTPECCSUPPLIERSNAQRDEFAULTPROFILE();
	BindingProvider bindungDatenCast = (BindingProvider) bindungDaten;

	
	// Username und Passwort setzen (Webaddresse schon im Objekt enthalten)
	bindungDatenCast.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
			"S0008266219");
	bindungDatenCast.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
			"Fleischgans85");
    
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

    //Ergebnis zurückgeben
	return result;
}




public static List<Contact> getSupplierData(SupplierSimpleByNameAndAddressResponseMessageSync supplierIDList){
	
	
	List<Contact> Kontaktliste = null;

	return Kontaktliste;
}


}

