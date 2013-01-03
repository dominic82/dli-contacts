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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import com.sap.xi.appl.se.global.SupplierSimpleByNameAndAddressQueryMessageSync;
import com.sap.xi.appl.se.global.ServiceECCSUPPLIERSNAQRDEFAULTPROFILE;


@WebService(wsdlLocation="supplier.xml")
public class main_sapkonnektor {

	/**
	 * @param args
	 */
		public static void main(String argv[]) {
		
		SupplierSimpleByNameAndAddressQueryMessageSync suppquery = new SupplierSimpleByNameAndAddressQueryMessageSync();
		
		ServiceECCSUPPLIERSNAQRDEFAULTPROFILE service = new ServiceECCSUPPLIERSNAQRDEFAULTPROFILE();
		
		
		
		}
}
