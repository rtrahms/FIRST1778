package frc1778.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc1778.OI;
import frc1778.RobotClass;
import frc1778.subsystems.Drive;

public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Drive drive;

    // TODO: Declare a single static instance of front gate
    // TODO: Matt should do this
    
    public static void init() {      
        drive = new Drive ();

        // TODO: instantiate new instance of front gate
        // TODO: Matt should do this
        
        //  MUST be init'd here.  don't move it
        oi = new OI();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
