<?php

   $server = "localhost";
   $user = "root";
   $password = "";
   $database = "dbperpus";

   
   $conn = mysqli_connect($server, $user, $password, $database);
   
        // Check connection
    if (!$conn) {
                die("Connection failed: " . mysqli_connect_error());
    }
?>