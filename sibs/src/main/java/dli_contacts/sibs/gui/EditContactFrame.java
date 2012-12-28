package dli_contacts.sibs.gui;

import dli_contacts.Contact;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class EditContactFrame extends JFrame implements ActionListener {

    private Contact contact = new Contact();

    public static enum ResultBranch {

        OK, CANCEL, UNKNOWN
    }
    private ResultBranch result = ResultBranch.UNKNOWN;
    private JLabel labelFirstname = new JLabel("Vorname: ");
    private JLabel labelLastname = new JLabel("Nachname: ");
    private JLabel labelCompany = new JLabel("Firma: ");
    private JLabel labelStreet = new JLabel("Straße: ");
    private JLabel labelZipcode = new JLabel("PLZ: ");
    private JLabel labelCity = new JLabel("Stadt: ");
    private JLabel labelPhone = new JLabel("Tel: ");
    private JLabel labelEmail = new JLabel("Email: ");
    private JTextField fieldFirstname = new JTextField();
    private JTextField fieldLastname = new JTextField();
    private JTextField fieldCompany = new JTextField();
    private JTextField fieldStreet = new JTextField();
    private JTextField fieldZipcode = new JTextField();
    private JTextField fieldCity = new JTextField();
    private JTextField fieldPhone = new JTextField();
    private JTextField fieldEmail = new JTextField();
    private JButton buttonSelect = new JButton("Ok");
    private JButton buttonCancel = new JButton("Abbrechen");

    public EditContactFrame(String title, Contact person) {
        super(title);
        contact = person;
        initializeWindow();
    }

    private void initializeWindow() {

        // Inhalte initialisieren
        fieldFirstname.setText(contact.getFirstname());
        fieldLastname.setText(contact.getLastname());
        fieldCompany.setText(contact.getCompany());
        fieldStreet.setText(contact.getStreet());
        fieldZipcode.setText(contact.getZipcode());
        fieldCity.setText(contact.getCity());
        fieldPhone.setText(contact.getPhone());
        fieldEmail.setText(contact.getEmail());

        //ActionListener initialisieren
        buttonSelect.addActionListener(this);
        buttonCancel.addActionListener(this);

        //Layout konfigurieren
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        //Labels setzen
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 20, 5, 0); //Margin

        c.gridx = 0;
        c.gridy = 0;
        pane.add(labelFirstname, c);

        c.gridx = 0;
        c.gridy = 1;
        pane.add(labelLastname, c);

        c.gridx = 0;
        c.gridy = 2;
        pane.add(labelCompany, c);

        c.gridx = 0;
        c.gridy = 3;
        pane.add(labelStreet, c);

        c.gridx = 0;
        c.gridy = 4;
        pane.add(labelZipcode, c);

        c.gridx = 0;
        c.gridy = 5;
        pane.add(labelCity, c);

        c.gridx = 0;
        c.gridy = 6;
        pane.add(labelPhone, c);

        c.gridx = 0;
        c.gridy = 7;
        pane.add(labelEmail, c);

        //TextFields setzen
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 5, 20); //Margin

        c.gridx = 1;
        c.gridy = 0;
        pane.add(fieldFirstname, c);

        c.gridx = 1;
        c.gridy = 1;
        pane.add(fieldLastname, c);

        c.gridx = 1;
        c.gridy = 2;
        pane.add(fieldCompany, c);

        c.gridx = 1;
        c.gridy = 3;
        pane.add(fieldStreet, c);

        c.gridx = 1;
        c.gridy = 4;
        pane.add(fieldZipcode, c);

        c.gridx = 1;
        c.gridy = 5;
        pane.add(fieldCity, c);

        c.gridx = 1;
        c.gridy = 6;
        pane.add(fieldPhone, c);

        c.gridx = 1;
        c.gridy = 7;
        pane.add(fieldEmail, c);

        //Buttons setzen
        c.insets = new Insets(10, 0, 10, 20); //Margin
        c.gridwidth = 1;

        c.gridx = 1;
        c.gridy = 8;
        pane.add(buttonCancel, c);

        c.gridx = 2;
        c.gridy = 8;
        pane.add(buttonSelect, c);

        //Fenster konfigurieren
        setResizable(false);
        setSize(350, 350);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == buttonCancel) {
            result = ResultBranch.CANCEL;
            synchronized (this) {
                this.notify();
            }
            dispose();
        }

        if (ae.getSource() == buttonSelect) {

            contact.setFirstname(fieldFirstname.getText());
            contact.setLastname(fieldLastname.getText());
            contact.setCompany(fieldCompany.getText());
            contact.setStreet(fieldStreet.getText());
            contact.setZipcode(fieldZipcode.getText());
            contact.setCity(fieldCity.getText());
            contact.setPhone(fieldPhone.getText());
            contact.setEmail(fieldEmail.getText());

            List<Contact.ValidationErrors> errors = contact.validate();
            if (errors.isEmpty()) {
                result = ResultBranch.OK;
                synchronized (this) {
                    this.notify();
                }
                dispose();
            } else {
                //TODO Fehlerbehandlung
                System.out.println("Contact Validation Error!");
            }
        }


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