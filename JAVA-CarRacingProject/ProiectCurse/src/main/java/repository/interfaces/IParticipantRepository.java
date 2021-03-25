package repository.interfaces;

import domain.Participant;
import repository.ICrudRepository;

public interface IParticipantRepository extends ICrudRepository<Integer, Participant> {
    /**
     * @param echipa - echipa dupa care se filtreaza participantii
     * @return toti participantii filtrati
     */
    Iterable<Participant> findParticipantibyEchipa(String echipa);

}
