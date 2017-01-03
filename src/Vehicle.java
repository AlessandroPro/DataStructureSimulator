import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

/**
 * Abstract class that represents a generic vehicle
 * @author Alessandro Profenna alessandro.profenna@ryerson.ca
 * @version March 25, 2015
 */
public abstract class Vehicle
{
	public Vehicle trailer;			// the vehicle's trailer that is attached behind it
	public Rectangle box;			// the bounding box of the vehicle
	public boolean hasTrailer;		// used to check if there is a trailer behind the vehicle
	public boolean isTrailer;		// used to check if the vehicle is a trailer
	private boolean isLoaded;		// used to check if the vehicle has a block on it
	private boolean selected;		// used to check if the vehicle is currently selected
	private Block loadedBlock;		// the block that is currently loaded on the vehicle
	private Random random;			// used to select a random location for popped rail cars
	
    /**
     * Draws the block
     * @param g2 the graphics context
     */
	public abstract void draw(Graphics2D g2);
	
    /**
     * A method that returns the bounding box of the vehicle
     * @return the Rectangle bounding box of the vehicle
     */
	public Rectangle getBox()
	{
		return box;
	}
	
    /**
     * A method that reconfigures the bounding box's size and location
     * @param x the integer value of the upper left x coordinate location of the vehicle
     * @param y the integer value of the upper left y coordinate location of the vehicle
     * @param width the width of the vehicle's bounding box
     * @param height the height of the vehicle's bounding box
     */
	public void setBox(int x, int y, int width, int height)
	{
		box = new Rectangle(x, y, width, height);
	}
	
    /**
     * A method that returns the upper left x coordinate location of the vehicle's bounding box
     * @return the integer value of the upper left x coordinate location of the vehicle's bounding box
     */
	public int getX()
	{
		return (int) box.getX();
	}
	
    /**
     * A method that returns the upper left y coordinate location of the vehicle's bounding box
     * @return the integer value of the upper left y coordinate location of the vehicle's bounding box
     */
	public int getY()
	{
		return (int) box.getY();
	}
	
