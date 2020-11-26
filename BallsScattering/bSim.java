
import java.awt.Color;

import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram {

	//parameters used
	private static final int WIDTH=1200;                           //n.b screen coordinates
	private static final int HEIGHT=600;
	private static final int OFFSET=200;
	private static final double SCALE=HEIGHT/100;                //pixel to meter
	private static final int NUMBALLS=100;                      //100 balls being generated
	private static final double MINSIZE=1;                     //minimum ball radius
	private static final double MAXSIZE=10;                   //max ball radius
	private static final double EMIN=0.1;                    //min loss coefficient
	private static final double EMAX=0.6;                   //max loss coefficient
	private static final double VoMIN=40;                  //min velocity (m/s)
	private static final double VoMAX=50;                 //max velocity (m/s)
	private static final double ThetaMIN=80;             //min launch angle degrees
	private static final double ThetaMAX=100;           //max launch angle degreees
	
	
	

	public void run() {
		
		this.resize(WIDTH,HEIGHT+OFFSET);                          //resizing window
		
		
		
		GLine ground=new GLine(0,HEIGHT,WIDTH,HEIGHT);
		add(ground);
		
		RandomGenerator rgen=new RandomGenerator(); //creating rgen instance
		//rgen.setSeed((long) 0.12345);       //pseudorandom
		for(int i=0;i < NUMBALLS; i++) {
			
			
			double bSize=rgen.nextDouble(MINSIZE,MAXSIZE);
			Color bColor=rgen.nextColor();
			double bLoss=rgen.nextDouble(EMIN,EMAX);
			double Vo=rgen.nextDouble(VoMIN,VoMAX);
			double theta=rgen.nextDouble(ThetaMIN, ThetaMAX);
			
			
			
			double Xi=100;
			double Yi=bSize; 
			
			
			
			
		aBall rball=new aBall(Xi,Yi,Vo,theta,bSize,bColor,bLoss);
		add(rball.myBall);
		rball.start();
		
		
		
			
				
			
		}
		
		 
		
	}
	
	

}
