import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class ReportUser extends HttpServlet {
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
  public void destroy() {
    System.out.println("Error loading...");
  } 
  public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session=request.getSession(true);
    session.setAttribute("user", "Jordi");
    // these lines simulate that the user Jordi is connected
    String user = (String)session.getAttribute("user");
    //Read the value
    String report=request.getParameter("report");
    String reportedUser = request.getParameter("reportedUser");
    //Register in database
    String sql = "INSERT INTO Report (Username, Report) VALUES (";
    sql += "'" + reportedUser + "'";
    sql += ", '" + report + "')";
    System.out.println("Insert sql: " + sql);
    try{
      Statement statementInsert=connection.createStatement();
      int num = statementInsert.executeUpdate(sql);
    } catch(SQLException e) {
      e.printStackTrace();
      System.out.println("Exception in register: " + e);
    }
    ResponseManager.AjaxOutput(response, reportedUser);
  }
}