import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import static java.util.Objects.isNull;

public class Home implements ActionListener {

	private JFrame frame = new JFrame("19SW08 ,19SW10 & 19SW21 Projects");
	private JPanel Reg_panel = new JPanel();
	private JLabel[] Flabels = new JLabel[8];
	private JTextField[] TextFields = new JTextField[10];
	private JTextField[] TFields = new JTextField[6];
	private JButton[] FButtons = new JButton[2];
	private JRadioButton[] radiobutton = new JRadioButton[2];
	private ButtonGroup Buttong = new ButtonGroup();

	private LineBorder B = new LineBorder(Color.black);
	private int size = 20;
	private int Record = 1;
	private JTable Ptable = new JTable();
	private JScrollPane sp = new JScrollPane(Ptable);
	DefaultTableModel model = new DefaultTableModel(new String[0][0],
			new String[] { "id", "Name", "father name", "Gender", "Age ", "Phone", "Dr Name ", "TimeDate" });

	private String doc_Name[] = new String[20]; /* = { "Doctors", "Dr strange", "Dr fate", "Dr doom", }; */
	private int doc_Fee[] = new int[20]; /* = { "Doctors", "Dr strange", "Dr fate", "Dr doom", }; */

	private JComboBox<String> doc;
	private LocalDateTime datetime = LocalDateTime.now();
	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	private DefaultListCellRenderer listRenderer1;
	private JList list = new JList(doc_Name);

	private JMenu[] menu = new JMenu[4];
	private JMenuBar menubar = new JMenuBar();
	private JMenuItem[] items = new JMenuItem[20];

	private Font font = new Font("Consolas", Font.BOLD, 15);
	private int p_id;
	private String Database_Name = new String();
	private String Database_Pass = new String();
	private JTextField Search = new JTextField();
	private JLabel LSearch = new JLabel("Search");

