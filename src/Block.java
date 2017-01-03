import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Class that represents a block
 * @author Alessandro Profenna alessandro.profenna@ryerson.ca
 * @version March 25, 2015
 */
public class Block
{
	public static int WIDTH = 30;					// the width of the block
	public static int HEIGHT = 30;					// the height of the block
	public static Color BOX_COLOR = Color.GREEN;	// the color of the block's outline
	public static Color LETTER_COLOR = Color.BLACK;	// the color of the letter inside the block
	
	private Vehicle carrier;						// the vehicle object that the block is loaded on
	private Rectangle box;							// the box shape of the block
	private String letter;							// the letter inside the block
	
    /**
     * Constructs a Block object with a letter on it
     * @param x the integer value of the upper left x coordinate location of the block
     * @param y the integer value of the upper left y coordinate location of the block
     * @param letter the String representation of the letter on the block
     */
	public Block(int x, int y, String letter)
	{
		box = new Rectangle(x, y, WIDTH, HEIGHT);
		this.letter = letter;
	}
	
    /**
     * A method that returns the upper left x coordinate location of the block
     * @return the integer value of the upper left x coordinate location of the block
     */
	public int getX()
	{
		return (int) box.getX();
	}
	
    /**
     * A method that returns the upper left y coordinate location of the block
     * @return the integer value of the upper left y coordinate location of the block
     */
	public int getY()
	{
		return (int) box.getY();
	}
	
    /**
     * A method that sets the location of the block to the specified x and y positions
     * @param x the integer value of the new x coordinate location of the block
     * @param y the integer value of the new y coordinate location of the block 
     */
	public void setLocation(int x, int y)
	{
		box.setLocation(x, y);
	}
	
    /**
     * A method that sets the vehicle carrier of the block
     * @param vehicle the Vehicle that the block is loaded on
     */
	public void setCarrier(Vehicle vehicle)
	{
		carrier = vehicle;
	}
	
    /**
     * Draws the block
     * @param g2 the graphics context
     */
	public void draw(Graphics2D g2)
	{
		// if the block has a carrier, it will be drawn on top of its carrier, not the stack
		if(carrier != null)
		{
			setLocation((int)(carrier.getX() + Block.WIDTH/2), (int)(carrier.getY() - Block.HEIGHT));
		}
		int x1 = getX() ;
		int y1 = getY() ; 
		g2.drawString(letter, x1 + WIDTH/2, y1 + HEIGHT/2);
		g2.setColor(BOX_COLOR);
		g2.draw(box);
		g2.setColor(LETTER_COLOR);
	}
}