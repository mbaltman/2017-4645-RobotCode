package org.usfirst.frc.team4645.robot.commands;

import org.usfirst.frc.team4645.robot.Robot;
import org.usfirst.frc.team4645.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceGearCommand extends CommandGroup 
{

    public PlaceGearCommand(double gearDegree, double backUpDistance) 
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
    	
    	//
    	
    	//faces the gear
    	addSequential(new MakeParallel(gearDegree));
    	
    	//updates vision values and moves in X, drops gear
    	double[] distanceInformation=(Robot.visionSubsystem.returnGearInformation());
    	addSequential(new MoveToX(0-distanceInformation[0]));
    	addSequential(new DropGearCommand());
        
    	//updates vision values and moves in Y, pushes gear
    	distanceInformation=(Robot.visionSubsystem.returnGearInformation());
     	addSequential(new MoveToY(RobotMap.GEAR_DISTANCE-distanceInformation[1]));
     	addSequential(new PushGearCommand());
     	
     	//backs up and resets gear servos
    	addSequential( new MoveToY(backUpDistance));
    	addSequential(new ResetGearCommand());
    	
    	
    	
    	
    }
}