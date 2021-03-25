using ProiectCSharpVersion.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Repository.Interfaces
{
    interface ICursaReository:ICrudRepository<int,Cursa>
    {
        //filtrare dupa capCilindrica
        IEnumerable<Cursa> GetCurseByCapCilindrica(int capCilindrica);
    }
}
