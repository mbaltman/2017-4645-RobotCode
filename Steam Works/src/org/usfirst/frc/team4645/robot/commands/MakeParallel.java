package org.usfirst.frc.team4645.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4645.robot.Robot;
import org.usfirst.frc.team4645.robot.RobotMap;
import org.usfirst.frc.team4645.robot.subsystems.SwerveDrive;

import com.ctre.CANTalon.TalonControlMode;


/**
 *
 */
public class MakeParallel extends Command 
{
	
	double desAngle;
	double curDrivBRPosition;
	double angDif;
	double drivingDistance;
	boolean finished;
	boolean isOrigPosDone;
	
	boolean finalFR;
	boolean finalFL;
	boolean finalBR;
	boolean finalBL;


    public MakeParallel(double desAngle) 
    {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.swerveDrive);
    	this.desAngle = desAngle * (Math.PI / 180);
    	
    	
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	SmartDashboard.putString("status", "initializing");
    	
    	curDrivBRPosition = SwerveDrive.drivingMotorBackRight.getEncPosition();
    	isOrigPosDone = false;
    	finished = false;
    	
    	finalFR = false;
    	finalFL = false;
    	finalBR = false;
    	finalBL = false;
    	
    	//get gyro angle in radians
    	double gyroAngle = SwerveDrive.gyro.getAngle();
    	gyroAngle %= 360;
    	if (gyroAngle < 0) 
    	{
    		gyroAngle += 360;
    	}
    	
    	//possible fix for possible problem
    	gyroAngle = 360 - gyroAngle;
    	
    	
    	gyroAngle *= Math.PI/180;
    	
    	//find desired components 
    	double xComp = Math.cos(desAngle);
    	double yComp = Math.sin(desAngle);
    	
    	//make them relative to the current angle of the robot
    	double newX = Robot.swerveDrive.calcRelMagX(xComp, yComp, gyroAngle);
    	double newY = Robot.swerveDrive.calcRelMagY(yComp, xComp, gyroAngle);
    	
