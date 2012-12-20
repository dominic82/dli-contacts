package dli_contacts.sibs;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

@SIBClass("TestSIB")
public class TestSIB implements Executable {

	public final String[] BRANCHES = { "default", "error" };

	public String StringParameter = "default value";
	public Integer IntegerParameter = new Integer(1);
	public ContextKey someContextKey = new ContextKey("someKey");

	public String trace(ExecutionEnvironment env) {
		System.out.println("Hallo Welt");
		boolean success = true;
		return success ? "default" : "error";
	}
}