
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Elevator extends Thread {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    String id;
    int currentFloor;
    int previousFloor;
    Semaphore spaceSemaphore = new Semaphore(Configuration.MAX_PEOPLE, true);
    JarvisSystem jarvisSystem;
    ArrayList<Person> space = new ArrayList<Person>(Configuration.MAX_PEOPLE);
    Map<Integer, Boolean> requestedFloors;
    ElevatorStatus status;
    ElevatorDirection direction;
    
    public int peopleInElevator() {
		return space.size();
    	
    }
    
    @Override
    public String toString() {
    	return "Elevator(" + this.id + ", floor=" + this.currentFloor + ", people=" + this.peopleInElevator() + 
    			", " + this.status.name() + ", " + this.direction.name() + ")";
    }

    public void initRequestedFloors() {
        this.requestedFloors = new HashMap<>();
        for (int i = Configuration.MIN_FLOOR; i <= Configuration.MAX_FLOOR; i++) {
            this.requestedFloors.put(i, false);
        }
        logger.info("requested floors initialized");
    }

    public Elevator(String id, ElevatorStatus status, JarvisSystem jarvisSystem) {
        this.id = id;
        this.jarvisSystem = jarvisSystem;
        this.currentFloor = Configuration.MIN_FLOOR;
        this.previousFloor = Configuration.MIN_FLOOR;
        this.status = status;
        this.direction = ElevatorDirection.NONE;
        this.initRequestedFloors();
    }

    public void turnOn() {
        this.status = ElevatorStatus.STOPPED;
        this.direction = ElevatorDirection.NONE;
        logger.info(this.toString() + " turned on");
    }

    public void turnOff() {
        this.status = ElevatorStatus.OFF;
        this.direction = ElevatorDirection.NONE;
        this.evacuatePeople();
        logger.info(this.toString() + " turned off");
    }

    public void broke() {
        this.status = ElevatorStatus.BROKEN;
        this.direction = ElevatorDirection.NONE;
        logger.info(this.toString() + " broke up");
    }

    public void evacuatePeople() {
    	logger.info(this.toString() + " start evacuating people");
        for (Person person : this.space) {
            person.floor = this.currentFloor;
            person.interrupt();
        }
        logger.info(this.toString() + " evacuated people");
    }

    public void move() {
        try {
            Thread.sleep((long) Configuration.MOVE_SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (this.direction == ElevatorDirection.UP) {
            this.previousFloor = this.currentFloor;
            this.currentFloor++;
        } else if (this.direction == ElevatorDirection.DOWN) {
            this.previousFloor = this.currentFloor;
            this.currentFloor--;
        }
        logger.info(this.toString() + " move to floor " + this.currentFloor);
        this.jarvisSystem.notifyFloorMove(this.currentFloor);
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
    	if (this.currentFloor < Configuration.MAX_FLOOR) {
    		remaining = this.checkRemainingRequestedFloors(this.currentFloor + 1, Configuration.MAX_FLOOR, this.requestedFloors);
    	}
    	return remaining;
         
    }

    public boolean remainingExternalRequestedUpperFloors() {
    	boolean remaining = false;
    	if (this.currentFloor < Configuration.MAX_FLOOR) {
    		remaining = this.checkRemainingRequestedFloors(this.currentFloor + 1, Configuration.MAX_FLOOR, this.jarvisSystem.getExternalRequestedFloors());
    	}
    	return remaining;
    }

    public boolean remainingRequestedLowerFloors() {
    	boolean remaining = false;
    	if (this.currentFloor > Configuration.MIN_FLOOR) {
    		remaining = this.checkRemainingRequestedFloors(this.currentFloor - 1, Configuration.MIN_FLOOR, this.requestedFloors);
    	}
    	return remaining;
    }

    public boolean remaininExternalRequestedLowerFloors() {
    	boolean remaining = false;
    	if (this.currentFloor > Configuration.MIN_FLOOR) {
    		remaining = this.checkRemainingRequestedFloors(this.currentFloor - 1, Configuration.MIN_FLOOR, this.jarvisSystem.getExternalRequestedFloors());
	    }
		return remaining;
    }

    public void moveToNextFloor() {
    	logger.info(this.toString() + " continue moving ");
        boolean internalUpperRequestedFloors = this.remainingRequestedUpperFloors();
        boolean externalUpperRequestedFloors = this.remainingExternalRequestedUpperFloors();
        boolean internalLowerRequestedFloors = this.remainingRequestedLowerFloors();
        boolean externalLowerRequestedFloors = this.remaininExternalRequestedLowerFloors();
        boolean move = false;

        if (this.currentFloor == Configuration.MIN_FLOOR) {
            this.direction = ElevatorDirection.UP;

        } else if (this.currentFloor == Configuration.MAX_FLOOR) {
            this.direction = ElevatorDirection.DOWN;

        }

        if (this.direction == ElevatorDirection.UP) {
            if (internalUpperRequestedFloors || externalUpperRequestedFloors) {
                move = true;
            } else {
                this.direction = ElevatorDirection.NONE;
            }
        }
        if (this.direction == ElevatorDirection.DOWN) {
            if (internalLowerRequestedFloors || externalLowerRequestedFloors) {
                move = true;
            } else {
                this.direction = ElevatorDirection.NONE;
            }
        }
        if (this.direction == ElevatorDirection.NONE) {
            if (internalUpperRequestedFloors || externalUpperRequestedFloors) {
                this.direction = ElevatorDirection.UP;
                move = true;
            } else {
                if (internalLowerRequestedFloors || externalLowerRequestedFloors) {
                    this.direction = ElevatorDirection.DOWN;
                    move = true;
                }
            }
        }

        if (move) {
            this.move();
        }
    }

    public void waitInFloor() {
    	logger.info(this.toString() + " arrived to floor " + this.currentFloor);
        this.status = ElevatorStatus.EXITING;
        try {
            sleep((long) 300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.status = ElevatorStatus.STOPPED;
        try {
            sleep((long) 300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopInFloor() {
        if (this.status == ElevatorStatus.BROKEN) {
            this.evacuatePeople();
            this.repair();
        }
        boolean floorInternalRequired = this.requestedFloors.get(this.currentFloor);
        boolean floorExternalRequired = this.jarvisSystem.getExternalRequestedFloors().get(this.currentFloor);
        if (floorInternalRequired || floorExternalRequired) {
            logger.info(this.toString() + " stopping in floor " + this.currentFloor);
            this.requestedFloors.put(this.currentFloor, false);
            this.jarvisSystem.getExternalRequestedFloors().put(this.currentFloor, false);
            this.jarvisSystem.notifyFloorStop(this.currentFloor);
            this.waitInFloor();
        }
    }

    public void repair() {
    	logger.info(this.toString() + " starts repairing");
        double randomTime = (Math.random() * (Configuration.MAX_REPAIR_SECONDS - Configuration.MIN_REPAIR_SECONDS +1) 
                + Configuration.MIN_REPAIR_SECONDS);

        try {
            Thread.sleep((long) randomTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.initRequestedFloors();
        this.status = ElevatorStatus.STOPPED;
        this.jarvisSystem.notifyElevatorRepaired();
        logger.info(this.toString() + " ends repairing");
    }

    public boolean enter(Person person) {
    	logger.info(this.toString() + " trying enter - " + person.toString());
        boolean inside = false;
        inside = this.spaceSemaphore.tryAcquire();
        if (inside) {
            this.space.add(person);
            logger.info(this.toString() + " inside - " + person.toString());
        }

        return inside;
    }

    public void requestFloor(int floor) {
        this.requestedFloors.put(floor, true);
    }

    public void waitFloor(Person person) {
    	logger.info(this.toString() + " waiting floor - " + person.toString());
    	boolean isInTargetFloor = false;
        try {
            while (!isInTargetFloor) {
            	isInTargetFloor = this.currentFloor == person.targetFloor;
            	person.floor = this.currentFloor;
            }
        } catch (Exception e) {
            // se evacua a la persona 
        	this.out(person);
        }

    }

    public void out(Person person) {
    	person.floor = this.currentFloor;
    	this.space.remove(person);
    	this.spaceSemaphore.release();
    	logger.info(this.toString() + " went out - " + person.toString());
    }

    @Override
    public void run() {
        while (true) {
            this.stopInFloor();
            while (this.status != ElevatorStatus.OFF) {
                this.moveToNextFloor();
                this.stopInFloor();
            }
            evacuatePeople();
        }
    }

}
