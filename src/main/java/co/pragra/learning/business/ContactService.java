package co.pragra.learning.business;

import co.pragra.learning.model.Contact;
import co.pragra.learning.model.ContactType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        if (!StringUtils.isNumeric(contact.getPhoneNumber())) {
            throw new RuntimeException("Phone is not a numeric value");
        }
        if (!contact.getFirstName().matches("[a-zA-Z]+")) {
            throw new RuntimeException("First name cannot contain numbers and special characters");
        }
        if (!contact.getLastName().matches("[a-zA-Z]+")) {
            throw new RuntimeException("Last name cannot contain numbers and special characters");
        }
        int index = checkIfExist(contact.getPhoneNumber());
        if (index >= 0){
            contacts.set(index,contact);
        } else {
            contact.setContactType(ContactType.PERSONAL);
            contacts.add(contact);
        }
    }

    public int checkIfExist(String phone) {
        for (int i = 0; i < contacts.size(); i++) {
            if(contacts.get(i).getPhoneNumber().equals(phone)){
                return i;
            }
        }
        return -1;
    }

    public Contact getContactByPhone(String phone) {
        if (!StringUtils.isNumeric(phone)) {
           throw new RuntimeException("Phone is not a numeric value");
        }
        int exist = checkIfExist(phone);
        if(exist>=0){
            return contacts.get(exist);
        }else {
            throw new RuntimeException("Contact Doesn't exist");
        }
    }

    public int getContactsSize() {
        return contacts.size();
    }

    public void deleteContact(String phone) {
        if (!StringUtils.isNumeric(phone)) {
            throw new RuntimeException("Phone is not a numeric value");
        }
        int index = checkIfExist(phone);
        if(index>=0){
            contacts.remove(index);
        }else {
            throw new RuntimeException("Contact Doesn't exist");
        }
    }

    public void printAll() {
        if (contacts.size() > 0) {
            for (int i = 0; i < contacts.size(); i++) {
                Contact c = contacts.get(i);
                System.out.println("Contact " + i +
                        " (firstName: " + c.getFirstName() +
                        ", lastName: " + c.getLastName() +
                        ", phoneNumber: " + c.getPhoneNumber() +
                        ", contactType: " + c.getContactType() +
                        ")");
            }
        } else {
            System.out.println("No contact records found");
        }
    }
}

