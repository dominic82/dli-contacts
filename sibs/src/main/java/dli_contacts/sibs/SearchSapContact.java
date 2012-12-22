package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;


@SIBClass("searchSapContact")
public class SearchSapContact implements Executable {

	public final String[] BRANCHES = { "default", "error", "found", "not found" };
	
	public ContextKey contact = new ContextKey("contact");
	public ContextKey contactList = new ContextKey("contactList");
	
	public String trace(ExecutionEnvironment env) {

		try {
						
			return "default";

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "error";
		}

	}
}
