package dli_contacts.sibs.gui;

import dli_contacts.Contact;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class EditContactFrame extends JFrame implements ActionListener {
    
    private Contact contact = new Contact();
    
    public static enum ResultBranch {SELECT, CANCEL, UNKNOWN}
    private ResultBranch result = ResultBranch.UNKNOWN;
    
    private JLabel labelTitle = new JLabel("");
    
    private JLabel labelFirstname = new JLabel("Vorname: ");
    private JLabel labelLastname = new JLabel("Nachname: ");
    private JLabel labelStreet = new JLabel("Straße: ");
    private JLabel labelZipcode = new JLabel("PLZ: ");
    private JLabel labelCity = new JLabel("Stadt: ");
    private JLabel labelPhone = new JLabel("Tel: ");
    private JLabel labelEmail = new JLabel("Email: ");
    
    private JTextField fieldFirstname = new JTextField();
    private JTextField fieldLastname = new JTextField();
    private JTextField fieldStreet = new JTextField();
    private JTextField fieldZipcode = new JTextField();
    private JTextField fieldCity = new JTextField();
    private JTextField fieldPhone = new JTextField();
    private JTextField fieldEmail = new JTextField();
    
    private JButton buttonSelect = new JButton("Auswählen");
    private JButton buttonCancel = new JButton("Abbrechen");
    
    public ResultBranch getResult() {
        return result;
    }
    
    public Contact getContact() {
        return contact;
    }
    
    public void setContact(Contact c) {
        contact = c;
    }

    public EditContactFrame(Contact person) {
        contact = person;
        initializeWindow();
    }

    private void initializeWindow() {
        
        add(labelFirstname);
        fieldFirstname.setText(contact.getFirstname());
        add(fieldFirstname);
        
        add(labelLastname);
        fieldLastname.setText(contact.getLastname());
        add(fieldLastname);
        
        add(labelStreet);
        fieldStreet.setText(contact.getStreet());
        add(fieldStreet);
        
        add(labelZipcode);
        fieldZipcode.setText(contact.getZipcode());
        add(fieldZipcode);
        
        add(labelCity);
        fieldCity.setText(contact.getCity());
        add(fieldCity);
        
        add(labelPhone);
        fieldPhone.setText(contact.getPhone());
        add(fieldPhone);
        
        add(labelEmail);
        fieldEmail.setText(contact.getEmail());
        add(fieldEmail);
        
        buttonSelect.addActionListener(this);
        add(buttonSelect);
        
        buttonCancel.addActionListener(this);
        add(buttonCancel);
        
        this.setSize(500, 500);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}