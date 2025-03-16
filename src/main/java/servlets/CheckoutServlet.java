/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import room_mgmt.Reservations;

/**
 *
 * checkout servlet
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {
    private static final String dburl = "jdbc:mysql://localhost:3306/airbnb"; //path to the sv
    private static final String user = "root";//username (root as always)
    private static final String pass = "12345678"; //infom requires 12345678 passwd default)

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String message = "";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            response.setContentType("text/html;charset=UTF-8");
            Connection connection = DriverManager.getConnection(dburl, user, pass);
            
                        String sReserveID = request.getParameter("reserveID");
                        
            //instantiate new Reservations object (see Reservations.java for reference)
            Reservations resv = new Reservations();

            //validate reservationID
            if(sReserveID != null && !sReserveID.trim().isEmpty()){
                try{ 
                    int reserveID = Integer.parseInt(sReserveID); //convert string into integer type
                    resv.setReserveID(reserveID); //set the table attribute to the reservationID
                    boolean success = resv.checkOut();

                    if(success){
                        message = "Reservation ID " + reserveID + " Successfully Checked-out";
                    } else{
                        message = "Reservation ID " + reserveID + " Check-out Failed";
                    }

                } catch(NumberFormatException nForme){
                    message = "Invalid Reservation ID"; //handle invalid input format
                }
            } else{ //if no reservationID inputted so set this aas the prompt
                message = "Please Provide a Valid Reservation ID";
            }
        } catch(SQLException sqle){
            message = sqle.getMessage();
        } catch(ClassNotFoundException cnf){
            message = cnf.getMessage();
        }
        //attach message to request
        request.setAttribute("message", message);
        //forward to the jsp page for displaying the result
        RequestDispatcher dispatcher = request.getRequestDispatcher("/transactions/c_review_reservation/update_checkout.jsp");
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
