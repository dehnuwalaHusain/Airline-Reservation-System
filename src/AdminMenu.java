//package airline;

import java.awt.Color;
import java.awt.Font;
/*
 1. Add flights
 2. View Users
 3. View Flights
 4. View booked tickets
*/
import java.awt.event.*;
import javax.swing.*;

public class AdminMenu extends JFrame implements ActionListener {
	
	JLabel head_label;
	JButton viewUsersButton, viewFlightsButton, viewTicketsButton, addFlightsButton, logoutButton;
	
	AdminMenu() {
		
		setLayout(null);
		setContentPane(new JLabel(new ImageIcon("C:\\Users\\User\\Desktop\\JavaMini\\Data\\LoginImage.jpg")));
		
		head_label=new JLabel("Hello Admin!");
		head_label.setBounds( 100, 50, 150, 20 );
		head_label.setFont(new Font( "ARIAL", Font.BOLD, 20 ));
		head_label.setForeground(Color.WHITE);
		
		viewFlightsButton=new JButton("VIEW FLIGHTS");
		viewFlightsButton.setBounds( 170, 200, 250, 50 );
		viewFlightsButton.setForeground(Color.WHITE);
		viewFlightsButton.setBackground(Color.GRAY);
		
		viewUsersButton=new JButton("VIEW USERS");
		viewUsersButton.setBounds( 500, 200, 250, 50 );
		viewUsersButton.setForeground(Color.WHITE);
		viewUsersButton.setBackground(Color.GRAY);
		
		addFlightsButton=new JButton("ADD FLIGHTS");
		addFlightsButton.setBounds( 170, 320, 250, 50 );
		addFlightsButton.setForeground(Color.WHITE);
		addFlightsButton.setBackground(Color.GRAY);


		viewTicketsButton=new JButton("VIEW TICKETS");
		viewTicketsButton.setBounds( 500, 320, 250, 50 );
		viewTicketsButton.setForeground(Color.WHITE);
		viewTicketsButton.setBackground(Color.GRAY);
		
		logoutButton = new JButton("Log out");
		logoutButton.setBounds ( 350, 470, 250, 50 );
		logoutButton.setForeground ( Color.WHITE );
		logoutButton.setBackground ( Color.GRAY );
		add ( logoutButton );
		logoutButton.addActionListener ( this );
		
		add(head_label);
		add(viewUsersButton);
		add(viewFlightsButton);
		add(viewTicketsButton);
		add(addFlightsButton);
		
		viewFlightsButton.addActionListener(this);
		viewUsersButton.addActionListener(this);
		viewTicketsButton.addActionListener(this);
		addFlightsButton.addActionListener(this);
		
		setSize(1024,640);
		setVisible(true);
		
	}
	
	
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==viewFlightsButton) {
			this.invalidate ();
			this.setVisible ( false );
			ViewFlights view_flights=new ViewFlights();
		}
		
		else if(ae.getSource()==viewUsersButton) {
			this.invalidate ();
			this.setVisible ( false );
			ViewUsers view_users=new ViewUsers();
		}
		
		else if(ae.getSource()==viewTicketsButton) {
			this.invalidate ();
			this.setVisible ( false );
			ViewBooking view_bookings=new ViewBooking();
		}

		else if(ae.getSource()==addFlightsButton){
			this.invalidate ();
			this.setVisible ( false );
			AddFlights add_flights=new AddFlights();
		}
		else if ( ae.getSource () == logoutButton )	{
			this.invalidate ();
			this.setVisible ( false );
			Home home=new Home ();
		}
	}
	
	public static void main(String[] args) {
		AdminMenu admin_menu=new AdminMenu();
	}
}
