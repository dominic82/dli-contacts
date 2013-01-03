package sap_not;

import javax.xml.ws.WebServiceFeature;

public class myWebServiceFeature extends WebServiceFeature {

	final static String ID = "SAP Web Service Feature";
	@Override
	public String getID() {
		
		return ID;
	}

}
