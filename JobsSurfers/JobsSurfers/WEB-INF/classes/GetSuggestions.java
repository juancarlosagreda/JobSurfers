import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class GetSuggestions extends HttpServlet {
    Connection connection;
        public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:JobSurfers";
            connection=DriverManager.getConnection(url);
            System.out.println("init" + (connection != null));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

     public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        String user = (String)session.getAttribute("user");
        String suggestions = "";
        String sql = "SELECT Friend1 AS FriendSuggestions FROM Friends "; 
        sql += "WHERE Friend1<>'"+user+"' ";
        sql += "AND (Friend2 IN (SELECT Friend1 FROM Friends WHERE Friend2='"+user+"') ";
        sql += "OR Friend2 IN (SELECT Friend2 FROM Friends WHERE Friend1='"+user+"')) ";
        sql += "AND Friend1 NOT IN (SELECT Friend1 FROM Friends WHERE Friend2='"+user+"') "; 
        sql += "AND Friend1 NOT IN (SELECT Friend2 FROM Friends WHERE Friend1='"+user+"') ";
        sql += "AND Friend1 NOT IN (SELECT UserReceives FROM FriendRequests WHERE UserSends = '"+user+"') ";
        sql += "AND Friend1 NOT IN (SELECT UserSends FROM FriendRequests WHERE UserReceives = '"+user+"') ";
        sql += "UNION SELECT Friend2 FROM Friends ";
        sql += "WHERE Friend2<>'"+user+"' ";
        sql += "AND (Friend1 IN (SELECT Friend1 FROM Friends WHERE Friend2='"+user+"') ";
        sql += "OR Friend1 IN (SELECT Friend2 FROM Friends WHERE Friend1='"+user+"')) ";
        sql += "AND Friend2 NOT IN (SELECT Friend1 FROM Friends WHERE Friend2='"+user+"') ";
        sql += "AND Friend2 NOT IN (SELECT Friend2 FROM Friends WHERE Friend1='"+user+"') ";
        sql += "AND Friend2 NOT IN (SELECT UserReceives FROM FriendRequests WHERE UserSends = '"+user+"') ";
        sql += "AND Friend2 NOT IN (SELECT UserSends FROM FriendRequests WHERE UserReceives = '"+user+"')";
 
        System.out.println("sqlFriends: " + sql);

        String sql2 = "SELECT CompanyName FROM CompanyReg ";
        sql2 += "WHERE CompanyID NOT IN (SELECT Comp FROM Following WHERE User = '"+user+"') ";
        sql2 += "AND CompanyID IN (SELECT Comp FROM Following ";
        sql2 += "WHERE User IN (SELECT Friend1 FROM Friends WHERE Friend2 = '"+user+"') "; 
        sql2 += "OR User IN (Select Friend2 FROM Friends WHERE Friend1 = '"+user+"'))";

        System.out.println(sql2);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            suggestions += "{ \"Friends\": [";
            if(result.next()){
                suggestions += "\"" + result.getString("FriendSuggestions") +"\"";
            }
            while(result.next()){
                suggestions += ",\""+result.getString("FriendSuggestions") + "\"";
            }
            suggestions += "], ";

        } catch(SQLException e){
            e.printStackTrace();
        }
        try{
            Statement statement2 = connection.createStatement();
            ResultSet res2 = statement2.executeQuery(sql2);
            suggestions += "\"Companies\": [";
            if (res2.next()){
                suggestions += "\"" + res2.getString("CompanyName") +"\"";
            }
            while(res2.next()){
                suggestions += ",\""+res2.getString("CompanyName") + "\"";
            }
            suggestions += "] }";
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        ResponseManager.AjaxOutput(res, suggestions);
     }
}