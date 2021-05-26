package controllers;

import curse.services.CurseException;
import curse.services.ICurseServices;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private TextField usernametf;
    @FXML
    private TextField passwordtf;
    @FXML
    private TextField confirmpasswordtf;
    @FXML
    private TextField nametf;
    private ICurseServices service;
    public Stage stage;
    public void initial(ICurseServices service, Stage s){
        this.service=service;
        this.stage=s;
    }

    public void register(MouseEvent mouseEvent) {
        String username=usernametf.getText();
        String password= passwordtf.getText();
        String confirmpassword= confirmpasswordtf.getText();
        String nume=nametf.getText();

        if(username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || nume.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campuri goale!");
            alert.setContentText("Niciunul dintre campuri nu poate ramane necompletat!");
            alert.showAndWait();
            return;
        }

        if(password.compareTo(confirmpassword)!=0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parole necorespondente");
            alert.setContentText("Campul confirm password trebuie sa corespunda cu campul password!");
            alert.showAndWait();
            return;
        }

        try{
            service.staffAdd(nume,username,password);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes!");
            alert.setContentText("Cont creat cu succes!");
            alert.showAndWait();
            stage.close();
        }catch(CurseException re){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Repo exception!");
            alert.setContentText(re.getMessage());
            alert.showAndWait();
        }
    }
}
