/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.Watchdog;

public class RobotTemplate extends SimpleRobot {
    
    CANJaguar mFrontLeft;
    CANJaguar mFrontRight;
    CANJaguar mBackLeft;
    CANJaguar mBackRight;
    RobotDrive drive;
    Joystick leftStick;
    Joystick rightStick;
    //Gyro gyro;
    //DriverStationLCD display;
    
    public RobotTemplate() throws CANTimeoutException {
        mFrontLeft = new CANJaguar(6);
        mBackLeft = new CANJaguar(2);
        mFrontRight = new CANJaguar(4);
        mBackRight = new CANJaguar(5);
        leftStick = new Joystick(1);
        rightStick = new Joystick(2);

        drive = new RobotDrive(mFrontLeft, mBackLeft, mFrontRight, mBackRight);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
    }
    
    public void autonomous() {
        
    }

    public void operatorControl() {
        getWatchdog().setEnabled(false);
        //display.clear();
        //gyro.reset();
        while(isEnabled() && isOperatorControl()) {
            //double angle = gyro.getAngle();
            drive.tankDrive(leftStick, rightStick);
            //display.println(DriverStationLCD.Line.kUser1,1,Double.toString(angle));
            //display.updateLCD();
        }
    }
    
    public void test() {
        
    }
}
