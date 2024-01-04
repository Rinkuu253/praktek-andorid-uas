<?php
include "myconnection.php";

// Select query to retrieve all data from tb_user
$sSQL = "SELECT * FROM tb_user";

$result = mysqli_query($db, $sSQL);

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
