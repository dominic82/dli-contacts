/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dli_contacts.sapconnector;

import dli_contacts.Contact;
import dli_contacts.DummyConnector;
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
public class main_sapkonnektorTest extends TestCase {

    private Contact contactSupplier = new Contact();
    private Contact contactCustomer = new Contact();
    private Contact contactEmployee = new Contact();

    public main_sapkonnektorTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        contactSupplier = new Contact();
        contactSupplier.setCompany("Paper Suplies");
        contactSupplier.setStreet("Jakobalee  45");
        contactSupplier.setCity("Berlin");
        contactSupplier.setType(Contact.ContactType.SUPPLIER);

        contactCustomer = new Contact();
        contactCustomer.setCompany("Karoline Kraft");
        contactCustomer.setStreet("Weigandufer");
        contactCustomer.setCity("Berlin");
        contactCustomer.setType(Contact.ContactType.CUSTOMER);

        contactEmployee = new Contact();
        contactEmployee.setFirstname("Anja");
        contactEmployee.setLastname("Müller");
        contactEmployee.setCity("Karlsruhe");
        contactEmployee.setType(Contact.ContactType.EMPLOYEE);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of fetchContact method, of class main_sapkonnektor.
     */
    public void testFetchContact() {
        searchContact(contactCustomer);
        searchContact(contactSupplier);
//        searchContact(contactEmployee);
    }

    private void searchContact(Contact contact) {
        System.out.println("fetchContacts: looking for..." + contact.getType().toString());

        List attributeList = new ArrayList();

        if (contact.getType().equals(Contact.ContactType.CUSTOMER) || contact.getType().equals(Contact.ContactType.SUPPLIER)) {
            attributeList.add("Company");
        }
        if (contact.getType().equals(Contact.ContactType.EMPLOYEE)) {
            attributeList.add("Firstname");
//            attributeList.add("Lastname");
        }

//        attributeList.add("Street");
        attributeList.add("City");

        System.out.println(attributeList.toString());

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
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                filter.setType(contact.getType());

                System.out.println(filter.getDataString());
                List result = main_sapkonnektor.fetchContact(filter);
                System.out.println("Ergebnisse: " + result.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }

                for (Iterator it = result.iterator(); it.hasNext();) {
                    Contact c = (Contact) it.next();
                    //System.out.println(c.toString());
                    if (contact.getType().equals(Contact.ContactType.CUSTOMER) || contact.getType().equals(Contact.ContactType.SUPPLIER)) {
                        if (contact.getCompany() == null ? c.getCompany() == null : contact.getCompany().equals(c.getCompany())) {
                            found = true;
                        }
                    }

                    if (contact.getType().equals(Contact.ContactType.EMPLOYEE)) {
                        if (contact.getFirstname() == null ? c.getFirstname() == null : contact.getFirstname().equals(c.getFirstname())) {
                            found = true;
                        }
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
