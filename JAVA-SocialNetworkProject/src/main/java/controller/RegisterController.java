package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.service.MessageService;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.RequestService;
import socialnetwork.service.UtilizatorService;

public class RegisterController {
    private UtilizatorService srv;
    private PrietenieService srvPrietenie;
    private MessageService srvMessaj;
    private RequestService srvRquest;

    @FXML
    private TextField prenume;

    @FXML
    private TextField nume;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField email;


    @FXML
    private Label erori;

    public void initial(UtilizatorService srvU, PrietenieService srvP, MessageService srvMessaj, RequestService srvRequest) {

        this.srv = srvU;
        this.srvPrietenie = srvP;
        this.srvMessaj = srvMessaj;
        this.srvRquest = srvRequest;
    }


    public void create(MouseEvent mouseEvent) {
         Long max = -10L;

         //generate next id
        Iterable<Utilizator> lis = srv.getAll();
        for (Utilizator utilizator : lis) {
           if(max<utilizator.getId())
               max = utilizator.getId();
        }
        Long new_id = max+1l;
        erori.setText("");

        //datele din textFielduri
        String surname = prenume.getText();
        String name = nume.getText();
        String usrname = username.getText();
        String pass = password.getText();
        String mail = email.getText();
        if (surname.isEmpty() || name.isEmpty() || usrname.isEmpty() || pass.isEmpty() || mail.isEmpty()) {
            erori.setText("Nu puteti lasa nici un camp necompletat!!!");
            return;
        }
        Utilizator newUser = new Utilizator(surname, name, usrname, pass, mail);
        newUser.setId(new_id);
        try{
            if(srv.addUtilizator(newUser)==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare de validare!");
                alert.setContentText("Exista deja acest utilizator!");
                alert.showAndWait();
            }
        }catch(ValidationException ve){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de validare!");
            alert.setContentText(ve.toString());
            alert.showAndWait();
        }catch(IllegalArgumentException iae){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de validare!");
            alert.setContentText(iae.toString());
            alert.showAndWait();
        }
        erori.setText("User nou adaugat!");
    }
}
