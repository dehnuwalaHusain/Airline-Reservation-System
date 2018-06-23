import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
public class ConfirmBooking extends JFrame implements ActionListener
{
// --------------------------------------------------------------
// DECLARATIONS
// --------------------------------------------------------------
	
	UserPOJO userObject;
	BookingPOJO bookingObject;
	
	JTable ticketsTable;
	JLabel total_amt, total_amt1, confirmLabel;
	Font f1, f2;
	JScrollPane scrollPane;	
	JPanel jpanel;
	ResultSet countResults, useResults;
	JButton confirmButton, cancelButton;
	
	PreparedStatement ps, ps1;
	Connection con;
	ResultSet result_set;
	
	
	int seats;
	String[] names;
	String travel_class;
	
	int count;
	int total_cost;
	String data [][];
	String[] heading = { "TicketID", "Name", "FlightID", "Seating Class", "UserID" };
// --------------------------------------------------------------	

	
	
// --------------------------------------------------------------
// ******************
// Part I: Back-end details.
// ******************
// --------------------------------------------------------------

	public void initVars ( int seats, String[] names )	{
		
		this.seats = seats;
		this.names = new String [names.length];
		this.travel_class = this.bookingObject.getTravel_class();
		for ( int iter = 0; iter < names.length; iter++)
			this.names[iter] = names[iter];
		data = new String [names.length][5];
	}
	
	public void GUIinit ( int total_cost )	{
		
		total_cost *= seats;
		Container p = getContentPane();
		p.setLayout ( null );

		setContentPane ( new JLabel ( new ImageIcon ( "C:\\Users\\User\\Desktop\\"
									+ "JavaMini\\Data\\LoginImage.jpg" )));
		count = 0;
		
		f1 = new Font ( "ARIAL" , Font.BOLD , 30);
		f2 = new Font ( "ARIAL" , Font.BOLD , 20);
		
		confirmLabel = new JLabel ( "Please confirm your booking details." );
		confirmLabel.setBounds ( 50, 100, 640, 40 );
		confirmLabel.setFont ( f1 );
		confirmLabel.setForeground ( Color.WHITE );
		add ( confirmLabel );
	
		total_amt1 = new JLabel ( );
		total_amt1.setBounds( 380, (325 + (45 + (count-1)*15)), 200, 30 );
		total_amt1.setFont ( f2 );
		total_amt1.setForeground ( Color.WHITE );
		total_amt1.setText ( String.format( "Total Amount: " + Integer.toString ( total_cost )));
		add ( total_amt1 );
		
		confirmButton = new JButton( "Confirm" );
		confirmButton.setBounds ( 100, (400 + (45 + (count-1)*15)), 120, 30);
		confirmButton.setForeground ( Color.WHITE );
		confirmButton.setBackground ( Color.GRAY );
		add ( confirmButton );

		cancelButton = new JButton( "Cancel" );
		cancelButton.setBounds ( 780, (400 + (45 + (count-1)*15)), 120, 30);
		cancelButton.setForeground ( Color.WHITE );
		cancelButton.setBackground ( Color.GRAY );
		add ( cancelButton );

		cancelButton.addActionListener ( this );
		confirmButton.addActionListener ( this );
	}
	
	public int calcPrice () {
		
		int data_seats, data_price;
		try	{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection ( "jdbc:mysql://localhost:3307/airline", "root", "1234" );
		}
		catch ( Exception e )	{
			JOptionPane.showMessageDialog (null, "Database Not Connected.");
		}
		try	{
			if ( travel_class.equals( "Economy" ))
				ps = con.prepareStatement ( "SELECT flightID, economySeats, "
						+ "economyPrice FROM bookingStatus WHERE flightID = ?" );
			
			else if ( travel_class.equals( "Business" ))
				ps = con.prepareStatement ( "SELECT flightID, businessSeats, "
						+ "businessPrice FROM bookingStatus WHERE flightID = ?" );
			
			ps.setInt ( 1, this.bookingObject.getFlightID() );
			result_set = ps.executeQuery ();
		}
		catch (Exception e)	{
			System.out.println("Can't fetch booking details.");
		}
		try	{
			if ( result_set.next ( )){
				data_seats = result_set.getInt ( 2 );
				data_price = result_set.getInt ( 3 );
				total_cost += data_price;
			}
		}
		catch ( Exception e )	{
			JOptionPane.showMessageDialog (null, "An error occured while processing your request."
					+ " Please contact your software vendor.");
			e.printStackTrace();
		}
		return total_cost;
	}
	
