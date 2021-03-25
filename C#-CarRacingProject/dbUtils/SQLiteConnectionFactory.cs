using System;
using System.Configuration;
using System.Data;
using System.Data.SQLite;

//using Mono.Data.Sqlite;
namespace ConnectionUtils
{
    public class SqliteConnectionFactory : ConnectionFactory
    {
        public override IDbConnection createConnection()
        {
            // Windows Sqlite Connection, fisierul .db ar trebuie sa fie in directorul debug/bin
            var props = ConfigurationManager.ConnectionStrings["db"].ConnectionString;
            return new SQLiteConnection(props);
        }
    }
}