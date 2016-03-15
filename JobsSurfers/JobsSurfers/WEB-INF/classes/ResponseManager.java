import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class ResponseManager extends HttpServlet {
	public static void output (HttpServletResponse res, String message)  throws IOException {
		res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		toClient.println("<!DOCTYPE HTML> <HTML lang=\"en\" dir=\"LTR\">");
		toClient.println("<head><title>Jobs Surfers</title>");
		toClient.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		toClient.println("<meta charset=\"utf-8\">");
		toClient.println("<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">");
		toClient.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script>");
		toClient.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script>");
		toClient.println("<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\"></script>");
		//toClient.println("<link href=\"sticky-footer-navbar.css\" rel=\"stylesheet\" />");
		toClient.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
		toClient.println("<script src=\"scripts.js\"></script>");
		toClient.println("</head>");
		toClient.println("<body onload='openM()'>");
		toClient.println("<nav class=\"navbar navbar-default navbar-fixed-top\"><div class=\"container\"><div class=\"navbar-header\"><button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\"><span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button>");
		toClient.println("<a class=\"back\" onClick=\"goBack()\">< BACK</a><a class=\"navbar-brand\" href=\"#\"><img id=\"logo\" src=\"logo.jpg\" alt=\"Jobs Surfers Logo\" title=\"Jobs Surfers Logo\" /></a></div><div id=\"navbar\" class=\"collapse navbar-collapse\">");
		toClient.println("<ul class=\"nav navbar-nav navbar-right\"><li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Connect <span class=\"caret\"></span></a><ul class=\"dropdown-menu\"><li><a href=\"MakeComment.html\">Dashboard</a></li><li><a href=\"#\">Connection suggestions</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"#\">Validate aptitudes</a></li><li><a href=\"#\">Ask for recommendation</a></li></ul></li>");
		toClient.println("<li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Jobs <span class=\"caret\"></span></a><ul class=\"dropdown-menu\"><li><a href=\"searchJob\">Apply for a job</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"#\">Post a job</a></li><li><a href=\"#\">Manage jobs</a></li></ul></li>");  
		toClient.println("<li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">My Profile <span class=\"caret\"></span></a><ul class=\"dropdown-menu\"><li><a href=\"ButtonViewProfile.html\">View Profile</a></li><li><a href=\"Postfoto.html\">Create Profile</a></li><li><a href=\"#\">Add Aptitudes</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"MyGroups.html\">Groups</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"#\">Sing out</a></li><li><a href=\"#\">Delete Account</a></li></ul></li></ul></div><!--/.nav-collapse --></div></nav>");
		toClient.println("<div id=\"page\">");
		toClient.println(message);
		toClient.println("</div>");
		toClient.println("</body>");
		toClient.println("</html>");
		toClient.close();
	}
	
	public static void AjaxOutput (HttpServletResponse res, String message)  throws IOException {
		res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		toClient.println(message);
	}
}