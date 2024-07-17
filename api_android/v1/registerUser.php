<?php
require_once '../includes/DbOperations.php';
$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(
        isset($_POST['username']) and isset($_POST['email']) and isset($_POST['password'])){
            
            $db = new DbOperations();
            $result = $db->createUser(
                $_POST['username'],
                $_POST['password'],
                $_POST['email']
            ); 
            if($result == 1){
                $response['error'] = false;
                $response['message'] = "Dang ky thanh cong";
            }elseif($result == 2){
                $response['error'] = true;
                $response['message'] = "Da xay ra loi, vui long thu lai";
            }
            elseif($result == 0){
                $response['error'] = true;
                $response['message'] = "Email va username bi trung lap, vui long kiem tra lai, va chon
                lai email va username khac";
            }
    }else{
        $response['error'] = true;
        $response['message'] = "Cac truong bat buoc bi thieu";
    }
}else{
    $response['error'] = true;
    $response['message'] = "Yeu cau khong hop le";

}

echo json_encode($response);
?>