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

