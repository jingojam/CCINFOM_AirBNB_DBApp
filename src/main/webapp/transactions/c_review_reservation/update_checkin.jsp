<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Check-in Details</title>
    </head>
    <body>
        <h1>Check-in Reservation</h1>
        
        <!--form to submit Reservation ID to the servlet-->
        <form action="<%= request.getContextPath() %>/CheckinServlet" method="POST">
            <label for="reserveID">Enter Reservation ID:</label>
            <input type="text" id="reserveID" name="reserveID" required>
            <button type="submit">Check-in</button>
        </form>
        
        <hr>
        
        <!--result-->
        <%
            String message = (String) request.getAttribute("message"); 
            
            if(message == null){
                message = "";
            }
            
            if (!message.isEmpty()) {
                out.println("<h2>" + message + "</h2>");
            }
        %>
        
    </body>
</html>
