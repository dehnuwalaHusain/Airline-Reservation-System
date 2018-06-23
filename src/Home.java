/*
 * 
 * This is the home page.
 * It's useless, just like this documentation here.
 * 
 * It leads you to the Login OR SignUp page. (Login.java / Register.java)
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Home extends JFrame implements ActionListener,Runnable {

// --------------------------------------------------------------
	// DECLARATIONS
	UserPOJO dummy;
	
	JLabel headerLabel, aboutLabel, introLabel1, introLabel2, introLabel3, introLabel4, introLabel5;
	JButton loginButton, signUpButton;
	Font fontHeader, fontAbout, fontIntroText;

	int x1;
	Thread thread;
 	
	String about, introText1, introText2, introText3, introText4, introText5;
	
// --------------------------------------------------------------
	
	protected void GUIinitializations ()	{
		
		about = "About";
		introText1 = "Hey!";
		introText2 = "This is your resolve for all the flight booking needs";
		introText3 = "you might have in India.";
		introText4 = "Log in if you've already registered with us.";
		introText5 = "Else, press sign-up.";
		
		fontHeader = new Font ( "ARIAL", Font.BOLD, 40);
		fontIntroText = new Font ("ARIAL", Font.BOLD, 20);
		
		setLayout (null);
		setContentPane(new JLabel(new ImageIcon("C:\\Users\\User\\Desktop\\JavaMini\\Data\\LoginImage.jpg")));
		
		headerLabel = new JLabel ( "Journey" );
		headerLabel.setBounds ( 150, 70, 200, 50 );
		headerLabel.setFont ( fontHeader );
		headerLabel.setForeground ( Color.WHITE );
		add ( headerLabel );
		headerLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE));
		
		introLabel1 = new JLabel (introText1);
		introLabel1.setBounds( 150, 160, 150, 30);
		introLabel1.setFont(fontIntroText);
		introLabel1.setForeground(Color.WHITE);
		add(introLabel1);
		
		introLabel2 = new JLabel (introText2);
		introLabel2.setBounds( 150, 190, 1000, 30);
		introLabel2.setFont(fontIntroText);
		introLabel2.setForeground(Color.WHITE);
		add(introLabel2);
		
		introLabel3 = new JLabel (introText3);
		introLabel3.setBounds( 150, 210, 1000, 30);
		introLabel3.setFont(fontIntroText);
		introLabel3.setForeground(Color.WHITE);
		add(introLabel3);
		
		introLabel4 = new JLabel (introText4);
		introLabel4.setBounds( 150, 240, 1000, 30);
		introLabel4.setFont(fontIntroText);
		introLabel4.setForeground(Color.WHITE);
		add(introLabel4);
		
		introLabel5 = new JLabel (introText5);
		introLabel5.setBounds( 150, 270, 1000, 30);
		introLabel5.setFont(fontIntroText);
		introLabel5.setForeground(Color.WHITE);
		add(introLabel5);
		
		loginButton = new JButton("LOGIN");
		loginButton.setBounds ( 170, 370, 120, 40);
		loginButton.setForeground( Color.WHITE );
		loginButton.setBackground( Color.GRAY );
		loginButton.addActionListener(this);
		add ( loginButton );
		
		signUpButton = new JButton("SIGN UP");
		signUpButton.setBounds ( 380, 370, 120, 40);
		signUpButton.setForeground( Color.WHITE );
		signUpButton.setBackground( Color.GRAY );
		signUpButton.addActionListener(this);
		add ( signUpButton );
		
		setSize ( 1024, 640);
		setVisible (true);
	}
	
// --------------------------------------------------------------
	Home ()	{

		dummy = new UserPOJO ();
		/* Marquee settings & Thread for the same.	*/
		// -----------
		x1 = -140;
		thread = new Thread ( this );
		thread.start();
		// -----------
		
		GUIinitializations ();
	}

	public void paint ( Graphics g ) {
		
     	super.paint ( g );
		g.setColor ( Color.YELLOW );
		g.setFont ( new Font ( "ARIAL", Font.BOLD, 15 ));
		g.drawString ( "Flat 30% Off on today's flights..Hurry Up !!", x1, 70);
	 }
	
	public void run() {
		
		try {
			for ( int iter = 1 ; iter <= 1020 ; iter++) {
				if ( iter >= 1020 ) {
					x1 =- 140;
					iter = 0;
				}
				x1 += 1; 
				thread.sleep ( 10 );
				repaint ();
			}
		}
		catch ( Exception e ) { 
			System.out.println ( e ); 
		}
	}
	
	@Override
	public void actionPerformed ( ActionEvent ae ) {
		
		if ( ae.getSource () == loginButton)	{
			this.invalidate ();
			this.setVisible ( false );
			new Login ( "-1", dummy );
		}
		else if ( ae.getSource () == signUpButton )	{
			this.invalidate ();
			this.setVisible ( false );
			new Register ();
		}
	}
	
	public static void main(String[] args) {
		
		Home home = new Home ();
	}
}