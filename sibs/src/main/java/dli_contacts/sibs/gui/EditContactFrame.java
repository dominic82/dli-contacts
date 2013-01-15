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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * 
 * @author dominic
 */
public class EditContactFrame extends JFrame implements ActionListener {

    private Contact contact = new Contact();
    private boolean doValidation = true;

    /**
     * List of defined output-branches of the jabc-SIB
     */
    public static enum ResultBranch {

        OK,
        CANCEL,
        UNKNOWN
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
    private JLabel labelType = new JLabel("Gruppe: ");
    private JTextField fieldFirstname = new JTextField();
    private JTextField fieldLastname = new JTextField();
    private JTextField fieldCompany = new JTextField();
    private JTextField fieldStreet = new JTextField();
    private JTextField fieldZipcode = new JTextField();
    private JTextField fieldCity = new JTextField();
    private JTextField fieldPhone = new JTextField();
    private JTextField fieldEmail = new JTextField();
    private JComboBox cboxType = new JComboBox(Contact.ContactType.values());
    private JButton buttonSelect = new JButton("Ok");
    private JButton buttonCancel = new JButton("Abbrechen");

    /**
     * 
     * @param title of the Window
     * @param person contact-object for pre-filled formfields
     */
    public EditContactFrame(String title, Contact person) {
        super(title);
        contact = person;
    }

    /**
     * initialize Windows an show it
     */
    public void initializeWindow() {

        // Inhalte initialisieren
        fieldFirstname.setText(contact.getFirstname());
        fieldLastname.setText(contact.getLastname());
        fieldCompany.setText(contact.getCompany());
        fieldStreet.setText(contact.getStreet());
        fieldZipcode.setText(contact.getZipcode());
        fieldCity.setText(contact.getCity());
        fieldPhone.setText(contact.getPhone());
        fieldEmail.setText(contact.getEmail());
        cboxType.setSelectedItem(contact.getType());

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

        c.gridx = 0;
        c.gridy = 8;
        pane.add(labelType, c);

        //TextFields setzen
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 5, 20); //Margin
        c.gridwidth = 2;
        c.weightx = 1.0;

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

        c.gridx = 1;
        c.gridy = 8;
        pane.add(cboxType, c);

        //Buttons setzen
        c.insets = new Insets(10, 0, 10, 20); //Margin
        c.gridwidth = 1;
        c.weightx = 0.5;

        c.gridx = 1;
        c.gridy = 9;
        pane.add(buttonCancel, c);

        c.gridx = 2;
        c.gridy = 9;
        pane.add(buttonSelect, c);

        //Fenster konfigurieren
        setResizable(true);
        setSize(350, 370);
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
            contact.setType((Contact.ContactType) cboxType.getSelectedItem());

            List<Contact.ValidationErrors> errors = contact.validate();
            if (errors.isEmpty() || !doValidation) {
                result = ResultBranch.OK;
                
                System.out.println(contact.getDataString());
                
                synchronized (this) {
                    this.notify();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        errors.get(0),
                        "Eingabe nicht korrekt",
                        JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    /**
     * Gets the edited contact-object
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
    
    /**
     * Validation activated?
     * @return boolean
     */
    public boolean isDoValidation() {
        return doValidation;
    }

    /**
     * Set Validation on/off
     * @param doValidation boolean
     */
    public void setDoValidation(boolean doValidation) {
        this.doValidation = doValidation;
    }
}