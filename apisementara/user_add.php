<?php
include "myconnection.php";

$email_user = isset($_GET['email_user']) ? $_GET['email_user'] : '';
$nama_user = isset($_GET['nama_user']) ? $_GET['nama_user'] : '';
$pw_user = isset($_GET['pw_user']) ? $_GET['pw_user'] : '';
$foto_profil = isset($_GET['foto_profil']) ? $_GET['foto_profil'] : '';

$sql = "INSERT INTO tb_user (`id_user`, `email_user`, `nama_user`, `pw_user`, `foto_profil`) 
         VALUES (NULL, '$email_user', '$nama_user', '$pw_user', '$foto_profil')";

$query = mysqli_query($conn, $sql);

if ($query) {
    echo json_encode(array(
        'status' => 'data tersimpan'
    ));
} else {
    echo "gagal";
}
?>