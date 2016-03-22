import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class DeleteUser extends HttpServlet {
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
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        session.setAttribute("Username", "Maialen");//Simulo 
		String Username = (String)session.getAttribute("Username")
		
		String sql = "DELETE FROM  UserReg WHERE Username=";
			sql +=  "'" + Username + "'" ; //No me coge lo de la última comilla

		try {
			System.out.println("Statement: " + (connection != null));//Creo que esto era lo que usabamos para ver si conectaba con la BD
			Statement statementInsert=connection.createStatement();
			int num = statementInsert.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
		//si ese usuario no existe en la BD
		//¿Puedo meter bootstap así sinmas?
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
