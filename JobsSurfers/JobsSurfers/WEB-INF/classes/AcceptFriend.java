import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class AcceptFriend extends HttpServlet {
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

     public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        String userReceives = (String)session.getAttribute("user");
        String userSends = req.getParameter("sender");

        String sql = "INSERT INTO Friends (Friend1, Friend2) VALUES (";
        sql +=  "'" + userSends + "'";
        sql +=  ", '" + userReceives + "')"; 
        System.out.println("Insert sql: " + sql);
        
        try {
            Statement statementInsert=connection.createStatement();
            int num = statementInsert.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
        ResponseManager.AjaxOutput(res, userSends);
     }
}
