<?php
$server = "localhost";
$username = "root";
$password = "";
$database = "register_for_app";
$conn = new mysqli($server,$username,$password,$database);
if($conn->connect_error){
    echo "Failed Connection";
    die("Connection Failed: ".$conn->connect_error);
}
else{echo "Connected to database";}

?>
