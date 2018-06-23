DROP DATABASE IF EXISTS airline;
CREATE DATABASE airline;
USE airline;

-- --------------------------------------------------------------------------
-- DROP TABLE IF EXISTS user;
-- Address and contact

CREATE TABLE user (
	userID int AUTO_INCREMENT,
    firstName VARCHAR(45),
    lastName VARCHAR(45), 
    address VARCHAR (200),
    email VARCHAR(45) UNIQUE,
    age INT,
    contact VARCHAR (10),
    password VARCHAR(45), 
	PRIMARY KEY (userID)
) AUTO_INCREMENT = 1006;

-- --------------------------------------------------------------------------
-- DROP TABLE IF EXISTS airport;
CREATE TABLE airport (
	airportID int NOT NULL AUTO_INCREMENT,
    city VARCHAR (45),
    PRIMARY KEY (airportID) 
) AUTO_INCREMENT = 1000;

-- --------------------------------------------------------------------------

-- DROP TABLE IF EXISTS flight;
CREATE TABLE flight (
	flightID int(11) NOT NULL AUTO_INCREMENT,
    departureCity INT,
    arrivalCity INT,
    departureTime time,
    arrivalTime time,
    PRIMARY KEY (flightID),
	FOREIGN KEY flight_depart(departureCity)
	REFERENCES airport(airportID),
    FOREIGN KEY flight_arrive(arrivalCity)
    REFERENCES airport(airportID)
) AUTO_INCREMENT = 8000;

-- ------------------------------------------------------------------------

-- DROP TABLE IF EXISTS booking;
CREATE TABLE booking (
	ticketID int NOT NULL auto_increment,
    name VARCHAR(45) NOT NULL,
    flightID INT NOT NULL,
    class VARCHAR (45) NOT NULL,
    userID int NOT NULL,
    PRIMARY KEY (ticketID),
	foreign key booking_flight(flightID)
    REFERENCES flight(flightID),
    foreign key booking_users(userID)
    references user(userID)
) AUTO_INCREMENT = 10000;

-- ------------------------------------------------------------------------

-- DROP TABLE IF EXISTS bookingStatus;
CREATE TABLE bookingStatus (
	flightID INT,
    economySeats INT,
    economyPrice INT,
    businessSeats INT,
    businessPrice INT,
    FOREIGN KEY bookingStatus_flight (flightID)
    REFERENCES flight(flightID)
);

-- ------------------------------------------------------------------------

-- DROP TABLE IF EXISTS prices;
-- CREATE TABLE prices (
-- flightID INT,
-- economyPrice INT,
-- businessPrice INT,
-- FOREIGN KEY prices_flight(flightID)
-- REFERENCES flight(flightID)
-- );

-- ------------------------------------------------------------------------

CREATE TABLE payment (
	paymentID INT NOT NULL AUTO_INCREMENT,
	userID INT NOT NULL,
    cost INT,
    PRIMARY KEY (paymentID),
    FOREIGN KEY payment_userID (userID)
    REFERENCES user(userID)
)AUTO_INCREMENT = 100232;

-- ------------------------------------------------------------------------