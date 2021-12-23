<?php
if(isset($_POST['email'])&&isset($_POST['passowrd'])){
    require_once "conn.php";
    require_once "validate.php";
    $email = validate($_POST['email']);
    $password = validate($_POST['password']);
    $sql = "select * from users where email='$email'and password='".md5($passowrd)."'";
    $result = $conn->query($sql);
    if($result->num_rows>0){echo "success";}else{echo num_rows;}
}
?>
