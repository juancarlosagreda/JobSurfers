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
function openM(){
    $("#myModal").modal();
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
    document.getElementById("createdbyyou").innerHTML = document.getElementById("createdbyyou").innerHTML + "<li class=\"group\"><a class=\"group-link\" href=\"#\">" + document.getElementById("groupnameinput").value +"</a></li>";
    document.getElementById("groupnameinput").value = "";
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
    document.getElementById("post-text").value="";
    var html = "";

    var request = new XMLHttpRequest();
    request.open('GET', 'MakePost?post-text='+post, true);

    request.onload = function() {
      if (request.status >= 200 && request.status < 400) {
        resp = request.responseText;
        data = JSON.parse(resp);
        html += "<li><div class=\"post-author\"><a href=\"#\">"+data.user+"</a> says: </div>";
        html += "<div class=\"post-content\"><input type=\"text\" disabled=\"true\" class=\"post\" value=\""+data.post+"\"></div></li>"
        document.getElementById("post-list").innerHTML =  html + document.getElementById("post-list").innerHTML;
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
        var li_height = $(".SuggestionList" ).find( "li" ).height();
        $(".SuggestionList" ).animate({ 'height': (li_height * 6) + 'px' });

      } 
    };

    request.send();
});
$(document).ready(function() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            document.getElementById("NumberOfFR").innerHTML = xhttp.responseText;
            if (xhttp.responseText != 0){
                document.getElementById("NumberOfFR").style = "background-color: rgba(224, 0, 0, 0.89); border: 1px solid rgba(224, 0, 0, 0.89);";
            }
        }
    };
      xhttp.open("GET", "GetNumberOfNewFR", true);
      xhttp.send();
});

$(document).ready(function() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            document.getElementById("NumberOfGI").innerHTML = xhttp.responseText;
            if (xhttp.responseText != 0){
                document.getElementById("NumberOfGI").style = "background-color: rgba(224, 0, 0, 0.89); border: 1px solid rgba(224, 0, 0, 0.89);";
            }
        }
    };
    xhttp.open("GET", "GetNumberOfNewGI", true);
    xhttp.send();
});

function Join(button, groupid, groupname) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            $(button).parent().parent().fadeOut();
            Joined(groupname);
        }
    };
    xhttp.open("GET", "JoinGroup?groupID="+groupid+"&groupName="+groupname, true);
    xhttp.send();
}
function emptyList(message){
    setTimeout(function() {var size = $('.InvitationList').find('li').length; var sizehidden = $(".InvitationList").find('li').filter(function () {return $(this).css('display') === 'none';}).length; if(size == sizehidden){$('.InvitationList').html('<h5>'+message+'</h5>');}}, 900);
}
function Accept(button, sender) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            $(button).parent().parent().fadeOut();
            Accepted(sender);
        }
        var size = $('.InvitationList').find('li').length
        var sizehidden = $(".InvitationList").find('li').filter(function () {return $(this).css('display') === 'none';}).length;
        if(size == sizehidden){
            $('.InvitationList').html('<h5> You have no more friend requests for now</h5>');
        }
    };
    xhttp.open("GET", "AcceptFriend?sender="+sender, true);
    xhttp.send();
}
function Joined(groupname){
    document.getElementById("notification").innerHTML = "<div class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a><strong>Success!</strong> You are now a member of: "+groupname+"</div>";
    setTimeout(function() {document.getElementById('notification').innerHTML='';},3000);
}
function Accepted(sender){
    document.getElementById("notification").innerHTML = "<div class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a><strong>Success!</strong> You are now connected to: "+sender+"</div>";
    setTimeout(function() {document.getElementById('notification').innerHTML='';},3000);
}


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

