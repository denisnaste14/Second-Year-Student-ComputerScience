using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

namespace WindowsFormApp1.Repository
{
    public static class DBUtils
    {
        private static IDbConnection instance = null;

        public static IDbConnection getConnection()
        {
            if (instance == null || instance.State == System.Data.ConnectionState.Closed)
            {
                instance = getNewConnection();
                Console.WriteLine(instance);
                instance.Open();
            }
            return instance;
        }

        private static IDbConnection getNewConnection()
        {
            return ConnectionUtils.ConnectionFactory.getInstance().createConnection();
        }
    }
}