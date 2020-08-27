package hospital;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Hospital {

    private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    ArrayList<HospitalFloor> floors;
    JarvisSystem jarvisSystem;

    public void initFloors() {
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
        this.initFloors();
        if (Configuration.LOGGING_ON) {
            logger.info("initialized with " + this.floors.size() + " floors");
        }
    }

    public HospitalFloor getFloor(int currentFloor) {
        HospitalFloor selectedFloor = null;
        for (HospitalFloor floor : this.floors) {
            if (floor.floor == currentFloor) {
                selectedFloor = floor;
                break;
            }

        }
        return selectedFloor;
    }

    public ArrayList<HospitalFloor> getFloors() {
        return floors;
    }

    public void setFloors(ArrayList<HospitalFloor> floors) {
        this.floors = floors;
    }

    public JarvisSystem getJarvisSystem() {
        return jarvisSystem;
    }

    public void setJarvisSystem(JarvisSystem jarvisSystem) {
        this.jarvisSystem = jarvisSystem;
    }

    public static Logger getLogger() {
        return logger;
    }

    public boolean isEvacuating() {
        return this.jarvisSystem.isEvacuating();
    }
}
