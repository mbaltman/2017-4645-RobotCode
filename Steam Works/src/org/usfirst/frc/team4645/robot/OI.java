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
	
	public static Joystick joy = new Joystick(1);
	
	 Button button1= new JoystickButton(joy, 1);
	 Button button2= new JoystickButton(joy, 2);
	 Button button3= new JoystickButton(joy, 3);
	 Button button4= new JoystickButton(joy, 4);
	 Button button5= new JoystickButton(joy, 5);
	 
	 Button button6= new JoystickButton(joy, 6);
	 Button button7= new JoystickButton(joy, 7);
	 Button button8= new JoystickButton(joy, 8);
	 Button button9= new JoystickButton(joy, 9);
	 Button button10= new JoystickButton(joy, 10);
	 
	 Button button11= new JoystickButton(joy, 11);
	 Button button12= new JoystickButton(joy, 12);
	 
	
	public OI()
	{
		
		button1.whileHeld(new TestShoot());
		button2.whileHeld(new IntakeCommand());
		
		
		button3.whileHeld(new ClimbCommand());
		
		
		button7.whenPressed(new HoldGearCommand());
		button8.whenPressed(new DropGearCommand());
		button9.whileHeld(new PushGearCommand());
	
	}

	// button.whileHeld(new ExampleCommand());

	
	// button.whenReleased(new ExampleCommand());
}
