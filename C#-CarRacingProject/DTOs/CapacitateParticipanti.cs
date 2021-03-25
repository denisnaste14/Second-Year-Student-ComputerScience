using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.DTOs
{
    class CapacitateParticipanti
    {
        public int CapCilindrica { get; set; }
        public int NrParticipanti { get; set; }

        public CapacitateParticipanti(int cap,int nr)
        {
            this.CapCilindrica = cap;
            this.NrParticipanti = nr;
        }
    }
}
