
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JarvisSystem {

    ArrayList<Elevator> elevators;
    ElevatorBackUp elevatorBackUp = new ElevatorBackUp("elevatorBackUp", ElevatorStatus.OFF);
    private Map<Integer, Boolean> externalRequestedFloors;
    ElevatorBreaker elevatorBroker;

    public void initElevators() {
        Elevator elevator1 = new Elevator("elevator1", ElevatorStatus.MOVE);
        Elevator elevator2 = new Elevator("elevator2", ElevatorStatus.MOVE);
        //Elevator elevator3 = new Elevator("elevator3", ElevatorStatus.MOVE);
        elevators.add(elevator1);
        elevators.add(elevator2);
        //elevators.add(elevator3);
    }

    public void initRequestedFloors() {
        this.setExternalRequestedFloors(new ConcurrentHashMap<>());
        for (int i = Configuration.MIN_FLOOR; i <= Configuration.MAX_FLOOR; i++) {
            this.getExternalRequestedFloors().put(i, false);
        }
    }

    public JarvisSystem() {
        this.initElevators();
        this.initRequestedFloors();
    }

    public void callElevator(int floor) {
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

}