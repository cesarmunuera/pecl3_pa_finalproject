import java.util.ArrayList;

public class Hospital {
	
	ArrayList<HospitalFloor> floors = new ArrayList<>();
	JarvisSystem jarvisSystem = new JarvisSystem();
	
	public Hospital() {
		for (int nFloor=Configuration.MIN_FLOOR; nFloor<=Configuration.MAX_FLOOR; nFloor++) { 
			floors.add(new HospitalFloor(nFloor, new JarvisRemoteControl(this.jarvisSystem, nFloor)));
		}
	}

	public HospitalFloor getFloor(int currentFloor) {
		HospitalFloor selectedFloor = null;
		for (HospitalFloor floor: this.floors) {
			if (floor.floor == currentFloor) {
				selectedFloor = floor;
				break;
			}
			
		}
		return selectedFloor;
	}

}
