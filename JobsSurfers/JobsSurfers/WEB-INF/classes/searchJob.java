import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class searchJob extends HttpServlet {
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
        

        //This is the content
        html += "<div class='content' id='turu'>";
        html += "<h2>List of jobs</h2>";

        //Iniciar sesion. Login falso.
        /*HttpSession session = req.getSession(true);
        String name = "ejemplo";
        session.setAttribute("name", name);*/
        
        //Usar sesión iniciada
        boolean sessionBool = false;
        //HttpSession session = req.getSession(false);
        if (session != null) {
            sessionBool = true;
        }
        
        if (sessionBool) {
            String name = (String)session.getAttribute("name");
            if (name != null) {
                html += "<div id='userApply'><h4>User: " + name + "</h4></div>";
            }
        }

        html += "<div class='searchOptions'><button class='options' onclick='showAll()'>Show all the job offers</button>";
        html += "<button class='options' onclick='filterSector()'>Filter by sector</button>";
        html += "<button class='options' onclick='filterCompany()'>Filter by company</button>";
        html += "<button class='options' onclick='filterPosition()'>Filter by position</button></div>";

        try {
            Statement stmt=conn.createStatement();
            ResultSet resultAll = stmt.executeQuery("SELECT code, sector, company, position FROM jobslist");
            html += "<div class='container' id='showAll'><h3>All the job offers</h3>";
            html += "<form method=GET action='apply'>";
            html += "<table border='1' class='table table-hover table-condensed'>";          
            html += "<thead><tr><th></th><th>Job ID</th><th>Sector</th><th>Company</th><th>Position</th></tr></thead><tbody>";
            while(resultAll.next()){
                html += "<tr>";
                String codeStr = resultAll.getString("code");
                if (sessionBool) {
                    html += "<td><input type='radio' name='job' value='" + codeStr + "'></td>";
                }
                html += "<td>" + codeStr + "</td>";
                html += "<td>" + resultAll.getString("sector") + "</td>";
                html += "<td>" + resultAll.getString("company") + "</td>";
                html += "<td>" + resultAll.getString("position") + "</td>";
                html += "</tr>";
            }
            html += "</tbody></table>";
            html += "<input type=submit class='applyButton' value='Apply for the job' />";
            html += "</form>";
            html += "</div>";
            resultAll.close();

            //filter by sector
            ResultSet numSector = stmt.executeQuery("SELECT Count(*) FROM (SELECT Count(*) As Cant FROM jobslist GROUP BY sector)");
            numSector.next();
            int lSector = numSector.getInt(1);
            numSector.close();
            
            ResultSet rSector = stmt.executeQuery("SELECT sector FROM jobslist GROUP BY sector");
            int i = 0;
            String[] arraySector = new String[lSector];
            while(rSector.next()){
                arraySector[i] = rSector.getString("sector");
                i++;
            }
            rSector.close();

            html += "<div id='cSector'>";
            html += "<h3>Filter by sector</h3>";
            html += "<form role='form' action='filterJob' method=GET><div class='form-group'>";
            html += "<label for='cS'>Choose one sector from the list below:</label><select id='cS' class='form-control' name='comboSector'>";
            html += "<option value='null'> - Select - </option>";
            i = 0;
            while(i<lSector){
                html += "<option value='" + arraySector[i] + "'>" + arraySector[i] + "</option>";
                i++;
            }
            html += "</select></div>";
            html += "<input class='click' type=submit value='Apply filter' />";
            html += "</form></div>";
            
            //filter by company
            ResultSet numComp = stmt.executeQuery("SELECT Count(*) FROM (SELECT Count(*) As Cant FROM jobslist GROUP BY company)");
            numComp.next();
            int lComp = numComp.getInt(1);
            numComp.close();
            
            ResultSet rComp = stmt.executeQuery("SELECT company FROM jobslist GROUP BY company");
            i = 0;
            String[] arrayComp = new String[lComp];
            while(rComp.next()){
                arrayComp[i] = rComp.getString("company");
                i++;
            }
            rComp.close();

            html += "<div id='cCompany'>";
            html += "<h3>Filter by company</h3>";
            html += "<form role='form' action='filterJob' method=GET><div class='form-group'>";
            html += "<label for='cC'>Choose one company from the list below:</label><select id='cC' class='form-control' name='comboCompany'>";
            html += "<option value='null'> - Select - </option>";
            i = 0;
            while(i<lComp){
                html += "<option value='" + arrayComp[i] + "'>" + arrayComp[i] + "</option>";
                i++;
            }
            html += "</select></div>";
            html += "<input class='click' type=submit value='Apply filter' />";
            html += "</form></div>";

            //filter by position
            ResultSet numPos = stmt.executeQuery("SELECT Count(*) FROM (SELECT Count(*) As Cant FROM jobslist GROUP BY position)");
            numPos.next();
            int lPos = numPos.getInt(1);
            numPos.close();
            
            ResultSet rPos = stmt.executeQuery("SELECT position FROM jobslist GROUP BY position");
            i = 0;
            String[] arrayPos = new String[lPos];
            while(rPos.next()){
                arrayPos[i] = rPos.getString("position");
                i++;
            }
            rPos.close();

            html += "<div id='cPosition'>";
            html += "<h3>Filter by position</h3>";
            html += "<form role='form' action='filterJob' method=GET><div class='form-group'>";
            html += "<label for='cP'>Choose one position from the list below:</label><select id='cP' class='form-control' name='comboPosition'>";
            html += "<option value='null'> - Select - </option>";
            i = 0;
            while(i<lPos){
                html += "<option value='" + arrayPos[i] + "'>" + arrayPos[i] + "</option>";
                i++;
            }
            html += "</select></div>";
            html += "<input class='click' type=submit value='Apply filter' />";
            html += "</form></div>";


            stmt.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
     

        if (sessionBool) {
        }
        html += "</div>";
        html += "</body>";
        html += "</html>";
        ResponseManager.output(res, html);
        toClient.close();
    }
    public void destroy() {
        try{
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
