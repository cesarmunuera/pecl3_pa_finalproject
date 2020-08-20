import java.util.ArrayList;
import java.util.logging.Logger;

public class Hospital {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
	
	private ArrayList<HospitalFloor> floors;
	private JarvisSystem jarvisSystem;
	

	public void initFloors() {
		if (Configuration.LOGGING_ON) logger.info("initializing floors");
		this.floors = new ArrayList<>();
		JarvisRemoteControl remote;
		HospitalFloor floor;
		
		for (int nFloor=Configuration.HOSPITAL_FLOOR_MIN; nFloor<=Configuration.HOSPITAL_FLOOR_MAX; nFloor++) { 
			remote = new JarvisRemoteControl(jarvisSystem, nFloor);
			floor = new HospitalFloor(nFloor, remote);
			this.floors.add(floor);
		}
	}
	
	public Hospital() {
		this.jarvisSystem = new JarvisSystem(this);
		this.initFloors();
		if (Configuration.LOGGING_ON) logger.info("initialized with " + this.floors.size() + " floors");
	}

	public HospitalFloor getFloor(int nFloor) {
		HospitalFloor selectedFloor = null;
		for (HospitalFloor floor: floors) {
			if (floor.getFloor() == nFloor) {
				selectedFloor = floor;
				break;
			}
			
		}
		return selectedFloor;
	}
	
	
}
