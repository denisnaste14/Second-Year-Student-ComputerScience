package repository;

import curse.model.Participant;
import curse.model.Staff;
import curse.model.Tuple;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.interfaces.IStaffRepository;

import javax.persistence.Query;
import java.util.List;

public class StaffHibernateRepo implements IStaffRepository {
    static SessionFactory sessionFactory;

    public StaffHibernateRepo(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()// configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
    @Override
    public Staff findOne(Tuple<String, String> stringStringTuple) {
        Staff s= new Staff();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx=session.beginTransaction();
                Query q= session.createQuery("FROM Staff where username= :username", Staff.class);
                q.setParameter("username",stringStringTuple.getLeft());
                List<Staff> part = q.getResultList();
                s=part.get(0);
                tx.commit();
                /*
                tx = session.beginTransaction();
                s = session.get(curse.model.Staff.class, stringStringTuple.getLeft());
                tx.commit();*/

            } catch (RuntimeException ex) {

                ex.printStackTrace();
                if (tx != null)
                    tx.rollback();
            }
        }
        return s;
    }

    @Override
    public Iterable<Staff> findAll() {
        return null;
    }

    @Override
    public Staff save(Staff entity) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx=session.beginTransaction();
                session.save(entity);
                tx.commit();
            }catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return entity;
    }

    @Override
    public Staff delete(Tuple<String, String> stringStringTuple) {
        return null;
    }

    @Override
    public Staff update(Staff entity) {
        return null;
    }
}
