/*
 *
 * CREATE TABLE airport (
 *  airportID int NOT NULL AUTO_INCREMENT,
 *  city VARCHAR (45),
 *  PRIMARY KEY (airportID) 
 * ) AUTO_INCREMENT = 1000;
 * 
 */
public class AirportPOJO {
	int airportID;
	String city;
	
	protected int getAirportID() {
		return airportID;
	}
	protected void setAirportID(int airportID) {
		this.airportID = airportID;
	}
	protected String getCity() {
		return city;
	}
	protected void setCity(String city) {
		this.city = city;
	}
	
	
}
