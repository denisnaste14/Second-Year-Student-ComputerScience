<?php
$mysqli = new mysqli("localhost", "root", "root", "lab_ajax");
if($mysqli->connect_error) {
    exit('Could not connect');
}

$start=$_GET['start'];
$stop=$_GET['stop'];
$countOnly=$_GET['ct'];
if($countOnly==1){
    $sql="SELECT COUNT(*) FROM Person";
    $nrElem=$mysqli->query($sql);
    $nrElem=$nrElem->fetch_assoc();
    $nrElem=array_values($nrElem);
    $nrElem=$nrElem[0]; //numarul de elemente din tabela
    echo $nrElem;
    return;
}
else{
    $sql="SELECT nume,prenume,telefon,email FROM Person WHERE id BETWEEN " .$start ." AND " .$stop;
    $persoane="";
    $result = $mysqli->query($sql);
    while($row = $result->fetch_assoc()) {
        $persoane .= $row['nume'] . "," . $row['prenume'] . "," . $row['telefon'] . "," . $row['email'] . ";";
    }
    $persoane[strlen($persoane)-1]=" "; #sterg ; de la final
    echo $persoane;
}



