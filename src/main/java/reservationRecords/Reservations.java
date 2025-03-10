/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reservationRecords;
import java.sql.*;
import java.util.*;
import java.io.*;

/**
 *
 * class for reservation records table
 */
public class Reservations { //resv table for mySQL
    private static final String dburl = "jdbc:mysql://localhost:3306/airbnb"; //path to the sv
    private static final String user = "root";//username (root as always)
    private static final String pass = "12345678"; //infom requires 12345678 passwd default)
    
    //fields (based on schema) in col order: 
    //reserveID|bookRefID|roomRefID|checkIn|checkOut|totalCost|reservationStatus
    //see generator sql scripts for reference
    private int reserveID, bookRefID, roomRefID;
    private String checkIn, checkOut;
    private float totalCost;
    private String reservationStatus;
    
    // default constructor (non-parameterized)
    public Reservations(){
    
    };
    
    /**
     * 
     * transaction 1: update resv status to "checked-in"
     * 
     * fixed
     */
    public boolean updateCheckIn(){
        //initialize connection to the mysql server via try-catch construct
        try(Connection connection = DriverManager.getConnection(dburl,user,pass)){
            //create sql statement to execute in mySQL
            //in this case, UPDATE
            PreparedStatement pst = connection.prepareStatement("UPDATE reservation_record\n" +
                                                                "SET reservationStatus = 'Checked-in' \n" +
                                                                "WHERE reserveID = ?");
            pst.setInt(1, getReserveID()); //obtain input
            pst.executeUpdate();//execute statement
            
            return true;
        } catch(SQLException sqle){ //if db connection fails
            System.err.println(sqle.getMessage());
            return false;
        }
    }
    
    public void checkInfo(){
        try(Connection connection = DriverManager.getConnection(dburl,user,pass)){
            Statement query = connection.createStatement();
            ResultSet result = query.executeQuery("SELECT guest.guestID, guest.firstName, guest.lastName, room.roomNumber\n"
                                                + "FROM guest_account_record guest\n"
                                                + "LEFT JOIN reservation_record resv ON guest.guestID = resv.bookRefID\n"
                                                + "LEFT JOIN room_record room ON room.roomNumber = resv.roomRefID");
            int guestID, roomNumber;
            String firstName, lastName;
            
            System.out.println("Reserved:");
            System.out.println("guestID   fullName        roomNumber");
            
            while(result.next()){
                guestID = result.getInt("guestID");
                firstName = result.getString("firstName");
                lastName = result.getString("lastName");
                roomNumber = result.getInt("roomNumber");
                
                System.out.println(guestID+"         "+firstName+" "+lastName+"   "+roomNumber);
            }
            
        } catch(SQLException sqle){
            System.err.println(sqle.getMessage());
        }
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
