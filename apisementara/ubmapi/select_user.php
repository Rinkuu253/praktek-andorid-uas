<?php
    header('content-type:application/json');
    include "myconnection.php";
    $sSQL= "";
    $sSQL = " select * from tb_member order  by member_id";
    $result = mysqli_query($conn, $sSQL);
    if (mysqli_num_rows($result)>0)
    {         $member= array();
              while ($row=mysqli_fetch_assoc($result))
              {
                   $temp = array();
                   $temp['member_id'] = $row['member_id'];
                   $temp['first_name'] = $row['first_name'];
                   $temp['last_name'] = $row['last_name'];
                   $temp['email_account'] = $row['email_account'];
                   $temp['member_password'] = $row['member_password'];
                   array_push($member,$temp);
              }
              echo json_encode($member, JSON_PRETTY_PRINT);
             // echo json_encode($member);
    }
    mysqli_close($conn);
?>    