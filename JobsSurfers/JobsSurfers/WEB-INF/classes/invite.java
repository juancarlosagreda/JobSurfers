import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")

public class invite extends HttpServlet {
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
        String group=req.getParameter("group");
        System.out.println(group);
        String html = "";

        if (session != null){
            String select = "SELECT Friend1 As friend FROM Friends WHERE Friend2 = '"+user+"' UNION SELECT Friend2 As friend FROM Friends WHERE Friend1 = '"+user+"';";
            
            try{
                Statement stmt = conn.createStatement();
        
                ResultSet numF = stmt.executeQuery("SELECT Count(*) FROM (SELECT Friend1 As friend FROM Friends WHERE Friend2 = '"+user+"' UNION SELECT Friend2 As friend FROM Friends WHERE Friend1 = '"+user+"');");
                numF.next();
                int lFriends = numF.getInt(1);
                numF.close();

                ResultSet rFriends = stmt.executeQuery(select);
                int i = 0;
                String[] arrayF = new String[lFriends];
                while(rFriends.next()){
                    arrayF[i] = rFriends.getString("friend");
                    i++;
                }
                rFriends.close();

                html += MessageModal.invite(res, group, arrayF, lFriends); 
                html += "<script>$('#WriteModal').modal('show');</script>";
                ResponseManager.outputUWOP(res, user, html);
                stmt.close();
            }catch(SQLException e) {
                e.printStackTrace();
                System.out.println("Resulset: " + select + " Exception: " + e);
            }
        }
    }
}