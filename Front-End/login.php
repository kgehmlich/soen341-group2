<php 
	$con=mysqli_connect("localhost", "id696414_team2", "123456789team2", "id696414_soen341project");
	
	$username = $_POST["username"];
	$password = $_POST["password"];
	
	$statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ? and password = ?");
	mysqli_stmt_bind_param($statement, "ss", $username, $password);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_brind_result($statement, $userID, $name, $username, $age, $degree, $password);
	
	$response = array();
	$response["success"] = false;
	
	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["name"] = $name;
		$response["username"] = $username;
		$response["age"] = $age;
		$response["degree"] = $degree;
		$response["password"] = $password;
	}
	
	echo json_encode($response);

/>