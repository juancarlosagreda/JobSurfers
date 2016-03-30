import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class showConnections extends HttpServlet {
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
        html += "<div class='content' id='turuconnections'>";

        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");

        String amigo = "";
        
        if (session != null){
            String select = "SELECT Friend1 As friend FROM Friends WHERE Friend2 = '"+user+"' UNION SELECT Friend2 As friend FROM Friends WHERE Friend1 = '"+user+"';";
            html += "<h2>My Connections</h2>";
            html += "<div id='userApply'><h4>User: " + user + "</h4></div>";
            try{
                Statement stmt = conn.createStatement();
                ResultSet myFriends = stmt.executeQuery(select);

                html += MessageModal.inbox(res, user);   

                html += "<table border='1' class='table table-hover table-condensed'>";          
                while(myFriends.next()){
                    amigo = myFriends.getString("friend");
                    html += "<tr>";
                    html += "<td>" + amigo + "<button type='button' class='turusend btn' onclick='openWM(`"+amigo+"`)'>Send message <span class='glyphicon glyphicon-envelope' aria-hidden='true'></span></button></td>";
                    html += "</tr>";
                }
                html += "</table>";
                
                myFriends.close();
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


