
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class JarvisSystem {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    ArrayList<Elevator> elevators;
    ElevatorBackUp elevatorBackUp;
    private Map<Integer, Boolean> externalRequestedFloors;
    ElevatorBreaker elevatorBroker;
    ArrayList<JarvisRemoteControl> remotes;

    public void initElevators() {
    	this.elevators = new ArrayList<>(Configuration.JARVIS_N_ELEVATORS);
    	
    	Elevator elevator;
    	for (int i=0; i<Configuration.JARVIS_N_ELEVATORS; i++) {
    		elevator = new Elevator("elevator_" + i, ElevatorStatus.MOVE, this);
    		this.elevators.add(elevator);
    		elevator.start();
    	}
        this.elevatorBackUp = new ElevatorBackUp("elevator_bkp", ElevatorStatus.OFF, this);
        logger.info("elevators initialized");
    }

    public void initRequestedFloors() {
        this.externalRequestedFloors = new ConcurrentHashMap<>();
        for (int i = Configuration.HOSPITAL_FLOOR_MIN; i <= Configuration.HOSPITAL_FLOOR_MAX; i++) {
            this.getExternalRequestedFloors().put(i, false);
        }
    }

    public JarvisSystem() {
    	logger.info("initializing jarvis");
    	this.initRequestedFloors();
    	this.initElevators();
        this.remotes = new ArrayList<>(Configuration.HOSPITAL_FLOOR_MAX + 1);
        logger.info("jarvis initialized!");
    }

    public void callElevator(int floor) {
    	logger.info("called elevator from floor " + floor);
        this.getExternalRequestedFloors().put(floor, true);

    }

    public void restoreRequestedFloors(Map<Integer, Boolean> requestedFloors) {
        for (int i = Configuration.HOSPITAL_FLOOR_MIN; i <= Configuration.HOSPITAL_FLOOR_MAX; i++) {
            boolean restoreRequested = requestedFloors.get(i);
            if (restoreRequested) {
                getExternalRequestedFloors().put(i, restoreRequested);
            }
        }
    }

    public ArrayList<Elevator> getElevatorsInFloor(int floor) {
        ArrayList<Elevator> elevatorsInFloor = new ArrayList<>();
        for (Elevator elevator : this.elevators) {
            if (elevator.currentFloor == floor && elevator.status != ElevatorStatus.BROKEN) {
            	elevatorsInFloor.add(elevator);
            }
        }
        
        // TODO: Check ElevatorBackUp
        return elevatorsInFloor;
    }

    public synchronized HashMap<Integer, Boolean> getExternalRequestedFloors(int currentFloor, ElevatorDirection direction) {
        HashMap<Integer, Boolean> requestedFloors = new HashMap<Integer, Boolean>();
        for (int i = Configuration.HOSPITAL_FLOOR_MIN; i <= Configuration.HOSPITAL_FLOOR_MAX; i++) {
            requestedFloors.put(i, false);
        }
        int step;
        int i = currentFloor;
        int stop;

        if (direction == ElevatorDirection.UP) {
            step = 1;
            stop = Configuration.HOSPITAL_FLOOR_MAX;
        } else if (direction == ElevatorDirection.DOWN) {
            step = -1;
            stop = Configuration.HOSPITAL_FLOOR_MIN;
        } else {
            // go up by default if no direction provided
            step = 1;
            stop = Configuration.HOSPITAL_FLOOR_MAX;
        }

        while (i != stop) {
            boolean isRequested = this.externalRequestedFloors.get(i);
            requestedFloors.put(i, isRequested);
            this.externalRequestedFloors.put(i, false);
            i += step;
        }
        return requestedFloors;

    }

    public Map<Integer, Boolean> getExternalRequestedFloors() {
        return externalRequestedFloors;
    }

    public void setExternalRequestedFloors(Map<Integer, Boolean> externalRequestedFloors) {
        this.externalRequestedFloors = externalRequestedFloors;
    }

	public void configureRemote(JarvisRemoteControl remote) {
		remotes.add(remote);
		logger.info("added new remote controller " + remote.toString());
		
	}

	public void notifyFloorStop(int floor) {
		for (JarvisRemoteControl remote: this.remotes) {
			if (remote.value == floor) {
				remote.notifyElevatorArriving();
			}
		}
	}

	public void notifyFloorMove(int floor) {
		for (JarvisRemoteControl remote: this.remotes) {
			if (remote.value == floor) {
				remote.notifyElevatorLeaving();
			}
		}
		
		// TODO: print system status
	}

	public void notifyElevatorRepaired() {
		this.elevatorBackUp.turnOff();
		
	}

}