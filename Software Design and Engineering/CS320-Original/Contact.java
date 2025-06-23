package contact.contactservice;

public class Contact {
	private String contactId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	
	public Contact(String contactId, String firstName, String lastName, String phoneNumber, String address) {
		validateId(contactId);
		validateFirstName(firstName);
		validateLastName(lastName);
		validatePhone(phoneNumber);
		validateAddress(address);
		
		this.contactId = contactId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
// The contact ID shall not be null and shall not be updatable.
	public String getContactId() {
		return contactId;
	}	
// First Name
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		validateFirstName(firstName);
		this.firstName = firstName;
	}
// Last Name
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		validateLastName(lastName);
		this.lastName = lastName;
	}
//Phone Number
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhone(String phoneNumber) {
		validatePhone(phoneNumber);
		this.phoneNumber = phoneNumber;
	}
//Address
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		validateAddress(address);
		this.address = address;
	}
//unique contact ID string that cannot be longer than 10 characters
	private void validateId(String id) {
		if(id == null || id.length() > 10) {
			throw new IllegalArgumentException("Invalid id.");
		}
	}
// firstName String field that cannot be longer than 10 characters
	private void validateFirstName(String firstName) {
		if(firstName == null || firstName.length() > 10) {
			throw new IllegalArgumentException("Invalid first name.");
		}
	}
// lastName String field that cannot be longer than 10 characters.
	private void validateLastName(String lastName) {
		if(lastName == null || lastName.length() > 10) {
			throw new IllegalArgumentException("Invalid last name.");
		}
	}
// phone String field must be exactly 10 digits.
	void validatePhone(String phoneNumber){
		if(phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
			throw new IllegalArgumentException("Invalid phone number.");
		}
	}
// address field must be no longer than 30 characters.
	void validateAddress(String address) {
		if(address == null || address.length() > 30) {
			throw new IllegalArgumentException("Invalid address.");
		}
	}
}
