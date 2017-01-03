import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Class that represents a frame that holds the panel and the menu of the game
 * @author Alessandro Profenna alessandro.profenna@ryerson.ca
 * @version March 25, 2015
 */
public class GameFrame extends JFrame
{
   static final int FRAME_WIDTH = 700;	// the width of the game's frame
   static final int FRAME_HEIGHT = 800;	// the height of the game's frame
   
   private JMenuBar menuBar;			// the menu bar at the top of the game's frame
   private GamePanel panel;				// the game's panel with all of it's logic 
   private Random random;				// used to select a random location for popped rail cars

	/**
	 * Constructs a frame with a menu bar and game panel
	 */	
   public GameFrame()
   {     
      menuBar = new JMenuBar();     
      setJMenuBar(menuBar);
      menuBar.add(createFileMenu());
      menuBar.add(createStackMenu());
      menuBar.add(createListMenu());
      panel = new GamePanel();
      add(panel);
      setSize(FRAME_WIDTH, FRAME_HEIGHT);
   }

   // A class that represents a listener used to exit the game and close the program
   class ExitItemListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         System.exit(0);
      }
   }
   
   // A class that represents a listener used to reset the game and start over
   class NewItemListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
    	  panel.reset();
      }
   }      
   
   /**
      Creates the File menu.
      @return the menu
   */
   public JMenu createFileMenu()
   {
      JMenu menu = new JMenu("File");
      JMenuItem newItem = new JMenuItem("New"); 
      JMenuItem exitItem = new JMenuItem("Exit");      
      ActionListener exitListener = new ExitItemListener();
      exitItem.addActionListener(exitListener);
      ActionListener newListener = new NewItemListener();
      newItem.addActionListener(newListener);
      menu.add(newItem);
      menu.add(exitItem);
      return menu;
   }

   /**
      Creates the Stack menu.
      @return the menu
   */
   public JMenu createStackMenu()
   {
      JMenu menu = new JMenu("Stack");
      menu.add(createPopItem("Pop"));
      menu.add(createPushItem("Push"));
      return menu;
   }  

   /**
      Creates the List menu.
      @return the menu
   */
   public JMenu createListMenu()
   {
      JMenu menu = new JMenu("List"); 
      menu.add(createAddLastItem("Add Last"));
      menu.add(createRemoveLastItem("Remove Last"));
      menu.add(createRemoveFirstItem("Remove First"));
      menu.add(createAddFirstItem("Add First"));
      return menu;
   }  

   /**
      Creates a menu item to add to the end of the train engine and set its action listener
      @param name the name of Add Last menu item
      @return the menu item
   */
   public JMenuItem createAddLastItem(final String name)
   {
	// A class that represents a listener used to add a trailer to the end of the train engine
      class AddLastItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
        	 if(panel.getVehicles().size() == 6)
        	 {
	        	for (int i = 1; i < panel.getVehicles().size(); i++) 
	     		{
	     			if(!panel.getVehicles().get(i).isTrailer() && panel.getVehicles().get(i).isSelected())
	     			{
	     				panel.getVehicles().get(i).deselect();
	     				panel.getVehicles().get(0).addLast(panel.getVehicles().get(i));
	     			}
	     		}
        	 }
         }
      }      

      JMenuItem item = new JMenuItem(name);      
      ActionListener listener = new AddLastItemListener();
      item.addActionListener(listener);
      return item;
   }
   
   /**
   Creates a menu item to remove the end of the train engine and set its action listener
   @param name the name of the Remove Last menu item
   @return the menu item
*/
   public JMenuItem createRemoveLastItem(final String name)
   {
	// A class that represents a listener used to remove a trailer from the end of the train engine
      class AddLastItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
        	if(panel.getVehicles().size() == 6)
        	{
        		panel.getVehicles().get(0).removeLast();
        	}
         }
      }      

      JMenuItem item = new JMenuItem(name);      
      ActionListener listener = new AddLastItemListener();
      item.addActionListener(listener);
      return item;
   }
   
   /**
   Creates a menu item to remove the first of the train engine and set its action listener
   @param name the name of the Remove First menu item
   @return the menu item
*/
   public JMenuItem createRemoveFirstItem(final String name)
   {
	// A class that represents a listener used to remove the first trailer of the train engine
      class RemoveFirstItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
        	if(panel.getVehicles().size() == 6)
        	{
	     		panel.getVehicles().get(0).removeFirst();
	     		for(int i = 1; i < panel.getVehicles().size(); i++)
	     		{
	     			if(!panel.getVehicles().get(i).hasTrailer && panel.getVehicles().get(i).trailer != null)
	     			{
	     				random = new Random();
	     				panel.getVehicles().get(i).trailer = null;
	     				panel.getVehicles().get(i).deselect();
	     				panel.getVehicles().get(i).setLocation(random.nextInt(GameFrame.FRAME_WIDTH), random.nextInt(GameFrame.FRAME_HEIGHT));
	     				break;
	     			}
	     		}
	     		panel.getVehicles().get(0).update();
        	}
         }
      }      

      JMenuItem item = new JMenuItem(name);      
      ActionListener listener = new RemoveFirstItemListener();
      item.addActionListener(listener);
      return item;
   }
   
   /**
   Creates a menu item to add the first of the train engine and set its action listener
   @param name the name of the Add First menu item
   @return the menu item
*/
   public JMenuItem createAddFirstItem(final String name)
   {
	// A class that represents a listener used to add a trailer, and its trailers, as the first trailer of the train engine
      class AddFirstItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
        	if(panel.getVehicles().size() == 6)
        	{
	        	for (int i = 1; i < panel.getVehicles().size(); i++) 
	      		{
	      			if(!panel.getVehicles().get(i).isTrailer() && panel.getVehicles().get(i).isSelected())
	      			{
	      				panel.getVehicles().get(0).addFirst(panel.getVehicles().get(i));
	      			}
	      		}
        	}
         }
      }      

      JMenuItem item = new JMenuItem(name);      
      ActionListener listener = new AddFirstItemListener();
      item.addActionListener(listener);
      return item;
   }
   
   /**
   Creates a menu item to push a block from the selected vehicle link and set its action listener
   @param name the name of Push menu item
   @return the menu item
*/
   public JMenuItem createPushItem(final String name)
   {
	// A class that represents a listener used to take the block off of the first loaded vehicle in of a selected link, and add it to the stack
      class PushItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
        	 for (int i = 0; i < panel.getVehicles().size(); i++) 
        	 {
        		 if(panel.getVehicles().get(i).isSelected() && !panel.getVehicles().get(i).isTrailer())
        		 {
        			 if(panel.getLoadedBlocks() != null)
        			 {
        				 if(i == 0 && panel.getVehicles().get(0).hasTrailer() && panel.getVehicles().get(0).trailer.isSelected())
        				 {
        					 Block pushBlock = panel.getVehicles().get(0).trailer.getLoad();
        					 if(pushBlock == null)
        						break;
        					 panel.getBlocks().push(pushBlock);
        					 panel.getVehicles().get(0).trailer.unload(pushBlock);
        					 panel.getLoadedBlocks().remove(pushBlock);
        				 }
        				 else if(i > 0)
        				 {
        					 Block pushBlock = panel.getVehicles().get(i).getLoad();
        					 if(pushBlock == null)
        						 break;
        					 panel.getBlocks().push(pushBlock);
        					 panel.getVehicles().get(i).unload(pushBlock);
        					 panel.getLoadedBlocks().remove(pushBlock);
        				 }
        			 }
        			 break;
        		 }
        	 }
         }
      }      

      JMenuItem item = new JMenuItem(name);      
      ActionListener listener = new PushItemListener();
      item.addActionListener(listener);
      return item;
   }
   
   /**
   Creates a menu item to pop a block from the stack and add it to the selected link and set its action listener
   @param name the name of Pop menu item
   @return the menu item
    */
   public JMenuItem createPopItem(final String name)
   {
	// A class that represents a listener used to take the block off of the stack and load it on the first empty vehicle in of a selected link
      class PopItemListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
        	 for (int i = 0; i < panel.getVehicles().size(); i++) 
        	 {
        		 if(panel.getVehicles().get(i).isSelected() && 
        			!panel.getVehicles().get(i).isTrailer() && 
        			panel.getVehicles().get(i).hasEmptyTrailer())
        		 {
        			 if(panel.getBlocks() != null)
        			 {
        				 if(i == 0 && panel.getVehicles().get(0).hasTrailer() && panel.getVehicles().get(0).trailer.isSelected())
        				 {
        					 if(panel.getVehicles().get(0).trailer.hasEmptyTrailer())
        					 {
        						 Block poppedBlock = panel.getBlocks().pop();
        						 panel.getVehicles().get(0).trailer.load(poppedBlock);
        						 panel.addLoadedBlock(poppedBlock);
        					 }
        				 }
        				 else if(i > 0)
        				 {
        					 Block poppedBlock = panel.getBlocks().pop();
        					 panel.getVehicles().get(i).load(poppedBlock);
        					 panel.addLoadedBlock(poppedBlock); 
        				 }
        			 }
        			 break;
        		 }
        	 }
         }
      }      

      JMenuItem item = new JMenuItem(name);      
      ActionListener listener = new PopItemListener();
      item.addActionListener(listener);
      return item;
   }
}