<?php
    header('content-type:application/json');
    include "myconnection.php";
    $sSQL= "";
    $sSQL = " select * from tb_buku order  by id_buku";
    $result = mysqli_query($conn, $sSQL);
    if (mysqli_num_rows($result)>0){         
        $user= array();
        while ($row=mysqli_fetch_assoc($result)){
            $temp = array();
            $temp['id_buku'] = $row['id_buku'];
            $temp['judul_buku'] = $row['judul_buku'];
            $temp['kategori_buku'] = $row['kategori_buku'];
            if('status_user' == NULL){
                $temp['status_buku'] = $row["buku tersedia"];
            }else{
                $temp['status_user'] = $row["buku sedang dipinjam"];
            }
            array_push($buku,$temp);
        }
        echo json_encode($buku, JSON_PRETTY_PRINT);
        // echo json_encode($member);
    }
    mysqli_close($conn);
?>    