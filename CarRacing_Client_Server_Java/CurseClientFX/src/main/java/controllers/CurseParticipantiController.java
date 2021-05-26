package controllers;

import DTOs.CapacitateNrParticipanti;
import curse.model.Cursa;
import curse.model.Inscriere;
import curse.model.Participant;
import curse.model.Staff;
import curse.services.ICurseObserver;
import curse.services.ICurseServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CurseParticipantiController implements ICurseObserver {
    private final ObservableList<CapacitateNrParticipanti> observableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<CapacitateNrParticipanti,Integer> capCilindricaColumn;
    @FXML
    private TableColumn<CapacitateNrParticipanti,Integer> nrParticipantiColumn;
    @FXML
    private TableView table;
    @FXML
    private void initialize(){
        capCilindricaColumn.setCellValueFactory(new PropertyValueFactory<>("capCilindrica"));
        nrParticipantiColumn.setCellValueFactory(new PropertyValueFactory<>("nrParticipanti"));
        table.setItems(observableList);
    }
    @FXML
    private TextField echipatf;
    @FXML
    private TextField capCilindricatf;
    @FXML
    private TextField numetf;
    @FXML
    private TextField echipaatf;
    private ICurseServices service;
    private Staff member;
    private Stage s;
    private TextField usrn;
    private TextField pass;


    private CautareController cc;
    public void initial(ICurseServices service,Stage s, TextField usrn, TextField pass){
        this.service=service;
        this.s=s;
        this.usrn=usrn;
        this.pass=pass;
      // refresh();
    }

    public void setMember(Staff s){
        member=s;
    }

    public void refresh(){
        List<CapacitateNrParticipanti> lista=new ArrayList<>();
        service.cursaFindAll().forEach(x->{
            List<Inscriere> insc= new ArrayList<>();
            service.inscriereFindAllByCursa(x.getId()).forEach(insc::add);

            lista.add(new CapacitateNrParticipanti(x.getCapCilindrica(),insc.size()));
        });
        observableList.setAll(lista);
        if(this.cc!=null)
            this.cc.newParticipantInscriereUpdate(null,null);
    }

    public void cautare(MouseEvent mouseEvent) throws Exception {
       String echipa=echipatf.getText();
        if(echipa.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Camp gol!");
            alert.setContentText("Campul pentru echipa nu poate ramane gol!");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/Cautare.fxml"));
        Pane root;
        root = loader.load();

        Stage s=new Stage();
        this.cc=loader.getController();
        cc.initial(service,echipa);
        s.setScene(new Scene(root));
        s.show();
    }

    public void inscriere(MouseEvent mouseEvent) {
        String capCilindricastring = capCilindricatf.getText();
        String nume = numetf.getText();
        String echipa = echipaatf.getText();

        if (capCilindricastring.isEmpty() || nume.isEmpty() || echipa.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campuri goale!");
            alert.setContentText("Niciunul dintre campuri nu poate ramane necompletat!");
            alert.showAndWait();
            return;
        }
        int capCil = 0;
        try {
            capCil = Integer.parseInt(capCilindricastring);
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Numar gresit!");
            alert.setContentText("Campul cu capacitatea cilindrica trebuie sa contina un numar!");
            alert.showAndWait();
            return;
        }
        boolean exista = false;
        for (Cursa c : service.cursaFindAllByCapCilindrica(capCil))
            if (c != null) {
                exista = true;
                break;
            }
        if(!exista){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Numar gresit!");
            alert.setContentText("Nu exista nici o cursa cu acea capacitate cilindrica!");
            alert.showAndWait();
            return;
        }
        int ultimulID = -1;
        for (Participant p : service.participantFindAll()) {
            if (p.getId() > ultimulID)
                ultimulID = p.getId();
        }
        ultimulID+=1;
        Participant p = new Participant(nume,echipa);
        p.setId(ultimulID);


        for(Cursa c: service.cursaFindAllByCapCilindrica(capCil)){
            Inscriere i = new Inscriere(p,c);
            i.setId(null);
            service.newParticipantInscriere(p,i);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Participant si inscriere adaugate");
        alert.setContentText("Participantul si inscrierea/inscrierile acestuia la cursa/curse au fost adaugate cu succes!");
        alert.showAndWait();
        capCilindricatf.clear();
        numetf.clear();
        echipaatf.clear();
    }

    public void Logout(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Esti sigur ca vrei sa te deloggezi?");
        alert.showAndWait();
        if(alert.getResult().getText().compareTo("OK")==0) {
            usrn.clear();
            pass.clear();
            service.logout(member);
            s.close();
        }
    }

    @Override
    public void newParticipantInscriereUpdate(Participant p, Inscriere i) {
       Platform.runLater(this::refresh);
    }
}
