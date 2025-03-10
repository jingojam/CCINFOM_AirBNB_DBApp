/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package checkinServlet;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import reservationRecords.Reservations;

/**
 *
 * 
 * servlet is intermediate between the reservations class and the webpage
 * handle check in request by updating reservaton status
 * buggy
 */
@WebServlet(name = "CheckinServlet", urlPatterns = {"/CheckinServlet"})
public class CheckinServlet extends HttpServlet {
    /**
     * 
     * process both GET and POST requests for checking in reservation
     * gets the reservationID from request and then validates it and then updates check in status
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");//set response type
        //retrieve the reservationID from request
        String sReserveID = request.getParameter("reserveID");
        String message = "";
        
        //instantiate new Reservations object (see Reservations.java for reference)
        Reservations resv = new Reservations();
        
        //validate reservationID
        if(sReserveID != null && !sReserveID.trim().isEmpty()){
            try{ 
                int reserveID = Integer.parseInt(sReserveID); //convert string into integer type
                resv.setReserveID(reserveID); //set the table attribute to the reservationID
                boolean success = resv.updateCheckIn();
                
                if(success){
                    message = "Reservation ID " + reserveID + " Successfully Checked-in";
                } else{
                    message = "Reservation ID " + reserveID + " Check-in Failed";
                }
                
            } catch(NumberFormatException nForme){
                message = "Invalid Reservation ID"; //handle invalid input format
            }
        } else{ //if no reservationID inputted so set this aas the prompt
            message = "Please Provide a Valid Reservation ID";
        }
        
        //attach message to request
        request.setAttribute("message", message);
        //forward to the jsp page for displaying the result
        RequestDispatcher dispatcher = request.getRequestDispatcher("update_checkin.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * 
     * handle HTTP GET requests 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        processRequest(request,response);
    }
    
    /**
     * 
     * handle HTTP POST requests
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
