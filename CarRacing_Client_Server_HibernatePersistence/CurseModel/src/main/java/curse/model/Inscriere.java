package curse.model;

public class Inscriere extends Entity<Integer>{
    private Participant participant;
    private Cursa cursa;

    public Inscriere(Participant p, Cursa c){
        participant=p;
        cursa=c;
    }
    public Inscriere(){
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Cursa getCursa() {
        return cursa;
    }

    public void setCursa(Cursa cursa) {
        this.cursa = cursa;
    }

    @Override
    public String toString() {
        return "Inscriere{"+
                "Participant="+participant+
                "Cursa="+cursa+
                "}";
    }
}
