package controllers;

import domain.Staff;
import domain.Tuple;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import repository.exceptions.RepoException;
import service.Service;

public class LoginController {

    @FXML
    private TextField usernametf;
    @FXML
    private TextField passwordtf;

    private Service service=null;

    public void initial(Service service){
        this.service=service;
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        String username=usernametf.getText();
        String password=passwordtf.getText();
        if(username.isEmpty() || password.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campuri goale!");
            alert.setContentText("Nici unul dintre campuri nu poate fi gol!");
            alert.showAndWait();
            return;
        }

        try{
            service.staffFindOne(username,password);
        }catch (RepoException re) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Membru al staffului inexistent");
            alert.setContentText(re.getMessage());
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/CurseParticipanti.fxml"));
        Pane root;
        root = loader.load();

        Stage s=new Stage();
        CurseParticipantiController cpc = loader.getController();
        cpc.initial(service,s,usernametf,passwordtf);
        s.setScene(new Scene(root));
        s.show();
    }

    public void signup(MouseEvent mouseEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/Signup.fxml"));
        Pane root;
        root = loader.load();

        Stage s=new Stage();
        SignupController sc=loader.getController();
        sc.initial(service,s);
        s.setScene(new Scene(root));
        s.setTitle("Register");
        s.show();
    }
}
