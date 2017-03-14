package org.usfirst.frc.team4645.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Servo;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap 
{
	
	
	//PWM Ports
	//motors
	public static final int driveFR = 9; //0
	public static final int hangerMotorB = 8;
	public static final int hangerMotorT =3;
	public static final int feederMotor = 6;
	public static final int driveBL = 4; 
	public static final int driveFL = 5; //now an sr
	public static final int intakeMotor = 7;
	//servos
	
	public static final int gearServoLeft = 2;
	public static final int gearServoRight = 1; //9
	public static final int climbingServo = 0; 
	
	
	//SRX IDs
	public static final int steerFR = 0;
	public static final int steerFL = 4;
	public static final int steerBL = 2;
	public static final int steerBR = 3;
	public static final int reservoirMotorPort = 1;
	public static final int driveBR = 5;
	
	
	public static final int shooterMotor = 6;
	
	
	//Steering errors
	
	
	public static final double FRONTRIGHT_ERROR = 126; //113 enforcer, 126 iron
	public static final double FRONTLEFT_ERROR = -24; //147 enforcer, -207 iron
	public static final double BACKRIGHT_ERROR = -24; //64 enforcer, -24 iron
	public static final double BACKLEFT_ERROR = -27; //21 enforcer, -27 iron
	 
	
	//Radius angles (degrees) and value (meters)
	public static final double FRONTRIGHT_RADANGLE = 48;
	public static final double FRONTLEFT_RADANGLE = 132;
	public static final double BACKRIGHT_RADANGLE = 312;
	public static final double BACKLEFT_RADANGLE = 228;
	
	public static final double RADIUS = 0.427;
	
	//dylan wanted changes
//	public static int gyroLock;
//	public static int zLock;
//	public static int xLock;
	
	//Shooter speeds, TBD
	public static final double slowSpeed = -475;
	public static final double fastSpeed = -600;
	
	//Shooter distances, TBD
	public static final double closeY = 2.3;
	public static final double farY = 3;
	
	//gear distance, TBD
	public static final double GEAR_DISTANCE = .20;
	
	
	
}







