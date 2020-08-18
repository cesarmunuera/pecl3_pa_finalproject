
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
	
	public static final String LOG_NAME = "HospitalElevatorSystem";
	private static final String LOG_FILE = "./hospital-elevator-system.log";
	private static final Logger logger = Logger.getLogger(LOG_NAME);
	
	private Logging() {
	}
	
	
	public static void initLogger() throws SecurityException, IOException {
		Handler fileHandler = new FileHandler(LOG_FILE, false);
		SimpleFormatter simpleFormatter = new SimpleFormatter();
		fileHandler.setFormatter(simpleFormatter);
		fileHandler.setLevel(Level.WARNING);
		logger.addHandler(fileHandler);
        
	}

}
