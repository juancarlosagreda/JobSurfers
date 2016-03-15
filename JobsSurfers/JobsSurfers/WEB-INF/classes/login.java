import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class login extends HttpServlet {
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
	
    public void destroy () {
        super.destroy();
        System.out.print("Closing connection ...");
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch(SQLException ex){
            System.out.println("Problem closing connection");
            System.out.println(ex.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
		String tipo = req.getParameter("tipo");
		String userlogin = req.getParameter("userlogin");
		String password = req.getParameter("password");
		
		String html = "";
		String sql = "";
		Statement statementSelect;
		ResultSet result;
		String pass;
		boolean correct = false;
		
		if (userlogin == "" || userlogin == null){
			html += "Sorry, you haven't enetered any user.";
		}else{
			if (tipo=="user"){
				sql = "SELECT Username, Password FROM UserReg WHERE Username ='"+ userlogin +"'";
				try{
					statementSelect=connection.createStatement();
					result = statementSelect.executeQuery(sql);
					while(result.next()) {
						pass = result.getString("Password");
						if (pass==password){
							html += "Welcome back user "+userlogin+".";
							correct = true;
						}
					}
					result.close();
					statementSelect.close();
					if (correct == false){
						html += "Dear user "+userlogin+", your password is incorrect.";
					}
				}catch(SQLException e) {
					e.printStackTrace();
					System.out.println("Exception in login: " + e);
				}
			}else{
				sql = "SELECT CompanyName, Password FROM CompanyReg WHERE CompanyName ='"+ userlogin +"'";
				try{
					statementSelect=connection.createStatement();
					result = statementSelect.executeQuery(sql);
					while(result.next()) {
						pass = result.getString("Password");
						if (pass==password){
							html += "Welcome back company "+userlogin+".";
							correct = true;
						}
					}
					result.close();
					statementSelect.close();
					if (correct == false){
						html += "Dear company "+userlogin+", your password is incorrect.";
					}
				}catch(SQLException e) {
					e.printStackTrace();
					System.out.println("Exception in login: " + e);
				}
			}
		}
		ResponseManager.output(res, html);
	}
}