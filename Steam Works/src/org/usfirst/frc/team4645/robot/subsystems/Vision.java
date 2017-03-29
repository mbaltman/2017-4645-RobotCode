package org.usfirst.frc.team4645.robot.subsystems;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4645.robot.subsystems.Pipeline;
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

	
	private VisionThread visionThreadBoiler;
	public static double centerXB = 1;
	private static double centerYB = 1;
	private static double widthB=1;
	private static double heightB=1;
	private final Object imgLockBoiler = new Object();
	
	private VisionThread visionThreadGear;
	private static double centerXG = 1;
	private static double centerYG = 1;
	private static double widthG=1;
	private static double heightG=1;
	public double distanceXGear= 0;
	public double distanceYGear=0;
	private final Object imgLockGear = new Object();
	
	private int i=0;
	private int j = 0;
	
	UsbCamera cameraGear;
    
    public void initDefaultCommand() 
    {
    cameraGear = CameraServer.getInstance().startAutomaticCapture(0);
		    
	}
	    
    public void start()
    {
    	SmartDashboard.putString("VisionStatus", "start");
    	//UsbCamera cameraBoiler = CameraServer.getInstance().startAutomaticCapture(0);
    	
    	
		
	//	cameraBoiler.setResolution(IMG_WIDTH, IMG_HEIGHT);
		    //draws a rectangle around the biggest contour and gets x and y coordinate of center 
		  
	    cameraGear.setResolution(IMG_WIDTH, IMG_HEIGHT);
		    

	    visionThreadGear = new VisionThread(cameraGear, new Pipeline(), pipeline -> 
	    {
	    	i=i+1;
	    	SmartDashboard.putNumber("counter for gear thread", i);
	       SmartDashboard.putNumber("Number of Contours", pipeline.filterContoursOutput().size());
	    	 
	    	if (pipeline.filterContoursOutput().size() >1)
	        {
	            Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
	           
	            SmartDashboard.putString("VisionStatus", "Object Seen");
	            synchronized (imgLockGear) 
	            {
	            	widthG= (double)(r2.width+ r1.width)/2.0;
		            heightG =(r1.height+r2.height)/2;
	            	
	            	centerXG= (r1.x + r2.x + widthG)/2;
	               
	               
	               centerYG = (r1.y + r2.y + heightG)/2;
	               
	               
	               
	               j=j+1;
	               SmartDashboard.putNumber("counter for gearPipeline",j);
	             
	               
	              
	            }
	        }
	    	else
	    	{
	    		SmartDashboard.putString("VisionStatus", "Object Not Seen");
	    	}
	    		 
	    }
	    );
	    visionThreadGear.start();
	   
    }
    
    public double[] returnGearInformation()
   	{
       	
       	double[] coordinateG={0,0,0,0,0,0,0};
       	
       	synchronized (imgLockGear) 
       	 {
       		coordinateG[2]= centerXG;
       		coordinateG[3]= centerYG;
       		 
       		coordinateG[4]= widthG;
       		
       		coordinateG[5]= heightG;
       		
       	 }
       	
       	double shortestDistance =(2.0 * 375)/widthG;//calculate exact distance from camera to center of tape
       	shortestDistance= shortestDistance * .0254; //convert to meters
   		coordinateG[6]=shortestDistance;
       	
       	
       	double sine = (coordinateG[2] -160.0) /320.0;
   		double distanceY= Math.sqrt((shortestDistance* shortestDistance)-(.04 * .04));
   		
   		double distanceX = distanceY * sine;
   		
   		coordinateG[0]=distanceX;
   		coordinateG[1]=distanceY;
   	    
   	     distanceXGear=distanceX;
   	     distanceYGear = distanceY;
       	return coordinateG;
   	
   	}
 
}

