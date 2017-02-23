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
	double curPositionFL;
	double curPositionBR;
	double drivingDistance;

    public MakeParallel(double desAngle) 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.swerveDrive);
    	this.desAngle = desAngle * (Math.PI / 180);
    	
    	curPositionFL = SwerveDrive.drivingMotorFrontLeft.getEncPosition();
    	curPositionBR = SwerveDrive.drivingMotorBackRight.getEncPosition();
    	
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    	//get gyro angle in radians
    	double gyroAngle = SwerveDrive.gyro.getAngle();
    	gyroAngle %= 360;
    	if (gyroAngle < 0) {gyroAngle += 360;}
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
			SwerveDrive.drivingMotorFrontLeft.set(curPositionFL + drivingDistance);
			SwerveDrive.drivingMotorBackRight.changeControlMode(TalonControlMode.Position);
			SwerveDrive.drivingMotorBackRight.set(curPositionBR + drivingDistance);
			
			double motorOutput = (SwerveDrive.drivingMotorFrontLeft.getOutputVoltage() * 100) / 12;
			SwerveDrive.drivingMotorFrontRight.set(motorOutput);
			SwerveDrive.drivingMotorBackLeft.set(motorOutput);
			
		}
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        if (SwerveDrive.drivingMotorFrontLeft.getEncPosition() < curPositionFL + drivingDistance + 2 
        		&& SwerveDrive.drivingMotorFrontLeft.getEncPosition() > curPositionFL + drivingDistance - 2)
			{
        	if (SwerveDrive.drivingMotorBackRight.getEncPosition() < curPositionBR + drivingDistance + 2
        			&& SwerveDrive.drivingMotorBackRight.getEncPosition() > curPositionBR + drivingDistance -2)
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
