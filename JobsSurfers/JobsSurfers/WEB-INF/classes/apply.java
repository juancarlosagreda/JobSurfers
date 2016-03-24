import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class apply extends HttpServlet {
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
        
        //<!-- Modal -->
        html += "<div class='modal fade' id='myModal' role='dialog'>";
        html += "<div class='modal-dialog'>";

        HttpSession session = req.getSession(false);

        if (req.getParameter("job") != null){
            if (session != null) {
                String name = (String)session.getAttribute("name");
                if (name != null) {
                    String cJob = req.getParameter("job");
                    String sql = "INSERT INTO Applications (Username, code) VALUES (";            
                    sql +=  "'" + name + "'";
                    sql +=  ", '" + cJob + "')";
                    
                    //<!-- Modal content-->
                    html += "<div class='modal-content'>";
                    html += "<div class='modal-header'>";
                    html += "<button type='button' class='close' data-dismiss='modal'>&times;</button>";
                    html += "<h4 class='modal-title'>Application succeed</h4>";
                    html += "</div>";
                    html += "<div class='modal-body'>";
                    html += "<p>The user " + name + " has applied for the job offer " + cJob + ".</p>";
                    html += "</div>";
                    html += "<div class='modal-footer'>";
                    html += "<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>";
                    html += "</div>";
                    html += "</div>";

                    try{
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate(sql);
                        stmt.close();
                    } catch(SQLException e) {
                        e.printStackTrace();
                        System.out.println("Resulset: " + sql + " Exception: " + e);
                    }
                }else{
                    //<!-- Modal content-->
                    html += "<div class='modal-content'>";
                    html += "<div class='modal-header'>";
                    html += "<button type='button' class='close' data-dismiss='modal'>&times;</button>";
                    html += "<h4 class='modal-title'>Application failed</h4>";
                    html += "</div>";
                    html += "<div class='modal-body'>";
                    html += "<p>You need to start a session to apply for a job.</p>";
                    html += "</div>";
                    html += "<div class='modal-footer'>";
                    html += "<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>";
                    html += "</div>";
                    html += "</div>";
                }
            }
        }else{
            //<!-- Modal content-->
            html += "<div class='modal-content'>";
            html += "<div class='modal-header'>";
            html += "<button type='button' class='close' data-dismiss='modal'>&times;</button>";
            html += "<h4 class='modal-title'>Application failed</h4>";
            html += "</div>";
            html += "<div class='modal-body'>";
            html += "<p>The job application hasn't been saved correctly.</p>";
            html += "</div>";
            html += "<div class='modal-footer'>";
            html += "<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>";
            html += "</div>";
            html += "</div>";
        }
        html += "</div>";
        html += "</div>";

        if (req.getParameter("job") != null && session != null){
            String name = (String)session.getAttribute("name");
            String select = "SELECT code, sector, company, position FROM jobslist WHERE code IN (SELECT code FROM Applications WHERE Username ='"+ name +"');";
            html += "<h2>Your Job Applications</h2>";
            html += "<div id='userApply'><h4>User: " + name + "</h4></div>";
            try{
                Statement stmt2 = conn.createStatement();
                ResultSet myJobs = stmt2.executeQuery(select);
                html += "<table border='1' class='table table-hover table-condensed'>";          
                html += "<thead><tr><th>Job ID</th><th>Sector</th><th>Company</th><th>Position</th></tr></thead><tbody>";
                while(myJobs.next()){
                    html += "<tr>";
                    html += "<td>" + myJobs.getString("code") + "</td>";
                    html += "<td>" + myJobs.getString("sector") + "</td>";
                    html += "<td>" + myJobs.getString("company") + "</td>";
                    html += "<td>" + myJobs.getString("position") + "</td>";
                    html += "</tr>";
                }
                html += "</tbody></table>";
                myJobs.close();
                stmt2.close();
            }catch(SQLException e) {
                e.printStackTrace();
                System.out.println("Resulset: " + select + " Exception: " + e);
            }
        }
        html += "</div></body></html>";
        ResponseManager.output(res, html);
        toClient.close();
    }
}


