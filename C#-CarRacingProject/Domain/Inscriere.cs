using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Domain
{
    class Inscriere : Entity<int>
    {
        public Participant Participant{get; set;}
        public Cursa Cursa{ get; set; }

        public Inscriere(Participant participant, Cursa cursa)
        {
            this.Participant = participant;
            this.Cursa = cursa;
        }

        public override string ToString()
        {
            return "Inscriere: " + Participant +"  "+ Cursa;
        }
    }
}
