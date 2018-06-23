//package airline;

import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class ViewFlights extends JFrame implements ActionListener {
	
	JLabel head_label;
	JTable table;
	JScrollPane scrollpane;
	
	String data[][];
	
	Connection con;
	Statement select_stmt;
	ResultSet result_set;
	
	ViewFlights() {
		
		setLayout(null);
		setContentPane(new JLabel(new ImageIcon("C:\\Users\\User\\Desktop\\JavaMini\\Data\\LoginImage.jpg")));
		
		head_label=new JLabel("Flight details : ");
		head_label.setBounds( 100, 50, 150, 20 );
		head_label.setFont(new Font( "ARIAL", Font.BOLD, 20 ));
		head_label.setForeground(Color.WHITE);
		
		add(head_label);
		
		displayFlights();
		
		setSize(1024,640);
		setVisible(true);
	}
	
	public void displayFlights() {
		
		//String heading[];
		
		data=new String [ 1010 ][ 5 ];
		String heading[]= {"FlightID","DepartureCity","ArrivalCity","DepartureTime","ArrivalTime"};
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3307/airline","root","1234");
			select_stmt=con.createStatement();
			result_set=select_stmt.executeQuery("SELECT * FROM flight");
			
			int i=0;
			while(result_set.next()) {
				
				for(int j=0;j<5;j++) {
					data[i][j]=result_set.getString(j+1);
				}
				i++;
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		
		table=new JTable(data,heading);
		table.setEnabled(false);
		scrollpane=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setBounds(50,120,900,400);
		
		add(scrollpane);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
	}
	
	public static void main(String[] args) {
		ViewFlights view_flights=new ViewFlights();
	}
}
