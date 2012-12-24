package test.sibs;

import dli_contacts.Contact;
import dli_contacts.ContactsConnector;
import dli_contacts.DummyConnector;
import dli_contacts.sibs.gui.ChooseContactFrame;

import java.util.List;
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
        ContactsConnector con = new DummyConnector();
        List<Contact> list = con.getGoogleContacts(contact);
        
        ChooseContactFrame frame = new ChooseContactFrame("Test Kontakt auswählen", list);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
