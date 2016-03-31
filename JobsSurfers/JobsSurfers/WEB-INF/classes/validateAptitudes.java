import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class validateAptitudes extends HttpServlet {
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
        String aptitude = "";
        int badge = 0;

    	//This is the content
        html += "<div class='content' id='turuconnections'>";

        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");
        
        if (session != null){
            String selectFriend = "SELECT Friend1 As friend FROM Friends WHERE Friend2 = '"+user+"' AND Friend1 IN (SELECT Username FROM Profile) UNION SELECT Friend2 As friend FROM Friends WHERE Friend1 = '"+user+"' AND Friend2 IN (SELECT Username FROM Profile);";
            String selectValidations = "SELECT User, Aptitude FROM Validations WHERE Validator = '"+user+"';";
            
            html += "<h2>Validate your friends' aptitudes</h2>";
            html += "<div id='userApply'><h4>User: " + user + "</h4></div>";
             try{
                Statement stmt = conn.createStatement();

                //find friends with profile
                ResultSet numF = stmt.executeQuery("SELECT Count(*) FROM (SELECT Friend1 As friend FROM Friends WHERE Friend2 = '"+user+"' AND Friend1 IN (SELECT Username FROM Profile) UNION SELECT Friend2 As friend FROM Friends WHERE Friend1 = '"+user+"' AND Friend2 IN (SELECT Username FROM Profile));");
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

                html += "<div class='list-group'>";
                for(i=0;i<lFriend;i++){
                    ResultSet numApt = stmt.executeQuery("SELECT Count(*) FROM Aptitudes WHERE Username = '"+arrayF[i]+"';");
                    numApt.next();
                    badge = numApt.getInt(1);
                    html += "<button class='list-group-item' onclick='showConversation(`"+arrayF[i]+"`)'><h5>Aptitudes of "+arrayF[i]+"<span class='badge'>"+badge+"</span></h5>";
                    html += "</button>";
                    numApt.close();
                }
                html += "</div>";   

                //find validations
                ResultSet numV = stmt.executeQuery("SELECT Count(*) FROM (SELECT User, Aptitude FROM Validations WHERE Validator = '"+user+"');");
                numV.next();
                int lVal = numV.getInt(1);
                numV.close();

                ResultSet rVal = stmt.executeQuery(selectValidations);
                i = 0;
                String[] arrayU = new String[lVal];
                String[] arrayA = new String[lVal];
                while(rVal.next()){
                    arrayU[i] = rVal.getString("User");
                    arrayA[i] = rVal.getString("Aptitude");
                    System.out.println("arrayU["+i+"] = "+arrayU[i]+ " and arrayA[i] = "+arrayA[i]);
                    i++;
                }
                rVal.close();

                //make validate buttons
                for(i=0;i<lFriend;i++){
                    html += "<ul id='conv"+arrayF[i]+"' class='list-group message-list'><h5>Aptitudes of "+arrayF[i]+"</h5>";
                    ResultSet rApt = stmt.executeQuery("SELECT Aptitude FROM Aptitudes WHERE Username = '"+arrayF[i]+"';");
                    while(rApt.next()){
                        boolean validated = false;
                        aptitude = rApt.getString("Aptitude");
                        for(int j=0;j<lVal;j++){
                            System.out.println("arrayF["+i+"] = "+arrayF[i]+" and arrayU["+j+"] = "+arrayU[j]+" and aptitude = "+aptitude+ " and arrayA["+j+"] = "+arrayA[j]);
                                
                            if(arrayF[i].equals(arrayU[j]) && aptitude.equals(arrayA[j])){
                                System.out.println("arrayF["+i+"] = "+arrayF[i]+" and arrayU["+j+"] = "+arrayU[j]+" and aptitude = "+aptitude+ " and arrayA["+j+"] = "+arrayA[j]);
                                validated = true;
                                break;
                            }
                        }
                        if (validated == true){
                            html += "<li class='list-group-item aptitudes'>"+aptitude+"<button type='button' id='apt"+arrayF[i]+aptitude+"'class='btn btn-default validateBtn validated' onclick='invalidate(`"+arrayF[i]+"`, `"+aptitude+"`, `"+user+"`)'><span class='glyphicon glyphicon-check'></span> Validated </button></li>";
                        }else{
                            html += "<li class='list-group-item aptitudes'>"+aptitude+"<button type='button' id='apt"+arrayF[i]+aptitude+"'class='btn btn-default validateBtn' onclick='validate(`"+arrayF[i]+"`, `"+aptitude+"`, `"+user+"`)'><span class='glyphicon glyphicon-check'></span> Validate </button></li>";
                        }
                    }
                    html += "</ul>";
                    rApt.close();
                }

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


