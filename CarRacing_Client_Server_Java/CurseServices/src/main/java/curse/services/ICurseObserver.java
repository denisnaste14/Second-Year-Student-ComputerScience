package curse.services;


import curse.model.Inscriere;
import curse.model.Participant;



public interface ICurseObserver {
    void newParticipantInscriereUpdate(Participant p, Inscriere i);
}
