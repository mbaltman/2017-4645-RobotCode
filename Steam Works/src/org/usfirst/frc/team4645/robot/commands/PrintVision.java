package org.usfirst.frc.team4645.robot.commands;

import org.usfirst.frc.team4645.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
/**
 *
 */
public class PrintVision extends Command {

    public PrintVision() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.visionSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double[] boilerInfo = Robot.visionSubsystem.returnBoilerInformation();
    	double[] gearInfo = Robot.visionSubsystem.returnGearInformation();
    	
    	SmartDashboard.putNumber("Boiler: distance in X", boilerInfo[0]);
    	SmartDashboard.putNumber("Boiler: distance in Y", boilerInfo[1]);
    	SmartDashboard.putNumber("Boiler: centerX", boilerInfo[2]);
    	SmartDashboard.putNumber("Boiler: centerY",boilerInfo[3]);
    	SmartDashboard.putNumber("Boiler: width Of tape",boilerInfo[4]);
    	SmartDashboard.putNumber("Boiler: height of tape", boilerInfo[5]);
    	
    	SmartDashboard.putNumber("Gear: distance in X", gearInfo[0]);
    	SmartDashboard.putNumber("Gear: distance in Y", gearInfo[1]);
    	SmartDashboard.putNumber("Gear: centerX", gearInfo[2]);
    	SmartDashboard.putNumber("Gear: centerY", gearInfo[3]);
    	SmartDashboard.putNumber("Gear: width Of tape", gearInfo[4]);
    	SmartDashboard.putNumber("Gear: height of tape", gearInfo[5]);
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
