<?php
if(isset($_POST['name'])&&isset($_POST['email'])&&isset($_POST['password'])){
    require_once 'conn.php';
    require_once 'validate.php';
    $name = validate($_POST['name']);
    $email = validate($_POST['email']);
    $password = validate($_POST['password']);
    echo "Name: ".$name;
    echo "Email: ".$email;
    echo "Password:".$password;
    $sql = "INSERT INTO users (name,email,password)VALUES('$name','$email','".md5($password)."')";

    if(!$conn->query($sql)){echo "Error: ".$sql."<br>".$conn->error;}else{echo "success";}
}
?>


