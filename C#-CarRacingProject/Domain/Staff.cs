using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Domain
{
    class Staff:Entity<Tuple<string,string>>
    {
        public string Username { get; set; }

        public string Password { get; set; }
        public string Nume { get; set; }

        public Staff(string username, string password, string nume)
        {
            this.Nume = nume;
            this.Username = username;
            this.Password = password;
            this.Id = new Tuple<string,string>(this.Username,this.Password);

        }

        public override string ToString()
        {
            return "Staff: nume=" + Nume;
        }
    }
}
