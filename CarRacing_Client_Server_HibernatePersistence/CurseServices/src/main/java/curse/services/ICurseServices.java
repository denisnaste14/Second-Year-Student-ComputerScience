package curse.services;

import curse.model.*;

public interface ICurseServices{

    //
    //
    //Participant
    //
    //
    Iterable<Participant> participantFindAll();
    Iterable<Participant> participantFindAllByEchipa(String echipa);

    void participantAdd(Integer id,String nume,String echipa);

    Participant participantFindOne(Integer id);
    //
    //
    //Cursa
    //
    //
    Iterable<Cursa> cursaFindAll();

    Iterable<Cursa> cursaFindAllByCapCilindrica(Integer capCil);
    //
    //
    //Inscriere
    //
    //

    Iterable<Inscriere> inscriereFindAllByParticipant(Integer idParticipant);

    Iterable<Inscriere> inscriereFindAllByCursa(Integer idCursa);

    void inscriereAdd(Integer id, Participant p, Cursa c);

    //
    //
    //Staff
    //
    //

    Staff login(Staff membru,ICurseObserver observer);
    void logout(Staff membru);
    Staff staffFindOne(String username, String password);

    void staffAdd(String username, String password, String nume);
    void newParticipantInscriere(Participant p, Inscriere i);
}
