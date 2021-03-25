using ProiectCSharpVersion.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectCSharpVersion.Repository.Interfaces
{
    interface ICrudRepository<ID,E>
    {
        IEnumerable<E> FindAll();
        E FindOne(ID id);
        E Save(E entity);
        E Delete(ID id);
        E Update(E entity);
    }
}
