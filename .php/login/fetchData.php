<?php
if(isset($_POST['userEmail'])){
	require_once 'conn.php';
	require_once 'validate.php';
	$userEmailSearch = validate($_POST['userEmail']);
	//echo $userEmailSearch;
	$sqlCheck = "SELECT * FROM users WHERE email = '$userEmailSearch'";
	$res = $conn->query($sqlCheck);
	$result_array = array();
	while($row = mysqli_fetch_array($res)){
		array_push($result_array,array('fitHistory'=>json_decode($row[4])));
	}

	echo json_encode($result_array[0]);
}


?>

