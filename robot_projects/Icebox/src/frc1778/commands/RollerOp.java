/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc1778.commands;

/**
 *
 * @author veilljai000
 */
public class RollerOp extends CommandBase {
    
    public RollerOp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(roller);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("RollerOp Init");
        roller.setRollerSpeed(.25);
        roller.setSafety(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
