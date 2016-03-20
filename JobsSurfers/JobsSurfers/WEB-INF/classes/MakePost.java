import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MakePost extends HttpServlet {
    Connection connection;
        public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:JobsSurfers";
            connection=DriverManager.getConnection(url);
            System.out.println("init" + (connection != null));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

     public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");
        String post = req.getParameter("post");

        String sql = "INSERT INTO Posts (Username, Post) VALUES (";
        sql +=  "'" + user + "'";
        sql +=  ", '" + post + "')"; 
        System.out.println("Insert sql: " + sql);
        
        try {
            System.out.println("Statement: " + (connection != null));
            Statement statementInsert=connection.createStatement();
            int num = statementInsert.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
        ResponseManager.AjaxOutput(res, user+": "+post);
     }
}
