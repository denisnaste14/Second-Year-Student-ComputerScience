namespace ProiectCSharpVersion
{
    partial class Cautare
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.table = new System.Windows.Forms.DataGridView();
            this.Nume = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.CapCilindrica = new System.Windows.Forms.DataGridViewTextBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.table)).BeginInit();
            this.SuspendLayout();
            // 
            // table
            // 
            this.table.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.table.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.Nume,
            this.CapCilindrica});
            this.table.Location = new System.Drawing.Point(13, 13);
            this.table.Name = "table";
            this.table.Size = new System.Drawing.Size(444, 307);
            this.table.TabIndex = 0;
            // 
            // Nume
            // 
            this.Nume.DataPropertyName = "Nume";
            this.Nume.HeaderText = "Nume";
            this.Nume.MinimumWidth = 50;
            this.Nume.Name = "Nume";
            this.Nume.Width = 200;
            // 
            // CapCilindrica
            // 
            this.CapCilindrica.DataPropertyName = "CapCilindrica";
            this.CapCilindrica.HeaderText = "CapCilindrica";
            this.CapCilindrica.MinimumWidth = 50;
            this.CapCilindrica.Name = "CapCilindrica";
            this.CapCilindrica.Width = 200;
            // 
            // Cautare
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(469, 332);
            this.Controls.Add(this.table);
            this.Name = "Cautare";
            this.Text = "Cautare";
            ((System.ComponentModel.ISupportInitialize)(this.table)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView table;
        private System.Windows.Forms.DataGridViewTextBoxColumn Nume;
        private System.Windows.Forms.DataGridViewTextBoxColumn CapCilindrica;
    }
}