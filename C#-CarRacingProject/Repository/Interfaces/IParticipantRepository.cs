using ProiectCSharpVersion.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Repository.Interfaces
{
    interface IParticipantRepository:ICrudRepository<int,Participant>
    {
        //filtrare dupa echipa
        IEnumerable<Participant> GetParticipantiByEchipa(string echipa);
    }
}
