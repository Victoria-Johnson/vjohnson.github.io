package contact.contactservice;

import java.util.ArrayList;
import java.util.List;

public class ContactService {
	private List<Contact> contacts;

public ContactService() {
contacts = new ArrayList<>();
}

public List<Contact> getContacts(){
	return contacts;
}

// Add contacts with a unique ID
public void addContact(Contact newContact) {
	boolean isIdExists = false;
// Check to see if the Id is unique
	for(Contact contact : contacts) {
		if(contact.getContactId().equals(newContact.getContactId())) {
			isIdExists = true;
		}
	}
	if(isIdExists) {
		throw new IllegalArgumentException("Id already exists.");
	} else {
		contacts.add(newContact);
	}
}
// Delete a contact per Id
public void deleteContact(String contactId) {
	Contact contactToRemove = null;
// Check if the contact Id is in the list. If yes, set it to contactToRemove
	for (Contact contact : contacts) {
		if (contact.getContactId().equals(contactId)) {
			contactToRemove = contact;
			break;
		}
	}
	if (contactToRemove == null) {
		throw new IllegalArgumentException("There is no such contact to delete.");
	} else {
		contacts.remove(contactToRemove);
	}
}
// Update contact fields per contact ID. Updatable fields: firstName, lastName, Number, Address
public void updateContact(String contactId, String firstName, String lastName, String phone, String address) {
	boolean isIdExists = false;
	for (Contact contact : contacts) {
		if (contact.getContactId().equals(contactId)) {
			contact.setFirstName(firstName);
			contact.setLastName(lastName);
			contact.setPhone(phone);
			contact.setAddress(address);
			isIdExists = true;
			break;
			}
		}
		if(!isIdExists) {
			throw new IllegalArgumentException("There is no such contact to update.");
		}
	}
}
