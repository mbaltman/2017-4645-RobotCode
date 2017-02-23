package org.usfirst.frc.team4645.robot.subsystems;

import org.usfirst.frc.team4645.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climbing extends Subsystem 
{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static Talon climbingTopMotor = new Talon(RobotMap.hangerMotorT);
	public static Talon climbingBottomMotor = new Talon(RobotMap.hangerMotorB);
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void stopClimb()
    {
     	climbingTopMotor.set(0);
    	climbingBottomMotor.set(0);
    }
    public void startClimb()
    {
    	climbingTopMotor.set(1);
        climbingBottomMotor.set(1);
    
    }
}

