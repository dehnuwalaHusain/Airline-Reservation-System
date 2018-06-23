/*
 * 
 * CREATE TABLE user (
 *	userID int AUTO_INCREMENT,
 *  firstName VARCHAR(45),
 *  lastName VARCHAR(45), 
 *  address VARCHAR (200),
 *  email VARCHAR(45) UNIQUE,
 *  age INT,
 *  contact VARCHAR (10),
 *  password VARCHAR(45), 
 *  PRIMARY KEY (userID) 
 * ) AUTO_INCREMENT = 1006;
 * 
 * 
 */
public class UserPOJO {

	int userID;
	String firstName;
	String lastName;
	String address;
	String email;
	int age;
	Long contact;
	String password;
	
	protected int getUserID() {
		return userID;
	}
	protected void setUserID(int userID) {
		this.userID = userID;
	}
	protected String getFirstName() {
		return firstName;
	}
	protected void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	protected String getLastName() {
		return lastName;
	}
	protected void setLastName(String lastName) {
		this.lastName = lastName;
	}
	protected String getAddress() {
		return address;
	}
	protected void setAddress(String address) {
		this.address = address;
	}
	protected String getEmail() {
		return email;
	}
	protected void setEmail(String email) {
		this.email = email;
	}
	protected int getAge() {
		return age;
	}
	protected void setAge(int age) {
		this.age = age;
	}
	protected Long getContact() {
		return contact;
	}
	protected void setContact(Long contact) {
		this.contact = contact;
	}
	protected String getPassword() {
		return password;
	}
	protected void setPassword(String password) {
		this.password = password;
	}
	
	UserPOJO ()	{
		this.userID = -1;
	}
	
	
	
}
