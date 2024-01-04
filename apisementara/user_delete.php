<?php

include "myconnection.php";

$id_user = $_GET['id_user'];
$sql = "DELETE FROM tb_user WHERE id_user=$id_user";

$query = mysqli_query($db, $sql);

if ($query) {
    echo json_encode(array(
        'status' => 'data terhapus'
    ));
} else {
    echo "gagal";
}

?>