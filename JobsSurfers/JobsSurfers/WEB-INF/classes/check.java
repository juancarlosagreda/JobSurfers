import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class check extends HttpServlet {
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
    String username=request.getParameter("username-input");
  	String firstname=request.getParameter("firstname-input");
  	String lastname=request.getParameter("lastname-input");
    String email=request.getParameter("email");
    String gender=request.getParameter("gender");
    String mobilenumber=request.getParameter("mobilenumber");
    String birthday=request.getParameter("birthday");
    //Registrar en la base de datos
    String sql = "INSERT INTO Profile (Username, name1, surname1, [e-mail], BirthDate, Gender, PhoneNumber) VALUES (";
    sql += "'" + username + "'";
    sql +=  ", '" + firstname + "'";
    sql +=  ", '" + lastname + "'";
    sql +=  ", '" + email + "'";
    sql += ", #" + birthday + "#";
    sql +=  ", '" + gender + "'";
    sql +=  ", '" + mobilenumber + "')";
    System.out.println("Insert sql: " + sql);
    try{
      Statement statementInsert=connection.createStatement();
      int num = statementInsert.executeUpdate(sql);
    } catch(SQLException e) {
      e.printStackTrace();
      System.out.println("Exception in register: " + e);
    }
    //Escritura de HTML.
    out.println("<HTML>"); 
  	out.println("<HEAD><TITLE>Check firstname, lastname and email</TITLE></HEAD>");  
    //Asignar a la sesion el usuario y la clave.
    session.setAttribute("firstname",firstname);
    session.setAttribute("lastname",lastname);
    // Continuamos la escritura HTML.
    out.println("<A HREF=\"Createprofile.html\"><INPUT STYLE=\"border: 0; background: white;\" TYPE=\"Button\" VALUE=\"< Go Back\"></A>");
		out.println("<center><IMG SRC=\"logo.jpg\" style=\"height:20%;\"><BR>");
    out.println("<H1>WELCOME : " + firstname + " " + lastname + " to Jobs Surfers</H1>");
		out.println("<TABLE>");
    out.println("<TR>");
    out.println("<TD><A HREF=\"home.html\"><INPUT TYPE=\"Button\" VALUE=\"Continue\"></A></TD></TR>");
		out.println("<TR><TD><FORM ACTION=\"logout\" method=\"GET\">");	
		out.println("<INPUT TYPE=\"submit\" VALUE=\"Logout\">");
		out.println("</FORM></TD>");
    out.println("</TR></TABLE></center>");
  	out.println("</BODY>");
    out.println("</HTML>");
    out.flush();
    out.close();
  } 
}