import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JarvisRemoteControl {
	
	JarvisSystem jarvisSystem;
	int value;
	boolean active = false; // condition
	boolean elevatorInFloor = false;
	final Lock lock = new ReentrantLock();
	final Condition elevatorInFloorCondition  = lock.newCondition(); 
	
	public JarvisRemoteControl(JarvisSystem jarvisSystem, int value) {
		this.jarvisSystem = jarvisSystem;
		this.value = value;
	}
	
	public void configureControl() {
		jarvisSystem.configureRemote(this);
	}
	
	public void waitForElevator() {
		try {
			lock.lock();
			while (!elevatorInFloor) {
				try {
					elevatorInFloorCondition.await();
				} catch (Exception e){}
			}
			//código accedido en exclusión mutua (al recurso compartido)
		}
		finally {
			lock.unlock();
		}
	}
	
	public void callElevator() {
		if (!this.active) {
			this.jarvisSystem.callElevator(this.value);
			this.active = true;
		}
		
		waitForElevator();
    }
	
	public void notifyElevatorArriving() {
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
