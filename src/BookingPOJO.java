/*
 * 
 * CREATE TABLE booking (
 *	ticketID int NOT NULL auto_increment,
 *  name VARCHAR(45) NOT NULL,
 *  flightID INT NOT NULL,
 *  class VARCHAR (45) NOT NULL,
 *  userID int NOT NULL,
 *  PRIMARY KEY (ticketID),
 *	foreign key booking_flight(flightID)
 *  REFERENCES flight(flightID),
 *  foreign key booking_users(userID)
 *  references user(userID)
 * ) AUTO_INCREMENT = 10000
 * 
 */
public class BookingPOJO {
	int ticketID;
	String name;
	int flightID;
	String travel_class;
	int userID;
	
	protected int getTicketID() {
		return ticketID;
	}
	protected void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}
	protected String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	protected int getFlightID() {
		return flightID;
	}
	protected void setFlightID(int flightID) {
		this.flightID = flightID;
	}
	protected String getTravel_class() {
		return travel_class;
	}
	protected void setTravel_class(String travel_class) {
		this.travel_class = travel_class;
	}
	protected int getUserID() {
		return userID;
	}
	protected void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