	Home() {
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1200, 800));
		frame.pack();
		frame.setLocationRelativeTo(null);
		setBar();
		frame.setJMenuBar(menubar);
		setForm();
		frame.add(Reg_panel);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		centerRenderer.setHorizontalTextPosition(DefaultTableCellRenderer.CENTER);
		setTable();
		Ptable.setDefaultRenderer(String.class, centerRenderer);
		frame.getContentPane().add(sp);
		Search.setBounds(400, 90, 110, 30);
		LSearch.setBounds(330, 90, 110, 30);
		Search.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// Koded by 19sw10

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// Koded by 19sw10

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// Koded by 19sw10
				String k=Search.getText().toLowerCase();
				filter(k);

			}

		});
		frame.add(LSearch);
		frame.add(Search);

		frame.setVisible(true);

	}

	void setBar() {
		int i = 0;
		menu[i++] = new JMenu("File");
		menu[i++] = new JMenu("Lab");
		menu[i++] = new JMenu("Reports");
		menu[i++] = new JMenu("Help");

		i = 0;

		items[i++] = new JMenuItem("New");
		items[i++] = new JMenuItem("Edit");
		items[i++] = new JMenuItem("Close");

		items[i++] = new JMenuItem("X RAY");
		items[i++] = new JMenuItem("UltaSound");
		items[i++] = new JMenuItem("Lab Tests");

		items[i++] = new JMenuItem("X-RAY");
		items[i++] = new JMenuItem("UltaSounds");
		items[i++] = new JMenuItem("Lab Tests");

		items[i++] = new JMenuItem("Developer");
		items[i++] = new JMenuItem("Contact us");

		i = 0;
		int j = 0;
		menu[i].add(items[j++]);
		menu[i].add(items[j++]);
		menu[i++].add(items[j++]);

		menu[i].add(items[j++]);
		menu[i].add(items[j++]);
		menu[i++].add(items[j++]);

		menu[i].add(items[j++]);
		menu[i].add(items[j++]);
		menu[i++].add(items[j++]);

		menu[i].add(items[j++]);
		menu[i].add(items[j++]);

		// set listener
		// for (int k = 0; k <12; k++)
		// items[k].addActionListener(this);

		for (int h = 0; h < 4; h++)
			menubar.add(menu[h]);

		for (int a = 0; a < 10; a++)
			items[a].addActionListener(this);

	}

	private Object[] getData() {
		Object[] data = new Object[20];
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "System", "saadkz07");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from Patient");

			while (rs.next()) {
				int i = 0, j = 0;

				data = new Object[20];
				data[j++] = rs.getInt(1);
				data[j++] = rs.getString(2);
				data[j++] = rs.getString(3);
				data[j++] = rs.getString(4);
				data[j++] = rs.getInt(5);
				data[j++] = rs.getString(6);
				data[j++] = (doc.getItemAt(rs.getInt(8)));
				data[j++] = rs.getString(7);
				model.addRow(data);

			}
			if (data[0] != null)
				p_id = (int) data[0];

			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	void setForm() {
		Reg_panel.setLayout(null);
		Reg_panel.setBounds(10, 100, 300, 450);
		Reg_panel.setBorder(B);
		radiobutton[0] = new JRadioButton("male");
		radiobutton[1] = new JRadioButton("Female");
		Buttong.add(radiobutton[0]);
		Buttong.add(radiobutton[1]);

		{
			int i = 0;
			for (int k = 0; k < 8; k++) {
				Flabels[k] = new JLabel();
				if (k < 6) {
					TFields[k] = new JTextField();
					Reg_panel.add(TFields[k]);
				}
				Reg_panel.add(Flabels[k]);

			}

			Flabels[i].setText("OPD");
			Flabels[i].setFont(new Font("MV Boli", Font.BOLD, 40));
			Flabels[i++].setBounds(100, 50, 600, 50);

			Flabels[i].setText("Patient Name");
			Flabels[i].setFont(font);
			Flabels[i++].setBounds(10, 150, 150, 50);

			Flabels[i].setText("Father Name");
			Flabels[i].setFont(font);
			Flabels[i++].setBounds(10, 190, 150, 50);

			Flabels[i].setText("Gender");
			Flabels[i].setFont(font);
			Flabels[i++].setBounds(10, 230, 150, 50);

			Flabels[i].setText("Age");
			Flabels[i].setFont(font);
			Flabels[i++].setBounds(10, 260, 150, 50);

			Flabels[i].setText("Phone");
			Flabels[i].setFont(font);
			Flabels[i++].setBounds(10, 300, 150, 50);

			Flabels[i].setText("Doctor");
			Flabels[i].setFont(font);
			Flabels[i++].setBounds(10, 340, 150, 50);

			Flabels[i].setText("Fee");
			Flabels[i].setFont(font);
			Flabels[i++].setBounds(230, 340, 150, 50);
			doc = new JComboBox<String>(setDoc());
			listRenderer1 = new DefaultListCellRenderer();
			listRenderer1.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
			doc.add(listRenderer1);
			doc.setRenderer(listRenderer1);
			doc.addActionListener(this);

			Reg_panel.add(doc);
			doc.setBounds(120, 350, 100, 25);

			Reg_panel.add(radiobutton[0]);
			Reg_panel.add(radiobutton[1]);

			radiobutton[0].setBounds(120, 240, 70, 20);
			radiobutton[1].setBounds(200, 240, 70, 20);

			i = 0;

			TFields[i].setBounds(120, 163, 150, 20);
			TFields[i++].setFont(font);

			TFields[i].setBounds(120, 200, 150, 20);
			TFields[i++].setFont(font);

			TFields[i].setBounds(120, 270, 150, 20);
			TFields[i++].setFont(font);

			TFields[i].setBounds(120, 310, 150, 20);
			TFields[i++].setFont(font);

			TFields[i].setBounds(260, 350, 35, 25);
			TFields[i].setEditable(true);
			TFields[i++].setFont(font);

			i = 0;

			FButtons[i] = new JButton("Submit");
			FButtons[i].setBounds(30, 410, 80, 25);
			FButtons[i].addActionListener(this);

			Reg_panel.add(FButtons[i++]);

			FButtons[i] = new JButton("Clear");
			FButtons[i].setBounds(140, 410, 80, 25);
			FButtons[i].addActionListener(this);
			Reg_panel.add(FButtons[i++]);
			System.out.println(getData());

		}

		Reg_panel.setVisible(true);
	}

	private String[] setDoc() {

		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "saadkz07");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select name,fee from doctor");
			int i = 0;
			doc_Name[i++] = "Doctors";
			while (rs.next()) {
				doc_Name[i] = rs.getString(1);
				doc_Fee[i++] = rs.getInt(2);

			}

			System.out.println("done");

			rs.close();
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc_Name;
	}

	void setTable() {
		Ptable.setModel(model);
		sp.setBounds(330, 150, 800, 400);
		JTableHeader header = Ptable.getTableHeader();
		header.setBackground(Color.black);
		header.setForeground(Color.WHITE);
		Ptable.getColumnModel().getColumn(7).setPreferredWidth(100);

	}

	void filter(String query) {
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);

		Ptable.setRowSorter(tr);
		tr.setRowFilter(javax.swing.RowFilter.regexFilter(query));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Koded by 19sw10

		if (FButtons[0] == e.getSource()) {
			int j = 0;
			int i = 0;
			String name = TFields[i++].getText(), fathername = TFields[i++].getText(), gender = null,
					age = TFields[i++].getText(), phone = TFields[i++].getText(), d = doc.getSelectedItem().toString();
			if (radiobutton[0].isSelected())
				gender = "male";
			else
				gender = "female";
			if (!(TFields[j++].getText().isEmpty() && !TFields[j++].getText().isEmpty()
					&& !TFields[j++].getText().isEmpty() && !TFields[j++].getText().isEmpty())
					&& (radiobutton[0].isSelected() || radiobutton[1].isSelected()) && (doc.getSelectedIndex() > 0)) {
				datetime = LocalDateTime.now();
				LocalDateTime now = LocalDateTime.now();
				String formattedDate = datetime.format(myFormatObj);
				if (p_id == 0)
					p_id = Ptable.getRowCount() + 1;
				Object[] data = { Ptable.getRowCount() + 1, name, fathername, gender, age, phone, d, formattedDate };
				model.addRow(data);
				try {
					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
							"saadkz07");
					Statement st = con.createStatement();
					PreparedStatement ps = con.prepareStatement(
							"insert into Patient(patient_id,name,fname,gender,age,phone,Time,doc_id) values(?,?,?,?,?,?,?,?)");
					ps.setInt(1, Ptable.getRowCount());
					ps.setString(2, name);
					ps.setString(3, fathername);
					ps.setString(4, gender);
					ps.setInt(5, Integer.parseInt(age));
					ps.setString(6, phone);
					ps.setString(7, formattedDate);
					int d_id = doc.getSelectedIndex();
					System.out.println(d_id);
					ps.setInt(8, d_id);
					ps.executeUpdate();
					ps.close();
					JOptionPane.showMessageDialog(null, "SuccessFully Inserted");

				}

				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Not insertd i db");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Please Sleect ");
			}
			FButtons[1].doClick();

		}
		if (FButtons[1] == e.getSource()) {
			String blank = "";
			int k = 0;
			TFields[k++].setText(blank);
			TFields[k++].setText(blank);
			TFields[k++].setText(blank);
			TFields[k++].setText(blank);
			TFields[k++].setText(blank);
			TFields[k++].setText(blank);
			Buttong.clearSelection();
			doc.setSelectedIndex(0);

		}
		if (doc == e.getSource()) {
			TFields[4].setText("" + doc_Fee[doc.getSelectedIndex()]);

		}
		int i = 0;
		if (items[i++] == e.getSource()) {

			// new
		}
		if (items[i++] == e.getSource()) {
			// edit
		}
		if (items[i++] == e.getSource()) {

			// close
			frame.dispose();

		}
		if (items[i++] == e.getSource()) {
			// xray
			new Lab(0);

		}
		if (items[i++] == e.getSource()) {
			// ultra sounds
			new Lab(1);
		}
		if (items[i++] == e.getSource()) {
			// lab test
			new Lab(2);
		}

		if (items[i++] == e.getSource()) {
			//
			new Reports(0);

		}
		if (items[i++] == e.getSource()) {
			new Reports(1);

		}
		if (items[i++] == e.getSource()) {
			// contact us
			new Reports(2);

		}
		if (items[i++] == e.getSource()) {
			// developer

		}

	}

}
