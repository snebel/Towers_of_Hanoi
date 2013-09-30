/*
Sam Nebel, snebel
Hanoi board to be displayed in a JFrame.
*/

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class HanoiComponent extends JComponent
{
    //fields
    //not private so can be accessed in PlayHanoi
    ArrayList<ColorCircle> source;
    ArrayList<ColorCircle> temp;
    ArrayList<ColorCircle> target;
    int num; //number of disks

    //constants
    //3 circles representing the wooden pegs
    static ColorCircle PEG1 = new ColorCircle(12, new Point(100, 250), new Color(156, 93, 82));
    static ColorCircle PEG2 = new ColorCircle(12, new Point(300, 250), new Color(156, 93, 82));
    static ColorCircle PEG3 = new ColorCircle(12, new Point(500, 250), new Color(156, 93, 82));
    static int n = 0; //tracks # of moves

    //constructor
    public HanoiComponent(int n)
    {
	source = new ArrayList<ColorCircle>();
	temp = new ArrayList<ColorCircle>();
	target = new ArrayList<ColorCircle>();

	//adds n disks to "source" in the correct order
	//and sets the radius of largest one to be close
	//to JFrame border
	double unit = 88.0/n;
	for (int i=1; i<=n; i++)
	    source.add(new ColorCircle(12+i*unit, PEG1.getCenter()));
	
	num = n;
    }

    public void paintComponent(Graphics g)
    {
	Graphics2D g2 = (Graphics2D) g;
	//paints largest disks first
	for (int i=source.size()-1; i>=0; i--)
	    source.get(i).cfill(g2);

	for (int i=temp.size()-1; i>=0; i--)
	    temp.get(i).cfill(g2);

	for (int i=target.size()-1; i>=0; i--)
	    target.get(i).cfill(g2);

	//draw the brown pegs last
	PEG1.cfill(g2);
	PEG2.cfill(g2);
	PEG3.cfill(g2);
	
	if (solved())
	    System.out.println("You solved this puzzle in "+n+" steps.");	
    }

    //Determines if a legal move can be made from Original to Destination
    public boolean isLegal(ArrayList<ColorCircle> orig, ArrayList<ColorCircle> dest)
    {
	if (orig.size()==0)
	    return false;
	else if (dest.size()==0)
	    return true;
	else
	    return (orig.get(0).lessThan(dest.get(0)));	
    }	    

    //makes legal move between source and temp
    public void moveSourcetoTemp() 
    {
	try{Thread.sleep(100);}catch(InterruptedException e){}
	
	if (isLegal(source, temp)) //move disk from source to temp
	{
	    ColorCircle a = source.get(0); //grabs smallest disk from
	                                   //from first peg
	    a.move(200); //moves its center to location of middle peg
	    source.remove(0);
	    temp.add(0, a); //adds the disk to the new correct peg
	    n++; //increments the number of moves by 1
	    System.out.println(n+". peg 1 to peg 2");

	    //THIS PROCEDURE IS REPEATED IN THE FOLLOWING METHODS
	}
	else if (isLegal(temp, source))
	{
	    ColorCircle b = temp.get(0);
	    b.move(-200);
	    temp.remove(0);
	    source.add(0, b);
	    n++;
	    System.out.println(n+". peg 2 to peg 1");
	}

    }

    //makes legal move between source and target
    public void moveSourcetoTarget()
    {
	if (isLegal(source, target))
	{
	    ColorCircle a = source.get(0);
	    a.move(400);
	    source.remove(0);
	    target.add(0, a);
	    n++;
	    System.out.println(n+". peg 1 to peg 3");
	}
	else if (isLegal(target, source))
	{
	    ColorCircle c = target.get(0);
	    c.move(-400);
	    target.remove(0);
	    source.add(0, c);
	    n++;
	    System.out.println(n+". peg 3 to peg 1");
	}

    }

    //makes legal move between temp and target
    public void moveTemptoTarget()
    {
	if (isLegal(temp, target))
	{
	    ColorCircle b = temp.get(0);
	    b.move(200);
	    temp.remove(0);
	    target.add(0, b);
	    n++;
	    System.out.println(n+". peg 2 to peg 3");
	}
	else if (isLegal(target, temp))
	{
	    ColorCircle c = target.get(0);
	    c.move(-200);
	    target.remove(0);
	    temp.add(0, c);
	    n++;
	    System.out.println(n+". peg 3 to peg 2");
	}

    }

    //Determines if the game is in a solved position
    public boolean solved()
    {
	return (source.size()==0 && temp.size()==0);
    }

    //Creates a new game with same number of disks
    public void reset()
    {
	HanoiComponent h = new HanoiComponent(num);
	source = h.source; 
	temp = h.temp;
	target = h.target;
	System.out.println("------------------");
	n = 0;
    }
}