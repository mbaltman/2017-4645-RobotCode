package org.usfirst.frc.team4645.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4645.robot.OI;
import org.usfirst.frc.team4645.robot.subsystems.SwerveDrive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team4645.robot.Robot;
import org.usfirst.frc.team4645.robot.RobotMap;

/**
 *
 */
public class DefaultSwerve extends Command
{

    public DefaultSwerve() 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.swerveDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	//get joystick mags
    	
    	//dylan wanted changes (flipped forward)
    	double tempXMag = OI.joy.getX() ;
    	double tempYMag = -OI.joy.getY();
    	double tempZMag = Robot.swerveDrive.getZMag(OI.joy.getZ());
    	
    	SmartDashboard.putNumber("tempXMag", tempXMag);
    	SmartDashboard.putNumber("tempYMag", tempYMag);
	
    	//get gyro position
		double gyroAngle = SwerveDrive.gyro.getAngle(); //new code
		SmartDashboard.putNumber("gyroAngle", gyroAngle);
		
    	double gyroPosition = gyroAngle * (1023.0/360.0); //2.8444
    	SmartDashboard.putNumber("gyroPosition", gyroPosition);
    	
    	//get wheel positions
    	
    	
    	double curFRPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorFrontRight);
    	double curFLPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorFrontLeft);
    	double curBRPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorBackRight);
    	double curBLPosition = Robot.swerveDrive.getPosition(SwerveDrive.steeringMotorBackLeft);
    	
    	
    	SmartDashboard.putNumber("curFRPosition", curFRPosition);
    	SmartDashboard.putNumber("curFLPosition", curFLPosition);
    	SmartDashboard.putNumber("curBRPosition", curBRPosition);
    	SmartDashboard.putNumber("curBLPosition", curBLPosition);
    	
    	
    	//get wheel angles
    	
    	/*
    	double curFRAngle = Robot.swerveDrive.getAngle(curFRPosition);
    	double curFLAngle = Robot.swerveDrive.getAngle(curFLPosition);
    	double curBRAngle = Robot.swerveDrive.getAngle(curBRPosition);
    	double curBLAngle = Robot.swerveDrive.getAngle(curBLPosition);
    	*/
    	          //new code
    	double curFRAngle = Robot.swerveDrive.getAngle(curFRPosition - gyroPosition);
    	double curFLAngle = Robot.swerveDrive.getAngle(curFLPosition - gyroPosition);
    	double curBRAngle = Robot.swerveDrive.getAngle(curBRPosition - gyroPosition);
    	double curBLAngle = Robot.swerveDrive.getAngle(curBLPosition - gyroPosition);
    	
    	
    	//get rotation angles
    	
    	/*
    	double rotFRAngle = Robot.swerveDrive.getRotationAngle(tempZMag, RobotMap.FRONTRIGHT_RADANGLE);
    	double rotFLAngle = Robot.swerveDrive.getRotationAngle(tempZMag, RobotMap.FRONTLEFT_RADANGLE);
    	double rotBRAngle = Robot.swerveDrive.getRotationAngle(tempZMag, RobotMap.BACKRIGHT_RADANGLE);
    	double rotBLAngle = Robot.swerveDrive.getRotationAngle(tempZMag, RobotMap.BACKLEFT_RADANGLE);
    	*/
    	
    	
    	           //new code
    	double rotFRAngle = Robot.swerveDrive.getRotationAngleGyro(tempZMag, RobotMap.FRONTRIGHT_RADANGLE, gyroAngle);
    	double rotFLAngle = Robot.swerveDrive.getRotationAngleGyro(tempZMag, RobotMap.FRONTLEFT_RADANGLE, gyroAngle);
    	double rotBRAngle = Robot.swerveDrive.getRotationAngleGyro(tempZMag, RobotMap.BACKRIGHT_RADANGLE, gyroAngle);
    	double rotBLAngle = Robot.swerveDrive.getRotationAngleGyro(tempZMag, RobotMap.BACKLEFT_RADANGLE, gyroAngle);
    	
    	
    	SmartDashboard.putNumber("rotFRAngle", rotFRAngle * 180 / Math.PI);
    	
    	
    	
    	//get rotation components
    	double radXFR = Robot.swerveDrive.getRotCompX(rotFRAngle, tempZMag);
    	double radYFR = Robot.swerveDrive.getRotCompY(rotFRAngle, tempZMag);
    	double radXFL = Robot.swerveDrive.getRotCompX(rotFLAngle, tempZMag);
    	double radYFL = Robot.swerveDrive.getRotCompY(rotFLAngle, tempZMag);
    	double radXBR = Robot.swerveDrive.getRotCompX(rotBRAngle, tempZMag);
    	double radYBR = Robot.swerveDrive.getRotCompY(rotBRAngle, tempZMag);
    	double radXBL = Robot.swerveDrive.getRotCompX(rotBLAngle, tempZMag);
    	double radYBL = Robot.swerveDrive.getRotCompY(rotBLAngle, tempZMag);
		
		//calc total components
    	double totalXFR = Robot.swerveDrive.getTotalComp(radXFR, tempXMag);
    	double totalYFR = Robot.swerveDrive.getTotalComp(radYFR, tempYMag);
    	double totalXFL = Robot.swerveDrive.getTotalComp(radXFL, tempXMag);
    	double totalYFL = Robot.swerveDrive.getTotalComp(radYFL, tempYMag);
    	double totalXBR = Robot.swerveDrive.getTotalComp(radXBR, tempXMag);
    	double totalYBR = Robot.swerveDrive.getTotalComp(radYBR, tempYMag);
    	double totalXBL = Robot.swerveDrive.getTotalComp(radXBL, tempXMag);
    	double totalYBL = Robot.swerveDrive.getTotalComp(radYBL, tempYMag);
		
		
		//calc total mags and max
		double totalFR = Math.sqrt(Math.pow(totalXFR, 2) + Math.pow(totalYFR, 2));
		double totalFL = Math.sqrt(Math.pow(totalXFL, 2) + Math.pow(totalYFL, 2));
		double totalBR = Math.sqrt(Math.pow(totalXBR, 2) + Math.pow(totalYBR, 2));
		double totalBL = Math.sqrt(Math.pow(totalXBL, 2) + Math.pow(totalYBL, 2));
		
		double max = Robot.swerveDrive.calcMax(totalFR, totalFL, totalBR, totalBL);
		
		if (max > 1)
		{
			totalFR /= max;
			totalFL /= max;
			totalBR /= max;
			totalBL /= max;
		}
		
		
		
		//calc relative magnitudes
		double newXMagFR = Robot.swerveDrive.calcRelMagX(totalXFR, totalYFR, curFRAngle);
		double newYMagFR = Robot.swerveDrive.calcRelMagY(totalYFR, totalXFR, curFRAngle);
		double newXMagFL = Robot.swerveDrive.calcRelMagX(totalXFL, totalYFL, curFLAngle);
		double newYMagFL = Robot.swerveDrive.calcRelMagY(totalYFL, totalXFL, curFLAngle);
		double newXMagBR = Robot.swerveDrive.calcRelMagX(totalXBR, totalYBR, curBRAngle);
		double newYMagBR = Robot.swerveDrive.calcRelMagY(totalYBR, totalXBR, curBRAngle);
		double newXMagBL = Robot.swerveDrive.calcRelMagX(totalXBL, totalYBL, curBLAngle);
		double newYMagBL = Robot.swerveDrive.calcRelMagY(totalYBL, totalXBL, curBLAngle);
		
		//get position difference
		double positionDifFR = Robot.swerveDrive.getPositionDif(newXMagFR, newYMagFR);
		double positionDifFL = Robot.swerveDrive.getPositionDif(newXMagFL, newYMagFL);
		double positionDifBR = Robot.swerveDrive.getPositionDif(newXMagBR, newYMagBR);
		double positionDifBL = Robot.swerveDrive.getPositionDif(newXMagBL, newYMagBL);
		
		//set motor output
		if (max > 0.15) 
		{
			
			Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontRight, curFRPosition, positionDifFR, RobotMap.FRONTRIGHT_ERROR);
			Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorFrontLeft, curFLPosition, positionDifFL, RobotMap.FRONTLEFT_ERROR);
			Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackRight, curBRPosition, positionDifBR, RobotMap.BACKRIGHT_ERROR);
			Robot.swerveDrive.setSteeringPosition(SwerveDrive.steeringMotorBackLeft, curBLPosition, positionDifBL, RobotMap.BACKLEFT_ERROR);
			
			//dylan wanted changes
