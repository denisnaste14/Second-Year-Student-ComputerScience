package DTOs;

public class CapacitateNrParticipanti {
    private Integer capCilindrica;
    private Integer nrParticipanti;

    public CapacitateNrParticipanti(Integer capCilindrica, Integer nrParticipanti){
        this.capCilindrica=capCilindrica;
        this.nrParticipanti=nrParticipanti;
    }

    public Integer getCapCilindrica() {
        return capCilindrica;
    }

    public Integer getNrParticipanti() {
        return nrParticipanti;
    }

    public void setCapCilindrica(Integer capCilindrica) {
        this.capCilindrica = capCilindrica;
    }

    public void setNrParticipanti(Integer nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }
}
