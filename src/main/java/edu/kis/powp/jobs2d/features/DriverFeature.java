package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.SelectDriverMenuOptionListener;
import edu.kis.powp.observer.Subscriber;


public class DriverFeature {

	private static DriverManager driverManager = new DriverManager();
	private static Application app;
	private static Subscriber subscriber = () -> updateDriverInfo();

	public static DriverManager getDriverManager() {
		return driverManager;
	}

	/**
	 * Setup jobs2d drivers Plugin and add to application.
	 * 
	 * @param application Application context.
	 */
	public static void setupDriverPlugin(Application application) {
		app = application;
		app.addComponentMenu(DriverFeature.class, "Drivers");
    
		driverManager.addSubscriber(subscriber);

		DriverInfoUpdaterObserver driverObserver = new DriverInfoUpdaterObserver(getDriverManager());
		driverManager.getPublisher().addSubscriber(driverObserver);
	}

	/**
	 * Add driver to context, create button in driver menu.
	 * 
	 * @param name   Button name.
	 * @param driver Job2dDriver object.
	 */
	public static void addDriver(String name, Job2dDriver driver) {
		SelectDriverMenuOptionListener listener = new SelectDriverMenuOptionListener(driver, driverManager);
		app.addComponentMenuElement(DriverFeature.class, name, listener);
	}

	/**
	 * Update driver info.
	 */

	public static Application getApp() {
		return app;
	}

	public static void updateDriverInfo() {
		app.updateInfo(driverManager.getCurrentDriver().toString());
	}

}
