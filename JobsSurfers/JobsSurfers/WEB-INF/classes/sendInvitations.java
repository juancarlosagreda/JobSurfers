import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class sendInvitations extends HttpServlet {
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
        
        HttpSession session = req.getSession(false);        
        String user = (String)session.getAttribute("user");
        String groupname = req.getParameter("group");
        String[] friends = req.getParameterValues("friend");
        String select = "SELECT GroupID FROM Groups WHERE GroupName = '"+groupname+"';";
        String sql = "";
        int num;
        String groupid = "";
        String html = "";

        try{
            Statement stmt=conn.createStatement();

            ResultSet rSelect = stmt.executeQuery(select);
            rSelect.next();
            groupid = rSelect.getString("GroupID");
            rSelect.close();

            for(int i=0;i<friends.length;i++){
              sql = "INSERT INTO GroupInvitees VALUES ("+groupid+", '" + friends[i] + "');";
              System.out.println("Insert sql: " + sql);
              num = stmt.executeUpdate(sql);
              System.out.println(num);
            }
            html += MessageModal.invited(res, groupname); 
            html += "<script>$('#SentModal').modal('show');</script>";
            ResponseManager.outputUWOP(res, user, html);
            stmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Exception in creation: " + e);
        }
    }
}