package org.usfirst.frc.team4645.robot.commands;

import org.usfirst.frc.team4645.robot.Robot;
import org.usfirst.frc.team4645.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceGearCommand extends CommandGroup 
{

    public PlaceGearCommand(double backUpDistance) 
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
    	//faces the gear
    	
    	//it is assumed that this command starts with the gear retroreflective tape already in view
    	
    	//makes itself parallel to the face of the airship with gear on it 

    	
    	//get vision values
    	//double[] distanceInformation=(Robot.visionSubsystem.returnGearInformation());
    	
    	//lines itself up in x so that the gear subsystem is directly in front of the peg
    	//addSequential(new MoveToX(0-distanceInformation[0]));
    	
    	//drops the gear
    	addSequential(new DropGearCommand());
        
    	//gets vision values again
    	//distanceInformation=(Robot.visionSubsystem.returnGearInformation());
     	
    	//calculates how far forwards it must move to be on the peg but not hitting the airship and moves
    	
    	//addSequential(new MoveToY(-(.5-RobotMap.GEAR_DISTANCE)));
     	
    	//pushes the gear onto the peg
     	addSequential(new PushGearCommand());
     
     	//backs up 
    	addSequential( new MoveToY(backUpDistance));
    	
    	addSequential(new DropGearCommand());
    	
    	//returns gear subsystem to intial spot
    	//addSequential(new HoldGearCommand());
    	
    	
    	
    	
    	
    	
    }
}