	public void bookSeats ()
	{
		try	{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection ( "jdbc:mysql://localhost:3307/airline", "root", "1234" );
		}
		catch ( Exception e )	{
			JOptionPane.showMessageDialog (null, "Database Not Connected.");
		}
		
		try {
			for (int iter = 0; iter < seats ; iter++) { 
				if ( travel_class.equals ( "Economy" ))
					ps = con.prepareStatement
							("UPDATE bookingStatus SET economySeats = economySeats - 1 WHERE flightID = ?");
		
				else if ( travel_class.equals( "Business" ) )
					ps = con.prepareStatement
							("UPDATE bookingStatus SET economySeats = economySeats - 1 WHERE flightID = ?");
				
				ps.setInt( 1, this.bookingObject.getFlightID( ));
				ps.executeUpdate();
				
				ps1 = con.prepareStatement
						("INSERT INTO booking(name, flightID, class, userID) VALUES (?, ?, ?, ?)");
				ps1.setString ( 1, names [ iter ]);
				ps1.setInt ( 2, this.bookingObject.getFlightID( ));
				
				ps1.setString (3, travel_class );
				ps1.setInt(4, this.userObject.getUserID ( ));
				ps1.executeUpdate();
				
				ps1 = con.prepareStatement
						("INSERT INTO payment(userID, cost) VALUES (?,?)");
				ps1.setInt (1, this.userObject.getUserID ( ));
				ps1.setInt (2, total_cost);
				ps1.executeUpdate ();
				JOptionPane.showMessageDialog (null, "Your ticket(s) have been booked.");
			}
			total_cost *= seats;
		}
		catch (Exception e)	{
			JOptionPane.showMessageDialog (null, "An error occured while processing your request."
					+ " Please contact your software vendor.");
			e.printStackTrace();
		}
	}
	
	public void tempWindow()
	{
		for (int iter = 0; iter < names.length ; iter++)	{
			data [iter] [1] = names [ iter ];
			data [iter] [2] = Integer.toString ( this.bookingObject.getFlightID ( ));
			data [iter] [3] = this.bookingObject.getTravel_class ();
			data [iter] [4] = Integer.toString ( this.userObject.getUserID() );
		}
		ticketsTable = new JTable ( data, heading );
		scrollPane = new JScrollPane( ticketsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
									ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
		scrollPane.setBounds ( 50, 170, 900, ( 53 + (count-1)*15 ) );
		
		add ( scrollPane );
		this.invalidate ();
		this.validate ();
		this.repaint ();
	}

// -------------------------------------------------------------
// *******************
// Part II: Functional Parts.
// *******************
// -------------------------------------------------------------
	
	ConfirmBooking ( int seats, BookingPOJO bookingObject, UserPOJO userObject, String names[] )	{
		
		this.userObject = userObject;
		this.bookingObject = bookingObject;
		total_cost = 0;
		
		initVars ( seats, names );
		calcPrice();
		GUIinit ( total_cost );

		tempWindow();
		
		setSize ( 1024, 640 );
		setVisible ( true );
	}
	
	public void actionPerformed(ActionEvent ae)	{
		
		if ( ae.getSource() == cancelButton )	{
			this.invalidate();
			setVisible (false);
			Page2 page = new Page2 ( userObject );
		}
		
		else if ( ae.getSource() == confirmButton )	{
			bookSeats();
			this.invalidate();
			this.setVisible(false);
			Page2 page = new Page2 ( userObject );
		}
	}
	
	public static void main(String args[])
	{
		//ConfirmBooking confirm = new ConfirmBooking ( 1005 , 235);
	}
}