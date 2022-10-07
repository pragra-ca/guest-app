package co.pragra.learning;

import co.pragra.learning.business.ContactService;
import co.pragra.learning.model.Contact;
import co.pragra.learning.model.ContactType;

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        boolean leaveApp = false;
        String firstName, lastName, phoneNumber, contactType = "";
        ContactService service = new ContactService();
        Scanner scanner  = new Scanner(System.in);

        System.out.println("Menu options:");
        System.out.println("l - List all contacts");
        System.out.println("a - Add a contact");
        System.out.println("e - Edit a contact");
        System.out.println("d - Delete a contact");
        System.out.println("x - Exit the application");

        do {
            System.out.println("");
            System.out.print("Enter an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "l":
                    System.out.println("CONTACT LIST:");
                    service.printAll();
                    break;
                case "e":
                    try {
                        System.out.println("EDIT CONTACT...");
                        System.out.print("Enter the phone number of the contact to edit: ");
                        phoneNumber = scanner.nextLine();
                        Contact c = service.getContactByPhone(phoneNumber);
                        System.out.print("Enter the first name (" + c.getFirstName() + ") : ");
                        firstName = scanner.nextLine();
                        System.out.print("Enter the last name (" + c.getLastName() + ") : ");
                        lastName = scanner.nextLine();
                        System.out.print("Enter the contact type (" + c.getContactType() + ") : ");
                        contactType = scanner.nextLine();
                        if (ContactType.findByName(String.valueOf(contactType)) == null) {
                            throw new RuntimeException("Invalid contact type value");
                        }
                        Contact contact = Contact.builder().firstName(firstName)
                                .lastName(lastName)
                                .phoneNumber(phoneNumber)
                                .contactType(ContactType.valueOf(contactType))
                                .build();
                        service.addContact(contact);
                        System.out.println("CONTACT EDITED.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("CONTACT NOT EDITED.");
                    }
                    break;
                case "a":
                    try {
                        System.out.println("ADD CONTACT...");
                        System.out.print("Enter the phone number : ");
                        phoneNumber = scanner.nextLine();
                        if (service.checkIfExist(phoneNumber) < 0) {
                            System.out.print("Enter the first name: ");
                            firstName = scanner.nextLine();
                            System.out.print("Enter the last name: ");
                            lastName = scanner.nextLine();
                            Contact contact = Contact.builder().firstName(firstName)
                                    .lastName(lastName)
                                    .phoneNumber(phoneNumber)
                                    .build();
                            service.addContact(contact);
                            System.out.println("CONTACT ADDED.");
                        } else {
                            System.out.println("Contact already exist");
                            System.out.println("CONTACT NOT ADDED.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("CONTACT NOT ADDED.");
                    }
                    break;
                case "d":
                    try {
                        System.out.println("DELETE CONTACT...");
                        System.out.print("Enter the phone number of the contact to delete: ");
                        phoneNumber = scanner.nextLine();
                        service.deleteContact(phoneNumber);
                        System.out.println("CONTACT DELETED.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("CONTACT NOT DELETED.");
                    }
                    break;
                case "x":
                    leaveApp = true;
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Please choose a valid menu option...");
                    break;
            }
        }
        while (!leaveApp);
    }
}
