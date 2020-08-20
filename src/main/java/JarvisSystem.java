
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
	private static final String KO_STR = "KO";
	private static final String NO_STR = "no";

	private Hospital hospital;
	private ArrayList<Elevator> elevators;
	private ElevatorBackUp elevatorBackUp;
    private Map<Integer, Boolean> externalRequestedFloors;
    private ElevatorBreaker elevatorBreaker;
    private ArrayList<JarvisRemoteControl> remotes;
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
        this.elevatorBackUp.start();
        this.elevatorBreaker = new ElevatorBreaker(this.elevators, this.elevatorBackUp);
        this.elevatorBreaker.start();
        if (Configuration.LOGGING_ON) logger.info("elevators initialized");
    }

    public void initRequestedFloors() {
        this.externalRequestedFloors = new ConcurrentHashMap<>();
        for (int i = Configuration.HOSPITAL_FLOOR_MIN; i <= Configuration.HOSPITAL_FLOOR_MAX; i++) {
            this.getExternalRequestedFloors().put(i, false);
        }
    }

    public JarvisSystem(Hospital hospital) {
    	if (Configuration.LOGGING_ON) logger.info("initializing jarvis");
    	this.hospital = hospital;
    	this.initRequestedFloors();
    	this.initElevators();
        this.remotes = new ArrayList<>(Configuration.HOSPITAL_FLOOR_MAX + 1);
        if (Configuration.LOGGING_ON) logger.info("jarvis initialized!");
    }
    
    public synchronized void printStatus() {
    	String floor;
    	String elevator0;
    	String elevator1;
    	String elevator2;
    	String buttonPulsed;
    	String destinationElevator0;
    	String destinationElevator1;
    	String destinationElevator2;
    	
    	System.out.format(STATUS_FORMAT, "Floor", "Evt.0", "Evt.1", "Evt.2", "B.Pulsed", "Dest.Evt.0", "Dest.Evt.1", "Dest.Evt.2");
    	int nFloor = Configuration.HOSPITAL_FLOOR_MAX;
    	while (nFloor != Configuration.HOSPITAL_FLOOR_MIN-1) {
    		floor = String.valueOf(nFloor);
        	elevator0 = EMPTY_STR;
        	elevator1 = EMPTY_STR;
        	elevator2 = EMPTY_STR;
        	buttonPulsed = NO_STR;
        	destinationElevator0 = EMPTY_STR;
        	destinationElevator1 = EMPTY_STR;
        	destinationElevator2 = EMPTY_STR;
        	
        	if (isRemoteControlPulsed(nFloor)) {
    			buttonPulsed = YES_STR;
    		}
    		
    		for (Elevator elevator: elevators) {
                if (elevator.getCurrentFloor() == nFloor) {
                	ArrayList<String> peopleInElevator = new ArrayList<>();
                	for (Person person: elevator.getSpace()) {
                		peopleInElevator.add(person.toString());
                	}
                	String elevatorDirection = elevator.getDirection().name();
                	if (elevator.getStatus() == ElevatorStatus.BROKEN) {
                		elevatorDirection = KO_STR;
                	}
                	String elevatorString = elevatorDirection + "#" + elevator.peopleInElevator();
                	if (elevator.getIdentification().equals("elevator_0")) {
                		elevator0 = elevatorString;
                		destinationElevator0 = peopleInElevator.toString();
                	} else if (elevator.getIdentification().equals("elevator_1")) {
                		elevator1 = elevatorString;
                		destinationElevator1 = peopleInElevator.toString();
                	}
                }
            }
            
    		if (elevatorBackUp.getCurrentFloor() == nFloor) {
	    		String elevatorBackUpDirection = elevatorBackUp.getDirection().name();
	            String elevatorBackUpString = elevatorBackUpDirection + "#" + elevatorBackUp.peopleInElevator();
	            elevator2 = elevatorBackUpString;
	            ArrayList<String> peopleInElevatorBackUp = new ArrayList<>();
	    		destinationElevator2 = peopleInElevatorBackUp.toString();
    		}
    		
    		System.out.format(STATUS_FORMAT, floor, elevator0, elevator1, elevator2, buttonPulsed, 
    				destinationElevator0, destinationElevator1, destinationElevator2);
        	
        	nFloor--;
    	}
    	System.out.println("\n\n--------------------------------- " + "MOVEMENT " + getMovesCounter() + " ----------------------------------------\n\n");
    }
    
    public boolean isRemoteControlPulsed(int n) {
    	boolean pulsed = false;
    	for (JarvisRemoteControl remote: remotes) {
    		if (remote.getValue() == n) {
    			pulsed = remote.isActive();
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
            if (movesCounter.compareAndSet(existingValue, newValue)) {
            	if (getMovesCounter() == Configuration.ELEVATORS_MAX_MOVES) {
            		turnSytemOff();
            	}
            	
                return;
            }
        }
    }
    
    public void turnSytemOff() {
    	for (Elevator elevator: elevators) {
    		elevator.turnEnd();
    	}
    	elevatorBackUp.turnEnd();
    }

    public void callElevator(int floor) {
    	if (Configuration.LOGGING_ON) logger.info("called elevator from floor " + floor);
        externalRequestedFloors.put(floor, true);

    }

    public void restoreRequestedFloors(Map<Integer, Boolean> requestedFloors) {
        for (int i = Configuration.HOSPITAL_FLOOR_MIN; i <= Configuration.HOSPITAL_FLOOR_MAX; i++) {
            boolean restoreRequested = requestedFloors.get(i);
            if (restoreRequested) {
            	externalRequestedFloors.put(i, restoreRequested);
            }
        }
    }

    public ArrayList<Elevator> getElevatorsInFloor(int floor) {
        ArrayList<Elevator> elevatorsInFloor = new ArrayList<>();
        for (Elevator elevator: elevators) {
            if (elevator.getCurrentFloor() == floor && elevator.getStatus() != ElevatorStatus.BROKEN) {
            	elevatorsInFloor.add(elevator);
            }
        }
        if (elevatorBackUp.getCurrentFloor() == floor && elevatorBackUp.getStatus() != ElevatorStatus.OFF) {
        	elevatorsInFloor.add(elevatorBackUp);
        }
        
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
            boolean isRequested = externalRequestedFloors.get(i);
            requestedFloors.put(i, isRequested);
            externalRequestedFloors.put(i, false);
            i += step;
        }
        return requestedFloors;

    }

    public Map<Integer, Boolean> getExternalRequestedFloors() {
        return externalRequestedFloors;
    }

	public void configureRemote(JarvisRemoteControl remote) {
		remotes.add(remote);
		if (Configuration.LOGGING_ON) logger.info("added new remote controller " + remote.toString());
		
	}

	public void notifyFloorStop(int nFloor) {
		for (JarvisRemoteControl remote: remotes) {
			if (remote.getValue() == nFloor) {
				remote.notifyElevatorArriving();
			}
		}
	}

	public void notifyFloorMove(int floor) {
		for (JarvisRemoteControl remote: this.remotes) {
			if (remote.getValue() == floor) {
				remote.notifyElevatorLeaving();
			}
		}
		this.addMovement();
		printStatus();
	}

	public synchronized  void notifyElevatorRepaired() {
		this.elevatorBackUp.turnOff();
	}

	public void notifyBreak() {
		this.elevatorBackUp.turnOn();
	}

	public HospitalFloor getHospitalFloor(int nFloor) {
		return hospital.getFloor(nFloor);
	}


}