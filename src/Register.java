/*
 * HI!
 * This is the registration form for the airline reservation system.
 * Here, a new user gets to sign-up / create a new account.
 * 
 * When the registration takes place, "user" table gets affected.
 * Given below is the description of 'user' table.
 * 
 * mysql> desc user;
 * +-----------+--------------+------+-----+---------+----------------+
 * | Field     | Type         | Null | Key | Default | Extra          |
 * +-----------+--------------+------+-----+---------+----------------+
 * | userID    | int(11)      | NO   | PRI | NULL    | auto_increment |
 * | firstName | varchar(45)  | YES  |     | NULL    |                |
 * | lastName  | varchar(45)  | YES  |     | NULL    |                |
 * | address   | varchar(200) | YES  |     | NULL    |                |
 * | email     | varchar(45)  | YES  | UNI | NULL    |                |
 * | age       | int(11)      | YES  |     | NULL    |                |
 * | contact   | varchar(10)  | YES  |     | NULL    |                |
 * | password  | varchar(45)  | YES  |     | NULL    |                |
 * +-----------+--------------+------+-----+---------+----------------+
 * 
 * 
 * For those interested in minor understanding of this source code,
 * 	it is divided into 2 parts, first is the messed up (ugly) part containing
 * 	back-end functioning.
 *  Second, calls to those functions in part, reading the names of which is 
 *  enough for a reader wanting to understand the flow of program.
 *  So, I'd suggest scrolling right down to main and reading the methods contained
 *  in the Part 2 of this source code.
 *  
 * For those interested in learning the back-end JDBC connectivity and functioning
 * 	of KeyInterfaces used, dive into the Part 1 function calls as you go on reading
 *  the source from main -> | part2 -> part1 | 
 * 								^			 |	
 *  							|			 |
 *  							--------------
 */

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Register extends JFrame implements ActionListener, KeyListener
{
	UserPOJO userObject;
	
	JLabel heading, firstNameLabel, lastNameLabel, ageLabel, addressLabel, emailLabel, contactLabel, passwordLabel;
	JTextField firstNameText, lastNameText, ageText, emailText, contactText, passwordText;
	JTextArea addressText;
	Font headingFont, labelFont, textFont;
	JButton saveButton, homeButton, resetButton;
	
	Connection con;
	PreparedStatement pstmt, pstmt3;

// -------------------------------------------------------------------------------

/* *************************
 * Part 1: I'd suggest, don't see this.		
 * *************************
*/
// -------------------------------------------------------------------------------
	
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	@Override
	public void keyPressed(KeyEvent k) {

		if(k.getSource() == firstNameText || k.getSource() == lastNameText)		//name
  		{
			while(true)
   			{
				int key = k.getKeyChar();
 				int key1 = k.getKeyCode();
 				if( isAlpha ( key ))
    				break;

		 		else if ( isShiftCapsBackSpace ( key1 ))
		        	break;

		    	else	{
		            JOptionPane.showMessageDialog(this,"Name contains an invalid character.",
		            		"Invalid Input",JOptionPane.ERROR_MESSAGE);
		            break;
		    	}
   			}
  		}

		else if(k.getSource()== addressText )		{
			while(true)		{
				int key = k.getKeyChar();
				int key1 = k.getKeyCode();
         	
				if ( isAlpha(key) || isNumeric (key) )
					break;

				else if( isShiftCapsBackSpace(key1) || key1 == KeyEvent.VK_COMMA || key1 == KeyEvent.VK_ENTER)
		        	break;

				else	{
					JOptionPane.showMessageDialog(this,"Address contains an invalid character.",
							"Invalid Input",JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
		}
		
		else if (k.getSource()== ageText || k.getSource() == contactText)	{
			while(true)		{
				
				int key = k.getKeyCode();
				if ( isNumeric ( key ) || key == KeyEvent.VK_BACK_SPACE)
					break;
				
				else	{
					JOptionPane.showMessageDialog(this,"Age or Contact field(s) contain invalid character(s).",
							"Invalid Input",JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
		}      
	}
	
	public void emptyFields ()	{
		firstNameText.setText ( "" );
		lastNameText.setText ( "" );
		ageText.setText ( "" );		
		addressText.setText ( "" );
		emailText.setText ( "" );
		contactText.setText ( "" );
		passwordText.setText ( "" );
	}	

	public void registerUser ()	{
		int age = 0, flag = 0;
		Long mob = 0L;
		String userID = "";
		
		userObject.setFirstName ( firstNameText.getText ( ));
		userObject.setLastName ( lastNameText.getText ( ));
		
		try	{
			age = Integer.parseInt ( ageText.getText ( ));
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog (null, "Age field must not contain letters or special symbols." );
			flag = 1;
		}
		
		userObject.setAge ( age );
		
		userObject.setAddress ( addressText.getText ( ));
		userObject.setEmail ( emailText.getText ( ));
		
		if (! userObject.getEmail().contains ( "@" ) || ! userObject.getEmail().contains ( "." ) || ! userObject.getEmail().contains ( ".com" ))
		{
			JOptionPane.showMessageDialog (null, "Please enter a valid email address." );
			flag = 1;
		}
		
		try	{
			mob = Long.parseLong ( contactText.getText ( ));
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog (null, "Contact no. field must not contain letters or special symbols." );
			flag = 1;
		}
		
		userObject.setContact ( mob );
		userObject.setPassword ( passwordText.getText ( ));
		
		if ( flag != 1 )	{
			try
			{
				Class.forName ( "com.mysql.jdbc.Driver" );
				con = DriverManager.getConnection ( "jdbc:mysql://localhost:3307/airline", "root", "1234" );
			}
			catch ( Exception e )	{
				JOptionPane.showMessageDialog (null, "Database Not Connected.");
			}
			try	{
				pstmt =con.prepareStatement
						("INSERT INTO user(firstName,lastName,address,email,age,contact,password)"
								+ "VALUES(?,?,?,?,?,?,?)");
				pstmt.setString ( 1, userObject.getFirstName ( ));
				pstmt.setString ( 2, userObject.getLastName ( ));
				pstmt.setInt ( 5, age );
				pstmt.setString ( 3, userObject.getAddress ( ));
				pstmt.setString ( 4, userObject.getEmail ( ));
				pstmt.setLong ( 6, mob );
				pstmt.setString( 7, userObject.getPassword ( ));
				pstmt.executeUpdate ();
			}
			catch (Exception e)	{
				JOptionPane.showMessageDialog (null, "Registration failed.");
			}
			try	{
				pstmt=con.prepareStatement ( "SELECT userID FROM user WHERE email=?" );
				pstmt.setString ( 1, userObject.getEmail ( ));
				ResultSet rs1 = pstmt.executeQuery ();
				if (rs1.next())
					userID = rs1.getString(1);
	
				String affirmation = "Hello " + userObject.getFirstName() + ", you have been registered as a user.\n"
						+ "Your userID is: " + userID;
				JOptionPane.showMessageDialog ( null, affirmation );
				con.close();
				this.invalidate ( );
				this.setVisible ( false );
				Login login = new Login ( userID, userObject );
			}
			catch ( Exception e ) {
				JOptionPane.showMessageDialog (null, "userID fetch failed.");
			}
		}
	}

	
	public Boolean ifFieldsEmpty ()	{
		if ( firstNameText.getText().isEmpty() || lastNameText.getText().isEmpty() ||
				ageText.getText().isEmpty() || addressText.getText().isEmpty() || 
				emailText.getText().isEmpty() || contactText.getText().isEmpty() ||
				passwordText.getText().isEmpty())

			return true;
		return false;
	}
	
	public Boolean isAlpha( int key )	{
		if((key >= 'A' && key <= 'Z' ) || (key >= 'a' && key <='z' ))
			return true;
		return false;
	}
	
	public Boolean isShiftCapsBackSpace ( int key1 )	{
		if(key1==KeyEvent.VK_SPACE || key1 == KeyEvent.VK_SHIFT || key1 == KeyEvent.VK_CAPS_LOCK ||
				 key1 == KeyEvent.VK_BACK_SPACE)
			return true;
		return false;
	}
	
	public Boolean isNumeric (int key)	{
		
		if (key >= '0' && key <= '9')
			return true;
		return false;
	}
	
	public void makeWindow ()	{
		
		headingFont = new Font ( "Arial", Font.BOLD, 25 );
		labelFont = new Font ( "ARIAL", Font.BOLD, 20 );
		textFont = new Font ( "ARIAL", Font.PLAIN, 20 );

		setLayout ( null );
		setContentPane ( new JLabel ( new ImageIcon ( "C:\\Users\\User\\Desktop\\JavaMini\\Data\\LoginImage.jpg" )));
		
		heading=new JLabel ( "Registration Form", JLabel.CENTER );
		Font headingFont = new Font ( "Arial", Font.BOLD, 30 );
		heading.setBounds ( 10, 40, 800, 37 );
		heading.setFont ( headingFont );
		heading.setForeground ( Color.WHITE );
		add ( heading );
				
		firstNameLabel = new JLabel ( "First Name :" );
		firstNameLabel.setBounds ( 30, 100, 200, 30 );
		firstNameLabel.setFont ( labelFont );
		firstNameLabel.setForeground ( Color.WHITE );
		firstNameText=new JTextField ();
		firstNameText.setBounds ( 280, 100, 200, 30 );
		firstNameText.setFont ( textFont );
		firstNameText.addKeyListener(this);
		add ( firstNameLabel );
		add ( firstNameText );

		lastNameLabel = new JLabel ( "Last Name :" );
		lastNameLabel.setBounds ( 30, 140, 200, 30 );
		lastNameLabel.setFont ( labelFont );
		lastNameLabel.setForeground ( Color.WHITE );
		lastNameText = new JTextField ();
		lastNameText.setBounds ( 280, 140, 200, 30 );
		lastNameText.setFont ( textFont );
		lastNameText.addKeyListener(this);
		add ( lastNameLabel );
		add ( lastNameText );
	
	
		ageLabel = new JLabel ( "Age :" );
		ageLabel.setBounds ( 30, 180, 200, 30 );
		ageLabel.setFont ( labelFont );
		ageLabel.setForeground ( Color.WHITE );
		ageText=new JTextField ();
		ageText.setBounds( 280, 180, 200, 30 );
		ageText.setFont ( textFont );
		ageText.addKeyListener(this);
		add( ageLabel );
		add( ageText );
		
		addressLabel = new JLabel ( "Address :" );			
		addressLabel.setBounds ( 30, 220, 200, 30 );
		addressLabel.setFont ( labelFont );
		addressLabel.setForeground ( Color.WHITE );
		addressText=new JTextArea ();
		addressText.setBounds ( 280, 220, 200, 60 );
		addressText.setFont ( textFont );
		addressText.addKeyListener(this);
		add ( addressLabel );
		add ( addressText );
		
		emailLabel = new JLabel ( "Email :" );
		emailLabel.setBounds ( 30, 290, 200, 30 );
		emailLabel.setFont ( labelFont );
		emailLabel.setForeground ( Color.WHITE );
		emailText=new JTextField ();
		emailText.setBounds ( 280, 290, 200, 30 );
		emailText.setFont ( textFont );

		add ( emailLabel );
		add ( emailText );
	
		contactLabel = new JLabel ( "Contact No :" );
		contactLabel.setBounds ( 30, 330, 200, 30 );
		contactLabel.setFont ( labelFont );
		contactLabel.setForeground ( Color.WHITE );
		contactText = new JTextField ();
		contactText.setBounds ( 280, 330, 200, 30);
		contactText.setFont ( textFont );
		contactText.addKeyListener(this);
		add ( contactLabel );
		add ( contactText );

		passwordLabel = new JLabel ( "Password :" );
		passwordLabel.setBounds ( 30, 370, 200, 30 );
		passwordLabel.setFont ( labelFont );
		passwordLabel.setForeground ( Color.WHITE );
		passwordText = new JTextField ();
		passwordText.setBounds ( 280, 370, 200, 30 );
		passwordText.setFont ( textFont );
		add ( passwordLabel );
		add ( passwordText );


		saveButton = new JButton ( "Save" );		
		saveButton.setBounds ( 100, 500, 100, 30 );
		//saveButton.setFont(headingFont);
		saveButton.setBackground ( Color.GRAY );
		saveButton.setForeground ( Color.WHITE );
		add ( saveButton );
		saveButton.addActionListener ( this );		
				
		resetButton = new JButton ( "Reset" );
		resetButton.setBounds(220,500,100,30);
		resetButton.setBackground ( Color.GRAY );	
		resetButton.setForeground( Color.WHITE );
		add ( resetButton );
		resetButton.addActionListener ( this );
				
		homeButton = new JButton( "Home" );
		homeButton.setBounds ( 340, 500, 100, 30 );
		//homeButton.setFont(headingFont);
		homeButton.setBackground ( Color.GRAY );	
		homeButton.setForeground ( Color.WHITE );
		add ( homeButton );
		homeButton.addActionListener ( this );
		
		setSize(1024,640);
		setVisible(true);
	}
	
	
// -------------------------------------------------------------------------------
	
/* ******************
 * Part 2:
 * SEE THIS!!!!
 * THIS IS ALL YOU NEED TO KNOW!
 * Function names are self-explanatory. If you feel the need to verify what it does,
 	 	(sigh) go ahead and do it by reading the ~unreadable~ code above.
 * ******************
*/
// -------------------------------------------------------------------------------
	
	public void actionPerformed(ActionEvent ae)	{
		
		if(ae.getSource() == saveButton)	{
			if (ifFieldsEmpty())
				JOptionPane.showMessageDialog (null, "All fields must be filled." );
			else
				registerUser();
		}
		else if(ae.getSource() == resetButton)	{
			emptyFields ();
		}
		else if (ae.getSource() == homeButton )	{
			Home home = new Home ();
		}
	}
	
	Register() 	{
		userObject = new UserPOJO ();
		makeWindow ();
	}

// ------------------------------------------------------------------------
	
//	public static void main(String args[]) 	{
//		Register register = new Register();
//	}

}

// ------------------------------------------------------------------------