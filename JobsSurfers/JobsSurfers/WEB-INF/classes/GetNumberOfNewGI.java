import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class GetNumberOfNewGI extends HttpServlet {
    Connection connection;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:JobSurfers";
            connection=DriverManager.getConnection(url); 
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");
        String answer="";

        String sql = "SELECT Count(*) AS NumberInv FROM GroupInvitees WHERE InvitedPerson = '";
        sql += user+"' AND GroupID NOT IN (SELECT GroupID FROM GroupMembers WHERE Members = '"+user+"')";
        System.out.println("Insert sql: " + sql);
        
        try {
            Statement statementInsert=connection.createStatement();
            ResultSet rs = statementInsert.executeQuery(sql);

            rs.next();
            answer += rs.getString("NumberInv");

        } catch(SQLException e){
            e.printStackTrace();
        }
        ResponseManager.AjaxOutput(res, answer);
        
    }
}