package Systems;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.vision.AxisCamera;

public class CameraAssembly {
	private static boolean initialized = false;

	private static final long TARGET_SCAN_PERIOD_USEC = 250000;
	private static final int TARGET_CENTER_TOLERANCE_PIXELS = 100;
	private static final int OFF_TARGET_VALUE = 1000;

	private static boolean scanningMode;
	private static long initTime, currentTime;

	private static int targetCenterX, targetCenterY;
	private static boolean targetSeen;

	public static void initialize() {
		if (!initialized) {
			// myImage = new HSLImage();
			scanningMode = false;
			initTime = Utility.getFPGATime();

			TargetFinder.initCamera("10.17.78.20");
			// AxisCamera camera = new AxisCamera("10.17.78.20");

			targetCenterX = OFF_TARGET_VALUE;
			targetCenterY = OFF_TARGET_VALUE;
			targetSeen = false;

			initialized = true;
		}
	}

	public static void setScanMode(boolean mode) {
		scanningMode = mode;
	}

	public static void scanForTarget() {
		// if we're not actively scanning, return
		if (!scanningMode)
			return;

		// if we are actively scanning but enough time has not passed, return
		currentTime = Utility.getFPGATime();
		if ((currentTime - initTime) < TARGET_SCAN_PERIOD_USEC)
			return;

		// check Targetfinder class to see if target is seen
		ParticleAnalysisReport target = null;
		try {
			target = TargetFinder.getTarget();
		} catch (NIVisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// print out stats on best target found if one exists
		if (target != null) {
			System.out.println("Target!  X: " + TargetFinder.getNormX(target) +
							   " Height: " + TargetFinder.getHeight(target) + 
							   " Width: " + TargetFinder.getWidth(target) + 
							   " Area: " + TargetFinder.getArea(target));
		}

		// get position relative to center of image

		// if target exists but not lined up, flag LED yellow
		// if we are lined up with target, flag LED green
		/*
		 * if (targetCenterY < TARGET_CENTER_TOLERANCE_PIXELS)
		 * RioDuinoAssembly.sendColor(RioDuinoAssembly.Color.Green); else if
		 * (targetSeen)
		 * RioDuinoAssembly.sendColor(RioDuinoAssembly.Color.Yellow); else
		 * RioDuinoAssembly.setTeamColor();
		 */

		// reset timer
		initTime = Utility.getFPGATime();

	}

}
