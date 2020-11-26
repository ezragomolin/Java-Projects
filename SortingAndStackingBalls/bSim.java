import java.awt.Color;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram {

	// parameters used
	private static final int WIDTH = 1200; // n.b screen coordinates
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final double SCALE = HEIGHT / 100; // pixel to meter
	private static final int NUMBALLS = 60; // 100 balls being generated
	private static final double MINSIZE = 1; // minimum ball radius
	private static final double MAXSIZE = 7; // max ball radius
	private static final double EMIN = 0.2; // min loss coefficient
	private static final double EMAX = 0.6; // max loss coefficient
	private static final double VoMIN = 40; // min velocity (m/s)
	private static final double VoMAX = 50; // max velocity (m/s)
	private static final double ThetaMIN = 80; // min launch angle degrees
	private static final double ThetaMAX = 100; // max launch angle degreees
	
	public void run() {

		this.resize(WIDTH, HEIGHT + OFFSET); // resizing window

		GLine ground = new GLine(0, HEIGHT, WIDTH, HEIGHT);
		add(ground);

		RandomGenerator rgen = new RandomGenerator(); // creating rgen instance

		bTree myTree = new bTree();

		rgen.setSeed((long) 424242); // pseudorandom
		
		for (int i = 0; i < NUMBALLS; i++) {

			double bSize = rgen.nextDouble(MINSIZE, MAXSIZE);
			Color bColor = rgen.nextColor();
			double bLoss = rgen.nextDouble(EMIN, EMAX);
			double Vo = rgen.nextDouble(VoMIN, VoMAX);
			double theta = rgen.nextDouble(ThetaMIN, ThetaMAX);

			double Xi = 100;
			double Yi = bSize;

			
			aBall rball = new aBall(Xi, Yi, Vo, theta, bSize, bColor, bLoss);
			add(rball.myBall);
			if (myTree.root == null)   //First ball in stack
				myTree.root = myTree.makeNode(rball);
			myTree.addNode(myTree.root, rball);
		    rball.start();
		}
/**
 * when the balls stop moving, a prompt appears  and when the screen is clicked, all the balls will stack in their desired space
 */
		while(myTree.isRunning());
		GLabel prompt = new GLabel("CR to continue", 1060, 560);
		prompt.setColor(Color.RED);
		add(prompt);
		waitForClick();
		myTree.inorder();
		prompt.setLabel("All Stacked!");
		
	}
	
}