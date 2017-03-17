
package org.usfirst.frc.team4645.robot;

import edu.wpi.cscore.UsbCamera;




import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.command.Subsystem;


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
	public static final Shooter shooterSubsystem = new Shooter();
	
	//public PDPJNI pdp= new PDPJNI();
	
	
	public static OI oi;
	//Command Groups
	//Command CenterAndShootCommand;
    //PlaceGearCommand PlaceGearCommand;
	//Autonomous Commands
    CommandGroup AutonomousCommand;
    
    
    public static SendableChooser<String> allianceChooser = new SendableChooser<>();
    
    public static SendableChooser<String> positionChooser = new SendableChooser<>();
    
    //public static SendableChooser<Double> shooterChooser = new SendableChooser<>();
    
    //basic Commands
    /*
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
	*/
	
    int allianceConstant=1;
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
		UsbCamera cameraBoiler = CameraServer.getInstance().startAutomaticCapture(0);
    	
    	UsbCamera cameraGear = CameraServer.getInstance().startAutomaticCapture(1);
		oi = new OI();
		
		
		allianceChooser.addObject("Blue Alliance", "Blue");
		allianceChooser.addObject("Red Alliance", "Red");
	
		//for combined auto
		positionChooser.addObject("Boiler", "Boiler");
		positionChooser.addObject("Middle", "Middle");
		positionChooser.addObject("Loading Station", "Loading");
		positionChooser.addObject("Cross the Baseline","null");
		
		
		
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Choose Alliance", allianceChooser);
		SmartDashboard.putData("Choose Position", positionChooser);
		
		
		SwerveDrive.steeringMotorFrontRight.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
        SwerveDrive.steeringMotorFrontRight.configNominalOutputVoltage(+0.0f, -0.0f);
        SwerveDrive.steeringMotorFrontRight.configPeakOutputVoltage(+12.0f, -12.0f);
        SwerveDrive.steeringMotorFrontRight.setP(15);
        SwerveDrive.steeringMotorFrontRight.setD(150);
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
        SwerveDrive.steeringMotorBackLeft.setP(15);
        SwerveDrive.steeringMotorBackLeft.setD(150);
        SwerveDrive.steeringMotorBackLeft.setAllowableClosedLoopErr(2);
        
        SwerveDrive.drivingMotorBackRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        SwerveDrive.drivingMotorBackRight.configNominalOutputVoltage(+0.0f, -0.0f);
        SwerveDrive.drivingMotorBackRight.configPeakOutputVoltage(+12.0f, 0.0f);
        SwerveDrive.drivingMotorBackRight.setP(5);
        SwerveDrive.drivingMotorBackRight.setI(0);
        SwerveDrive.drivingMotorBackRight.setD(50);
        SwerveDrive.drivingMotorBackRight.reverseSensor(false);
        SwerveDrive.drivingMotorBackRight.reverseOutput(true);

        
        //SwerveDrive.drivingMotorBackRight.enableBrakeMode(true);
        /*
        SwerveDrive.drivingMotorBackRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        SwerveDrive.drivingMotorBackRight.configNominalOutputVoltage(+0.0f, -0.0f);
        SwerveDrive.drivingMotorBackRight.configPeakOutputVoltage(+12.0f, 0f);
        SwerveDrive.drivingMotorBackRight.setP(0);
        SwerveDrive.drivingMotorBackRight.setD(0);
        */
        SwerveDrive.gyro.calibrate();
        
        Shooter.shooterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        Shooter.shooterMotor.reverseSensor(false);
        //shooter.configEncoderCodesPerRev(80); // if using FeedbackDevice.QuadEncoder
        //shooter.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        Shooter.shooterMotor.configNominalOutputVoltage(+0.0f, -0.0f);
        Shooter.shooterMotor.configPeakOutputVoltage(12.0f, 0.0f);
        Shooter.shooterMotor.setF(0.0086); //1.557
        Shooter.shooterMotor.setP(0.03); //4.096
       // Shooter.shooterMotor.setI(0); 
        Shooter.shooterMotor.setD(0.3); //81.84
		
        
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() 
	{
		//SmartDashboard.putData("Choose Alliance", allianceChooser);
		//SmartDashboard.putData("Choose Position", positionChooser);
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
		
		
       AutonomousCommand = new Autonomous();
       
	
       
       
		if (AutonomousCommand != null)
		{
			AutonomousCommand.start();
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
		//pdp.clearPDPStickyFaults(0);
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (AutonomousCommand != null)
			AutonomousCommand.cancel();
		
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
		SmartDashboard.putNumber("curDrivingPosiiton", SwerveDrive.drivingMotorBackRight.getEncPosition());

		
   		
		//SmartDashboard.putNumber("Error", Shooter.shooterMotor.getClosedLoopError());
		
		/*
		SmartDashboard.putNumber("drivingFLPosition", SwerveDrive.drivingMotorBackRight.getPosition());
		SmartDashboard.putNumber("drivingBRPosition", SwerveDrive.drivingMotorBackLeft.getPosition());
		
		SmartDashboard.putNumber("curFLDrivePosition", MoveToX.curDrivFLPosition);
		SmartDashboard.putNumber("curBRDrivePosition", MoveToX.curDrivBRPosition);
		SmartDashboard.putNumber("drivingDistance", MoveToX.drivingDistance);
		*/
		
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
