import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class JarvisRemoteControl {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
	
	private JarvisSystem jarvisSystem;
	private int value;
	private boolean active; // condition
	private boolean elevatorInFloor;
	private final Lock lock;
	private final Condition elevatorInFloorCondition; 
	
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
		return "JarvisRemote(" + value + ")";
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
		if (!active) {
			jarvisSystem.callElevator(value);
			active = true;
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
			elevatorInFloor = true;
			elevatorInFloorCondition.signal();
		}
		finally {
			lock.unlock();
		}
	}

	public void notifyElevatorLeaving() {
		elevatorInFloor = false;
		active = false;
	}

	public ArrayList<Elevator> getElevatorsInFloor(int floor) {
		return jarvisSystem.getElevatorsInFloor(floor);
	}

	public int getValue() {
		return value;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isElevatorInFloor() {
		return elevatorInFloor;
	}


}
