import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import com.sun.jdi.event.Event;

public class Driver  {
	
	

	public static void main(String[] arg) {
		

		try {
			// select Look and Feel
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			// start application

			new Home();

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	

}
}
