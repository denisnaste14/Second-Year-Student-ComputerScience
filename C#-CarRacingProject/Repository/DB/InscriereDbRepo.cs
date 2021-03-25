using log4net;
using ProiectCSharpVersion.Domain;
using ProiectCSharpVersion.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WindowsFormApp1.Repository;

namespace ProiectCSharpVersion.Repository.DB
{
    class InscriereDbRepo : IInscriereRepository
    {
        private static readonly ILog log = LogManager.GetLogger("CursaDbRepo");
        public static ParticipantDbRepo pdr;
        private static CursaDbRepo cdr;
        public InscriereDbRepo(ParticipantDbRepo pdrr, CursaDbRepo cdrr)
        {
            log.Info("Creating ParticipantDbRepo");
            pdr = pdrr;
            cdr = cdrr;
        }
        public Inscriere Delete(int id)
        {
            log.InfoFormat("Entering Update");
            IDbConnection con = DBUtils.getConnection();
            Inscriere ins = FindOne(id);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Inscriere where iid=@iid";

                IDbDataParameter paramIid = comm.CreateParameter();
                paramIid.ParameterName = "@iid";
                paramIid.Value = id;
                comm.Parameters.Add(paramIid);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.InfoFormat("update exited with value {0}", null);
                    return null;
                }
                else
                {
                    log.InfoFormat("update exited with value {0}", result);
                    return ins;
                }
            }
        }

        public IEnumerable<Inscriere> FindAll()
        {
            log.InfoFormat("Entering findAll");
            IDbConnection con = DBUtils.getConnection();
            IList<Inscriere> inscrieri = new List<Inscriere>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Inscriere";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int iid = dataR.GetInt32(0);
                        int pid = dataR.GetInt32(1);
                        int cid = dataR.GetInt32(2);
                        Inscriere i = new Inscriere(pdr.FindOne(pid), cdr.FindOne(cid));
                        i.Id = iid;
                        inscrieri.Add(i);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return inscrieri;
        }

        public Inscriere FindOne(int id)
        {
            log.InfoFormat("Entering findOne");
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select iid,pid,cid from Inscriere where iid=@iid";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@iid";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int iid = dataR.GetInt32(0);
                        int pid = dataR.GetInt32(1);
                        int cid = dataR.GetInt32(2);
                        Inscriere i = new Inscriere(pdr.FindOne(pid), cdr.FindOne(cid));
                        i.Id = iid;
                        log.InfoFormat("Exiting findOne with value {0}", i);
                        return i;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Inscriere> GetInscrieriByIdCursa(int idCursa)
        {
            log.InfoFormat("Entering GetInscrieriByIdCursa");
            IDbConnection con = DBUtils.getConnection();
            IList<Inscriere> inscrieri = new List<Inscriere>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select iid,pid,cid from Inscriere where cid=@cid";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@cid";
                paramId.Value = idCursa;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int iid = dataR.GetInt32(0);
                        int pid = dataR.GetInt32(1);
                        int cid = dataR.GetInt32(2);
                        Inscriere i = new Inscriere(pdr.FindOne(pid), cdr.FindOne(cid));
                        i.Id = iid;
                        inscrieri.Add(i);
                    }
                }
            }
            log.InfoFormat("Exiting GetInscrieriByIdCursa");
            return inscrieri;
        }

        public IEnumerable<Inscriere> GetInscrieriByIdParticipant(int idParticipant)
        {
            log.InfoFormat("Entering GetInscrieriByIdParticipant");
            IDbConnection con = DBUtils.getConnection();
            IList<Inscriere> inscrieri = new List<Inscriere>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select iid,pid,cid from Inscriere where pid=@pid";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@pid";
                paramId.Value = idParticipant;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int iid = dataR.GetInt32(0);
                        int pid = dataR.GetInt32(1);
                        int cid = dataR.GetInt32(2);
                        Inscriere i = new Inscriere(pdr.FindOne(pid), cdr.FindOne(cid));
                        i.Id = iid;
                        inscrieri.Add(i);
                    }
                }
            }
            log.InfoFormat("Exiting GetInscrieriByIdParticipant");
            return inscrieri;
        }

        public Inscriere Save(Inscriere entity)
        {
            log.InfoFormat("Entering Save");
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "Insert into Inscriere(pid,cid) values (@pid,@cid)";

                IDbDataParameter paramPid = comm.CreateParameter();
                paramPid.ParameterName = "@pid";
                paramPid.Value = entity.Participant.Id;
                comm.Parameters.Add(paramPid);

                IDbDataParameter paramCid = comm.CreateParameter();
                paramCid.ParameterName = "@cid";
                paramCid.Value = entity.Cursa.Id;
                comm.Parameters.Add(paramCid);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.InfoFormat("save exited with value {0}", null);
                    return null;
                }
                else
                {
                    log.InfoFormat("save exited with value {0}", result);
                    return entity;
                }
            }
        }

        public Inscriere Update(Inscriere entity)
        {
            log.InfoFormat("Entering Update");
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Inscriere set pid=@pid, cid=@cid where iid=@iid";

                IDbDataParameter paramPid = comm.CreateParameter();
                paramPid.ParameterName = "@pid";
                paramPid.Value = entity.Participant.Id;
                comm.Parameters.Add(paramPid);

                IDbDataParameter paramCid = comm.CreateParameter();
                paramCid.ParameterName = "@cid";
                paramCid.Value = entity.Cursa.Id;
                comm.Parameters.Add(paramCid);

                IDbDataParameter paramIid = comm.CreateParameter();
                paramIid.ParameterName = "@iid";
                paramIid.Value = entity.Id;
                comm.Parameters.Add(paramIid);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.InfoFormat("update exited with value {0}", null);
                    return null;
                }
                else
                {
                    log.InfoFormat("update exited with value {0}", result);
                    return entity;
                }
            }
        }
    }
}
