import curse.services.ICurseServices;
import repository.database.CursaDbRepo;
import repository.database.InscriereDbRepo;
import repository.database.ParticipantDbRepo;
import repository.database.StaffDbRepo;
import server.Service;
import utils.AbstractServer;
import utils.CurseRpcConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/curseserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find curseserver.properties "+e);
            return;
        }

        ParticipantDbRepo pdr=new ParticipantDbRepo(serverProps);
        CursaDbRepo cdr=new CursaDbRepo(serverProps);
        InscriereDbRepo idr=new InscriereDbRepo(serverProps,pdr,cdr);
        StaffDbRepo sdr=new StaffDbRepo(serverProps);
        ICurseServices service=new Service(pdr,cdr,idr,sdr);
        int curseServerPort=defaultPort;
        try {
            curseServerPort = Integer.parseInt(serverProps.getProperty("curse.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+curseServerPort);
        AbstractServer server = new CurseRpcConcurrentServer(curseServerPort, service);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
