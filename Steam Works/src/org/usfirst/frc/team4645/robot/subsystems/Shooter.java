package org.usfirst.frc.team4645.robot.subsystems;

import org.usfirst.frc.team4645.robot.OI;
import org.usfirst.frc.team4645.robot.Robot;
import org.usfirst.frc.team4645.robot.RobotMap;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
/**
 *
 */

public class Shooter extends Subsystem 
{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static CANTalon shooterMotor = new CANTalon(RobotMap.shooterMotor);
	public static Talon feederMotor = new Talon(RobotMap.feederMotor);
	
	
	
	
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	
        
    }
   
    public void shoot(double speed)
    {
    	shooterMotor.changeControlMode(TalonControlMode.Speed);
    	shooterMotor.set(speed);
    	
    	//if (shooterMotor.getEncVelocity() < -(speed - 30))
    	//{
    	feederMotor.set(-1);
    	//}
    	//else
    	//{
    		//feederMotor.set(0);
    	//}
    }
    public void shooterStop()
    {
    	shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
    	shooterMotor.set(0);
    	feederMotor.set(0);
    }
    
    public void feeder()
    {
    	feederMotor.set(-1);
    }


    
}

