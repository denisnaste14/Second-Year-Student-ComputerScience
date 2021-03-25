using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Domain
{
    class Tuple<E1, E2>
    {
        public E1 Elem1 { get; set; }
        public E2 Elem2 { get; set; }

        public Tuple(E1 e1, E2 e2)
        {
            this.Elem1 = e1;
            this.Elem2 = e2;
        }

        public override string ToString()
        {
            return Elem1 + "," + Elem2;
        }
    }
}
