
package org.usfirst.frc.team4645.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4645.robot.commands.*;
import org.usfirst.frc.team4645.robot.subsystems.*;

import com.ctre.CANTalon.FeedbackDevice;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{

	public static final SwerveDrive swerveDrive = new SwerveDrive();
	public static final Intake intakeSubsystem = new Intake();
	public static final Reservoir reservoirSubsystem = new Reservoir();
	public static final Climbing climberSubsystem = new Climbing();
	
	
	public static final Gears gearSubsystem = new Gears();
	public static final Vision visionSubsystem = new Vision();
	public static final Shooter shooterSubsystem = new Shooter();
	
	public static OI oi;
	//Command Groups
	Command CenterAndShootCommand;
    Command PlaceGearCommand;
	//Autonomous Commands
    Command autonomousCommand;
    
    
    public static SendableChooser<String> allianceChooser = new SendableChooser<>();
    
    public static SendableChooser<String> positionChooser = new SendableChooser<>();
    
    public static SendableChooser<Double> shooterChooser = new SendableChooser<>();
    
    //basic Commands
    Command ClimbCommand;
    Command DefaultSwerve;
	Command DropGearCommand;
	Command IntakeCommand;
	Command MakeParallel;
	Command MoveToX;
	Command MoveToY;
	Command PushGearCommand;
	Command ResetGyro;
	Command ShootCommand;
	Command testValuesVision;
	
    public static int allianceConstant=1;
	public String allianceColor= null;
	
	public String shooterPosition=  null;
	double shooterSpeed;
	
	public static boolean auto;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() 
	{
		oi = new OI();
		
		
		
		allianceChooser.addDefault("Practice Alliance:RED", "Red");
		allianceChooser.addObject("Blue Alliance", "Blue");
		allianceChooser.addObject("Red Alliance", "Red");
		
		
		//for combined auto
		positionChooser.addDefault("BoilerDef", "Boiler");
		positionChooser.addObject("Boiler", "Boiler");
		positionChooser.addObject("Middle", "Middle");
		positionChooser.addObject("Loading Station", "Loading");
		
		shooterChooser.addDefault("Close", RobotMap.slowSpeed);
		shooterChooser.addObject("Close", RobotMap.slowSpeed);
		shooterChooser.addObject("Far", RobotMap.fastSpeed);
		
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Choose Alliance", allianceChooser);
		SmartDashboard.putData("Choose the Shooter Distance", shooterChooser);
		
		SwerveDrive.steeringMotorFrontRight.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
        SwerveDrive.steeringMotorFrontRight.configNominalOutputVoltage(+0.0f, -0.0f);
        SwerveDrive.steeringMotorFrontRight.configPeakOutputVoltage(+12.0f, -12.0f);
        SwerveDrive.steeringMotorFrontRight.setP(25);
        SwerveDrive.steeringMotorFrontRight.setD(250);
        SwerveDrive.steeringMotorFrontRight.setAllowableClosedLoopErr(2);
        
        SwerveDrive.steeringMotorFrontLeft.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
        SwerveDrive.steeringMotorFrontLeft.configNominalOutputVoltage(+0.0f, -0.0f);
        SwerveDrive.steeringMotorFrontLeft.configPeakOutputVoltage(+12.0f, -12.0f);
        SwerveDrive.steeringMotorFrontLeft.setP(25);
        SwerveDrive.steeringMotorFrontLeft.setD(250);
        SwerveDrive.steeringMotorFrontLeft.setAllowableClosedLoopErr(2);
        
        SwerveDrive.steeringMotorBackRight.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
        SwerveDrive.steeringMotorBackRight.configNominalOutputVoltage(+0.0f, -0.0f);
        SwerveDrive.steeringMotorBackRight.configPeakOutputVoltage(+12.0f, -12.0f);
        SwerveDrive.steeringMotorBackRight.setP(25);
        SwerveDrive.steeringMotorBackRight.setD(250);
        SwerveDrive.steeringMotorBackRight.setAllowableClosedLoopErr(2);
        
        SwerveDrive.steeringMotorBackLeft.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
        SwerveDrive.steeringMotorBackLeft.configNominalOutputVoltage(+0.0f, -0.0f);
        SwerveDrive.steeringMotorBackLeft.configPeakOutputVoltage(+12.0f, -12.0f);
        SwerveDrive.steeringMotorBackLeft.setP(25);
        SwerveDrive.steeringMotorBackLeft.setD(250);
        SwerveDrive.steeringMotorBackLeft.setAllowableClosedLoopErr(2);
        
        SwerveDrive.drivingMotorFrontLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        SwerveDrive.drivingMotorFrontLeft.configNominalOutputVoltage(+0.0f, -0.0f);
        SwerveDrive.drivingMotorFrontLeft.configPeakOutputVoltage(+12.0f, 0f);
        SwerveDrive.drivingMotorFrontLeft.setP(0);
        SwerveDrive.drivingMotorFrontLeft.setD(0);
        
        SwerveDrive.drivingMotorBackRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        SwerveDrive.drivingMotorBackRight.configNominalOutputVoltage(+0.0f, -0.0f);
        SwerveDrive.drivingMotorBackRight.configPeakOutputVoltage(+12.0f, 0f);
        SwerveDrive.drivingMotorBackRight.setP(0);
        SwerveDrive.drivingMotorBackRight.setD(0);
        
        SwerveDrive.gyro.calibrate();
        
        Shooter.shooterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        Shooter.shooterMotor.reverseSensor(true);
        //shooter.configEncoderCodesPerRev(80); // if using FeedbackDevice.QuadEncoder
        //shooter.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        Shooter.shooterMotor.configNominalOutputVoltage(+0.0f, -0.0f);
        Shooter.shooterMotor.configPeakOutputVoltage(0.0f, -12.0f);
        Shooter.shooterMotor.setF(1.557);
        Shooter.shooterMotor.setP(4.096);
       // Shooter.shooterMotor.setI(0); 
        Shooter.shooterMotor.setD(81.84);
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() 
	{
		 
	}

	@Override
	public void disabledPeriodic() 
	{
		
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		
       autonomousCommand = new Autonomous();
       
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
		{
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit()
	{
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		auto = false;
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() 
	{
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Shooter speed", Shooter.shooterMotor.getEncVelocity());
		
		SmartDashboard.putNumber("Error", Shooter.shooterMotor.getClosedLoopError());
		
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() 
	{
		LiveWindow.run();
	}
	
	
	
}
