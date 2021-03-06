package org.usfirst.frc.team4645.robot.commands;

import org.usfirst.frc.team4645.robot.Robot;
import org.usfirst.frc.team4645.robot.OI;
import org.usfirst.frc.team4645.robot.RobotMap;
import org.usfirst.frc.team4645.robot.subsystems.SwerveDrive;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveToY extends Command 
{
	
	double distance;
	double drivingDistance;
	double curDrivBRPosition;
	boolean finished;
	boolean isOrigPosDone;

	boolean finalFR;
	boolean finalFL;
	boolean finalBR;
	boolean finalBL;
	
    public MoveToY(double distance) 
    {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveDrive);
    	this.distance = distance;
    	
    	
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	//set distance to position
    	drivingDistance = Math.abs(distance) * -1670.84;
    	curDrivBRPosition = SwerveDrive.drivingMotorBackRight.getEncPosition();
    	finished = false;
    	isOrigPosDone = false;

    	finalFR = false;
    	finalFL = false;
    	finalBR = false;
    	finalBL = false;
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
    	
    	if (!isOrigPosDone)
    	{
    		
    		SmartDashboard.putString("status", "!isOrigPosDone");
			SmartDashboard.putString("status2", "!isOrigPosDone");

    		
    		//calc relative magnitudes
    		double newXMagFR = Robot.swerveDrive.calcRelMagX(0, -Math.signum(distance), curFRAngle);
    		double newYMagFR = Robot.swerveDrive.calcRelMagY(-Math.signum(distance), 0, curFRAngle);
    		double newXMagFL = Robot.swerveDrive.calcRelMagX(0, -Math.signum(distance), curFLAngle);
    		double newYMagFL = Robot.swerveDrive.calcRelMagY(-Math.signum(distance), 0, curFLAngle);
	    	double newXMagBR = Robot.swerveDrive.calcRelMagX(0, -Math.signum(distance), curBRAngle);
	    	double newYMagBR = Robot.swerveDrive.calcRelMagY(-Math.signum(distance), 0, curBRAngle);
	    	double newXMagBL = Robot.swerveDrive.calcRelMagX(0, -Math.signum(distance), curBLAngle);
	    	double newYMagBL = Robot.swerveDrive.calcRelMagY(-Math.signum(distance), 0, curBLAngle);
	    			
	    	//get position difference
	    	double positionDifFR = Robot.swerveDrive.getPositionDif(newXMagFR, newYMagFR);
	    	double positionDifFL = Robot.swerveDrive.getPositionDif(newXMagFL, newYMagFL);
	    	double positionDifBR = Robot.swerveDrive.getPositionDif(newXMagBR, newYMagBR);
	    	double positionDifBL = Robot.swerveDrive.getPositionDif(newXMagBL, newYMagBL);
	    	
    	
    		//set steering motor position
    		Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontRight, curFRPosition, positionDifFR, RobotMap.FRONTRIGHT_ERROR);
    		Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontLeft, curFLPosition, positionDifFL, RobotMap.FRONTLEFT_ERROR);
    		Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackRight, curBRPosition, positionDifBR, RobotMap.BACKRIGHT_ERROR);
    		Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackLeft, curBLPosition, positionDifBL, RobotMap.BACKLEFT_ERROR);
    	
		
		//set driving motor output
    		finalFR = positionDifFR + RobotMap.FRONTRIGHT_ERROR > -5 && positionDifFR + RobotMap.FRONTRIGHT_ERROR < 5;
			finalFL = positionDifFL + RobotMap.FRONTLEFT_ERROR > -5 && positionDifFL + RobotMap.FRONTLEFT_ERROR < 5;
			finalBR = positionDifBR + RobotMap.BACKRIGHT_ERROR > -5 && positionDifBR + RobotMap.BACKRIGHT_ERROR < 5;
			finalBL = positionDifBL + RobotMap.BACKLEFT_ERROR > -5 && positionDifBL + RobotMap.BACKLEFT_ERROR < 5;
		
    	}
		
		if (finalFR && finalFL && finalBR && finalBL)
		{
			SmartDashboard.putString("status", "final Pos done");
			SmartDashboard.putNumber("curDrivingPosiiton", SwerveDrive.drivingMotorBackRight.getEncPosition());
			SmartDashboard.putNumber("origPos", curDrivBRPosition);
			SmartDashboard.putNumber("drivingDistance", drivingDistance);
			SmartDashboard.putNumber("newPos", curDrivBRPosition + drivingDistance);
			
			isOrigPosDone = true;
			
	        SwerveDrive.drivingMotorBackRight.configPeakOutputVoltage(+3.5f, 0.0f);
			SwerveDrive.drivingMotorBackRight.changeControlMode(TalonControlMode.Position);
			SwerveDrive.drivingMotorBackRight.set(curDrivBRPosition + drivingDistance);
		
			
			double motorOutput = SwerveDrive.drivingMotorBackRight.getOutputVoltage() / 12;
			SmartDashboard.putNumber("motorOutput", motorOutput);

			
			SwerveDrive.drivingMotorFrontRight.set(-motorOutput);
			SwerveDrive.drivingMotorFrontLeft.set(motorOutput);
			SwerveDrive.drivingMotorBackLeft.set(motorOutput);
			
			if (SwerveDrive.drivingMotorBackRight.getEncPosition() < curDrivBRPosition + drivingDistance + 10) 
	    	{
				SmartDashboard.putString("status2", "driving motor position done");
				

				
				double newXMagFR = Robot.swerveDrive.calcRelMagX(.669 * distance, -.743 * distance, curFRAngle);
				double newYMagFR = Robot.swerveDrive.calcRelMagY(-.743 * distance, .669 * distance, curFRAngle);
				double newXMagFL = Robot.swerveDrive.calcRelMagX(-.669 * distance, -.743 * distance, curFLAngle);
				double newYMagFL = Robot.swerveDrive.calcRelMagY(-.743 * distance, -.669 * distance, curFLAngle);
				double newXMagBR = Robot.swerveDrive.calcRelMagX(-.669 * distance, -.743 * distance, curBRAngle);
				double newYMagBR = Robot.swerveDrive.calcRelMagY(-.743 * distance, -.669 * distance, curBRAngle);
				double newXMagBL = Robot.swerveDrive.calcRelMagX(.669 * distance, -.743 * distance, curBLAngle);
				double newYMagBL = Robot.swerveDrive.calcRelMagY(-.743 * distance, .669 * distance, curBLAngle);
				
				double newPositionDifFR = Robot.swerveDrive.getPositionDif(newXMagFR, newYMagFR);
		    	double newPositionDifFL = Robot.swerveDrive.getPositionDif(newXMagFL, newYMagFL);
		    	double newPositionDifBR = Robot.swerveDrive.getPositionDif(newXMagBR, newYMagBR);
		    	double newPositionDifBL = Robot.swerveDrive.getPositionDif(newXMagBL, newYMagBL);
				
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontRight, curFRPosition, newPositionDifFR, RobotMap.FRONTRIGHT_ERROR);
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontLeft, curFLPosition, newPositionDifFL, RobotMap.FRONTLEFT_ERROR);
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackRight, curBRPosition, newPositionDifBR, RobotMap.BACKRIGHT_ERROR);
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackLeft, curBLPosition, newPositionDifBL, RobotMap.BACKLEFT_ERROR);
	        	
				boolean endFR = newPositionDifFR + RobotMap.FRONTRIGHT_ERROR > -5 && newPositionDifFR + RobotMap.FRONTRIGHT_ERROR < 5;
				boolean endFL = newPositionDifFL + RobotMap.FRONTLEFT_ERROR > -5 && newPositionDifFL + RobotMap.FRONTLEFT_ERROR < 5;
				boolean endBR = newPositionDifBR + RobotMap.BACKRIGHT_ERROR > -5 && newPositionDifBR + RobotMap.BACKRIGHT_ERROR < 5;
				boolean endBL = newPositionDifBL + RobotMap.BACKLEFT_ERROR > -5 && newPositionDifBL + RobotMap.BACKLEFT_ERROR < 5;
				
				if (endFR && endFL && endBR && endBL)
				{
					SmartDashboard.putString("status", "ended");
					
					finished = true;	
				}
	        }
		}
		
		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	if (finished)
    	{
	        SwerveDrive.drivingMotorBackRight.configPeakOutputVoltage(+12f, 0.0f);
    		return true;
    	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        SwerveDrive.drivingMotorBackRight.configPeakOutputVoltage(+12.0f, 0.0f);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    }
}
