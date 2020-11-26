import java.awt.Color;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.graphics.GLabel;
import acm.graphics.GLine;

import acm.gui.DoubleField;
import acm.gui.IntField;
import acm.gui.TableLayout;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram implements ChangeListener, ItemListener {

// parameters used
	private static final int WIDTH = 1400; 
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final double SCALE = HEIGHT / 100; // pixel to meter
	bSim link;

	RandomGenerator rgen = new RandomGenerator(); // creating rgen instance
	
	
	//creating variables JComboBox
	JComboBox<String> bSimC;
	JComboBox<String> bFileC;
	JComboBox<String> bEditC;
	JComboBox<String> bHelpC;
	JToggleButton traceme;

	//creating variables for the chosen values of the ball properties
	slide numballsb;
	slide minsizeb;
	slide maxsizeb;
	slide minlossb;
	slide maxlossb;
	slide minvelb;
	slide maxvelb;
	slide minthetab;
	slide maxthetab;
	bTree myTree = new bTree();
	
	//adding line 

	public void addline() {
		GLine ground = new GLine(0, HEIGHT, WIDTH, HEIGHT);
		add(ground);
	}
	
	
	
	
//the main run method
	public void init() {
		rgen.setSeed((long) 424242);
		addline();
		this.resize(WIDTH, HEIGHT + OFFSET); // resizing window
		
		/**
		 * Here we are adding items to the JComboBox
		 * As well as making multiple Boxes
		 * @param top represents the top panel where the JComboBoxes appear
		 */
		bSimC = new JComboBox<String>();
		bSimC.addItem("bSim");
		bSimC.addItem("Run");
		bSimC.addItem("Stack");
		bSimC.addItem("Clear");
		bSimC.addItem("Stop");
		bSimC.addItem("Quit");

		bFileC = new JComboBox<String>();
		bFileC.addItem("File");

		bEditC = new JComboBox<String>();
		bEditC.addItem("Edit");

		bHelpC = new JComboBox<String>();
		bHelpC.addItem("Help");

		JPanel top = new JPanel(new TableLayout(1, 4));
		top.add(bSimC);
		top.add(bFileC);
		top.add(bEditC);
		top.add(bHelpC);
		add(top, NORTH);
		
		/**
		 * Here we are creating parameters for the User Interface
		 * @param sidebar represents the label on the right side of the screen
		 * @param right represents the JPannel on the right side of the screen
		 * This panel on the side allows users to choose their ball parameters in ranges and the random generator will use those min/max values
		 */
		JLabel sidebar = new JLabel("General Simulation Parameters");
		numballsb = new slide("NUMBALLS:", 60, 1, 255);
		minsizeb = new slide("MIN SIZE:", 1, 1, 25);
		maxsizeb = new slide("MAX SIZE:", 7, 1, 25);
		minlossb = new slide("LOSS MIN:", 0.2, 0.0, 1.0);
		maxlossb = new slide("LOSS MAX:", 0.6, 0.0, 1.0);
		minvelb = new slide("MIN VEL:", 40, 1, 200);
		maxvelb = new slide("MAX VEL:", 50, 1, 200);
		minthetab = new slide("THETA MIN:", 80, 1, 180);
		maxthetab = new slide("THETA MAX:", 100, 1, 180);

		JPanel right = new JPanel(new TableLayout(10, 1));
		right.add(sidebar);
		right.add(numballsb);
		right.add(minsizeb);
		right.add(maxsizeb);
		right.add(minlossb);
		right.add(maxlossb);
		right.add(minvelb);
		right.add(maxvelb);
		right.add(minthetab);
		right.add(maxthetab);

		add(right, EAST);  //adds to right side of screen

		bSimC.addItemListener(this);

		
		
		traceme = new JToggleButton("Trace");
		add(traceme, SOUTH);
		traceme.addChangeListener(this);

	
	}

	public void run() {
		
	}
	

	
	/**
	 * doSim method actually generates the balls with the following parameters
	 * @param NUMBALLS represents number of balls in simulation
	 * @param MINSIZE represents the minimum radius of the balls
	 * @param MAXSIZE represents the maximum radius of the ball
	 * @param EMIN represents the minimum loss coefficient
	 * @param EMAX represents the max loss coefficient
	 * @param ThetaMIN represents minimum launch angle
	 * @param ThetaMAX represents the maximum launch angle
	 * @param VoMIN  represents the minimum launch velocity
	 * @param VoMAX represents the maximum launch velocity
	 * This method adds the balls to the screen by creating a new aBall 
	 */

	public void doSim(int NUMBALLS, int MINSIZE, int MAXSIZE, double EMIN, double EMAX, int ThetaMIN, int ThetaMAX,
			int VoMIN, int VoMAX) {
		
		
		for (int i = 0; i < NUMBALLS; i++) {

			double bSize = rgen.nextDouble(MINSIZE, MAXSIZE);
			Color bColor = rgen.nextColor();
			double bLoss = rgen.nextDouble(EMIN, EMAX);
			double Vo = rgen.nextDouble(VoMIN, VoMAX);
			double theta = rgen.nextDouble(ThetaMIN, ThetaMAX);
			double Xi = 100;
			double Yi = bSize;
			
			
			aBall rball = new aBall(Xi, Yi, Vo, theta, bSize, bColor, bLoss,link);
			add(rball.myBall);
			rball.start();
			if (myTree.root == null) // First ball in stack
			myTree.root = myTree.makeNode(rball);
			myTree.addNode(myTree.root, rball);
			

		}
		
	}
	

	public void stateChanged(ChangeEvent e) {
		if (e.getSource()== traceme) {
			if (traceme.isSelected()){
			link = this;
			}
			else {
			link=null;
			}	
		}
	}
/**
 * The following tells the program what to display depending on the jComboBox option the user clicks on
 * If chosen run, the program runs
 * if chosen stack, the program stacks the balls
 * if chosen clear, the program clears the screen
 * if chosen stop, the screen freezes
 * if chosen quit, the program exits
 */
	public void itemStateChanged(ItemEvent e) {

		JComboBox source = (JComboBox) e.getSource();

		if (source == bSimC && e.getStateChange() == 1) {

			if (bSimC.getSelectedIndex() == 1) {

				System.out.println("Starting simulation");
				
				doSim(numballsb.intval.getValue(), minsizeb.intval.getValue(), maxsizeb.intval.getValue(),
						minlossb.doubleval.getValue(), maxlossb.doubleval.getValue(), minthetab.intval.getValue(),
						maxthetab.intval.getValue(), minvelb.intval.getValue(), maxvelb.intval.getValue());
				
			}

			else if (bSimC.getSelectedIndex() == 2)  {
				System.out.println("Stacking Balls");
				myTree.lastSize = 0;
				myTree.yo = 0;
				myTree.xo = 0;
				myTree.doStack() ;
				GLabel prompt2 = new GLabel("ALL STACKED", 1060, 560);
				prompt2.setColor(Color.RED);
				add(prompt2);
				
			    
			}
			  

			else if (bSimC.getSelectedIndex() == 3) {
				System.out.println("CLEAR SELECTED");
				this.removeAll();
				addline();
			  myTree = new bTree();
			}

			else if (bSimC.getSelectedIndex() == 4) {
				System.out.println("STOP SELECTED");
				myTree.stopscreen();

			}

			else if (bSimC.getSelectedIndex() == 5) {
				System.out.println("QUIT SELECTED");
				System.exit(0);
			}

		}
		

	}
	
	
	
}