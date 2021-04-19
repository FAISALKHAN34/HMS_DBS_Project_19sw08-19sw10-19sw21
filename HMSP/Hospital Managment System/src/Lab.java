import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Lab implements ActionListener {
	private JFrame frame = new JFrame("Lab");
	private JTabbedPane pane = new JTabbedPane();

	private JPanel Xray = new JPanel();
	private JPanel ultraSound = new JPanel();
	private JPanel Labtest = new JPanel();

	private JLabel[] Xlabels = new JLabel[8];
	private JLabel[] Uslabels = new JLabel[10];
	private JLabel[] Ltlabels = new JLabel[10];

	private JTextField[] XFields = new JTextField[6];
	private JTextField[] UsFields = new JTextField[10];
	private JTextField[] LtFields = new JTextField[10];

	// buttons
	private JButton[] Xbutton = new JButton[2];
	private JButton[] Usbutton = new JButton[2];
	private JButton[] Ltbutton = new JButton[2];

	private JRadioButton[] radiobutton = new JRadioButton[2];
	private ButtonGroup BG = new ButtonGroup();
	private JRadioButton[] radiobutton1 = new JRadioButton[2];
	private ButtonGroup BG1 = new ButtonGroup();
	private JRadioButton[] radiobutton2 = new JRadioButton[2];
	private ButtonGroup BG2 = new ButtonGroup();

	private Font font = new Font("Consolas", Font.BOLD, 15);
	private DefaultListCellRenderer listRenderer1;

	private String[] Xt = { "Types of X-Rays", "Kidney, Ureter, and Bladder X-ray", "Teeth and bones X-rays.",
			"Chest X-rays", "Lungs X-rays", "Abdomen X-rays", " Standard Computed Tomography" };
	private JComboBox Xray_Type = new JComboBox(Xt);
	private String[] Ut = { "types","Pelvic ultrasounds", "Abdominal ultrasound", "Pregnancy ultrasounds",
			"Ultrasound - Carotid", "Sonography - General", "Biopsy-Breast" };
	private JComboBox Us_Type = new JComboBox(Ut);
	private String[] Lt = { "types","Organ function", "Screening ", "Urine", "Skin biopsy", "Blood", "Bone marrow",
			"Nasal or throat swabs", "Wound swabs" };
	private JComboBox Lt_Type = new JComboBox(Lt);

	private LocalDateTime datetime = LocalDateTime.now();
	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	private JTable Ptable = new JTable();
	private JScrollPane sp = new JScrollPane(Ptable);
	DefaultTableModel model = new DefaultTableModel(new String[0][0],
			new String[] { "id", "Name", "father name", "Gender", "Age ", "Phone", "Test Type ", "TimeDate" });
	private JTable Ptable1 = new JTable();
	private JScrollPane sp1 = new JScrollPane(Ptable);
	DefaultTableModel model1 = new DefaultTableModel(new String[0][0],
			new String[] { "id", "Name", "father name", "Gender", "Age ", "Phone", "Test Sound ", "TimeDate" });
	private JTable Ptable2 = new JTable();
	private JScrollPane sp2 = new JScrollPane(Ptable);
	DefaultTableModel model2 = new DefaultTableModel(new String[0][0],
			new String[] { "id", "Name", "father name", "Gender", "Age ", "Phone", "Test Test ", "TimeDate" });
	private int ID=1;

	Lab(int i) {
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(1200, 700);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);

		// setXTable();
		setXray();
		setUltraSound();
		setLab();
		pane.add(Xray, "X-Ray");
		pane.add(ultraSound, "Ultra Sound");
		pane.add(Labtest, "Lab Tests");
		pane.setSize(1200, 500);
		frame.getContentPane().add(pane);
		getData();
        pane.setSelectedIndex(i);
		frame.setVisible(true);
	}

	void setUltraSound() {
		ultraSound.setLayout(null);
		ultraSound.setSize(600, 600);
		for (int j = 0; j < 8; j++) {
			ultraSound.add(Uslabels[j] = new JLabel());
			if (j < 6)
				ultraSound.add(UsFields[j] = new JTextField());

		}
		int i = 0;

		Uslabels[i].setText("Ultra Sound");
		Uslabels[i].setFont(new Font("MV Boli", Font.BOLD, 40));
		Uslabels[i++].setBounds(100, 50, 600, 50);

		Uslabels[i].setText("Patient Name");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 150, 150, 50);

		Uslabels[i].setText("Father Name");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 190, 150, 50);

		Uslabels[i].setText("Gender");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 230, 150, 50);

		Uslabels[i].setText("Age");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 260, 150, 50);

		Uslabels[i].setText("Phone");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 300, 150, 50);

		Uslabels[i].setText("Ultra Sound ");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 340, 150, 50);

		Uslabels[i].setText("Amount");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(190, 380, 150, 50);

		ultraSound.add(radiobutton1[0] = new JRadioButton("Male"));
		ultraSound.add(radiobutton1[1] = new JRadioButton("Female"));

		radiobutton1[0].setBounds(120, 240, 70, 20);
		radiobutton1[1].setBounds(200, 240, 70, 20);
		BG1.add(radiobutton1[0]);
		BG1.add(radiobutton1[1]);

		i = 0;
		UsFields[i].setBounds(120, 163, 150, 20);
		UsFields[i++].setFont(font);

		UsFields[i].setBounds(120, 200, 150, 20);
		UsFields[i++].setFont(font);

		UsFields[i].setBounds(120, 270, 150, 20);
		UsFields[i++].setFont(font);

		UsFields[i].setBounds(120, 310, 150, 20);
		UsFields[i++].setFont(font);

		UsFields[i].setBounds(260, 390, 35, 25);
		UsFields[i].setEditable(true);
		UsFields[i++].setFont(font);

		i = 0;

		Usbutton[i] = new JButton("Submit");
		Usbutton[i].setBounds(30, 420, 80, 25);
		Usbutton[i].addActionListener(this);

		ultraSound.add(Usbutton[i++]);

		Usbutton[i] = new JButton("Clear");
		Usbutton[i].setBounds(140, 420, 80, 25);
		Usbutton[i].addActionListener(this);
		ultraSound.add(Usbutton[i++]);

		listRenderer1 = new DefaultListCellRenderer();
		listRenderer1.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		Us_Type.add(listRenderer1);
		Us_Type.setRenderer(listRenderer1);
		Us_Type.setBounds(100, 350, 210, 25);
		ultraSound.add(Us_Type);
		ultraSound.add(sp1);
		setUTable();
		ultraSound.setVisible(true);

	}
