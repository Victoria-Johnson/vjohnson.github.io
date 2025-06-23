import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    public void testValidContactCreation() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        assertEquals("12345", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
    }

    @Test
    public void testInvalidPhoneThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "Jane", "Smith", "12345", "456 Main St");
        });
    }

    @Test
    public void testToFileFormat() {
        Contact contact = new Contact("001", "Alice", "Brown", "5555555555", "789 Elm St");
        assertEquals("001,Alice,Brown,5555555555,789 Elm St", contact.toFileFormat());
    }

    @Test
    public void testFromFileFormat() {
        Contact contact = Contact.fromFileFormat("002,Bob,Smith,1112223333,321 Oak St");
        assertEquals("002", contact.getContactId());
        assertEquals("Bob", contact.getFirstName());
        assertEquals("Smith", contact.getLastName());
        assertEquals("1112223333", contact.getPhone());
        assertEquals("321 Oak St", contact.getAddress());
    }

    @Test
    public void testSettersUpdateFields() {
        Contact contact = new Contact("999", "Tom", "Lee", "9998887777", "Old Rd");
        contact.setFirstName("Tim");
        contact.setLastName("Li");
        contact.setPhone("1231231234");
        contact.setAddress("New Rd");

        assertEquals("Tim", contact.getFirstName());
        assertEquals("Li", contact.getLastName());
        assertEquals("1231231234", contact.getPhone());
        assertEquals("New Rd", contact.getAddress());
    }

    @Test
    public void testFromFileFormatWithCorruptDataThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Contact.fromFileFormat("Bad,Data,MissingFields");
        });
    }
} 