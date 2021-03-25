using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Domain
{
    class Participant:Entity<int>
    {
        public string Nume { get; set; }
        public string Echipa { get; set; }
        public Participant(string nume, string echipa)
        {
            this.Nume = nume;
            this.Echipa = echipa;
        }

        public override string ToString()
        {
            return "Participat: nume=" + Nume + " echipa=" + Echipa;
        }
    }
}
