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
	
	//Button testMakeParallel = new JoystickButton(leftJoy,3);
	Button testMoveToX = new JoystickButton(leftJoy, 4);
	
	//Button testMoveToY = new JoystickButton(leftJoy, 5);
	Button buttonGear= new JoystickButton(leftJoy,6);
	Button buttonIntake = new JoystickButton(leftJoy, 5);
	Button buttonClimb = new JoystickButton(leftJoy, 7);
	Button spinIn = new JoystickButton(leftJoy,10);
	Button spinOut = new JoystickButton(leftJoy,11);
	
	
	
	
	public static Button resetGyro = new JoystickButton(leftJoy, 7);
	
	public OI()
	{
		
		resetGyro.whenPressed(new ResetGyro());

		
		//buttonLeftGear.whenPressed(new PlaceGearCommand(1,1));
	    //buttonMiddleGear.whenPressed(new PlaceGearCommand(0,1));
		//buttonRightGear.whenPressed(new PlaceGearCommand(-1,1));
		
		buttonShoot.whileHeld(new ReservoirAlternate());
		
		
		//buttonIntake.whileHeld(new IntakeCommand());
		buttonClimb.whileHeld(new ClimbCommand());
		//testMakeParallel.whenPressed(new MakeParallel(45));
		testMoveToX.whenPressed(new MoveToX(-1));
		buttonIntake.whileHeld(new IntakeCommand());
		buttonGear.whenPressed(new TestGear());
		
		
		//testMoveToY.whenPressed(new MoveToY(1));
		
		
	
	}

	// button.whileHeld(new ExampleCommand());

	
	// button.whenReleased(new ExampleCommand());
}
