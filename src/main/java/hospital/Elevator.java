package hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Elevator extends Thread {

    private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
    private static final int EXITING_MS = 500;
    private static final int STOPPED_MS = 500;

    String identification;
    int currentFloor;
    int previousFloor;
    Semaphore spaceSemaphore;
    JarvisSystem jarvisSystem;
    ArrayList<Person> space;
    Map<Integer, Boolean> requestedFloors;
    ElevatorStatus status;
    ElevatorDirection direction;
    boolean evacuating;

    public void initRequestedFloors() {
        this.requestedFloors = new HashMap<>();
        for (int i = Configuration.HOSPITAL_FLOOR_MIN; i <= Configuration.HOSPITAL_FLOOR_MAX; i++) {
            this.requestedFloors.put(i, false);
        }
        if (Configuration.LOGGING_ON) {
            logger.info("requested floors initialized");
        }
    }

    public Elevator(String identification, ElevatorStatus status, JarvisSystem jarvisSystem) {
        this.identification = identification;
        this.jarvisSystem = jarvisSystem;
        this.currentFloor = Configuration.HOSPITAL_FLOOR_MIN;
        this.previousFloor = Configuration.HOSPITAL_FLOOR_MIN;
        this.spaceSemaphore = new Semaphore(Configuration.ELEVATOR_MAX_PEOPLE, true);
        this.space = new ArrayList<Person>(Configuration.ELEVATOR_MAX_PEOPLE);
        this.status = status;
        this.direction = ElevatorDirection.NONE;
        this.evacuating = false;
        this.initRequestedFloors();
    }

    @Override
    public String toString() {
        return "Elevator(" + this.identification + ", floor = " + this.currentFloor + ", people = " + this.peopleInElevator()
                + ", " + this.status.name() + ", " + this.direction.name() + ")";
    }

    public int peopleInElevator() {
        return space.size();

    }

    public void turnOn() {
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " turning on");
        }
        this.status = ElevatorStatus.STOPPED;
        this.direction = ElevatorDirection.NONE;
        System.out.println(this.toString() + " turning on");

    }

    public void turnOff() {
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " turning off");
        }

        this.status = ElevatorStatus.OFF;
        this.direction = ElevatorDirection.NONE;
        forceOutPeople();

    }

    public void turnEnd() {
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " turning end");
        }
        this.status = ElevatorStatus.END;
        this.direction = ElevatorDirection.NONE;
        this.forceOutPeople();

    }

    public void broke() {
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " breaking up");
        }
        System.out.println(this.toString() + " breaking up");
        this.status = ElevatorStatus.BROKEN;
        this.direction = ElevatorDirection.NONE;
        this.jarvisSystem.notifyBreak();

    }

    public synchronized void forceOutPeople() {
        forceOutPeople(false);
    }

    public synchronized void forceOutPeople(boolean evacuateSystem) {
        if (evacuateSystem) {
            this.evacuating = true;
        }
        if (peopleInElevator() > 0) {
            if (Configuration.LOGGING_ON) {
                logger.info(this.toString() + " start evacuating people");
            }
            for (Person person : this.space) {
                //System.out.println(toString() + " : intentando evacuar a " + person.toString());
                person.setFloor(this.currentFloor);
                if (this.evacuating) {
                    person.evacuate();
                }
                person.interrupt();
            }

            if (Configuration.LOGGING_ON) {
                logger.info(this.toString() + " evacuated people");
            }
        }
    }

    public void move() throws InterruptedException {
        this.status = ElevatorStatus.MOVE;
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " continue moving ");
        }

        sleep((long) Configuration.ELEVATOR_MOVE_MS);
        if (this.direction == ElevatorDirection.UP) {
            this.previousFloor = this.currentFloor;
            this.currentFloor++;
        } else if (this.direction == ElevatorDirection.DOWN) {
            this.previousFloor = this.currentFloor;
            this.currentFloor--;
        }

        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " move to floor " + this.currentFloor);
        }
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
        if (this.currentFloor < Configuration.HOSPITAL_FLOOR_MAX) {
            remaining = this.checkRemainingRequestedFloors(this.currentFloor + 1, Configuration.HOSPITAL_FLOOR_MAX, this.requestedFloors);
        }
        return remaining;

    }

    public boolean remainingExternalRequestedUpperFloors() {
        boolean remaining = false;
        if (this.currentFloor < Configuration.HOSPITAL_FLOOR_MAX) {
            remaining = this.checkRemainingRequestedFloors(this.currentFloor + 1, Configuration.HOSPITAL_FLOOR_MAX, this.jarvisSystem.getExternalRequestedFloors());
        }
        return remaining;
    }

    public boolean remainingRequestedLowerFloors() {
        boolean remaining = false;
        if (this.currentFloor > Configuration.HOSPITAL_FLOOR_MIN) {
            remaining = this.checkRemainingRequestedFloors(this.currentFloor - 1, Configuration.HOSPITAL_FLOOR_MIN, this.requestedFloors);
        }
        return remaining;
    }

    public boolean remaininExternalRequestedLowerFloors() {
        boolean remaining = false;
        if (this.currentFloor > Configuration.HOSPITAL_FLOOR_MIN) {
            remaining = this.checkRemainingRequestedFloors(this.currentFloor - 1, Configuration.HOSPITAL_FLOOR_MIN, this.jarvisSystem.getExternalRequestedFloors());
        }
        return remaining;
    }

    public void moveToNextFloor() throws InterruptedException {
        if (this.status != ElevatorStatus.OFF) {
            boolean internalUpperRequestedFloors = remainingRequestedUpperFloors();
            boolean externalUpperRequestedFloors = remainingExternalRequestedUpperFloors();
            boolean internalLowerRequestedFloors = remainingRequestedLowerFloors();
            boolean externalLowerRequestedFloors = remaininExternalRequestedLowerFloors();
            boolean move = false;

            if (this.currentFloor == Configuration.HOSPITAL_FLOOR_MIN) {
                this.direction = ElevatorDirection.UP;

            } else if (this.currentFloor == Configuration.HOSPITAL_FLOOR_MAX) {
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

            if (this.currentFloor == Configuration.HOSPITAL_FLOOR_MAX) {
                this.direction = ElevatorDirection.NONE;
            } else if (this.currentFloor == Configuration.HOSPITAL_FLOOR_MIN) {
                this.direction = ElevatorDirection.NONE;
            }
        }
    }

    public void waitInFloor() throws InterruptedException {
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " arrived to floor " + this.currentFloor);
        }
        this.status = ElevatorStatus.EXITING;
        sleep((long) EXITING_MS);
        this.status = ElevatorStatus.STOPPED;
        sleep((long) STOPPED_MS);
    }

    public void stopInFloor() throws InterruptedException {
        if (this.status != ElevatorStatus.OFF) {
            boolean floorInternalRequired = this.requestedFloors.get(this.currentFloor);
            boolean floorExternalRequired = this.jarvisSystem.getExternalRequestedFloors().get(this.currentFloor);
            if (floorInternalRequired || floorExternalRequired) {
                if (Configuration.LOGGING_ON) {
                    logger.info(this.toString() + " stopping in floor " + this.currentFloor);
                }
                this.jarvisSystem.notifyFloorStop(this.currentFloor);
                this.waitInFloor();
                this.requestedFloors.put(this.currentFloor, false);
                this.jarvisSystem.getExternalRequestedFloors().put(this.currentFloor, false);
            }
        }
    }

    public void repair() {
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " starts repairing");
        }
        double randomTime = (Math.random() * (Configuration.ELEVATOR_REPAIR_MAX_MS - Configuration.ELEVATOR_REPAIR_MIN_MS + 1)
                + Configuration.ELEVATOR_REPAIR_MIN_MS);

        try {
            Thread.sleep((long) randomTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.initRequestedFloors();
        this.status = ElevatorStatus.STOPPED;
        this.jarvisSystem.notifyElevatorRepaired();
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " ends repairing");
        }
    }

    public boolean enter(Person person) {
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " trying enter - " + person.toString());
        }
        boolean inside = false;
        inside = this.spaceSemaphore.tryAcquire();
        if (inside) {
            this.space.add(person);
            if (Configuration.LOGGING_ON) {
                logger.info(this.toString() + " inside - " + person.toString());
            }
            this.requestFloor(person.targetFloor);
        }

        return inside;
    }

    public void requestFloor(int floor) {
        this.requestedFloors.put(floor, true);
    }

    public synchronized void out(Person person) {
        person.setHospitalFloor(this.jarvisSystem.getHospitalFloor(this.currentFloor));
        person.setFloor(this.currentFloor);
        this.spaceSemaphore.release();
        this.space.remove(person);
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " went out - " + person.toString());
        }
    }

    @Override
    public void run() {
        if (this.status != ElevatorStatus.OFF) {
            try {
                stopInFloor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (this.status != ElevatorStatus.END) {
            while (this.status != ElevatorStatus.OFF) {
                try {
                    moveToNextFloor();
                    stopInFloor();
                } catch (InterruptedException e) {
                    broke();
                    //System.out.println(toString() + ": evacuating people");
                    forceOutPeople();
                    repair();
                }

            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        forceOutPeople();
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String id) {
        this.identification = id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getPreviousFloor() {
        return previousFloor;
    }

    public void setPreviousFloor(int previousFloor) {
        this.previousFloor = previousFloor;
    }

    public Semaphore getSpaceSemaphore() {
        return spaceSemaphore;
    }

    public void setSpaceSemaphore(Semaphore spaceSemaphore) {
        this.spaceSemaphore = spaceSemaphore;
    }

    public JarvisSystem getJarvisSystem() {
        return jarvisSystem;
    }

    public void setJarvisSystem(JarvisSystem jarvisSystem) {
        this.jarvisSystem = jarvisSystem;
    }

    public synchronized ArrayList<Person> getSpace() {
        return space;
    }

    public void setSpace(ArrayList<Person> space) {
        this.space = space;
    }

    public Map<Integer, Boolean> getRequestedFloors() {
        return requestedFloors;
    }

    public void setRequestedFloors(Map<Integer, Boolean> requestedFloors) {
        this.requestedFloors = requestedFloors;
    }

    public ElevatorStatus getStatus() {
        return status;
    }

    public void setStatus(ElevatorStatus status) {
        this.status = status;
    }

    public ElevatorDirection getDirection() {
        return direction;
    }

    public void setDirection(ElevatorDirection direction) {
        this.direction = direction;
    }

    public static Logger getLogger() {
        return logger;
    }

    public HospitalFloor getHospitalFloor(int nFloor) {
        return this.jarvisSystem.getHospitalFloor(nFloor);
    }

    public boolean isEvacuating() {
        return evacuating;
    }

    public void setEvacuating(boolean evacuating) {
        this.evacuating = evacuating;
    }

}
