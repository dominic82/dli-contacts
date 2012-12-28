package dli_contacts.sibs.gui;

import dli_contacts.Contact;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChooseContactFrame extends JFrame implements ActionListener, ListSelectionListener {

    private List<Contact> contacts = new ArrayList<Contact>();
    private Contact contact = new Contact();

    public static enum ResultBranch {

        OK, CANCEL, UNKNOWN
    }
    private ResultBranch result = ResultBranch.UNKNOWN;
    private JList listContacts = new JList();
    private JTextArea textContactDetails = new JTextArea();
    private JButton buttonSelect = new JButton("Ok");
    private JButton buttonCancel = new JButton("Abbrechen");

    public ChooseContactFrame(String title, List<Contact> list) {
        super(title);
        contacts = list;
        initializeWindow();
    }

    private void initializeWindow() {

        // Inhalte initialisieren
        DefaultListModel model = new DefaultListModel();
        for (Contact person : contacts) {
            model.addElement(person);
        }
        listContacts = new JList(model);
        listContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel listSelectionModel = listContacts.getSelectionModel();
        listSelectionModel.addListSelectionListener(this);

        textContactDetails.setEditable(false);
        textContactDetails.setText("\n\n\n\n\n\n\n\n\n");

        //ActionListener initialisieren
        buttonSelect.addActionListener(this);
        buttonCancel.addActionListener(this);

        //Layout konfigurieren
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 20, 0, 20); //Margin

        c.gridx = 0;
        c.gridy = 0;
        pane.add(listContacts, c);

        c = new GridBagConstraints();
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 20, 0, 20); //Margin

        c.gridx = 0;
        c.gridy = 1;
        pane.add(textContactDetails, c);


        //Buttons setzen
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(10, 20, 20, 20); //Margin

        c.gridx = 0;
        c.gridy = 2;
        pane.add(buttonCancel, c);

        c.gridx = 1;
        c.gridy = 2;
        pane.add(buttonSelect, c);

        //Fenster konfigurieren
        setResizable(true);
        setSize(500, 400);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == buttonCancel) {
            result = ResultBranch.CANCEL;
        }

        if (ae.getSource() == buttonSelect) {
            Contact person = (Contact) listContacts.getSelectedValue();

            contact.setFirstname(person.getFirstname());
            contact.setLastname(person.getLastname());
            contact.setStreet(person.getStreet());
            contact.setZipcode(person.getZipcode());
            contact.setCity(person.getCity());
            contact.setPhone(person.getPhone());
            contact.setEmail(person.getEmail());
            contact.setGoogleId(person.getGoogleId());
            contact.setSapId(person.getSapId());

            result = ResultBranch.OK;
        }

        synchronized (this) {
            this.notify();
        }

        dispose();
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {

        if (!lse.getValueIsAdjusting()) {
            Contact person = (Contact) listContacts.getSelectedValue();
            textContactDetails.setText(generateDetailsText(person));
        }
    }

    public Contact getContact() {
        return contact;
    }

    public ResultBranch getResult() {
        return result;
    }

    private String generateDetailsText(Contact person) {
        String text = "";
        if (!person.getCompany().isEmpty()) {
            text += person.getCompany() + "\n";
        }
        text += person.getFirstname() + " " + person.getLastname() + "\n";
        text += person.getStreet() + "\n";
        text += person.getZipcode() + " " + person.getCity() + "\n";
        text += "\n" + person.getPhone() + "\n" + person.getEmail() + "\n";

        text += "\n" + "Sap-ID: " + person.getSapId();
        text += "\n" + "Google-ID: " + person.getGoogleId();

        return text;
    }
}