<?PHP 
$hostname_localhost="localhost";
$database_localhost="id8658415_sinsenadb";
$username_localhost="id8658415_sinsenaapp";
$password_localhost="miguel1993";

$json =array();


		
		$conexion= mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		
		$consulta= "SELECT * FROM USUARIO";
		$resultado=mysqli_query($conexion,$consulta);
		while ($registro=mysqli_fetch_array($resultado)) {
			$result["ID_USUARIO"]=$registro["ID_USUARIO"];
			$result["NOMBRE_USUARIO"]=$registro["NOMBRE_USUARIO"];
			$result["ROL"]=$registro["ROL"];
			$result["CORREO"]=$registro["CORREO"];
			$json['usuarios'][]=$result; 
		
		}

		mysqli_close($conexion);
		echo json_encode($json);

		


 ?>