package org.usfirst.frc.team4645.robot.subsystems;

import org.usfirst.frc.team4645.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem 
{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static final Talon intakeMotor =  new Talon(RobotMap.intakeMotor);
	
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void intakeIn()
    {
    	intakeMotor.set(-.85);
    	
    }
    public void intakeStop()
    {
    	intakeMotor.set(0);
    }
    
    
    
    
}

