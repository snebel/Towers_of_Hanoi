/*
Sam Nebel, snebel

Displays Towers of Hanoi in a JFRame and lets the user solve it himself.
Moves are kept track of in console.
To see animation of solution, run SolveHanoi.java
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class PlayHanoi
{
    public final static int WIDTH = 600;
    public final static int HEIGHT = 500;

    public static void main(String[] args)
    {
	//asks user how many disks he wants to start with
	String num = JOptionPane.showInputDialog("Enter number of disks to start with");
	final int n = Integer.parseInt(num);
	final HanoiComponent h = new HanoiComponent(n); //creates component
	h.setPreferredSize(new Dimension(WIDTH,HEIGHT));

	final JPanel panel = new JPanel();
	panel.add(h);

	//Was trying to add this so that animation of solution
	//could be viewed in same window but can't get it to work.
	final JPanel panel1 = new JPanel();
	final HanoiComponentSolver h1 = new HanoiComponentSolver(h);
	h1.setPreferredSize(new Dimension(WIDTH,HEIGHT));
	panel1.add(h1);

	final JPanel buttonPanel = new JPanel();
	/*JButton solve = new JButton("Solve!"); //this does not currently work
	buttonPanel.add(solve);*/
	//These buttons execute the appropriate move between two pegs
	JButton AB = new JButton("1 <-> 2");
	buttonPanel.add(AB);
	JButton AC = new JButton("1 <-> 3");
	buttonPanel.add(AC);
	JButton BC = new JButton("2 <-> 3");
	buttonPanel.add(BC);
	JButton reset = new JButton("Reset");
	buttonPanel.add(reset);

	final JFrame frame = new JFrame("Towers of Hanoi");
	frame.setSize(WIDTH,HEIGHT);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());
	frame.add(buttonPanel, BorderLayout.NORTH);
	frame.add(panel, BorderLayout.CENTER);
	frame.setVisible(true);

	class TimerListener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {	     
		frame.remove(panel); //cannot get this to work... original
		frame.add(panel1);  //HanoiComponent still displayed
		h1.repaint();
	    }
	}
    /* 	ActionListener s = new TimerListener();
	solve.addActionListener(s); */

	//the following listeners are added to the buttons above to
	//make the correct move.
	class ABListener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {
		try
		{
		    h.moveSourcetoTemp();
		    h.repaint();
		}
		catch(Exception e){}
	    }
	}	
      	ActionListener ab = new ABListener();
	AB.addActionListener(ab);

	class ACListener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {
		try
		{
		    h.moveSourcetoTarget();
		    h.repaint();
		}
		catch(Exception e){}
	    }
	}	
      	ActionListener ac = new ACListener();
	AC.addActionListener(ac);


	class BCListener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {
		try
		{
		    h.moveTemptoTarget();
		    h.repaint();
		}
		catch(Exception e){}
	    }
	}	
      	ActionListener bc = new BCListener();
	BC.addActionListener(bc);

	
	class ResetListener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {
		try
		{
		    h.reset();
		    h.repaint();
		}
		catch(Exception e){}
	    }
	}
	ActionListener r = new ResetListener();
	reset.addActionListener(r);
	
    }
}