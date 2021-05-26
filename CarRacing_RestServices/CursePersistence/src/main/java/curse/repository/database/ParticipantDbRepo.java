package curse.repository.database;
import curse.model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import curse.repository.interfaces.IParticipantRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;

public class ParticipantDbRepo implements IParticipantRepository {
    private final Jdbc dbUtils;
    private static final Logger logger= LogManager.getLogger();
    public ParticipantDbRepo(Properties prop){
        logger.info("Initializare ParticipantDbRepo cu proprietatile {}",prop);
        dbUtils=new Jdbc(prop);
    }

    @Override
    public Iterable<Participant> findParticipantibyEchipa(String echipa) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        List<Participant> participanti=new ArrayList<>();
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Participant WHERE echipa=?")){
            prepStat.setString(1,echipa);
            try(ResultSet result=prepStat.executeQuery()){
                while(result.next()){
                    int pid=result.getInt("pid");
                    String nume=result.getString("nume");
                    String echipaa=result.getString("echipa");
                    Participant p =new Participant(nume,echipaa);
                    p.setId(pid);
                    participanti.add(p);
                }
            }
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return participanti;
    }

    @Override
    public Participant findOne(Integer aInt) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Participant p=new Participant("","");
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Participant WHERE pid=?")){
            prepStat.setInt(1,aInt);
            try(ResultSet result=prepStat.executeQuery()){
                    int pid=result.getInt("pid");
                    String nume=result.getString("nume");
                    String echipa=result.getString("echipa");
                    p.setNume(nume);
                    p.setEchipa(echipa);
                    p.setId(pid);
            }
        }catch(SQLException e){
          logger.error(e);
        }
        logger.traceExit();
        return p;
    }

    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        List<Participant> participanti=new ArrayList<>();
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Participant")){
            try(ResultSet result=prepStat.executeQuery()){
                while(result.next()){
                    int pid=result.getInt("pid");
                    String nume=result.getString("nume");
                    String echipa=result.getString("echipa");
                    Participant p =new Participant(nume,echipa);
                    p.setId(pid);
                    participanti.add(p);
                }
            }
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return participanti;
    }

    @Override
    public Participant save(Participant entity) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Participant p=findOne(entity.getId());
        if(!p.getEchipa().isEmpty())
            return null; // am gasit un participant cu acelasi pid
        try(PreparedStatement prepStat=connection.prepareStatement("INSERT INTO Participant (nume,echipa) VALUES (?,?)")){
            prepStat.setString(1,entity.getNume());
            prepStat.setString(2, entity.getEchipa());
            prepStat.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return entity;
    }

    @Override
    public Participant delete(Integer aInt) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Participant p=findOne(aInt);
        if(p.getEchipa().isEmpty())
            return null; // nu am gasit un participant cu pidul dat
        try(PreparedStatement prepStat=connection.prepareStatement("DELETE FROM Participant WHERE pid=?")){
            prepStat.setInt(1,aInt);
            prepStat.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return p;
    }

    @Override
    public Participant update(Participant entity) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Participant p=findOne(entity.getId());
        if(p.getEchipa().isEmpty())
            return null; // nu am gasit un participant cu pidul dat
        try(PreparedStatement prepStat=connection.prepareStatement("UPDATE Participant SET nume=?, echipa=? WHERE pid=?")){
            prepStat.setString(1, entity.getNume());
            prepStat.setString(2, entity.getEchipa());
            prepStat.setInt(3,entity.getId());
            prepStat.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return p;
    }
}
