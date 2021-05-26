package objectprotocol;

import curse.model.Staff;

public class LoginRequest implements Request{
    private final Staff s;

    public LoginRequest(Staff s){
        this.s=s;
    }

    public Staff getStaff(){
        return s;
    }
}
