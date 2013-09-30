/*
Sam Nebel, snebel
Displays a HanoiComponent in a JFrame that is subsequently solved. 
*/

import javax.swing.JComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class HanoiComponentSolver extends JComponent
{
    //fields
    //not private so can be accessed in PlayHanoi
    ArrayList<ColorCircle> source;
    ArrayList<ColorCircle> temp;
    ArrayList<ColorCircle> target;
    int num; //number of disks

    //constants
    static ColorCircle PEG1 = new ColorCircle(12, new Point(100, 250), new Color(156, 93, 82));
    static ColorCircle PEG2 = new ColorCircle(12, new Point(300, 250), new Color(156, 93, 82));
    static ColorCircle PEG3 = new ColorCircle(12, new Point(500, 250), new Color(156, 93, 82));
    static int n = 0; //tracks # of moves
    private static boolean move = false; //used in paintcomponent to sync picture

    //constructor
    public HanoiComponentSolver(int n)
    {
	source = new ArrayList<ColorCircle>();
	temp = new ArrayList<ColorCircle>();
	target = new ArrayList<ColorCircle>();

	double unit = 88.0/n;
	for (int i=1; i<=n; i++)
	    source.add(new ColorCircle(12+i*unit, PEG1.getCenter()));

	num = n;
    }

    //creates a solver based on an existing component
    public HanoiComponentSolver(HanoiComponent h)
    {
	source = h.source;
	temp = h.temp;
	target = h.target;
	num = h.num;
    }

    //repaints the component after the next logical move has been executed
    public void paintComponent(Graphics g)
    {
	//initially paints original game without executing a move for
	if (move){
	    //if board isn't solved, make next move
	    if (!solved()){
		try{Thread.sleep(500);}catch(InterruptedException e){}
		makeNextMove();
	    }
	}

	Graphics2D g2 = (Graphics2D) g;
	//paints largest disks first
	for (int i=source.size()-1; i>=0; i--)	    
	    source.get(i).cfill(g2);

	for (int i=temp.size()-1; i>=0; i--)
	    temp.get(i).cfill(g2);

	for (int i=target.size()-1; i>=0; i--)
	    target.get(i).cfill(g2);

	PEG1.cfill(g2);
	PEG2.cfill(g2);
	PEG3.cfill(g2);

	if (!move)
	    try{Thread.sleep(1000);}catch(InterruptedException e){}
	
	move = true; //next time repaint is called
	             //a move will be made
    }

    //determines if legal to move from orig to dest.
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
	if (isLegal(source, temp)) //move disk from source to temp
	{
	    ColorCircle a = source.get(0); //grab smallest disk
	    a.move(200); //moves disk to new peg
	    source.remove(0); 
	    temp.add(0, a);  //add disk to new peg
	    System.out.println(n+". peg 1 to peg 2");

	    //THIS PROCEDURE IS REPEATED IN THE METHODS BELOW
	}
	else if (isLegal(temp, source))
	{
	    ColorCircle b = temp.get(0);
	    b.move(-200);
	    temp.remove(0);
	    source.add(0, b);
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
	    System.out.println(n+". peg 1 to peg 3");
	}
	else if (isLegal(target, source))
	{
	    ColorCircle c = target.get(0);
	    c.move(-400);
	    target.remove(0);
	    source.add(0, c);
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
	    System.out.println(n+". peg 2 to peg 3");
	}
	else if (isLegal(target, temp))
	{
	    ColorCircle c = target.get(0);
	    c.move(-200);
	    target.remove(0);
	    temp.add(0, c);
	    System.out.println(n+". peg 3 to peg 2");
	}
    }

    //executes the next logical move based on the
    //number of moves that have been made.
    //Algorithm is slightly different for starting with
    //even vs. odd number of disks.
    public void makeNextMove()
    {
	if (num%2==1) //odd disks
	{
	    if (n%3==0){
		n++;
		moveSourcetoTarget();}	
	    else if (n%3==1){
		n++;
		moveSourcetoTemp();}	
	    else if(n%3==2){
		n++;
		moveTemptoTarget();}
	}
	else //even disks
	{
	    if (n%3==0){
		n++;
		moveSourcetoTemp();}	
	    else if (n%3==1){
		n++;
		moveSourcetoTarget();}	
	    else if(n%3==2){
		n++;
		moveTemptoTarget();}
	}

	if (solved())
	    System.out.println("Puzzle of "+num+" disks solved in "+n+" steps");
    }

    //Determines if game is solved
    public boolean solved()
    {
	return (source.size()==0 && temp.size()==0);
    }

}