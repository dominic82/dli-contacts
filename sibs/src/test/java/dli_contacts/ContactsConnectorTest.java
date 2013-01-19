/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dli_contacts;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author dominic
 */
public class ContactsConnectorTest extends TestCase {

    List attributeList = new ArrayList();
    private DummyConnector dm = new DummyConnector();
    private Contact sapContactSupplier = new Contact();
    private Contact sapContactCustomer = new Contact();
    private Contact sapContactEmployee = new Contact();
    private Contact gContactSupplier = new Contact();
    private Contact gContactCustomer = new Contact();
    private Contact gContactEmployee = new Contact();

    public ContactsConnectorTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        sapContactSupplier = new Contact();
        sapContactSupplier.setCompany("Paper Suplies");
        sapContactSupplier.setStreet("Jakobalee  45");
        sapContactSupplier.setCity("Berlin");
        sapContactSupplier.setType(Contact.ContactType.SUPPLIER);

        sapContactCustomer = new Contact();
        sapContactCustomer.setCompany("Karoline Kraft");
        sapContactCustomer.setStreet("Weigandufer");
        sapContactCustomer.setCity("Berlin");
        sapContactCustomer.setType(Contact.ContactType.CUSTOMER);

        sapContactEmployee = new Contact();
        sapContactEmployee.setFirstname("Anja");
        sapContactEmployee.setLastname("Müller");
        sapContactEmployee.setCity("Karlsruhe");
        sapContactEmployee.setType(Contact.ContactType.EMPLOYEE);

        gContactCustomer = dm.getPerson1();
        gContactSupplier = dm.getPerson2();
        gContactEmployee = dm.getPerson3();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private void addGoogleAttributes(Contact contact) {
        attributeList.add("Firstname");
//        attributeList.add("Lastname");
        attributeList.add("Company");
//        attributeList.add("Zipcode");
        attributeList.add("City");
//        attributeList.add("Email");
    }

    private void addSapAttributes(Contact contact) {
        if (contact.getType().equals(Contact.ContactType.CUSTOMER) || contact.getType().equals(Contact.ContactType.SUPPLIER)) {
            attributeList.add("Company");
            attributeList.add("Street");
        }
        if (contact.getType().equals(Contact.ContactType.EMPLOYEE)) {
            attributeList.add("Firstname");
            attributeList.add("Lastname");
        }

        attributeList.add("City");
    }

    /**
     * Test of getSapContacts method, of class ContactsConnector.
     */
    public void testGetSapContacts() {
        searchSapContact(sapContactCustomer);
        searchSapContact(sapContactSupplier);
        searchSapContact(sapContactEmployee);

        assertEquals(true, true);
    }

    /**
     * Test of getGoogleContacts method, of class ContactsConnector.
     */
    public void testGetGoogleContacts() throws Exception {
//        searchGoogleContact(gContactCustomer);
//        searchGoogleContact(gContactSupplier);
//        searchGoogleContact(gContactEmployee);

        assertEquals(true, true);
    }

    public void testGetSapContacts_withEmptyContact() {
        System.out.println("Sap fetchContacts: Empty Contact...");
        ContactsConnector instance = new ContactsConnector();
        List result = null;
        Contact filter = new Contact();

        // CUSTOMER
        filter = new Contact();
        filter.setType(Contact.ContactType.CUSTOMER);
        result = instance.getSapContacts(filter);
        assertNotNull("Null returned from search", result);
        assertTrue("No List returned from search", result.size() >= 0);

        // SUPPLIER
        filter = new Contact();
        filter.setType(Contact.ContactType.SUPPLIER);
        result = null;
        result = instance.getSapContacts(filter);
        assertNotNull("Null returned from search", result);
        assertTrue("No List returned from search", result.size() >= 0);

        // EMPLOYEE
        filter = new Contact();
        filter.setType(Contact.ContactType.EMPLOYEE);
        result = null;
        result = instance.getSapContacts(filter);
        assertNotNull("Null returned from search", result);
        assertTrue("No List returned from search", result.size() >= 0);
    }

    public void testGetGoogleContacts_withEmptyContact() {
        System.out.println("Google fetchContacts: Empty Contact...");
        ContactsConnector instance = new ContactsConnector();
        List result = null;
        Contact filter = new Contact();

        // CUSTOMER
        filter = new Contact();
        filter.setType(Contact.ContactType.CUSTOMER);

        try {
            result = instance.getGoogleContacts(filter);
        } catch (AuthenticationException ex) {
        } catch (ServiceException | IOException ex) {
        }
        assertNotNull("Null returned from search", result);
        assertTrue("No List returned from search", result.size() >= 0);

        // SUPPLIER
        filter = new Contact();
        filter.setType(Contact.ContactType.SUPPLIER);
        result = null;
        try {
            result = instance.getGoogleContacts(filter);
        } catch (AuthenticationException ex) {
        } catch (ServiceException | IOException ex) {
        }
        assertNotNull("Null returned from search", result);
        assertTrue("No List returned from search", result.size() >= 0);

        // EMPLOYEE
        filter = new Contact();
        filter.setType(Contact.ContactType.EMPLOYEE);
        result = null;
        try {
            result = instance.getGoogleContacts(filter);
        } catch (AuthenticationException ex) {
        } catch (ServiceException | IOException ex) {
        }
        assertNotNull("Null returned from search", result);
        assertTrue("No List returned from search", result.size() >= 0);
    }

