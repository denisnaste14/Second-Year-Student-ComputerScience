package rpcprotocol;

import DTOs.CapacitateNrParticipanti;
import curse.model.*;
import curse.services.CurseException;
import curse.services.ICurseObserver;
import curse.services.ICurseServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;


public class CurseClientRpcReflexionWorker implements Runnable,ICurseObserver{
    private ICurseServices server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    public CurseClientRpcReflexionWorker(ICurseServices serv, Socket connection){
        this.server=serv;
        this.connection=connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
           // System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        //System.out.println("Sending response " + response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        Staff s= (Staff)request.data();
        try {
            server.login(s, this);
            return new Response.Builder().type(ResponseType.OK).data(s).build();
        } catch (CurseException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        Staff s=(Staff)request.data();
        try {
            server.logout(s);
            connected=false;
            return okResponse;
        } catch (CurseException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleParticipantiFindAllByEchipa(Request request){
        try{
            String echipa=(String) request.data();
            Iterable<Participant> participants=server.participantFindAllByEchipa(echipa);
            return new Response.Builder().type(ResponseType.OK).data(participants).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleParticipantFindAll(Request request){
        try{
            Iterable<Participant> participants=server.participantFindAll();
            return new Response.Builder().type(ResponseType.OK).data(participants).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleParticipantAdd(Request request){
        try{
            Participant p = (Participant) request.data();
            server.participantAdd(p.getId(),p.getNume(),p.getEchipa());
            return new Response.Builder().type(ResponseType.OK).data(p).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleParticipantFindOne(Request request){
        try{
            Participant aux=(Participant) request.data();
            Participant p = server.participantFindOne(aux.getId());
            return new Response.Builder().type(ResponseType.OK).data(p).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleCursaFindAll(Request request){
        try{
            Iterable<Cursa> curse=server.cursaFindAll();
            return new Response.Builder().type(ResponseType.OK).data(curse).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleCursaFindAllByCapCilndrica(Request request){
        try{
            Integer cap=(Integer) request.data();
            Iterable<Cursa> curse=server.cursaFindAllByCapCilindrica(cap);
            return new Response.Builder().type(ResponseType.OK).data(curse).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleInscriereFindAllByParticipant(Request request){
        try{
            Integer idParticipant=(Integer) request.data();
            Iterable<Inscriere> inscrieri=server.inscriereFindAllByParticipant(idParticipant);
            return new Response.Builder().type(ResponseType.OK).data(inscrieri).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleInscriereFindAllByCursa(Request request){
        try{
            Integer idCursa = (Integer) request.data();
            Iterable<Inscriere> inscrieri=server.inscriereFindAllByCursa(idCursa);
            return new Response.Builder().type(ResponseType.OK).data(inscrieri).build();
        }catch (CurseException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleInscriereAdd(Request request) {
        try {
            Inscriere inscriere = (Inscriere) request.data();
            server.inscriereAdd(inscriere.getId(), inscriere.getParticipant(), inscriere.getCursa());
            return new Response.Builder().type(ResponseType.OK).data(inscriere).build();
        } catch (CurseException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleStaffFindOne(Request request){
        try{
            Staff s = (Staff) request.data();
            s=server.staffFindOne(s.getId().getLeft(),s.getId().getRight());
            return new Response.Builder().type(ResponseType.OK).data(s).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleStaffAdd(Request request){
        try{
            Staff s = (Staff) request.data();
            server.staffAdd(s.getId().getLeft(),s.getId().getRight(),s.getNume());
            return new Response.Builder().type(ResponseType.OK).data(s).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleNew_Participant_Inscriere(Request request){
        try{
            var tuple=(Tuple)request.data();
            Participant p = (Participant) tuple.getLeft();
            Inscriere i = (Inscriere) tuple.getRight();
            server.newParticipantInscriere(p,i);
            return new Response.Builder().type(ResponseType.OK).data(tuple).build();
        }catch (CurseException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    @Override
    public void newParticipantInscriereUpdate(Participant p, Inscriere i) {
        Response response=new Response.Builder().type(ResponseType.NEW_PARTICIPANT_INSCRIERE).data(new Tuple<>(p,i)).build();
        try{
            sendResponse(response);
        }catch (IOException ioe){
           ioe.printStackTrace();
        }
    }
}
