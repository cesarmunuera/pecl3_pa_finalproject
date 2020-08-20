import java.util.ArrayList;
import java.util.logging.Logger;

public class HospitalFloor {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    int floor;
    JarvisRemoteControl jarvisRemoteControl;
    boolean elevatorInFloor = false;
    boolean waitingElevator = false;

    public HospitalFloor(int floor, JarvisRemoteControl jarvisRemoteControl) {
		this.floor = floor;
		this.jarvisRemoteControl = jarvisRemoteControl;
		if (Configuration.LOGGING_ON) logger.info(this.toString() + " initialized");
	}
    
    @Override
	public String toString() {
		return "HospitalFloor(" + this.floor + ")";
	}

	public void callElevator() {
        this.jarvisRemoteControl.callElevator();
    }

    public ArrayList<Elevator> getElevators() {
        return this.jarvisRemoteControl.getElevatorsInFloor(this.floor);
    }

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public JarvisRemoteControl getJarvisRemoteControl() {
		return jarvisRemoteControl;
	}

	public void setJarvisRemoteControl(JarvisRemoteControl jarvisRemoteControl) {
		this.jarvisRemoteControl = jarvisRemoteControl;
	}

	public boolean isElevatorInFloor() {
		return elevatorInFloor;
	}

	public void setElevatorInFloor(boolean elevatorInFloor) {
		this.elevatorInFloor = elevatorInFloor;
	}

	public boolean isWaitingElevator() {
		return waitingElevator;
	}

	public void setWaitingElevator(boolean waitingElevator) {
		this.waitingElevator = waitingElevator;
	}

	public static Logger getLogger() {
		return logger;
	}
    
    

}
