import java.awt.* ;
import java.awt.geom.* ;

/**
 * Class that represents a rail car vehicle
 * @author Alessandro Profenna alessandro.profenna@ryerson.ca
 * @version March 25, 2015
 */
public class RailCar extends Vehicle
{
    /**
    Constants used for drawing the rail car
    */
    public static final int UNIT = 10 ;
    public static final int U6 = 6 * UNIT ;
    public static final int U5 = 5 * UNIT ;
    public static final int U4 = 4 * UNIT ;
    public static final int U3 = 3 * UNIT ;
    public static final int U2 = 2 * UNIT ;
    public static final int U15 = UNIT / 2 ;
    public static final int U05 =  UNIT / 2 ;
    public static final int BODY_WIDTH = U3 ;
    public static final int BODY_HEIGHT = U2 ;
    
    private int number;									// the number on the rail car
    public static final int TOTAL_WIDTH = U6 + U05;	// the width of the rail car's bounding box
    public static final int TOTAL_HEIGHT = U2;			// the height of the rail car's bounding box
    
    /**
     * Constructs a RailCar object with a number on it
     * @param x the integer value of the upper left x coordinate location of the rail car
     * @param y the integer value of the upper left y coordinate location of the rail car
     * @param number the integer value of the rail car's number
     */
    public RailCar(int x, int y, int number)
    {
    	setBox(x, y, TOTAL_WIDTH, TOTAL_HEIGHT);
    	this.number = number;
    }
    
    /**
       Draw the rail car
       @param g2 the graphics context
     */
    public void draw(Graphics2D g2)
    {
		int yTop = getY() ;		
		Rectangle2D.Double body 
		    = new Rectangle2D.Double(getX(), yTop, U6, UNIT);      
		Ellipse2D.Double frontTire 
		    = new Ellipse2D.Double(getX() + UNIT, yTop + UNIT, UNIT, UNIT);
		Ellipse2D.Double rearTire
		    = new Ellipse2D.Double(getX() + U4, yTop + UNIT, UNIT, UNIT);	
		// the right end of the hitch
		Point2D.Double r5 
		    = new Point2D.Double(getX() + U6, yTop + U15);
		// the left end of the hitch
		Point2D.Double r6 
		    = new Point2D.Double(getX() + U6 + U05, yTop + U15);
		Line2D.Double hitch
		    = new Line2D.Double(r5, r6);
		if(isSelected())
		{
			g2.setColor(Color.RED);
		}
		else
		{
			g2.setColor(Color.BLACK);
		}
		g2.draw(body);
		g2.draw(hitch);
		g2.draw(frontTire);
		g2.draw(rearTire);
		g2.draw(body) ;
		g2.drawString("" + getNumber(), getX() + U2, yTop + UNIT) ;
		g2.setColor(Color.BLACK);
		
		// recursively draws linked vehicles
		if(hasTrailer())
		{
			trailer = (RailCar) trailer;
			if(trailer != null)
			trailer.draw(g2);
		}
    }
    
	/**
	 * A method that returns the rail car's number
	 * @return the integer value of the rail car's number
	 */
	public int getNumber() 
	{
		return number;
	}
}
    