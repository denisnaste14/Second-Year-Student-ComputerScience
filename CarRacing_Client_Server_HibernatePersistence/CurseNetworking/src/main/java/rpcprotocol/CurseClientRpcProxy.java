package rpcprotocol;

import DTOs.CapacitateNrParticipanti;
import curse.model.*;
import curse.services.CurseException;
import curse.services.ICurseObserver;
import curse.services.ICurseServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class CurseClientRpcProxy implements ICurseServices {
    private final String host;
    private final int port;

    private ICurseObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public CurseClientRpcProxy(String host,int port){
        this.host=host;
        this.port=port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.NEW_PARTICIPANT_INSCRIERE;
    }

    private void handleUpdate(Response response){
        var tuple = (Tuple) response.data();
        client.newParticipantInscriereUpdate((Participant)tuple.getLeft(),(Inscriere) tuple.getRight());
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading (class not found) error "+e);
                }
            }
        }
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void initializeConnection() throws CurseException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //requests
    @Override
    public Iterable<Participant> participantFindAll() {
        Iterable<Participant> lista=new ArrayList<>();
        Request req = new Request.Builder().type(RequestType.ParticipantFindAll).data(lista).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
        lista =(Iterable<Participant>) response.data();
        return lista;
    }

    @Override
    public Iterable<Participant> participantFindAllByEchipa(String echipa) {
        Iterable<Participant> lista;
        Request req = new Request.Builder().type(RequestType.ParticipantiFindAllByEchipa).data(echipa).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
        lista =(Iterable<Participant>) response.data();
        return lista;
    }

    @Override
    public void participantAdd(Integer id, String nume, String echipa) {
        Participant p = new Participant(nume, echipa);
        p.setId(id);
        Request req = new Request.Builder().type(RequestType.ParticipantAdd).data(p).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
    }

    @Override
    public Participant participantFindOne(Integer id) {
        Participant p = new Participant(null, null);
        p.setId(id);
        Request req = new Request.Builder().type(RequestType.ParticipantFindOne).data(p).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
        p=(Participant) response.data();
        return p;
    }

    @Override
    public Iterable<Cursa> cursaFindAll() {
        Iterable<Cursa> lista = new ArrayList<>();
        Request req = new Request.Builder().type(RequestType.CursaFindAll).data(lista).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
        lista =(Iterable<Cursa>) response.data();
        return lista;
    }

    @Override
    public Iterable<Cursa> cursaFindAllByCapCilindrica(Integer capCil) {
        Iterable<Cursa> lista = new ArrayList<>();
        Request req = new Request.Builder().type(RequestType.CursaFindAllByCapCilndrica).data(capCil).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
        lista =(Iterable<Cursa>) response.data();
        return lista;
    }

    @Override
    public Iterable<Inscriere> inscriereFindAllByParticipant(Integer idParticipant) {
        Iterable<Inscriere> lista = new ArrayList<>();
        Request req = new Request.Builder().type(RequestType.InscriereFindAllByParticipant).data(idParticipant).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
        lista =(Iterable<Inscriere>) response.data();
        return lista;
    }

    @Override
    public Iterable<Inscriere> inscriereFindAllByCursa(Integer idCursa) {
        Iterable<Inscriere> lista = new ArrayList<>();
        Request req = new Request.Builder().type(RequestType.InscriereFindAllByCursa).data(idCursa).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
        lista =(Iterable<Inscriere>) response.data();
        return lista;
    }

    @Override
    public void inscriereAdd(Integer id, Participant p, Cursa c) {
        Inscriere i = new Inscriere(p,c);
        i.setId(id);
        Request req = new Request.Builder().type(RequestType.InscriereAdd).data(i).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
    }

    @Override
    public Staff login(Staff membru, ICurseObserver observer) {
        initializeConnection();
        Request req=new Request.Builder().type(RequestType.LOGIN).data(membru).build();
        sendRequest(req);

        Response response=readResponse();
        if(response.type()==ResponseType.OK){
            this.client=observer;
            return membru;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new CurseException(err);
        }
        return null;
    }

    @Override
    public void logout(Staff membru) {
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(membru).build();
        sendRequest(req);

        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
    }

    @Override
    public Staff staffFindOne(String username, String password) {
        Staff s = new Staff("denis",username,password);
        s.setId(new Tuple<>(username,password));
        Request req = new Request.Builder().type(RequestType.StaffFindOne).data(s).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
        s=(Staff) response.data();
        return s;
    }

    @Override
    public void staffAdd(String username, String password, String nume) {
        Staff s = new Staff(nume,username,password);
        s.setId(new Tuple<>(username,password));
        Request req = new Request.Builder().type(RequestType.StaffAdd).data(s).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
    }


    @Override
    public void newParticipantInscriere(Participant p, Inscriere i) {
        Request req=new Request.Builder().type(RequestType.New_Participant_Inscriere).data(new Tuple<>(p,i)).build();
        sendRequest(req);

        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CurseException(err);
        }
    }


    private void sendRequest(Request request) throws CurseException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new CurseException("Error sending object " + e);
        }
    }

    private Response readResponse() throws CurseException {
        Response response=null;
        try{
            //System.out.println(qresponses);
            response=qresponses.take();
            //System.out.println(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
