import java.util.Map;

import java.io.File;
import java.io.IOException;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceClient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceFeature;


import sap_not.myWebServiceFeature;

import com.sap.xi.appl.se.global.*;
import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryMessageSync.SupplierSimpleSelectionByNameAndAddress;

import dli_contacts.Contact;

import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryMessageSync;
import com.sap.xi.appl.se.global.ServiceECCSUPPLIERSNAQRDEFAULTPROFILE;




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
		
//		BindingProvider bs = (BindingProvider) ws;
		
//		Binding ha = bs.getBinding();
		
		
		//Testcode
		  SupplierSimpleByNameAndAddressQueryMessageSync suppquery = new SupplierSimpleByNameAndAddressQueryMessageSync();
		  SupplierSimpleSelectionByNameAndAddress supSelection = new SupplierSimpleSelectionByNameAndAddress();
		//  supSelection.setXXX();
		  suppquery.setSupplierSimpleSelectionByNameAndAddress(supSelection);
		  
		  SupplierSimpleByNameAndAddressResponseMessageSync result = null;
		  
		  ServiceECCSUPPLIERSNAQRDEFAULTPROFILE service = new ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();
		  
/*		  try { */
		   SupplierSimpleByNameAndAddressQueryResponseIn binding = service.getBindingTHTTPAHTTPECCSUPPLIERSNAQRDEFAULTPROFILE();
		   BindingProvider bp = (BindingProvider) binding;
//		   Map<String, Object> reqCont = bp.getRequestContext();
		   
		   bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "Webaddresse");
		   bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "S0008266219");
		   bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "Fleischgans85");
		   
/*		   result = binding.supplierSimpleByNameAndAddressQueryResponseIn(suppquery);
		   
		  }
		  
		  catch (StandardMessageFault e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  result.getSupplier().get(0).getBasicData().getCommon().getName();
*/		
		  
		  
		  
		  
		Contact xta = new Contact();
		}
}