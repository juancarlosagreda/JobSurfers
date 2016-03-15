import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class register extends HttpServlet {
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

        
		String username = req.getParameter("username");
		String companyname = req.getParameter("companyname");
        String password = req.getParameter("password");
		PrintWriter regfail = res.getWriter();
		
		boolean user = false;
		if (username != null){
			user = true;
		}
		System.out.println("+user+");
		
		String select;
		String sql;
		if (user == true){
			System.out.println("username");
			select = "SELECT username, password FROM UserReg WHERE username = '" +username + "'";
			System.out.println("Select sql: " + select);
			sql = "INSERT INTO UserReg (username, password) VALUES (";
			sql +=  "'" + username + "'";
			sql +=  ", '" + password + "')";
			System.out.println("Insert sql: " + sql);
		}
		else {
			System.out.println("companyname");
			select = "SELECT companyname, password From CompanyReg WHERE companyname = '" +companyname + "'";
			System.out.println("Select sql: " + select);
			sql = "INSERT INTO CompanyReg (companyname, password) VALUES (";
			sql +=  "'" + companyname + "'";
			sql +=  ", '" + password + "')";
			System.out.println("Insert sql: " + sql);
		}
     

        try{
            Statement statementSelect=connection.createStatement();
			ResultSet result = statementSelect.executeQuery(select);
			
            boolean exist = false;
            while(result.next()) {
                exist = true;
				regfail.println("<!DOCTYPE HTML>");
				regfail.println("<html>");
				regfail.println("<head><title>Registration</title></head>");
				regfail.println("<body>");
				if (user == true) {
					regfail.println("<h2>Sorry, the username "+username+" already exists</h2>");
				}else{
					regfail.println("<h2>Sorry, the companyname "+companyname+" already exists</h2>");
				}
				regfail.println("<a href=\"html.html\">try again</a>");
				regfail.println("</body>");
				regfail.println("</html>");
				regfail.close();
            }
            if (!exist) {
                Statement statementInsert=connection.createStatement();
                int num = statementInsert.executeUpdate(sql);
                String congratulation = "";
				congratulation += "<h2> Congratulations you have registered with the username: " + username + "</h2>";
				ResponseManager.output(res, congratulation );
				System.out.println("insert");
            }

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Exception in register: " + e);
        }
		
    }
}