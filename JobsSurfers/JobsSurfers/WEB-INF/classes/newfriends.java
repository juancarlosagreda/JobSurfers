import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class newfriends extends HttpServlet {
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
  	response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session=request.getSession(true);
    //Leer los valores
    String username=request.getParameter("username");
	String userreq=request.getParameter("userreq");
    //Registrar en la base de datos. No hace falta comprobar si el usuario ya es tu amigo, 
    // porque la lista de usuarios mostrada será la de amigos que no tienes.
    String sql = "INSERT INTO FriendRequests (UserSends, UserReceives) VALUES (";
    sql += "'" + username + "'";
	sql +=  ", '" + userreq + "')";
    System.out.println("Insert sql: " + sql);
    try{
      Statement statementInsert=connection.createStatement();
      int num = statementInsert.executeUpdate(sql);
    } catch(SQLException e) {
      e.printStackTrace();
      System.out.println("Exception in register: " + e);
    }
    out.println("<!DOCTYPE html>"); 
    out.println("<html lang=\"en\">");
    out.println("<head><title>Add friend</title>");
    out.println("<meta charset=\"utf-8\">");
    out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
    out.println("<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css \">");
    out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js \"></script>");
    out.println("<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js \"></script>");
    out.println("</head>");
    out.println("<BODY>");
    out.println("<center><h1>Congratulations " + username + "! Now " + userreq + " is your new friend!</h1>");
    out.println("<a href=\"AddFriend.html\" class=\"jordi\"> Go Back to add New Friend </a><br>");
    out.println("<a href=\"home.html\" class=\"jordi\">Home </a></center>");
    out.println("</BODY>");
    out.println("</HTML>");
    out.flush();
    out.close();
  } 
}