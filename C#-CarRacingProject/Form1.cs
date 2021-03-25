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
    partial class Form1 : Form
    {
        Service service = null;
        public Form1(Service servicee)
        {
            this.service = servicee;
            InitializeComponent();
        }

        private void Login(object sender, EventArgs e)
        {
            string username = usernametb.Text;
            string password = passwordt.Text;
            if( username.Equals("") || password.Equals(""))
            {
                var eror = MessageBox.Show("Nici unul dintre campuri nu poate ramen gol!", "Campuri goale!",
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            Domain.Tuple<string, string> sid = new Domain.Tuple<string,string>(username,password);  

            Staff staffMember=service.StaffFindOne(sid);
            if(staffMember == null)
            {
                var eror = MessageBox.Show("Nu exista nici un membru al staffului cu acest cont", "Membru din staff inexistent!",
                  MessageBoxButtons.OK, MessageBoxIcon.Error);
                usernametb.Clear();
                passwordt.Clear();
                return;
            }
            MainForm mf = new MainForm(service);
            mf.Show();
             
        }

        private void Register(object sender, EventArgs e)
        {
            RegisterForm f2 = new RegisterForm(service);
            f2.Show();
        }
    }
}
