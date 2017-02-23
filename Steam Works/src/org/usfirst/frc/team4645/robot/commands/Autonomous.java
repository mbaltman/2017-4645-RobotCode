package org.usfirst.frc.team4645.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4645.robot.*;

/**
 *
 */
public class Autonomous extends CommandGroup 
{

    public Autonomous() 
    {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	double initialDistanceY;
    	double boilerAngle;
    	double backUpDistance;
    	
    	//selected values
    	String position = Robot.positionChooser.getSelected();
    	String alliance = Robot.allianceChooser.getSelected();
    	double shooterSpeed = Robot.shooterChooser.getSelected();
    	
    	boolean redBoiler = alliance.equals("Red") && position.equals("Boiler");
    	boolean redMiddle = alliance.equals("Red") && position.equals("Middle");
    	boolean redLoading = alliance.equals("Red") && position.equals("Loading");
    	boolean blueBoiler = alliance.equals("Blue") && position.equals("Boiler");
    	boolean blueMiddle = alliance.equals("Blue") && position.equals("Middle");
    	boolean blueLoading = alliance.equals("Blue") && position.equals("Loading");
    	
    	
    	//finds the initial distance to move in Y
    	if (position.equals("Boiler"))
    	{
    		initialDistanceY = -1.764;
    	}
    	else if (position.equals("Middle"))
    	{
    		initialDistanceY = -1.7;
    	}
    	else
    	{
    		initialDistanceY = -1.794;
    	}
    	
    	
    	//find the degree the boiler is at
    	if (alliance.equals("Red"))
    	{
    		boilerAngle = -45;
    	}
    	else
    	{
    		boilerAngle = 45;
    	}
    	
    	
    	//find distance to backup from gear
    	if (position.equals("Loading"))
    	{
    		backUpDistance = 1;
    	}
    	else 
    	{
    		backUpDistance = 1.5;
    	}
    	
    	
    	//moves to initial Y position
    	addSequential(new MoveToY(initialDistanceY));
    	
    	
    	//turns to face gear and move forward if necessary
    	if (redBoiler)
    	{
    		addSequential(new MakeParallel(-60));
    		addSequential(new MoveToY(-1.68));
    	}
    	
    	else if (redLoading)
    	{
    		addSequential(new MakeParallel(60));
    		addSequential(new MoveToY(-1.76));
    	}
    	
    	else if (blueBoiler)
    	{
    		addSequential(new MakeParallel(60));
    		addSequential(new MoveToY(-1.68));
    	}
    	
    	else if (blueLoading)
    	{
    		addSequential(new MakeParallel(-60));
    		addSequential(new MoveToY(-1.76));
    	}
    	
    	//places the gear + backs up
    	double gearAngle;
    	if (redBoiler || blueLoading)
    	{
    		gearAngle = -60;
    	}
    	else if (redMiddle || blueMiddle)
    	{
    		gearAngle = 0;
    	}
    	else
    	{
    		gearAngle = 60;
    	}
    	addSequential(new PlaceGearCommand(gearAngle, backUpDistance));
    	
    	//if at a loading station, move some more
    	if (redLoading)
    	{
    		addSequential(new MakeParallel(-90));
    		addSequential(new MoveToX(1.25));
    		addSequential(new MoveToY(3.5));
    	}
    	else if (blueLoading)
    	{
    		addSequential(new MakeParallel(90));
    		addSequential(new MoveToX(-1.25));
    		addSequential(new MoveToY(3.5));
    	}
    	
    	//face boiler, center and shoot
    	addSequential(new MakeParallel(boilerAngle));
    	addSequential(new CenterAndShootCommand());
    	
    }
}


























