import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class SeatCancellation extends JFrame {
	
	//declare instance variables
	
	UserPOJO userObject;
	
	JLabel no_of_seats;
	JLabel ticket_id[];
	JTextField cancel_seats,input_ticket_ids[];
	JButton ok_button,cancel_button, backButton;
	
	int seats, userID, flag = 0;
	int id[];
	
	static int count=0;
	String seating_class;
	
	Connection con;
	Statement delete_statement,select_statement,update_statement;
	ResultSet result_set;
	
	SeatCancellation ( UserPOJO userObject ) {
		
		this.userObject = userObject;
		
		setLayout(null);
		setContentPane ( new JLabel ( new ImageIcon ( "C:\\Users\\User\\"
				+ "Desktop\\JavaMini\\Data\\LoginImage.jpg" )));
		
		no_of_seats = new JLabel ( "Enter no. of seats to be cancelled" );
		no_of_seats.setBounds ( 150, 60, 350, 40 );
		no_of_seats.setFont ( new Font ( "ARIAL", Font.BOLD, 20 ));
		no_of_seats.setForeground ( Color.WHITE );
		
		cancel_seats = new JTextField ();
		cancel_seats.setBounds ( 520, 65, 250, 30 );
		
		ok_button = new JButton ( "OK" );
		ok_button.setBounds ( 420, 120, 100, 30 );
		
		cancel_button=new JButton( "CANCEL" );
		cancel_button.setBounds ( 500, 500, 100, 40 );

		backButton = new JButton ( "Go Back" );
		backButton.setBounds ( 400, 350, 200, 30 );
		backButton.setForeground ( Color.WHITE );
		backButton.setBackground ( Color.GRAY );
		//backButton.addActionListener ( this );
		add ( backButton );
		
		add ( no_of_seats );
		add ( cancel_seats );
		add ( ok_button );
		
		ok_button.addMouseListener(
			new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					try {
						seats=Integer.parseInt ( cancel_seats.getText ( ));
						displayTextfields ( seats );
						setVisible ( false );
						setVisible ( true );
					}
					catch(Exception e)	
					{ 
						System.out.println(e);
					}
				}
			}
		);
		
		cancel_button.addMouseListener(
			new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					try{
						
						id = new int [ seats ];
						
						for(int iter = 0 ; iter < seats ; iter++ ) {
							
							id [ iter ] = Integer.parseInt ( input_ticket_ids [ iter ].getText ( ));
							System.out.println ( id [ iter ] + "\t" );
							cancelTickets ( id );
						}
					}
					catch(Exception e) {
						System.out.println(e);
					}
				}
			}
		);
		
		setSize(1024, 640);
		setVisible(true);
	}
	
	
	public void displayTextfields ( int seats ) {
		
		if ( count != 0 )
			removeTexts(seats);
		
		int y_coordinate=300;
		int iter, j;
		
		ticket_id = new JLabel [ seats ];
		input_ticket_ids = new JTextField [ seats ];
		
		try{
			for(j = 0 ; j < ticket_id.length && j < 4; j++) {
				
				ticket_id [ j ] = new JLabel( "Enter Ticket ID : " );
				ticket_id [ j ].setBounds ( 170, y_coordinate, 250, 22 );
				ticket_id [ j ].setFont ( new Font ( "ARIAL", Font.BOLD, 20 ));
				ticket_id [ j ].setForeground ( Color.WHITE );
				y_coordinate+=50;
				count++;
				add ( ticket_id [ j ]);
			}
				
			y_coordinate = 300;
			for(iter = 0; iter < seats && iter < 4 ; iter++) {
				
				input_ticket_ids [ iter ] = new JTextField ();
				input_ticket_ids [ iter ].setBounds ( 400, y_coordinate, 180, 22 );
				y_coordinate += 50;
				count++;
				add ( input_ticket_ids [ iter ]);
			}
			
			
			add ( cancel_button );
		}
		catch ( Exception e ) {
			//JOptionPane.showMessageDialog(null);
		}
			
	}
	
	public void cancelTickets ( int []id ) {
		int flightID=0;
		try {
			Class.forName ( "com.mysql.jdbc.Driver" );
			con = DriverManager.getConnection ( "jdbc:mysql://localhost:3307/airline", "root", "1234" );
			
			for(int i=0;i<id.length;i++) {
				
				delete_statement=con.createStatement();
				select_statement=con.createStatement();
				update_statement=con.createStatement();	
				
				result_set = select_statement.executeQuery("SELECT flightID, userID FROM booking WHERE ticketID = '" + id[i] + "'");
				if (result_set.next())	
					flightID = result_set.getInt(1);
				else	{
					JOptionPane.showMessageDialog ( null, "You do not have any tickets to cancel.");
					flag = 1;
					break;
				}

				if (result_set.getInt(2) != userID)	{
					JOptionPane.showMessageDialog ( null, "You do not have the permission to cancel this ticket.");
					flag = 1;
					break;
				}
				
				result_set=select_statement.executeQuery("SELECT class FROM booking WHERE ticketID='"+id[i]+"'");
				if(result_set.next())	
					seating_class=result_set.getString(1);
				
				delete_statement.executeUpdate("DELETE FROM BOOKING WHERE ticketID='"+id[i]+"'");
				
				if (seating_class.equals("Economy"))
					update_statement.executeUpdate("UPDATE bookingStatus SET economySeats=economySeats+1 WHERE flightID=" + flightID);
				else if (seating_class.equals("Business"))
					update_statement.executeUpdate("UPDATE bookingStatus SET businessSeats=businessSeats+1 WHERE flightID=" + flightID);
			}
			if (flag == 0)
				JOptionPane.showMessageDialog(null,"Your ticket(s) have been cancelled. "
						+ "The ticket's amount with 30% tax cut will be returned to your account shortly.");
			this.invalidate ();
			this.setVisible ( false );
			Page2 page = new Page2 ( userObject );
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void removeTexts(int seats) {
		for(int i=0;i<seats;i++) {
			remove(input_ticket_ids[i]);
		}
	}
	
//	public static void main(String[] args) {
//		SeatCancellation seat_cancellation = new SeatCancellation (1007);
//	}
}