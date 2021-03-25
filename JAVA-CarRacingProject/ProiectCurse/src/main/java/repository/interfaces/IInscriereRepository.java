package repository.interfaces;


import domain.Inscriere;
import repository.ICrudRepository;

public interface IInscriereRepository extends ICrudRepository<Integer, Inscriere> {
    /**
     * @param idParticipant - idParticipant dupa care se filtreaza Inscrierile
     * @return toate Inscrierile filtrate
     */
    Iterable<Inscriere> findInscrieribyIdParticipant(Integer idParticipant);

    /**
     * @param idCursa - dupa care se filtreaza Inscrierile
     * @return toate Inscrierile filtrate
     */
    Iterable<Inscriere> findInscrieribyIdCursa(Integer idCursa);
}
