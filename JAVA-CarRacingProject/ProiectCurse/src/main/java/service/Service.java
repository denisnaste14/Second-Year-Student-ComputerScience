package service;

import domain.*;
import repository.database.CursaDbRepo;
import repository.database.InscriereDbRepo;
import repository.database.ParticipantDbRepo;
import repository.database.StaffDbRepo;
import repository.exceptions.RepoException;

public class Service {
    private final ParticipantDbRepo pdr;
    private final CursaDbRepo cdr;
    private final InscriereDbRepo idr;
    private final StaffDbRepo sdr;

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

    public Iterable<Participant> participantFindAllByEchipa(String echipa){
        return pdr.findParticipantibyEchipa(echipa);
    }

    public void participantAdd(Integer id,String nume,String echipa){
        Participant p = new Participant(nume,echipa);
        if(id!=null)
            p.setId(id);
        else
            p.setId(-1);
        if(pdr.save(p)==null)
            throw new RepoException("Acest participant exista deja!");
    }

    public Participant participantFindOne(Integer id){
        Participant p = pdr.findOne(id);
        if(p.getNume().isEmpty())
            throw  new RepoException("Nu exista un participant cu id-ul dat!");
        return p;
    }

    public void participantDelete(Integer id){
        Participant p = pdr.delete(id);
        if(p.getNume().isEmpty())
            throw  new RepoException("Nu exista un participant cu id-ul dat!");
    }

    public void participantUpdate(Integer id, String nume,String echipa){
        Participant p = new Participant(nume, echipa);
        p.setId(id);
        if(pdr.update(p).getNume().isEmpty())
            throw  new RepoException("Nu exista un participant cu id-ul dat!");
    }
    //
    //
    //Cursa
    //
    //
    public Iterable<Cursa> cursaFindAll(){
        return cdr.findAll();
    }

    public Iterable<Cursa> cursaFindAllByCapCilindrica(Integer capCil){
        return cdr.findCursebyCapCilindrica(capCil);
    }

    public Cursa cursaFindOne(Integer id){
        Cursa c= cdr.findOne(id);
        if(c==null)
            throw new RepoException("Nu exista o cursa cu id-ul dat!");
        return  c;
    }

    public void cursaAdd(Integer id,Integer capCilindrica){
        Cursa c=new Cursa(capCilindrica);
        if(id!=null)
            c.setId(id);
        else
            c.setId(-1);
        if(cdr.save(c)==null)
            throw new RepoException("Exista deja o cursa cu asest id!");

    }

    public void cursaDelete(Integer id){
        Cursa c = cdr.delete(id);
        if(c==null)
            throw  new RepoException("Nu exista o cursa cu acest id!");
    }

    public void cursaUpdate(Integer id, Integer capCilindrica){
        Cursa c = new Cursa(capCilindrica);
        c.setId(id);
        if(cdr.update(c)==null)
            throw  new RepoException("Nu exista o cursa cu acest id!");
    }
    //
    //
    //Inscriere
    //
    //
    public Iterable<Inscriere> inscriereFindAll(){
        return idr.findAll();
    }

    public Iterable<Inscriere> inscriereFindAllByParticipant(Integer idParticipant){
        return idr.findInscrieribyIdParticipant(idParticipant);
    }

    public Iterable<Inscriere> inscriereFindAllByCursa(Integer idCursa){
        return idr.findInscrieribyIdCursa(idCursa);
    }

    public Inscriere inscrireFindOne(Integer idInscriere){
        Inscriere i = idr.findOne(idInscriere);
        if(i==null)
            throw new RepoException("Nu exista o inscriere cu acest id!");
        return i;
    }

    public void inscriereAdd(Integer id, Participant p, Cursa c){
        Inscriere i = new Inscriere(p,c);
        if(id!=null)
            i.setId(id);
        else
            i.setId(-1);
        if(idr.save(i)==null)
            throw new RepoException("Exista deja o inscriere cu acest id!");
    }

    public void inscriereDelete(Integer id){
        Inscriere i = idr.delete(id);
        if(i==null)
            throw new RepoException("Nu exista nici o inscriere cu acest id!");
    }

    public void inscriereUpdate(Integer id, Participant p, Cursa c){
        Inscriere i = new Inscriere(p,c);
        i.setId(id);
        if(idr.update(i)==null)
            throw new RepoException("Nu exista nici o inscriere cu acest id!");
    }
    //
    //
    //Staff
    //
    //
    public Iterable<Staff> staffFindAll(){
        return sdr.findAll();
    }

    public Staff staffFindOne(String username,String password){
        Tuple tuple=new Tuple(username,password);
        Staff s= sdr.findOne(tuple);
        if(s.getNume().isEmpty())
            throw new RepoException("Nu exista nici un membru din staff cu acest cont!");
        return s;
    }

    public void staffAdd(String username, String password, String nume){
        Staff s= new Staff(nume, username, password);
        if(sdr.save(s)==null)
            throw new RepoException("Exista deja un membru din staff cu acest cont!");
    }

    public void staffDelete(String username, String password){
        Tuple tuple=new Tuple(username,password);
        if(sdr.delete(tuple).getNume().isEmpty())
            throw new RepoException("Nu exista nici un membru din staff cu acest cont!");
    }

    public void staffUpdate(String nume, String username,String password){
        Staff s= new Staff(nume, username, password);
        if(sdr.update(s).getNume().isEmpty())
            throw new RepoException("Nu exista nici un membru din staff cu acest cont!");
    }

}
