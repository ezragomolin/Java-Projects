

/**
 * 
 * @author Ezra Gomolin based on given instructions and sample bTree.java
 *
 */


public class bTree {
	//Initializing constants variables/offsets used througout bTree
	double DELTASIZE = 0.1;
	double xo = -1;
	double yo = 0;
	double lastSize = 0;
	double SCALE = 6;
	

	// Instance variables

	bNode root = null;
//recursive method
	public bNode addNode(bNode current, aBall rball) {
		// The case where we're at the bottom of the tree
		if (current == null) {      //leaf node
			return makeNode(rball);  //attaches new node
		}
		
		// The case where we're not at the bottom
		if (rball.getbSize() < current.data.getbSize()) {
			current.left = addNode(current.left, rball);
		}
		if (rball.getbSize() > current.data.getbSize()) {
			current.right = addNode(current.right, rball);
		}
		
		return current;
	}

	bNode makeNode(aBall rball) {
		bNode node = new bNode(); // creates the object
		node.data = rball; // data field is a rball
		node.left = null; // starts at null
		node.right = null; // starts at null
		return node; // return for next object
	}
	
	/**
	 * recall to recursive method
	 */
	public void stopscreen() { //will call the method to stop the balls
		traverseinorderstop(root);
	}
	
	public void traverseinorderstop(bNode root) { 
		if (root.left != null) traverseinorderstop(root.left);
		if (root.data != null) root.data.stop(); 
		if (root.right != null) traverseinorderstop(root.right);
	}

	public void doStack() {
		traverse_inorder(root);
	}
	
	/**
	 * traverse_inorder method - recursively traverses tree, LEFT-Root-RIGHT, balls in increasing order
	 */
	
	public void traverse_inorder(bNode root) {

		if (root.left != null)
			traverse_inorder(root.left);
		System.out.println(root.data.getbSize());  //prints radius
		
		/**
		 * The following algorithm detects weather or not to start a new stack or stack the following ball on top of the previouss stack
		 * xo is a offset and is initially set to -1, so the if statement sets the first balls location at x=0.
		 * root.data.moveTo actually moves the ball to the desired position 
		 */
		
		if (root.data.getbSize() - lastSize > DELTASIZE) {
			lastSize = root.data.getbSize();
			if (xo < 0)
				xo = 0;
			else
				xo += 2 * lastSize * SCALE;
			    yo = 0;
		}
		
		root.data.moveTo(xo+(2*root.data.getbSize()), 600 - yo - (2 * root.data.getbSize() * SCALE));
		lastSize = root.data.getbSize();
		yo += 2 * lastSize * SCALE;
		
			
		if (root.right != null)
			traverse_inorder(root.right);

	}
	
/**
 * checking if any balls are still moving
 */
	
	public int numberofballs;
	private void stillrunning(bNode root) {
		if (root.left != null) 
			stillrunning(root.left);
		
		boolean isr=root.data.isRunning;
		if (isr) 
			numberofballs+= 1;
		
		if (root.right != null) 
			stillrunning(root.right);
	}
	
	//boolean is used in bSim to see when the tree is running
	public boolean isRunning() {
		numberofballs = 0;
		stillrunning(root);
		if (numberofballs != 0) return true; //return false only if 0 balls running
			
	return false;
	
	}
	
	
//initializing data for binaryTree
	class bNode {
		aBall data;
		bNode left;
		bNode right;
	}
}
