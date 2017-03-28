package org.usfirst.frc.team4645.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4645.robot.*;
import org.usfirst.frc.team4645.robot.subsystems.Reservoir;

/**
 *
 */
public class ShootCommand extends Command {

    public ShootCommand() 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.shooterSubsystem);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	Robot.shooterSubsystem.shoot(75000);
    	//Robot.reservoirSubsystem.spinIn();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	//return Reservoir.servo1.get() > 0.99;
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	Robot.shooterSubsystem.shooterStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	end();
    }
}
