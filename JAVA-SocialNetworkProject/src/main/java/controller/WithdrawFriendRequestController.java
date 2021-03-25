package controller;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import socialnetwork.domain.Request;
import socialnetwork.domain.Tuple;
import socialnetwork.service.MessageService;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.RequestService;
import socialnetwork.service.UtilizatorService;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class WithdrawFriendRequestController {

    private final ObservableList<ReqUser> observableList = FXCollections.observableArrayList();
    @FXML
    public TableColumn<ReqUser,String> firstName;
    @FXML
    public TableColumn<ReqUser,String> lastName;
    @FXML
    public TableView table;

    @FXML
    public void initialize(){
        firstName.setCellValueFactory(new PropertyValueFactory<ReqUser,String>("nume"));
        lastName.setCellValueFactory(new PropertyValueFactory<ReqUser,String>("lastName"));
        table.setItems(observableList);
    }

    private UtilizatorService srv;
    private PrietenieService srvPrietenie;
    private MessageService srvMessaj;
    private RequestService srvRquest;
    private Long idUserLongged;

    public void initial(Long idUserLongged , UtilizatorService srvU, PrietenieService srvP, MessageService srvMessaj, RequestService srvRequest) {
        this.idUserLongged=idUserLongged;
        this.srv = srvU;
        this.srvPrietenie = srvP;
        this.srvMessaj = srvMessaj;
        this.srvRquest = srvRequest;
        refresh();
    }

    private void refresh(){
//        le filtrez pe acelea care au id-ul celui care a trimis cererea=id-ul celui logat (doar la acelea am eu
//        ca utilizator acces si de asemenea ele trebuie inca sa fie in stare pending, pentru a mai putea retrage cererea
        Iterable<Request> filtredFriendRequests = StreamSupport.stream(srvRquest.getAll().spliterator(),false)
                .filter(x->x.getId().getLeft().equals(idUserLongged))
                .filter(x->x.getStatus().equals("pending"))
                .collect(Collectors.toList());
        List<ReqUser> reqUsers=new ArrayList<>();
        for(Request r:filtredFriendRequests){
            reqUsers.add(new ReqUser(r.getId(),
                    r.getId().getRight(),
                    srv.getOne(r.getId().getRight()).getFirstName(),
                    srv.getOne(r.getId().getRight()).getLastName(),
                    r.getStatus()));
        }
        observableList.setAll(reqUsers);
    }

    public void retrageCererea(MouseEvent mouseEvent) {
        ReqUser selected=(ReqUser) table.getSelectionModel().getSelectedItem();
        if(selected!=null){
            srvRquest.delete(selected.getIdRequest());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nici o selectie!");
            alert.setContentText("Inainte de a retrage o cerere de prietenie, te rog selecteaz-o din tabel!");
            alert.showAndWait();
        }
        refresh();
    }
}
