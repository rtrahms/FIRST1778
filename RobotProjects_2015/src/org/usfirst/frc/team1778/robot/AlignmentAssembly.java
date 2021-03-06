package org.usfirst.frc.team1778.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TalonSRX;

// Chill Out 1778 class for controlling the alignment mechanism

public class AlignmentAssembly {
	
    // Joystick magnitudes, need to be opposite each other to perform properly

    private static final double MIN_INCREMENT = 0.1;
	private final double LEFT_POLARITY = -1.0;
    private final double RIGHT_POLARITY = 1.0;
    private final double WHEEL_THROTTLE = 0.75;   

    // Speed Controller IDs
	private final int LEFT_WHEEL_TALON_ID = 9;
	private final int RIGHT_WHEEL_TALON_ID = 10;
	
	// elevator controller gampad ID
	private final int GAMEPAD_ID = 2;
	
	private CANTalon mLeftWheel, mRightWheel;
	
	private Joystick gamepad;
	
	private double leftStep, rightStep;

	// constructor
	public AlignmentAssembly()
	{
		mLeftWheel = new CANTalon(LEFT_WHEEL_TALON_ID);
		mRightWheel = new CANTalon(RIGHT_WHEEL_TALON_ID);
		
        // wheel control
        gamepad = new Joystick(GAMEPAD_ID);
        
	}
	
	public void autoInit()
	{
		
	}
	
	public void autoPeriodic()
	{
	}
		
	public void teleopInit()
	{
	
	}
	
	public void teleopPeriodic()
	{
        // right and left wheel operation via right joystick
        double leftIncrement = LEFT_POLARITY * WHEEL_THROTTLE * gamepad.getRawAxis(3);   
        double rightIncrement = RIGHT_POLARITY * WHEEL_THROTTLE* gamepad.getRawAxis(1);   
        
        if (Math.abs(leftIncrement) < MIN_INCREMENT)
        {
            leftIncrement = 0.0;
        }
        
        if (Math.abs(rightIncrement) < MIN_INCREMENT)
        {
            rightIncrement = 0.0;
        }
        
        
        
        // set wheel speed for left and right
        mLeftWheel.set(leftIncrement);
        mRightWheel.set(rightIncrement);
            
	}
}
