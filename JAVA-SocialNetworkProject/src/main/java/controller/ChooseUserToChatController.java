package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.MessageService;
import socialnetwork.service.UtilizatorService;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ChooseUserToChatController {

    private final ObservableList<Utilizator> observableList = FXCollections.observableArrayList();
    @FXML
    public TableColumn<Utilizator,String> firstName;
    @FXML
    public TableColumn<Utilizator,String> lastName;
    @FXML
    public TableView table;
    @FXML
    public void initialize(){
        firstName.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        table.setItems(observableList);
    }

    private UtilizatorService srv;
    private MessageService srvMesaj;
    private Long idUserLongged;
    public void initial(Long idUserLongged,UtilizatorService srv,MessageService srvMesaj){
        this.srv=srv;
        this.idUserLongged=idUserLongged;
        this.srvMesaj=srvMesaj;
        refresh();
    }

    public void refresh(){
//        aleg toti utilizatorii cu care userul logat poate conversa
     Iterable<Utilizator> usersToChatWith= StreamSupport.stream(srv.getAll().spliterator(),false)
             .filter(x-> !x.getId().equals(idUserLongged))
             .collect(Collectors.toList());
     List<Utilizator> usersToPut = new ArrayList<>();
     for(Utilizator u:usersToChatWith)
         usersToPut.add(u);
     observableList.setAll(usersToPut);
    }
    public void startConv(MouseEvent mouseEvent) throws Exception{
        Utilizator selected= (Utilizator) table.getSelectionModel().getSelectedItem();
        if(selected!=null){
            //deschid conversatia;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/Conversation.fxml"));
            Pane root;
            root = loader.load();

            Stage register_stage=new Stage();
            ConversationController cController=loader.getController();
            cController.initial(idUserLongged,selected.getId(),srv,srvMesaj);
            register_stage.setScene(new Scene(root));
            register_stage.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nici o selectie!");
            alert.setContentText("Inainte de a intra intr-o conversatie, te rog selecteaz-o din tabel!");
            alert.showAndWait();
        }
    }
}
