/*
 * 
 * CREATE TABLE bookingStatus (
 *  flightID INT,
 *  economySeats INT,
 *  economyPrice INT,
 *  businessSeats INT,
 *  businessPrice INT,
 *  FOREIGN KEY bookingStatus_flight (flightID)
 *  REFERENCES flight(flightID)
 * );
 *
 */

public class BookingStatusPOJO {

	int flightID;
	int economySeats;
	int economyPrice;
	int businessSeats;
	int businessPrice;
	
	protected int getFlightID() {
		return flightID;
	}
	protected void setFlightID(int flightID) {
		this.flightID = flightID;
	}
	protected int getEconomySeats() {
		return economySeats;
	}
	protected void setEconomySeats(int economySeats) {
		this.economySeats = economySeats;
	}
	protected int getEconomyPrice() {
		return economyPrice;
	}
	protected void setEconomyPrice(int economyPrice) {
		this.economyPrice = economyPrice;
	}
	protected int getBusinessSeats() {
		return businessSeats;
	}
	protected void setBusinessSeats(int businessSeats) {
		this.businessSeats = businessSeats;
	}
	protected int getBusinessPrice() {
		return businessPrice;
	}
	protected void setBusinessPrice(int businessPrice) {
		this.businessPrice = businessPrice;
	}
	
	
}
