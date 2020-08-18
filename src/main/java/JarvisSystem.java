
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class JarvisSystem {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
	private static final String STATUS_FORMAT = "| %10s | %10s | %10s | %10s | %10s | %60s | %60s | %60s | \n";
	private static final String EMPTY_STR = "";
	private static final String YES_STR = "YES";
	private static final String NO_STR = "no";

    ArrayList<Elevator> elevators;
    ElevatorBackUp elevatorBackUp;
    private Map<Integer, Boolean> externalRequestedFloors;
    ElevatorBreaker elevatorBroker;
    ArrayList<JarvisRemoteControl> remotes;
    private final AtomicInteger movesCounter = new AtomicInteger(0);

    public void initElevators() {
    	this.elevators = new ArrayList<>(Configuration.JARVIS_N_ELEVATORS);
    	
    	Elevator elevator;
    	for (int i=0; i<Configuration.JARVIS_N_ELEVATORS; i++) {
    		elevator = new Elevator("elevator_" + i, ElevatorStatus.MOVE, this);
    		this.elevators.add(elevator);
    		elevator.start();
    	}
        this.elevatorBackUp = new ElevatorBackUp("elevator_bkp", ElevatorStatus.OFF, this);
        //logger.info("elevators initialized");
    }

    public void initRequestedFloors() {
        this.externalRequestedFloors = new ConcurrentHashMap<>();
        for (int i = Configuration.HOSPITAL_FLOOR_MIN; i <= Configuration.HOSPITAL_FLOOR_MAX; i++) {
            this.getExternalRequestedFloors().put(i, false);
        }
    }

    public JarvisSystem() {
    	//logger.info("initializing jarvis");
    	this.initRequestedFloors();
    	this.initElevators();
        this.remotes = new ArrayList<>(Configuration.HOSPITAL_FLOOR_MAX + 1);
        //logger.info("jarvis initialized!");
    }
    
    public synchronized void printStatus() {
    	String floor;
    	String elevator1;
    	String elevator2;
    	String elevator3;
    	String buttonPulsed;
    	String destinationElevator1;
    	String destinationElevator2;
    	String destinationElevator3;
    	
    	System.out.format(STATUS_FORMAT, "Floor", "Evt.1", "Evt.2", "Evt.3", "B.Pulsed", "Dest.Evt.1", "Dest.Evt.2", "Dest.Evt.3");
    	int nFloor = Configuration.HOSPITAL_FLOOR_MAX;
    	while (nFloor != Configuration.HOSPITAL_FLOOR_MIN-1) {
    		floor = String.valueOf(nFloor);
        	elevator1 = EMPTY_STR;
        	elevator2 = EMPTY_STR;
        	elevator3 = EMPTY_STR;
        	buttonPulsed = NO_STR;
        	destinationElevator1 = EMPTY_STR;
        	destinationElevator2 = EMPTY_STR;
        	destinationElevator3 = EMPTY_STR;
        	
        	if (isRemoteControlPulsed(nFloor)) {
    			buttonPulsed = YES_STR;
    		}
    		
    		for (Elevator elevator : this.elevators) {
                if (elevator.currentFloor == nFloor) {
                	ArrayList<String> peopleInElevator = new ArrayList<>();
                	for (Person person: elevator.space) {
                		peopleInElevator.add(person.toString());
                	}
                	String elevatorString = elevator.status.name() + "#" + elevator.direction.name() + "#" + elevator.peopleInElevator();
                	if (elevator.id.equals("elevator_0")) {
                		elevator1 = elevatorString;
                		destinationElevator1 = peopleInElevator.toString();
                	} else if (elevator.id.equals("elevator_1")) {
                		elevator2 = elevatorString;
                		destinationElevator2 = peopleInElevator.toString();
                	} else if (elevator.id.equals("elevator_bkp")) {
                		elevator3 = elevatorString;
                		destinationElevator3 = peopleInElevator.toString();
                	}
                }
            }
    		
    		System.out.format(STATUS_FORMAT, floor, elevator1, elevator2, elevator3, buttonPulsed, 
    				destinationElevator1, destinationElevator2, destinationElevator3);
        	
        	nFloor--;
    	}
    	System.out.println("\n\n--------------------------------- " + "MOVEMENT " + this.getMovesCounter() + " ----------------------------------------\n\n");
    }
    
    public boolean isRemoteControlPulsed(int n) {
    	boolean pulsed = false;
    	for (JarvisRemoteControl remote: this.remotes) {
    		if (remote.value == n) {
    			pulsed = remote.active;
    			break;
    		}
    	}
    	return pulsed;
    }
    
    public int getMovesCounter() {
        return movesCounter.get();
    }
    
    public void addMovement() {
        while(true) {
            int existingValue = getMovesCounter();
            int newValue = existingValue + 1;
            if(movesCounter.compareAndSet(existingValue, newValue)) {
            	if (getMovesCounter() == Configuration.ELEVATOR_MAX_PEOPLE) {
            		this.turnSytemOff();
            	}
            	printStatus();
                return;
            }
        }
    }
    
    public void turnSytemOff() {
    	for (Elevator elevator : this.elevators) {
    		elevator.turnEnd();
    	}
    	elevatorBackUp.turnEnd();
    }

    public void callElevator(int floor) {
    	//logger.info("called elevator from floor " + floor);
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

        if (direction == ElevatorDirection.DOWN) {
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
		//logger.info("added new remote controller " + remote.toString());
		
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
		this.addMovement();
		// TODO: print system status
	}

	public void notifyElevatorRepaired() {
		this.elevatorBackUp.turnOff();
		
	}

}