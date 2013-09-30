/*
Sam Nebel, snebel
Displays a HanoiComponentSolver and executes an animation
of the puzzle being solved, with the moves printed at the console.
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class SolveHanoi
{
    public final static int WIDTH = 600;
    public final static int HEIGHT = 500;

    public static void main(String[] args)
    {
	String num = JOptionPane.showInputDialog("Enter number of disks to start with");
	final int n = Integer.parseInt(num);
	final HanoiComponentSolver h = new HanoiComponentSolver(n);
	h.setPreferredSize(new Dimension(WIDTH,HEIGHT));

	final JPanel panel = new JPanel();
	panel.add(h);

	final JFrame frame = new JFrame("Towers of Hanoi");
	frame.setSize(WIDTH,HEIGHT);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());
	frame.add(panel, BorderLayout.CENTER);
	frame.setVisible(true);

	class TimerListener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {
		//correct moves executed in paintcomponent
		h.repaint();
	    }
	}
      	ActionListener s = new TimerListener();
	Timer t = new Timer(1, s);	
	t.start();
	if (h.solved())
	    t.stop();
    }
}