import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class GetFR extends HttpServlet {
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
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");
        String html="<div id=\"content\"><h2 class=\"title\">Friend Requests</h2>";
        
        String sql ="SELECT UserSends FROM FriendRequests WHERE UserReceives = '";
        sql += user + "' AND UserSends NOT IN (SELECT Friend1 FROM Friends WHERE Friend2 = '"+user+"')";
        System.out.println("sql: " + sql);
        
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            boolean haselements = false;
            html += "<ul class=\"InvitationList\">";
            
            while(result.next()) {
                haselements = true;
                String sender = result.getString("UserSends");
                html += "<li><a href=\"#\">" +sender+ "</a><span class=\"buttons\"><button type=\"button\" class=\"btn btn-primary\" onclick=\"Accept(this,'"+sender+"')\">Accept</button><button type=\"button\" class=\"btn btn-primary\">Decline</button></span></li>";
            }
            if(haselements == false){
                html += "<h5>You have no invitations for now</h5>";
            }
            html += "</ul></div>";
            statement.close();
        
        } catch(SQLException e){
            e.printStackTrace();
        }
        
        String hasprofilesql = "SELECT * FROM Profile WHERE Username='"+user+"'";
        try {
            Statement statement3 = connection.createStatement();
            ResultSet rs2 = statement3.executeQuery(hasprofilesql);
            
            if (rs2.next()) {
                ResponseManager.outputUWP(res, user, html);
            } else {
                ResponseManager.outputUWOP(res, user, html);
            }
            statement3.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}