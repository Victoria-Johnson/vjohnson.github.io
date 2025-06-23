import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {
    private ContactService service;
    private final String testFilename = "test_contacts.txt";

    @BeforeEach
    public void setup() {
        service = new ContactService();
        // Reset the test file before each test
        try (PrintWriter writer = new PrintWriter(new FileWriter(testFilename))) {
            writer.print("");
        } catch (IOException e) {
            fail("Failed to initialize test file");
        }
    }

    @Test
    public void testAddAndViewContacts() {
        ByteArrayInputStream in = new ByteArrayInputStream("123\nAlice\nBrown\n5555555555\n789 Oak St\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        service.addContact(scanner);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        service.viewContacts();

        String output = out.toString();
        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("789 Oak St"));
    }

    @Test
    public void testSaveAndLoadContacts() {
        Contact contact = new Contact("001", "Bob", "Smith", "1112223333", "123 Main St");
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);

        // Save contacts to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFilename))) {
            for (Contact c : contacts) {
                writer.write(c.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            fail("Failed to write to test file");
        }

        // Load contacts from file using service method
        ContactService loader = new ContactService();
        loader.loadContacts();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        loader.viewContacts();
        String output = out.toString();

        assertTrue(output.contains("Bob"));
        assertTrue(output.contains("1112223333"));
    }

    @Test
    public void testEditContactUpdatesFields() {
        Contact contact = new Contact("edit1", "Ann", "Lee", "9999999999", "Old Rd");
        service.getContacts().add(contact);

        contact.setFirstName("Anna");
        contact.setPhone("1111111111");

        assertEquals("Anna", contact.getFirstName());
        assertEquals("1111111111", contact.getPhone());
    }

    @Test
    public void testDeleteContactById() {
        Contact contact = new Contact("del1", "Mark", "James", "2223334444", "Del St");
        service.getContacts().add(contact);

        int initialSize = service.getContacts().size();
        service.getContacts().removeIf(c -> c.getContactId().equals("del1"));
        int newSize = service.getContacts().size();

        assertEquals(initialSize - 1, newSize);
    }

    @Test
    public void testInteractiveEditContact() {
        Contact contact = new Contact("E1", "Jake", "Long", "1234567890", "Old Town");
        service.getContacts().add(contact);

        ByteArrayInputStream in = new ByteArrayInputStream("E1\nJames\nWong\n0987654321\nNew City\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        service.editContact(scanner);

        assertEquals("James", contact.getFirstName());
        assertEquals("Wong", contact.getLastName());
        assertEquals("0987654321", contact.getPhone());
        assertEquals("New City", contact.getAddress());
    }

    @Test
    public void testInteractiveDeleteContact() {
        Contact contact = new Contact("D1", "Lily", "Smith", "3334445555", "Delete Lane");
        service.getContacts().add(contact);

        ByteArrayInputStream in = new ByteArrayInputStream("D1\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        service.deleteContact(scanner);

        assertTrue(service.getContacts().stream().noneMatch(c -> c.getContactId().equals("D1")));
    }
}