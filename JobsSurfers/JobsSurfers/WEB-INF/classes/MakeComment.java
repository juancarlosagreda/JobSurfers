import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MakeComment extends HttpServlet {
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
	//Hasta aquí que es el 'aceso' a la BD
	 public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession(true);
		session.setAttribute("UserCommented", "Maialen");
		
		
		String UserCommented = (String)session.getAttribute("UserCommented");//A traves de esto finjo coger al usuario que esta en sesion
        String User = req.getParameter("User");//Simulo quien recibe el mensaje
		String post=req.getParameter("post");
		String comment = req.getParameter("comment");

        String sql = "INSERT INTO Comments (User, Post, UserCommented, Comment) VALUES (";
		sql +=  "'" + User + "'";
        sql +=  ", '" + post + "'"; 
		sql +=  ", '" + UserCommented + "'";
		sql += ", '" + comment + "')";
        System.out.println("Insert sql2: " + sql);
		
		try {
			System.out.println("Statement: " + (connection != null));
			Statement statementInsert=connection.createStatement();
			int num = statementInsert.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
		ResponseManager.AjaxOutput(res, UserCommented+": " +comment);
	 }
}
