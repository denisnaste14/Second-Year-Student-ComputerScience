package repository;

import curse.model.Participant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.interfaces.IParticipantRepository;

import javax.persistence.Query;
import java.util.List;

public class ParticipantHibernateRepo implements IParticipantRepository {

    static SessionFactory sessionFactory;
    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public ParticipantHibernateRepo(){
        // A SessionFactory is set up once for an application!
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


    //Select Where echipa
    @Override
    public Iterable<Participant> findParticipantibyEchipa(String echipa) {
        List<Participant> participanti=null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx=session.beginTransaction();

                Query q= session.createQuery("from Participant where echipa= :echipa", Participant.class);
                q.setParameter("echipa",echipa);
                participanti= q.getResultList();
                tx.commit();
            }catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return participanti;
    }

    //Select where pid
    @Override
    public Participant findOne(Integer integer) {
        Participant p=new Participant();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx=session.beginTransaction();

                Query q= session.createQuery("from Participant where pid= :pid", Participant.class);
                q.setParameter("pid",integer);
                List<Participant> part = q.getResultList();

                try{
                    p=part.get(0);
                }catch (Exception exc){
                    return null;
                }
                tx.commit();
            }catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return p;
    }

    //Select
    @Override
    public Iterable<Participant> findAll() {
        List<Participant> participanti=null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx=session.beginTransaction();
                participanti= session.createQuery("from Participant", Participant.class).list();
                tx.commit();
            }catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return participanti;
    }

    //Insert
    @Override
    public Participant save(Participant entity) {
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
    public Participant delete(Integer integer) {
        return null;
    }

    @Override
    public Participant update(Participant entity) {
        return null;
    }
}
