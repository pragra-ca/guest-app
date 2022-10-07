
package co.pragra.learning.business;

import co.pragra.learning.model.Contact;
import co.pragra.learning.model.ContactType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ContactServiceTest {

    /*
    Code coverage: 100%

    Methods unit tested in ContactService class:
    addContact() - tested by addValidContact(), addInvalidContact(), editExistingContact(), editNonExistentContact()
    checkIfExist() - tested by checkExistingContact(), checkNonExistentContact()
    getContactByPhone() - tested by getExistingContact(), editNonExistentContact()
    getContactsSize() - tested by getContactsList()
    deleteContact() - tested by deleteExistingContact(), deleteNonExistentContact()
    printAll() - tested by printAllContacts()
     */

    private ContactService service;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        service = new ContactService();

        Contact contact = Contact.builder().firstName("John")
                .lastName("Deer")
                .phoneNumber("4161234456")
                .build();
        service.addContact(contact);

        // this can be simplified by the system-rules external library
        // for now, just do manual output stream captor
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void getContactsList() {
        // setUp() has already added 1 contact
        // add another contact to make the list count to 2
        Contact contact = Contact.builder().firstName("Jane")
                .lastName("Smith")
                .phoneNumber("9053456678")
                .build();
        service.addContact(contact);
        Assert.assertEquals(2, service.getContactsSize());
    }

    @Test
    public void addValidContact() {
        int beforeAddSize = service.getContactsSize();
        Contact contact = Contact.builder().firstName("Jane")
                .lastName("Smith")
                .phoneNumber("9053456678")
                .build();
        service.addContact(contact);
        Assert.assertTrue(service.getContactsSize() > beforeAddSize);
    }

    @Test
    public void addInvalidContact() {
        int beforeAddSize = service.getContactsSize();
        //String eMsg = "";
        Contact contact = Contact.builder().firstName("Jane")
                .lastName("Smith")
                // set invalid phone number
                .phoneNumber("ABC4161234456")
                .build();
        try {
            service.addContact(contact);
        } catch (Exception e) {
            //eMsg = e.getMessage();
        }
        //Assert.assertTrue(eMsg.equals("Phone is not a numeric value"));
        Assert.assertTrue(service.getContactsSize() == beforeAddSize);
    }

    @Test
    public void editExistingContact() {
        // get existing contact.
        // this will include the positive test on checkIfExist() method
        Contact existingContact = service.getContactByPhone("4161234456");
        Contact contact = Contact.builder().firstName("John")
                .lastName("Deer")
                .phoneNumber("4161234456")
                // keep existing data, but change contact type from PERSONAL to BUSINESS
                .contactType(ContactType.BUSINESS)
                .build();
        service.addContact(contact);
        Contact updatedContact = service.getContactByPhone("4161234456");
        Assert.assertTrue(updatedContact.getPhoneNumber().equals(existingContact.getPhoneNumber()) &&
                updatedContact.getLastName().equals(existingContact.getLastName()) &&
                updatedContact.getFirstName().equals(existingContact.getFirstName()) &&
                // assert that the contact type value has been actually updated.
                !updatedContact.getContactType().equals(existingContact.getContactType()));
    }

    @Test
    public void editNonExistentContact() {
        String eMsg = "";
        // attempt to get a non-existent contact
        try {
            // this will include the negative test on checkIfExist() method
            Contact existingContact = service.getContactByPhone("4161234477");
        } catch (Exception e) {
            eMsg = e.getMessage();
        }
        Assert.assertTrue(eMsg.equals("Contact Doesn't exist"));
    }

    @Test
    public void checkExistingContact() {
        Assert.assertEquals(0, service.checkIfExist("4161234456"));
    }

    @Test
    public void checkNonExistentContact() {
        Assert.assertEquals(-1, service.checkIfExist("4161234499"));
    }

    @Test
    public void getExistingContact() {
        Contact existingContact = service.getContactByPhone("4161234456");
        Assert.assertTrue(existingContact.getFirstName().equals("John") &&
                existingContact.getLastName().equals("Deer") &&
                existingContact.getContactType().equals(ContactType.PERSONAL));
    }

    @Test
    public void deleteExistingContact() {
        int beforeDeleteSize = service.getContactsSize();
        service.deleteContact("4161234456");
        Assert.assertTrue(service.getContactsSize() < beforeDeleteSize);
    }

    @Test
    public void deleteNonExistentContact() {
        String eMsg = "";
        // attempt to delete a non-existent contact
        try {
            service.deleteContact("4161234488");
        } catch (Exception e) {
            eMsg = e.getMessage();
        }
        Assert.assertTrue(eMsg.equals("Contact Doesn't exist"));
    }

    @Test
    public void printAllContacts() {
        service.printAll();
        Assert.assertEquals("Contact 0" +
                " (firstName: John" +
                ", lastName: Deer" +
                ", phoneNumber: 4161234456" +
                ", contactType: PERSONAL" +
                ")", outputStreamCaptor.toString()
                .trim());
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }


}
