package repository.database;

import domain.Entity;
import domain.Participant;
import domain.Staff;
import domain.Tuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ICrudRepository;
import utils.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StaffDbRepo implements ICrudRepository<Tuple<String,String>, Staff> {
    private final Jdbc dbUtils;
    private static final Logger logger= LogManager.getLogger();
    public StaffDbRepo(Properties properties){
        logger.info("Initializare ParticipantDbRepo cu proprietatile {}",properties);
        dbUtils=new Jdbc(properties);
    }
    @Override
    public Staff findOne(Tuple<String, String> id) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Staff s=new Staff("","","");
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Staff WHERE username=?")){
            prepStat.setString(1,id.getLeft());
            try(ResultSet result=prepStat.executeQuery()){
                    String username=result.getString("username");
                    String password=result.getString("password");
                    String nume=result.getString("nume");
                    s.setId(new Tuple<>(username,password));
                    s.setNume(nume);
            }
        }catch(SQLException e){
            logger.error(e);
        }
        logger.traceExit();
        return s;
    }

    @Override
    public Iterable<Staff> findAll() {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        List<Staff> staff=new ArrayList<>();
        try(PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM Staff")){
            try(ResultSet result=prepStat.executeQuery()){
                while(result.next()){
                    String username=result.getString("username");
                    String password=result.getString("password");
                    String nume=result.getString("nume");
                    Staff s=new Staff(nume,username,password);
                    staff.add(s);
                }
            }
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return staff;
    }

    @Override
    public Staff save(Staff entity) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Staff s=findOne(entity.getId());
        if(!s.getNume().isEmpty())
            return null;
        try(PreparedStatement prepStat=connection.prepareStatement("INSERT INTO Staff(username,password,nume) VALUES(?,?,?)")){
            prepStat.setString(1,entity.getId().getLeft());
            prepStat.setString(2,entity.getId().getRight());
            prepStat.setString(3, entity.getNume());
            prepStat.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return s;
    }

    @Override
    public Staff delete(Tuple<String, String> id) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Staff s=findOne(id);
        if(s.getNume().isEmpty())
            return null;
        try(PreparedStatement prepStat=connection.prepareStatement("DELETE FROM Staff WHERE username=?")){
            prepStat.setString(1,id.getLeft());
            prepStat.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return s;
    }

    @Override
    public Staff update(Staff entity) {
        logger.traceEntry();
        Connection connection=dbUtils.getConnection();
        Staff s=findOne(entity.getId());
        if(s.getNume().isEmpty())
            return null;
        try(PreparedStatement prepStat=connection.prepareStatement("UPDATE Staff SET nume=? WHERE username=?")){
            prepStat.setString(1, entity.getNume());
            prepStat.setString(2,entity.getId().getLeft());
            prepStat.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.err.println("DB Error"+e);
        }
        logger.traceExit();
        return s;
    }
}
