package dli_contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author dominic
 */
public class Contact {

    /**
     *  fixed definition of available contactgroups
     */
    public static enum ContactType {

        CUSTOMER, SUPPLIER, EMPLOYEE
    }
    private String googleId = "";
    private String sapId = "";
    private String firstname = "";
    private String lastname = "";
    private String company = "";
    private String street = "";
    private String zipcode = "";
    private String city = "";
    private String phone = "";
    private String email = "";
    private ContactType type = ContactType.CUSTOMER;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     *  fixed definition of handled errors
     */
    public static enum ValidationErrors {

        FIRSTNAME_INCORRECT,
        LASTNAME_INCORRECT,
        COMPANY_INCORRECT,
        STREET_INCORRECT,
        ZIPCODE_INCORRECT,
        CITY_INCORRECT,
        PHONE_INCORRECT,
        EMAIL_INCORRECT
    }

    /**
     * validates the values of the object-parameters
     * @return List of defined ValidationErrors
     */
    public List<ValidationErrors> validate() {
        List<ValidationErrors> errors = new ArrayList<ValidationErrors>();
        if (firstname.isEmpty()) {
            errors.add(ValidationErrors.FIRSTNAME_INCORRECT);
        }
        if (lastname.isEmpty()) {
            errors.add(ValidationErrors.LASTNAME_INCORRECT);
        }
        if (company.length() > 35) {
            errors.add(ValidationErrors.COMPANY_INCORRECT);
        }
        if (street.length() > 35) {
            errors.add(ValidationErrors.STREET_INCORRECT);
        }
        if (zipcode.length() > 10) {
            errors.add(ValidationErrors.ZIPCODE_INCORRECT);
        }
        if (city.length() > 35) {
            errors.add(ValidationErrors.CITY_INCORRECT);
        }
        if (phone.length() > 35) {
            errors.add(ValidationErrors.PHONE_INCORRECT);
        }
        if (!email.isEmpty() && !email.matches(EMAIL_PATTERN)) {
            errors.add(ValidationErrors.EMAIL_INCORRECT);
        }
        return errors;
    }

    /**
     * 
     * @return String-representation of the object
     */
    @Override
    public String toString() {
        String output = "";
        if (!getFirstname().isEmpty() || !getLastname().isEmpty()) {
            output += getFirstname() + " " + getLastname() + ", ";
        }
        if (getCompany() != null && !getCompany().isEmpty()) {
            output += getCompany() + ", ";
        }
        
        if (getCity() != null && !getCity().isEmpty()) {
            output += getCity();
        }
        
        return output;
    }

    public String getDataString() {
        String output = "";
        output += ("Name: " + getFirstname() + " " + getLastname() + "\n");
        output += ("Firma: " + getCompany() + "\n");
        output += ("Strasse: " + getStreet() + "\n");
        output += ("Ort: " + getZipcode() + " " + getCity() + "\n");
        output += ("Tel.: " + getPhone() + "\n");
        output += ("Email: " + getEmail() + "\n");
        output += ("Google-ID: " + getGoogleId() + "\n");
        output += ("SAP-ID: " + getSapId() + "\n");
        output += ("Gruppe: " + getType().toString());
        return output;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getSapId() {
        return sapId;
    }

    public void setSapId(String sapId) {
        this.sapId = sapId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }
}
