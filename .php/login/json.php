<?php
if(isset($_POST['fitHistory'])){
	require_once 'conn.php';
	require_once 'validate.php';
	$userRecords = validate($_POST['currUserEmail']);
	$format = json_decode($_POST['fitHistory'],true);
	$json = json_encode($format);
	
	echo "FitHistory:".$json;
	/*$sql="UPDATE users SET fitHistory = '$json' WHERE user_id = 1";*/
	$sql = "UPDATE users SET fitHistory = JSON_ARRAY_APPEND(fitHistory,'$',CAST('$json'as JSON)) WHERE email = '$userRecords' ";
	if(!$conn->query($sql)){echo "Error: ".$sql."<br>".$conn->error;}else{echo "success";}
}
?>
