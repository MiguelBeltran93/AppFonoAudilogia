<?PHP 
$hostname_localhost="localhost";
$database_localhost="id4487885_tst";
$username_localhost="id4487885_transitunja";
$password_localhost="transitunja";
$json =array();

	if(isset($_GET["ID_RUTA"])){
		$ID_RUTA=$_GET["ID_RUTA"];
		
		$conexion= mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		
		$consulta= "SELECT * FROM RUTA where ID_RUTA= '{$ID_RUTA}'";
		$resultado=mysqli_query($conexion,$consulta);
		if($registro=mysqli_fetch_array($resultado)){
				$json['ruta'][]=$registro;
			}
			mysqli_close($conexion);
			echo json_encode($json);

		}
	else {
			echo('no existe ruta');
	}


 ?>