package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;


@SIBClass("putContact")
public class PutContact implements Executable {

	public final String[] BRANCHES = { "default", "error" };
	
	public ContextKey contact = new ContextKey("contact");
	
	public String trace(ExecutionEnvironment env) {

		try {
			
			
			
			return "default";

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "error";
		}

	}
}
