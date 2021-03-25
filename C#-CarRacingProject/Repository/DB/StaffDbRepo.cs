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
    class StaffDbRepo : IStaffRepository
    {
        private static readonly ILog log = LogManager.GetLogger("CursaDbRepo");
        public StaffDbRepo()
        {
            log.Info("Creating ParticipantDbRepo");
        }
        public Staff Delete(Domain.Tuple<string, string> id)
        {
            log.InfoFormat("Entering Save");
            IDbConnection con = DBUtils.getConnection();
            Staff stafff = FindOne(id);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Staff where username=@username";

                IDbDataParameter paramUser = comm.CreateParameter();
                paramUser.ParameterName = "@username";
                paramUser.Value = id.Elem1;
                comm.Parameters.Add(paramUser);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.InfoFormat("save exited with value {0}", null);
                    return null;
                }
                else
                {
                    log.InfoFormat("save exited with value {0}", result);
                    return stafff;
                }
            }
        }

        public IEnumerable<Staff> FindAll()
        {
            log.InfoFormat("Entering findAll");
            IDbConnection con = DBUtils.getConnection();
            IList<Staff> staffs = new List<Staff>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Staff";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        string username = dataR.GetString(0);
                        string password = dataR.GetString(1);
                        string nume = dataR.GetString(2);

                        Staff s = new Staff(username, password, nume);
                        staffs.Add(s);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return staffs;
        }

        public Staff FindOne(Domain.Tuple<string, string> id)
        {
            log.InfoFormat("Entering findOne");
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select username,password,nume from Staff where username=@username";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@username";
                paramId.Value = id.Elem1;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        string username = dataR.GetString(0);
                        string password = dataR.GetString(1);
                        string nume = dataR.GetString(2);

                        Staff s = new Staff(username, password, nume);
                        log.InfoFormat("Exiting findOne with value {0}", s);
                        return s;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public Staff Save(Staff entity)
        {
            log.InfoFormat("Entering Save");
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "Insert into Staff(username,password,nume) values (@username,@password,@nume)";

                IDbDataParameter paramUser = comm.CreateParameter();
                paramUser.ParameterName = "@username";
                paramUser.Value = entity.Id.Elem1;
                comm.Parameters.Add(paramUser);

                IDbDataParameter paramPass = comm.CreateParameter();
                paramPass.ParameterName = "@password";
                paramPass.Value = entity.Id.Elem2;
                comm.Parameters.Add(paramPass);

                IDbDataParameter paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = entity.Nume;
                comm.Parameters.Add(paramNume);
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

        public Staff Update(Staff entity)
        {
            log.InfoFormat("Entering Save");
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Staff set password=@password, nume=@nume where username=@username";

                IDbDataParameter paramPass = comm.CreateParameter();
                paramPass.ParameterName = "@password";
                paramPass.Value = entity.Id.Elem2;
                comm.Parameters.Add(paramPass);

                IDbDataParameter paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = entity.Nume;
                comm.Parameters.Add(paramNume);

                IDbDataParameter paramUser = comm.CreateParameter();
                paramUser.ParameterName = "@username";
                paramUser.Value = entity.Id.Elem1;
                comm.Parameters.Add(paramUser);

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
    }
}
