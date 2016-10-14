<?php
$server = "localhost";
$username = "root";
$password = "Roopa";
$database = "chat";

// Connecting to the database
$conn = mysql_connect($server, $username, $password);
if (!$conn)
{
  echo "Please try later.";
}
else
{
//to select the database
$db = mysql_select_db($database, $conn);
}

session_start();

if(isset($_GET['logout'])){
$fp = fopen("log.html", 'a');
fwrite($fp, "<div class='msgln'><i>User ". $_SESSION['name'] ." has left the chat session.</i><br></div>");
fclose($fp);

echo 'on logout';

//When the user logs out the session column in the table is set to no 
$uplog = "UPDATE userinfo SET session='no' WHERE username= '".$_SESSION['name']."' ";
$updatelog = mysql_query( $uplog, $conn );
mysql_close($conn);
	 
session_destroy();
header("Location: index.php"); //Redirect the user
}

function loginForm(){
echo'
<div id="loginform">
<form action="index.php" method="post">
<p>Please enter username and password to continue:</p>
<label for="name">Name:</label>
<input type="text" name="name" id="name" />
<label for="password">Password:</label>
<input type="text" name="password" id="password" />
<input type="submit" name="enter" id="enter" value="Enter" />
</form>
</div>
';
}

if(isset($_POST['enter'])){
//After the submit button is clicked, if the username field is not empty it	copies it to the session global variable
    if($_POST['name'] != ""){
	  
	  $_SESSION['name'] = stripslashes(htmlspecialchars($_POST['name'])); 
	}	
//If the username is not entered then it throws an error and returns to the login page
    else{
	   echo "Please type in a name";
	    loginForm();
	    return 0;
    }
//After the submit button is clicked, if the password field is not empty it	copies it to the session global variable
    if($_POST['password'] != ""){
	$_SESSION['password'] = stripslashes(htmlspecialchars($_POST['password']));
	}
	
//If the password is not entered then it throws an error and returns to the login page
    else{
	  echo "Please type in a password";
	  loginForm();
	  return 0;
    }
	
//If the username or the password didn't match to the database one, it throws an error and returns to the login page
	$name=$_SESSION['name'];
	$pas= $_SESSION['password'];
	$result1 =mysql_query("SELECT username, password FROM chat.userinfo where username='$name' and password='$pas' ");
	$num_rows=mysql_num_rows($result1);
	
	if($num_rows==0)
	{
		echo"Invalid user/password";
		loginForm();
	    return 0;
	}
}

?>


<!DOCTYPE html>
<html>
<head>
<title>chat</title>
<link type="text/css" rel="stylesheet" href="style.css" />
</head>

<?php
//if the session name and password is not set it goes to the login page
if(!isset($_SESSION['name']) && !isset($_SESSION['password'])){
loginForm();
}

