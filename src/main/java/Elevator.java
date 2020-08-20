
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Elevator extends Thread {

    private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
    private static final int EXITING_MS = 300;
    private static final int STOPPED_MS = 300;

    private String identification;
    private int currentFloor;
    private int previousFloor;
    private Semaphore spaceSemaphore = new Semaphore(Configuration.ELEVATOR_MAX_PEOPLE, true);
    private JarvisSystem jarvisSystem;
    private ArrayList<Person> space = new ArrayList<>(Configuration.ELEVATOR_MAX_PEOPLE);
    private Map<Integer, Boolean> requestedFloors;
    private ElevatorStatus status;
    private ElevatorDirection direction;
    

    public void initRequestedFloors() {
        this.requestedFloors = new HashMap<>();
        for (int i = Configuration.HOSPITAL_FLOOR_MIN; i <= Configuration.HOSPITAL_FLOOR_MAX; i++) {
            this.requestedFloors.put(i, false);
        }
        if (Configuration.LOGGING_ON) logger.info("requested floors initialized");
    }

    public Elevator(String identification, ElevatorStatus status, JarvisSystem jarvisSystem) {
        this.identification = identification;
        this.jarvisSystem = jarvisSystem;
        this.currentFloor = Configuration.HOSPITAL_FLOOR_MIN;
        this.previousFloor = Configuration.HOSPITAL_FLOOR_MIN;
        this.status = status;
        this.direction = ElevatorDirection.NONE;
        this.initRequestedFloors();
    }
    
    public int peopleInElevator() {
        return space.size();

    }
    
    @Override
    public String toString() {
        return "Elevator(" + identification + ", floor=" + currentFloor + ", people=" + peopleInElevator()
                + ", " + status.name() + ", " + direction.name() + ")";
    }

    public void turnOn() {
    	if (Configuration.LOGGING_ON) logger.info(toString() + " turning on");
    	status = ElevatorStatus.STOPPED;
        direction = ElevatorDirection.NONE;
        
    }

    public void turnOff() {
    	if (Configuration.LOGGING_ON) logger.info(toString() + " turning off");
    	status = ElevatorStatus.OFF;
    	direction = ElevatorDirection.NONE;
        evacuatePeople();
        
    }
    
    public void turnEnd() {
    	if (Configuration.LOGGING_ON) logger.info(toString() + " turning end");
    	status = ElevatorStatus.END;
    	direction = ElevatorDirection.NONE;
        evacuatePeople();
        
    }

    public void broke() {
    	if (Configuration.LOGGING_ON) logger.info(toString() + " breaking up");
    	status = ElevatorStatus.BROKEN;
    	direction = ElevatorDirection.NONE;
        jarvisSystem.notifyBreak();
        
    }

    public synchronized void evacuatePeople() {
    	if (Configuration.LOGGING_ON) logger.info(toString() + " start evacuating people");
        
    	for (Person person: space) {
            person.setFloor(currentFloor);
            person.interrupt();
        }
    	
        if (Configuration.LOGGING_ON) logger.info(toString() + " evacuated people");
    }

    public void move() {
        status = ElevatorStatus.MOVE;
        if (Configuration.LOGGING_ON) logger.info(toString() + " continue moving ");
        
        try {
            Thread.sleep((long) Configuration.ELEVATOR_MOVE_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (direction == ElevatorDirection.UP) {
            previousFloor = currentFloor;
            currentFloor++;
        } else if (direction == ElevatorDirection.DOWN) {
        	previousFloor = currentFloor;
        	currentFloor--;
        }
        
        if (Configuration.LOGGING_ON) logger.info(toString() + " move to floor " + currentFloor);
        jarvisSystem.notifyFloorMove(currentFloor);
    }

    public boolean checkRemainingRequestedFloors(int from, int to, Map<Integer, Boolean> map) {
        boolean remaining = false;
        int i = from;
        int step = 1;
        int stop = to + 1;
        if (from > to) {
            step = -1;
            stop = to - 1;
        }
        while (!remaining && i != stop) {
            remaining = map.get(i);
            i += step;
        }
        return remaining;
    }

    public boolean remainingRequestedUpperFloors() {
        boolean remaining = false;
        if (currentFloor < Configuration.HOSPITAL_FLOOR_MAX) {
            remaining = checkRemainingRequestedFloors(currentFloor + 1, Configuration.HOSPITAL_FLOOR_MAX, requestedFloors);
        }
        return remaining;

    }

    public boolean remainingExternalRequestedUpperFloors() {
        boolean remaining = false;
        if (currentFloor < Configuration.HOSPITAL_FLOOR_MAX) {
            remaining = checkRemainingRequestedFloors(currentFloor + 1, Configuration.HOSPITAL_FLOOR_MAX, jarvisSystem.getExternalRequestedFloors());
        }
        return remaining;
    }

    public boolean remainingRequestedLowerFloors() {
        boolean remaining = false;
        if (currentFloor > Configuration.HOSPITAL_FLOOR_MIN) {
            remaining = checkRemainingRequestedFloors(currentFloor - 1, Configuration.HOSPITAL_FLOOR_MIN, requestedFloors);
        }
        return remaining;
    }

    public boolean remaininExternalRequestedLowerFloors() {
        boolean remaining = false;
        if (currentFloor > Configuration.HOSPITAL_FLOOR_MIN) {
            remaining = checkRemainingRequestedFloors(currentFloor - 1, Configuration.HOSPITAL_FLOOR_MIN, jarvisSystem.getExternalRequestedFloors());
        }
        return remaining;
    }

    public void moveToNextFloor() {
        boolean internalUpperRequestedFloors = remainingRequestedUpperFloors();
        boolean externalUpperRequestedFloors = remainingExternalRequestedUpperFloors();
        boolean internalLowerRequestedFloors = remainingRequestedLowerFloors();
        boolean externalLowerRequestedFloors = remaininExternalRequestedLowerFloors();
        boolean move = false;

        if (currentFloor == Configuration.HOSPITAL_FLOOR_MIN) {
            direction = ElevatorDirection.UP;

        } else if (currentFloor == Configuration.HOSPITAL_FLOOR_MAX) {
            direction = ElevatorDirection.DOWN;

        }

        if (direction == ElevatorDirection.UP) {
            if (internalUpperRequestedFloors || externalUpperRequestedFloors) {
                move = true;
            } else {
                direction = ElevatorDirection.NONE;
            }
        }
        if (direction == ElevatorDirection.DOWN) {
            if (internalLowerRequestedFloors || externalLowerRequestedFloors) {
                move = true;
            } else {
                direction = ElevatorDirection.NONE;
            }
        }
        if (direction == ElevatorDirection.NONE) {
            if (internalUpperRequestedFloors || externalUpperRequestedFloors) {
                direction = ElevatorDirection.UP;
                move = true;
            } else {
                if (internalLowerRequestedFloors || externalLowerRequestedFloors) {
                    direction = ElevatorDirection.DOWN;
                    move = true;
                }
            }
        }

        if (move) {
            move();
        }
        
        if (currentFloor == Configuration.HOSPITAL_FLOOR_MAX) {
        	direction = ElevatorDirection.DOWN;
        } else if (currentFloor == Configuration.HOSPITAL_FLOOR_MIN) {
        	direction = ElevatorDirection.UP;
        }
    }

    public void waitInFloor() {
    	if (Configuration.LOGGING_ON) logger.info(toString() + " arrived to floor " + currentFloor);
        status = ElevatorStatus.EXITING;
        try {
            sleep((long) EXITING_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        status = ElevatorStatus.STOPPED;
        try {
            sleep((long) STOPPED_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopInFloor() {
        if (status == ElevatorStatus.BROKEN) {
            evacuatePeople();
            repair();
        } else {
	        boolean floorInternalRequired = requestedFloors.get(currentFloor);
	        boolean floorExternalRequired = jarvisSystem.getExternalRequestedFloors().get(currentFloor);
	        if (floorInternalRequired || floorExternalRequired) {
	        	if (Configuration.LOGGING_ON) logger.info(toString() + " stopping in floor " + currentFloor);
	            requestedFloors.put(currentFloor, false);
	            jarvisSystem.getExternalRequestedFloors().put(currentFloor, false);
	            jarvisSystem.notifyFloorStop(currentFloor);
	            waitInFloor();
	        }
        }
    }

    public void repair() {
    	if (Configuration.LOGGING_ON) logger.info(toString() + " starts repairing");
        double randomTime = (Math.random() * (Configuration.ELEVATOR_REPAIR_MAX_MS - Configuration.ELEVATOR_REPAIR_MIN_MS + 1)
                + Configuration.ELEVATOR_REPAIR_MIN_MS);

        try {
            Thread.sleep((long) randomTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initRequestedFloors();
        status = ElevatorStatus.STOPPED;
        jarvisSystem.notifyElevatorRepaired();
        if (Configuration.LOGGING_ON) logger.info(toString() + " ends repairing");
    }

    public boolean enter(Person person) {
    	if (Configuration.LOGGING_ON) logger.info(toString() + " trying enter - " + person.toString());
        boolean inside = false;
        inside = spaceSemaphore.tryAcquire();
        if (inside) {
            space.add(person);
            if (Configuration.LOGGING_ON) logger.info(toString() + " inside - " + person.toString());
            requestFloor(person.getTargetFloor());
        }

        return inside;
    }

    public void requestFloor(int floor) {
        requestedFloors.put(floor, true);
    }

    public void waitFloor(Person person) {
    	if (Configuration.LOGGING_ON) logger.info(toString() + " waiting floor - " + person.toString());
        boolean isInTargetFloor = false;
        try {
            while (!isInTargetFloor) {
                Thread.sleep(5);
                isInTargetFloor = currentFloor == person.getTargetFloor();
                person.setFloor(currentFloor);
            }
        } catch (Exception e) {
            // se evacua a la persona 
        	System.out.println("Evacuando a la persona: " + person.toString());
            out(person);
        }

    }

    public void out(Person person) {
        person.setFloor(currentFloor);
        person.setHospitalFloor(jarvisSystem.getHospitalFloor(currentFloor));
        space.remove(person);
        spaceSemaphore.release();
        if (Configuration.LOGGING_ON) logger.info(toString() + " went out - " + person.toString());
    }

    @Override
    public void run() {
    	if (status != ElevatorStatus.OFF) {
    		stopInFloor();
    	}
        while (status != ElevatorStatus.END) {
        	
            while (status != ElevatorStatus.OFF) {
                moveToNextFloor();
                stopInFloor();
                
            } 
            if (peopleInElevator() != 0) {
            	evacuatePeople();
            }
        }
        evacuatePeople();
    }
    
    public String getIdentification() {
		return identification;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public ArrayList<Person> getSpace() {
		return space;
	}

	public ElevatorStatus getStatus() {
		return status;
	}

	public ElevatorDirection getDirection() {
		return direction;
	}


}
