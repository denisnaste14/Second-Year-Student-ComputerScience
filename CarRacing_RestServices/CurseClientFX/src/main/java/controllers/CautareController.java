package controllers;

import DTOs.NumeParticipCapCilindrica;
import curse.model.Inscriere;
import curse.model.Participant;
import curse.services.ICurseObserver;
import curse.services.ICurseServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.ArrayList;


public class CautareController implements ICurseObserver {
    private final ObservableList<NumeParticipCapCilindrica> observableList = FXCollections.observableArrayList();
    @FXML
    private TableView table;
    @FXML
    private TableColumn<NumeParticipCapCilindrica,String> numeParticipantColumn;
    @FXML
    private TableColumn<NumeParticipCapCilindrica,Integer> capCilindircaColumn;
    @FXML
    private void initialize(){
        numeParticipantColumn.setCellValueFactory(new PropertyValueFactory<>("numeParticipant"));
        capCilindircaColumn.setCellValueFactory(new PropertyValueFactory<>("capCilindrica"));
        table.setItems(observableList);
    }
    private ICurseServices service;
    private String echipa;
    public void initial(ICurseServices service, String echipa){
        this.service=service;
        this.echipa=echipa;
        refresh();
    }

    public void refresh(){
        List<NumeParticipCapCilindrica> lista=new ArrayList<>();
        service.participantFindAllByEchipa(echipa).forEach(x->{
            service.inscriereFindAllByParticipant(x.getId()).forEach(y->{
                lista.add(new NumeParticipCapCilindrica(x.getNume(),y.getCursa().getCapCilindrica()));
            });
        });
        observableList.setAll(lista);
    }

    @Override
    public void newParticipantInscriereUpdate(Participant p, Inscriere i) {
        Platform.runLater(this::refresh);
    }
}
