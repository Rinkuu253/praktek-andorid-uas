<?php
include "myconnection.php";

function request ($req) {
    global $db;
    
    $namatabel = mysqli_query($db, $req);

    $ambiltabel = mysqli_fetch_all($namatabel); // pake fetchall emng singkat tapi returnnya berupa array numerik

    return $ambiltabel; // nilai $ambiltabel disimpan ke $calonsiswa dilist.php
}





function add ($email_user, $nama_user, $pw_user, $foto_profil) {
    global $db;

    $email_user = isset($email_user) ? $email_user : '';
    $nama_user = isset($nama_user) ? $nama_user : '';
    $pw_user = isset($pw_user) ? $pw_user : '';
    $foto_profil = isset($foto_profil) ? $foto_profil : '';

    $sql = "INSERT INTO tb_user (`id_user`, `email_user`, `nama_user`, `pw_user`, `foto_profil`) 
            VALUES (NULL, '$email_user', '$nama_user', '$pw_user', '$foto_profil')";

    $query = mysqli_query($db, $sql);

    if ($query) {
        echo json_encode(array(
            'status' => 'data tersimpan'
        ));
    } else {
        echo "gagal";
    }

    return mysqli_affected_rows($db);
}

// Example usage
// $email_user = $_GET['email_user'] ?? '';
// $nama_user = $_GET['nama_user'] ?? '';
// $pw_user = $_GET['pw_user'] ?? '';
// $foto_profil = $_GET['foto_profil'] ?? '';

// tambah($email_user, $nama_user, $pw_user, $foto_profil);





function showAllUserData() {
    global $db;

    // Select query to retrieve all user data
    $sql = "SELECT * FROM tb_user";
    $result = mysqli_query($db, $sql);

    if ($result) {
        $data = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $data[] = $row;
        }

        if (!empty($data)) {
            echo json_encode($data);
        } else {
            echo "No users found";
        }
    } else {
        echo "Query failed";
    }
}





function showDetail($id) {
    global $db;

    $id= mysqli_real_escape_string($db, $id);

    // Select query to retrieve user data
    $sql = "SELECT * FROM tb_user WHERE id_user = '$id'";
    $result = mysqli_query($db, $sql);

    if ($result) {
        $data = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $data[] = $row;
        }

        if (!empty($data)) {
            echo json_encode($data);
        } else {
            echo "User not found";
        }
    } else {
        echo "Query failed";
    }
}

// Example usage
// $id_user = $_GET['id_user'] ?? '';
// showUserData($id_user);





function delete ($id) {
    global $db;
    
    $sql = "DELETE FROM tb_user WHERE id_user=$id";
    
    $query = mysqli_query($db, $sql);
    
    if ($query) {
        echo json_encode(array(
            'status' => 'data terhapus'
        ));
    } else {
        echo "gagal";
    }

    return mysqli_affected_rows($db);
}





function update ($id_user, $email_user, $nama_user, $pw_user, $foto_profil) {
    global $db;

    $id_user = mysqli_real_escape_string($db, $id_user);
    $email_user = mysqli_real_escape_string($db, $email_user);
    $nama_user = mysqli_real_escape_string($db, $nama_user);
    $pw_user = mysqli_real_escape_string($db, $pw_user);
    $foto_profil = mysqli_real_escape_string($db, $foto_profil);

    $sql = "UPDATE tb_user 
            SET 
            email_user = '$email_user',
            nama_user = '$nama_user',
            pw_user = '$pw_user',
            foto_profil = '$foto_profil'
            WHERE id_user = '$id_user'";

    $query = mysqli_query($db, $sql);

    if ($query) {
        echo json_encode(array(
            'status' => 'data terupdate'
        ));
    } else {
        echo "gagal";
    }

    return mysqli_affected_rows($db);
}

// Example usage
// $id_user = $_GET['id_user'] ?? '';
// $email_user = $_GET['email_user'] ?? '';
// $nama_user = $_GET['nama_user'] ?? '';
// $pw_user = $_GET['pw_user'] ?? '';
// $foto_profil = $_GET['foto_profil'] ?? '';

// edit ($id_user, $email_user, $nama_user, $pw_user, $foto_profil);



?>