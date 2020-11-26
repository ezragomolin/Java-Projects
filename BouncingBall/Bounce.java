import java.awt.Color;

import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Bounce extends GraphicsProgram{
	
	//Work by Ezra Gomolin Student Id:260926917 (Assignment 1 ECSE-202)
	
	//users chooses values for initial velocity, launch angle, energy loss parameter, radius of ball
	double Vo=readDouble ("Enter the initial velocity of the ball in m/s [0,100]:");
	double angle= readDouble ("Enter the launch angle [0,90]:");
	double loss= readDouble ("Enter the energy loss parameter [0,1]:");
    double bSize= readDouble ("Enter the radius of the ball in m [0.1,5.0]:");
	//screen parameters
	private static final int WIDTH=600;                        //width of screen
	private static final int HEIGHT=600;                      //height of screen
	private static final int OFFSET=200;                     //bottom of screen to ground plate
    //simulation parameters
	private static final double g=9.8;                      //gravity constant
	private static final double pi=3.141592654;            //pi to convert angles
	private static final double Xint=5.0;                 //initial ball location (x)
    double Yint=bSize;                                   //initial ypos
	private static final double TICK=0.1;               //clock tick duration in seconds
	private static final double ETHR=0.01;             //If KEx or KEy are less than ETHR, stop
	private static final double XMAX=100.0;           //max value of x
	private static final double YMAX=100.0;          //max value of y
	private static final double PD=1.0;             //trace point diameter of ovals
	private static final double SCALE=HEIGHT/XMAX; // pixels/meter scale
	private static final double k=0.0016; 
	double time =0;                              //time starts at 0
	double X=Xint;                              //intitial x position 5
	double Y=Yint;                             //intitial y pos
	 
	
	
	
	
	
	public void run () {
		this.resize(WIDTH,OFFSET+HEIGHT);            //resize screen
		
		GRect ground=new GRect(0, 600, 600, 3);    //properties of grect
		ground.setFilled(true);                   //filling in grect
		add(ground);                             //create ground
		
		GOval myBall = new GOval((X+Xint)*SCALE,HEIGHT-2*Yint*SCALE,2*bSize*SCALE,2*bSize*SCALE); //properties of ball
		myBall.setFilled(true);                   //filling ball
		myBall.setColor(Color.RED);              //color  of ball
		add(myBall);							// create the ball
		pause(1000);                           //pause for a second
		
		
		
		double Vox=Vo*Math.cos((angle*pi)/180);      //creating horizontal velocity parameter
		double Voy=Vo*Math.sin((angle*pi)/180);     //creating vertical velocity parameter
		double Vt=g/(4*pi*bSize*bSize*k);          //vt constant depending on bsize	
		double time=0;                            //time starts at 0
		double Xlast=Xint;                       //creating variable for initial Xlast
		double Ylast=bSize;                     //creating variable for initial Ylast
		double Vx=Vox;                         //variable for Vx initial is Vox
		double Vy=Voy;                        //variable for Vy initial is Voy
		double Scrx;                         //creating variable for x pos, initial is at Xint
		double Scry;                        //creating variable for Y pos of ball, initial at Yint
		double KEx=0;                      //initially no loss of energy in  x
		double KEy=0;                     //initially no energy of loss for y
		double Xo=Xint;                  // declaring variable Xo; so ball keeps moving forward after bounce
		
		//repeats function 
		while(true) {
			
			 
			Xlast=X;                                                           //setting up X last to be the prior X component
		    Ylast=Y;                                                          // setting up Y last to be the prior Y component
		    time+=TICK;                                                      // adding to the time each time
			X= Xint+ (Vox*Vt/g*(1-Math.exp(-g*time/Vt)));                   //kinematic equation for X position
		    Y= bSize +((Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time));  //kinematic equation for Y position
		    Vx=(X-Xlast)/TICK;                                            //formula for Vx
		    Vy=(Y-Ylast)/TICK;                                           //formula for Vy
		    Scrx = (Xo+X)*SCALE;                                        //ball's x location on screen
		    Scry=HEIGHT-(bSize+Y)*SCALE;                               // ball's Y location on screen
		    myBall.setLocation(Scrx, Scry);                           //setting ball at X and Y location
		    pause(10);                                               //pause ball
		    
		    
		    
		   if(Y>=bSize) add(new GOval ((int)((Xo+X)*SCALE+bSize*SCALE),(int)(HEIGHT-Y*SCALE+bSize*2*SCALE),PD,PD)); //Tracing the balls trajectory as long as its above the ground
		    
		   System.out.printf("t: %.2f X: %.2f Y: %.2f Vx: %.2f Vy:%.2f\n", time,Xo+X,Y,Vx,Vy);                     //Printing some of our variables
		    
		  //detecting when a collision occurs
		  if(Vy<0 && Y<=bSize) {
			    
			   
			    KEx=0.5*Vx*Vx*(1-loss);	                 //kinetic energy after loss (x)
			    KEy=0.5*Vy*Vy*(1-loss);                 //kinetic energy after loss (y)
			    Y=Yint;                                // resetting y pos
			    Xo+=X-Xint;                           //resetting x pos
			    time=0;                              //resetting time 
			    Vox=Math.sqrt(2*KEx);               // resulting velocity (x) after colision
			    Voy=Math.sqrt(2*KEy);              // resulting velocity (y) after colision
			    
			    
			   
			 
		        
		      //stopping the program 
			   if(KEx<ETHR || KEy<ETHR)break;
			   
					 
			    }
		  
		    
		   		
		
		}
	    
	
		

	}
	

}