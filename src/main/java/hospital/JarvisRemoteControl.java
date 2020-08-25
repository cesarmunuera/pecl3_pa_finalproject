package hospital;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class JarvisRemoteControl {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
	
	JarvisSystem jarvisSystem;
	int value;
	boolean active; // condition
	boolean elevatorInFloor;
	final Lock lock;
	final Condition elevatorInFloorCondition; 
	
	public JarvisRemoteControl(JarvisSystem jarvisSystem, int value) {
		this.jarvisSystem = jarvisSystem;
		this.active = false;
		this.elevatorInFloor = false;
		this.lock = new ReentrantLock();
		this.elevatorInFloorCondition = lock.newCondition();
		this.value = value;
		this.jarvisSystem.configureRemote(this);
		//logger.info(this.toString() + " initialized and configured");
	}
	
	@Override
	public String toString() {
		return "JarvisRemote(" + this.value + ")";
	}
	
	public void waitForElevator() {
		try {
			lock.lock();
			while (!elevatorInFloor) {
				try {
					elevatorInFloorCondition.await();
				} catch (Exception e){}
			}
			// end waiting - person continues with execution
		}
		finally {
			lock.unlock();
		}
	}
	
	public void callElevator() {
		if (!this.active) {
			this.jarvisSystem.callElevator(this.value);
			this.active = true;
			if (Configuration.LOGGING_ON) logger.info(this.toString() + " call elevator");
		} else {
			if (Configuration.LOGGING_ON) logger.info(this.toString() + " elevator already called ");
		}
		waitForElevator();
    }
	
	public synchronized void notifyElevatorArriving() {
		if (Configuration.LOGGING_ON) logger.info(this.toString() + " elevator arrives - wake up people waiting");
		try {
			lock.lock();
			this.elevatorInFloor = true;
			elevatorInFloorCondition.signal();
			}
		finally {
			lock.unlock();
		}
	}

	public void notifyElevatorLeaving() {
		this.elevatorInFloor = false;
		this.active = false;
		
	}

	public ArrayList<Elevator> getElevatorsInFloor(int floor) {
		return this.jarvisSystem.getElevatorsInFloor(floor);
	}

	public JarvisSystem getJarvisSystem() {
		return jarvisSystem;
	}

	public void setJarvisSystem(JarvisSystem jarvisSystem) {
		this.jarvisSystem = jarvisSystem;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isElevatorInFloor() {
		return elevatorInFloor;
	}

	public void setElevatorInFloor(boolean elevatorInFloor) {
		this.elevatorInFloor = elevatorInFloor;
	}

	public static Logger getLogger() {
		return logger;
	}

	public Lock getLock() {
		return lock;
	}

	public Condition getElevatorInFloorCondition() {
		return elevatorInFloorCondition;
	}
	
	

}