    /**
     * A method that sets the location of the vehicle to the specified x and y positions
     * @param x the integer value of the new x coordinate location of the vehicle
     * @param y the integer value of the new y coordinate location of the vehicle
     */
	public void setLocation(int x, int y)
	{
		box.setLocation(x, y);
		// if the vehicle has linked trailers, their locations will be set recursively
		if(hasTrailer)
		{
			if(this instanceof TrainEngine)
	    	{
	    		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY() + 17);
	    	}
	    	else
	    	{
	    		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY());
	    	}
		}
	}
	
    /**
     * A method that checks if the selected vehicle overlaps with another
     * @param v the Vehicle to check for the overlapping of this one
     * @ return true, if the vehicles' bounding boxes intersect, false otherwise
     */
    public boolean overlaps(Vehicle v)
    {
    	if(this.box.intersects(v.box))
    	{
    		return true;
    	}
    	else if(hasTrailer())
    	{
    		return trailer.box.intersects(v.box);
    	}
    	return false;
    }

    /**
     * A method that checks if the vehicle's bounding box contains a specified point
     * @param p the Point that is checked for being contained in the vehicle's bounding box
     * @return true if the vehicle's bounding box contains the point, false otherwise
     */
    public boolean contains(Point p)
    {
    	if(box.contains(p))
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * A method that checks if the vehicle's position equals that of another vehicle's
     * @param other the other vehicle that is behind checked for a matching location
     * @return true if the vehicles' positions are the same, false otherwise
     */
    public boolean equals(Vehicle other)
    {
    	if(this.getX() == other.getX() && this.getY() == other.getY())
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * A method that checks to see if a vehicle is selected
     * @return true if the vehicle is selected, false otherwise
     */
    public boolean isSelected()
    {
    	return selected;
    }
    
    /**
     * A method that considers a vehicle selected
     */
    public void select()
    {
    	selected = true;
    	// if the vehicle has trailers, they are all selected recursively
    	if(hasTrailer())
    	{
    		trailer.select();
    	}
    }
    
    /**
     * A method that considers a vehicle deselected
     */
    public void deselect()
    {
    	selected = false;
    	// if the vehicle has trailers, they are all deselected recursively
    	if(hasTrailer())
    	{
    		trailer.deselect();
    	}
    }
    
    /**
     * A method that checks if the vehicle is a trailer
     * @return true if the vehicle is a trailer, false otherwise
     */
    public boolean isTrailer()
    {
    	return isTrailer;
    }
    
    /**
     * A method that checks if the vehicle has a trailer
     * @return true if the vehicle has a trailer, false otherwise
     */
    public boolean hasTrailer()
    {
    	return hasTrailer;
    }
    
    /**
     * A method that checks if the vehicle has a trailer without a loaded block
     * @return true if the vehicle has a trailer, false otherwise
     */
    public boolean hasEmptyTrailer()
    {
    	// recursively checks all of the vehicle's trailers, if it has more than one
    	if(isLoaded() && hasTrailer())
    	{
    		return trailer.hasEmptyTrailer();
    	}
    	if(!isLoaded())
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * A method that checks if the vehicle has a block loaded on it
     * @return true if the vehicle has block loaded on it, false otherwise
     */
    public boolean isLoaded()
    {
    	return isLoaded;
    }
    
    /**
     * A method that loads the specified block onto the vehicle
     * @param load the block to be loaded 
     */
    public void load(Block load)
    {
    	// loads the block on the vehicle's next empty trailer, if it has one 
    	if(isLoaded() && hasTrailer())
    	{
    		trailer.load(load);
    		return;
    	}
    	if(!isLoaded())
    	{	
    		isLoaded = true;
    		loadedBlock = load;
    		load.setCarrier(this);
    	}
    }
    
    /**
     * A method that unloads the specified block from the vehicle
     * @param load the block to be unloaded 
     */
    public void unload(Block load)
    {
    	// unloads the block from the vehicle's next loaded trailer, if it doesn't have one 
    	if(!isLoaded() && hasTrailer())
    	{
    		trailer.unload(load);
    		return;
    	}
    	isLoaded = false;
    	load.setCarrier(null);
    	loadedBlock = null;
    }
    
    /**
     * A method that returns the loaded block of the vehicle
     * @return the vehicle's loaded block
     */
    public Block getLoad()
    {
    	// returns the block from the vehicle's next loaded trailer, if it doesn't have one 
    	if(!isLoaded && hasTrailer)
    	{
    		return trailer.getLoad();
    	}
    	return loadedBlock;
    }
    
    /**
     * A method that attaches a selected vehicle and its trailers, to the back of this vehicle and its trailers
     * @param otherTrailer the Vehicle to become this vehicle's newest trailer
     */
    public void addLast(Vehicle otherTrailer)
    {
    	if(hasTrailer())
    	{
    		this.trailer.addLast(otherTrailer);
    		return;
    	}
    	this.trailer = otherTrailer;
    	trailer.isTrailer = true;
    	hasTrailer = true;
    	if(this instanceof TrainEngine)
    	{
    		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY() + 17);
    	}
    	else
    	{
    		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY());
    	}
    }
    
    /**
     * A method that removes the last trailer of a vehicle, and places it in a random location
     */
    public void removeLast()
    {
    	if(hasTrailer())
    	{
    		if(trailer.hasTrailer())
	    	{
	    		trailer.removeLast();
	    		return;
	    	}
	    	random = new Random();
	    	trailer.setLocation(random.nextInt(GameFrame.FRAME_WIDTH - RailCar.TOTAL_WIDTH), 
	    						random.nextInt(GameFrame.FRAME_HEIGHT - RailCar.TOTAL_HEIGHT));
	    	trailer.isTrailer = false;
	    	hasTrailer = false;
	    	trailer.deselect();
	    	trailer = null;
    	}
    }
    
    /**
     * A method that removes the first trailer of a vehicle, and places it in a random location
     */
    public void removeFirst()
    {
    	if(hasTrailer)
    	{
    		if(trailer.hasTrailer)
    		{
    	    	this.trailer.isTrailer = false;
    	    	this.trailer.hasTrailer = false;
    	    	trailer = this.trailer.trailer;
    		}
    		else
    		{
    			random = new Random();
    	    	trailer.isTrailer = false;
    	    	hasTrailer = false;
    	    	trailer.setLocation(random.nextInt(GameFrame.FRAME_WIDTH - RailCar.TOTAL_WIDTH), 
									random.nextInt(GameFrame.FRAME_HEIGHT - RailCar.TOTAL_HEIGHT));
    			trailer.deselect();
    			trailer = null;
    		}
    	}
    }
    
    /**
     * A method that attaches a selected vehicle and its trailers, as the first trailer of this vehicle
     * @param otherTrailer the Vehicle to be inserted as this vehicle's first trailer
     */
    public void addFirst(Vehicle otherTrailer)
    {
    	if(hasTrailer)
    	{
    		if(otherTrailer.hasTrailer)
    		{
        		findLast(otherTrailer).hasTrailer = true;
        		findLast(otherTrailer).trailer= this.trailer;
        		this.trailer = otherTrailer;
        		trailer.isTrailer = true;
        		this.trailer.hasTrailer = true;
        		if(this instanceof TrainEngine)
            	{
            		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY() + 17);
            	}
            	else
            	{
            		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY());
            	}
        		trailer.deselect();
    		}
    		else
    		{
    			otherTrailer.trailer = this.trailer;
    			this.trailer = otherTrailer;
    			trailer.isTrailer = true;
    			this.trailer.hasTrailer = true;
    			if(this instanceof TrainEngine)
    	    	{
    	    		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY() + 17);
    	    	}
    	    	else
    	    	{
    	    		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY());
    	    	}
    			trailer.deselect();
    		}
    	}
    	else
    	{
    		this.trailer = otherTrailer;
        	trailer.isTrailer = true;
        	hasTrailer = true;
        	if(this instanceof TrainEngine)
        	{
        		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY() + 17);
        	}
        	else
        	{
        		trailer.setLocation((int) (box.getX() + box.getWidth()), (int) box.getY());
        	}
        	trailer.deselect();
    	}
    }
    
    /**
     * A method that returns the last trailer of a specified vehicle
     * @param first the Vehicle whose last trailer is returned
     * @return the last trailer of the specified vehicle
     */
    public Vehicle findLast(Vehicle first)
    {
    	if(first.trailer == null)
    	{
    		return first;
    	}
    	else
    	{
    		return first.findLast(first.trailer);
    	}
    }

    /**
     * A method that updates the location of the vehicle
     */
	public void update() 
	{
		setLocation(getX(), getY());	
	}
 
}