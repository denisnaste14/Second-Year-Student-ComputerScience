package controller;

import javafx.scene.control.Button;
import socialnetwork.domain.Tuple;

public class ReqUser {
    private String nume;
    private  String status;
    private Long idUser;
    private Tuple<Long,Long> idRequest;
    private String lastName;
    public ReqUser(Tuple<Long,Long> idReq ,Long iduser ,String nume,String status)
    {
        this.idUser = iduser;
        idRequest = idReq;
        this.nume = nume;
        this.status = status;

    }
    //doar in unele cazuri am nevoie si de prenume, de aceea am creeat un constructor separat
    public ReqUser(Tuple<Long,Long> idReq ,Long iduser ,String nume, String lastName,String status)
    {
        this.idUser = iduser;
        idRequest = idReq;
        this.nume = nume;
        this.status = status;
        this.lastName=lastName;
    }
    public Tuple<Long,Long> getIdRequest()
    {
        return this.idRequest;
    }

    public String getStatus(){return this.status;}

    public String getNume() {
        return nume;
    }

    public String getLastName() { return lastName; }

    public Long getIdUser() {
        return idUser;
    }




}
