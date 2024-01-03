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


<?php
    header('content-type:application/json');
    if (isset($_POST['first_name']) && isset($_POST['last_name']) && 
        isset($_POST['email_account']) && isset($_POST['member_password']))
    {
        include "myconnection.php";
        $sSQL="";
        $sSQL = " select * from tb_member 
                  where 
                  email_account='$_POST[email_account]' 
                  limit 1";

        //die($sSQL);          
        
        // if found record          
        if (mysqli_num_rows(mysqli_query($conn,$sSQL))>0)
        {
            $data['error']= false;
            $data['message']='Failed insert record, duplicate email account';
        }  
        else
        {
            $sSQL="";
              $sSQL= " INSERT INTO tb_member (first_name,last_name,email_account, member_password) 
                                VALUES ('$_POST[first_name]','$_POST[last_name]','$_POST[email_account]', 
                                MD5('$_POST[member_password]'))";


            //die ($sSQL);                  
            $ok = mysqli_query($conn, $sSQL);                  

            if ($ok)
            {
               $data['error']= false;
               $data['message']='Succesfully inserted data member';
            }
            else {
                $data['error']= true;
                $data['message']='Failed insert data, System Error';
                 
            }
        }
        echo json_encode($data);
        mysqli_close($conn);
    }
?>


INSERT INTO `tb_user` (`id_user`, `email_user`, `nama_user`, `pw_user`, `foto_profil`) VALUES (NULL, 'cowo@wo.id', 'cowo', 'cowobuluk', 'cowo.jpg');