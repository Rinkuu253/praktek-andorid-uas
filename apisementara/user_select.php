<?php
include "myconnection.php";

$id_user = isset($_GET['id_user']) ? $_GET['id_user'] : '';

// Select query
$sSQL = "SELECT * FROM tb_user";

// If 'id_user' is provided, modify the query to retrieve a specific user
if (!empty($id_user)) {
    $sSQL .= " WHERE id_user = '$id_user'";
}

$result = mysqli_query($conn, $sSQL);

if ($result) {
    $data = array();

    while ($row = mysqli_fetch_assoc($result)) {
        $data[] = $row;
    }

    echo json_encode($data);
} else {
    echo "gagal";
}
?>
