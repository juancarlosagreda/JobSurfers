import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class showMessages extends HttpServlet {
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
        String emisor = "";
        String message = "";
        String sentdate = "";
        int badge = 0;

    	//This is the content
        html += "<div class='content' id='turumessages'>";

        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");
        
        if (session != null){
            String selectFriend = "SELECT Friend1 As friend FROM Friends WHERE Friend2 = '"+user+"' UNION SELECT Friend2 As friend FROM Friends WHERE Friend1 = '"+user+"';";
            String selectMess = "SELECT User1 As user FROM Messages WHERE User2 = '"+user+"' UNION SELECT User2 As user FROM Messages WHERE User1 = '"+user+"';";
            
            html += "<h2>My Message Box</h2>";
            html += "<div id='userApply'><h4>User: " + user + "</h4></div>";
            html += MessageModal.inbox(res, user); 
             try{
                Statement stmt = conn.createStatement();

                //Select Friends
                ResultSet numF = stmt.executeQuery("SELECT Count(*) FROM (SELECT Friend1 As friend FROM Friends WHERE Friend2 = '"+user+"' UNION SELECT Friend2 As friend FROM Friends WHERE Friend1 = '"+user+"');");
                numF.next();
                int lFriend = numF.getInt(1);
                numF.close();

                ResultSet rFriends = stmt.executeQuery(selectFriend);
         
                int i = 0;
                String[] arrayF = new String[lFriend];
                while(rFriends.next()){
                    arrayF[i] = rFriends.getString("friend");
                    i++;
                }
                rFriends.close();

                //Select friends with messages
                ResultSet numM = stmt.executeQuery("SELECT Count(*) FROM (SELECT User1 As user FROM Messages WHERE User2 = '"+user+"' UNION SELECT User2 As user FROM Messages WHERE User1 = '"+user+"');");
                numM.next();
                int lMess = numM.getInt(1);
                numM.close();

                ResultSet rMess = stmt.executeQuery(selectMess);
         
                i = 0;
                String[] arrayM = new String[lMess];
                while(rMess.next()){
                    arrayM[i] = rMess.getString("user");
                    i++;
                }
                rMess.close();

                //New message
                html += "<div class='panel panel-default'><div class='panel-body'><form role='form' class='form-inline' id='messageForm' accept-charset=utf-8><label for='friendlist'>Start a new conversation with </label><select class='form-control' id='friendlist'>";
                for(i=0;i<lFriend;i++){
                  html += "<option value='"+arrayF[i]+"'>"+arrayF[i]+"</option>";
                }
                html += "</select><button onclick='openNewWM()' id='newMbtn' class='btn btn-default' type='button'>Write</button></form></div></div>";
           
                //Conversations panel
                html += "<div class='list-group'>";
                for(i=0;i<lMess;i++){
                    ResultSet numConv = stmt.executeQuery("SELECT Count(*) FROM Messages WHERE User1 IN ('"+arrayM[i]+"', '"+user+"') AND User2 IN ('"+arrayM[i]+"', '"+user+"') ;");
                    numConv.next();
                    badge = numConv.getInt(1);
                    html += "<button class='list-group-item' onclick='showConversation(`"+arrayM[i]+"`)'><h5>Conversation with "+arrayM[i]+"<span class='badge'>"+badge+"</span></h5>";
                    html += "</button>";
                    numConv.close();
                }
                html += "</div>";          
                html += "</div><div class='content' id='turuconversation'>";

                //Conversations detailed
                html += "<ul class='list-group'>";
                for(i=0;i<lMess;i++){
                    html += "<li id='conv"+arrayM[i]+"' class='list-group-item message-list'><h5>Conversation with "+arrayM[i]+"</h5>";
                    ResultSet rConv = stmt.executeQuery("SELECT User1 As Emisor, Message, SentDate FROM Messages WHERE User1 IN ('"+arrayM[i]+"', '"+user+"') AND User2 IN ('"+arrayM[i]+"', '"+user+"') ORDER BY SentDate DESC;");
                    while(rConv.next()){
                      emisor = rConv.getString("Emisor");
                      message = rConv.getString("Message");
                      sentdate = rConv.getString("SentDate");
                      html += "<div><div><span class='emisor'>"+emisor+"</span><span class='sentdate'>"+sentdate+"</span></div><span class='message'>"+message+"</span><hr></div>";
                    }
                    html += "<div><button type='button' id='otherturusend' class='turusend btn' onclick='openWM(`"+arrayM[i]+"`)'>New message <span class='glyphicon glyphicon-pencil' aria-hidden='true'></span></button></div></li>";
                    rConv.close();
                }
                html += "</ul>";
                
                stmt.close();
            }catch(SQLException e) {
                e.printStackTrace();
                System.out.println("Resulset: " + selectFriend + " Exception: " + e);
            }
        }
        html += "</div></body></html>";
        ResponseManager.outputUWOP(res, user, html);
        toClient.close();
    }
}


