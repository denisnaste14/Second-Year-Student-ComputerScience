package curse.services;

public class CurseException extends RuntimeException{
    public CurseException(){
    }

    public CurseException(String message){
        super(message);
    }

    public CurseException(String message, Throwable cause){
        super(message, cause);
    }
}
