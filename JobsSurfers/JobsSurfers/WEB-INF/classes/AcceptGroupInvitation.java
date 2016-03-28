import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class AcceptGroupInvitation extends HttpServlet {
    Connection connection;
        public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:JobSurfers";
            connection=DriverManager.getConnection(url);
            System.out.println("init" + (connection != null));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

     public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        String invitedPerson = (String)session.getAttribute("user");
        String groupid = req.getParameter("groupID");
        String groupName = req.getParameter("groupName");

        String sql = "INSERT INTO GroupMembers (GroupID, Members) VALUES (";
        sql +=  "'" + groupid + "'";
        sql +=  ", '" + invitedPerson + "')";
        System.out.println("Insert sql: " + sql);
        
        try {
            Statement statementInsert=connection.createStatement();
            int num = statementInsert.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
        ResponseManager.AjaxOutput(res, groupName);
     }
}
