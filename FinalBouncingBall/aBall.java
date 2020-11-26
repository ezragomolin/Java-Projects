import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton;

import acm.graphics.GLabel;
import acm.graphics.GOval;

public class aBall extends Thread {

	/**
	 * This Program runs a simulation that extends assignment 1 and 2 Instead of all our balls landing we
	 * have 60 balls with randomly generated parameters that will stack on top of eachother and create a new stack dependening 
	 * on the difference in size
	 * @author Ezra Gomolin Assignment 4-ECSE-202 Many of the Javadoc and some of
	 *         the code was taken from assignment-1,2,3,4 instructions
	 */

	/**
	 * @param HEIGHT int The height of the screen
	 * @param SCALE  int The scale to go from pixels to real life coordinates
	 * @param TICK   double The time interval
	 */
	
	private bSim link;
	GOval trace;
	private static final int HEIGHT = 600;
	private static final int SCALE = 6;
	private static final double g = 9.8;
	private static final double TICK = 0.1;
	private static double ETHR = 0.01; // Value that is used to break the simulation
	private static double Pi = 3.141592654; // Constant
	private static double k = 0.0001; // Constant

	// Declaring Variables used for the balls
	
	double Xi;
	double Yi;
	double Vo;
	double theta;
	double bSize;
	Color bColor;
	double bLoss;
	GOval myBall;
	boolean isRunning;
	
	
	

	public GOval getBall() {
		return myBall;
	}

	/**
	 * The constructor specifies the parameters for simulation. They are *
	 * 
	 * @param Xi     double The initial X position of the center of the ball
	 * @param Yi     double The initial Y position of the center of the ball
	 * @param Vo     double The initial velocity of the ball at launch
	 * @param theta  double Launch angle (with the horizontal plane)
	 * @param bSize  double The radius of the ball in simulation units
	 * @param bColor Color The initial color of the ball
	 * @param bLoss  double Fraction [0,1] of the energy lost on each bounce
	 */

	public aBall(double Xi, double Yi, double Vo, double theta, double bSize, Color bColor, double bLoss,bSim link) {
		this.Xi = Xi;
		this.Yi = Yi;
		this.Vo = Vo;
		this.theta = theta;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.link = link;
		

		// Setting ball properties
		myBall = new GOval(2 * bSize * SCALE, 2 * bSize * SCALE);
		myBall.setFillColor(bColor);
		myBall.setFilled(true);

	}

	/**
	 * The run method implements the simulation loop from Assignment 1. * Once the
	 * start method is called on the aBall instance, the code in the run method is
	 * executed concurrently with the main program.
	 * 
	 * @param void
	 * @return void
	 */

	public void moveTo(double x, double y) {
		myBall.setLocation(x, y);
	}

	

	
	public void run() {
		
		isRunning = true;
		// Kinematic equations for velocities of ball
		double Vox = Vo * Math.cos((theta * Pi) / 180);
		double Voy = Vo * Math.sin((theta * Pi) / 180);
		double Vt = g / (4 * Pi * bSize * bSize * k);
		// declaring variables to be used with their intial values
		double t = 0;
		double X = Xi;
		double Y = Yi;
		double Ylast = Yi;
		double Xlast = Xi;
		double Vx = Vox;
		double Vy = Voy;
		double KEx = ETHR;
		double KEy = ETHR;
		double Xo = Xi;
		double ScrX;
		double ScrY;
		double KEl = 0.5 * Vo * Vo;
		double KEt = KEx + KEy;
		boolean hasEnoughEnergy = true;
		
		/**
		 * The while loop allows the simulation to occur by repositioning the x and y
		 * values of the ball
		 * 
		 * @param Xlast is used to record the x component of velocity
		 * @param Ylast is used to record the Y component of velocity
		 */
		while (hasEnoughEnergy) {

			Xlast = X;
			Ylast = Y;
			t += TICK;
			X = Vox * Vt / g * (1 - Math.exp(-g * t / Vt));
			Y = bSize + ((Vt / g * (Voy + Vt) * (1 - Math.exp(-g * t / Vt)) - Vt * t));
			Vx = (X - Xlast) / TICK;
			Vy = (Y - Ylast) / TICK;
			
			
			/**
			 * The if statement is used to detect the collision when the ball hits the
			 * ground line
			 * 
			 * @param Xo is declared above and is used as an offset after the collision
			 *           occurs The initial velocity in x and y are represented by @param
			 *           Vox, @param Voy respectively they are getting recalculated and used
			 *           in the next loop in order for the balls to move in their correct
			 *           direction Vox was multiplied by a negative when Vx was negative The
			 *           purpose of that was because KEx is always posative therefore making
			 *           Vox always posative
			 */

			if (Vy < 0 && Y <= Yi) {

				KEx = 0.5 * Vx * Vx * (1 - bLoss);
				KEy = 0.5 * Vy * Vy * (1 - bLoss);
				KEt = KEx + KEy; // total kinetic energy
				t = 0;
				Y = Yi;
				Xo += X;
				Vox = Math.sqrt(2 * KEx);
				Voy = Math.sqrt(2 * KEy);
				if (Vx < 0) {
					Vox = -Vox;
					
				}
				X = 0;
				
				

				/**
				 * The break condition was provided in the instructions of assignement two
				 */

				if ((KEx + KEy) < ETHR || (KEx + KEy) >= (KEl))
					hasEnoughEnergy = false;
				
				   
				else
					KEl = KEx + KEy;

			}
			
		
			/**
			 * @param ScrX represents the X position in screen coordinates
			 * @param ScrY represents the Y position in screen coordinates
			 */

			ScrX = ((Xo + X - bSize) * SCALE);
			ScrY = (HEIGHT - ((Y + bSize) * SCALE));
			myBall.setLocation(ScrX, ScrY);
		
			//if link is not null, trace
		if (link!=null) {
			trace(Xo+X,Y);
		}
		

			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		isRunning = false;
		
	}
	
	

	//this function getbSize will allow us to get the bSize from the rball object in bTree

	public double getbSize() {
		return bSize;

	}
	//trace method
	private void trace (double x, double y) {
		double ScrXtrace = x*SCALE;
		double ScrYtrace=HEIGHT-y*SCALE;
		GOval tracer = new GOval (ScrXtrace, ScrYtrace, 1,1);
		tracer.setColor(this.bColor);
		tracer.setFilled(true);
		link.add(tracer);
	}
}

