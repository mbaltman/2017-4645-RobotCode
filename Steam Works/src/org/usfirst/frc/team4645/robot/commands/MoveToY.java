package org.usfirst.frc.team4645.robot.commands;

import org.usfirst.frc.team4645.robot.Robot;
import org.usfirst.frc.team4645.robot.OI;
import org.usfirst.frc.team4645.robot.RobotMap;
import org.usfirst.frc.team4645.robot.subsystems.SwerveDrive;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToY extends Command 
{
	
	double distance;
	double drivingDistance;
	
	double curDrivFLPosition;
	double curDrivBRPosition;
	
    public MoveToY(double distance) 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveDrive);
    	this.distance = distance;
    	
    	//set distance to position
    	drivingDistance = Math.abs(distance) * 1670.84;
    	
    	curDrivFLPosition = SwerveDrive.drivingMotorFrontLeft.getEncPosition();
    	curDrivBRPosition = SwerveDrive.drivingMotorBackRight.getEncPosition();
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    	
    	//get wheel positions
    	double curFRPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorFrontRight);
    	double curFLPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorFrontLeft);
    	double curBRPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorBackRight);
    	double curBLPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorBackLeft);
    	
    	//get wheel angles
    	double curFRAngle = Robot.swerveDrive.getAngle(curFRPosition);
    	double curFLAngle = Robot.swerveDrive.getAngle(curFLPosition);
    	double curBRAngle = Robot.swerveDrive.getAngle(curBRPosition);
    	double curBLAngle = Robot.swerveDrive.getAngle(curBLPosition);
    	
    	//calc relative magnitudes
    	double newXMagFR = Robot.swerveDrive.calcRelMagX(0, Math.signum(distance), curFRAngle);
   		double newYMagFR = Robot.swerveDrive.calcRelMagY(Math.signum(distance), 0, curFRAngle);
   		double newXMagFL = Robot.swerveDrive.calcRelMagX(0, Math.signum(distance), curFLAngle);
    	double newYMagFL = Robot.swerveDrive.calcRelMagY(Math.signum(distance), 0, curFLAngle);
    	double newXMagBR = Robot.swerveDrive.calcRelMagX(0, Math.signum(distance), curBRAngle);
    	double newYMagBR = Robot.swerveDrive.calcRelMagY(Math.signum(distance), 0, curBRAngle);
    	double newXMagBL = Robot.swerveDrive.calcRelMagX(0, Math.signum(distance), curBLAngle);
    	double newYMagBL = Robot.swerveDrive.calcRelMagY(Math.signum(distance), 0, curBLAngle);
    			
    	//get position difference
    	double positionDifFR = Robot.swerveDrive.getPositionDif(newXMagFR, newYMagFR);
    	double positionDifFL = Robot.swerveDrive.getPositionDif(newXMagFL, newYMagFL);
    	double positionDifBR = Robot.swerveDrive.getPositionDif(newXMagBR, newYMagBR);
    	double positionDifBL = Robot.swerveDrive.getPositionDif(newXMagBL, newYMagBL);
    	
    	//set steering motor position
    	Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontRight, curFRPosition, positionDifFR, 0, RobotMap.FRONTRIGHT_ERROR);
		Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontLeft, curFLPosition, positionDifFL, 0, RobotMap.FRONTLEFT_ERROR);
		Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackRight, curBRPosition, positionDifBR, 0, RobotMap.BACKRIGHT_ERROR);
		Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackLeft, curBLPosition, positionDifBL, 0, RobotMap.BACKLEFT_ERROR);
		
		//set driving motor output
		boolean finalFR = positionDifFR + RobotMap.FRONTRIGHT_ERROR > -5 && positionDifFR + RobotMap.FRONTRIGHT_ERROR < 5;
		boolean finalFL = positionDifFL + RobotMap.FRONTLEFT_ERROR > -5 && positionDifFL + RobotMap.FRONTLEFT_ERROR < 5;
		boolean finalBR = positionDifBR + RobotMap.BACKRIGHT_ERROR > -5 && positionDifBR + RobotMap.BACKRIGHT_ERROR < 5;
		boolean finalBL = positionDifBL + RobotMap.BACKLEFT_ERROR > -5 && positionDifBL + RobotMap.BACKLEFT_ERROR < 5;
		
		if (finalFR && finalFL && finalBR && finalBL)
		{
			SwerveDrive.drivingMotorFrontLeft.changeControlMode(TalonControlMode.Position);
			SwerveDrive.drivingMotorFrontLeft.set(curDrivFLPosition + drivingDistance);
			SwerveDrive.drivingMotorBackRight.changeControlMode(TalonControlMode.Position);
			SwerveDrive.drivingMotorBackRight.set(curDrivBRPosition + drivingDistance);
			
			double motorOutput = (SwerveDrive.drivingMotorFrontLeft.getOutputVoltage() * 100) / 12;
			SwerveDrive.drivingMotorFrontRight.set(motorOutput);
			SwerveDrive.drivingMotorBackLeft.set(motorOutput);
		}
		
		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	if (SwerveDrive.drivingMotorFrontLeft.getEncPosition() < curDrivFLPosition + drivingDistance + 2 
        		&& SwerveDrive.drivingMotorFrontLeft.getEncPosition() > curDrivFLPosition + drivingDistance - 2) 
	{
        	if (SwerveDrive.drivingMotorBackRight.getEncPosition() < curDrivBRPosition + drivingDistance + 2
        			&& SwerveDrive.drivingMotorBackRight.getEncPosition() > curDrivBRPosition + drivingDistance -2) 
		{
        		return true;
        	}
        }
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
