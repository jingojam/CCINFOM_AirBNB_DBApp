/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package room_mgmt;
import java.sql.*;

/**
 *
 * class for reservation records table
 */
public class Reservations { //resv table for mySQL
    private static String dburl; //path to the sv
    private static String user;//username (root as always)
    private static String pass; //infom requires 12345678 passwd default)
    
    //fields (based on schema) in col order: 
    //reserveID|bookRefID|roomRefID|checkIn|checkOut|totalCost|reservationStatus
    //see generator sql scripts for reference
    private int reserveID, bookRefID, roomRefID;
    private String checkIn, checkOut;
    private float totalCost;
    private String reservationStatus;
    
    // default constructor (non-parameterized)
    public Reservations(){
        this.dburl = "jdbc:mysql://localhost:3306/airbnb"; 
        this.user = "root";
        this.pass = "12345678";
    };
    
    /**
     *  TRANSACTIONS 1-4
     */
    
    /**
     * 
     * transaction 1: update resv status to "checked-in"
     * 
     * fixed
     */
    public boolean checkIn(){
        //initialize connection to the mysql server via try-catch construct
        try(Connection connection = DriverManager.getConnection(dburl,user,pass)){
            //create sql statement to execute in mySQL
            //in this case, UPDATE
            PreparedStatement pst = connection.prepareStatement("UPDATE reservation_record\n" +
                                                                "SET reservationStatus = 'Checked-in' \n" +
                                                                "WHERE reserveID = ?");
            pst.setInt(1, getReserveID()); //get the reservation ID (primary key) of the reservations table
            pst.executeUpdate();//execute statement
            
            return true; //indicate successful
        } catch(SQLException sqle){ //if db connection fails
            return false; //indicate fail
        }
    }
    
    /**
     * 
     * transaction 2: update room reservation to "checked-out"
     */
    public boolean checkOut(){
        try(Connection connection = DriverManager.getConnection(dburl,user,pass)){ //establish connection to the database
            PreparedStatement pst = connection.prepareStatement("UPDATE reservation_record\n" +
                                                                "SET reservationStatus = 'Checked-out' \n" +
                                                                "WHERE reserveID = ?");
            pst.setInt(1, getReserveID()); //get the reservation ID (primary key) of the reservations table
            pst.executeUpdate(); //execute the statement
            
            return true;//indicate successful
        } catch(SQLException sqle){ //if connection to the database fails
            return false; //indicate fail
        }
    }
    
    /**
     * 
     * helper method for parsing Integer instantiations of date fields (year, day, month)
     * into a concatenated String (for SQL-format queries)
     * 
     */
    public String parseDate(Integer year, Integer month, Integer day){
        return year.toString() + "-" + month.toString() + "-" + day.toString();
    }
    
    /**
     * 
     * transaction 3a: update checkIn date
     */
    public boolean updateCheckin(){
       try(Connection connection = DriverManager.getConnection(dburl,user,pass)){//establish connection to the database
           //create a query
           PreparedStatement pst = connection.prepareStatement("UPDATE reservation_record\n"
                                                             + "SET checkIn = ?" +
                                                               "WHERE reserveID = ?"); 
           
           pst.setString(1, getCheckIn());
           pst.setInt(2, getReserveID());//get the reservation ID (primary key) of the reservations table
           pst.executeUpdate();  // execute the query
           
           return true; //return true if successful
       } catch(SQLException sqle){
           return false; // return false if fail to connect to the database
       }
    }
    
    /**
     * 
     *transaction 3b: update checkOut date 
     */
    public boolean updateCheckout(){
       try(Connection connection = DriverManager.getConnection(dburl,user,pass)){//establish connection to the database
           //create a query
           PreparedStatement pst = connection.prepareStatement("UPDATE reservation_record\n"
                                                             + "SET checkIn = ?" +
                                                               "WHERE reserveID = ?");
           
           pst.setString(1, getCheckIn());
           pst.setInt(2, getReserveID());//get the reservation ID (primary key) of the reservations table
           pst.executeUpdate(); //execute the query
           
           return true; //return true if successful
       } catch(SQLException sqle){ //catch block if the program doesn't connect to mySQL successfully
           return false; //return false to indicate
       }
    }
    
    /**
     * 
     * transaction 4: calculate total cost 
     */
    public float totalCost(){
       try(Connection connection = DriverManager.getConnection(dburl,user,pass)){ //establish connection to the database
           //create the SQL statement
           PreparedStatement query = connection.prepareStatement("SELECT resv.reserveID, room.pricePerNight * DATEDIFF(resv.checkOut,resv.checkIn) AS totalPrice\n" +
                                                                 "FROM reservation_record resv\n" +
                                                                 "JOIN room_record room ON resv.roomRefID = room.roomNumber\n" +
                                                                 "WHERE resv.reserveID = ?"); //? corresponds to input to be obtained within this java file
           query.setInt(1, getReserveID()); //get the reservation ID (primary key) of the reservations table
           
           ResultSet res = query.executeQuery(); //execute the query
                
           if(res.next()){ //get input from the table
               float price = res.getFloat("totalPrice"); //get the next value (row-wise) from the totalPrice column 
               return price; //return the total price
           }
       } catch(SQLException sqle){ //catch block if the program doesn't connect to mySQL successfully
           return -1; //flag (initial) 
       }
       return 0; //if no data matches the argument
    }
    
    //setters
    public void setReserveID(int reserveID){
        this.reserveID = reserveID;
    }
    
    public void setBookRefID(int bookRefID){
        this.bookRefID = bookRefID;
    }
    
    public void setRoomRefID(int roomRefID){
        this.roomRefID = roomRefID;
    }
    
    public void setCheckIn(String checkIn){
        this.checkIn = checkIn;
    }
    
    public void setCheckOut(String checkOut){
        this.checkOut = checkOut;
    }
    
    public void setTotalCost(float totalCost){
        this.totalCost = totalCost;
    }
    
    public void setReservationStatus(String reservationStatus){
        this.reservationStatus = reservationStatus;
    }
    
    //getters
    public int getReserveID(){
        return reserveID;
    }
    
    public int getBookRefID(){
        return bookRefID;
    }
    
    public int getRoomRefID(){
        return roomRefID;
    }
    
    public String getCheckIn(){
        return checkIn;
    }
    
    public String getCheckOut(){
        return checkOut;
    }
    
    public float getTotalCost(){
        return totalCost;
    }
    
    public String getReservationStatus(){
        return reservationStatus;
    }
}