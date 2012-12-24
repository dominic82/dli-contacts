package dli_contacts.sibs.gui;

import dli_contacts.Contact;

import java.awt.BorderLayout;
import java.awt.Container;
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
    
    public EditContactFrame(String title, Contact person) {
        super(title);
        contact = person;
        initializeWindow();
    }

    private void initializeWindow() {
        
        setLayout(new BorderLayout());
        
        Container container = getContentPane();
        
        container.add(labelFirstname, BorderLayout.WEST);
        fieldFirstname.setText(contact.getFirstname());
        container.add(fieldFirstname, BorderLayout.CENTER);
        
        container.add(labelLastname, BorderLayout.WEST);
        fieldLastname.setText(contact.getLastname());
        container.add(fieldLastname, BorderLayout.CENTER);
        
        container.add(labelStreet, BorderLayout.WEST);
        fieldStreet.setText(contact.getStreet());
        container.add(fieldStreet, BorderLayout.CENTER);
        
        container.add(labelZipcode, BorderLayout.WEST);
        fieldZipcode.setText(contact.getZipcode());
        container.add(fieldZipcode, BorderLayout.CENTER);
        
        container.add(labelCity, BorderLayout.WEST);
        fieldCity.setText(contact.getCity());
        container.add(fieldCity, BorderLayout.CENTER);
        
        container.add(labelPhone, BorderLayout.WEST);
        fieldPhone.setText(contact.getPhone());
        container.add(fieldPhone, BorderLayout.CENTER);
        
        container.add(labelEmail, BorderLayout.WEST);
        fieldEmail.setText(contact.getEmail());
        container.add(fieldEmail, BorderLayout.CENTER);
        
        buttonSelect.addActionListener(this);
        container.add(buttonSelect, BorderLayout.SOUTH);
        
        buttonCancel.addActionListener(this);
        container.add(buttonCancel, BorderLayout.SOUTH);
        
        setSize(500, 500);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public ResultBranch getResult() {
        return result;
    }

}