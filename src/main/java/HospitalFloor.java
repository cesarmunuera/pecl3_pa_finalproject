

public class HospitalFloor {
	
	int floor;
	JarvisSystem jarvisSystem;
	boolean elevatorInFloor = false;
	boolean waitingElevator = false;
	
	public void callElevator() {
		this.jarvisSystem.callElevator(this.floor);
	}
	
	public Elevator waitElevator() {
		return this.jarvisSystem.getElevatorInFloor(this.floor);
	}
	

}
