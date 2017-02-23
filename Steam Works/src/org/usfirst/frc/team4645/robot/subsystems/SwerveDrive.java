package org.usfirst.frc.team4645.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Talon;

import org.usfirst.frc.team4645.robot.OI;
import org.usfirst.frc.team4645.robot.RobotMap;
import org.usfirst.frc.team4645.robot.commands.DefaultSwerve;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;


/**
 *
 */
public class SwerveDrive extends Subsystem 
{
	
	public static CANTalon steeringMotorFrontRight = new CANTalon(RobotMap.steerFR);
	public static CANTalon steeringMotorFrontLeft = new CANTalon(RobotMap.steerFL);
	public static CANTalon steeringMotorBackRight = new CANTalon(RobotMap.steerBR);
	public static CANTalon steeringMotorBackLeft = new CANTalon(RobotMap.steerBL);
	
	public static Talon drivingMotorFrontRight = new Talon(RobotMap.driveFR);
	public static CANTalon drivingMotorFrontLeft = new CANTalon(RobotMap.driveFL);
	public static CANTalon drivingMotorBackRight = new CANTalon(RobotMap.driveBR);
	public static Talon drivingMotorBackLeft = new Talon(RobotMap.driveBL);
	
	public static GyroBase gyro = new ADXRS450_Gyro();

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public double getZMag(double initialZMag)
	{
		if (initialZMag < 0.2 && initialZMag > -0.2) 
		{
			initialZMag = 0;
		}
		if (initialZMag < 0) 
		{
			initialZMag += 0.2;
		}
		else if (initialZMag > 0) 
		{
			initialZMag -= 0.2;
		}
		
		return initialZMag;
	}
	
	public double getPosition(CANTalon steeringMotor)
	{
		double position = steeringMotor.getAnalogInPosition();
		
		return position;
	}
	
	public double getAngle(double position) 
	{
		double angle = position % 1023;
		if (angle < 0) { angle += 1023; }
		angle *= ((2*Math.PI) / 1023);
		
		return angle;
	}
	
	public double getRotationAngle(double joystickZ, double finalRad) 
	{
		double angleZ = 90 * Math.signum(joystickZ);
		double rotAngle = angleZ + finalRad;
		rotAngle *= (Math.PI / 180);
		
		return rotAngle;
	}
	
	public double getRotCompX (double angle, double mag) 
	{
		double rotCompX = Math.cos(angle) * Math.abs(mag);
		
		return rotCompX;
	}
	
	public double getRotCompY (double angle, double mag)
	{
		double rotCompY = Math.sin(angle) * Math.abs(mag);
		
		return rotCompY;
	}
	
	public double getTotalComp(double rotComp, double linComp) 
	{
		return (rotComp + linComp);
	}
	
	public double calcMax(double totalFR, double totalFL, double totalBR, double totalBL) 
	{
		double max = totalFR;
		if (totalFL > max) {max = totalFL;}
		if (totalBR > max) {max = totalBR;}
		if (totalBL > max) {max = totalBL;}
		
		return max;
	}
	
	public double calcRelMagX (double xMag, double yMag, double angle) 
	{
		double posDif = ((xMag * Math.cos(angle)) + (yMag * Math.sin(angle)));
		
		return posDif;
	}
	
	public double calcRelMagY (double yMag, double xMag, double angle) 
	{
		double posDif = ((yMag * Math.cos(angle)) - (xMag * Math.sin(angle)));
		
		return posDif;
	}
	
	public double getPositionDif(double magX, double magY) 
	{
		double posDif = Math.atan2(magY, magX);
		posDif *= (1023/(2 * Math.PI));
		
		return posDif;
	}
	
	public void setSteeringPosition(CANTalon motor, double curPos, double posDif, double gyroPos, double error)
	{
		motor.changeControlMode(CANTalon.TalonControlMode.Position);
		motor.set(curPos + posDif + gyroPos + error);
		
	}
	
	
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DefaultSwerve());
    }
}

