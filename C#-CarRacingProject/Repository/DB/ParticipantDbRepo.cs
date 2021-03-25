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
    class ParticipantDbRepo : IParticipantRepository
    {
        private static readonly ILog log = LogManager.GetLogger("CursaDbRepo");
        public ParticipantDbRepo()
        {
            log.Info("Creating ParticipantDbRepo");
        }

        public Participant Delete(int id)
        {
            log.InfoFormat("Entering delete");
            IDbConnection con = DBUtils.getConnection();
            Participant part = FindOne(id);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Participant where pid=@pid";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@pid";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.InfoFormat("delete exited with value {0}", null);
                    return null;
                }
                else
                {
                    log.InfoFormat("update exited with value {0}", result);
                    return part;
                }
            }
        }

        public IEnumerable<Participant> FindAll()
        {
            log.InfoFormat("Entering findAll");
            IDbConnection con = DBUtils.getConnection();
            IList<Participant> participants = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Participant";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int pid = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        string echipa = dataR.GetString(2);

                        Participant p = new Participant(nume, echipa);
                        p.Id = pid;
                        participants.Add(p);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return participants;
        }

        public Participant FindOne(int id)
        {
            log.InfoFormat("Entering findOne");
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select pid,nume,echipa from Participant where pid=@pid";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@pid";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int pid = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        string echipa = dataR.GetString(2);

                        Participant p = new Participant(nume, echipa);
                        p.Id = pid;
                        log.InfoFormat("Exiting findOne with value {0}", p);
                        return p;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }


        public IEnumerable<Participant> GetParticipantiByEchipa(string echipa)
        {
            log.InfoFormat("Entering GetParticipantiByEchipa");
            IDbConnection con = DBUtils.getConnection();
            IList<Participant> participants = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select pid,nume,echipa from Participant where echipa=@echipa";
                IDbDataParameter paramEchipa = comm.CreateParameter();
                paramEchipa.ParameterName = "@echipa";
                paramEchipa.Value = echipa;
                comm.Parameters.Add(paramEchipa);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int pid = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        string echipaa = dataR.GetString(2);

                        Participant p = new Participant(nume, echipaa);
                        p.Id = pid;
                        participants.Add(p);
                    }
                }
            }
            log.InfoFormat("Exiting GetParticipantiByEchipa");
            return participants;
        }

        public Participant Save(Participant entity)
        {
            log.InfoFormat("Entering Save");
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "Insert into Participant(nume,echipa) values (@nume,@echipa)";

                IDbDataParameter paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = entity.Nume;
                comm.Parameters.Add(paramNume);

                IDbDataParameter paramEchipa = comm.CreateParameter();
                paramEchipa.ParameterName = "@echipa";
                paramEchipa.Value = entity.Echipa;
                comm.Parameters.Add(paramEchipa);

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

        public Participant Update(Participant entity)
        {
            log.InfoFormat("Entering Update");
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Participant set nume=@nume, echipa=@echipa where pid=@pid";

                IDbDataParameter paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = entity.Nume;
                comm.Parameters.Add(paramNume);

                IDbDataParameter paramEchipa = comm.CreateParameter();
                paramEchipa.ParameterName = "@echipa";
                paramEchipa.Value = entity.Echipa;
                comm.Parameters.Add(paramEchipa);

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@pid";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);
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
