
public class HospitalFloor {

    int floor;
    JarvisRemoteControl jarvisRemoteControl;
    boolean elevatorInFloor = false;
    boolean waitingElevator = false;

    public HospitalFloor(int floor, JarvisRemoteControl jarvisRemoteControl) {
		this.floor = floor;
		this.jarvisRemoteControl = jarvisRemoteControl;
	}

	public void callElevator() {
        this.jarvisRemoteControl.callElevator();
    }

    public Elevator getElevator() {
        return this.jarvisRemoteControl.getElevatorInFloor(this.floor);
    }

}
