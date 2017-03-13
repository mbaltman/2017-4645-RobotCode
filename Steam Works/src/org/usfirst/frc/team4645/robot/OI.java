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
	
	 Button button1_1= new JoystickButton(joy, 1);
	 Button button1_2= new JoystickButton(joy, 2);
	 Button button1_3= new JoystickButton(joy, 3);
	 Button button1_4= new JoystickButton(joy, 4);
	 Button button1_5= new JoystickButton(joy, 5);
	 
	 Button button1_6= new JoystickButton(joy, 6);
	 Button button1_7= new JoystickButton(joy, 7);
	 Button button1_8= new JoystickButton(joy, 8);
	 Button button1_9= new JoystickButton(joy, 9);
	 Button button1_10= new JoystickButton(joy, 10);
	 
	 Button button1_11= new JoystickButton(joy, 11);
	 Button button1_12= new JoystickButton(joy, 12);
	 
	
	 public static Joystick joy1 = new Joystick(2);
		
	 Button button2_1= new JoystickButton(joy1, 1);
	 Button button2_2= new JoystickButton(joy1, 2);
     Button button2_3= new JoystickButton(joy1, 3);
	 Button button2_4= new JoystickButton(joy1, 4);
	 Button button2_5= new JoystickButton(joy1, 5);
	 
	 Button button2_6= new JoystickButton(joy1, 6);
	 Button button2_7= new JoystickButton(joy1, 7);
	 Button button2_8= new JoystickButton(joy1, 8);
	 Button button2_9= new JoystickButton(joy1, 9);
	 Button button2_10= new JoystickButton(joy1, 10);
	 
	 Button button2_11= new JoystickButton(joy1, 11);
	 Button button2_12= new JoystickButton(joy1, 12);
	 
	 
	
	public OI()
	{
		
		
		
		button1_2.whileHeld(new TestShoot());
		button1_2.whileHeld(new ReservoirCommand());
		
		
		
		
		button1_10.whenPressed(new MakeParallel(0));
		button1_11.whenPressed(new MoveToX(.2));
		button1_12.whenPressed(new MoveToY(-.2));
		
		
		button2_7.whenPressed(new DropGearCommand());
		button2_9.whenPressed(new PushGearCommand());
		button2_9.whenReleased(new HoldGearCommand());
		
		button2_5.whileHeld(new ClimbCommand());
		
		button2_6.whileHeld(new IntakeCommand());
	
	}

	// button.whileHeld(new ExampleCommand());

	
	// button.whenReleased(new ExampleCommand());
}
