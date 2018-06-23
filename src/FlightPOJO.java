/*
 * 
 * CREATE TABLE flight (
 *	flightID int(11) NOT NULL AUTO_INCREMENT,
 *  departureCity INT,
 *  arrivalCity INT,
 *  departureTime time,
 *  arrivalTime time,
 *  PRIMARY KEY (flightID),
 *	FOREIGN KEY flight_depart(departureCity)
 *  REFERENCES airport(airportID),
 *  FOREIGN KEY flight_arrive(arrivalCity)
 *  REFERENCES airport(airportID)
 * ) AUTO_INCREMENT = 8000;
 * 
 */
public class FlightPOJO {
	int flightID;
	int departureCity;
	int arrivalCity;
	String departureTime;
	String arrivalTime;
	
	protected int getFlightID() {
		return flightID;
	}
	protected void setFlightID(int flightID) {
		this.flightID = flightID;
	}
	protected int getDepartureCity() {
		return departureCity;
	}
	protected void setDepartureCity(int departureCity) {
		this.departureCity = departureCity;
	}
	protected int getArrivalCity() {
		return arrivalCity;
	}
	protected void setArrivalCity(int arrivalCity) {
		this.arrivalCity = arrivalCity;
	}
	protected String getDepartureTime() {
		return departureTime;
	}
	protected void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	protected String getArrivalTime() {
		return arrivalTime;
	}
	protected void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	
}
