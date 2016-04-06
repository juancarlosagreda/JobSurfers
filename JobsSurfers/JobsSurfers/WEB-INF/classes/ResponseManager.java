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

	public static void outputUWOP (HttpServletResponse res, String user, String message)  throws IOException {
		res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		toClient.println("<!DOCTYPE HTML><HTML lang=\"en\" dir=\"LTR\"><head>");
        toClient.println("<title>Jobs Surfers - "+user+"</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><meta charset=\"utf-8\">");
        toClient.println("<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">");
        toClient.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script><script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\"></script><script src=\"scripts.js\"></script>");
        toClient.println("<link rel=\"stylesheet\" href=\"style.css\"><link rel=\"icon\" href=\"icon.jpg\"></head><body>");
        toClient.println("<nav class=\"navbar navbar-default navbar-fixed-top\"><div class=\"container\"><div class=\"navbar-header\">");
        toClient.println("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\"><span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button>");
        toClient.println("<a class=\"back\" href=\"#\">< BACK</a><a class=\"navbar-brand\" href=\"#\"><img id=\"logo\" src=\"logo.jpg\" alt=\"Jobs Surfers Logo\" title=\"Jobs Surfers Logo\" /></a></div>");
        
        toClient.println("<div class=\"input-group navbar-form navbar-left\" id=\"adv-search\"><input type=\"text\" class=\"form-control\" id=\"inp-search\" placeholder=\"Search for users and companies...\" /><div class=\"input-group-btn\">");
        toClient.println("<div class=\"btn-group\" id=\"btn-group-search\" role=\"group\"><div class=\"dropdown dropdown-lg\"><button type=\"button\" class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"caret\"></span></button>");
        toClient.println("<div class=\"dropdown-menu dropdown-menu-right\" id=\"dropdown-search\" role=\"menu\"><form role=\"form\" action=\"searcher\" accept-charset=utf-8>");
        toClient.println("<div class=\"form-group form-group-search\"><label for=\"selFilter\">Filter by</label>");
        toClient.println("<select class=\"form-control form-control-search\" id=\"selFilter\" name=\"selFilter\"><option value=\"1\">User</option><option value=\"2\">Company</option></select></div>");
        toClient.println("<div class=\"form-group form-group-search\"><label for=\"nameSearch\">Name</label><input type=\"text\" id=\"nameSearch\" name=\"nameSearch\" class=\"form-control form-control-search\" type=\"text\" /></div>");
        toClient.println("<button type=\"submit\" class=\"btn btn-primary btn-submit-search\"><span class=\"glyphicon glyphicon-search\" aria-hidden=\"true\"></span></button></form></div></div>");
        toClient.println("<button type=\"button\" id=\"searchAllBtn\" onclick=\"searchAll()\"  class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-search\" aria-hidden=\"true\"></span></button></div></div></div>");
        
        toClient.println("<div id=\"navbar\" class=\"collapse navbar-collapse\"><ul class=\"nav navbar-nav navbar-right\"><li><a href=\"showMessages\"><p id=\"envelope\"><span class=\"glyphicon glyphicon-envelope\" aria-hidden=\"true\"></span></p></a></li><li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Connect <span class=\"caret\"></span></a><ul class=\"dropdown-menu\"><li><a href=\"#\">Find Connections <span class=\"glyphicon glyphicon-globe\" aria-hidden=\"true\"></span></a></li><li><a href=\"showConnections\">My Connections</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"validateAptitudes\">Validate aptitudes <span class=\"glyphicon glyphicon-check\" aria-hidden=\"true\"></span></a></li><li><a href=\"#\">Ask for recommendation</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"CompaniesFollowing\">Companies I Follow</a></li></ul></li>"); 
        toClient.println("<li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Jobs <span class=\"caret\"></span></a><ul class=\"dropdown-menu\"><li><a href=\"searchJob\">Apply for a job <span class=\"glyphicon glyphicon-paperclip\" aria-hidden=\"true\"></span></a></li><li><a href=\"myApplications\">See applications' status</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"#\">Post a job <span class=\"glyphicon glyphicon-pushpin\" aria-hidden=\"true\"></span></a></li><li><a href=\"#\">Manage jobs <span class=\"glyphicon glyphicon-briefcase\" aria-hidden=\"true\"></span></a></li></ul></li>");
		toClient.println("<li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">"+user+"'s Account <span class=\"caret\"></span></a><ul class=\"dropdown-menu\"><li><a href=\"#\">Create Profile <span class=\"glyphicon glyphicon-user\" aria-hidden=\"true\"></span></a></li><li><a href=\"#\">Add Aptitudes</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"FindGroups\">Groups</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"#\">Sign Out <span class=\"glyphicon glyphicon-off\" aria-hidden=\"true\"></span></a></li><li><a href=\"#\">Delete Account <span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></a></li></ul></li></ul></div></div></nav>");
		toClient.println(message);
		toClient.println("</body></html>");
	}	
	public static void outputUWP (HttpServletResponse res, String user, String message)  throws IOException {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println("<!DOCTYPE HTML><HTML lang=\"en\" dir=\"LTR\"><head>");
        toClient.println("<title>Jobs Surfers - "+user+"</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><meta charset=\"utf-8\">");
        toClient.println("<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">");
        toClient.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script><script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\"></script><script src=\"scripts.js\"></script>");
        toClient.println("<link rel=\"stylesheet\" href=\"style.css\"><link rel=\"icon\" href=\"icon.jpg\"></head><body>");
        toClient.println("<nav class=\"navbar navbar-default navbar-fixed-top\"><div class=\"container\"><div class=\"navbar-header\">");
        toClient.println("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\"><span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button>");
        toClient.println("<a class=\"back\" href=\"#\">< BACK</a><a class=\"navbar-brand\" href=\"#\"><img id=\"logo\" src=\"logo.jpg\" alt=\"Jobs Surfers Logo\" title=\"Jobs Surfers Logo\" /></a></div>");
        
        toClient.println("<div class=\"input-group navbar-form navbar-left\" id=\"adv-search\"><input type=\"text\" class=\"form-control\" id=\"inp-search\" placeholder=\"Search for users and companies...\" /><div class=\"input-group-btn\">");
        toClient.println("<div class=\"btn-group\" id=\"btn-group-search\" role=\"group\"><div class=\"dropdown dropdown-lg\"><button type=\"button\" class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"caret\"></span></button>");
        toClient.println("<div class=\"dropdown-menu dropdown-menu-right\" id=\"dropdown-search\" role=\"menu\"><form role=\"form\" action=\"searcher\" accept-charset=utf-8>");
        toClient.println("<div class=\"form-group form-group-search\"><label for=\"selFilter\">Filter by</label>");
        toClient.println("<select class=\"form-control form-control-search\" id=\"selFilter\" name=\"selFilter\"><option value=\"1\">User</option><option value=\"2\">Company</option></select></div>");
        toClient.println("<div class=\"form-group form-group-search\"><label for=\"nameSearch\">Name</label><input type=\"text\" id=\"nameSearch\" name=\"nameSearch\" class=\"form-control form-control-search\" type=\"text\" /></div>");
        toClient.println("<button type=\"submit\" class=\"btn btn-primary btn-submit-search\"><span class=\"glyphicon glyphicon-search\" aria-hidden=\"true\"></span></button></form></div></div>");
        toClient.println("<button type=\"button\" id=\"searchAllBtn\" onclick=\"searchAll()\"  class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-search\" aria-hidden=\"true\"></span></button></div></div></div>");
        
        toClient.println("<div id=\"navbar\" class=\"collapse navbar-collapse\"><ul class=\"nav navbar-nav navbar-right\"><li><a href=\"showMessages\"><p id=\"envelope\"><span class=\"glyphicon glyphicon-envelope\" aria-hidden=\"true\"></span></p></a></li><li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Connect <span class=\"caret\"></span></a><ul class=\"dropdown-menu\"><li><a href=\"#\">Find Connections</a></li><li><a href=\"#\">Ask for recommendations</a></li><li><a href=\"#\">My Connections</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"CompaniesFollowing\">Companies I Follow</a></li></ul></li>"); 
        toClient.println("<li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Jobs <span class=\"caret\"></span></a><ul class=\"dropdown-menu\"><li><a href=\"searchJob\">Apply for a job <span class=\"glyphicon glyphicon-paperclip\" aria-hidden=\"true\"></span></a></li><li><a href=\"myApplications\">See applications' status</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"#\">Post a job <span class=\"glyphicon glyphicon-pushpin\" aria-hidden=\"true\"></span></a></li><li><a href=\"#\">Manage jobs <span class=\"glyphicon glyphicon-briefcase\" aria-hidden=\"true\"></span></a></li></ul></li>");
        toClient.println("<li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">"+user+"'s Account <span class=\"caret\"></span></a><ul class=\"dropdown-menu\"><li><a href=\"#\">View Profile <span class=\"glyphicon glyphicon-user\" aria-hidden=\"true\"></span></a></li><li><a href=\"#\">Add Aptitudes</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"FindGroups\">Groups</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"#\">Sign Out <span class=\"glyphicon glyphicon-off\" aria-hidden=\"true\"></span></a></li><li><a href=\"#\">Delete Account <span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></a></li></ul></li></ul></div></div></nav>");
        toClient.println(message);
        toClient.println("</body></html>");
    }   
}