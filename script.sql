CREATE TABLE room_record(
			 roomNumber INTEGER NOT NULL,
             pricePerNight FLOAT,
             availability VARCHAR(20),
             maxCapacity INTEGER,
             CONSTRAINT PRIMARY KEY(roomNumber));

CREATE TABLE guest_account_record(
			 guestID INTEGER NOT NULL,
             firstName VARCHAR(25),
             lastName VARCHAR(25),
             email VARCHAR(25),
             telno VARCHAR(25),
             CONSTRAINT PRIMARY KEY(guestID),
             CONSTRAINT UNIQUE(telno));             

CREATE TABLE reservation_record(
			 reserveID INTEGER NOT NULL,
             bookRefID INTEGER NOT NULL,
             roomRefID INTEGER NOT NULL,
             checkIn DATETIME,
             checkOut DATETIME,
             totalCost FLOAT,
             reservationStatus VARCHAR(20),
			 CONSTRAINT PRIMARY KEY(reserveID),
             CONSTRAINT FOREIGN KEY(bookRefID) REFERENCES guest_account_record(guestID),
             CONSTRAINT FOREIGN KEY(roomRefID) REFERENCES room_record(roomNumber));

# TEST DATA
INSERT INTO room_record
VALUES (1,1000.00,'Available',1),
	   (2,2000.00,'Occupied',2),
       (3,3000.00,'Available',3), 
	   (4,4000.00,'Occupied',4),
	   (5,5000.00,'Available',5);

INSERT INTO guest_account_record
VALUES (1,'Alice','Anderson','alice.anderson@gmail.com','1-387-779-0427'),
	   (2,'Bob','Barley','bob.barley@gmail.com','1-480-557-6270'),
       (3,'Charlie','Coy','charlie.coy@gmail.com','1-957-324-2443'),
       (4,'David','Darman','david.darman@gmail.com','1-656-743-5091'),
       (5,'Edward','Elliot','edward.elliot@gmail.com','813-311-7424');

INSERT INTO reservation_record
VALUES (1,1,2,'2025-02-28','2025-03-04',10000.0,'Booked'),
	   (2,3,4,'2025-03-01','2025-03-03',12000.0,'Booked');