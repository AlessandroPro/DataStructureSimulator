import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class that represents a panel with all of the game's objects and logic
 * @author Alessandro Profenna alessandro.profenna@ryerson.ca
 * @version March 25, 2015
 */
public class GamePanel extends JPanel
{
	private int clicks;						// the number of clicks
	private int trailerNumber;				// the railcar's number
	private Rectangle blockBase;			// the rectangular base for the blocks
	private ArrayList<Vehicle> vehicles;	// the list of all vehicles in the game
	private ArrayList<Block> loadedBlocks;	// the list of blocks that are currently loaded on vehicles
	private Stack<Block> blocks;			// the stack of blocks that are currently on the block base
	private int xStack;						// the x position of the first block to be added to the stack
	private int yStack;						// the y position of the first block to be added to the stack
	private int xSelect;					// the x position of the selected vehicle
	private int ySelect;					// the y position of the selected vehicle
	
	/**
	 * Constructs a panel with the vehicles, blocks, and game logic
	 */	
	public GamePanel() 
	{
		clicks = 0;
		trailerNumber = 0;
		vehicles = new ArrayList<Vehicle>();
		loadedBlocks = new ArrayList<Block>();
		blocks = new Stack<Block>();

		// Class that represents a listener used to draw the vehicles and blocks, and used to move the vehicles
		class MouseEventListener implements MouseListener
		{
			@Override
			public void mousePressed(MouseEvent e) 
			{
				// draws the train engine
				if(clicks == 0)
				{
					Vehicle engine = new TrainEngine(e.getX(), e.getY());
					vehicles.add(engine);
					clicks++;
				}
				// draws the rail cars
				else if(clicks > 0 && clicks < 6)
				{
					trailerNumber++;
					Vehicle trailer = new RailCar(e.getX(), e.getY(), trailerNumber);
					vehicles.add(trailer);
					clicks++;
				}
				// draws the block stack
				else if(clicks == 6)
				{
					xStack = GameFrame.FRAME_WIDTH - TrainEngine.TOTAL_WIDTH;
					yStack = GameFrame.FRAME_HEIGHT - 5 * TrainEngine.TOTAL_HEIGHT;
					blocks.add(new Block(xStack, yStack, "A"));
					blocks.add(new Block(xStack, yStack - Block.HEIGHT, "B"));
					blocks.add(new Block(xStack, yStack - 2 * Block.HEIGHT, "C"));
					blocks.add(new Block(xStack, yStack - 3 * Block.HEIGHT, "D"));
					blocks.add(new Block(xStack, yStack - 4 * Block.HEIGHT, "E"));
					clicks++;
				}
				// selects a vehicle
				else
				{
					xSelect = e.getX();
					ySelect = e.getY();
					for(int i = 0; i < vehicles.size(); i++)
					{
						if(!vehicles.get(i).isTrailer())
						{
							vehicles.get(i).deselect();
						}
					}
					for(int i = 0; i < vehicles.size(); i++)
					{
						if(vehicles.get(i).contains(new Point(xSelect, ySelect)))
						{
							if(!vehicles.get(i).isTrailer())
							{
								vehicles.get(i).select();
								break;
							}
						}
					}
				}
			}

			@Override
			// if the selected vehicle is overlapping the last trailer of linked vehicles, then it will attach it to the back of the link
			public void mouseReleased(MouseEvent e) 
			{
				search:
				for(int i = 1; i < vehicles.size(); i++)
				{
					if(!vehicles.get(i).isTrailer() && vehicles.get(i).isSelected())
					{
						for(int j = 0; j < vehicles.size(); j++)
						{
							if(vehicles.get(i).overlaps(vehicles.get(j)) && 
								!vehicles.get(i).equals(vehicles.get(j)) && 
								!vehicles.get(j).isSelected() && 
								!vehicles.get(j).hasTrailer())
							{
								vehicles.get(j).addLast(vehicles.get(i));
								vehicles.get(i).deselect();
								break search;
							}
						}
					}
				}
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		}	
		MouseListener mListener = new MouseEventListener();
		this.addMouseListener(mListener);
		
		// Class that represents a listener used to repaint the game's graphics every 30 milliseconds
		class UpdateTimerListener implements ActionListener 
		{ 
			public void actionPerformed(ActionEvent event) 
			{
				repaint();
			}
		}
		ActionListener updateTimerListener = new UpdateTimerListener();
		final int UPDATE_DELAY = 30; 
		Timer updateTimer = new Timer(UPDATE_DELAY, updateTimerListener);
		updateTimer.start();
		
		// Class that represents a listener used to update the location of a vehicle as it is dragged by the mouse
		class MouseMotion implements MouseMotionListener 
		{
			public void mouseDragged(MouseEvent e) 
			{
				for(int i = 0; i < vehicles.size(); i++)
				{
					if(vehicles.get(i).isSelected() && !vehicles.get(i).isTrailer())
					{
						vehicles.get(i).setLocation(e.getX(), e.getY());
						break;
					}
				}
			}
			
			public void mouseMoved(MouseEvent event) {}
		}
		MouseMotionListener motionListener = new MouseMotion();
		this.addMouseMotionListener(motionListener);

	}

	/**
	* Redraws all of the game's graphics each time the repaint method is called
	* @param g the Graphics object that is passed as a parameter
	*/
	public void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;
		
		if(vehicles != null)
		{
			for (int i = 0; i < vehicles.size(); i++) 
			{
				if(!vehicles.get(i).isTrailer())
				{
					vehicles.get(i).draw(g2);
				}
			}
		}
		
		if(blocks != null)
		{
			for(int i = 0; i < blocks.size(); i++)
			{
				if(i == 0)
				{
					blocks.get(i).setLocation(xStack, yStack);
				}
				else
				{
					blocks.get(i).setLocation(blocks.get(i-1).getX(), blocks.get(i-1).getY() - Block.HEIGHT);
				}
				blocks.get(i).draw(g2);
			}
		}
		
		if(loadedBlocks != null)
		{
			for(int i = 0; i < loadedBlocks.size(); i++)
			{
				loadedBlocks.get(i).draw(g2);
			}
		}
		
		if(clicks == 7)
		{
			blockBase = new Rectangle(xStack - Block.WIDTH, yStack + Block.HEIGHT, 3 * Block.WIDTH, Block.HEIGHT/2);
			g2.fill(blockBase);
			g2.draw(blockBase);
		}	
	}
	
	/**
	 * A method that returns the list of vehicles
	 * @return the ArrayList of vehicles
	 */
	public ArrayList<Vehicle> getVehicles()
	{
		return vehicles;
	}
	
	/**
	 * A method that returns the stack of blocks
	 * @return the Stack of blocks
	 */
	public Stack<Block> getBlocks()
	{
		return blocks;
	}
	
	/**
	 * A method that adds the specified block to the ArrayList of loaded blocks
	 * @param block the block that was loaded
	 */
	public void addLoadedBlock(Block block)
	{
		loadedBlocks.add(block);
	}
	
	/**
	 * A method that returns the ArrayList of loaded blocks
	 * @return the ArrayList of loaded blocks
	 */
	public ArrayList<Block> getLoadedBlocks()
	{
		return loadedBlocks;
	}
	
	/**
	 * A method that resets the panel and all of its variables
	 */
	public void reset()
	{
		clicks = 0;
		trailerNumber = 0;
		blockBase = null;
		vehicles = new ArrayList<Vehicle>();
		loadedBlocks = new ArrayList<Block>();
		blocks = new Stack<Block>();
		xStack = 0;
		yStack = 0;
		xSelect = 0;
		ySelect = 0;
	}
}



	
	
	