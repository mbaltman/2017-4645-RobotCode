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
    	if (position.equals("Boiler"))
    	{
    		initialDistanceY = -1.764;
    	}
    	else if (position.equals("Middle"))
    	{
    		initialDistanceY = -1.925;
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
    		backUpDistance = 1.69 - RobotMap.GEAR_DISTANCE;
    	}
    	else 
    	{
    		backUpDistance = 1;
    	}
    	
    	
    	//moves to initial Y position
    	addSequential(new MoveToY(initialDistanceY));
    	
    	
    	//turns to face gear and move forward if necessary
    	if (redBoiler)
    	{
    		addSequential(new MakeParallel(60));
    		addSequential(new MoveToY(-1.49));
    	}
    	
    	else if (redLoading)
    	{
    		addSequential(new MakeParallel(-60));
    		addSequential(new MoveToY(-1.73));
    	}
    	
    	else if (blueBoiler)
    	{
    		addSequential(new MakeParallel(-60));
    		addSequential(new MoveToY(-1.49));
    	}
    	
    	else if (blueLoading)
    	{
    		addSequential(new MakeParallel(60));
    		addSequential(new MoveToY(-1.73));
    	}
    
    	
    	
    	
    	
    	
    	addSequential(new PlaceGearCommand(backUpDistance));
    	
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
    		addSequential(new MoveToX((.44 + (Math.signum(boilerAngle) * .2625)) * Math.signum(boilerAngle)));
    		addParallel(new TestShoot(),8);
    		addSequential(new ReservoirCommand(),8);
    		
    		
    	}
    	
    }
}


























