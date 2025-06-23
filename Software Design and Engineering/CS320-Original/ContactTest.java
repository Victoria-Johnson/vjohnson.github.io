package contact.contactservice;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ContactTest {
//test ContactID
	@Test
	void testContactIDTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact("1234567890","Victoria","Johnson","1112223333","1234 Main Street");
		});
	}
	@Test
	void testContactIDNull() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact(null,"Victoria","Johnson","1112223333","1234 Main Street");
		});
	}
//test firstName
	@Test
	void testFirstNameTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact("1234567890","Vviccttoorriiaa","Johnson","1112223333","1234 Main Street");
		});
	}
	@Test
	void testFirstNameNull() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact("1234567890",null,"Johnson","1112223333","1234 Main Street");
		});
	}
//test lastName
	@Test
	void testLastNameTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact("1234567890","Victoria","Jjoohhnnssoonn","1112223333","1234 Main Street");
		});
	}
	@Test
	void testLastNameNull() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact("1234567890","Victoria",null, "1112223333","1234 Main Street");
		});
	}

//test phoneNumber
	@Test
	void testPhoneNumberTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact("1234567890","Victoria","Johnson","111222333333","1234 Main Street");
		});
	}
	@Test
	void testPhoneNumberNull() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact("1234567890","Victoria","Johnson",null,"1234 Main Street");
		});
	}
//test address
	@Test
	void testAddressTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact("1234567890","Victoria","Johnson","1112223333","1234 Main Street Smithfield Virginia 23430");
		});
	}
	@Test
	void testAddressNull() {
		Assertions.assertThrows(IllegalArgumentException.class,() ->{
			new Contact("1234567890","Victoria","Johnson","1112223333",null);
		});
	}
}
