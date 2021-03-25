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

namespace ProiectCSharpVersion.Repository
{
    class CursaDbRepo : ICursaReository
    {
        private static readonly ILog log = LogManager.GetLogger("CursaDbRepo");
        public CursaDbRepo()
        {
            log.Info("Creating CursaDbRepo");
        }

        public Cursa Delete(int id)
        {
            log.InfoFormat("Entering Delete");
            IDbConnection con = DBUtils.getConnection();
            Cursa c = FindOne(id);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Cursa where cid=@cid";

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@cid";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.InfoFormat("delete exited with value {0}", result);
                    return null;
                }
                log.InfoFormat("delete exited with value {0}", result);
                return c;
            }
        }

        public IEnumerable<Cursa> FindAll()
        {
            log.InfoFormat("Entering findAll");
            IDbConnection con = DBUtils.getConnection();
            IList<Cursa> curse = new List<Cursa>();
            using (var comm = con.CreateCommand())
             {
                 comm.CommandText = "select * from Cursa";
                 using (var dataR = comm.ExecuteReader())
                 {
                     while (dataR.Read())
                     {
                        int cid = dataR.GetInt32(0);
                        int capCilindrica = dataR.GetInt32(1);

                        Cursa c= new Cursa(capCilindrica);
                        c.Id = cid;
                        
                        curse.Add(c);
                     }
                 }
             }
             log.InfoFormat("Exiting findAll with the result from it"); 
            return curse;
        }


        public Cursa FindOne(int id)
        {
            log.InfoFormat("Entering findAll");
            IDbConnection con = DBUtils.getConnection();
            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select cid,capCilindrica from Cursa where cid=@cid";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@cid";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    if(dataR.Read())
                    {
                        int cid = dataR.GetInt32(0);
                        int capCilindrica = dataR.GetInt32(1);
                        Cursa c = new Cursa(capCilindrica);
                        c.Id = cid;
                        log.InfoFormat("Exiting findOne with value {0}", c);
                        return c;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Cursa> GetCurseByCapCilindrica(int capCilindricaa)
        {
            log.InfoFormat("Entering GetCurseByCapCilindrica");
            IDbConnection con = DBUtils.getConnection();
            IList<Cursa> curse = new List<Cursa>();
            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select cid,capCilindrica from Cursa where capCilindrica=@capCilindrica";
                IDbDataParameter paramCap = comm.CreateParameter();
                paramCap.ParameterName = "@capCilindrica";
                paramCap.Value = capCilindricaa;
                comm.Parameters.Add(paramCap);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int cid = dataR.GetInt32(0);
                        int capCilindrica = dataR.GetInt32(1);

                        Cursa c = new Cursa(capCilindrica);
                        c.Id = cid;

                        curse.Add(c);
                    }
                }
            }
            log.InfoFormat("Exiting getCurseByCapCilindrica with the result from it");
            return curse;
        }

        public Cursa Save(Cursa entity)
        {
            log.InfoFormat("Entering Save");
            IDbConnection con = DBUtils.getConnection();
            
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "Insert into Cursa(capCilindrica) values (@capCilindrica)";

                IDbDataParameter paramCap = comm.CreateParameter();
                paramCap.ParameterName = "@capCilindrica";
                paramCap.Value = entity.CapCilindrica;
                comm.Parameters.Add(paramCap);

                var result = comm.ExecuteNonQuery();
                if(result == 0)
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

        public Cursa Update(Cursa entity)
        {
            log.InfoFormat("Entering Update");
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Cursa set capCilindrica=@capCilindrica where cid=@cid";

                IDbDataParameter paramCap = comm.CreateParameter();
                paramCap.ParameterName = "@capCilindrica";
                paramCap.Value = entity.CapCilindrica;
                comm.Parameters.Add(paramCap);

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@cid";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.InfoFormat("update exited with value {0}", result);
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
