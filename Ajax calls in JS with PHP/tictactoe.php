<?php
$values=$_GET['values'];
$values=explode(',',$values);

if($_GET['check']==1){
    if(@checkVictoryStatus($values,'X')!='')
         echo "X " .@checkVictoryStatus($values,'X');
    else
        echo "";
}
else
{
    while(true){
        $index_rand=rand(0,8);
        if($values[$index_rand]==0) {
            $values[$index_rand] = 'O';
            break;
        }
    }
    if(@checkVictoryStatus($values,'O')!='')
        echo "O " .@checkVictoryStatus($values,'O');
    else
    {
        $elems="";
        for($i=0;$i<8;$i++){
            $elems.=$values[$i].",";
        }
        $elems.=$values[8];

        echo $elems;

    }
}

function checkVictoryStatus($values,$who){
    //lines
    if($values[0]==$values[1] and $values[0]==$values[2] and $values[0]==$who)
        return "Won";
    if($values[3]==$values[4] and $values[3]==$values[5] and $values[3]==$who)
        return "Won";
    if($values[6]==$values[7] and $values[6]==$values[8] and $values[6]==$who)
        return "Won";

    //columns
    if($values[0]==$values[3] and $values[0]==$values[6] and $values[0]==$who)
        return "Won";
    if($values[1]==$values[4] and $values[1]==$values[7] and $values[1]==$who)
        return "Won";
    if($values[2]==$values[5] and $values[2]==$values[8] and $values[2]==$who)
        return "Won";

    //diag
    if($values[0]==$values[4] and $values[0]==$values[8] and $values[0]==$who)
        return "Won";
    if($values[2]==$values[4] and $values[2]==$values[6] and $values[2]==$who)
        return "Won";
}
