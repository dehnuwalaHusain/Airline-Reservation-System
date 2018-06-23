import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class PastTransactions extends JFrame implements ActionListener {
	
	UserPOJO userObject;
	
	JTable ticketsTable;
	JScrollPane scrollPane;
	JPanel jpanel;
	
	JButton backButton;
	Font fontText;
	JLabel head;
	
	Connection con;
	PreparedStatement ps;
	ResultSet countResults, useResults;
	
	int count;
	String data[][];
	String header[] = { "ticketID", "Name", "FlightID" };
	
	public void getDetails ()	{
		try	{
			Class.forName( "com.mysql.jdbc.Driver" );
			con = DriverManager.getConnection ( "jdbc:mysql://localhost:3307/airline", "root", "1234" );
		}
		catch ( Exception e )	{
			JOptionPane.showMessageDialog (null, "Database Not Connected.");
		}
		try	{
			ps = con.prepareStatement ( "SELECT ticketID, name, flightID "
					+ "FROM booking WHERE userID = ?" );
			ps.setInt(1, userObject.getUserID ( ));
			countResults = ps.executeQuery ( );
			while ( countResults.next ( ))
				count++;
			
			data = new String [ count ][ 3 ];
			useResults = ps.executeQuery ();
			int iter = 0;
			while (useResults.next())	{
				data [iter][0] = Integer.toString(useResults.getInt(1));
				data [iter][1] = useResults.getString(2);
				data [iter][2] = Integer.toString(useResults.getInt(3));
				
				iter++;
			}
			ticketsTable = new JTable ( data, header );
			scrollPane = new JScrollPane( ticketsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
								ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			scrollPane.setBounds ( 50, 170, 900, ( 38 + (count-1)*15 ) );
			
			add ( scrollPane );
			this.invalidate ();
			this.validate ();
			this.repaint ();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	PastTransactions ( UserPOJO userObject )	{
		
		count = 0;
		this.userObject = userObject;
		
		setLayout(null);
		setContentPane ( new JLabel ( new ImageIcon ( "C:\\Users\\User\\Desktop\\JavaMini\\Data\\LoginImage.jpg" )));
		
		fontText = new Font ( "ARIAL", Font.BOLD, 20 );
		
		head = new JLabel( "Previously booked tickets by user " + userObject.getUserID());
		head.setBounds ( 70, 70, 400, 50);
		head.setFont( fontText );
		head.setForeground(Color.WHITE);
		add(head);
		
		getDetails();
		
		backButton = new JButton ( "Go Back" );
		backButton.setBounds ( 400, 350, 200, 30 );
		backButton.setFont ( fontText );
		backButton.setForeground ( Color.WHITE );
		backButton.setBackground ( Color.GRAY );
		backButton.addActionListener ( this );
		add (backButton);
		
		setSize ( 1024, 640);
		setVisible ( true );
	}

	@Override
	public void actionPerformed ( ActionEvent ae ) {
		if ( ae.getSource() == backButton)	{
			this.invalidate ();
			this.setVisible ( false );
			Page2 page = new Page2 ( userObject );
		}
		
	}
	
//	public static void main(String[] args) {
//		PastTransactions past = new PastTransactions ( 1006 );
//	}
}
