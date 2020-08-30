package hospital;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class JarvisRemoteControl {

    private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    private JarvisSystem jarvisSystem;
    private int value;
    private boolean active; // condition
    private boolean elevatorInFloor;
    private final Lock lock;
    private final Condition elevatorInFloorCondition;
    private int peopleWaiting;

    public JarvisRemoteControl(JarvisSystem jarvisSystem, int value) {
        this.jarvisSystem = jarvisSystem;
        this.active = false;
        this.elevatorInFloor = false;
        this.lock = new ReentrantLock();
        this.elevatorInFloorCondition = lock.newCondition();
        this.value = value;
        this.jarvisSystem.configureRemote(this);
        this.peopleWaiting = 0;
        if (Configuration.LOGGING_ON) {
        	logger.info(toString() + " initialized and configured");
        }
    }

    @Override
    public String toString() {
        return "JarvisRemote(" + this.value + ")";
    }

    private void waitForElevator() {
        try {
            lock.lock();
            this.peopleWaiting++;
            while (!elevatorInFloor) {
                try {
                    elevatorInFloorCondition.await();
                } catch (Exception e) {
                }
            }
            // end waiting - person continues with execution
            this.peopleWaiting = 0;
        } finally {
            lock.unlock();
        }
    }

    public void callElevator() {
        if (!this.active) {
            this.jarvisSystem.callElevator(this.value);
            this.active = true;
        }

        waitForElevator();
    }

    public synchronized void notifyElevatorArriving() {
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " elevator arrives - wake up people waiting");
        }
        try {
            lock.lock();
            this.elevatorInFloor = true;
            elevatorInFloorCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void notifyElevatorLeaving() {
        this.elevatorInFloor = false;
        this.active = false;
        this.peopleWaiting = 0;

    }

    public ArrayList<Elevator> getElevatorsInFloor(int floor) {
        return this.jarvisSystem.getElevatorsInFloor(floor);
    }

    public JarvisSystem getJarvisSystem() {
        return jarvisSystem;
    }

    public void setJarvisSystem(JarvisSystem jarvisSystem) {
        this.jarvisSystem = jarvisSystem;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isElevatorInFloor() {
        return elevatorInFloor;
    }

    public void setElevatorInFloor(boolean elevatorInFloor) {
        this.elevatorInFloor = elevatorInFloor;
    }

    public static Logger getLogger() {
        return logger;
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getElevatorInFloorCondition() {
        return elevatorInFloorCondition;
    }

    public int getPeopleWaiting() {
        return peopleWaiting;
    }

}
