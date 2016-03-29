import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class myApplications extends HttpServlet {
	Connection conn;
  public void init(ServletConfig config) throws ServletException {
      super.init(config);
      try {
          Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
          conn=DriverManager.getConnection("jdbc:odbc:JobSurfers"); 
      } catch(Exception e) {
          e.printStackTrace();
      }
  }
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
 		res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        String html="";

    	//This is the content
        html += "<div class='content' id='turu'>";

        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");
        
        if (session != null){
            String select = "SELECT Applications.code, jobslist.sector, CompanyReg.CompanyName, jobslist.position, ApplicationStatus.status FROM (CompanyReg INNER JOIN jobslist ON CompanyReg.CompanyID = jobslist.company) INNER JOIN (ApplicationStatus INNER JOIN Applications ON ApplicationStatus.statusID = Applications.status) ON jobslist.code = Applications.code WHERE Username = '"+ user +"';";
            html += "<h2>My Job Applications</h2>";
            html += "<div id='userApply'><h4>User: " + user + "</h4></div>";
            try{
                Statement stmt = conn.createStatement();
                ResultSet myJobs = stmt.executeQuery(select);
                html += "<table border='1' class='table table-hover table-condensed'>";          
                html += "<thead><tr><th>Job ID</th><th>Sector</th><th>Company</th><th>Position</th><th>Status</th></tr></thead><tbody>";
                while(myJobs.next()){
                    html += "<tr>";
                    html += "<td>" + myJobs.getString("code") + "</td>";
                    html += "<td>" + myJobs.getString("sector") + "</td>";
                    html += "<td>" + myJobs.getString("CompanyName") + "</td>";
                    html += "<td>" + myJobs.getString("position") + "</td>";
                    html += "<td>" + myJobs.getString("status") + "</td>";
                    html += "</tr>";
                }
                html += "</tbody></table>";
                myJobs.close();
                stmt.close();
            }catch(SQLException e) {
                e.printStackTrace();
                System.out.println("Resulset: " + select + " Exception: " + e);
            }
        }
        html += "</div></body></html>";
        ResponseManager.outputUWOP(res, user, html);
        toClient.close();
    }
}


