/*
Sam Nebel, snebel

Colored Circle class.
*/

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import java.util.Random;


public class ColorCircle
{
    //fields
    double radius;
    Point center;
    Color color;

    //constructors
    public ColorCircle()
    {
	radius = 25;
	center = new Point(250,250);
	color = Color.RED;
    }

    public ColorCircle(double r, Point p, Color c)
    {
	radius = r;
	center = p;
	color = c;
    }

    //for use in HanoiComponent
   public ColorCircle(double r, Point p)
    {
	radius = r;
	center = p;
	Random gen = new Random();
	int red = gen.nextInt(250);
	int g = gen.nextInt(250);
	int b = gen.nextInt(250);
	color = new Color(red, g, b);
    }

    //methods
    public void widen(int x)
    {
	radius = radius + x;
    }

    //increases amount of red
    public void redden(int x)
    {
	//avoids out of bounds error for red
	if(color.getRed()+x<=255 && color.getRed()>=0)
	    color = new Color(color.getRed()+x, color.getGreen(), color.getBlue());
    }

    //increases amount of blue
    public void bluen(int x)
    {
	//avoids out of bounds error for blue
	if(color.getBlue()+x<=255 && color.getBlue()>=0)
	    color = new Color(color.getRed(), color.getGreen(), color.getBlue()+x);
    }

    public double getRadius()
    {
	return radius;
    }

    public Point getCenter()
    {
	return center;
    }

    public Color getColor()
    {
	return color;
    }


    //defines how to fill a circle using ellipse
    public void cfill(Graphics2D g)
    {
	Ellipse2D e = new Ellipse2D.Double((int)center.getX()-radius,
					   (int)center.getY()-radius, 2*radius, 2*radius);
	g.setColor(color);
	g.fill(e);
    }

    //this.lessThan(c)?
    public boolean lessThan(ColorCircle c)
    {
	return (this.getRadius() < c.getRadius());
    }

    //tests if a point is in the colorcircle
    //used for mousevent in PlayHanoi.java
    public boolean isInside(Point p)
    {
	Point c = this.getCenter();
	double r = this.getRadius();
	double x = c.getX();
	double y = c.getY();

	return ((p.getX()<=x+r && p.getX()>=x-r) &&
		(p.getY()<=y+r && p.getY()>=y-r));
    }

    public void move(int x)
    {
	center = new Point((int)center.getX()+x, (int)center.getY());
    }

    public String toString()
    {
	return "center = ("+center.getX()+", "+center.getY()+
	    "), radius = "+radius+", "+color;
    }
}