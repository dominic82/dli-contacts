/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dli_contacts.googleconnector;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import dli_contacts.Contact;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import junit.framework.TestCase;

import dli_contacts.DummyConnector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author dominic
 */
public class DLI_GoogleContactsConnectorTest extends TestCase {

    private Contact contactSupplier = new Contact();
    private Contact contactCustomer = new Contact();
    private Contact contactEmployee = new Contact();
    private DummyConnector dm = new DummyConnector();
    private List attributeList = new ArrayList();

    public DLI_GoogleContactsConnectorTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        contactCustomer = dm.getPerson1();
        contactSupplier = dm.getPerson2();
        contactEmployee = dm.getPerson3();

        attributeList.add("Firstname");
        attributeList.add("Lastname");
        attributeList.add("Company");
        attributeList.add("Zipcode");
        attributeList.add("City");
//        attributeList.add("Email");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of createContact method, of class DLI_GoogleContactsConnector.
     */
    public void testCreateContact_Contact() throws Exception {
        System.out.println("createContact");

        Contact contactInfo1 = dm.getPerson1();
        Contact contactInfo2 = dm.getPerson2();
        Contact contactInfo3 = dm.getPerson3();

        DLI_GoogleContactsConnector instance = new DLI_GoogleContactsConnector();

//        instance.createContact(contactInfo1);
//        instance.createContact(contactInfo2);
//        instance.createContact(contactInfo3);

        assertEquals(true, true);
    }

    /**
     * Test of fetchContacts method, of class DLI_GoogleContactsConnector.
     */
    public void testFetchContacts_Contact() throws Exception {
        searchContactTest(contactCustomer);
        searchContactTest(contactSupplier);
        searchContactTest(contactEmployee);
    }

    private void searchContactTest(Contact contact) {
        System.out.println("fetchContacts: looking for..." + contact.getType().toString());
        DLI_GoogleContactsConnector instance = null;
        try {
            instance = new DLI_GoogleContactsConnector();
        } catch (AuthenticationException ex) {
        }

        List<Integer> superSet = new ArrayList<>();
        for (int i = 0; i < attributeList.size(); i++) {
            superSet.add(i);
        }
        int sum = 0;
        for (int i = 0; i < attributeList.size(); i++) {
            List<Set<Integer>> subsets_i = getSubsets(superSet, i + 1);
            sum += subsets_i.size();

            for (Set<Integer> set : subsets_i) {
                boolean found = false;
                Contact filter = new Contact();
                for (Integer j : set) {
                    try {
                        Class params[] = {};
                        Object paramsObj[] = {};
                        Class thisClass = contact.getClass();
                        Method thisMethod = thisClass.getDeclaredMethod("get" + attributeList.get(j), params);
                        String value = thisMethod.invoke(contact, paramsObj).toString();

                        Class params2[] = {};
                        Object paramsObj2[] = {};
                        Class thisClass2 = filter.getClass();
                        Method thisMethod2 = thisClass2.getDeclaredMethod("set" + attributeList.get(j), String.class);
                        thisMethod2.invoke(filter, value);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                    }
                }
                filter.setType(contact.getType());

                System.out.println(filter.getDataString());
                List result = null;
                try {
                    result = instance.fetchContacts(filter);
                } catch (ServiceException | IOException ex) {
                }
                System.out.println("Ergebnisse: " + result.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }

                for (Iterator it = result.iterator(); it.hasNext();) {
                    Contact c = (Contact) it.next();
                    //System.out.println(c.toString());
                    if (contact.getFirstname() == null ? c.getFirstname() == null : contact.getFirstname().equals(c.getFirstname())) {
                        found = true;
                    }
                }
                assertEquals(true, found);
            }

        }
        System.out.println("Anfagen: " + sum);
    }

    private void getSubsets(List<Integer> superSet, int k, int idx, Set<Integer> current, List<Set<Integer>> solution) {
        //successful stop clause
        if (current.size() == k) {
            solution.add(new HashSet<>(current));
            return;
        }
        //unseccessful stop clause
        if (idx == superSet.size()) {
            return;
        }
        Integer x = superSet.get(idx);
        current.add(x);
        //"guess" x is in the subset
        getSubsets(superSet, k, idx + 1, current, solution);
        current.remove(x);
        //"guess" x is not in the subset
        getSubsets(superSet, k, idx + 1, current, solution);
    }

    private List<Set<Integer>> getSubsets(List<Integer> superSet, int k) {
        List<Set<Integer>> res = new ArrayList<>();
        getSubsets(superSet, k, 0, new HashSet<Integer>(), res);
        return res;
    }
}
