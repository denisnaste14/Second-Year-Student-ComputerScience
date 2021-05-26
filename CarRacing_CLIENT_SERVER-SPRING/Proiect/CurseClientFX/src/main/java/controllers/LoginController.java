package controllers;

import curse.model.Staff;
import curse.services.CurseException;
import curse.services.ICurseObserver;
import curse.services.ICurseServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernametf;
    @FXML
    private TextField passwordtf;
    @FXML
    private Button signupbtn;

    private ICurseServices service=null;
    private CurseParticipantiController cpc;
    private Parent parent;
    public void initial(ICurseServices service){
        signupbtn.setVisible(true);
        this.service=service;
    }

    public void setCurseParticipantiController(CurseParticipantiController c) {
        this.cpc=c;
    }

    public void setParent(Parent p){
        this.parent=p;
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
        Staff sta=new Staff("",username,password);
        try{
            service.login(sta,cpc);
        }catch (CurseException re) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Membru al staffului inexistent");
            alert.setContentText(re.getMessage());
            alert.showAndWait();
            return;
        }

        Stage s=new Stage();
        cpc.initial(service,s,usernametf,passwordtf);
        cpc.refresh();
        cpc.setMember(sta);
        s.setScene(new Scene(parent));
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
