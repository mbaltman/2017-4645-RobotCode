package org.usfirst.frc.team4645.robot.subsystems;

import org.usfirst.frc.team4645.robot.Robot;

import org.usfirst.frc.team4645.robot.RobotMap;
import org.usfirst.frc.team4645.robot.commands.ReservoirCommand;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import com.ctre.CANTalon;


/**
 *
 */
public class Reservoir extends Subsystem 
{

	public static final CANTalon reservoirMotor =  new CANTalon(RobotMap.reservoirMotorPort);

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    public void spinReservoir()
    {
    	reservoirMotor.set(-1);
    }
    
    public void stopReservoir()
    {
    	reservoirMotor.set(0);
    }
}

