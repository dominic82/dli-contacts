
package dli_contacts.sibs.gui;

import dli_contacts.Contact;

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

public class ChooseContactFrame extends JFrame implements ActionListener {

    private List<Contact> contacts = new ArrayList<Contact>();
    private Object lock = new Object();

    public static enum ResultBranch {OK, CANCEL, UNKNOWN}
    private ResultBranch result = ResultBranch.UNKNOWN;
    
    private JList listContacts = new JList();
    
    private JButton buttonSelect = new JButton("Ok");
    private JButton buttonCancel = new JButton("Abbrechen");

    public ChooseContactFrame(String title, List<Contact> list) {
        super(title);
        contacts = list;
        initializeWindow();
    }

    public ChooseContactFrame(String title, List<Contact> list, Object lock) {
        this(title, list);
        this.lock = lock;
    }

    private void initializeWindow() {
        
        // Inhalte initialisieren
        DefaultListModel model = new DefaultListModel();
        int i = 0;
        for(Contact contact: contacts) {
            String entry = contact.getFirstname() + " " + contact.getLastname();
            i++;
            model.addElement(entry);
        }
        listContacts = new JList(model);

        //ActionListener initialisieren
        buttonSelect.addActionListener(this);
        buttonCancel.addActionListener(this);

        //Layout konfigurieren
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 3;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 20, 5, 20); //Margin
        
        c.gridx = 0;
        c.gridy = 0;
        pane.add(listContacts, c);
        
        //Buttons setzen
        c.insets = new Insets(10, 0, 10, 20); //Margin
        c.gridwidth = 1;
        c.weightx = 0.2;

        c.gridx = 1;
        c.gridy = 1;
        pane.add(buttonCancel, c);

        c.gridx = 2;
        c.gridy = 1;
        pane.add(buttonSelect, c);

        //Fenster konfigurieren
        setResizable(false);
        setSize(350, 300);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        System.out.println("ChooseContactFrame: List size() = " + contacts.size());

        if (ae.getSource() == buttonCancel) {
            result = ResultBranch.CANCEL;
        }

        if (ae.getSource() == buttonSelect) {

            result = ResultBranch.OK;
        }

        synchronized (lock) {
            lock.notify();
        }

        dispose();
    }

    public ResultBranch getResult() {
        return result;
    }
}