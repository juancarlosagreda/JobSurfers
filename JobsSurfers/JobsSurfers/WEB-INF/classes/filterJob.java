import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class filterJob extends HttpServlet {
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
        html += "<h2>List of jobs</h2>";
        
        boolean sessionBool = false;
        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");
        if (session != null) {
            sessionBool = true;
        }
        
        if (sessionBool) {
            
            if (user != null) {
                html += "<div id='userApply'><h4>User: " + user + "</h4></div>";
            }
            
        }

        try {
            Statement stmt=conn.createStatement();
            if (req.getParameter("comboSector") == null && req.getParameter("comboCompany") == null && req.getParameter("comboPosition") == null){
                html += "No filter has been applied";
            }else{
                String str;
                String x;
                if (req.getParameter("comboSector") != null){
                    x = req.getParameter("comboSector");
                    str = "sector";
                } 
                else if(req.getParameter("comboCompany") != null){
                    x = req.getParameter("comboCompany");
                    str = "CompanyReg.CompanyName";
                }
                else{
                    x = req.getParameter("comboPosition");
                    str = "position";
                }

                html += "<div id='filter'>";
                html += "<form method=GET action='apply'>";
                ResultSet filter = stmt.executeQuery("SELECT jobslist.code, jobslist.sector, CompanyReg.CompanyName, jobslist.position FROM CompanyReg, jobslist WHERE CompanyReg.CompanyID = jobslist.company AND " + str + " ='" + x + "'" );
                html += "<table border='1' class='table table-hover table-condensed'>";          
                html += "<thead><tr><th></th><th>Job ID</th><th>Sector</th><th>Company</th><th>Position</th></tr></thead><tbody>";
                while(filter.next()){
                    html += "<tr>";
                    String codeStr = filter.getString("code");
                    if (sessionBool) {
                        html += "<td><input type='radio' name='job' value='" + codeStr + "'></td>";
                    }
                    html += "<td>" + codeStr + "</td>";
                    html += "<td>" + filter.getString("sector") + "</td>";
                    html += "<td>" + filter.getString("CompanyName") + "</td>";
                    html += "<td>" + filter.getString("position") + "</td>";
                    html += "</tr>";
                }
                html += "</tbody></table>";
                html += "<input type=submit class='applyButton' value='Apply for the job' />";
                html += "</form>";
                html += "</div>";
                filter.close();
            }
            if (sessionBool) {
                ResponseManager.outputUWOP(res, user, html);
            }else{
                ResponseManager.output(res, html);
            }
            stmt.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
 
}


