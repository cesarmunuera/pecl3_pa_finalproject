import java.util.ArrayList;

public class Hospital {
	
	ArrayList<HospitalFloor> floors = new ArrayList<>();
	
	public Hospital() {
		//TODO: check if floor 20 or until 19
		for (int i=Configuration.MIN_FLOOR; i<Configuration.MAX_FLOOR; i++) { 
			floors.add(new HospitalFloor(i));
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
