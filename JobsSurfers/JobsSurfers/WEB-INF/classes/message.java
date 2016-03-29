import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class message extends HttpServlet {
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

        String from=req.getParameter("from");
        String to=req.getParameter("to");
        String textMessage=req.getParameter("textMessage");

        if (from != null && to != null && textMessage != null){
          System.out.println("there is a message");
          System.out.println("from = "+from);
          System.out.println("to = "+to);
          System.out.println("textMessage = "+textMessage);
        }else{
          System.out.println("there is no message");
          System.out.println("from = "+from);
          System.out.println("to = "+to);
          System.out.println("textMessage = "+textMessage);
        }

        String sql = "INSERT INTO Messages (User1, User2, Message) VALUES (";
        sql +=  "'" + from + "'";
        sql +=  ", '" + to + "'";
        sql +=  ", '" + textMessage + "')";
        System.out.println("Insert sql: " + sql);

        try{
            Statement stmt=conn.createStatement();
            int num = stmt.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Exception in creation: " + e);
        }
    }
}