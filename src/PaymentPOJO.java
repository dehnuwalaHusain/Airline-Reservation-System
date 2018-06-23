/*
 * 
 * 
 * CREATE TABLE payment (
 *	paymentID INT NOT NULL AUTO_INCREMENT,
 *	userID INT NOT NULL,
 *  cost INT,
 *  PRIMARY KEY (paymentID),
 *  FOREIGN KEY payment_userID (userID)
 *  REFERENCES user(userID)
 * )AUTO_INCREMENT = 100232;
 * 
 */
public class PaymentPOJO {

	int paymentID;
	int userID;
	int cost;
	protected int getPaymentID() {
		return paymentID;
	}
	protected void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}
	protected int getUserID() {
		return userID;
	}
	protected void setUserID(int userID) {
		this.userID = userID;
	}
	protected int getCost() {
		return cost;
	}
	protected void setCost(int cost) {
		this.cost = cost;
	}
	
	
}
