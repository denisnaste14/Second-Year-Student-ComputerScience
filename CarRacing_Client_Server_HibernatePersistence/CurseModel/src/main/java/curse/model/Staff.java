package curse.model;

public class Staff extends Entity<Tuple<String,String>>{
    private  String username;
    private  String password;
    private String nume;



    public Staff(String nume,String username,String password) {
        this.nume=nume;
        this.username=username;
        this.password=password;
        this.setId(new Tuple<>(this.username,this.password));
    }

    public Staff(){
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Staff{"+
                "username="+username+
                " nume="+this.nume+"}";
    }
}
