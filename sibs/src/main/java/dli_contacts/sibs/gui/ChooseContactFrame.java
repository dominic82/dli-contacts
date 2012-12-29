package dli_contacts.sibs.gui;

import dli_contacts.Contact;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * @author dominic
 */
public class ChooseContactFrame extends JFrame implements ActionListener, ListSelectionListener {

    private List<Contact> contacts = new ArrayList<Contact>();
    private Contact contact = new Contact();

    /**
     * List of defined output-branches of the jabc-SIB
     */
    public static enum ResultBranch {

        OK,
        CANCEL,
        UNKNOWN
    }
    private ResultBranch result = ResultBranch.UNKNOWN;
    private JList listContacts = new JList();
    private JTextArea textContactDetails = new JTextArea();
    private JButton buttonSelect = new JButton("Ok");
    private JButton buttonCancel = new JButton("Abbrechen");

    /**
     * @param title of the Window
     * @param list of contact-objects to choose from
     */
    public ChooseContactFrame(String title, List<Contact> list) {
        super(title);
        contacts = list;
        initializeWindow();
    }

    private void initializeWindow() {

        /**
         *  Swing Components konfigurieren
         */
        // Liste der Kontakte initialisieren
        DefaultListModel model = new DefaultListModel();
        for (Contact person : contacts) {
            model.addElement(person);
        }
        listContacts = new JList(model);
        listContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel listSelectionModel = listContacts.getSelectionModel();
        listSelectionModel.addListSelectionListener(this);

        // Kontakt-Details initialisieren
        textContactDetails.setEditable(false);
        textContactDetails.setText("\n\n\n\n\n\n\n\n\n");

        // Buttons initialisieren
        buttonSelect.addActionListener(this);
        buttonCancel.addActionListener(this);

        /**
         *  GRIDBAG-LAYOUT konfigurieren
         */
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Constraints für Liste setzen
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 20, 0, 20); //Margin

        //Liste hinzufügen
        c.gridx = 0;
        c.gridy = 0;
        JScrollPane scrollPane = new JScrollPane(listContacts);
        scrollPane.setBorder(BorderFactory.createTitledBorder(loweredetched, " Einträge: " + contacts.size() + " "));
        pane.add(scrollPane, c);

        //Constraints resetten und für Details setzen
        c = new GridBagConstraints();
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 20, 0, 20); //Margin

        //Details hinzufügen
        c.gridx = 0;
        c.gridy = 1;
        JPanel tempPanel = new JPanel(new BorderLayout());
        tempPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, " Kontakt-Details: "));
        tempPanel.add(textContactDetails, BorderLayout.PAGE_START);
        pane.add(tempPanel, c);

        //Constraints resetten und für Buttons setzen
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(10, 20, 20, 20); //Margin

        //Buttons hinzufügen
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

    /**
     * This Method is called when a button is clicked
     * @param ae Swing-ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == buttonCancel) {
            result = ResultBranch.CANCEL;
        }

        if (ae.getSource() == buttonSelect) {
            Contact person = (Contact) listContacts.getSelectedValue();

            contact.setFirstname(person.getFirstname());
            contact.setLastname(person.getLastname());
            contact.setCompany(person.getCompany());
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

    /**
     * THis method is called when the selection of the list has changed
     * @param lse Swing ListSelectionEvent
     */
    @Override
    public void valueChanged(ListSelectionEvent lse) {

        if (!lse.getValueIsAdjusting()) {
            Contact person = (Contact) listContacts.getSelectedValue();
            textContactDetails.setText(generateDetailsText(person));
        }
    }

    /**
     * Gets the chosen contact-object
     * @return contact-object
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Gets the chosen ouput-branch of the jabc-SIB
     * @return value of enum ResultBranch
     */
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