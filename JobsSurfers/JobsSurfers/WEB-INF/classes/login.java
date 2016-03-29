import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class login extends HttpServlet {
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
    
    public void destroy () {
        super.destroy();
        System.out.print("Closing connection ...");
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch(SQLException ex){
            System.out.println("Problem closing connection");
            System.out.println(ex.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        HttpSession session = req.getSession(true);
        String tipo = req.getParameter("tipo");
        String userlogin = req.getParameter("userlogin");
        String password = req.getParameter("password");
        
        String html = "";
        String sql = "";
        Statement statementSelect;
        Statement hasprofilestatement;
        ResultSet result;
        ResultSet hasprofile;
        String pass;
        boolean correct = false;
        
    
        if (tipo.equals("user")){
            sql = "SELECT Username, Password FROM UserReg WHERE Username ='"+ userlogin +"'";
            try{
                statementSelect=connection.createStatement();
                result = statementSelect.executeQuery(sql);
                while(result.next()) {
                    pass = result.getString("Password");
                    if (pass.equals(password)){
                        html += "<div id=\"left-sidebar\"><ul class=\"interactionMenu\"><li><a href=\"GetFR\">Friend Requests</a><span id=\"NumberOfFR\"></span></li><li><a href=\"GetGI\">Group Invitations</a><span id=\"NumberOfGI\"></span></li></ul></div><div id=\"page\"><div id=\"make-post-form\"><textarea class=\"post-textarea\" id=\"post-text\" placeholder=\"What are you thinking about?\" maxlength=\"140\" onkeydown=\"count()\"></textarea><input type=\"text\" id=\"characters\"  disabled=\"true\" value=\"140\"><button type=\"button\" class=\"btn btn-primary\" id=\"post-btn\" onclick=\"MakePost()\">Post</button></div>";
                        html += "<div id=\"dashboard\"><ul id=\"post-list\"></ul></div></div><div id=\"right-sidebar\"><h4>Connection Suggestions</h4><div id=\"connection-suggestion-placeholder\"></div><h4>Companies to Follow</h4><div id=\"companies-to-follow-placeholder\"></div></div>";
                        correct = true;
                        session.setAttribute("user", userlogin);
                    }
                }
                result.close();
                statementSelect.close();
                if (correct == false){
                    html += "Dear user "+userlogin+", your password is incorrect.";
                }
            }catch(SQLException e) {
                e.printStackTrace();
                System.out.println("Exception in login: " + e);
            }
            String hasprofilesql = "SELECT * FROM Profile WHERE Username = '"+userlogin+"'";

            try{
                hasprofilestatement = connection.createStatement();
                hasprofile = hasprofilestatement.executeQuery(hasprofilesql);
                if(hasprofile.next()){
                    ResponseManager.outputUWP(res, userlogin, html);
                } else {
                    ResponseManager.outputUWOP(res, userlogin, html);
                }

            }catch(SQLException hpex){
                
            }

        }else{

            sql = "SELECT CompanyName, Password FROM CompanyReg WHERE CompanyName ='"+ userlogin +"'";
            try{
                statementSelect=connection.createStatement();
                result = statementSelect.executeQuery(sql);
                while(result.next()) {
                    pass = result.getString("Password");
                    if (pass==password){
                        html += "Welcome back company "+userlogin+".";
                        correct = true;
                    }
                }
                result.close();
                statementSelect.close();
                if (correct == false){
                    html += "Dear company "+userlogin+", your password is incorrect.";
                }
            }catch(SQLException e) {
                e.printStackTrace();
                System.out.println("Exception in login: " + e);
            }
        }
    }
}