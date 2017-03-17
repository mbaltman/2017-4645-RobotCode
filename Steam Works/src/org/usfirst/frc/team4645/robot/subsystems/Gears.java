package org.usfirst.frc.team4645.robot.subsystems;

import org.usfirst.frc.team4645.robot.*;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 *
 */
public class Gears extends Subsystem 
{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static final Servo gearLeft =  new Servo(RobotMap.gearServoLeft);
	public static final Servo gearRight =  new Servo(RobotMap.gearServoRight);
	
	
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
   
    public void holdGear()
    {
    	
    	gearLeft.set(1);
    	gearRight.set(0);
    	
    }
   
    public void dropGear()
    {
    	gearLeft.set(.5);//.4
    	gearRight.set(.5);//.6
    }
    
    public void pushGear()
    {
    	
    	gearLeft.set(0);
    	gearRight.set(1);
    	
    }
   
}












