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

        HttpSession session = req.getSession(false); 
        String user=(String)session.getAttribute("user");

        String createdGroupssql = "SELECT GroupName FROM Groups WHERE creator = '" + user + "'";
        String html = "";
        System.out.println("Select sql1: " + createdGroupssql);
        html += "<div id=\"content\">";
        html += "<h2 id=\"title\"> Groups you are a part of </h2><span id=\"create-group-button\"> <button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#CreateGroupModal\"> Create Group </button></span><div id=\"GroupList\"></div><div class=\"modal fade\" id=\"CreateGroupModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\"><div class=\"modal-dialog\" role=\"document\"><div class=\"modal-content\"><div class=\"modal-header\"><button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><h3 class=\"modal-title\">Create Group</h3></div><div class=\"modal-body\"><form action=\"CreateGroup\" method=\"GET\" id=\"groupcreationform\"><div class=\"form-group\"><label for=\"GroupName\" class=\"control-label\">GroupName:</label><input type=\"text\" class=\"form-control\" name=\"GroupName\" id=\"groupnameinput\"><label for=\"publicGroup\" class=\"control-label\">Check if you want your group to be public: </label><input type=\"checkbox\" name=\"public\" id=\"publicgroupbooleaninput\"></div></form></div><div class=\"modal-footer\"><button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button><button type=\"button\" class=\"btn btn-default\" onClick=\"createGroup()\">Create</button></div></div></div></div>";

        try{
            boolean hascreated = false;
            Statement statement1 = connection.createStatement();
            ResultSet result = statement1.executeQuery(createdGroupssql);
            html += "<h3>Created by you</h3>";
            html += "<ul id=\"createdbyyou\">";
            while(result.next()) {
                hascreated = true;
                html += "<li class=\"group\"><a class=\"group-link\" href=\"#\">" + result.getString("GroupName") + "</a></li>";
            }
            if (hascreated == false){
                html += "<h5>You have not created any groups yet</h5>";
            }
            html += "</ul>";
            statement1.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
            System.out.println(" Exception: " + e);
        }
        

        String memberOfGroupssql = "Select GroupName FROM Groups, GroupMembers WHERE Groups.GroupID = GroupMembers.GroupID AND Members='"+user+"'";
        System.out.println("Select sql2: " + memberOfGroupssql);
        try{
            boolean ismember = false;
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery(memberOfGroupssql);
            html += "<h3>You are a member of</h3>";
            html += "<ul id=\"memberof\">";
            while(result2.next()) {
                ismember = true;
                html += "<li class=\"group\"><a class=\"group-link\" href=\"#\">" + result2.getString("GroupName") + "</a></li>";
            }
            if (ismember == false){
                html += "<h5>You're not a member of any group yet</h5>";
            }
            html += "</ul>";
            statement2.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
            System.out.println(" Exception: " + e);
        }

        html += "<div id=\"congratsplaceholder\"><div class=\"modal fade\" id=\"responseCreateGroup\" abindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\"><div class=\"modal-dialog\" role=\"document\"><div class=\"modal-content\"><div class=\"modal-header\"><button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><h3 class=\"modal-title\">Congratulations</h3></div><div class=\"modal-body\"><span class=\"Congrats-text\">You have created the group <span id=\"groupnameplaceholder\"></span> successfully!</span></div><div class=\"modal-footer\"><button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button><button type=\"button\" class=\"btn btn-default\">Invite Friends</button></div></div></div></div></div></div>";

        String hasprofilesql = "SELECT * FROM Profile WHERE Username='"+user+"'";
        try {
            Statement statement3 = connection.createStatement();
            ResultSet rs = statement3.executeQuery(hasprofilesql);

            if (rs.next()) {
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