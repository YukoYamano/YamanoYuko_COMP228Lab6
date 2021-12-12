package exercise1;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class GameScreen extends JFrame{

	private Game game;
	JLabel lbl[]=new JLabel[2];
	String lblNames[]= {"Game ID:","Game Title"};
	int txtWidths[]={9,20,20,20,20,2,6};
	  
	//data access buttons
	JButton btn[]=new JButton[5];
	String btnNames[]={"Add New","Save","Update","Delete","Display"};
	
	//navigator buttons
	JButton nav[]= new JButton[4];
	String navNames[]={"First","Previous","Next","Last"}; 
	JTextField txt[]=new JTextField[2];
	Font font;
	
	GridBagLayout gridBag;
	private GridBagConstraints constraints; // constraints of this layout
	JPanel screen;
	
	
	
	//Construct the frame
	public GameScreen() {
		//
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		this.setSize(500, 300);
		this.setTitle("Game Information");
		//	Set the GridBagLayout for the frame
		screen = new JPanel();
		gridBag = new GridBagLayout();
		screen.setLayout(gridBag);
		
		
		
		
		//create data entry screen
		for(int i = 0; i<lbl.length;i++)
		{
			lbl[i] = new JLabel(lblNames[i]);
			lbl[i].setFont(new Font("Arial", Font.BOLD, 24));
			txt[i]=new JTextField(txtWidths[i]);
			txt[i].setFont(new Font("Arial", Font.BOLD, 24));
			//add labels 
			constraints = new GridBagConstraints();
			addComponent(lbl[i], i, 0, 1, 1,
					GridBagConstraints.BOTH,
					GridBagConstraints.EAST,new Insets(3, 3, 3, 3));
			
			//add text fields
			addComponent(txt[i], i, 1, 1, 1,GridBagConstraints.NONE,
					GridBagConstraints.WEST,new Insets(3, 3, 3, 3));
			
		}
		
	
		
		for(int i = 0; i<btn.length;i++)
		{
			btn[i] = new JButton(btnNames[i]);
			btn[i].setFont(new Font("Arial", Font.BOLD, 24));
			
			addComponent(btn[i], i, 2, 1, 1,
					GridBagConstraints.HORIZONTAL,
					GridBagConstraints.CENTER,new Insets(3, 3, 3, 3));

			btn[i].addActionListener(new ButtonHandler());
		}
		//navigators
		JPanel pSouth = new JPanel();
		for(int i = 0; i<nav.length;i++)
		{
			nav[i] = new JButton(navNames[i]);
			nav[i].setFont(new Font("Arial", Font.BOLD, 24));
		
			pSouth.add(nav[i]);
			nav[i].addActionListener(new NavigatorHandler());
		}
		c.add(screen,BorderLayout.CENTER);
		c.add(pSouth,BorderLayout.SOUTH);
		//load and show the first record
		try
		{
			game= new Game();
			showRow();
		}
		catch(Exception e)
		{
			//
		}
		btn[1].setEnabled(false);
		//set the focus to txtName when frame is opened
		addWindowListener( new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
				txt[0].requestFocus();
			}
		} );
		pack();//size window to preferred size
	} //end of constructor
	
	//Overridden so we can exit when window is closed
		protected void processWindowEvent(WindowEvent e) {
			super.processWindowEvent(e);
			if (e.getID() == WindowEvent.WINDOW_CLOSING) {
				System.exit(0);
			}
		}
		
		// method to set constraints on 
		private void addComponent( Component component,
		      int row, int column, int width, int height, int fill,
		      int anchor, Insets insets)
		{
			constraints.gridx = column; // set gridx
		    constraints.gridy = row; // set gridy
		    constraints.gridwidth = width; // set gridwidth
		    constraints.gridheight = height; // set gridheight
		    constraints.fill = fill; //specify the fill
		    constraints.anchor = anchor; //set the anchor
		    constraints.insets = insets; //set the insets
		    // set constraints
		    gridBag.setConstraints( component, constraints ); 
		    screen.add( component ); // add component
		} // end method addComponent
		//
		//read values from text fields
		public void readRow(String row[])
		{
			for(int i=0;i<txt.length;i++)
			{
				row[i]=txt[i].getText();
			}
		    
		}
		
		//show the row in text fields
		public void showRow()
		{
			txt[0].setText(game.getGameID());
			txt[1].setText(game.getGameTitle());
			
		}
		class ButtonHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				String name = e.getActionCommand();
				if(name.equals(btnNames[0])) //add new
				{
					for(int i=0;i<txt.length;i++)
					{
						txt[i].setText("");
					}
					txt[0].requestFocus();
					btn[0].setEnabled(false);
					btn[2].setEnabled(false);
					btn[3].setEnabled(false);
					btn[1].setEnabled(true);
				}
				else if (name.equals(btnNames[1])) //save
				{
					String row[] = new String[7];
					readRow(row);
					try
					{
						game = new Game();
						
						game.saveStudent(row);
					}
					catch (Exception ex)
					{ ex.printStackTrace();}
					btn[1].setEnabled(false);
					btn[0].setEnabled(true);
					btn[2].setEnabled(true);
					btn[3].setEnabled(true);
				}
				else if(name.equals(btnNames[2])) //update
				{
					String row[] = new String[7];
					readRow(row);
					game.updateGame(row);
				}
				else if(name.equals(btnNames[3])) //delete
				{
					game.deleteGame();
				}else if(name.equals(btnNames[4]))//add display
				{
					//////*****************************************************************************
					game.display();
					GameData gd;
					try {
						gd = new GameData();
						gd.displayResults();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
		  
			
			} //actionPerformed
		} //end of ButtonHandler
		//
		class NavigatorHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				String name = e.getActionCommand();
				if(name.equals(navNames[0])) //add new
				{
					String row[] = new String[7];
					row=game.moveToFirst();
					showRow();
				}
				else if (name.equals(navNames[1])) //save
				{
			  
					String row[] = new String[7];
					row=game.moveToPrevious();
					showRow();
				}
				else if(name.equals(navNames[2])) //update
				{
					String row[] = new String[7];
					row=game.moveToNext();
					showRow();
				}
				else if(name.equals(navNames[3])) //delete
				{
					String row[] = new String[7];
					row=game.moveToLast();
					showRow();
				}
			  			
			} //actionPerformed
		} //NavigatorHandler
		
		public static void main(String[] args) {
			GameScreen frame = new GameScreen();
			frame.setFont(new Font("Arial", Font.BOLD, 24));
			//Validate frames that have preset sizes
			//Pack frames that have useful preferred size info, e.g. from their layout
			
			//Center the window
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = frame.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
			frame.setVisible(true);
		  	
		}
		
}
