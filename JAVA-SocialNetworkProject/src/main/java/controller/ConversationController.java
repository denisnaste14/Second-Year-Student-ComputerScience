package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import socialnetwork.domain.Message;
import socialnetwork.service.MessageService;
import socialnetwork.service.UtilizatorService;

public class ConversationController {
    private final ObservableList<ConvDTO> observableList = FXCollections.observableArrayList();
    @FXML
    public TableColumn<ConvDTO,String> firstName;
    @FXML
    public TableColumn<ConvDTO,String> lastName;
    @FXML
    public TableColumn<ConvDTO,String> mesaj;
    @FXML
    public TableColumn<ConvDTO,String> reply;
    @FXML
    public TableView table;
    @FXML
    public void initialize(){
        firstName.setCellValueFactory(new PropertyValueFactory<ConvDTO,String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<ConvDTO,String>("lastName"));
        mesaj.setCellValueFactory(new PropertyValueFactory<ConvDTO,String>("mesaj"));
        reply.setCellValueFactory(new PropertyValueFactory<ConvDTO,String>("mesajReply"));
        table.setItems(observableList);
    }
    @FXML
    public Label ChatWithLabel;
    @FXML
    public TextField textField;

    private Long idUserLongged;
    private Long idUserChatWith;
    private UtilizatorService srv;
    private MessageService srvMessaj;
    public void initial(Long idUserLongged, Long idUserChatWith, UtilizatorService srv, MessageService srvMessaj){
        this.idUserLongged=idUserLongged;
        this.idUserChatWith=idUserChatWith;
        this.srv=srv;
        this.srvMessaj=srvMessaj;

        //scriu cu cine conversez
        String cuCineConversez="Conversatie cu ";
        cuCineConversez+=srv.getOne(idUserChatWith).getFirstName()+" "
                +srv.getOne(idUserChatWith).getFirstName();
        ChatWithLabel.setText(cuCineConversez);
        refresh();
    }

    public void refresh(){
        List<Message> convorbireaRaw= srvMessaj.cronologic_message(idUserLongged,idUserChatWith);
        List<ConvDTO> mesaje=new ArrayList<>();
        for(Message m: convorbireaRaw){
            if(m.getReply() == 0L)
                mesaje.add(new ConvDTO(m.get_from().getFirstName(),m.get_from().getLastName(),m.get_message(),"",m.getId()));
            else
                mesaje.add(new ConvDTO(m.get_from().getFirstName(),m.get_from().getLastName(),m.get_message(),srvMessaj.getOne(m.getReply()).get_message(),m.getId()));

        }
        observableList.setAll(mesaje);
    }

    public void sendMsg(MouseEvent mouseEvent) {
        String mesajDeTrimis=textField.getText();
        if(mesajDeTrimis.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mesaj gol!");
            alert.setContentText("Nu poti trimite un mesaj gol!");
            alert.showAndWait();
        }
        else{
            long maxid=-1;
            for(Message m:srvMessaj.getAll()){
                if(m.getId()>maxid)
                    maxid=m.getId();
            }
            List<Long> dest=new ArrayList<>();
            dest.add(idUserChatWith);
            srvMessaj.send_message(maxid+1,idUserLongged,dest,mesajDeTrimis);
            textField.clear();
        }
        refresh();
    }

    public void reply(MouseEvent mouseEvent) {
        ConvDTO selected=(ConvDTO) table.getSelectionModel().getSelectedItem();
        if(selected==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selectie goala!");
            alert.setContentText("Inainte de a da un reply unui mesaj, selectati un mesaj!");
            alert.showAndWait();
        }
        else
        {
            String mesajDeTrimis=textField.getText();
            if(mesajDeTrimis.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mesaj gol!");
                alert.setContentText("Nu poti trimite un mesaj gol!");
                alert.showAndWait();
            }
            else{
                long maxid=-1;
                for(Message m:srvMessaj.getAll()){
                    if(m.getId()>maxid)
                        maxid=m.getId();
                }
                srvMessaj.reply_one(maxid+1,selected.getIdMesaj(),idUserLongged,mesajDeTrimis,idUserChatWith);
            }
        }
        refresh();
    }
}
