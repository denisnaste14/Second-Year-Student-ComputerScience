using ProiectCSharpVersion.Servicee;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ProiectCSharpVersion
{
    partial class Cautare : Form
    {
        private Service service = null;
        private string echipa;
        public Cautare(Service service,string echipa)
        {
            this.service = service;
            this.echipa = echipa;
            InitializeComponent();
            IncarcareDate();
        }

        private void IncarcareDate()
        {
            DataTable tab = new DataTable();
            tab.Columns.Add("Nume", typeof(string)); 
            tab.Columns.Add("CapCilindrica", typeof(int));
            foreach(var elem in service.ParticipantGetAllByEchipa(echipa))
            {
                string numele = elem.Nume;
                foreach(var insc in service.InscriereGetAllByIdParticipant(elem.Id))
                {
                    DataRow row = tab.NewRow();
                    row["Nume"] = numele;
                    row["CapCilindrica"] = insc.Cursa.CapCilindrica;
                    tab.Rows.Add(row);
                }
            }
            table.DataSource = tab;
        }
    }
}
