import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class GetGI extends HttpServlet {
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
        String html="<div id=\"content\"><h2 class=\"title\">Group Invitations</h2>";
        
        String sql = "SELECT Groups.GroupID, GroupName From Groups, GroupInvitees WHERE Groups.GroupID = GroupInvitees.GroupID AND InvitedPerson = '";
        sql += user + "' AND GroupInvitees.GroupID NOT IN (SELECT GroupID FROM GroupMembers WHERE Members = '"+user+"')";
        System.out.println("sql: " + sql);
        
        try {
            Statement ggiStatement=connection.createStatement();
            ResultSet result = ggiStatement.executeQuery(sql);
            boolean haselements = false;
            html += "<ul class=\"InvitationList\">";
            
            while(result.next()) {
                haselements = true;
                String group = result.getString("GroupName");
                String id = result.getString("GroupID");
                html += "<li><a href=\"#\">" + group + "</a><span class=\"buttons\"><button type=\"button\" class=\"btn btn-primary\" onclick=\"Join(this, "+id+", '"+group+"')\">Accept</button><button type=\"button\" class=\"btn btn-primary\">Decline</button></span></li>";
            }
            if(haselements == false){
                html += "<h5>You have no invitations for now</h5>";
            }
            html += "</ul></div>";
            ggiStatement.close();
        
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