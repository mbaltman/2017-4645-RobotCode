package org.usfirst.frc.team4645.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4645.robot.subsystems.SwerveDrive;
import org.usfirst.frc.team4645.robot.Robot;

/**
 *
 */
public class ResetGyro extends Command 
{

    public ResetGyro() 
    {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerveDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	SwerveDrive.gyro.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    }
}
