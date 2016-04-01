/*Jordi*/

function see(image){
  document.getElementById("image").innerHTML = "<img src="+image+">" 
}

function goBack() {
    window.history.back()
}
function al () {
  if (document.getElementById("username-input").value=="" || document.getElementById("firstname-input").value=="" || document.getElementById("lastname-input").value=="") {
    alert("Problem to define username, firstname or lastname ");
  } else{
    alert("Are you soure to create this profile?");
    document.getElementById("check").submit();
  }
}
function clean(elemento){
  elemento.value = "";
}

/*Anna*/
function showAll(){
	document.getElementById("showAll").style.display="block";
	document.getElementById("cSector").style.display="none";
	document.getElementById("cCompany").style.display="none";
	document.getElementById("cPosition").style.display="none";	
}
function filterSector(){
	document.getElementById("showAll").style.display="none";
	document.getElementById("cSector").style.display="block";
	document.getElementById("cCompany").style.display="none";
	document.getElementById("cPosition").style.display="none";
}
function filterCompany(){
	document.getElementById("showAll").style.display="none";
	document.getElementById("cSector").style.display="none";
	document.getElementById("cCompany").style.display="block";
	document.getElementById("cPosition").style.display="none";
}
function filterPosition(){
	document.getElementById("showAll").style.display="none";
	document.getElementById("cSector").style.display="none";
	document.getElementById("cCompany").style.display="none";
	document.getElementById("cPosition").style.display="block";
}
function showConversation(conv){
  var x = document.getElementsByClassName("message-list");
  for (var i=0;i<x.length;i++){
    x[i].style.display="none";
  }
  document.getElementById("conv"+conv).style.display="block";
}
function openM(){
	$("#myModal").modal();
}
function openNewWM(){
  var amigo = document.getElementById("friendlist").value;
  document.getElementById("sperson").innerHTML=amigo;
  document.getElementById("rperson").innerHTML=amigo;
  document.getElementById("to").value=amigo;
  $("#WriteModal").modal();
}
function openWM(amigo){
  document.getElementById("sperson").innerHTML=amigo;
  document.getElementById("rperson").innerHTML=amigo;
  document.getElementById("to").value=amigo;
  $("#WriteModal").modal();
}
function createMessage() {
  $('#WriteModal').modal('hide');
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
      if (xhttp.readyState == 4 && xhttp.status == 200) {
        $('#SentModal').modal('show');
      }
    };
  xhttp.open("GET", "message?from="+ document.getElementById("from").value+"&to="+ document.getElementById("to").value+"&textMessage="+ document.getElementById("textMessage").value, true);
  xhttp.send(); 
}
function validate(user, apt, val){
  document.getElementById("apt"+user+apt).innerHTML="<span class='glyphicon glyphicon-check'></span> Validated ";
  $("#apt"+user+apt).addClass("validated");
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", "validate?user="+ user+"&aptitude="+ apt+"&validator="+ val, true);
  xhttp.send();
}
function invalidate(user, apt, val){
  document.getElementById("apt"+user+apt).innerHTML="<span class='glyphicon glyphicon-check'></span> Validate";
  $("#apt"+user+apt).removeClass("validated");
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", "validate?inuser="+ user+"&inaptitude="+ apt+"&invalidator="+ val, true);
  xhttp.send();
}
function searchAll(){
  window.location="searcher?nameSearch="+document.getElementById("inp-search").value;
}
/*Juan Fran*/
function createGroup() {
	$('#CreateGroupModal').modal('hide');
	var checked = document.getElementById("publicgroupbooleaninput").checked;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
      if (xhttp.readyState == 4 && xhttp.status == 200) {
        $('#groupnameplaceholder').html(xhttp.responseText);
        $('#responseCreateGroup').modal('show');
      }
    };
	  if (checked){
		  xhttp.open("GET", "CreateGroup?GroupName="+ document.getElementById("groupnameinput").value+"&public=on", true);
		  xhttp.send();
	  }else{
		  xhttp.open("GET", "CreateGroup?GroupName="+ document.getElementById("groupnameinput").value, true);
		  xhttp.send();
	  }
    document.getElementById("groupnameinput").value = "";
}
function FindGroups() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("GroupList").innerHTML = xhttp.responseText;
    }
  };
  xhttp.open("GET", "FindGroups", true);
  xhttp.send();
}
function count(){ 
       document.getElementById("characters").value=140-document.getElementById("post-text").value.length
       if (document.getElementById("characters").value <= 5) {
        var zero="0"
            document.getElementById("characters").style.color = 'red';
       } else { document.getElementById("characters").style.color = 'black';}
} 

function MakePost() {    
    var data ="";
    var post = document.getElementById("post-text").value;

    var request = new XMLHttpRequest();
    request.open('GET', 'MakePost?post-text='+post, true);

    request.onload = function() {
      if (request.status >= 200 && request.status < 400) {
        resp = request.responseText;
        data = JSON.parse(resp);
        document.getElementById("dashboard").innerHTML = "<li><div class=\"post-author\"><a href=\"#\">"+data.user+"</a></div><div class=\"post-content\"><input type=\"text\" disabled=\"true\" class=\"post\" value=\""+data.post+"\"></div></li>" + document.getElementById("dashboard").innerHTML;
      } 
    };

    request.send();
}
$(document).ready(function() {
    var data ="";
    var html = "";
    var html2 = "";
    var request = new XMLHttpRequest();
    request.open('GET', 'GetSuggestions', true);

    request.onload = function() {
      if (request.status >= 200 && request.status < 400) {
        resp = request.responseText;
        data = JSON.parse(resp);
        html += "<ul class=\"SuggestionList\">";
        for (var i=0; i<data.Friends.length; i++){
            html += "<li><a href=\"#\">"+ data.Friends[i] + "</a></li>";
        }
        html += "</ul>";
        document.getElementById("connection-suggestion-placeholder").innerHTML = html;

        html2 += "<ul class=\"SuggestionList\">";
        for (var i=0; i<data.Companies.length; i++){
            html2 += "<li><a href=\"#\">"+ data.Companies[i] + "</a></li>";
        }
        html2 += "</ul>";
        document.getElementById("companies-to-follow-placeholder").innerHTML = html2;

      } 
    };

    request.send();
});

/*Maialen*/
function showComment(element) {
	var comment = $(element).prevAll('.com:first').val();
	var user = $(element).prevAll('.user:first').val();
	var post = $(element).parent().parent().prevAll('.post:first').val();
	var xhttp = new XMLHttpRequest();

	$(element).prevAll('.com:first').val("");
	$(element).parent().toggle();
		xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
		   $(element).parent().siblings('.commentlist').append("<br>"+ xhttp.responseText) ;
		}
    };
	
  xhttp.open("GET", "MakeComment?post="+post+"&comment="+comment+"&User="+user, true);
  xhttp.send();
}

