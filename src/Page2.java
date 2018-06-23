/*
 * 
 * This page provides you the various options that the application has to offer.
 * 
 */

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Page2 extends JFrame implements ActionListener
{
	UserPOJO userObject;
	
	JLabel heading;
	Font fontHeader, fontText;
	JButton bookTicketsButton, cancelTicketsButton, pastStatusButton, logoutButton;	
	
// --------------------------------------------------------------
// ****************
// Part I: Back-end Details.
// ****************
// --------------------------------------------------------------
	
	public void GUIinitializations ()	{
		setLayout ( null );
		setContentPane ( new JLabel ( new ImageIcon ( "C:\\Users\\User\\"
				+ "Desktop\\JavaMini\\Data\\LoginImage.jpg" )));

		fontHeader = new Font ( "ARIAL", Font.BOLD, 40);
		fontText = new Font ("ARIAL", Font.BOLD, 20);
	
		heading = new JLabel ( "Options" );
		heading.setFont ( fontHeader );
		heading.setForeground ( Color.WHITE );
		heading.setBounds ( 150, 70, 200, 50 );
		heading.setBorder(BorderFactory.createMatteBorder ( 0, 0, 2, 0, Color.WHITE));
		add(heading);
				
		bookTicketsButton = new JButton( "Book a Ticket" );		
		bookTicketsButton.setBounds ( 170, 200, 250, 50);
		bookTicketsButton.setFont( fontText );
		bookTicketsButton.setForeground ( Color.WHITE );
		bookTicketsButton.setBackground ( Color.GRAY );
		add ( bookTicketsButton );
		bookTicketsButton.addActionListener ( this );		
		

		cancelTicketsButton = new JButton( "Cancel a Ticket" );
		cancelTicketsButton.setBounds ( 500, 200, 250, 50);
		cancelTicketsButton.setFont( fontText );
		cancelTicketsButton.setForeground ( Color.WHITE );
		cancelTicketsButton.setBackground ( Color.GRAY );
		add ( cancelTicketsButton );
		cancelTicketsButton.addActionListener ( this );
				
		pastStatusButton = new JButton ( "View booked tickets" );
		pastStatusButton.setBounds ( 170, 320, 250, 50);
		pastStatusButton.setFont ( fontText );
		pastStatusButton.setForeground ( Color.WHITE );
		pastStatusButton.setBackground( Color.GRAY );
		add(pastStatusButton);
		pastStatusButton.addActionListener(this);
				
		logoutButton = new JButton("Log out");
		logoutButton.setBounds ( 500, 320, 250, 50 );
		logoutButton.setFont ( fontText );
		logoutButton.setForeground ( Color.WHITE );
		logoutButton.setBackground ( Color.GRAY );
		add ( logoutButton );
		logoutButton.addActionListener ( this );
		
		setSize(1024,640);
		setVisible(true);
	}
	
	
	public void init ( UserPOJO userObject ) {
		this.userObject = userObject;
	}
	
// --------------------------------------------------------------------------------
// *****************	
// Part II:	Functional parts.
// *****************	
// --------------------------------------------------------------------------------
	
	Page2( UserPOJO userObject )	{
		init ( userObject );
		GUIinitializations ();		
	}	

	public void actionPerformed(ActionEvent ae)	{

		try	{
		
			if ( ae.getSource () == bookTicketsButton )	{

				this.invalidate ();
				this.setVisible ( false );
				Booking booking = new Booking ( userObject );
			}

			else if ( ae.getSource () == cancelTicketsButton )	{

				this.invalidate ();
				this.setVisible ( false );
				SeatCancellation cancel = new SeatCancellation ( userObject );
			}
			
			else if ( ae.getSource () == pastStatusButton )	{
				
				PastTransactions past = new PastTransactions ( userObject );
			}
			
			else if ( ae.getSource () == logoutButton )	{

				this.invalidate ();
				this.setVisible ( false );
				Home home=new Home ();
			}
		}
		catch(Exception e)	{
			JOptionPane.showMessageDialog ( null, "An error occurred." );
		}
	}	
	//public static void main(String args[])	{
		//Page2 page = new Page2 ( 1006 );
	//}
}