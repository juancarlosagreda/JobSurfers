import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CreateGroup extends HttpServlet {
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
		
		HttpSession session = req.getSession(true); // on the complete app the boolean changes to false
		session.setAttribute("user", "JuanFran"); 
		// the previous two lines simulate that you're connected as the user "JuanFran"
		
		// requesting the parameters from creation form
        String groupname = req.getParameter("GroupName");
        String creator = (String)session.getAttribute("user");
        String publicGroup = req.getParameter("public");

        // Constructing Query
        String sql = "INSERT INTO Groups (creator, GroupName, Public) VALUES (";
        sql +=  "'" + creator + "'";
        sql +=  ", '" + groupname + "'";
        sql +=  ", " + publicGroup + ")";
        System.out.println("Insert sql: " + sql);

        try{
			Statement statementInsert=connection.createStatement();
			int num = statementInsert.executeUpdate(sql);
		
			ResponseManager.AjaxOutput(res, groupname);
			
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Exception in creation: " + e);
        }
    }
}