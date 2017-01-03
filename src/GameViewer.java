import javax.swing.JFrame;

/**
 * Class that views and runs the Game
 * @author Alessandro Profenna alessandro.profenna@ryerson.ca
 * @version March 25, 2015
 */
public class GameViewer
{  
	// Creates a Game Frame and runs the game
   public static void main(String[] args)
   {  
      JFrame frame = new GameFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setTitle("Data Structure Simulator");
      frame.setVisible(true);      
   }
}
