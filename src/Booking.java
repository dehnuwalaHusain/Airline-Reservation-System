import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class Booking extends JFrame implements ActionListener, ItemListener
{	
	
// -------------------------------------------------------------
//	DECLARATIONS
// -------------------------------------------------------------
	
	UserPOJO userObject;
	BookingPOJO bookingObject;
	
	JLabel introLabel, dateLabel, click, invisible;
	JComboBox fromCombo, toCombo;
	JButton searchButton, thisFlight[];
	JTable availableFlights;
	JScrollPane scrollPane;	
	JPanel jpanel;
	Font fontHeader;
	Font fontLabels;
	
	int rowCount, userID;
	
	String data[][], heading[] =  { "FlightID", "DCity", "DTime", "ACity", "ATime", "ESeats", "EPrice"};
	String fromCity, toCity;
	
	static ResultSet resultSet,rs1, fromResultSet,toResultSet,flightsResultSet,countFlights,rs5;
	static Connection con,con1,con2,con3,con4,conn;
	static Statement statement, st4;
	PreparedStatement prepareState;

// --------------------------------------------------------------------------------
// *****************	
// Part I:	Back-end Details.
// *****************	
// --------------------------------------------------------------------------------
	public static Connection getConnection()
	{
		try	{
			Class.forName ( "com.mysql.jdbc.Driver" );
			conn = DriverManager.getConnection ( "jdbc:mysql://localhost:3307/airline", "root", "1234" );
		}
		catch(Exception ae)	{
			System.out.println ( ae );
		}
		return conn;
	}
	
	public void GUIinitializations ()	{
		setContentPane(new JLabel(new ImageIcon("C:\\Users\\User\\Desktop"
				+ "\\JavaMini\\Data\\LoginImage.jpg")));
		
		Container p = getContentPane();
		
		fontHeader = new Font ( "ARIAL", Font.BOLD, 40);
		fontLabels = new Font ("ARIAL", Font.BOLD, 20);
		
		introLabel=new JLabel ( "Book flights" );
		introLabel.setBounds ( 20, 10, 400, 50 );
		introLabel.setFont ( fontHeader );
		introLabel.setForeground ( Color.WHITE );
		introLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE));
		add ( introLabel );

		//printing system Date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern ( "yyyy/MM/dd" );
		LocalDateTime now = LocalDateTime.now ();
		String currentDate = dtf.format ( now );

		dateLabel = new JLabel ( currentDate );
		dateLabel.setBounds ( 580, 80, 200, 30);
		dateLabel.setFont ( fontLabels );
		dateLabel.setForeground ( Color.WHITE );
		add ( dateLabel );

		click = new JLabel ( "Click me" );
		click.setBounds ( 580, 50, 200, 30);
		click.setFont ( fontLabels );
		click.setForeground ( Color.WHITE );

		searchButton = new JButton( "Search" );
		searchButton.setBounds ( 800, 80, 80, 30);
		add ( searchButton );
		searchButton.addActionListener ( this );
		
		fromCombo=new JComboBox();
		toCombo=new JComboBox();
	}
	
	public void setCombo ()	{
		
		try	{
			con = getConnection();		
			Statement statement = con.createStatement();
			resultSet = statement.executeQuery ( "SELECT city FROM airport");
			
			while ( resultSet.next ( ))	{
				fromCombo.addItem ( resultSet.getString ( 1 ));
			}
		}
		catch(Exception e)	{
			System.out.println(e);
		}
		
		add ( fromCombo );
		fromCombo.setSelectedIndex ( -1 );
		fromCombo.addItemListener ( this );
		
		try	{
			con = getConnection ();
			statement = con.createStatement ();
		 	
			rs1 = statement.executeQuery ( "SELECT city FROM airport" );
		 	while ( rs1.next ())	{
				toCombo.addItem ( rs1.getString ( 1 ));
			}
		}
		catch(Exception e)	{
			System.out.println(e);
		}
		add ( toCombo );
		toCombo.setSelectedIndex ( -1 );
		toCombo.addItemListener ( this );
	}
	
	public void showFlights ()	{
		try	{	
			prepareState = con.prepareStatement ("SELECT b.flightID, f.departureCity, f.departureTime, f.arrivalCity, f.arrivalTime, b.economySeats, b.economyPrice FROM bookingStatus b , flight f WHERE b.flightID = f.flightID AND b.flightID IN (SELECT flightID FROM flight WHERE departureCity = ? and arrivalCity = ?)");
			prepareState.setInt ( 1, fromResultSet.getInt ( 1 ));
			prepareState.setInt ( 2, toResultSet.getInt ( 1 ));
			countFlights = prepareState.executeQuery();
		}

		catch (Exception e) {
			JOptionPane.showMessageDialog ( null, "Error occured while fetching flight details." );
		}
		
		try	{
			int i = 0, j = 0;
			rowCount = 0;
			
			while (countFlights.next ())
				rowCount++;
	
			/* No flights available for this route */
			if ( rowCount == 0 )	{
				JOptionPane.showMessageDialog (null, "No flights available for this route." );
				this.invalidate ();
				this.setVisible ( false );
				Page2 page = new Page2 ( userObject );
				
			}
			else	{
				data = new String [ rowCount ][ 9 ];
				thisFlight = new JButton [rowCount];
				
				flightsResultSet = prepareState.executeQuery();
				while(flightsResultSet.next())	{
					for(j = 0 ; j < 7 ; j++)	{
						
						Object currResult = flightsResultSet.getObject(j+1);
						
						if ( currResult instanceof Integer )							
							data [i][j] = Integer.toString (( int ) currResult );

						else if (currResult instanceof String)
							data [i][j] = (String) currResult;
						
						else if (currResult instanceof java.sql.Time)	{
							DateFormat df = new SimpleDateFormat ("hh:mm:ss");
							data [i][j] = df.format(currResult);
						}
						
						if (j == 0)	{
							thisFlight[i] = new JButton("Book");
							thisFlight[i].setBounds(50, 170 + i*16, 110, 16);
						}
					}
					i++;
				}
				for(int k = 0 ; k < rowCount ; k++)	{
					add ( thisFlight[k] );
					thisFlight[k].addActionListener(this);
				}

				availableFlights = new JTable ( data, heading );
				availableFlights.setEnabled(false);
				scrollPane = new JScrollPane (
									availableFlights,
									ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
									ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
								);
					
				scrollPane.setBounds( 50, 150, 900, ( 38 + (rowCount-1)*15 ));
				add ( scrollPane );
				this.invalidate();
				this.validate();
				this.repaint();
			}
			
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
	}

// --------------------------------------------------------------------------------
// *****************	
// Part II:	Functional parts.
// *****************	
// --------------------------------------------------------------------------------
	
	Booking ( UserPOJO userObject )
	{
		this.userObject = userObject;
		this.bookingObject = new BookingPOJO ();
		GUIinitializations ();

		fromCombo.setBounds ( 60, 80, 200, 30);
		toCombo.setBounds( 280, 80, 200, 30);
		setCombo ();
		
		setSize ( 1024, 640 );
		setVisible ( true );
	}
		 
	public void actionPerformed( ActionEvent ae )
	{
		if ( ae.getSource() == searchButton )	
			showFlights ();

		for (int i = 0; i < rowCount ; i++ )	{
			if (ae.getSource() == thisFlight[i])	{
				this.invalidate();
				this.setVisible(false);
				this.bookingObject.setFlightID (Integer.parseInt (
						(String) availableFlights.getValueAt ( i, 0 )
						));
				SeatBooking seat = new SeatBooking ( bookingObject, userObject );
			}
		}
	}

	public void itemStateChanged(ItemEvent ie)
	{
		if(ie.getSource () == fromCombo)	{
			try	{
				con = getConnection();
		 		statement = con.createStatement();
		 		
				fromCity = ( String ) fromCombo.getSelectedItem ();
				
				fromResultSet = statement.executeQuery( "SELECT airportID FROM airport WHERE city ='"
										+ fromCity + "'" );
				fromResultSet.next();
			}
			catch(Exception e1)	{
				System.out.println(e1);
			}
	 	}
		else if(ie.getSource () == toCombo)	{
			try	{
				con = getConnection();
				
		 		statement = con.createStatement();
				toCity = ( String ) toCombo.getSelectedItem ();
				toResultSet=statement.executeQuery("SELECT airportID FROM airport WHERE city = '"
										+ toCity + "'");
				toResultSet.next();
			}
			catch(Exception e1)	{
				System.out.println(e1);
			}
		 }	
			
	}
//	public static void main(String args[])
//	{
//		BookingPOJO dummyBook;
//		UserPOJO dummyUser;
//		Booking p = new Booking( dummyBook, dummyUser );
//	}

}	