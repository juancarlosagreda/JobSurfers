import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class GetNumberOfNewFR extends HttpServlet {
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

        String sql = "SELECT Count(*) AS numberFR FROM FriendRequests WHERE UserReceives = '";
        sql += user + "' AND UserSends NOT IN (SELECT Friend1 FROM Friends WHERE Friend2 = '";
        sql += user +"')";
        System.out.println("Insert sql: " + sql);
        
        try {
            Statement statementInsert=connection.createStatement();
            ResultSet rs = statementInsert.executeQuery(sql);

            rs.next();
            answer += rs.getString("numberFR");

        } catch(SQLException e){
            e.printStackTrace();
        }
        ResponseManager.AjaxOutput(res, answer);
        
    }
}