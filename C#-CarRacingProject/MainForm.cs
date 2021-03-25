using ProiectCSharpVersion.Domain;
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
    partial class MainForm : Form
    {
        Service service = null;
        public MainForm(Service service)
        {
            this.service = service;
            InitializeComponent();
            Refreshh();
        }

        private void Refreshh()
        {
            DataTable tab = new DataTable();
            tab.Columns.Add("CapCilindrica", typeof(int));
            tab.Columns.Add("NrParticipanti", typeof(int));
            foreach (var elem in service.GenerareCapParticipDTOList())
            {
                DataRow row = tab.NewRow();
                row["CapCilindrica"] = elem.CapCilindrica;
                row["NrParticipanti"] = elem.NrParticipanti;
                tab.Rows.Add(row);
            }
            table.DataSource = tab;
        }

        private void Cautare(object sender, MouseEventArgs e)
        {
            string echipaCautare = echipaCautaret.Text;
            if (echipaCautare.Equals(""))
            {
                var eror = MessageBox.Show("Campul pentru echipa nu poate ramane gol!", "Camp gol!",
                MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            Cautare cautareForm = new Cautare(service, echipaCautare);
            cautareForm.Show();
        }

        private void Inscriere(object sender, MouseEventArgs e)
        {
            string capCilindricastring = capCilindricat.Text;
            string nume = numet.Text;
            string echipa = echipat.Text;

            if (capCilindricastring.Equals("") || nume.Equals("") || echipa.Equals(""))
            {
                var eror = MessageBox.Show("Nici unul dintre campuri nu poate ramane gol!", "Campuri goale!",
                  MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            try
            {
                service.ParticipantAdd(0, nume, echipa);
            }
            catch(Exception exc)
            {
                var eror = MessageBox.Show(exc.Message, "Repo Error",
                  MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            int ultimulID = 0;
            foreach( var elem in service.ParticipantGetAllByEchipa(echipa))
            {
                if (elem.Id > ultimulID)
                    ultimulID = elem.Id;
            }
            int capCil = int.Parse(capCilindricastring);
            foreach(var curs in service.CursaGetAllByCapCilindrica(capCil))
            {
                service.InscriereAdd(0, service.ParticipantFindOne(ultimulID), curs);
            }

            var info = MessageBox.Show("Inscrieri adaugate cu succes!", "Succes!", MessageBoxButtons.OK, MessageBoxIcon.Information);
            capCilindricat.Clear();
            numet.Clear();
            echipat.Clear();
        }

        private void Logout(object sender, MouseEventArgs e)
        {
            Close();
        }
    }
}
