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
	public static final int driveFR = 0;
	public static final int hangerMotorB = 1;
	public static final int hangerMotorT = 2;
	public static final int feederMotor = 3;
	public static final int driveBL = 5;
	public static final int intakeMotor = 6;
	//servos
	public static final int reservoirServo = 7;
	public static final int gearServoDrop =8;
	public static final int gearServoPush =9;
	
	
	//SRX IDs
	public static final int steerFR = 0;
	public static final int steerFL = 1;
	public static final int steerBR = 3;
	public static final int steerBL = 2;
	public static final int driveFL = 4;
	public static final int driveBR = 5;
	public static final int shooterMotor = 6;
	
	
	//Steering errors
	/*
	public static final double FRONTRIGHT_ERROR = 124; //113 enforcer, 124 iron
	public static final double FRONTLEFT_ERROR = -345; //147 enforcer, -345
	public static final double BACKRIGHT_ERROR = -49; //64 enforcer, -319
	public static final double BACKLEFT_ERROR = -319; //21 enforcer, -49
	*/
	
	public static final double FRONTRIGHT_ERROR = 113; //113 enforcer, 124 iron
	public static final double FRONTLEFT_ERROR = 147; //147 enforcer, -345
	public static final double BACKRIGHT_ERROR = 64; //64 enforcer, -319
	public static final double BACKLEFT_ERROR = 21; //21 enforcer, -49
	 
	
	//Radius angles (degrees) and value (meters)
	public static final double FRONTRIGHT_RADANGLE = 48;
	public static final double FRONTLEFT_RADANGLE = 132;
	public static final double BACKRIGHT_RADANGLE = 312;
	public static final double BACKLEFT_RADANGLE = 228;
	
	public static final double RADIUS = 0.427;
	
	//Shooter speeds, TBD
	public static final double slowSpeed = -475;
	public static final double fastSpeed = -600;
	
	//Shooter distances, TBD
	public static final double closeY = 2.3;
	public static final double farY = 3;
	
	//gear distance, TBD
	public static final double GEAR_DISTANCEY = .20;
	
	
	
}







