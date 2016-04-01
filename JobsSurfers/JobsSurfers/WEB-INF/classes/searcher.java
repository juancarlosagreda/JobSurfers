import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class searcher extends HttpServlet {
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
        String word = req.getParameter("nameSearch");
        String filter = req.getParameter("selFilter");
        if (filter!="1" && filter!="2"){
            filter = "0";
        }
    	//This is the content
        html += "<div class='content' id='turuconnections'>";

        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");

        String person = "";
        String comp = "";
        
        if (session != null){
            String selectAllU = "SELECT UserReg.Username FROM UserReg, Profile WHERE UserReg.Username LIKE '%"+word+"%' OR (UserReg.Username = Profile.Username AND Profile.name1 LIKE '%"+word+"%') OR  (UserReg.Username = Profile.Username AND Profile.name2 LIKE '%"+word+"%') OR  (UserReg.Username = Profile.Username AND Profile.Surname1 LIKE '%"+word+"%') OR  (UserReg.Username = Profile.Username AND Profile.Surname2 LIKE '%"+word+"%') OR  (UserReg.Username = Profile.Username AND Profile.email LIKE '%"+word+"%') GROUP BY UserReg.Username;";
            String selectAllC = "SELECT CompanyName FROM CompanyReg WHERE CompanyName LIKE '%"+word+"%';";
            html += "<div id='userApply'><h4>Results from the search: "+word+"</h4></div>";
            try{
                Statement stmt = conn.createStatement();
                html += MessageModal.inbox(res, user); 

                if(filter.equals("1") || filter.equals("0")){
                    html += "<div class='list-group'>";
                    ResultSet searchAllU = stmt.executeQuery(selectAllU);          
                    while(searchAllU.next()){
                        person = searchAllU.getString("Username");
                        html += "<div class='list-group-item'>";
                        html += "<div>" + person + "<button type='button' class='turusend btn'>Add as a friend <span class='glyphicon glyphicon-plus' aria-hidden='true'></span><span class='glyphicon glyphicon-user' aria-hidden='true'></span></button><button type='button' class='turusend btn' onclick='openWM(`"+person+"`)'>Send message <span class='glyphicon glyphicon-envelope' aria-hidden='true'></span></button></div>";
                        html += "</div>";
                    }
                    html += "</div>";
                    searchAllU.close();
                }
                if(filter.equals("2") || filter.equals("0")){
                    html += "<div class='list-group'>";
                    ResultSet searchAllC = stmt.executeQuery(selectAllC);          
                    while(searchAllC.next()){
                        comp = searchAllC.getString("CompanyName");
                        html += "<div class='list-group-item'>";
                        html += "<div>" + comp + "<button type='button' class='turusend btn'>Follow <span class='glyphicon glyphicon-star-empty' aria-hidden='true'></span></button></div>";
                        html += "</div>";
                    }
                    html += "</div>";
                    searchAllC.close();
                }

                stmt.close();
            }catch(SQLException e) {
                e.printStackTrace();
                System.out.println("Resulset: " + selectAllC + " Exception: " + e);
            }
        }
        html += "</div></body></html>";
        ResponseManager.outputUWOP(res, user, html);
        toClient.close();
    }
}


