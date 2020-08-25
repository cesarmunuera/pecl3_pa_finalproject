package hospital;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class JarvisSystem {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
	
	private Hospital hospital;
	private static final String STATUS_FORMAT = "| %5s | %8s | %8s | %8s | %8s | %60s | %60s | %60s | \n";
	private static final String EMPTY_STR = "";
	private static final String YES_STR = "YES";
	private static final String NO_STR = "no";

    ArrayList<Elevator> elevators;
    ElevatorBackUp elevatorBackUp;
    private ConcurrentHashMap<Integer, Boolean> externalRequestedFloors;
    ElevatorBreaker elevatorBreaker;
    ArrayList<JarvisRemoteControl> remotes;
    private final AtomicInteger movesCounter;
    private boolean evacuating;

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
        this.movesCounter = new AtomicInteger(0);
        this.evacuating = false;
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
    		
    		for (Elevator elevator : this.elevators) {
                if (elevator.currentFloor == nFloor) {
                	ArrayList<String> peopleInElevator = new ArrayList<>();
                	for (Person person: elevator.getSpace()) {
                		peopleInElevator.add(person.toString());
                	}
                	String elevatorDirection = elevator.direction.name();
                	if (elevator.status == ElevatorStatus.BROKEN) {
                		elevatorDirection = "KO";
                	}
                	String elevatorString = elevatorDirection + "#" + elevator.peopleInElevator();
                	if (elevator.identification.equals("elevator_0")) {
                		elevator0 = elevatorString;
                		destinationElevator0 = peopleInElevator.toString();
                	} else if (elevator.identification.equals("elevator_1")) {
                		elevator1 = elevatorString;
                		destinationElevator1 = peopleInElevator.toString();
                	}
                }
            }
            
    		if (this.elevatorBackUp.currentFloor == nFloor) {
	    		String elevatorBackUpDirection = this.elevatorBackUp.direction.name();
	            String elevatorBackUpString = elevatorBackUpDirection + "#" + this.elevatorBackUp.peopleInElevator();
	            elevator2 = elevatorBackUpString;
	            ArrayList<String> peopleInElevatorBackUp = new ArrayList<>();
	            for (Person person: elevatorBackUp.getSpace()) {
	            	peopleInElevatorBackUp.add(person.toString());
            	}
	    		destinationElevator2 = peopleInElevatorBackUp.toString();
    		}
    		
    		System.out.format(STATUS_FORMAT, floor, elevator0, elevator1, elevator2, buttonPulsed, 
    				destinationElevator0, destinationElevator1, destinationElevator2);
        	
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
            if (movesCounter.compareAndSet(existingValue, newValue)) {
            	if (getMovesCounter() == Configuration.ELEVATORS_MAX_MOVES) {
            		this.turnSytemOff();
            	}
            	
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
    
    public void startEvacuation() {
    	if (!this.evacuating) {
    		this.evacuating = true;
	    	for (Elevator elevator : this.elevators) {
	    		elevator.forceOutPeople(true);
	    	}
	    	elevatorBackUp.forceOutPeople(true);
	    	elevatorBackUp.turnEnd();
    	}
    }

    public void callElevator(int floor) {
    	if (Configuration.LOGGING_ON) logger.info("called elevator from floor " + floor);
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
        if (this.elevatorBackUp.currentFloor == floor && this.elevatorBackUp.status != ElevatorStatus.OFF) {
        	elevatorsInFloor.add(this.elevatorBackUp);
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

	public void configureRemote(JarvisRemoteControl remote) {
		remotes.add(remote);
		if (Configuration.LOGGING_ON) logger.info("added new remote controller " + remote.toString());
		
	}

	public void notifyFloorStop(int floor) {
		this.externalRequestedFloors.put(floor, false);
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
		printStatus();
	}

	public synchronized void notifyElevatorRepaired() {
		this.elevatorBackUp.turnOff();
		
	}

	public void notifyBreak() {
		//System.out.println("Jarvis encendiendo el elevador backup");
		this.elevatorBackUp.turnOn();
		
	}

	public synchronized ArrayList<Elevator> getElevators() {
		return elevators;
	}

	public void setElevators(ArrayList<Elevator> elevators) {
		this.elevators = elevators;
	}

	public ElevatorBackUp getElevatorBackUp() {
		return elevatorBackUp;
	}

	public void setElevatorBackUp(ElevatorBackUp elevatorBackUp) {
		this.elevatorBackUp = elevatorBackUp;
	}

	public ElevatorBreaker getElevatorBreaker() {
		return elevatorBreaker;
	}

	public void setElevatorBreaker(ElevatorBreaker elevatorBreaker) {
		this.elevatorBreaker = elevatorBreaker;
	}

	public ArrayList<JarvisRemoteControl> getRemotes() {
		return remotes;
	}

	public void setRemotes(ArrayList<JarvisRemoteControl> remotes) {
		this.remotes = remotes;
	}

	public static Logger getLogger() {
		return logger;
	}

	public HospitalFloor getHospitalFloor(int nFloor) {
		return this.hospital.getFloor(nFloor);
	}

	public boolean isEvacuating() {
		return evacuating;
	}
	
	
	

}