package org.usfirst.frc.team4645.robot;


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
	public static final int hangerMotorT =0;
	public static final int feederMotor = 6;
	public static final int driveBL = 4; 
	public static final int driveFL = 5; //now an sr
	public static final int intakeMotor = 7;
	//servos
	
	public static final int gearServoLeft = 2;
	public static final int gearServoRight = 1; //9

	
	
	//SRX IDs
	public static final int steerFR = 0;
	public static final int steerFL = 4;
	public static final int steerBL = 2;
	public static final int steerBR = 3;
	public static final int reservoirMotorPort = 1;
	public static final int driveBR = 5;
	
	
	public static final int shooterMotor = 6;
	
	
	//Steering errors
	
	
	public static final double FRONTRIGHT_ERROR = 114 ;;//132 //113 enforcer, 126 iron
	public static final double FRONTLEFT_ERROR = 33; //147 enforcer, -207 iron
	public static final double BACKRIGHT_ERROR = 23; //10//64 enforcer, -24 iron
	public static final double BACKLEFT_ERROR = -60; //-50//21 enforcer, -27 iron
	 
	
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
	public static final double GEAR_DISTANCEX = .18;
	public static final double GEAR_DISTANCEY = .20;
	
	
	//limit switch for gear
	public static final int GEAR_SWITCH_CHANNEL = 0;
	
	
}






