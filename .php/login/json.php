<?php
if(isset($_POST['fitHistory'])){
	require_once 'conn.php';
	require_once 'validate.php';
	$format = json_decode($_POST['fitHistory'],true);
	$json = json_encode($format);
	
	echo "FitHistory:".$json;
	$sql="UPDATE users SET fitHistory = '$json' WHERE user_id = 1";
	if(!$conn->query($sql)){echo "Error: ".$sql."<br>".$conn->error;}else{echo "success";}
}
?>
