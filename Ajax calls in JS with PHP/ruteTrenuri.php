<?php
$mysqli = new mysqli("localhost", "root", "root", "lab_ajax");
if($mysqli->connect_error) {
    exit('Could not connect');
}

$urlparam=$_GET['q'];
$sql="";
if($urlparam==="") {
    $sql = "SELECT DISTINCT plecare FROM Statii";
    $plecare="";
    $result=$mysqli->query($sql);
    if($result->num_rows > 0){
        while($row = $result->fetch_assoc()){
            $plecare .= $row['plecare'] . ",";
        }
        $plecare[strlen($plecare)-1]=" "; #sterg ultima virgula
        echo $plecare;
    }
    else
        echo "no rows found";
}
else {
    $sql = "SELECT sosire FROM Statii WHERE plecare='" . $urlparam . "'";
    $sosire="";
    $result=$mysqli->query($sql);
    if($result->num_rows > 0){
        while($row = $result->fetch_assoc()){
            $sosire .= $row['sosire'] . ",";
        }
        $sosire[strlen($sosire)-1]=" "; #sterg ultima virgula
        echo $sosire;
    }
    else
        echo "no rows found";

}

