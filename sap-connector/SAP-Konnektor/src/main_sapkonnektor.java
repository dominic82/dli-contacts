import java.util.Map;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceFeature;

import sap_not.myWebServiceFeature;

import com.sap.xi.appl.se.global.*;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryMessageSync.SupplierSimpleSelectionByNameAndAddress;

import dli_contacts.Contact;



public class main_sapkonnektor {

	/**
	 * @param args
	 */
		public static void main(String argv[]) {
		
		SupplierSimpleByNameAndAddressQueryMessageSync suppaquery = new SupplierSimpleByNameAndAddressQueryMessageSync();
		
		ServiceECCSUPPLIERSNAQRDEFAULTPROFILE servic = new ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();
		
		//Kommentar
		SupplierSimpleSelectionByNameAndAddress x = new SupplierSimpleSelectionByNameAndAddress();
		
		WebServiceFeature ws = new myWebServiceFeature();
		
		BindingProvider bs = (BindingProvider) ws;
		
		Binding ha = bs.getBinding();
		
		
		//Testcode
		  SupplierSimpleByNameAndAddressQueryMessageSync suppquery = new SupplierSimpleByNameAndAddressQueryMessageSync();
		  SupplierSimpleSelectionByNameAndAddress supSelection = new SupplierSimpleSelectionByNameAndAddress();
		//  supSelection.setXXX();
		  suppquery.setSupplierSimpleSelectionByNameAndAddress(supSelection);
		  
		  SupplierSimpleByNameAndAddressResponseMessageSync result = null;
		  
		  ServiceECCSUPPLIERSNAQRDEFAULTPROFILE service = new ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();
		  
		  try {
		   SupplierSimpleByNameAndAddressQueryResponseIn binding = service.getBindingTHTTPAHTTPECCSUPPLIERSNAQRDEFAULTPROFILE();
		   BindingProvider bp = (BindingProvider) binding;
		   Map<String, Object> reqCont = bp.getRequestContext();
		   result = binding.supplierSimpleByNameAndAddressQueryResponseIn(suppquery);
		  } catch (StandardMessageFault e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  result.getSupplier().get(0).getBasicData().getCommon().getName();
		
		
		
		
		
		
		
		
		
		
		
		Contact xta = new Contact();
		}
}
