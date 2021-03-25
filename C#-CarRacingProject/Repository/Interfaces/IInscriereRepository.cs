using ProiectCSharpVersion.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Repository.Interfaces
{
    interface IInscriereRepository:ICrudRepository<int,Inscriere>
    {
        //filtrare dupa idParticipant
        IEnumerable<Inscriere> GetInscrieriByIdParticipant(int idParticipant);

        //filtrare dupa idCUrsa
        IEnumerable<Inscriere> GetInscrieriByIdCursa(int idCursa);
    }
}