    	//find the position to run the driving motors to
    	angDif = Math.atan2(newY, newX);
    	drivingDistance = Math.abs(angDif * RobotMap.RADIUS);
    	drivingDistance *= -1670.84;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    		
    	//finding current angle of the steering motors
    	double curFRPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorFrontRight);
    	double curFLPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorFrontLeft);
    	double curBRPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorBackRight);
    	double curBLPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorBackLeft);
    	
    	double curFRAngle = Robot.swerveDrive.getAngle(curFRPosition);
    	double curFLAngle = Robot.swerveDrive.getAngle(curFLPosition);
    	double curBRAngle = Robot.swerveDrive.getAngle(curBRPosition);
    	double curBLAngle = Robot.swerveDrive.getAngle(curBLPosition);
    	
    	
    	if (!isOrigPosDone)
		{
    	
    		SmartDashboard.putString("status", "!isOrigPosDone");
			SmartDashboard.putString("status2", "!isOrigPosDone");
			
    		
	    	//find angle the steering motors should eventually be at
	    	double rotAngFR = Robot.swerveDrive.getRotationAngle(-angDif, RobotMap.FRONTRIGHT_RADANGLE);
	    	double rotAngFL = Robot.swerveDrive.getRotationAngle(-angDif, RobotMap.FRONTLEFT_RADANGLE);
	    	double rotAngBR = Robot.swerveDrive.getRotationAngle(-angDif, RobotMap.BACKRIGHT_RADANGLE);
	    	double rotAngBL = Robot.swerveDrive.getRotationAngle(-angDif, RobotMap.BACKLEFT_RADANGLE);
	    	
	    	//find their components for relativizationism
	    	double radXFR = Robot.swerveDrive.getRotCompX(rotAngFR, 1);
	    	double radYFR = Robot.swerveDrive.getRotCompY(rotAngFR, 1);
	    	double radXFL = Robot.swerveDrive.getRotCompX(rotAngFL, 1);
	    	double radYFL = Robot.swerveDrive.getRotCompY(rotAngFL, 1);
	    	double radXBR = Robot.swerveDrive.getRotCompX(rotAngBR, 1);
	    	double radYBR = Robot.swerveDrive.getRotCompY(rotAngBR, 1);
	    	double radXBL = Robot.swerveDrive.getRotCompX(rotAngBL, 1);
	    	double radYBL = Robot.swerveDrive.getRotCompY(rotAngBL, 1);
	    	
	    	//finding relative coordinates
	    	double newXMagFR = Robot.swerveDrive.calcRelMagX(radXFR, radYFR, curFRAngle);
			double newYMagFR = Robot.swerveDrive.calcRelMagY(radYFR, radXFR, curFRAngle);
			double newXMagFL = Robot.swerveDrive.calcRelMagX(radXFL, radYFL, curFLAngle);
			double newYMagFL = Robot.swerveDrive.calcRelMagY(radYFL, radXFL, curFLAngle);
			double newXMagBR = Robot.swerveDrive.calcRelMagX(radXBR, radYBR, curBRAngle);
			double newYMagBR = Robot.swerveDrive.calcRelMagY(radYBR, radXBR, curBRAngle);
			double newXMagBL = Robot.swerveDrive.calcRelMagX(radXBL, radYBL, curBLAngle);
			double newYMagBL = Robot.swerveDrive.calcRelMagY(radYBL, radXBL, curBLAngle);
	    	
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
		
			finalFR = positionDifFR + RobotMap.FRONTRIGHT_ERROR > -7 && positionDifFR + RobotMap.FRONTRIGHT_ERROR < 7;
			finalFL = positionDifFL + RobotMap.FRONTLEFT_ERROR > -7 && positionDifFL + RobotMap.FRONTLEFT_ERROR < 7;
			finalBR = positionDifBR + RobotMap.BACKRIGHT_ERROR > -7 && positionDifBR + RobotMap.BACKRIGHT_ERROR < 7;
			finalBL = positionDifBL + RobotMap.BACKLEFT_ERROR > -7 && positionDifBL + RobotMap.BACKLEFT_ERROR < 7;
		
		}
		
		if (finalFR && finalFL && finalBR && finalBL) 
		{
			SmartDashboard.putString("status", "final Pos done");
			SmartDashboard.putNumber("curDrivingPosiiton", SwerveDrive.drivingMotorBackRight.getEncPosition());

			
			isOrigPosDone = true;
			
	        SwerveDrive.drivingMotorBackRight.configPeakOutputVoltage(+2.5f, 0.0f);
			SwerveDrive.drivingMotorBackRight.changeControlMode(TalonControlMode.Position);
			SwerveDrive.drivingMotorBackRight.set(curDrivBRPosition + drivingDistance);
			
			
			double motorOutput = SwerveDrive.drivingMotorBackRight.getOutputVoltage() / 12;
			
			SwerveDrive.drivingMotorFrontRight.set(motorOutput);
			SwerveDrive.drivingMotorFrontLeft.set(motorOutput);
			SwerveDrive.drivingMotorBackLeft.set(motorOutput);
			
			if (SwerveDrive.drivingMotorBackRight.getEncPosition() < curDrivBRPosition + drivingDistance + 7) 
	    	{
				SmartDashboard.putString("status2", "driving motor position done");
				
				
				double newXMagFR = Robot.swerveDrive.calcRelMagX(Math.signum(angDif), 0, curFRAngle);
				double newYMagFR = Robot.swerveDrive.calcRelMagY(0, Math.signum(angDif), curFRAngle);
				double newXMagFL = Robot.swerveDrive.calcRelMagX(0, -Math.signum(angDif), curFLAngle);
				double newYMagFL = Robot.swerveDrive.calcRelMagY(-Math.signum(angDif), 0, curFLAngle);
				double newXMagBR = Robot.swerveDrive.calcRelMagX(0, Math.signum(angDif), curBRAngle);
				double newYMagBR = Robot.swerveDrive.calcRelMagY(Math.signum(angDif), 0, curBRAngle);
				double newXMagBL = Robot.swerveDrive.calcRelMagX(-Math.signum(angDif), 0, curBLAngle);
				double newYMagBL = Robot.swerveDrive.calcRelMagY(0, -Math.signum(angDif), curBLAngle);
				
				double newPositionDifFR = Robot.swerveDrive.getPositionDif(newXMagFR, newYMagFR);
		    	double newPositionDifFL = Robot.swerveDrive.getPositionDif(newXMagFL, newYMagFL);
		    	double newPositionDifBR = Robot.swerveDrive.getPositionDif(newXMagBR, newYMagBR);
		    	double newPositionDifBL = Robot.swerveDrive.getPositionDif(newXMagBL, newYMagBL);
				
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontRight, curFRPosition, newPositionDifFR, RobotMap.FRONTRIGHT_ERROR);
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontLeft, curFLPosition, newPositionDifFL, RobotMap.FRONTLEFT_ERROR);
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackRight, curBRPosition, newPositionDifBR, RobotMap.BACKRIGHT_ERROR);
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackLeft, curBLPosition, newPositionDifBL, RobotMap.BACKLEFT_ERROR);
	        	
				boolean endFR = newPositionDifFR + RobotMap.FRONTRIGHT_ERROR > -7 && newPositionDifFR + RobotMap.FRONTRIGHT_ERROR < 7;
				boolean endFL = newPositionDifFL + RobotMap.FRONTLEFT_ERROR > -7 && newPositionDifFL + RobotMap.FRONTLEFT_ERROR < 7;
				boolean endBR = newPositionDifBR + RobotMap.BACKRIGHT_ERROR > -7 && newPositionDifBR + RobotMap.BACKRIGHT_ERROR < 7;
				boolean endBL = newPositionDifBL + RobotMap.BACKLEFT_ERROR > -7 && newPositionDifBL + RobotMap.BACKLEFT_ERROR < 7;
				
				if (endFR && endFL && endBR && endBL)
				{
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
    		return true;
    	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        SwerveDrive.drivingMotorBackRight.configPeakOutputVoltage(+12f, 0.0f);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    }
}
