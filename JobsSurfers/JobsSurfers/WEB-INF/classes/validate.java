import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class validate extends HttpServlet {
    Connection conn;
    
    public void init(ServletConfig config) throws ServletException {
      super.init(config);
      try {
          Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
          conn=DriverManager.getConnection("jdbc:odbc:JobSurfers"); 
      } catch(Exception e) {
          e.printStackTrace();
      }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        
        HttpSession session = req.getSession(false);
        String sql = "";

        String user=req.getParameter("user");
        String aptitude=req.getParameter("aptitude");
        String validator=req.getParameter("validator");

        String inuser=req.getParameter("inuser");
        String inaptitude=req.getParameter("inaptitude");
        String invalidator=req.getParameter("invalidator");

        if(user!=null && aptitude!=null && validator!=null){
          sql = "INSERT INTO Validations (User, Aptitude, Validator) VALUES (";
          sql +=  "'" + user + "'";
          sql +=  ", '" + aptitude + "'";
          sql +=  ", '" + validator + "')";
          System.out.println("Insert sql: " + sql);
        }

        if(inuser!=null && inaptitude!=null && invalidator!=null){
          sql = "DELETE FROM Validations";
          sql +=  " WHERE User = '" + inuser + "'";
          sql +=  " AND Aptitude = '" + inaptitude + "'";
          sql +=  " AND Validator =  '" + invalidator + "';";
          System.out.println("Delete sql: " + sql);
        }

        try{
            Statement stmt=conn.createStatement();
            int num = stmt.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Exception in creation: " + e);
        }
    }
}