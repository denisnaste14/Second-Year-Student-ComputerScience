using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SQLite;
using ProiectCSharpVersion.Repository;
using ProiectCSharpVersion.Domain;
using ProiectCSharpVersion.Repository.DB;
using ProiectCSharpVersion.Servicee;

namespace ProiectCSharpVersion
{
    static class Program
    {
        [STAThread]
        static void Main()
        {
            ParticipantDbRepo pdr = new ParticipantDbRepo();
            CursaDbRepo cdr = new CursaDbRepo();
            InscriereDbRepo idr = new InscriereDbRepo(pdr, cdr);
            StaffDbRepo sdr = new StaffDbRepo();
            Service service = new Service(pdr,cdr,idr,sdr);
            

             Application.EnableVisualStyles();
             Application.SetCompatibleTextRenderingDefault(false);
             Application.Run(new Form1(service));

        }
    }
}
