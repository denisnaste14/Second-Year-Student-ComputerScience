using Microsoft.CSharp.RuntimeBinder;
using ProiectCSharpVersion.Domain;
using ProiectCSharpVersion.DTOs;
using ProiectCSharpVersion.Repository;
using ProiectCSharpVersion.Repository.DB;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Servicee
{
    class Service
    {
        private ParticipantDbRepo pdr = null;
        private CursaDbRepo cdr = null;
        private InscriereDbRepo idr = null;
        private StaffDbRepo sdr = null;

        public Service(ParticipantDbRepo pdr, CursaDbRepo cdr, InscriereDbRepo idr, StaffDbRepo sdr)
        {
            this.pdr = pdr;
            this.cdr = cdr;
            this.idr = idr;
            this.sdr = sdr;
        }

        //Participant
        public IEnumerable<Participant> ParticipantFindAll()
        {
            return pdr.FindAll();
        }
        public IEnumerable<Participant> ParticipantGetAllByEchipa(string echipa)
        {
            return pdr.GetParticipantiByEchipa(echipa);
        }
        public Participant ParticipantFindOne(int pid)
        {
            return pdr.FindOne(pid);
        }
        public void ParticipantAdd(int pid, string nume, string echipa)
        {
            Participant p = new Participant(nume, echipa);
            p.Id = pid;
            if (pdr.Save(p) == null)
                throw new Exception("Exista deja un participant cu acest id!");
        }

        //Cursa
        public IEnumerable<Cursa> CursaFindAll()
        {
            return cdr.FindAll();
        }
        public IEnumerable<Cursa> CursaGetAllByCapCilindrica(int capCilindrica)
        {
            return cdr.GetCurseByCapCilindrica(capCilindrica);
        }
        public Cursa CursaFindOne(int cid)
        {
            return cdr.FindOne(cid);
        }
        public void CursaAdd(int cid, int capCilindrica)
        {
            Cursa c = new Cursa(capCilindrica);
            c.Id = cid;
            if (cdr.Save(c) == null)
                throw new Exception("Exista deja o cursa cu acest id!");
        }
        //Inscriere
        public IEnumerable<Inscriere> InscriereFindAll()
        {
            return idr.FindAll();
        }
        public IEnumerable<Inscriere> InscriereGetAllByIdParticipant(int idParticipant)
        {
            return idr.GetInscrieriByIdParticipant(idParticipant);
        }
        public IEnumerable<Inscriere> InscriereGetAllByIdCursa(int idCursa)
        {
            return idr.GetInscrieriByIdCursa(idCursa);
        }
        public Inscriere InscriereFindOne(int iid)
        {
            return idr.FindOne(iid);
        }
        public void InscriereAdd(int iid,Participant p, Cursa c)
        {
            Inscriere i = new Inscriere(p, c);
            i.Id = iid;
            if (idr.Save(i) == null)
                throw new Exception("Exista deja o inscriere cu acest id!");
        }
        //Staff
        public IEnumerable<Staff> StaffFindAll()
        {
            return sdr.FindAll();
        }
        public Staff StaffFindOne(Domain.Tuple<string,string> sid)
        {
            return sdr.FindOne(sid);
        }
        
        public void StaffAdd(string username,string password, string nume)
        {
            Staff staff = new Staff(username, password, nume);
            if (sdr.Save(staff) == null)
                throw new Exception("Exista deja un membru al staffului cu acest cont!");
        }

        //UTILS
        public List<CapacitateParticipanti> GenerareCapParticipDTOList()
        {
            List<CapacitateParticipanti> lista = new List<CapacitateParticipanti>();
            foreach(var c in CursaFindAll())
            {
                int nrParticipanti = InscriereGetAllByIdCursa(c.Id).ToList().Count;
                lista.Add(new CapacitateParticipanti(c.CapCilindrica, nrParticipanti));
            }
            return lista;
        }
        
    }
}