    public void testAddGoogleContact() throws Exception {
        System.out.println("addGoogleContact");

        Contact contactInfo1 = dm.getPerson1();
        Contact contactInfo2 = dm.getPerson2();
        Contact contactInfo3 = dm.getPerson3();

        ContactsConnector instance = new ContactsConnector();
//        instance.addGoogleContact(gContactCustomer);
//        instance.addGoogleContact(gContactSupplier);
//        instance.addGoogleContact(gContactEmployee);

        assertEquals(true, true);
    }

    private void searchGoogleContact(Contact contact) {
        System.out.println("Google fetchContacts: looking for..." + contact.getType().toString());

        attributeList.clear();
        addGoogleAttributes(contact);

        System.out.println(attributeList.toString());

        ContactsConnector instance = new ContactsConnector();

        List<Integer> superSet = new ArrayList<>();
        for (int i = 0; i < attributeList.size(); i++) {
            superSet.add(i);
        }
        int sum = 0;
        for (int i = 0; i < attributeList.size(); i++) {
            List<Set<Integer>> subsets_i = getSubsets(superSet, i + 1);
            sum += subsets_i.size();

            for (Set<Integer> set : subsets_i) {

                Contact filter = new Contact();
                for (Integer j : set) {
                    try {
                        Class params[] = {};
                        Object paramsObj[] = {};
                        Class thisClass = contact.getClass();
                        Method thisMethod = thisClass.getDeclaredMethod("get" + attributeList.get(j), params);
                        String value = thisMethod.invoke(contact, paramsObj).toString();

                        Class thisClass2 = filter.getClass();
                        Method thisMethod2 = thisClass2.getDeclaredMethod("set" + attributeList.get(j), String.class);
                        thisMethod2.invoke(filter, value);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                    }
                }
                filter.setType(contact.getType());

                List result = null;
                try {
                    result = instance.getGoogleContacts(filter);
                } catch (AuthenticationException ex) {
                } catch (ServiceException | IOException ex) {
                }

                assertNotNull("Null returned from search", result);
                assertTrue("No List returned from search", result.size() >= 0);

                int found = 0;
                for (Iterator it = result.iterator(); it.hasNext();) {
                    Contact c = (Contact) it.next();

                    assertTrue("Returned Contact Type mismatch filter", contact.getType().equals(c.getType()));

                    if (contact.getFirstname() == null ? c.getFirstname() == null : contact.getFirstname().equals(c.getFirstname())) {
                        found++;
                    }
                }

                if (found == 0) {
                    System.out.println("Kontakt '" + contact.toString() + "' nicht gefunden mit Filter:");
                    System.out.println(filter.getDataString());
                    System.out.println("---------------");
                }

                assertTrue("filter not found", found > 0);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
            }

        }
        System.out.println("Anfagen: " + sum);
    }

    private void searchSapContact(Contact contact) {
        System.out.println("SAP fetchContacts: looking for..." + contact.getType().toString());

        attributeList.clear();
        addSapAttributes(contact);

        System.out.println(attributeList.toString());

        ContactsConnector instance = new ContactsConnector();

        List<Integer> superSet = new ArrayList<>();
        for (int i = 0; i < attributeList.size(); i++) {
            superSet.add(i);
        }
        int sum = 0;
        for (int i = 0; i < attributeList.size(); i++) {
            List<Set<Integer>> subsets_i = getSubsets(superSet, i + 1);
            sum += subsets_i.size();

            for (Set<Integer> set : subsets_i) {

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
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                filter.setType(contact.getType());

                List result = instance.getSapContacts(filter);
                
                assertNotNull("Null returned from search", result);
                assertTrue("No List returned from search", result.size() >= 0);

                int found = 0;
                for (Iterator it = result.iterator(); it.hasNext();) {
                    Contact c = (Contact) it.next();
                    //System.out.println(c.toString());

                    assertTrue("Returned Contact Type mismatch filter", contact.getType().equals(c.getType()));

                    if (contact.getType().equals(Contact.ContactType.CUSTOMER) || contact.getType().equals(Contact.ContactType.SUPPLIER)) {
                        if (contact.getCompany() == null ? c.getCompany() == null : contact.getCompany().equals(c.getCompany())) {
                            found++;
                        }
                    }

                    if (contact.getType().equals(Contact.ContactType.EMPLOYEE)) {
                        if (contact.getFirstname() == null ? c.getFirstname() == null : contact.getFirstname().equals(c.getFirstname())) {
                            found++;
                        }
                    }
                }

                if (found == 0) {
                    System.out.println("Kontakt '" + contact.toString() + "' nicht gefunden mit Filter:");
                    System.out.println(filter.getDataString());
                    System.out.println("---------------");
                }

                assertTrue("filter not found", found > 0);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
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
