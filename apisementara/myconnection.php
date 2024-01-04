<?php

   $server = "localhost";
   $user = "root";
   $password = "";
   $database = "dbperpus";

   
   $db = mysqli_connect($server, $user, $password, $database);
   
        // Check connection
    if (!$db) {
                die("Connection failed: " . mysqli_connect_error());
    }
?>