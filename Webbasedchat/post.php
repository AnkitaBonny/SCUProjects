<?php
session_start();
if(isset($_SESSION['name'])){
	
   //getting the user message
   $text = $_POST['text'];
   
   //taking the name of the logged in user and the user that was selected to chat
   $selecteduser= $_POST['suser'];
   $loggedinuser= $_SESSION['name'];
   
   $selecteduser.trim();
   $loggedinuser.trim();
   
   
   //taking the argument from the index file for the creation of log file 
    if(($loggedinuser=="Friend1" && $selecteduser=="Friend2") || ($loggedinuser=="Friend2" && $selecteduser=="Friend1")){
				  
				   $file="file12";
                   
    }
	if(($loggedinuser=="Friend1" && $selecteduser=="Friend3") || ($loggedinuser=="Friend3" && $selecteduser=="Friend1")){
				  
				   $file="file13";
                   
    }
	if(($loggedinuser=="Friend1" && $selecteduser=="Friend4") || ($loggedinuser=="Friend4" && $selecteduser=="Friend1")){
				  
				   $file="file14";
                   
    }
	if(($loggedinuser=="Friend2" && $selecteduser=="Friend3") || ($loggedinuser=="Friend3" && $selecteduser=="Friend2")){
				  
				   $file="file23";
                   
    }
	if(($loggedinuser=="Friend2" && $selecteduser=="Friend4") || ($loggedinuser=="Friend4" && $selecteduser=="Friend2")){
				  
				   $file="file24";
                   
    }
	if(($loggedinuser=="Friend3" && $selecteduser=="Friend4") || ($loggedinuser=="Friend4" && $selecteduser=="Friend3")){
				  
				   $file="file34";
                   
    }
   //passing the filename
   $fp = fopen($file.'.html','a');
   fwrite($fp, "<div class='msgln'>(".date("g:i A").") <b>".$_SESSION['name']."</b>: ".stripslashes(htmlspecialchars($text))."<br></div>");
   fclose($fp);
}
?>