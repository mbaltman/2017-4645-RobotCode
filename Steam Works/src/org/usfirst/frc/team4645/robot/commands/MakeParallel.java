package org.usfirst.frc.team4645.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4645.robot.OI;
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
	double curDrivFLPosition;
	double drivingDistance;
	boolean finished;
	boolean isOrigPosDone;


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
    	curDrivFLPosition = SwerveDrive.drivingMotorFrontLeft.getEncPosition();
    	isOrigPosDone = false;
    	finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    	//get gyro angle in radians
    	double gyroAngle = SwerveDrive.gyro.getAngle();
    	gyroAngle %= 360;
    	if (gyroAngle < 0) {gyroAngle += 360;}
    	
    	//possible fix for possible problem
    	//gyroAngle = 360 - gyroAngle;
    	
    	
    	gyroAngle *= Math.PI/180;
    	
    	//find desired components 
    	double xComp = Math.cos(desAngle);
    	double yComp = Math.sin(desAngle);
    	
    	//make them relative to the current angle of the robot
    	double newX = Robot.swerveDrive.calcRelMagX(xComp, yComp, gyroAngle);
    	double newY = Robot.swerveDrive.calcRelMagY(yComp, xComp, gyroAngle);
    	
    	//find the position to run the driving motors to
    	double angDif = Math.atan2(newY, newX);
    	drivingDistance = Math.abs(angDif * RobotMap.RADIUS);
    	drivingDistance *= 1670.84;
    	
    	//finding current angle of the steering motors
    	double curFRPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorFrontRight);
    	double curFLPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorFrontLeft);
    	double curBRPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorBackRight);
    	double curBLPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorBackLeft);
    	
    	double curFRAngle = Robot.swerveDrive.getAngle(curFRPosition);
    	double curFLAngle = Robot.swerveDrive.getAngle(curFLPosition);
    	double curBRAngle = Robot.swerveDrive.getAngle(curBRPosition);
    	double curBLAngle = Robot.swerveDrive.getAngle(curBLPosition);
    	
    	//find angle the steering motors should eventually be at
    	double rotAngFR = Robot.swerveDrive.getRotationAngle(angDif, RobotMap.FRONTRIGHT_RADANGLE);
    	double rotAngFL = Robot.swerveDrive.getRotationAngle(angDif, RobotMap.FRONTLEFT_RADANGLE);
    	double rotAngBR = Robot.swerveDrive.getRotationAngle(angDif, RobotMap.BACKRIGHT_RADANGLE);
    	double rotAngBL = Robot.swerveDrive.getRotationAngle(angDif, RobotMap.BACKLEFT_RADANGLE);
    	
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
		
		if (!isOrigPosDone)
		{
			//set steering motor position
			Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontRight, curFRPosition, positionDifFR, RobotMap.FRONTRIGHT_ERROR);
			Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontLeft, curFLPosition, positionDifFL, RobotMap.FRONTLEFT_ERROR);
			Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackRight, curBRPosition, positionDifBR, RobotMap.BACKRIGHT_ERROR);
			Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackLeft, curBLPosition, positionDifBL, RobotMap.BACKLEFT_ERROR);
		}
		
		//set driving motor output
		
		boolean finalFR = positionDifFR + RobotMap.FRONTRIGHT_ERROR > -3 && positionDifFR + RobotMap.FRONTRIGHT_ERROR < 3;
		boolean finalFL = positionDifFL + RobotMap.FRONTLEFT_ERROR > -3 && positionDifFL + RobotMap.FRONTLEFT_ERROR < 3;
		boolean finalBR = positionDifBR + RobotMap.BACKRIGHT_ERROR > -3 && positionDifBR + RobotMap.BACKRIGHT_ERROR < 3;
		boolean finalBL = positionDifBL + RobotMap.BACKLEFT_ERROR > -3 && positionDifBL + RobotMap.BACKLEFT_ERROR < 3;
		
		if (finalFR && finalFL && finalBR && finalBL) 
		{
			isOrigPosDone = true;
			
	        SwerveDrive.drivingMotorFrontLeft.configPeakOutputVoltage(+4.5f, 0.0f);
			SwerveDrive.drivingMotorFrontLeft.changeControlMode(TalonControlMode.Position);
			SwerveDrive.drivingMotorFrontLeft.set(curDrivFLPosition + drivingDistance);
			
			
			double motorOutput = SwerveDrive.drivingMotorFrontLeft.getOutputVoltage() / 12;
			
			SwerveDrive.drivingMotorFrontRight.set(-motorOutput);
			SwerveDrive.drivingMotorBackRight.set(motorOutput);
			SwerveDrive.drivingMotorBackLeft.set(motorOutput);
			
			if (SwerveDrive.drivingMotorFrontLeft.getEncPosition() > curDrivFLPosition + drivingDistance - 4) 
	    	{
				newXMagFR = Robot.swerveDrive.calcRelMagX(-1 * angDif, 0, curFRAngle);
				newYMagFR = Robot.swerveDrive.calcRelMagY(0, -1 * angDif, curFRAngle);
				newXMagFL = Robot.swerveDrive.calcRelMagX(0, 1 * angDif, curFLAngle);
				newYMagFL = Robot.swerveDrive.calcRelMagY(1 * angDif, 0, curFLAngle);
				newXMagBR = Robot.swerveDrive.calcRelMagX(0, -1 * angDif, curBRAngle);
				newYMagBR = Robot.swerveDrive.calcRelMagY(-1 * angDif, 0, curBRAngle);
				newXMagBL = Robot.swerveDrive.calcRelMagX(1 * angDif, 0, curBLAngle);
				newYMagBL = Robot.swerveDrive.calcRelMagY(0, 1 * angDif, curBLAngle);
				
				positionDifFR = Robot.swerveDrive.getPositionDif(newXMagFR, newYMagFR);
		    	positionDifFL = Robot.swerveDrive.getPositionDif(newXMagFL, newYMagFL);
		    	positionDifBR = Robot.swerveDrive.getPositionDif(newXMagBR, newYMagBR);
		    	positionDifBL = Robot.swerveDrive.getPositionDif(newXMagBL, newYMagBL);
				
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontRight, curFRPosition, positionDifFR, RobotMap.FRONTRIGHT_ERROR);
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontLeft, curFLPosition, positionDifFL, RobotMap.FRONTLEFT_ERROR);
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackRight, curBRPosition, positionDifBR, RobotMap.BACKRIGHT_ERROR);
				Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackLeft, curBLPosition, positionDifBL, RobotMap.BACKLEFT_ERROR);
	        	
				finished = true;
	        }
			
			
		}
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	if (finished)
    	{
	        SwerveDrive.drivingMotorFrontLeft.configPeakOutputVoltage(+12f, 0.0f);
    		return true;
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
