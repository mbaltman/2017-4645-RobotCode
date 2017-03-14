package org.usfirst.frc.team4645.robot.subsystems;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
//import org.usfirst.frc.team4645.robot.subsystems.*;
//import org.usfirst.frc.team4645.robot.RobotMap;
import org.usfirst.frc.team4645.robot.commands.PrintVision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */


public class Vision extends Subsystem {

	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	
	//private VisionThread visionThreadBoiler;
	private double centerXB = 1;
	private double centerYB = 1;
	private double widthB=1;
	private double heightB=1;
	private final Object imgLockBoiler = new Object();
	
	private VisionThread visionThreadGear;
	private double centerXG = 1;
	private double centerYG = 1;
	private double widthG=1;
	private double heightG=1;
	private final Object imgLockGear = new Object();
	
	private int i=0;
	
    
    public void initDefaultCommand() 
    {
    	UsbCamera cameraBoiler = CameraServer.getInstance().startAutomaticCapture(0);
    	
    	UsbCamera cameraGear = CameraServer.getInstance().startAutomaticCapture(1);
		
		cameraBoiler.setResolution(IMG_WIDTH, IMG_HEIGHT);
		    //draws a rectangle around the biggest contour and gets x and y coordinate of center 
		  
	    cameraGear.setResolution(IMG_WIDTH, IMG_HEIGHT);
		    

	    visionThreadGear = new VisionThread(cameraGear, new Pipeline(), pipeline -> 
	    {
	        if (!pipeline.filterContoursOutput().isEmpty())
	        {
	            Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
	            //Rect rSmall = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
	            
	            
	            synchronized (imgLockGear) {
	            	widthG= (r2.width+ r1.width)/2;
		            heightG = (r1.height+r2.height)/2;
	            	
	            	centerXG = (r1.x + r2.x + widthG)/2;
	               
	               
	               centerYG =(r1.y + r2.y + heightG)/2;
	               i=i+1;
	               SmartDashboard.putNumber("counter for gear", i);
	               
	              
	            }
	        }
	    }
	    );
	    visionThreadGear.start();
	    
//		    visionThreadBoiler = new VisionThread(cameraBoiler, new Pipeline(), pipeline -> 
//		    {
//		        if (!pipeline.filterContoursOutput().isEmpty())
//		        {
//		            Rect rBig = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
//		            //Rect rSmall = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
//		            
//		            
//		            synchronized (imgLockBoiler) {
//		               centerXB = rBig.x + rBig.width/2;
//		               centerYB = rBig.y + rBig.height/2;
//		               widthB= rBig.width;
//		               heightB = rBig.height;
//		              
//		            }
//		        }
//		    }
//		    );
//		    
//		    visionThreadBoiler.start();
		    
		    
		    
		    setDefaultCommand(new PrintVision());
	}
	    
    
   
    public double[] returnBoilerInformation()
	{
    	
    	double[] coordinateB={0,0,0,0,0,0};
    	
//    	synchronized (imgLockBoiler) 
//    	 {
//    		coordinateB[2]= centerXB;
//    		coordinateB[3]= centerYB;
//    		 
//    		coordinateB[4]= widthB;
//    		
//    		 coordinateB[5]= heightB;
//    		 
//    	 }
    	
    	double shortestDistance =(11.8 * 510.0)/widthB;//calculate exact distance from camera to center of tape
    	
    	shortestDistance= shortestDistance * .0254; //convert to meters
		
    	double sine = (coordinateB[2] -160.0) /320.0;
		
    	double distanceY= Math.sqrt((shortestDistance* shortestDistance)-(1.83 * 1.83));
		double distanceX = distanceY * sine;
		
		coordinateB[0]=distanceX;
		coordinateB[1]=distanceY;
		
    	return coordinateB;
	
	}
    
    public double[] returnGearInformation()
   	{
       	
       	double[] coordinateG={0,0,0,0,0,0};
       	
       	synchronized (imgLockGear) 
       	 {
       		coordinateG[2]= centerXG;
       		coordinateG[3]= centerYG;
       		 
       		coordinateG[4]= widthG;
       		
       		coordinateG[5]= heightG;
       		
       	 }
       	
       	double shortestDistance =(2.0*510.0)/widthG;//calculate exact distance from camera to center of tape
       	shortestDistance= shortestDistance * .0254; //convert to meters
   		
       	double sine = (coordinateG[2] -160.0) /320.0;
   		double distanceY= Math.sqrt((shortestDistance* shortestDistance)-(.04 * .04));
   		
   		double distanceX = distanceY * sine;
   		
   		coordinateG[0]=distanceX;
   		coordinateG[1]=distanceY;
   	     
   	     
       	return coordinateG;
   	
   	}
 
}

