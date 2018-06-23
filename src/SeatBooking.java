import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class SeatBooking extends JFrame {

// --------------------------------------------------------------
// DECLARATIONS
// --------------------------------------------------------------
	
	UserPOJO userObject;
	BookingPOJO bookingObject;
	
	JLabel no_of_seats, select_class;
	JButton okButton, bookButton, backButton;
	JTextField input_seats;
	Choice input_class;
	int seats, travel_class;
	JLabel name[];
	JTextField input_names[];
	String names[];
	
	static int count=0;
	
	Connection con;
// --------------------------------------------------------------
	

// --------------------------------------------------------------
// ******************
// Part I: Back-end details.
// ******************
// --------------------------------------------------------------
	public void GUIinitialize()	{
		
		setLayout(null);
		setContentPane ( new JLabel ( new ImageIcon ("C:\\Users\\User\\Desktop\\JavaMini\\Data\\LoginImage.jpg")));
		
		//Labels
		no_of_seats = new JLabel ( "Select number of seats.");
		no_of_seats.setBounds (180, 60, 250, 40);
		no_of_seats.setFont (new Font ( "ARIAL", Font.BOLD, 20 ));
		no_of_seats.setForeground ( Color.WHITE );
		
		select_class = new JLabel ( "Select Class" );
		select_class.setBounds ( 480, 60, 150, 40 );
		select_class.setFont ( new Font ( "ARIAL", Font.BOLD, 20 ));
		select_class.setForeground ( Color.WHITE );
		
		//TextField
		input_seats = new JTextField ();
		input_seats.setBounds ( 180, 120, 250, 22 );
		
		//Drop down List
		input_class = new Choice ();
		input_class.setBounds ( 480, 120, 250, 50 );
		input_class.add ( "Economy" );
		input_class.add ( "Business" );
		
		//Buttons
		okButton = new JButton ( "OK" );
		okButton.setBounds(300, 200, 100, 40);
		okButton.setForeground(Color.WHITE);
		okButton.setBackground(Color.GRAY);
		
		bookButton=new JButton("BOOK");
		bookButton.setBounds(500, 520, 100, 40);
		bookButton.setForeground(Color.WHITE);
		bookButton.setBackground(Color.GRAY);
		
		backButton=new JButton("MODIFY SEARCH");
		backButton.setBounds(450, 200, 160, 40);
		backButton.setForeground(Color.WHITE);
		backButton.setBackground(Color.GRAY);
		
		//Add all the components
		
		add(no_of_seats);
		add(select_class);
		add(input_seats);
		add(input_class);
		add(okButton);
		add(backButton);
	}
	
	public void displayTextfields(int seats) {
		
		if ( count != 0 )
			removeTexts(seats);
		
		int y_coordinate=300;
		int i,j;
		
		name = new JLabel [ seats ];
		input_names = new JTextField [ seats ];
		
		try{
			for( j = 0; j < name.length && j < 4; j++) {
				
				name[j]=new JLabel("Name : ");
				name[j].setBounds( 180, y_coordinate, 100, 22);
				name[j].setFont(new Font("ARIAL",Font.BOLD,20));
				name[j].setForeground(Color.WHITE);
				y_coordinate+=50;
				count++;
				add(name[j]);
			}
				
			y_coordinate=300;
			for(i=0;i<seats && i<4;i++) {
				
				input_names[i]=new JTextField();
				input_names[i].setBounds( 300, y_coordinate, 250, 22);
				y_coordinate+=50;
				count++;
				add(input_names[i]);
			}
			add(bookButton);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}
			
	}
	
	public void removeTexts(int seats) {
		for(int i = 0 ; i < seats ; i++) {
			remove(input_names[i]);
		}
	}
	

// --------------------------------------------------------------
// ******************
// Part II: Functional Part.
// ******************
// --------------------------------------------------------------

	SeatBooking ( BookingPOJO bookingObject, UserPOJO userObject )	{

		this.bookingObject = bookingObject;
		this.userObject = userObject;
		GUIinitialize ();
		
		
		//Add Adapter class as Anonymous Inner class
		okButton.addMouseListener(
			new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					try {
						seats = Integer.parseInt ( input_seats.getText ( ));
						displayTextfields ( seats );
						setVisible ( false );
						setVisible ( true );
					}
					catch(Exception e)	{
						System.out.println(e);
					}
				}
			}
		);
		
		bookButton.addMouseListener(
			new MouseAdapter() {
				int total_cost;
				public void mouseClicked(MouseEvent me) {
					try {
						seats = Integer.parseInt ( input_seats.getText ( ));
						travel_class = input_class.getSelectedIndex ();
						
						if (travel_class == 0)
							bookingObject.setTravel_class ( "Economy" );
						else 
							bookingObject.setTravel_class ( "Business" );
						
						int answer = JOptionPane.showConfirmDialog ( null, "Are you sure you want to proceed?",
								"Select an Option",JOptionPane.YES_NO_CANCEL_OPTION);
						
						if(answer == JOptionPane.YES_OPTION) {
							
							names = new String [ seats ];
							for(int iter = 0; iter < seats ; iter++) 
								names[ iter ]= input_names [ iter ].getText ();
						}
						
						else if(answer==JOptionPane.NO_OPTION) {
							JOptionPane.showMessageDialog(null, "Enter number of seats");
							input_seats.setText ( "" );
							
							for(int iter = 0 ; iter < seats ; iter++) {
								input_names [ iter ].setText ( "" );
							}
						}
						ConfirmBooking confirm = new ConfirmBooking ( 
								seats, bookingObject, userObject, names );
					}
					catch(Exception e) {
						System.out.println(e);
					}
				}
			}
		);
		
		backButton.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						Booking booking = new Booking ( userObject );
					}
				}
		);

		setSize(1024, 640);
		setVisible(true);
	}
	
//	public static void main(String[] args) {
		
		// SeatBooking (flightID, userID)
	//	SeatBooking seat_booking = new SeatBooking ( 8000, 1006 );
	//}
	
}