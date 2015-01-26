package org.usfirst.frc.team1778.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.TalonSRX;

//Chill Out 1778 class for controlling the drivetrain

public class DriveAssembly {

	// Speed controller IDs
	private final int LEFT_FRONT_TALON_ID = 3;
	private final int LEFT_REAR_TALON_ID = 4;
	private final int RIGHT_FRONT_TALON_ID = 8;
	private final int RIGHT_REAR_TALON_ID = 7;
	
	private final int LATERAL_TALON_ID1 = 5;
	private final int LATERAL_TALON_ID2 = 6;
		
	// joystick ids
	private final int LEFT_JOYSTICK_ID = 0;
	private final int RIGHT_JOYSTICK_ID = 1;
	
    // drive throttle (how fast the drivetrain moves, and direction)
    //private final double DRIVE_STEP_MAGNITUDE_DEFAULT = 1.0;
    //private final double DRIVE_STEP_POLARITY_DEFAULT = 1.0;
    // minimum motor increment (for joystick dead zone)
    //private final double MIN_INCREMENT = 0.1;
		
	// speed controllers and drive class
	private CANTalon mFrontLeft, mBackLeft, mFrontRight, mBackRight;
	private CANTalon mLateral_1, mLateral_2;
    private RobotDrive drive;
    
    // drive control
    private Joystick leftStick, rightStick;
	
	// constructor - tank drive
	public DriveAssembly()
	{
        mFrontLeft = new CANTalon(LEFT_FRONT_TALON_ID);
        mBackLeft = new CANTalon(LEFT_REAR_TALON_ID);
        mFrontRight = new CANTalon(RIGHT_FRONT_TALON_ID);
        mBackRight = new CANTalon(RIGHT_REAR_TALON_ID);
        
        mLateral_1 = new CANTalon(LATERAL_TALON_ID1);
        mLateral_1.enableBrakeMode(true);
        
        mLateral_2 = new CANTalon(LATERAL_TALON_ID2);
        mLateral_2.enableBrakeMode(true);
        
        drive = new RobotDrive(mFrontLeft, mBackLeft, mFrontRight, mBackRight);
        
        leftStick = new Joystick(LEFT_JOYSTICK_ID);
        rightStick = new Joystick(RIGHT_JOYSTICK_ID);
	}


	public void autoPeriodic()
	{
		// todo - autonomous operation of drive
	}
		
	public void teleopPeriodic()
	{
		
		// control robot forward and turn movement with tank drive
		drive.tankDrive(leftStick, rightStick);
		
		// control strafe speed controller with x-axis (use left joystick)
		double lateralValue = leftStick.getX();
		mLateral_1.set(lateralValue);
		mLateral_2.set(lateralValue);
		
		System.out.println("Drivetrain: LeftY = " + leftStick.getY() + " RightY = " + rightStick.getY() + " LeftX = " + leftStick.getX());
	}
	
	// calibrated joystick drive
	// not currently used
	private void tankDrive()
	{
		/*
        double driveStep = DRIVE_STEP_POLARITY_DEFAULT * DRIVE_STEP_MAGNITUDE_DEFAULT;
        double leftDriveIncrement = leftStick.getRawAxis(2) * driveStep;
        if (Math.abs(leftDriveIncrement) < MIN_INCREMENT)
            leftDriveIncrement = 0.0;
        double rightDriveIncrement = rightStick.getRawAxis(2) * driveStep;
        if (Math.abs(rightDriveIncrement) < MIN_INCREMENT)
            rightDriveIncrement = 0.0;
 
        drive.tankDrive(leftStick, rightStick);
        */
	}
		
}
