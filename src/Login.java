/*
 * HI!
 * This is the Login page for the airline reservation system. 
 * You log in here, after you've registered yourself as a user and start booking flights.
 * 
 * Login takes effect from essentially two values from the `user` table in the `airline` database, i.e.
 * 		`userID` and `password`
 * Givent below is the description of `user` table.
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
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

// --------------------------------------------------------------------------------
	// DECLARATIONS
	
	UserPOJO userObject;
	
	JLabel head, username, password;
	JLabel img;
	JTextField user;
	JPasswordField pass;
	JButton login, reset, signUp;
	Font fontText;
	
	AdminMenu adminMenu;
	Page2 page;
	
// --------------------------------------------------------------------------------
// *****************
// Part I: Back-end details.
// *****************	
// --------------------------------------------------------------------------------
	public void GUIinitializations ( String defaultUserID )	{
		
		setLayout(null);
		setContentPane ( new JLabel ( new ImageIcon ( "C:\\Users\\User\\Desktop"
				+ "\\JavaMini\\Data\\LoginImage.jpg" )));
		
		fontText = new Font ( "ARIAL", Font.BOLD, 20);
		
		head = new JLabel ( "LOGIN" );
		head.setBounds ( 400, 70, 150, 50 );
		head.setFont ( new Font ( "ARIAL", Font.BOLD, 40 ));
		head.setForeground ( Color.WHITE );
		add ( head );
		
		username = new JLabel ( "Username : " );
		username.setBounds ( 250, 170, 140, 40);
		username.setFont ( fontText );
		username.setForeground ( Color.WHITE );
		add ( username );
	
		user = new JTextField ( "" );
		user.setBounds( 450, 170, 210, 40);
		if (Integer.parseInt ( defaultUserID ) != -1)
			user.setText ( defaultUserID );
		add ( user );
	
		password = new JLabel( "Password: " );
		password.setBounds ( 250, 250, 140, 40 );
		password.setFont ( fontText );
		password.setForeground ( Color.WHITE );
		add ( password );
	
		pass = new JPasswordField( "" );
		pass.setBounds( 450, 250, 210, 40);
		add ( pass );
		
		login = new JButton( "LOGIN" );
		login.setBounds ( 300, 370, 120, 40);
		login.setForeground ( Color.WHITE );
		login.setBackground ( Color.GRAY );
		add ( login );
		
		reset = new JButton ( "RESET" );
		reset.setBounds ( 460, 370, 120, 40);
		reset.setForeground ( Color.WHITE );
		reset.setBackground ( Color.GRAY );
		add ( reset );
		
		signUp = new JButton ( "Or, sign up!" );
		signUp.setBounds( 620, 370, 120, 40 );
		signUp.setForeground ( Color.WHITE );
		signUp.setBackground ( Color.GRAY );
		add ( signUp );
	
		login.addActionListener ( this );
		reset.addActionListener ( this );
		signUp.addActionListener ( this );
	
		setSize ( 1024, 640 );
		setVisible ( true );
	}
	
	public void loginUser ()	{
		try
		{
			Class.forName( "com.mysql.jdbc.Driver" );
			Connection con = DriverManager.getConnection ( "jdbc:mysql://localhost:3307/airline",
														"root", "1234" );
			Statement st = con.createStatement ();
			String userID;
			String password;
			
			userID = user.getText ();
			userObject.setUserID ( Integer.parseInt ( userID ));
			
			password = new String ( pass.getPassword ( ));
			userObject.setPassword ( password );
			
			ResultSet rs1 = st.executeQuery ( "SELECT password FROM user WHERE userID = " + userObject.userID );

			if( rs1.next( ))	{
				
				if( userObject.password.equals ( rs1.getString( 1 )))	{
					
					JOptionPane.showMessageDialog ( null, "Login Successful" );
					this.invalidate ( );
					this.setVisible ( false );
					if ( Integer.parseInt ( userID ) == 1005)
						adminMenu = new AdminMenu ();
					else
						page = new Page2 ( userObject );
				}

				else if (! ( password.equals ( rs1.getString ( 1 ))))
					JOptionPane.showMessageDialog ( null, "Incorrect Username or Password" );
			}
			con.close();
		}
		catch ( Exception e )	{
			JOptionPane.showMessageDialog ( null, "An error occurred." );
		}
	}
	
// --------------------------------------------------------------------------------
// *****************	
// Part II:	Functional parts.
// *****************	
// --------------------------------------------------------------------------------
	
	Login ()	{
	}
	
	Login ( String defaultUserID, UserPOJO userObject ) {
		
		this.userObject = userObject;
		this.userObject.setUserID ( Integer.parseInt ( defaultUserID ));
		GUIinitializations ( Integer.toString( userObject.userID ));
	}
	
	public void actionPerformed(ActionEvent ae) {
		if( ae.getSource () == login )
			loginUser ();
		
		else if( ae.getSource () == reset )	{
			user.setText("");
			pass.setText("");
		}
		else if( ae.getSource () == signUp ) {
			this.invalidate ( );
			this.setVisible ( false );
			Register register = new Register ();
		}
	}
	
	public static void main(String[] args) {
		
		UserPOJO dummy = new UserPOJO ();
		Login login=new Login ( "-1", dummy );
	}
}
