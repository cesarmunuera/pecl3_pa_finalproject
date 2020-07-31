
public class HospitalFloor {

    int floor;
    JarvisSystem jarvisSystem;
    JarvisRemoteControl jarvisRemoteControl;
    boolean elevatorInFloor = false;
    boolean waitingElevator = false;

    public HospitalFloor(int floor) {
		this.floor = floor;
		this.jarvisRemoteControl = new JarvisRemoteControl(this.jarvisSystem, this.floor);
	}

	public void callElevator() {
        this.jarvisRemoteControl.callElevator();
    }

    public Elevator getElevator() {
        return this.jarvisSystem.getElevatorInFloor(this.floor);
    }

}
