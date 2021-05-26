package curse.services;


import curse.model.Inscriere;
import curse.model.Participant;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ICurseObserver extends Remote {
    void newParticipantInscriereUpdate(Participant p, Inscriere i) throws CurseException, RemoteException;
}
