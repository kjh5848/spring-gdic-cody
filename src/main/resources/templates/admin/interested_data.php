<?php
	$db_host = "localhost"; 
	$db_user = "gdicland"; 
	$db_passwd = "gdicland!@#1";
	$db_name = "gdicland";

	// MySQL - DB 접속.
	$conn = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);

	if (mysqli_connect_errno()){

	echo "MySQL 연결 오류: " . mysqli_connect_error();

	exit;

	} else {
		//echo "DB : \"$db_name\"에 접속 성공.<br/>";
	}
	mysqli_set_charset($conn,"utf8"); 

	$sql = "SELECT * FROM interested_table";
	$result = mysqli_query($conn,$sql);

	$interested_data = "[";
	$add_data = "";
	
   if (mysqli_num_rows($result) > 0) {
	   while($row = mysqli_fetch_assoc($result)) {
		$con_data = nl2br($row['content']);
		$con_data = preg_replace('/\r\n|\r|\n/','',$con_data);

		$add_data = '{"id": "'.$row['id'].'",'.'"company_name": "'.$row['company_name'].'",'.'"user_name": "'.$row['user_name'].'",'.'"tell": "'.$row['tell'].'",'.'"content": "'. $con_data .'",'.'"address": "'.$row['address'].'"},' ;

		$interested_data = $interested_data . $add_data;
	   }
	   

	   $interested_data = rtrim($interested_data, ", ");
		$interested_data = $interested_data . "]";
   }else{
	$interested_data = false;
   }

   echo $interested_data;
?>