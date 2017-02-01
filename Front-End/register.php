<php

	$con = mysqli_connect("localhost", "id696414_team2", "123456789team2", "id696414_soen341project");
	
	$name = $_POST["name"];
	$username = $_POST["username"];
	$age = $_POST["age"];
	$degree = $_POST["degree"];
	$password = $_POST["password"];
	
	$statement = mysqli_prepare($con, "INSERT INTO user (name, username, age, degree, password) values (?, ?, ?, ?, ?)");
	mysqli_stmt_bind_param($statement, "siss", $name, $username, $age, $degree, $password);
	
	$response = array();
	$response["success"] = true;
	
	echo json_encode($response);
/>