package server;

import curse.model.*;
import curse.services.CurseException;
import curse.services.ICurseObserver;
import curse.services.ICurseServices;
import repository.database.CursaDbRepo;
import repository.database.InscriereDbRepo;
import repository.database.ParticipantDbRepo;
import repository.database.StaffDbRepo;
import repository.interfaces.ICursaRepository;
import repository.interfaces.IInscriereRepository;
import repository.interfaces.IParticipantRepository;
import repository.interfaces.IStaffRepository;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements ICurseServices {
    private final IParticipantRepository pdr;
    private final ICursaRepository cdr;
    private final IInscriereRepository idr;
    private final IStaffRepository sdr;
    private Map<String, ICurseObserver> loggedClients=new ConcurrentHashMap<>();
    //interfete
    public Service(ParticipantDbRepo pdr, CursaDbRepo cdr, InscriereDbRepo idr, StaffDbRepo sdr){
        this.pdr=pdr;
        this.cdr=cdr;
        this.idr=idr;
        this.sdr=sdr;
    }
    //
    //
    //Participant
    //
    //
    public Iterable<Participant> participantFindAll(){
        return pdr.findAll();
    }

    public synchronized Iterable<Participant> participantFindAllByEchipa(String echipa){
        return pdr.findParticipantibyEchipa(echipa);
    }

    public synchronized void participantAdd(Integer id,String nume,String echipa){
        Participant p = new Participant(nume,echipa);
        if(id!=null)
            p.setId(id);
        else
            p.setId(-1);
        if(pdr.save(p)==null)
            throw new CurseException("Acest participant exista deja!");
    }

    public synchronized Participant participantFindOne(Integer id){
        Participant p = pdr.findOne(id);
        if(p.getNume().isEmpty())
            throw  new CurseException("Nu exista un participant cu id-ul dat!");
        return p;
    }

    public void participantDelete(Integer id){
        Participant p = pdr.delete(id);
        if(p.getNume().isEmpty())
            throw  new CurseException("Nu exista un participant cu id-ul dat!");
    }

    public void participantUpdate(Integer id, String nume,String echipa){
        Participant p = new Participant(nume, echipa);
        p.setId(id);
        if(pdr.update(p).getNume().isEmpty())
            throw  new CurseException("Nu exista un participant cu id-ul dat!");
    }
    //
    //
    //Cursa
    //
    //
    public synchronized Iterable<Cursa> cursaFindAll(){
        return cdr.findAll();
    }

    public synchronized Iterable<Cursa> cursaFindAllByCapCilindrica(Integer capCil){
        return cdr.findCursebyCapCilindrica(capCil);
    }

    public Cursa cursaFindOne(Integer id){
        Cursa c= cdr.findOne(id);
        if(c==null)
            throw new CurseException("Nu exista o cursa cu id-ul dat!");
        return  c;
    }

    public void cursaAdd(Integer id,Integer capCilindrica){
        Cursa c=new Cursa(capCilindrica);
        if(id!=null)
            c.setId(id);
        else
            c.setId(-1);
        if(cdr.save(c)==null)
            throw new CurseException("Exista deja o cursa cu asest id!");

    }

    public void cursaDelete(Integer id){
        Cursa c = cdr.delete(id);
        if(c==null)
            throw  new CurseException("Nu exista o cursa cu acest id!");
    }

    public void cursaUpdate(Integer id, Integer capCilindrica){
        Cursa c = new Cursa(capCilindrica);
        c.setId(id);
        if(cdr.update(c)==null)
            throw  new CurseException("Nu exista o cursa cu acest id!");
    }
    //
    //
    //Inscriere
    //
    //
    public Iterable<Inscriere> inscriereFindAll(){
        return idr.findAll();
    }

    public synchronized Iterable<Inscriere> inscriereFindAllByParticipant(Integer idParticipant){
        return idr.findInscrieribyIdParticipant(idParticipant);
    }

    public synchronized Iterable<Inscriere> inscriereFindAllByCursa(Integer idCursa){
        return idr.findInscrieribyIdCursa(idCursa);
    }

    public Inscriere inscrireFindOne(Integer idInscriere){
        Inscriere i = idr.findOne(idInscriere);
        if(i==null)
            throw new CurseException("Nu exista o inscriere cu acest id!");
        return i;
    }

    public synchronized void inscriereAdd(Integer id, Participant p, Cursa c){
        Inscriere i = new Inscriere(p,c);
        if(id!=null)
            i.setId(id);
        else
            i.setId(-1);
        if(idr.save(i)==null)
            throw new CurseException("Exista deja o inscriere cu acest id!");
    }

    @Override
    public synchronized Staff login(Staff membru, ICurseObserver observer) {
        Staff s = sdr.findOne(membru.getId());
        if(s !=null){
            if(loggedClients.get(s.getId().getLeft())!=null)
                throw new CurseException("Membru al staffului deja loggat!");
            loggedClients.put(s.getId().getLeft(),observer);
        }
        else
            throw new CurseException("Nu se poate realiza log in-ul!");
        return s;
    }

    @Override
    public synchronized void logout(Staff membru) {
        ICurseObserver localObs=loggedClients.remove(membru.getId().getLeft());
        if(localObs==null)
            throw new CurseException("Acest membru al staffului nu este loggat!");
    }

    public void inscriereDelete(Integer id){
        Inscriere i = idr.delete(id);
        if(i==null)
            throw new CurseException("Nu exista nici o inscriere cu acest id!");
    }

    public void inscriereUpdate(Integer id, Participant p, Cursa c){
        Inscriere i = new Inscriere(p,c);
        i.setId(id);
        if(idr.update(i)==null)
            throw new CurseException("Nu exista nici o inscriere cu acest id!");
    }
    //
    //
    //Staff
    //
    //
    public Iterable<Staff> staffFindAll(){
        return sdr.findAll();
    }

    //LOGIN
    public synchronized Staff staffFindOne(String username,String password){
        Tuple tuple=new Tuple(username,password);
        return sdr.findOne(tuple);
    }

    public synchronized void staffAdd(String username, String password, String nume){
        Staff s= new Staff(nume, username, password);
        if(sdr.save(s)==null)
            throw new CurseException("Exista deja un membru din staff cu acest cont!");
    }

    public void staffDelete(String username, String password){
        Tuple tuple=new Tuple(username,password);
        if(sdr.delete(tuple).getNume().isEmpty())
            throw new CurseException("Nu exista nici un membru din staff cu acest cont!");
    }

    public void staffUpdate(String nume, String username,String password){
        Staff s= new Staff(nume, username, password);
        if(sdr.update(s).getNume().isEmpty())
            throw new CurseException("Nu exista nici un membru din staff cu acest cont!");
    }

    public synchronized void newParticipantInscriere(Participant p, Inscriere i){
        participantAdd(p.getId(),p.getNume(),p.getEchipa());
        inscriereAdd(i.getId(),i.getParticipant(),i.getCursa());
        notifyClients(p,i);
    }

    private final int defaultThreadsNo = 5;
    private void notifyClients(Participant p, Inscriere i) {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for(String clientLogged : loggedClients.keySet()) {
            ICurseObserver client = loggedClients.get(clientLogged);
            if(client!=null)
            {
                executor.execute(() -> {
                    System.out.println("Notifying [" + clientLogged + "]");
                    System.out.println(client);
                    synchronized (client) {
                        try {
                            client.newParticipantInscriereUpdate(p,i);
                        } catch (CurseException|RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Notified");
                });
            }
        }
        executor.shutdown();
    }
}
