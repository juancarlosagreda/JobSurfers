import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class FindGroups extends HttpServlet {
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

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {

        HttpSession session = req.getSession(true); 
        session.setAttribute("user", "JuanFran");
        //The previous two lines simulate that the user JuanFran is in session

        String user=(String)session.getAttribute("user");

        String createdGroupssql = "SELECT GroupName FROM Groups WHERE creator = '" + user + "'";
        String html = "";
        System.out.println("Select sql1: " + createdGroupssql);

        try{
            Statement statement1 = connection.createStatement();
            ResultSet result = statement1.executeQuery(createdGroupssql);
            html += "<h3>Created by you</h3>";
            html += "<ul>";
            while(result.next()) {
                html += "<li class=\"group\"><a class=\"group-link\" href=\"#\">" + result.getString("GroupName") + "</a></li>";
            }
            html += "</ul>";
        }
        catch(SQLException e) {
            e.printStackTrace();
            System.out.println(" Exception: " + e);
        }
        

        String memberOfGroupssql = "Select GroupName FROM Groups, GroupMembers WHERE Groups.GroupID = GroupMembers.GroupID AND Members='"+user+"'";
        System.out.println("Select sql2: " + memberOfGroupssql);
        try{
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery(memberOfGroupssql);
            html += "<h3>You are a member of</h3>";
            html += "<ul>";
            while(result2.next()) {
                html += "<li class=\"group\"><a class=\"group-link\" href=\"#\">" + result2.getString("GroupName") + "</a></li>";
            }
            html += "</ul>";
        }
        catch(SQLException e) {
            e.printStackTrace();
            System.out.println(" Exception: " + e);
        }

        ResponseManager.AjaxOutput(res, html);

    }
}