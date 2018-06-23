import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class AddFlights extends JFrame implements ActionListener  {

	JLabel  dcity, acity, dtime, atime;
	JTextField  text_dcity, text_acity, text_dtime, text_atime;
	JButton addFlight;

	Connection con;
	PreparedStatement insert_stmt, getDetails;
	ResultSet result_set;

	int departureCity,arrivalCity;
	String departureTime,arrivalTime;

	AddFlights() {

		setLayout(null);
		setContentPane(new JLabel(new ImageIcon("C:\\Users\\User\\Desktop\\JavaMini\\Data\\LoginImage.jpg")));

		
		dcity = new JLabel("Departure City : ");
		dcity . setBounds( 50, 160, 250, 30 );
		dcity . setFont(new Font("ARIAL", Font.BOLD, 20));
		dcity . setForeground(Color.WHITE);

		acity = new JLabel("Arrival City : ");
		acity . setBounds( 50, 210, 250, 30 );
		acity . setFont(new Font("ARIAL", Font.BOLD, 20));
		acity . setForeground(Color.WHITE);

		dtime = new JLabel("Departure Time : ");
		dtime . setBounds( 50, 260, 250, 30 );
		dtime . setFont(new Font("ARIAL", Font.BOLD, 20));
		dtime . setForeground(Color.WHITE);

		atime = new JLabel("Arrival Time : ");
		atime . setBounds( 50, 310, 250, 30 );
		atime . setFont(new Font("ARIAL", Font.BOLD, 20));
		atime . setForeground(Color.WHITE);

		text_dcity = new JTextField();
		text_dcity . setBounds( 330, 160, 250, 30 );
	
		text_acity = new JTextField();
		text_acity . setBounds( 330, 210, 250, 30 );

		text_dtime = new JTextField();
		text_dtime . setBounds( 330, 260, 250, 30 );
	
		text_atime = new JTextField();
		text_atime . setBounds( 330, 310, 250, 30 );

		addFlight=new JButton("ADD");
		addFlight.setBounds( 300, 380, 120, 40 );
		addFlight.setForeground(Color.WHITE);
		addFlight.setBackground(Color.GRAY);
		addFlight.addActionListener (this);

		add(dcity);
		add(acity);
		add(dtime);
		add(atime);

		add(text_dcity);
		add(text_acity);
		add(text_dtime);
		add(text_atime);

		add(addFlight);


		setSize(1024,640);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==addFlight) {
			try{

				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3307/airline","root","1234");

				departureCity=Integer.parseInt(text_dcity.getText());
				arrivalCity=Integer.parseInt(text_acity.getText());
				departureTime = text_dtime.getText();
				arrivalTime  = text_atime.getText();

				//getDetails = con.prepareStatement ("SELECT airportID FROM airport WHERE city = ?");
				//getDetails.setString(1, departureCity);
				//if (getDetails.next())


				SimpleDateFormat sdf = new SimpleDateFormat ("hh:mm:ss");
				long ms = sdf.parse(departureTime).getTime();
				Time t = new Time (ms);

				long ms2 = sdf.parse(arrivalTime).getTime();
				Time t2 = new Time (ms2);

				System.out.println (t);
				System.out.println (t2);
				
				insert_stmt=con.prepareStatement("INSERT INTO flight(departureCity,arrivalCity,departureTime,arrivalTime) VALUES(?,?,?,?) ");

				insert_stmt.setInt(1,departureCity);
				insert_stmt.setInt(2,arrivalCity);
				insert_stmt.setTime(3, t);
				insert_stmt.setTime(4, t2);

				insert_stmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "This flight has been added.");
			}
			catch(Exception e) {
				e.printStackTrace ();
			}
		}
	}

	public static void main(String ar[]){
		AddFlights add_flights=new AddFlights();
	} 
}