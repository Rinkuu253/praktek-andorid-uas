<?php

include "myconnection.php";


$id_user = isset($_GET['id_user']) ? $_GET['id_user'] : '';


$email_user = isset($_GET['email_user']) ? $_GET['email_user'] : '';
$nama_user = isset($_GET['nama_user']) ? $_GET['nama_user'] : '';
$pw_user = isset($_GET['pw_user']) ? $_GET['pw_user'] : '';
$foto_profil = isset($_GET['foto_profil']) ? $_GET['foto_profil'] : '';

// Update query
$sSQL = "UPDATE tb_user 
         SET 
         email_user = '$email_user',
         nama_user = '$nama_user',
         pw_user = '$pw_user',
         foto_profil = '$foto_profil'
         WHERE id_user = '$id_user'";

$query = mysqli_query($db, $sSQL);

if ($query) {
    echo json_encode(array(
        'status' => 'data terupdate'
    ));
} else {
    echo "gagal";
}

?>
