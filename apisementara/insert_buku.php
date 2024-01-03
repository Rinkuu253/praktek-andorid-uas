<?php
    header('content-type:application/json');
    if (isset($_POST['judul_buku']) &&isset($_POST['kategori_buku']) && isset($_POST['password_user'])){
        include "myconnection.php";
        $sSQL="";
        $sSQL = " select * from tb_buku
                where 
                judul_buku='$_POST[judul_buku]'";

        //die($sSQL);          
        
        // if found record
        $sSQL="";
        $sSQL= " INSERT INTO tb_user (judul_buku, kategori_buku, status_buku) 
        VALUES ('$_POST[judul_buku]','$_POST[kategori_buku]','$_POST[status_buku]'))";


        //die ($sSQL);                  
        $ok = mysqli_query($conn, $sSQL);                  

        if ($ok){
            $data['error']= false;
            $data['message']='Succesfully inserted data buku';
        }else{
            $data['error']= true;
            $data['message']='Failed insert data, System Error';         
        }
        echo json_encode($data);
        mysqli_close($conn);
    }
?>