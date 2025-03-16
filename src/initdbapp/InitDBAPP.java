package initdbapp;
import java.util.*;
import java.sql.*;

public class InitDBAPP {
    private static final String dburl = "jdbc:mysql://localhost:3306/dbsales";
    private static final String user = "root";
    private static final String pass = "12345678";
    
    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(dburl,user,pass)){
            Statement query = conn.createStatement();
            ResultSet res = query.executeQuery("SELECT emp.employeeNumber, emp.firstName, emp.lastName FROM employees AS emp;");
            
            int code;
            String title;
            
            int employeeNum;
            String fname, lname;
            
            System.out.println("employeeNumber   fullName");
            
            while(res.next()){
                employeeNum = res.getInt("employeeNumber");
                lname = res.getString("lastName").trim();
                fname = res.getString("firstName").trim();
                
                System.out.println(employeeNum + 
                                   "             " + lname +
                                   " " + fname);
            }
            
        } catch(SQLException sqle){
            System.err.println(sqle);
        }
    }
}
