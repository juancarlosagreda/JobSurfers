import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ButtonViewProfile extends HttpServlet {
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
	
    public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
		HttpSession session=req.getSession(true);
		session.setAttribute("Username", "Asier");
		
		String html="";
        String Username = (String)session.getAttribute("Username");
        String sqlSelect = "SELECT  Profile.Username, picture, name1, name2, surname1, surname2, [email], BirthDate, Gender, PhoneNumber, MobilePhone  FROM UserReg, Profile WHERE UserReg.Username = UserReg.Username AND Profile.Username = '" +
            Username + "'";

		System.out.println("Select sql: " + sqlSelect);
		
		try{
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlSelect);
			
			while(result.next()){
				String name1 = result.getString("name1");
				String name2 = result.getString("name2");
				String surname1 =result.getString("surname1");
				String surname2 =result.getString("surname2");
				String email =result.getString("email");
				String birthdate = result.getString("BirthDate");
				String gender = result.getString("Gender");
				String phone = result.getString("PhoneNumber");
				String mobile = result.getString("MobilePhone");
				
				html += "<div class=\"img\"><img src=\"picture.jpg\" alt=\"picture\" width=\"200\" height=\"200\"></div>";
				html += "<BR><BR><BR><BR><BR><BR><BR><BR><BR><p><h2><b> "+surname1+" "+surname2+", <span>"+name1+" "+name2 +"</span></b></h2></p>"; 
				html += "<p><b>BirthDate: "+birthdate+"</b></p>";
				html += "<p><b>E-mail: "+email+"</b></p>";
				html += "<p><b>Phone number: "+phone+"</b></p>";
				html += "<p><b>Mobile phone: "+mobile+"</b></p>";
				html += "<div class=\"experience\"><h4 align=\"center\">Work experience</h4><BR><h4 align=\"center\">Education</h4><BR><h4 align=\"center\">Description</h4><BR><h4 align=\"center\">Languages</h4><BR> <h4 align=\"center\">Aptitudes</h4></div></div>";
				ResponseManager.output(resp, html);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
            System.out.println("Exception in register: " + e);
			
		}
	}
}