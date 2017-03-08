package org.usfirst.frc.team4645.robot.commands;

import org.usfirst.frc.team4645.robot.Robot;
import org.usfirst.frc.team4645.robot.subsystems.Gears;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DropGearCommand extends Command {

	public DropGearCommand() 
    {
       // Use requires() here to declare subs;ystem dependencies
       // eg. requires(chassis);
   	requires(Robot.gearSubsystem);
   }

   // Called just before this Command runs the first time
   protected void initialize() {
   }

   // Called repeatedly when this Command is scheduled to run
   protected void execute() {
   	Robot.gearSubsystem.dropGear();
   	
   	
   
   }

   // Make this return true when this Command no longer needs to run execute()
   protected boolean isFinished() {
       return true;
   }

   // Called once after isFinished returns true
   protected void end() {
   }

   // Called when another command which requires one or more of the same
   // subsystems is scheduled to run
   protected void interrupted() {
   }
}