package org.usfirst.frc.team4645.robot.commands;

import org.usfirst.frc.team4645.robot.*;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterAndShootCommand extends CommandGroup 
{

    public CenterAndShootCommand() 
    {
    	
    	String alliance = Robot.allianceChooser.getSelected();
    	//double shooterSpeed = Robot.shooterChooser.getSelected();
       
    	/*
    	int alliance= Robot.allianceConstant;
    	double idealXDistance = 0;
    	double idealYDistance = 0;
    	double degreeToBoiler=135 * alliance;
    	*/
    	
    	//points robot to face boiler
        double boilerAngle;
        if (alliance.equals("Red"))
        {
        	boilerAngle = -45;
        }
        else
        {
        	boilerAngle = 45;
        }
        addSequential(new MakeParallel(boilerAngle));
    	
        
        //updates vision values
      //  double[] distanceInformation=(Robot.visionSubsystem.returnBoilerInformation());
        //moves in X
      //  addSequential(new MoveToX(0-distanceInformation[0]));
       
        
        //updates vision
        
        
        //moves Y
//        double positionFinalY;
//        if (shooterSpeed == RobotMap.fastSpeed)
//        {
//        	positionFinalY = RobotMap.farY;
//        }
//        else
//        {
//        	positionFinalY = RobotMap.closeY;
//        }
        
       // distanceInformation=(Robot.visionSubsystem.returnBoilerInformation());
    	//addSequential(new MoveToY(positionFinalY-distanceInformation[1]));
    	
    	addSequential(new TestShoot(),5);
    	
    
    }
}