void setLab() {
	Labtest.setLayout(null);
	Labtest.setSize(600, 600);
	for (int j = 0; j < 8; j++) {
		Labtest.add(Ltlabels[j] = new JLabel());
		if (j < 6)
			Labtest.add(LtFields[j] = new JTextField());

	}
	int i = 0;

	Ltlabels[i].setText("Labotary");
	Ltlabels[i].setFont(new Font("MV Boli", Font.BOLD, 40));
	Ltlabels[i++].setBounds(100, 50, 600, 50);

	Ltlabels[i].setText("Patient Name");
	Ltlabels[i].setFont(font);
	Ltlabels[i++].setBounds(10, 150, 150, 50);

	Ltlabels[i].setText("Father Name");
	Ltlabels[i].setFont(font);
	Ltlabels[i++].setBounds(10, 190, 150, 50);

	Ltlabels[i].setText("Gender");
	Ltlabels[i].setFont(font);
	Ltlabels[i++].setBounds(10, 230, 150, 50);

	Ltlabels[i].setText("Age");
	Ltlabels[i].setFont(font);
	Ltlabels[i++].setBounds(10, 260, 150, 50);

	Ltlabels[i].setText("Phone");
	Ltlabels[i].setFont(font);
	Ltlabels[i++].setBounds(10, 300, 150, 50);

	Ltlabels[i].setText("Lab ");
	Ltlabels[i].setFont(font);
	Ltlabels[i++].setBounds(10, 340, 150, 50);

	Ltlabels[i].setText("Amount");
	Ltlabels[i].setFont(font);
	Ltlabels[i++].setBounds(190, 380, 150, 50);

	Labtest.add(radiobutton2[0] = new JRadioButton("Male"));
	Labtest.add(radiobutton2[1] = new JRadioButton("Female"));

	radiobutton2[0].setBounds(120, 240, 70, 20);
	radiobutton2[1].setBounds(200, 240, 70, 20);
	BG2.add(radiobutton2[0]);
	BG2.add(radiobutton2[1]);

	i = 0;
	LtFields[i].setBounds(120, 163, 150, 20);
	LtFields[i++].setFont(font);

	LtFields[i].setBounds(120, 200, 150, 20);
	LtFields[i++].setFont(font);

	LtFields[i].setBounds(120, 270, 150, 20);
	LtFields[i++].setFont(font);

	LtFields[i].setBounds(120, 310, 150, 20);
	LtFields[i++].setFont(font);

	LtFields[i].setBounds(260, 390, 35, 25);
	LtFields[i].setEditable(true);
	LtFields[i++].setFont(font);

	i = 0;

	Ltbutton[i] = new JButton("Submit");
	Ltbutton[i].setBounds(30, 420, 80, 25);
	Ltbutton[i].addActionListener(this);

	Labtest.add(Ltbutton[i++]);

	Ltbutton[i] = new JButton("Clear");
	Ltbutton[i].setBounds(140, 420, 80, 25);
	Ltbutton[i].addActionListener(this);
	Labtest.add(Ltbutton[i++]);

	listRenderer1 = new DefaultListCellRenderer();
	listRenderer1.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
	Lt_Type.add(listRenderer1);
	Lt_Type.setRenderer(listRenderer1);
	Lt_Type.setBounds(100, 350, 210, 25);
	Labtest.add(Lt_Type);
	Labtest.add(sp2);
	setLTable();
	Labtest.setVisible(true);

	
	
	
	
}
	void setXray() {
		Xray.setLayout(null);
		Xray.setSize(600, 600);
		for (int j = 0; j < 8; j++) {
			Xray.add(Xlabels[j] = new JLabel());
			if (j < 6)
				Xray.add(XFields[j] = new JTextField());

		}
		int i = 0;

		Xlabels[i].setText("Xray");
		Xlabels[i].setFont(new Font("MV Boli", Font.BOLD, 40));
		Xlabels[i++].setBounds(100, 50, 600, 50);

		Xlabels[i].setText("Patient Name");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(10, 150, 150, 50);

		Xlabels[i].setText("Father Name");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(10, 190, 150, 50);

		Xlabels[i].setText("Gender");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(10, 230, 150, 50);

		Xlabels[i].setText("Age");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(10, 260, 150, 50);

		Xlabels[i].setText("Phone");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(10, 300, 150, 50);

		Xlabels[i].setText("X-ray Type");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(10, 340, 150, 50);

		Xlabels[i].setText("Amount");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(190, 380, 150, 50);

		Xray.add(radiobutton[0] = new JRadioButton("Male"));
		Xray.add(radiobutton[1] = new JRadioButton("Female"));

		radiobutton[0].setBounds(120, 240, 70, 20);
		radiobutton[1].setBounds(200, 240, 70, 20);
		BG.add(radiobutton[0]);
		BG.add(radiobutton[1]);

		i = 0;
		XFields[i].setBounds(120, 163, 150, 20);
		XFields[i++].setFont(font);

		XFields[i].setBounds(120, 200, 150, 20);
		XFields[i++].setFont(font);

		XFields[i].setBounds(120, 270, 150, 20);
		XFields[i++].setFont(font);

		XFields[i].setBounds(120, 310, 150, 20);
		XFields[i++].setFont(font);

		XFields[i].setBounds(260, 390, 35, 25);
		XFields[i].setEditable(true);
		XFields[i++].setFont(font);

		i = 0;

		Xbutton[i] = new JButton("Submit");
		Xbutton[i].setBounds(30, 420, 80, 25);
		Xbutton[i].addActionListener(this);

		Xray.add(Xbutton[i++]);

		Xbutton[i] = new JButton("Clear");
		Xbutton[i].setBounds(140, 420, 80, 25);
		Xbutton[i].addActionListener(this);
		Xray.add(Xbutton[i++]);

		listRenderer1 = new DefaultListCellRenderer();
		listRenderer1.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		Xray_Type.add(listRenderer1);
		Xray_Type.setRenderer(listRenderer1);
		Xray_Type.setBounds(100, 350, 210, 25);
		Xray.add(Xray_Type);
		Xray.add(sp);
		setXTable();
		Xray.setVisible(true);

	}

	void setXTable() {

		Ptable.setModel(model);
		sp.setBounds(330, 150, 800, 400);
		sp.setViewportView(Ptable);
		JTableHeader header = Ptable.getTableHeader();
		header.setBackground(Color.black);
		header.setForeground(Color.WHITE);
		Ptable.getColumnModel().getColumn(7).setPreferredWidth(100);
	}

	void setUTable() {

		Ptable1.setModel(model1);
		sp1.setBounds(330, 150, 800, 400);
		sp1.setViewportView(Ptable1);
		JTableHeader header = Ptable1.getTableHeader();
		header.setBackground(Color.black);
		header.setForeground(Color.WHITE);
		Ptable1.getColumnModel().getColumn(7).setPreferredWidth(100);
	}

	void setLTable() {

		Ptable2.setModel(model2);
		sp2.setBounds(330, 150, 800, 400);
		sp2.setViewportView(Ptable2);

		JTableHeader header = Ptable2.getTableHeader();
		header.setBackground(Color.black);
		header.setForeground(Color.WHITE);
		Ptable2.getColumnModel().getColumn(7).setPreferredWidth(100);
	}

	private Object[] getData() {
		Object[] data= new Object[20];
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "System", "saadkz07");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from Lab");

			while (rs.next()) {
				int i=0,j=0;
				
			  data= new Object[20];
			  data[j++]=rs.getInt(1);
			  data[j++]=rs.getString(2);
			  data[j++]=rs.getString(3);
			  data[j++]=rs.getString(4);
			  data[j++]=rs.getInt(5);
			  data[j++]=rs.getString(6);
			  data[j++]=rs.getString(7);
			  data[j++]=rs.getString(8);
			  model.addRow(data);
			  model1.addRow(data);
			  model2.addRow(data);
				
				
			}
			if(data[0]!=null)
				ID=(int) data[0]+1;
				
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// Koded by 19sw10
		int b = 0;
		if (Xbutton[b++] == e.getSource()) {
			int j = 0;
			int i = 0;
			String name = XFields[i++].getText(), fathername = XFields[i++].getText(), gender = null,
					age = XFields[i++].getText(), phone = XFields[i++].getText(),
					d = Xray_Type.getSelectedItem().toString();
			if (radiobutton[0].isSelected())
				gender = "male";
			else
				gender = "female";
			if (!(XFields[j++].getText().isEmpty() && !XFields[j++].getText().isEmpty()
					&& !XFields[j++].getText().isEmpty() && !XFields[j++].getText().isEmpty())
					&& (radiobutton[0].isSelected() || radiobutton[1].isSelected())
					&& (Xray_Type.getSelectedIndex() > 0)) {
				datetime = LocalDateTime.now();
				String formattedDate = datetime.format(myFormatObj);

				Object[] data = { ID++, name, fathername, gender, age, phone, d, formattedDate };
				model.addRow(data);
				
				
				try {
					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
							"saadkz07");
					Statement st = con.createStatement();
					PreparedStatement ps = con.prepareStatement(
							"insert into Lab(id,name,fname,gender,age,phone,Test_Type,Time) values(?,?,?,?,?,?,?,?)");
					ps.setInt(1, ID-1);
					ps.setString(2, name);
					ps.setString(3, fathername);
					ps.setString(4, gender);
					ps.setInt(5, Integer.parseInt(age));
					ps.setString(6, phone);
					ps.setString(7,Xt[Xray_Type.getSelectedIndex()]);

					ps.setString(8, formattedDate);
					ps.executeUpdate();
					ps.close();
					JOptionPane.showMessageDialog(null, "SuccessFully Inserted");
				
				}

				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Not insertd i db");
				}

				
				Xbutton[1].doClick();

			} else {
				JOptionPane.showMessageDialog(null, "Please Sleect ");
			}

		}
		if (Xbutton[b++] == e.getSource()) {
			String blank = "";
			int k = 0;
			XFields[k++].setText(blank);
			XFields[k++].setText(blank);
			XFields[k++].setText(blank);
			XFields[k++].setText(blank);
			XFields[k++].setText(blank);
			XFields[k++].setText(blank);
			BG.clearSelection();
			Xray_Type.setSelectedIndex(0);
		}

		int u = 0;
		// us
		if (Usbutton[u++] == e.getSource()) {
			int j = 0;
			int i = 0;
			String name = UsFields[i++].getText(), fathername = UsFields[i++].getText(), gender = null,
					age = UsFields[i++].getText(), phone = UsFields[i++].getText(),
					d = Us_Type.getSelectedItem().toString();
			if (radiobutton1[0].isSelected())
				gender = "male";
			else
				gender = "female";
			if (!(UsFields[j++].getText().isEmpty() && !UsFields[j++].getText().isEmpty()
					&& !UsFields[j++].getText().isEmpty() && !UsFields[j++].getText().isEmpty())
					&& (radiobutton1[0].isSelected() || radiobutton1[1].isSelected())
					&& (Us_Type.getSelectedIndex() > 0)) {
				datetime = LocalDateTime.now();
				String formattedDate = datetime.format(myFormatObj);

				Object[] data = { ID++, name, fathername, gender, age, phone, d, formattedDate };
				model1.addRow(data);
				try {
					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
							"saadkz07");
					Statement st = con.createStatement();
					PreparedStatement ps = con.prepareStatement(
							"insert into Lab(id,name,fname,gender,age,phone,Test_Type,Time) values(?,?,?,?,?,?,?,?)");
					ps.setInt(1, ID-1);
					ps.setString(2, name);
					ps.setString(3, fathername);
					ps.setString(4, gender);
					ps.setInt(5, Integer.parseInt(age));
					ps.setString(6, phone);
					ps.setString(7,Ut[Us_Type.getSelectedIndex()]);
					ps.setString(8, formattedDate);
					ps.executeUpdate();
					ps.close();
					JOptionPane.showMessageDialog(null, "SuccessFully Inserted");
				
				}

				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Not insertd i db");
				}

				
				
				
				
				
				Usbutton[1].doClick();

			} else {
				JOptionPane.showMessageDialog(null, "Please Sleect ");
			}

		}
		if (Usbutton[u++] == e.getSource()) {
			String blank = "";
			int k = 0;
			UsFields[k++].setText(blank);
			UsFields[k++].setText(blank);
			UsFields[k++].setText(blank);
			UsFields[k++].setText(blank);
			UsFields[k++].setText(blank);
			UsFields[k++].setText(blank);
			BG1.clearSelection();
			Xray_Type.setSelectedIndex(0);
		}
   //LT
		int l=0;
		if (Ltbutton[l++] == e.getSource()) {
			int j = 0;
			int i = 0;
			String name = LtFields[i++].getText(), fathername = LtFields[i++].getText(), gender = null,
					age = LtFields[i++].getText(), phone = LtFields[i++].getText(),
					d = Xray_Type.getSelectedItem().toString();
			if (radiobutton2[0].isSelected())
				gender = "male";
			else
				gender = "female";
			if (!(LtFields[j++].getText().isEmpty() && !LtFields[j++].getText().isEmpty()
					&& !LtFields[j++].getText().isEmpty() && !LtFields[j++].getText().isEmpty())
					&& (radiobutton2[0].isSelected() || radiobutton2[1].isSelected())
					&& (Lt_Type.getSelectedIndex() > 0)) {
				datetime = LocalDateTime.now();
				String formattedDate = datetime.format(myFormatObj);
				Object[] data = { ID++, name, fathername, gender, age, phone, d, formattedDate };
				model2.addRow(data);
				
				try {
					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
							"saadkz07");
					PreparedStatement ps = con.prepareStatement(
							"insert into Lab(id,name,fname,gender,age,phone,Test_Type,Time) values(?,?,?,?,?,?,?,?)");
					ps.setInt(1, ID-1);
					ps.setString(2, name);
					ps.setString(3, fathername);
					ps.setString(4, gender);
					ps.setInt(5, Integer.parseInt(age));
					ps.setString(6, phone);
					ps.setString(7,Lt[Lt_Type.getSelectedIndex()]);
					ps.setString(8, formattedDate);
					ps.executeUpdate();
					ps.close();
					JOptionPane.showMessageDialog(null, "SuccessFully Inserted");
				
				}

				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Not insertd i db");
				}

				

				
				
				
				Ltbutton[1].doClick();

			} else {
				JOptionPane.showMessageDialog(null, "Please Sleect ");
			}

		}
		if (Ltbutton[l++] == e.getSource()) {
			String blank = "";
			int k = 0;
			LtFields[k++].setText(blank);
			LtFields[k++].setText(blank);
			LtFields[k++].setText(blank);
			LtFields[k++].setText(blank);
			LtFields[k++].setText(blank);
			LtFields[k++].setText(blank);
			BG2.clearSelection();
			Lt_Type.setSelectedIndex(0);
		}

	}

}
