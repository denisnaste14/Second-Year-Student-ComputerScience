package curse.repository.database;

import curse.model.Inscriere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import curse.repository.interfaces.IInscriereRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;

public class InscriereDbRepo implements IInscriereRepository {
    private final Jdbc dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private final ParticipantDbRepo participantDbRepo;
    private final CursaDbRepo cursaDbRepo;
    public InscriereDbRepo(Properties properties, ParticipantDbRepo participantDbRepo, CursaDbRepo cursaDbRepo){
        logger.info("Initializare InscriereDbRepo cu proprietatile {}",properties);
        dbUtils=new Jdbc(properties);
        this.participantDbRepo=participantDbRepo;
        this.cursaDbRepo=cursaDbRepo;
    }

    @Override
    public Iterable<Inscriere> findInscrieribyIdParticipant(Integer idParticipant) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        List<Inscriere> inscrieri=new ArrayList<>();
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Inscriere WHERE pid=?")){
            prepStat.setInt(1,idParticipant);
            try(ResultSet result=prepStat.executeQuery()){
                while(result.next()){
                    int iid=result.getInt("iid");
                    int pid=result.getInt("pid");
                    int cid=result.getInt("cid");
                    Inscriere i= new Inscriere(participantDbRepo.findOne(pid),cursaDbRepo.findOne(cid));
                    i.setId(iid);
                    inscrieri.add(i);
                }
            }
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        return inscrieri;
    }

    @Override
    public Iterable<Inscriere> findInscrieribyIdCursa(Integer idCursa) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        List<Inscriere> inscrieri=new ArrayList<>();
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Inscriere WHERE cid=?")){
            prepStat.setInt(1,idCursa);
            try(ResultSet result=prepStat.executeQuery()){
                while(result.next()){
                    int iid=result.getInt("iid");
                    int pid=result.getInt("pid");
                    int cid=result.getInt("cid");
                    Inscriere i= new Inscriere(participantDbRepo.findOne(pid),cursaDbRepo.findOne(cid));
                    i.setId(iid);
                    inscrieri.add(i);
                }
            }
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        return inscrieri;
    }

    @Override
    public Inscriere findOne(Integer integer) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Inscriere i =new Inscriere(null,null);
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Inscriere WHERE iid=?")){
            prepStat.setInt(1,integer);
            try(ResultSet result=prepStat.executeQuery()){
                int iid=result.getInt("iid");
                int pid=result.getInt("pid");
                int cid=result.getInt("cid");
                i.setParticipant(participantDbRepo.findOne(pid));
                i.setCursa(cursaDbRepo.findOne(cid));
                i.setId(iid);
            }
        }catch(SQLException e){
            logger.error(e);
        }
        if(i.getCursa()==null)
            return null;
        else
            return i;
    }

    @Override
    public Iterable<Inscriere> findAll() {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        List<Inscriere> inscrieri=new ArrayList<>();
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Inscriere")){
            try(ResultSet result=prepStat.executeQuery()){
                while(result.next()){
                    int iid=result.getInt("iid");
                    int pid=result.getInt("pid");
                    int cid=result.getInt("cid");
                    Inscriere i= new Inscriere(participantDbRepo.findOne(pid),cursaDbRepo.findOne(cid));
                    i.setId(iid);
                    inscrieri.add(i);
                }
            }
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        return inscrieri;
    }

    @Override
    public Inscriere save(Inscriere entity) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Inscriere i =findOne(entity.getId());
        if(i!=null)
            return null;
        try(PreparedStatement prepStat=connection.prepareStatement("INSERT INTO Inscriere(pid,cid) VALUES (?,?)")){
            prepStat.setInt(1,entity.getParticipant().getId());
            prepStat.setInt(2,entity.getCursa().getId());
            prepStat.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return entity;
    }

    @Override
    public Inscriere delete(Integer integer) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Inscriere i =findOne(integer);
        if(i==null)
            return null;
        try(PreparedStatement prepStat=connection.prepareStatement("DELETE FROM Inscriere WHERE iid=?")){
            prepStat.setInt(1,integer);
            prepStat.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return i;
    }

    @Override
    public Inscriere update(Inscriere entity) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Inscriere i =findOne(entity.getId());
        if(i==null)
            return null;
        try(PreparedStatement prepStat=connection.prepareStatement("UPDATE Inscriere SET pid=?, cid=? WHERE iid=?")){
            prepStat.setInt(1,entity.getParticipant().getId());
            prepStat.setInt(2,entity.getCursa().getId());
            prepStat.setInt(3,entity.getId());
            prepStat.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return entity;
    }
}
