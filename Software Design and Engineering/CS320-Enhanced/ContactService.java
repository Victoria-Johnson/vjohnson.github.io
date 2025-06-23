import java.io.*;
import java.util.*;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();
    private final String filename = "contacts.txt";

    public List<Contact> getContacts() {
        return contacts;
    }

    public void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contacts.add(Contact.fromFileFormat(line));
            }
        } catch (IOException e) {
            System.out.println("No saved contacts found or error reading file.");
        }
    }

    public void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Contact c : contacts) {
                writer.write(c.toFileFormat());
                writer.newLine();
            }
            System.out.println("Contacts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to display.");
            return;
        }
        for (Contact c : contacts) {
            System.out.println("ID: " + c.getContactId());
            System.out.println("First Name: " + c.getFirstName());
            System.out.println("Last Name: " + c.getLastName());
            System.out.println("Phone: " + c.getPhone());
            System.out.println("Address: " + c.getAddress());
            System.out.println("-----------------------");
        }
    }

    public void addContact(Scanner scanner) {
        try {
            System.out.print("Enter Contact ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter First Name: ");
            String first = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String last = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            contacts.add(new Contact(id, first, last, phone, address));
            System.out.println("Contact added successfully.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editContact(Scanner scanner) {
        System.out.print("Enter the ID of the contact to edit: ");
        String id = scanner.nextLine();
        Optional<Contact> contactOpt = contacts.stream().filter(c -> c.getContactId().equals(id)).findFirst();

        if (contactOpt.isPresent()) {
            Contact contact = contactOpt.get();
            try {
                System.out.print("Enter new First Name: ");
                contact.setFirstName(scanner.nextLine());
                System.out.print("Enter new Last Name: ");
                contact.setLastName(scanner.nextLine());
                System.out.print("Enter new Phone Number: ");
                contact.setPhone(scanner.nextLine());
                System.out.print("Enter new Address: ");
                contact.setAddress(scanner.nextLine());
                System.out.println("Contact updated successfully.\n");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Contact with ID " + id + " not found.");
        }
    }

    public void deleteContact(Scanner scanner) {
        System.out.print("Enter the ID of the contact to delete: ");
        String id = scanner.nextLine();
        boolean removed = contacts.removeIf(c -> c.getContactId().equals(id));
        if (removed) {
            System.out.println("Contact deleted successfully.\n");
        } else {
            System.out.println("Contact with ID " + id + " not found.");
        }
    }

    public void runCLI() {
        Scanner scanner = new Scanner(System.in);
        loadContacts();

        System.out.println("Welcome to the Contact Manager CLI");
        while (true) {
            System.out.println("\n1. Add Contact\n2. View Contacts\n3. Edit Contact\n4. Delete Contact\n5. Save & Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addContact(scanner);
                    break;
                case "2":
                    viewContacts();
                    break;
                case "3":
                    editContact(scanner);
                    break;
                case "4":
                    deleteContact(scanner);
                    break;
                case "5":
                    saveContacts();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        new ContactService().runCLI();
    }
}
