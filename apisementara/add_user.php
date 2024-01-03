<?php
    header('content-type:application/json');
    
    if (isset($_POST['name_user']) &&isset($_POST['email_user']) && isset($_POST['password_user'])){
        include "myconnection.php";
        $sSQL="";
        $sSQL = " select * from tb_user 
                where 
                email_user='$_POST[email_user]' 
                limit 1";

        //die($sSQL);          
        
        // if found record          
        if (mysqli_num_rows(mysqli_query($conn,$sSQL))>0){
            $data['error']= false;
            $data['message']='Failed insert record, duplicate email account';
        }else{
            $sSQL="";
            $sSQL= " INSERT INTO tb_user (name_user, email_user, status_user, tanggal_peminjaman ,password_user) 
            VALUES ('$_POST[name_user]','$_POST[email_user]','$_POST[status_user]','$_POST[tanggal_peminjaman]',
            MD5('$_POST[password_user]'))";


            //die ($sSQL);                  
            $ok = mysqli_query($conn, $sSQL);                  

            if ($ok){
               $data['error']= false;
               $data['message']='Succesfully inserted data user';
            }else{
                $data['error']= true;
                $data['message']='Failed insert data, System Error';
            }
        }
        echo json_encode($data);
        mysqli_close($conn);
    }
?>

