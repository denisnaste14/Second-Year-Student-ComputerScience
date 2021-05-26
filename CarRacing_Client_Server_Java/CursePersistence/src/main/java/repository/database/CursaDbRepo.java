package repository.database;

import curse.model.Cursa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.ICursaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CursaDbRepo implements ICursaRepository {

    private final Jdbc dbUtils;
    private static final Logger logger= LogManager.getLogger();
    public CursaDbRepo(Properties properties){
        logger.info("Initializare CursaDbRepo cu proprietatile {}",properties);
        dbUtils=new Jdbc(properties);
    }
    @Override
    public Iterable<Cursa> findCursebyCapCilindrica(Integer capCilindrica) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        List<Cursa> curse=new ArrayList<>();
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Cursa WHERE capCilindrica=?")){
            prepStat.setInt(1,capCilindrica);
            try(ResultSet result=prepStat.executeQuery()){
                while(result.next()){
                    int cid=result.getInt("cid");
                    int capCil=result.getInt("capCilindrica");
                    Cursa c=new Cursa(capCil);
                    c.setId(cid);
                    curse.add(c);
                }
            }
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return curse;
    }

    @Override
    public Cursa findOne(Integer integer) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Cursa c=new Cursa(-1);
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Cursa WHERE cid=?")){
            prepStat.setInt(1,integer);
            try(ResultSet result=prepStat.executeQuery()){
                    int cid=result.getInt("cid");
                    int capCil=result.getInt("capCilindrica");
                    c.setCapCilindrica(capCil);
                    c.setId(cid);
            }
        }catch(SQLException e){
            logger.error(e);
        }
        logger.traceExit();
        if(c.getCapCilindrica()!=-1)
            return c;
        else
            return null;
    }

    @Override
    public Iterable<Cursa> findAll() {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        List<Cursa> curse=new ArrayList<>();
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Cursa")){
            try(ResultSet result=prepStat.executeQuery()){
                while(result.next()){
                    int cid=result.getInt("cid");
                    int capCil=result.getInt("capCilindrica");
                    Cursa c=new Cursa(capCil);
                    c.setId(cid);
                    curse.add(c);
                }
            }
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return curse;
    }

    @Override
    public Cursa save(Cursa entity) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Cursa c=findOne(entity.getId());
        if(c!=null)
            return null;
        else{
            try(PreparedStatement prepStatm = connection.prepareStatement("INSERT INTO Cursa(capCilindrica) VALUES (?)")){
                prepStatm.setInt(1,entity.getCapCilindrica());
                prepStatm.executeUpdate();
            }catch (SQLException e){
                logger.error(e);
                System.err.println("DB Error"+e);
            }
        }
        logger.traceExit();
        return entity;
    }

    @Override
    public Cursa delete(Integer integer) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Cursa c=findOne(integer);
        if(c==null)
            return null;
        else{
            try(PreparedStatement prepStatm = connection.prepareStatement("DELETE FROM Cursa WHERE cid=?")){
                prepStatm.setInt(1,integer);
                prepStatm.executeUpdate();
            }catch (SQLException e){
                logger.error(e);
                System.err.println("DB Error"+e);
            }
        }
        logger.traceExit();
        return c;
    }

    @Override
    public Cursa update(Cursa entity) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Cursa c=findOne(entity.getId());
        if(c==null)
            return null;
        else{
            try(PreparedStatement prepStatm = connection.prepareStatement("UPDATE Cursa SET capCilindrica=? WHERE cid=?")){
                prepStatm.setInt(1,entity.getCapCilindrica());
                prepStatm.setInt(2,entity.getId());
                prepStatm.executeUpdate();
            }catch (SQLException e){
                logger.error(e);
                System.err.println("DB Error"+e);
            }
        }
        logger.traceExit();
        return entity;
    }
}
