package controllers;

import DTOs.NumeParticipCapCilindrica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

import java.util.List;
import java.util.ArrayList;


public class CautareController {
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
    private Service service;
    private String echipa;
    public void initial(Service service, String echipa){
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

}
