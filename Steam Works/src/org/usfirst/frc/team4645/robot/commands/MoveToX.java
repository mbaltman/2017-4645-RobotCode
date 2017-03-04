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
public class MoveToX extends Command 
{

	double distance;
	public static double drivingDistance;
	
	public static double curDrivFLPosition;
	public static double curDrivBRPosition;
	
    public MoveToX(double distance) 
    {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerveDrive);
        this.distance = distance;
        
        
        
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	//set distance to position
    	drivingDistance = Math.abs(distance) * 1670.84;
    	
    	curDrivFLPosition = SwerveDrive.drivingMotorFrontLeft.getEncPosition();
    	curDrivBRPosition = SwerveDrive.drivingMotorBackRight.getEncPosition();
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
    	double newXMagFR = Robot.swerveDrive.calcRelMagX(-Math.signum(distance), 0, curFRAngle);
   		double newYMagFR = Robot.swerveDrive.calcRelMagY(0, -Math.signum(distance), curFRAngle);
   		double newXMagFL = Robot.swerveDrive.calcRelMagX(-Math.signum(distance), 0, curFLAngle);
    	double newYMagFL = Robot.swerveDrive.calcRelMagY(0, -Math.signum(distance), curFLAngle);
    	double newXMagBR = Robot.swerveDrive.calcRelMagX(-Math.signum(distance), 0, curBRAngle);
    	double newYMagBR = Robot.swerveDrive.calcRelMagY(0, -Math.signum(distance), curBRAngle);
    	double newXMagBL = Robot.swerveDrive.calcRelMagX(-Math.signum(distance), 0, curBLAngle);
    	double newYMagBL = Robot.swerveDrive.calcRelMagY(0, -Math.signum(distance), curBLAngle);
    			
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
		boolean finalFR = positionDifFR + RobotMap.FRONTRIGHT_ERROR > -3 && positionDifFR + RobotMap.FRONTRIGHT_ERROR < 3;
		boolean finalFL = positionDifFL + RobotMap.FRONTLEFT_ERROR > -3 && positionDifFL + RobotMap.FRONTLEFT_ERROR < 3;
		boolean finalBR = positionDifBR + RobotMap.BACKRIGHT_ERROR > -3 && positionDifBR + RobotMap.BACKRIGHT_ERROR < 3;
		boolean finalBL = positionDifBL + RobotMap.BACKLEFT_ERROR > -3 && positionDifBL + RobotMap.BACKLEFT_ERROR < 3;
		
		SmartDashboard.putBoolean("finalFR", finalFR);
		SmartDashboard.putBoolean("finalFL", finalFL);
		SmartDashboard.putBoolean("finalBR", finalBR);
		SmartDashboard.putBoolean("finalBL", finalBL);
		
		SmartDashboard.putNumber("curFRPosition", curFRPosition);
    	SmartDashboard.putNumber("curFLPosition", curFLPosition);
    	SmartDashboard.putNumber("curBRPosition", curBRPosition);
    	SmartDashboard.putNumber("curBLPosition", curBLPosition);
    	
    	
		
		if (finalFR && finalFL && finalBR && finalBL) 
		{
	        SwerveDrive.drivingMotorFrontLeft.configPeakOutputVoltage(+2.0f, 0.0f);
			SwerveDrive.drivingMotorFrontLeft.changeControlMode(TalonControlMode.Position);
			SwerveDrive.drivingMotorFrontLeft.set(curDrivFLPosition + drivingDistance);
			
			//SwerveDrive.drivingMotorBackRight.changeControlMode(TalonControlMode.Position);
			//SwerveDrive.drivingMotorBackRight.set(curDrivBRPosition + drivingDistance);

			
			
			double motorOutput = SwerveDrive.drivingMotorFrontLeft.getOutputVoltage() / 12.0;
			SmartDashboard.putNumber("motorOutput", motorOutput);
			SwerveDrive.drivingMotorFrontRight.set(-motorOutput);
			SwerveDrive.drivingMotorBackLeft.set(motorOutput);
			SwerveDrive.drivingMotorBackRight.set(motorOutput);
		}
		
		
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	if (//SwerveDrive.drivingMotorFrontLeft.getEncPosition() < curDrivFLPosition + drivingDistance + 4
        		 SwerveDrive.drivingMotorFrontLeft.getEncPosition() > curDrivFLPosition + drivingDistance - 4) 
    	{
        	// (//SwerveDrive.drivingMotorBackRight.getEncPosition() < curDrivBRPosition + drivingDistance + 4
        			// SwerveDrive.drivingMotorBackRight.getEncPosition() > curDrivBRPosition + drivingDistance -4)
        	//{
        		SmartDashboard.putString("isFinished", "yes");
        		return true;
        	//}
        }
        
    	SmartDashboard.putString("isFinished", "no");
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        SwerveDrive.drivingMotorFrontLeft.configPeakOutputVoltage(+12.0f, 0.0f);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    }
}
