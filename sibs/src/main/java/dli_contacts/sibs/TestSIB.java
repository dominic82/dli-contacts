package dli_contacts.sibs;

import javax.swing.JFrame;
import javax.swing.JLabel;

import de.metaframe.jabc.framework.execution.ExecutionEnvironment;
import de.metaframe.jabc.framework.sib.annotation.SIBClass;
import de.metaframe.jabc.framework.sib.parameter.ContextKey;
import de.metaframe.jabc.sib.Executable;

import dli_contacts.Contact;

@SIBClass("TestSIB")
public class TestSIB implements Executable {

	public final String[] BRANCHES = { "default", "error" };

	public String StringParameter = "test";
	public ContextKey someContextKey = new ContextKey("someKey");

	public String trace(ExecutionEnvironment env) {

		try {
			
			Contact person = new Contact();
			person.setFirstname("Dominic");
			System.out.println("Mein Name ist " + person.getFirstname());
			
			//helloSwing();
			env.putGlobally(StringParameter, person);
			
			return "default";

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "error";
		}

	}
	
	private void helloSwing() {
		JFrame frame = new JFrame("HelloWorldSwing");
	    final JLabel label = new JLabel("Hello World");
	    frame.getContentPane().add(label);

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
}