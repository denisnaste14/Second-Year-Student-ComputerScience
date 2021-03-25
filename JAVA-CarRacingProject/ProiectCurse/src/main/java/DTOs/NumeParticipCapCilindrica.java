package DTOs;

public class NumeParticipCapCilindrica {
    private String numeParticipant;
    private Integer capCilindrica;

    public NumeParticipCapCilindrica(String numeParticipant, Integer capCilindrica){
        this.capCilindrica=capCilindrica;
        this.numeParticipant=numeParticipant;
    }

    public String getNumeParticipant() {
        return numeParticipant;
    }

    public Integer getCapCilindrica() {
        return capCilindrica;
    }

    public void setCapCilindrica(Integer capCilindrica) {
        this.capCilindrica = capCilindrica;
    }

    public void setNumeParticipant(String numeParticipant) {
        this.numeParticipant = numeParticipant;
    }
}
