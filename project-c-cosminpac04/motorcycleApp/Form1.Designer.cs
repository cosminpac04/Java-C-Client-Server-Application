namespace motorcycleApp;

partial class Form1
{
    /// <summary>
    ///  Required designer variable.
    /// </summary>
    private System.ComponentModel.IContainer components = null;

    /// <summary>
    ///  Clean up any resources being used.
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
        components = new System.ComponentModel.Container();
        btnAddParticipant = new System.Windows.Forms.Button();
        dataGridView1 = new System.Windows.Forms.DataGridView();
        txtNam = new System.Windows.Forms.TextBox();
        txtEngineCap = new System.Windows.Forms.TextBox();
        btnDeleteParticipant = new System.Windows.Forms.Button();
        TestRaceApiButton = new System.Windows.Forms.Button();
        contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(components);
        TxtCapacity = new System.Windows.Forms.TextBox();
        ((System.ComponentModel.ISupportInitialize)dataGridView1).BeginInit();
        SuspendLayout();
        // 
        // btnAddParticipant
        // 
        btnAddParticipant.Location = new System.Drawing.Point(441, 353);
        btnAddParticipant.Name = "btnAddParticipant";
        btnAddParticipant.Size = new System.Drawing.Size(116, 35);
        btnAddParticipant.TabIndex = 0;
        btnAddParticipant.Text = "Add";
        btnAddParticipant.UseVisualStyleBackColor = true;
        btnAddParticipant.Click += btnAddParticipant_Click;
        // 
        // dataGridView1
        // 
        dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
        dataGridView1.Location = new System.Drawing.Point(52, 41);
        dataGridView1.Name = "dataGridView1";
        dataGridView1.RowHeadersWidth = 51;
        dataGridView1.Size = new System.Drawing.Size(628, 294);
        dataGridView1.TabIndex = 1;
        dataGridView1.Text = "dataGridView1";
        // 
        // txtNam
        // 
        txtNam.Location = new System.Drawing.Point(52, 361);
        txtNam.Name = "txtNam";
        txtNam.PlaceholderText = "Name";
        txtNam.Size = new System.Drawing.Size(110, 27);
        txtNam.TabIndex = 2;
        // 
        // txtEngineCap
        // 
        txtEngineCap.Location = new System.Drawing.Point(168, 361);
        txtEngineCap.Name = "txtEngineCap";
        txtEngineCap.PlaceholderText = "Engine Capacity";
        txtEngineCap.Size = new System.Drawing.Size(111, 27);
        txtEngineCap.TabIndex = 3;
        // 
        // btnDeleteParticipant
        // 
        btnDeleteParticipant.Location = new System.Drawing.Point(575, 353);
        btnDeleteParticipant.Name = "btnDeleteParticipant";
        btnDeleteParticipant.Size = new System.Drawing.Size(105, 35);
        btnDeleteParticipant.TabIndex = 4;
        btnDeleteParticipant.Text = "Delete";
        btnDeleteParticipant.UseVisualStyleBackColor = true;
        btnDeleteParticipant.Click += btnDeleteParticipant_Click;
        // 
        // TestRaceApiButton
        // 
        TestRaceApiButton.Location = new System.Drawing.Point(441, 394);
        TestRaceApiButton.Name = "TestRaceApiButton";
        TestRaceApiButton.Size = new System.Drawing.Size(115, 40);
        TestRaceApiButton.TabIndex = 5;
        TestRaceApiButton.Text = "TestRace";
        TestRaceApiButton.UseVisualStyleBackColor = true;
        TestRaceApiButton.Click += TestRaceApiButton_Click;
        // 
        // contextMenuStrip1
        // 
        contextMenuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
        contextMenuStrip1.Name = "contextMenuStrip1";
        contextMenuStrip1.Size = new System.Drawing.Size(61, 4);
        // 
        // TxtCapacity
        // 
        TxtCapacity.Location = new System.Drawing.Point(51, 405);
        TxtCapacity.Name = "TxtCapacity";
        TxtCapacity.PlaceholderText = "Race Capacity";
        TxtCapacity.Size = new System.Drawing.Size(110, 27);
        TxtCapacity.TabIndex = 7;
        // 
        // Form1
        // 
        AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
        AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        ClientSize = new System.Drawing.Size(800, 450);
        Controls.Add(TxtCapacity);
        Controls.Add(TestRaceApiButton);
        Controls.Add(btnDeleteParticipant);
        Controls.Add(txtEngineCap);
        Controls.Add(txtNam);
        Controls.Add(dataGridView1);
        Controls.Add(btnAddParticipant);
        StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        Text = "Motorcycle";
        Load += Form1_Load;
        ((System.ComponentModel.ISupportInitialize)dataGridView1).EndInit();
        ResumeLayout(false);
        PerformLayout();
    }

    private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
    private System.Windows.Forms.TextBox TxtCapacity;

    private System.Windows.Forms.Button TestRaceApiButton;

    private System.Windows.Forms.Button btnDeleteParticipant;
    private System.Windows.Forms.Button btnAddParticipant;
    private System.Windows.Forms.DataGridView dataGridView1;
    private System.Windows.Forms.TextBox txtNam;
    private System.Windows.Forms.TextBox txtEngineCap;

    #endregion
}