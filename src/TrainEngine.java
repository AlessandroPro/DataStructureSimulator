import java.awt.geom.* ;
import java.awt.* ;

/**
 * Class that represents a train engine vehicle that pulls a chain of rail cars
 * @author Alessandro Profenna alessandro.profenna@ryerson.ca
 * @version March 25, 2015
 */

public class TrainEngine extends Vehicle
{
    /**
       Constants used for drawing the Train Engine
     */
    private static final double WIDTH = 35 ;
    private static final double UNIT = WIDTH / 5 ;
    private static final double U_3 = 0.3 * UNIT ; 
    private static final double U2_5 = 2.5 * UNIT ; 
    private static final double U3 = 3 * UNIT ; 
    private static final double U4 = 4 * UNIT ; 
    private static final double U10 = 10 * UNIT ; 
    private static final double U10_7 = 10.7 * UNIT ; 
    private static final double U11 = 11 * UNIT ; 
    private static final double U12 = 12 * UNIT ; 
    private static final double U13 = 13 * UNIT ; 
    private static final double U14 = 14 * UNIT ; 
    
    public static final int TOTAL_WIDTH = (int) (U3 + U11);	// the width of the train engine's bounding box
    public static final int TOTAL_HEIGHT = (int) (U4 + UNIT);	// the height of the train engine's bounding box
    
    /**
     * Constructs a TrainEngine object 
     * @param x the integer value of the upper left x coordinate location of the train engine
     * @param y the integer value of the upper left y coordinate location of the train engine
     */
    public TrainEngine(int x, int y)
    {
    	setBox(x, y, (int) TOTAL_WIDTH, TOTAL_HEIGHT);
    }
    
	/**
       Draws the train engine
       @param g2 the graphics context
     */
    public void draw(Graphics2D g2)
    {
		int x1 = getX() ;
		int y1 = getY() ;
		Rectangle2D.Double hood = new Rectangle2D.Double(x1, y1 + UNIT, 
								 U3, U3 ) ;
		g2.setColor(Color.blue) ;
		g2.fill(hood) ;
	
		Rectangle2D.Double body = new Rectangle2D.Double(x1 + U3,
								 y1,
								 U10, U4) ;
		g2.setColor(Color.blue) ;
		g2.fill(body) ;
	
		Line2D.Double hitch = new Line2D.Double(x1 + U13,
							y1 + U2_5,
							x1 + U14, 
							y1 + U2_5) ;
		g2.setColor(Color.black) ;
		g2.draw(hitch) ;
	
		if(isSelected())
		{
			g2.setColor(Color.RED);
		}
		else
		{
			g2.setColor(Color.BLACK);
		}
		Ellipse2D.Double wheel1 = new Ellipse2D.Double(x1 + U_3, 
							       y1 + U4, 
								 UNIT, UNIT) ;
		g2.fill(wheel1) ;
	
		Ellipse2D.Double wheel2 = new Ellipse2D.Double(x1 + 1.3 * UNIT, 
							       y1 + U4, 
								 UNIT, UNIT) ;
		g2.fill(wheel2) ;
	
		Ellipse2D.Double wheel3 = new Ellipse2D.Double(x1 + 2.3 * UNIT, 
							       y1 + 4 * UNIT, 
								 UNIT, UNIT) ;
		g2.fill(wheel3) ;
	
		Ellipse2D.Double wheel4 = new Ellipse2D.Double(x1 + U10_7, 
							       y1 + U4, 
								 UNIT, UNIT) ;
		g2.fill(wheel4) ;
	
		Ellipse2D.Double wheel5 = new Ellipse2D.Double(x1 + U12, 
							       y1 + U4, 
								 UNIT, UNIT) ;
		g2.fill(wheel5) ;
		
		Ellipse2D.Double wheel6 = new Ellipse2D.Double(x1 + 9.7 * UNIT, 
			       y1 + U4, 
				 UNIT, UNIT) ;
		g2.fill(wheel6) ;
		g2.setColor(Color.BLACK);
	
		// recursively draws linked vehicles
		if(hasTrailer())
		{
			trailer = (RailCar) trailer;
			trailer.draw(g2);
		}
    }
}