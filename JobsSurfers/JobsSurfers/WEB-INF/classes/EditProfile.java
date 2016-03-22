import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class EditProfile extends HttpServlet {
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
}
	//Hasta aquÃ­ que es el 'aceso' a la BD
	 public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession(true);
		session.setAttribute("Username", "Maialen");
		
		
		String Username = (String)session.getAttribute("Username");//A traves de esto finjo coger al usuario que esta en sesion
        String name1 = req.getParameter("name1");//Simulo quien recibe el mensaje
		String name2=req.getParameter("name2");
		String surname1 = req.getParameter("surname1");
		String surname2 = req.getParameter("surname2");
		String email = req.getParameter("email");
		String BirthDate = req.getParameter("BirthDate");
		String Gender = req.getParameter("Gender");
		String PhoneNumber = req.getParameter("PhoneNumber");
		String MobilePhone = req.getParameter("MobilePhone");

        String sql = "UPDATE Profile SET name 1 ="; 
		sql +=  "'" + name1 + "'";
        sql +=  "name2='" + name2 + "'"; 
		sql +=  "surname1= '" + surname1 + "'";
		sql +=  "surname2= '" + surname2 + "'";
		sql +=  "email= '" + email + "'";
		sql +=  "BirthDate='" + BirthDate + "'";
		sql +=  "Gender='" + Gender + "'";
		sql +=  "PhoneNumber='" + PhoneNumber + "'";
		sql +=  "MobilePhone='" + MobilePhone + "'";
		sql += "WHERE Username=" + Username + "'";
        System.out.println("Insert sql2: " + sql);
		
		try {
			System.out.println("Statement: " + (connection != null));
			Statement statementInsert=connection.createStatement();
			int num = statementInsert.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println(<body>);
		System.out.println(<div class="bs-example">);
		System.out.println(<div class="alert alert-danger fade in">);
		System.out.println(<a href="#" class="close" data-dismiss="alert">&times;</a>);
		System.out.println(<strong>Success!</strong>The user has been successfully deleted. >);
		System.out.println(</div>);
		System.out.println(</div>);
		System.out.println(</body>);
	 }
}

