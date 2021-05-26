package utils;


import curse.services.ICurseServices;
import rpcprotocol.CurseClientRpcReflexionWorker;

import java.net.Socket;


public class CurseRpcConcurrentServer extends AbsConcurrentServer {
    private ICurseServices curseServer;
    public CurseRpcConcurrentServer(int port, ICurseServices curseServer) {
        super(port);
        this.curseServer = curseServer;
        System.out.println("ProiectCurse - CurseRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        CurseClientRpcReflexionWorker worker = new CurseClientRpcReflexionWorker(curseServer,client);
        return new Thread(worker);
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }

}
