import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class JarvisRemoteControl {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
	
	JarvisSystem jarvisSystem;
	int value;
	boolean active = false; // condition
	boolean elevatorInFloor = false;
	final Lock lock = new ReentrantLock();
	final Condition elevatorInFloorCondition = lock.newCondition(); 
	
	public JarvisRemoteControl(JarvisSystem jarvisSystem, int value) {
		this.jarvisSystem = jarvisSystem;
		this.value = value;
		this.jarvisSystem.configureRemote(this);
		logger.info(this.toString() + " initialized and configured");
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
			logger.info(this.toString() + " call elevator");
		} else {
			logger.info(this.toString() + " elevator already called ");
		}
		waitForElevator();
    }
	
	public synchronized void notifyElevatorArriving() {
		logger.info(this.toString() + " elevator arrives - wake up people waiting");
		try {
			lock.lock();
			this.elevatorInFloor = true;
			elevatorInFloorCondition.notifyAll();
			}
		finally {
			lock.unlock();
		}
	}

	public void notifyElevatorLeaving() {
		this.elevatorInFloor = false;
		this.active = false;
		
	}

	public Elevator getElevatorInFloor(int floor) {
		return this.jarvisSystem.getElevatorInFloor(floor);
	}

}