//If the session name and password is set, it will come here and this where I am updating my session column in the table as yes 
//i.e. it indicates that the user has logged in
else{
	 $i=0;
     $check = "SELECT username, session FROM chat.userinfo";
     $result = mysql_query($check);
		
     while ($row = mysql_fetch_assoc($result)) {
	 if($row["username"]== $_SESSION['name'] && $row["session"] == 'yes' ) {	
     echo "You have already logged in from another browser";
			 return 0;
	}
	else{
	$up = "UPDATE userinfo SET session='yes' WHERE username= '".$_SESSION['name']."' ";
	$retval = mysql_query( $up, $conn );
   }
}		   
?>

<!--creating it dynamically. Hence commented-->
<!--p class="welcome">Welcome, <b><?php echo $_SESSION['name']; ?></b></p>
<p class="logout"><a id="exit" href="#">Exit Chat</a></p>
<div style="clear:both"></div-->

<div>
<p> Please select a friend with whom you want to chat </p>
<ul id="friendslist"> 
<li>Friend1</li> <br>
<li>Friend2</li> <br>
<li>Friend3</li> <br>
<li>Friend4</li> <br>
</div>


<?php
if(file_exists("log.html") && filesize("log.html") > 0){
    $handle = fopen("log.html", "r");
    $contents = fread($handle, filesize("log.html"));
    fclose($handle);

    echo $contents;
}
?>

<!--creating it dynamically. Hence commented-->
<!--form name="message" action="">
<input name="usermsg" type="text" id="usermsg" size="63" />
<input name="submitmsg" type="submit"  id="submitmsg" value="Send" />
</form-->


<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js">
</script>
<script type="text/javascript">

$(document).ready(function(){
	var selecteduser; var loggedinuser; var file;
	
	//on click of the friendslist, creating wrapper and menu div dynamically and also appending chatbox and usermessage
	$("li").click(function(){
		
	// taking the the name of the person on whom the user clicked
	selecteduser = $(this).text(); 	
		
	//creating wrapper element and appending it to the list
	$('#wrapper').remove();
	var wrapperDiv = document.createElement('div');
    wrapperDiv.id = 'wrapper';
	document.getElementById('friendslist').appendChild(wrapperDiv);
		
    //appending menu 
	$('#wrapper').append('<input type="text" class="welcome" name="Welcome" value="Welcome," />');
	$('#wrapper').append('<input type="text" name="name1" id="textbox" value="<?php echo $_SESSION['name'];?>"  />');
		
	//taking the value of the logged in user name from the above textbox
	loggedinuser = document.getElementById('textbox').value;
		
	$('#wrapper').append('<p class="logout"><a id="exit" href="#">Exit Chat</a></p>');
	$('#wrapper').append('<div style="clear:both"></div>');
		
	//appending chatbox
	$('#wrapper').append('<div id="chatbox"></div>');
		
    //appending usermessage form
	$('#wrapper').append('<form name="message" action="">');
	$('#wrapper').append('<input name="usermsg" type="text" id="usermsg" size="63" />');
	$('#wrapper').append('<input name="submitmsg" type="submit"  id="submitmsg" value="Send" />');
	$('#wrapper').append('<p> You are now talking to '+selecteduser+' </p>');
		
    
    $("#exit").click(function(){
    var exit = confirm("Are you sure you want to end the session?");
    if(exit==true){window.location = 'index.php?logout=true';}
   });


    $("#submitmsg").click(function(){
    var clientmsg = $("#usermsg").val();
	
	//passing the user message as well as the friend name on whom the user clicked
    $.post("post.php", {text: clientmsg,suser: selecteduser});
	$("#usermsg").attr("value", "");
    return false;
    }); 	
});
  
setInterval (loadLog, 2500);
                            
function loadLog(){
	
	var oldscrollHeight = $("#chatbox").attr("scrollHeight") - 20;
		
	//initalizing separate log file names on the basis of the logged in user and the person who was selected by the logged in user	 
	if((loggedinuser=="Friend1"  && selecteduser=="Friend2") || (loggedinuser=="Friend2" && selecteduser=="Friend1")){
		
				   file="file12";
                 
    }
	if((loggedinuser=="Friend1" && selecteduser=="Friend3") || (loggedinuser=="Friend3" && selecteduser=="Friend1")){
				  
				   file="file13";
                   
    }
	if((loggedinuser=="Friend1" && selecteduser=="Friend4") || (loggedinuser=="Friend4" && selecteduser=="Friend1")){
				  
				   file="file14";
                   
    }
	if((loggedinuser=="Friend2" && selecteduser=="Friend3") || (loggedinuser=="Friend3" && selecteduser=="Friend2")){
				  
				   file="file23";
                   
    }
	if((loggedinuser=="Friend2" && selecteduser=="Friend4") || (loggedinuser=="Friend4" && selecteduser=="Friend2")){
				  
				   file="file24";
                   
    }
	if((loggedinuser=="Friend3" && selecteduser=="Friend4") || (loggedinuser=="Friend4" && selecteduser=="Friend3")){
				  
				   file="file34";
                   
    }
	//passing the log file name
    $.ajax({ url: file+".html",
             cache: false,
             success: function(html){
                $("#chatbox").html(html);
                var newscrollHeight = $("#chatbox").attr("scrollHeight") - 20;
                if(newscrollHeight > oldscrollHeight){
                    $("#chatbox").animate({ scrollTop: newscrollHeight }, 'normal'); 
                }
             },
    });
}
});
</script>
<?php
}
?>


</body>
</html>