package domain;

public class Participant extends Entity<Integer> {
    private String nume;
    private String echipa;

    public Participant(String nume, String echipa){
        this.nume=nume;
        this.echipa=echipa;

    }

    public String getNume() {
        return nume;
    }

    public String getEchipa() {
        return echipa;
    }


    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setEchipa(String echipa) {
        this.echipa = echipa;
    }

    @Override
    public String toString() {
        return "Participant{"+
                "nume="+this.nume+
                " echipa="+this.echipa+
                "}";
    }


}
