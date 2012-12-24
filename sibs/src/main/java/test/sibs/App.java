package test.sibs;

import dli_contacts.Contact;
import dli_contacts.sibs.gui.EditContactFrame;
import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        Contact contact = new Contact();
        
        EditContactFrame frame = new EditContactFrame("Test Edit Contact", contact);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
