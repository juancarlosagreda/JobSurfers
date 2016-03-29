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

                html += "<table border='1' class='table table-hover table-condensed'>";          
                while(myFriends.next()){
                    amigo = myFriends.getString("friend");
                    html += "<tr>";
                    html += "<td>" + amigo + "<button type='button' class='turusend btn' onclick='openWriteModal(`"+amigo+"`)'>Send message <span class='glyphicon glyphicon-envelope' aria-hidden='true'></span></button></td>";
                    html += "</tr>";
                }
                html += "</table>";

                    //WriteModal
                    html += "<div class='modal fade' id='WriteModal' role='dialog'><div class='modal-dialog'>";

                    //Modal content
                    html += "<div class='modal-content'><div class='modal-header'><button type='button' class='close' data-dismiss='modal'>&times;</button><h4 class='modal-title'>Send a message to <span id='sperson'></span></h4></div>";
                    html += "<div class='modal-body'><form role='form' id='messageForm' action='showConnections'><div class='form-group'><label for='from'>From:</label><input type='text' class='form-control' id='from' value='"+user+"' readonly=''></div><div class='form-group'><label for='to'>To:</label><input type='text' class='form-control' id='to' readonly=''></div><textarea class='form-control' rows='5' id='textMessage' placeholder='Write your message here...' ></textarea></form></div>";
                    html += "<div class='modal-footer'><button type='button' class='btn btn-default' data-dismiss='modal'><a href='#'>Close</a></button><button type='button' id='bsend' class='btn btn-primary' onclick='createMessage()'>Send message</button></div></div>";

                    //End Modal
                    html += "</div></div>";

                    //SentModal
                    html += "<div class='modal fade' id='SentModal' role='dialog'><div class='modal-dialog'>";

                    //Modal content
                    html += "<div class='modal-content'><div class='modal-header'><button type='button' class='close' data-dismiss='modal'>&times;</button><h4 class='modal-title'>Message sent</h4></div>";
                    html += "<div class='modal-body'><p>Your message to <span id='rperson'></span> has been sent successfully.</p></div>";
                    html += "<div class='modal-footer'><button type='button' class='btn btn-default' data-dismiss='modal'><a href='#'>Close</a></button></div></div>";

                    //End Modal
                    html += "</div></div>";

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


