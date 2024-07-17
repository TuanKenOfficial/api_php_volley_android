<?php
require_once '../includes/DbOperations.php';
$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(
        isset($_POST['username']) and isset($_POST['password'])){
        
        $db = new DbOperations();
        
        if($db->userLogin($_POST['username'],
        $_POST['password'])){
            $user = $db->getUserByUsername($_POST['username']);
            $response['error'] = false;
            $response['id'] = $user['id'];
            $response['username'] = $user['username'];
            $response['password'] = $user['password'];
            $response['email'] = $user['email'];
            
        }else{
            $response['error'] = true;
            $response['message'] = "Email hoac mat khau khong hop le";
        }
    }
    else{
        $response['error'] = true;
        $response['message'] = "Cac truong bat buoc bi thieu";
    }
}
echo json_encode($response);
?>