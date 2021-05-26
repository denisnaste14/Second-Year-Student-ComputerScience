package repository.interfaces;


import curse.model.Participant;

public interface IParticipantRepository extends ICrudRepository<Integer, Participant> {
    /**
     * @param echipa - echipa dupa care se filtreaza participantii
     * @return toti participantii filtrati
     */
    Iterable<Participant> findParticipantibyEchipa(String echipa);

}
