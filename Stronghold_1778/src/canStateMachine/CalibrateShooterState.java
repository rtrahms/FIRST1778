package canStateMachine;

import Systems.AutoShooterAssembly;
import Systems.CatapultAssembly;

public class CalibrateShooterState extends AutoState {
	public CalibrateShooterState() {
		this.name = "<Calibrate Shooter State>";		
		
		// do some initialization
		AutoShooterAssembly.initialize();
	}
	
	public CalibrateShooterState(String name)
	{
		this.name = name;
		
		// do some initialization
		AutoShooterAssembly.initialize();
	}

	// state entry
	public void enter() {
		
		// NOT YET TESTED FOR AUTO MODE - DISABLED
		// set the calibration mode
		//AutoShooterAssembly.setCalibration(true);
						
		super.enter();
	}
	
	// called periodically
	public AutoState process()  {
		
		// NOT YET TESTED FOR AUTO MODE - DISABLED
		// calibrate shooter
		//AutoShooterAssembly.calibrateShooter();
		
		return super.process();
	}
	
	// state cleanup and exit
	public void exit() {
	
		// NOT YET TESTED FOR AUTO MODE - DISABLED
		// reset calibration mode
		//AutoShooterAssembly.setCalibration(false);

		// cleanup base class
		super.exit();
	}

}