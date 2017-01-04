# DataStructureSimulator
A simple GUI that includes interactive train cars and stackable blocks to demonstrate common programming concepts. These include linked lists, stacks, recursion, inheritance, frames, and listeners. 


Some screenshots:


<img width="434" alt="screen shot 2017-01-03 at 6 57 55 pm" src="https://cloud.githubusercontent.com/assets/15040875/21627581/cabc33ae-d1e6-11e6-831a-5b694092dc96.png">
<img width="432" alt="screen shot 2017-01-03 at 6 58 45 pm" src="https://cloud.githubusercontent.com/assets/15040875/21627582/cabe0d96-d1e6-11e6-91d5-62c157926a5a.png">


Basic mouse operations are as follows:


• Press: Initially the panel is blank. The user can then press the mouse button and the train engine is created at the mouse position. Then, wherever they press the mouse button a railcar is created at that location. After five railcars have been created in this manner, then the next mouse button press creates a stack of 5 storage containers and a container base. Any subsequent mouse button presses on a railcar selects the railcar (and any linked railcars) and changes its (and any linked railcars) color to red. 


• Drag: If the user presses and holds the mouse button and drags the mouse on a railcar which has been selected, then the railcar (and any linked railcars) moves with the mouse. You can look up MouseMotionListener in the java API to implement dragging. Note: the train engine can be selected and dragged, along with all of its linked railcars.
 
 
• Release: When you release the mouse button, if the bounding box of the selected railcar intersects the bounding box of another vehicle (railcar or train engine) which does not already have a trailer, then the selected railcar (and any linked railcars) becomes the trailer for that vehicle.


The File menu lets the user do the following:


• New: Start a new simulation. 
• Exit: Exit from the application.


The List menu should let the user do the following:
 
 
• Add First: Link the selected railcar (along with any linked railcars) to the train engine as the first one in the chain of vehicles towed by the train engine. 


• Add Last: Link the selected railcar (along with any linked railcars) to the last railcar in the chain of vehicles towed by the train engine.


• Remove First: Remove the first railcar from the chain of railcars linked to the train engine. The other railcars remain attached to the train engine. Position the removed railcar randomly.


• Remove Last: Remove the last railcar from the chain of railcars attached to the train engine. 

  
The Stack menu lets the user do the following:

• pop: pop the top storage container off of the container stack and load it onto the selected railcar only if the railcar is empty. If the railcar is not empty then check the linked railcars (i.e. traverse the linked railcars) for an empty railcar. If one is found, load the storage container onto it. If none is found, leave the container stack unchanged.  


• push: unload the storage container from the selected railcar and push it onto the container stack. If the selected railcar is empty, traverse the linked railcars looking for the first nonempty railcar. If one is found, unload the container and push it onto the container stack. 



