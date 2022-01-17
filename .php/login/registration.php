<?php
if(isset($_POST['name'])&&isset($_POST['email'])&&isset($_POST['password'])){
    require_once 'conn.php';
    require_once 'validate.php';
    $name = validate($_POST['name']);
    $email = validate($_POST['email']);
    $password = validate($_POST['password']);
    $fitHistoryIntialise = json_encode("[{}]");
    echo "Name: ".$name;
    echo "Email: ".$email;
    echo "Password:".$password;
    //Check if the user exist first
    $userCheck = "SELECT * FROM users WHERE email='$email'";
    $result = $conn->query($userCheck);
    if($result->num_rows>0){
        echo "This User Already Exists";
    }else{
        $sqlInsert = "INSERT INTO users (name,email,password,fitHistory)VALUES('$name','$email','".md5($password)."',JSON_ARRAY())";
        if(!$conn->query($sqlInsert)){echo "Error: ".$sqlInsert."<br>".$conn->error;}else{echo "success";}
    }
    
}
?>


