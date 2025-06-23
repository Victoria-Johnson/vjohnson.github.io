package contact.contactservice;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test;


public class ContactServiceTest { 
	@Test
	@DisplayName("Add a new Contact") public void addContact() {
		ContactService cs = new ContactService();
		Contact contact = new Contact("1234567891", "Jack", "Frost", "2223334444", "5656 Freezing Lane");
		cs.addContact(contact);
		
		assertEquals(1, cs.getContacts().size());
		Contact createdContact = cs.getContacts().get(0); 
		assertTrue(createdContact.getContactId().equals("1234567891")); 
		assertTrue(createdContact.getFirstName().equals("Jack")); 
		assertTrue(createdContact.getLastName().equals("Frost")); 
		assertTrue(createdContact.getPhoneNumber().equals("2223334444")); 
		assertTrue(createdContact.getAddress().equals("5656 Freezing Lane"));
	}
	@Test
	@DisplayName("Add contact with a unique contact ID") public void addUniqueIdContact() {
		ContactService cs = new ContactService();
		Contact initialContact = new Contact("1234567891", "Jack", "Frost", "2223334444", "5656 Freezing Lane");
		cs.addContact(initialContact);

		Contact newContact = new Contact("1234567892", "Rose", "Thrones", "3334445555", "4444 Fields of Flowers Ln");
		cs.addContact(newContact);

		assertEquals(2, cs.getContacts().size());
		Contact createdContact = cs.getContacts().get(1); 
		assertTrue(createdContact.getContactId().equals("1234567892")); 
		assertTrue(createdContact.getFirstName().equals("Rose")); 
		assertTrue(createdContact.getLastName().equals("Thrones")); 
		assertTrue(createdContact.getPhoneNumber().equals("3334445555")); 
		assertTrue(createdContact.getAddress().equals("4444 Fields of Flowers Ln"));
	}

	@Test
	@DisplayName("Add contact with a duplicate contact ID") 
	public void addDuplicateContact() {
		ContactService cs = new ContactService();
		Contact contact1 = new Contact("1234567891", "Jack", "Frost", "2223334444", "5656 Freezing Lane");
		cs.addContact(contact1);

		Contact contact2 = new Contact("1234567892", "Rose", "Thrones", "3334445555", "4444 Fields of Flowers Ln");
		cs.addContact(contact2);

		Contact contact3 = new Contact("1234567891", "Amy", "Adams", "4445556666", "789 Love Drive");
		Assertions.assertThrows(IllegalArgumentException.class, () -> { cs.addContact(contact3);
		});
	}

	@Test
	@DisplayName("Delete a contact") public void deleteContact() {
		ContactService cs = new ContactService();
		cs.addContact(new Contact("1234567891", "Jack", "Frost", "2223334444", "5656 Freezing Lane"));
		cs.addContact(new Contact("1234567892", "Rose", "Thrones", "3334445555", "4444 Fields of Flowers Ln"));
		cs.addContact(new Contact("1234567893", "Amy", "Adams", "4445556666", "789 Love Drive"));
 
		cs.deleteContact("1234567891"); 
		assertEquals(2, cs.getContacts().size());
		assertTrue(cs.getContacts().get(0).getContactId().equals("1234567892")); 
		assertTrue(cs.getContacts().get(1).getContactId().equals("1234567893"));
	}

	@Test
	@DisplayName("Delete a contact with an non-existing contact id") public void deleteNonExistContact() {
		ContactService cs = new ContactService();
		cs.addContact(new Contact("1234567891", "Jack", "Frost", "2223334444", "5656 Freezing Lane"));
		cs.addContact(new Contact("1234567892", "Rose", "Thrones", "3334445555", "4444 Fields of Flowers Ln"));
		cs.addContact(new Contact("1234567893", "Amy", "Adams", "4445556666", "789 Love Drive"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> { cs.deleteContact("1234567895");
		});
	}

	@Test
	@DisplayName("Update a contact") public void updateContact() {
		ContactService cs = new ContactService();
		cs.addContact(new Contact("1234567891", "Jack", "Frost", "2223334444", "5656 Freezing Lane"));
		cs.addContact(new Contact("1234567892", "Rose", "Thrones", "3334445555", "4444 Fields of Flowers Ln"));
		cs.addContact(new Contact("1234567893", "Amy", "Adams", "4445556666", "789 Love Drive"));
		cs.updateContact("1234567891", "Jack", "Frost", "2223334444", "3234 Frozen Blvd");

		assertEquals(3, cs.getContacts().size()); 
		assertTrue(cs.getContacts().get(0).getContactId().equals("1234567891")); 
		assertTrue(cs.getContacts().get(0).getFirstName().equals("Jack")); 
		assertTrue(cs.getContacts().get(0).getLastName().equals("Frost"));
		assertTrue(cs.getContacts().get(0).getPhoneNumber().equals("2223334444")); 
		assertTrue(cs.getContacts().get(0).getAddress().equals("3234 Frozen Blvd"));
	}

	@Test
	@DisplayName("Update a contact with a non-existing contact id") public void updateNonExistContact() {
		ContactService cs = new ContactService();
		cs.addContact(new Contact("1234567891", "Jack", "Frost", "2223334444", "5656 Freezing Lane"));
		cs.addContact(new Contact("1234567892", "Rose", "Thrones", "3334445555", "4444 Fields of Flowers Ln"));
		cs.addContact(new Contact("1234567893", "Amy", "Adams", "4445556666", "789 Love Drive"));

		Assertions.assertThrows(IllegalArgumentException.class, () -> { cs.updateContact("1234567894", "Jack", "Frost", "2223334444", "5656 Freezing Lane");
		});
	}
}
