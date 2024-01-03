<?php
    header('content-type:application/json');
    include "myconnection.php";
    $sSQL= "";
    $sSQL = " select * from tb_user order  by id_user";
    $result = mysqli_query($conn, $sSQL);
    if (mysqli_num_rows($result)>0){         
        $user= array();
        while ($row=mysqli_fetch_assoc($result)){
            $temp = array();
            $temp['id_user'] = $row['id_user'];
            $temp['email_user'] = $row['email_user'];
            $temp['name_user'] = $row['name_user'];
            $temp['password_user'] = $row['password_user'];
            if('status_user' == NULL){
                $temp['status_user'] = $row["tidak meminjam"];
                $temp['tanggal_peminjaman'] = $row["-"];
            }else{
                $temp['status_user'] = $row["meminjam"];
                $temp['tanggal_peminjaman'] = $row['tanggal_peminjaman'];
            }
            array_push($member,$temp);
        }
        echo json_encode($member, JSON_PRETTY_PRINT);
        // echo json_encode($member);
    }
    mysqli_close($conn);
?>    