package repository.interfaces;

import domain.Cursa;
import repository.ICrudRepository;

public interface ICursaRepository extends ICrudRepository<Integer, Cursa> {
    /**
     * @param capCilindrica - capacitatea cilindrica dupa care se filtreaza Cursele
     * @return toate cursele filtrate
     */
    Iterable<Cursa> findCursebyCapCilindrica(Integer capCilindrica);
}
