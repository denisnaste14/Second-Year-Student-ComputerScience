package controller;

public class ConvDTO {
    private String firstName;
    private String lastName;
    private String mesaj;
    private String mesajReply;
    private Long idMesaj;

    public ConvDTO(String first,String last,String mesaj,String mesajReply,Long idMesaj){
        this.firstName=first;
        this.lastName=last;
        this.mesaj=mesaj;
        this.mesajReply=mesajReply;
        this.idMesaj=idMesaj;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMesaj() {
        return mesaj;
    }

    public String getMesajReply() {
        return mesajReply;
    }

    public Long getIdMesaj() {
        return idMesaj;
    }
}
