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
    partial class RegisterForm : Form
    {
        private Service service;
        public RegisterForm(Service service)
        {
            this.service = service;
            InitializeComponent();
        }

        private void register(object sender, EventArgs e)
        {
            string username = usernamet.Text;
            string password = passwordt.Text;
            string confirmpassword = confirmpasswordt.Text;
            string nume = namet.Text;

            if (username.Equals("") || password.Equals("") || confirmpassword.Equals("") || nume.Equals(""))
            {
                var eror = MessageBox.Show("Nici unul dintre campuri nu poate ramane gol!", "Campuri goale!",
                  MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            if(!password.Equals(confirmpassword))
            {
                var eror = MessageBox.Show("Parolele trebuie sa corespnda!", "Parolele nu corespund",
                 MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            try
            {
                service.StaffAdd(username, password, nume);
                Close();
            }
            catch(Exception ee)
            {
                var eror = MessageBox.Show(ee.Message, "Cont deja existent!",
                MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }
    }
}
