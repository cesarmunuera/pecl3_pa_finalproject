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
		logger.info(this.toString() + " initialized");
	}
    
    @Override
	public String toString() {
		return "HospitalFloor(" + this.floor + ")";
	}

	public void callElevator() {
        this.jarvisRemoteControl.callElevator();
    }

    public Elevator getElevator() {
        return this.jarvisRemoteControl.getElevatorInFloor(this.floor);
    }

}
