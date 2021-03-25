using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Domain
{
    class Cursa:Entity<int>
    {
        public int CapCilindrica { get; set; }
        public Cursa(int capCilindrica)
        {
            this.CapCilindrica = capCilindrica;
        }

        public override string ToString()
        {
            return "Cursa: capCilindrica=" + CapCilindrica;
        }
    }
}