//			double tempThrottle = (OI.joy.getThrottle() + 1.0) / 2.0;
//			
//			totalFR *= tempThrottle;
//			totalFL *= tempThrottle;
//			totalBR *= tempThrottle;
//			totalBL *= tempThrottle;
			
			SwerveDrive.drivingMotorFrontRight.set(-totalFR);
			SwerveDrive.drivingMotorFrontLeft.set(totalFL);
			SwerveDrive.drivingMotorBackRight.set(totalBR);
			SwerveDrive.drivingMotorBackLeft.set(totalBL);
			
			
			
			
		}
		else 
		{
			SwerveDrive.drivingMotorFrontRight.set(0);
			SwerveDrive.drivingMotorFrontLeft.set(0);
			SwerveDrive.drivingMotorBackRight.set(0);
			SwerveDrive.drivingMotorBackLeft.set(0);
		}
		
		SmartDashboard.putString("swerve status", "execute");
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    	SmartDashboard.putString("swerve status", "end");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	SwerveDrive.drivingMotorFrontRight.set(0);
    	SwerveDrive.drivingMotorFrontLeft.set(0);
    	SwerveDrive.drivingMotorBackRight.set(0);
    	SwerveDrive.drivingMotorBackLeft.set(0);
    	SmartDashboard.putString("swerve status", "interrupted");
    	
    }
    
    
    
    
    
    
    
    
    
    
    
}
