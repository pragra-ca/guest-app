package co.pragra.learning.business;

import co.pragra.learning.model.Contact;
import co.pragra.learning.model.ContactType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contact.setContactType(ContactType.PERSONAL);

        int index = checkIfExist(contact.getPhoneNumber());
        if (index>= 0){
            contacts.set(index,contact);

        }else {
            contacts.add(contact);
        }
    }

    private int checkIfExist(String phone) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getPhoneNumber().equals(phone)) {
                return i;
            }

        }
        return -1;
    }
    public Contact getContactByPhone(String phone){

        if (!StringUtils.isNumeric(phone)){
            throw new RuntimeException("Phone is not a Numeric value");
        }
        int exist =checkIfExist(phone);
        if (exist>=0){
            return contacts.get(exist);
        }
        else{
            throw new RuntimeException("Contact does not Exist");
        }




    }
    public void printAll(){
        System.out.println(contacts);
    }


}


