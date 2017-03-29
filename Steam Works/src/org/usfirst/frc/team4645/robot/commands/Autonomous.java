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
    	//double shooterSpeed = Robot.shooterChooser.getSelected();
    	
    	boolean redBoiler = alliance.equals("Red") && position.equals("Boiler");
    	boolean redMiddle = alliance.equals("Red") && position.equals("Middle");
    	boolean redLoading = alliance.equals("Red") && position.equals("Loading");
    	boolean blueBoiler = alliance.equals("Blue") && position.equals("Boiler");
    	boolean blueMiddle = alliance.equals("Blue") && position.equals("Middle");
    	boolean blueLoading = alliance.equals("Blue") && position.equals("Loading");
    	
    	
    	//finds the initial distance to move in Y
    	
    	if(position.equals("null"))
    	{
    		addSequential(new MoveToY(-2));
    		
    	}
    	
    	else
    	{
	    	
	    	
	    	
	    	if (position.equals("Boiler"))
	    	{
	    		initialDistanceY = -1.764;
	    	}
	    	else if (position.equals("Middle"))
	    	{
	    		initialDistanceY = -1.85;
	    	}
	    	else 
	    	{
	    		initialDistanceY = -1.794;
	    	}
	    	
	    	
	    	//find the degree the boiler is at
	    	if (alliance.equals("Red"))
	    	{
	    		boilerAngle = 45;
	    	}
	    	else
	    	{
	    		boilerAngle = -45;
	    	}
	    	
	    	
	    	//find distance to backup from gear
	    	if (position.equals("Boiler"))
	    	{
	    		backUpDistance = 1.634 - RobotMap.GEAR_DISTANCEY;
	    	}
	    	else 
	    	{
	    		backUpDistance = .9572-RobotMap.GEAR_DISTANCEY;
	    	}
	    	
	    	
	    	//moves to initial Y position
	    	if (redMiddle || blueMiddle)
	    	{
	    		addSequential(new MoveToY(initialDistanceY), 7.5);
	    	}
	    	else
	    	{
	    		addSequential(new MoveToY(initialDistanceY));
	    	}
	    	
	    	
	    	//turns to face gear and move forward if necessary
	    	if (redBoiler)
	    	{
	    		addSequential(new MakeParallel(60));
	    		addSequential(new MoveToY(-1.844), 7.5);
	    	}
	    	
	    	else if (redLoading)
	    	{
	    		addSequential(new MakeParallel(-60));
	    		addSequential(new MoveToY(-1.754), 7.5);
	    	}
	    	
	    	else if (blueBoiler)
	    	{
	    		addSequential(new MakeParallel(-60));
	    		addSequential(new MoveToY(-1.844), 7.5);
	    	}
	    	
	    	else if (blueLoading)
	    	{
	    		addSequential(new MakeParallel(60));
	    		addSequential(new MoveToY(-1.754), 7.5);
	    	}
	    
	    	
	    	
	    	//place gear command was here, need to back up differently now
	    	//just use movetoy with the calced backupdistance
	    	addSequential(new MoveToY(backUpDistance));
	    	
	    	
	    	
	    	//NO shooting from loading position
	//    	//if at a loading station, move some more
	//    	if (redLoading)
	//    	{
	//    		addSequential(new MakeParallel(-90));
	//    		addSequential(new MoveToX(1.25));
	//    		addSequential(new MoveToY(3.5));
	//    	}
	//    	else if (blueLoading)
	//    	{
	//    		addSequential(new MakeParallel(90));
	//    		addSequential(new MoveToX(-1.25));
	//    		addSequential(new MoveToY(3.5));
	//    	}
	    	
	    	//face boiler, center and shoot
	    	
	    	if (redBoiler || blueBoiler)
	    	{
	    		//addSequential (new MoveToY(1.69 - RobotMap.GEAR_DISTANCE));
	    		addSequential(new MakeParallel(boilerAngle));
	    		addSequential(new MoveToX((.367 + (Math.signum(boilerAngle) * .2625)) * Math.signum(boilerAngle)));
	    		addParallel(new ShootCommand(),8);
	    		addSequential(new ReservoirCommand(),8);
	    	}
	    	
	    	else if (redMiddle)
	    	{
	    		addSequential(new MoveToX(-2.117));
	    		addSequential(new MoveToY (-.514));
	    		addSequential(new MakeParallel(boilerAngle));
	    		addParallel(new ShootCommand(),8);
	    		addSequential(new ReservoirCommand(),8);
	    	}
	    	else if (blueMiddle)
	    	{
	    		addSequential(new MoveToX(2.117));
	    		addSequential(new MoveToY (-.514));
	    		addSequential(new MakeParallel(boilerAngle));
	    		addParallel(new ShootCommand(),8);
	    		addSequential(new ReservoirCommand(),8);
	    	}
	    	else if (redLoading||blueLoading)
	    	{
	    		addSequential(new MakeParallel(0));
	    		addSequential(new MoveToY(2));
	    	}
	    	
    	}
    	
    }
}


























