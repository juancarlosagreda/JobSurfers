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
        /*html += "<!DOCTYPE HTML>");
        html += "<html>");
        html += "<head><title>Jobs Surfers | Jobs Searcher</title><meta name='viewport' content='width=device-width, initial-scale=1'><meta charset='utf-8'><link rel='stylesheet' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css'><script src='https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js'></script><script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js'></script><link href='sticky-footer-navbar.css' rel='stylesheet' /><script type='text/javascript' src='scripts.js'></script><link rel='stylesheet' type='text/css' href='styles.css'><style>.navbar > .container .navbar-brand, .navbar > .container-fluid .navbar-brand{margin-left: 0px;}#logo{display: block; height: 50px; margin-top: -15px; margin-left: -15px;}.back{position: relative;display: inline;padding: 10px 15px;line-height: 20px;padding-top: 15px;padding-bottom: 15px;color: #777;list-style: none;text-decoration: none;float: left;height: 50px;padding: 15px 15px;font-size: 18px;line-height: 20px;}.back:hover, .back:focus{color: #333; text-decoration: none;}</style><script>function goBack(){window.history.back();}</script></head>");
        html += "<body>");*/

        //This is the Bootstrap Navigation Bar
        /*File file = new File("navbar.txt");
        Scanner scanner = new Scanner(file);
        String text = "";
        int i=0;
        while (scanner.hasNext()) {
            text += scanner.nextLine();
            i++;
        }
        html += text);*/

    	//This is the content
        html += "<div class='content'>";
        html += "<h2>List of jobs</h2>";
        
        boolean sessionBool = false;
        HttpSession session = req.getSession(false);
        if (session != null) {
            sessionBool = true;
        }
        
        if (sessionBool) {
            String name = (String)session.getAttribute("name");
            if (name != null) {
                html += "<div id='userApply'><h4>User: " + name + "</h4></div>";
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
                    str = "company";
                }
                else{
                    x = req.getParameter("comboPosition");
                    str = "position";
                }

                html += "<div id='filter'>";
                html += "<form method=GET action='apply'>";
                ResultSet filter = stmt.executeQuery("SELECT code, sector, company, position FROM jobslist WHERE " + str + " ='" + x + "'" );
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
                    html += "<td>" + filter.getString("company") + "</td>";
                    html += "<td>" + filter.getString("position") + "</td>";
                    html += "</tr>";
                }
                html += "</tbody></table>";
                html += "<input type=submit class='applyButton' value='Apply for the job' />";
                html += "</form>";
                html += "</div>";
                filter.close();
            }
            ResponseManager.output(res, html);
            stmt.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
 
}


