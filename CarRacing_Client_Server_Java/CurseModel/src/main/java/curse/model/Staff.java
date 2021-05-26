package curse.model;

public class Staff extends Entity<Tuple<String,String>>{
    private final String username;
    private final String password;
    private String nume;

    public Staff(String nume,String username,String password) {
        this.nume=nume;
        this.username=username;
        this.password=password;
        this.setId(new Tuple<>(this.username,this.password));
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Staff{"+
                "username="+username+
                " nume="+this.nume+"}";
    }
}
