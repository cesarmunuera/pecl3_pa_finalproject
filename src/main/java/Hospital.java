import java.util.ArrayList;
import java.util.logging.Logger;

public class Hospital {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
	
	ArrayList<HospitalFloor> floors = new ArrayList<>();
	JarvisSystem jarvisSystem = new JarvisSystem();
	
	public void initFloors() {
		logger.info("initializing floors");
		JarvisRemoteControl remote;
		HospitalFloor floor;
		
		for (int nFloor=Configuration.HOSPITAL_FLOOR_MIN; nFloor<=Configuration.HOSPITAL_FLOOR_MAX; nFloor++) { 
			remote = new JarvisRemoteControl(this.jarvisSystem, nFloor);
			floor = new HospitalFloor(nFloor, remote);
			floors.add(floor);
		}
	}
	
	public Hospital() {
		this.initFloors();
		logger.info("initialized with " + this.floors.size() + " floors");
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
