import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.io.*;

public class Reports implements ActionListener, ListSelectionListener {

	private JFrame frame = new JFrame("Lab Reports");
	private JTabbedPane pane = new JTabbedPane();

	private JPanel Xray = new JPanel();
	private JPanel ultraSound = new JPanel();
	private JPanel Labtest = new JPanel();

	private JLabel[] Xlabels = new JLabel[4];
	private JLabel[] Uslabels = new JLabel[4];
	private JLabel[] Ltlabels = new JLabel[4];
	private JLabel[] Lpic = new JLabel[100];
	private JLabel[] Lpic1 = new JLabel[100];
	private JLabel[] Lpic2 = new JLabel[100];

	private JTextField[] XFields = new JTextField[3];
	private JTextField[] UsFields = new JTextField[3];
	private JTextField[] LtFields = new JTextField[3];

	// buttons
	private JButton[] Xbutton = new JButton[3];
	private JButton[] Usbutton = new JButton[3];
	private JButton[] Ltbutton = new JButton[3];

	private JTable Ptable = new JTable() {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};;
	private JScrollPane sp = new JScrollPane(Ptable);
	DefaultTableModel model = new DefaultTableModel(new String[0][0], new String[] { "id", "pic" });
	private JTable Ptable1 = new JTable() {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};;

	private JScrollPane sp1 = new JScrollPane(Ptable1);
	DefaultTableModel model1 = new DefaultTableModel(new String[0][0], new String[] { "id", "pic" });
	private JTable Ptable2 = new JTable() {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};;
	private JScrollPane sp2 = new JScrollPane(Ptable2);
	DefaultTableModel model2 = new DefaultTableModel(new String[0][0], new String[] { "id", "pic" });

	private ListSelectionModel cellSelectionModel = Ptable.getSelectionModel();
	private ListSelectionModel cellSelectionModel1 = Ptable1.getSelectionModel();
	private ListSelectionModel cellSelectionModel2 = Ptable2.getSelectionModel();
	private Font font = new Font("Consolas", Font.BOLD, 15);
	private File file1;
	private String path = null;
	private int counter = 0;
	private JDialog dialog ;

	Reports(int index) {
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);

