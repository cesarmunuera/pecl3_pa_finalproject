package hospital;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Hospital {

    private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    private ArrayList<HospitalFloor> floors;
    private JarvisSystem jarvisSystem;

    private void initFloors() {
        if (Configuration.LOGGING_ON) {
            logger.info("initializing floors");
        }
        this.floors = new ArrayList<>();
        JarvisRemoteControl remote;
        HospitalFloor floor;

        for (int nFloor = Configuration.HOSPITAL_FLOOR_MIN; nFloor <= Configuration.HOSPITAL_FLOOR_MAX; nFloor++) {
            remote = new JarvisRemoteControl(this.jarvisSystem, nFloor);
            floor = new HospitalFloor(nFloor, remote);
            floors.add(floor);
        }
    }

    public Hospital() {
        this.jarvisSystem = new JarvisSystem(this);
        initFloors();
        if (Configuration.LOGGING_ON) {
            logger.info("initialized with " + this.floors.size() + " floors");
        }
    }

    public HospitalFloor getFloor(int currentFloor) {
        HospitalFloor selectedFloor = null;
        for (HospitalFloor floor : this.floors) {
            if (floor.getFloor() == currentFloor) {
                selectedFloor = floor;
                break;
            }

        }
        return selectedFloor;
    }

    public ArrayList<HospitalFloor> getFloors() {
        return floors;
    }

    public JarvisSystem getJarvisSystem() {
        return jarvisSystem;
    }

    public boolean isEvacuating() {
        return this.jarvisSystem.isEvacuating();
    }
}
