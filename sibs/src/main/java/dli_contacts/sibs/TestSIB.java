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

	public ContextKey contact = new ContextKey("contact");

	public String trace(ExecutionEnvironment env) {

		try {
			Contact person = (Contact) env.get(contact);
			System.out.println("Test-SIB: Name = " + person.getFirstname());
                        
                        helloSwing();
                        
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

	    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
}