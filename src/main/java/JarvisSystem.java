
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
    	this.elevators = new ArrayList<>(Configuration.N_ELEVATORS);
    	
    	Elevator elevator;
    	for (int i=0; i<Configuration.N_ELEVATORS; i++) {
    		elevator = new Elevator("elevator_" + i, ElevatorStatus.MOVE, this);
    		this.elevators.add(elevator);
    		elevator.start();
    	}
        this.elevatorBackUp = new ElevatorBackUp("elevator_bkp", ElevatorStatus.OFF, this);
        logger.info("elevators initialized");
    }

    public void initRequestedFloors() {
        this.externalRequestedFloors = new ConcurrentHashMap<>();
        for (int i = Configuration.MIN_FLOOR; i <= Configuration.MAX_FLOOR; i++) {
            this.getExternalRequestedFloors().put(i, false);
        }
    }

    public JarvisSystem() {
    	logger.info("initializing jarvis");
    	this.initRequestedFloors();
    	this.initElevators();
        this.remotes = new ArrayList<>(Configuration.MAX_FLOOR + 1);
        logger.info("jarvis initialized!");
    }

    public void callElevator(int floor) {
    	logger.info("called elevator from floor " + floor);
        this.getExternalRequestedFloors().put(floor, true);

    }

    public void restoreRequestedFloors(Map<Integer, Boolean> requestedFloors) {
        for (int i = Configuration.MIN_FLOOR; i <= Configuration.MAX_FLOOR; i++) {
            boolean restoreRequested = requestedFloors.get(i);
            if (restoreRequested) {
                getExternalRequestedFloors().put(i, restoreRequested);
            }
        }
    }

    public Elevator getElevatorInFloor(int floor) {
        Elevator elevatorInFloor = null;
        for (Elevator elevator : this.elevators) {
            if (elevator.currentFloor == floor && elevator.status == ElevatorStatus.STOPPED) {

            }
        }
        return elevatorInFloor;
    }

    public synchronized HashMap<Integer, Boolean> getExternalRequestedFloors(int currentFloor, ElevatorDirection direction) {
        HashMap<Integer, Boolean> requestedFloors = new HashMap<Integer, Boolean>();
        for (int i = Configuration.MIN_FLOOR; i <= Configuration.MAX_FLOOR; i++) {
            requestedFloors.put(i, false);
        }
        int step;
        int i = currentFloor;
        int stop;

        if (direction == ElevatorDirection.UP) {
            step = 1;
            stop = Configuration.MAX_FLOOR;
        } else if (direction == ElevatorDirection.DOWN) {
            step = -1;
            stop = Configuration.MIN_FLOOR;
        } else {
            // go up by default if no direction provided
            step = 1;
            stop = Configuration.MAX_FLOOR;
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