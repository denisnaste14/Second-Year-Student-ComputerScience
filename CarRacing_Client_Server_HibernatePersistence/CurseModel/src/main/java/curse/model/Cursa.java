package curse.model;

public class Cursa extends Entity<Integer>{
    private Integer capCilindrica;
    public Cursa(){

    }
    public Cursa(Integer capCilindrica){
        this.capCilindrica=capCilindrica;
    }
    public Integer getCapCilindrica() {
        return capCilindrica;
    }

    public void setCapCilindrica(Integer capCilindrica) {
        this.capCilindrica = capCilindrica;
    }

    @Override
    public String toString() {
        return "Cursa{"+
                "capCilindrica="+capCilindrica+
                "mc}";
    }
}
