<?php
   $server = "localhost";
   $database = "ubmdb";
   $user = "root";
   $password = "";

   $conn = mysqli_connect($server, $user, $password, $database);
        // Check connection
    if (!$conn) {
                die("Connection failed: " . mysqli_connect_error());
    }
?>