		setXray();
		setUltraSound();
		setLab();
		pane.add(Xray, "X-Ray");
		pane.add(ultraSound, "Ultra Sound");
		pane.add(Labtest, "Lab Tests");
		pane.setSelectedIndex(index);
		pane.setSize(1200, 500);
		frame.getContentPane().add(pane);
		frame.setVisible(true);

	}

	void setXray() {
		Xray.setLayout(null);
		Xray.setSize(700, 700);

		for (int i = 0; i < 4; i++) {
			Xray.add(Xlabels[i] = new JLabel());
			if (i < 3)
				Xray.add(XFields[i] = new JTextField());

		}

		int i = 0;

		Xlabels[i].setText("Xray");
		Xlabels[i].setFont(new Font("MV Boli", Font.BOLD, 40));
		Xlabels[i++].setBounds(100, 50, 600, 50);

		Xlabels[i].setText("Patient Id");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(10, 150, 150, 50);

		Xlabels[i].setText("Add Report File");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(10, 190, 150, 50);

		Xlabels[i].setText("File Name");
		Xlabels[i].setFont(font);
		Xlabels[i++].setBounds(10, 230, 150, 50);

		i = 0;
		XFields[i].setBounds(100, 163, 50, 20);
		XFields[i++].setFont(font);

		XFields[i].setBounds(100, 245, 150, 20);
		XFields[i].setEditable(false);
		XFields[i++].setFont(font);

		Xray.add(Xbutton[0] = new JButton("Insert"));
		Xray.add(Xbutton[1] = new JButton("Submit"));
		Xray.add(Xbutton[2] = new JButton("Search"));

		Xbutton[0].setBounds(140, 200, 80, 25);
		Xbutton[1].setBounds(100, 290, 80, 25);
		Xbutton[2].setBounds(160, 160, 80, 25);

		Xbutton[0].addActionListener(this);
		Xbutton[1].addActionListener(this);
		Xbutton[2].addActionListener(this);
		Xbutton[0].setEnabled(false);
		Xbutton[1].setEnabled(false);

		Xray.add(sp);
		setXTable();
		Xray.setVisible(true);

	}

	void setUltraSound() {
		ultraSound.setLayout(null);
		ultraSound.setSize(700, 700);

		for (int i = 0; i < 4; i++) {
			ultraSound.add(Uslabels[i] = new JLabel());
			if (i < 3)
				ultraSound.add(UsFields[i] = new JTextField());

		}

		int i = 0;

		Uslabels[i].setText("ultraSound");
		Uslabels[i].setFont(new Font("MV Boli", Font.BOLD, 40));
		Uslabels[i++].setBounds(100, 50, 600, 50);

		Uslabels[i].setText("Patient Id");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 150, 150, 50);

		Uslabels[i].setText("Add Report File");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 190, 150, 50);

		Uslabels[i].setText("File Name");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 230, 150, 50);

		i = 0;
		UsFields[i].setBounds(100, 163, 50, 20);
		UsFields[i++].setFont(font);

		UsFields[i].setBounds(100, 245, 150, 20);
		UsFields[i].setEditable(false);
		UsFields[i++].setFont(font);

		ultraSound.add(Usbutton[0] = new JButton("Insert"));
		ultraSound.add(Usbutton[1] = new JButton("Submit"));
		ultraSound.add(Usbutton[2] = new JButton("Search"));

		Usbutton[0].setBounds(140, 200, 80, 25);
		Usbutton[1].setBounds(100, 290, 80, 25);
		Usbutton[2].setBounds(160, 160, 80, 25);

		Usbutton[0].setEnabled(false);
		Usbutton[1].setEnabled(false);

		Usbutton[0].addActionListener(this);
		Usbutton[1].addActionListener(this);
		Usbutton[2].addActionListener(this);

		ultraSound.add(sp1);
		setUTable();
		ultraSound.setVisible(true);

	}

	void setLab() {
		Labtest.setLayout(null);
		Labtest.setSize(700, 700);

		for (int i = 0; i < 4; i++) {
			Labtest.add(Uslabels[i] = new JLabel());
			if (i < 3)
				Labtest.add(LtFields[i] = new JTextField());

		}

		int i = 0;

		Uslabels[i].setText("Labtest");
		Uslabels[i].setFont(new Font("MV Boli", Font.BOLD, 40));
		Uslabels[i++].setBounds(100, 50, 600, 50);

		Uslabels[i].setText("Patient Id");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 150, 150, 50);

		Uslabels[i].setText("Add Report File");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 190, 150, 50);

		Uslabels[i].setText("File Name");
		Uslabels[i].setFont(font);
		Uslabels[i++].setBounds(10, 230, 150, 50);

		i = 0;
		LtFields[i].setBounds(100, 163, 50, 20);
		LtFields[i++].setFont(font);

		LtFields[i].setBounds(100, 245, 150, 20);
		LtFields[i].setEditable(false);
		LtFields[i++].setFont(font);

		Labtest.add(Ltbutton[0] = new JButton("Insert"));
		Labtest.add(Ltbutton[1] = new JButton("Submit"));
		Labtest.add(Ltbutton[2] = new JButton("Search"));

		Ltbutton[0].addActionListener(this);
		Ltbutton[1].addActionListener(this);
		Ltbutton[2].addActionListener(this);
		Ltbutton[0].setEnabled(false);
		Ltbutton[1].setEnabled(false);

		Ltbutton[0].setBounds(140, 200, 80, 25);
		Ltbutton[1].setBounds(100, 290, 80, 25);
		Ltbutton[2].setBounds(160, 160, 80, 25);

		Labtest.add(sp2);
		setLTable();
		Labtest.setVisible(true);

	}

	void setXTable() {

		Ptable.setModel(model);
		sp.setBounds(330, 150, 300, 300);
		sp.setViewportView(Ptable);
		JTableHeader header = Ptable.getTableHeader();
		header.setBackground(Color.black);
		header.setForeground(Color.WHITE);
		Ptable.setCellSelectionEnabled(true);

		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel.addListSelectionListener(this);

		getData(1, "Xray");

		// Ptable.getColumnModel().getColumn(1).setPreferredWidth(100);
	}

	void setUTable() {

		Ptable1.setModel(model1);
		sp1.setBounds(330, 150, 300, 300);
		sp1.setViewportView(Ptable1);
		JTableHeader header = Ptable1.getTableHeader();

		header.setBackground(Color.black);
		header.setForeground(Color.WHITE);

		cellSelectionModel1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel1.addListSelectionListener(this);
		getData(2, "us_test");

		// Ptable.getColumnModel().getColumn(1).setPreferredWidth(100);
	}

	void setLTable() {

		Ptable2.setModel(model2);
		sp2.setBounds(330, 150, 300, 300);
		sp2.setViewportView(Ptable2);
		JTableHeader header = Ptable2.getTableHeader();
		header.setBackground(Color.black);
		header.setForeground(Color.WHITE);

		cellSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel2.addListSelectionListener(this);
		getData(3, "Lab_test");

		// Ptable.getColumnModel().getColumn(1).setPreferredWidth(100);
	}

	private Object[] getData(int s, String Tname) {
		Object[] data = new Object[20];

		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "System", "saadkz07");

			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("Select * from " + Tname);

			int i = 0;
			while (rs.next()) {
				data = new Object[20];
				i = 0;
				data[i++] = rs.getInt(1);
				byte barr[] = rs.getBytes(2);// 1 means first image
				ImageIcon image = new ImageIcon(barr);
				Image img = image.getImage();
				Image dimg = img.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
				ImageIcon ic = new ImageIcon(dimg);
				data[i++]="View Report";

				switch (s) {
				case 1:
					Lpic[(int) data[0]] = new JLabel();
					Lpic[(int) data[0]].setSize(400, 400);
					Lpic[(int) data[0]].setIcon(ic);
					model.addRow(data);
					break;
				case 2:
					Lpic1[(int) data[0]] = new JLabel();
					Lpic1[(int) data[0]].setSize(400, 400);
					Lpic1[(int) data[0]].setIcon(ic);
					model1.addRow(data);

					break;
				case 3:
					Lpic2[(int) data[0]] = new JLabel();
					Lpic2[(int) data[0]].setSize(400, 400);
					Lpic2[(int) data[0]].setIcon(ic);
					model2.addRow(data);
					break;

				}
			}

			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	boolean Search(int id) {
		boolean exist = false;
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "System", "saadkz07");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select name from Lab where id=" + id);

			if (rs.next())
				exist = true;

			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return exist;
	}

	void insert(int i, int id, String path) {
		String db = new String();
		switch (i) {
		case 1:
			db = "Xray";
			break;
		case 2:
			db = "us_Test";
			break;
		case 3:
			db = "Lab_Test";
			break;
		}

		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "saadkz07");
			Statement st = con.createStatement();
			PreparedStatement ps = con.prepareStatement("insert into " + db + " values(?,?)");
			ps.setInt(1, id);
			FileInputStream fin = new FileInputStream(path);
			ps.setBinaryStream(2, fin, fin.available());

			ps.executeUpdate();
			ps.close();
			switch (i) {
			case 1:
				model.addRow(new Object[] { id });
				break;
			case 2:
				model1.addRow(new Object[] { id });

				break;
			case 3:
				model2.addRow(new Object[] { id });

				break;
			}
			JOptionPane.showMessageDialog(null, "SuccessFully Inserted");

			Xbutton[0].setEnabled(false);
			Xbutton[1].setEnabled(false);

			Usbutton[0].setEnabled(false);
			Usbutton[1].setEnabled(false);

			Ltbutton[0].setEnabled(false);
			Ltbutton[1].setEnabled(false);
			String n = "";
			LtFields[1].setText(n);
			XFields[1].setText(n);
			UsFields[1].setText(n);

		}

		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Not insertd in db");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Koded by 19sw10
		String ID;
		int k = 0;
		String ID1;
		int k1 = 0;
		String ID2;
		int k2 = 0;

		if (Xbutton[2] == e.getSource()) {

			if (!XFields[0].getText().isEmpty()) {
				ID = XFields[0].getText();
				k = Integer.parseInt(ID);
				try {
					if (Search(k)) {

						Xbutton[0].setEnabled(true);
						Xbutton[1].setEnabled(true);

					} else {
						JOptionPane.showMessageDialog(null, "Id not found");
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Invalid Id");

				}
			} else {
				JOptionPane.showMessageDialog(null, "Enter Id");
			}
		}

		if (Xbutton[1] == e.getSource()) {
			ID = XFields[0].getText();
			k = Integer.parseInt(ID);
			if (file1 != null) {
				try {
					System.out.println(k);
					insert(1, k, path);

				} catch (Exception r) {
					r.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Please Select File");
			}

		}

		if (Xbutton[0] == e.getSource()) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("All files", "png", "jpg", "jpeg");
			fc.addChoosableFileFilter(filter);
			fc.setAcceptAllFileFilterUsed(false);

			int i = fc.showOpenDialog(null);

			if (i == fc.APPROVE_OPTION) {
				file1 = fc.getSelectedFile();
				path = file1.getAbsolutePath();
				XFields[1].setText(path);

			}

		}

		if (Usbutton[2] == e.getSource()) {
			if (!UsFields[0].getText().isEmpty()) {

				ID1 = UsFields[0].getText();
				k1 = Integer.parseInt(ID1);
				try {
					if (Search(k1)) {

						Usbutton[0].setEnabled(true);
						Usbutton[1].setEnabled(true);

					} else {
						JOptionPane.showMessageDialog(null, "Id us not found");
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Invalid Id");

				}

			} else {
				JOptionPane.showMessageDialog(null, "enter Id");
			}
		}

		if (Usbutton[0] == e.getSource()) {
			ID1 = UsFields[0].getText();
			k1 = Integer.parseInt(ID1);
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("All files", "png", "jpg", "jpeg");
			fc.addChoosableFileFilter(filter);
			fc.setAcceptAllFileFilterUsed(false);

			int i = fc.showOpenDialog(null);

			if (i == fc.APPROVE_OPTION) {
				file1 = fc.getSelectedFile();
				path = file1.getAbsolutePath();
				UsFields[1].setText(path);

			}

		}
		if (Usbutton[1] == e.getSource()) {
			ID1 = UsFields[0].getText();
			k1 = Integer.parseInt(ID1);
			if (file1 != null) {
				try {
					System.out.println(k1);
					insert(2, k1, path);
				} catch (Exception r) {
					r.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Please Select File");
			}

		}

		if (Ltbutton[1] == e.getSource()) {
			if (file1 != null) {
				ID2 = LtFields[0].getText();
				k2 = Integer.parseInt(ID2);
				try {
					insert(3, k2, path);
				} catch (Exception r) {
					r.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Please Select File");
			}

		}

		if (Ltbutton[2] == e.getSource()) {
			if (!LtFields[0].getText().isEmpty()) {
				ID2 = LtFields[0].getText();
				k2 = Integer.parseInt(ID2);
				try {
					if (Search(k2)) {

						Ltbutton[0].setEnabled(true);
						Ltbutton[1].setEnabled(true);

					} else {
						JOptionPane.showMessageDialog(null, "Id Lt not found");
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Invalid Id");

				}

			} else {
				JOptionPane.showMessageDialog(null, "enter id ");

			}
		}
		if (Ltbutton[0] == e.getSource()) {
			ID2 = LtFields[0].getText();
			k2 = Integer.parseInt(ID2);
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("All files", "png", "jpg", "jpeg");
			fc.addChoosableFileFilter(filter);
			fc.setAcceptAllFileFilterUsed(false);

			int i = fc.showOpenDialog(null);

			if (i == fc.APPROVE_OPTION) {
				file1 = fc.getSelectedFile();
				path = file1.getAbsolutePath();
				LtFields[1].setText(path);

			}
		}

	}

	public static void main(String[] arg) {

		new Reports(1);
	}
	
	public void valueChanged(ListSelectionEvent e) {
		// Koded by 19sw10
		if (!e.getValueIsAdjusting()) {//This line prevents double events

	    
		if (cellSelectionModel == e.getSource()) {
			int s = Ptable.getSelectedRow();
			int row = Ptable.getSelectedRow();
			int column = Ptable.getSelectedColumn();
			int id;
			if(row>=0)
			{
			    id = (int) Ptable.getValueAt(row, 0);
			JDialog dialog = new JDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setTitle("Report");
			dialog.setLocationRelativeTo(frame);
			dialog.add(Lpic[id]);

			dialog.pack();
			dialog.setVisible(true);
			int firstRow=Ptable.getEditingRow();
			int lastRow;
			model.fireTableCellUpdated(firstRow, 1);
			}

		}

		if (cellSelectionModel1 == e.getSource()) {
			int s = Ptable.getSelectedRow();
			int row = Ptable1.getSelectedRow();
			int column = Ptable1.getSelectedColumn();
			if(row>=0) {
			int id = (int) Ptable1.getValueAt(row, 0);
			JDialog dialog = new JDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setTitle("Report");
			dialog.setLocationRelativeTo(frame);

			dialog.add(Lpic1[id]);

			dialog.pack();
			dialog.setVisible(true);
			int editrow=Ptable.getEditingRow();
			model1.fireTableCellUpdated(editrow, 1);}


		}

		if (cellSelectionModel2 == e.getSource()) {
			
			System.out.println(cellSelectionModel2.getLeadSelectionIndex());
				int s = Ptable2.getSelectedRow();
				
			int row = Ptable2.getSelectedRow();
			int column = Ptable2.getSelectedColumn();
			if(row>=0) {
			int id = (int) Ptable2.getValueAt(row, 0);
			 dialog = new JDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setTitle("Report");
			dialog.setLocationRelativeTo(frame);

			dialog.add(Lpic2[id]);

			dialog.pack();
			dialog.setVisible(true);
			int firstRow=Ptable.getEditingRow();
            model2.fireTableCellUpdated(firstRow, 1);
			}}
	

		
	}}

}
