package test.sibs;

import dli_contacts.Contact;
import dli_contacts.ContactsConnector;
import dli_contacts.DummyConnector;
import dli_contacts.sibs.gui.ChooseContactFrame;

import dli_contacts.sibs.gui.EditContactFrame;
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
        contact = list.get(1);
        
        EditContactFrame frame = new EditContactFrame("Test Kontakt bearbeiten", contact);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ChooseContactFrame frame2 = new ChooseContactFrame("Test Kontakt auswählen", list);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
