package org.usfirst.frc.team4645.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team4645.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
	
	public static Joystick leftJoy = new Joystick(1);
	Button buttonShoot = new JoystickButton(leftJoy, 1);
	
	Button testShoot = new JoystickButton(leftJoy,3);
	Button testFeeder = new JoystickButton(leftJoy, 11);
	Button testGear = new JoystickButton(leftJoy, 10);
	
	Button buttonIntake = new JoystickButton(leftJoy, 4);
	Button buttonClimb = new JoystickButton(leftJoy, 5);
	
	
	
	public static Button resetGyro = new JoystickButton(leftJoy, 7);
	
	public OI()
	{
		
		resetGyro.whenPressed(new ResetGyro());

		
		//buttonLeftGear.whenPressed(new PlaceGearCommand(1,1));
	    //buttonMiddleGear.whenPressed(new PlaceGearCommand(0,1));
		//buttonRightGear.whenPressed(new PlaceGearCommand(-1,1));
		
		//buttonShoot.whenPressed(new CenterAndShootCommand());
		buttonIntake.whileHeld(new IntakeCommand());
		buttonIntake.whenReleased(new StopIntake());
		
		buttonClimb.whileHeld(new ClimbCommand());
		buttonClimb.whenReleased(new ClimbStop());
		
		testShoot.whileHeld(new TestShoot());
		testShoot.whenReleased(new TestStopShooter());
		testGear.whenPressed(new TestGear());
		
		
	
	}

	// button.whileHeld(new ExampleCommand());

	
	// button.whenReleased(new ExampleCommand());
}
