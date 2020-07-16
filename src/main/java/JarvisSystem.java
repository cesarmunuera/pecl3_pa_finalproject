

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JarvisSystem {
	
	ArrayList<Elevator> elevators;
	HashMap<Integer, Boolean> externalRequestedFloors;
	ElevatorBroker elevatorBroker;
	
	public JarvisSystem() {
		this.externalRequestedFloors = new HashMap<>();
		for (int i=Configuration.MIN_FLOOR; i<=Configuration.MAX_FLOOR; i++) {
			this.externalRequestedFloors.put(i, false);
		}
	}

	public void callElevator(int floor) {
		this.externalRequestedFloors.put(floor, true);
		
	}
	
	public void restoreRequestedFloors(Map<Integer, Boolean> requestedFloors) {
		for (int i=Configuration.MIN_FLOOR; i<=Configuration.MAX_FLOOR; i++) {
			boolean restoreRequested = requestedFloors.get(i);
			if (restoreRequested) {
				externalRequestedFloors.put(i, restoreRequested);
			}
		}
	}

	public Elevator getElevatorInFloor(int floor) {
		Elevator elevatorInFloor = null;
		for (Elevator elevator: this.elevators) {
			if (elevator.currentFloor == floor && elevator.status == ElevatorStatus.STOPPED) {
				
			}
		}
		return elevatorInFloor;
	}

	public synchronized HashMap<Integer, Boolean> getExternalRequestedFloors(int currentFloor, ElevatorDirection direction) {
		HashMap<Integer, Boolean> requestedFloors = new HashMap<Integer, Boolean>();
		for (int i=Configuration.MIN_FLOOR; i<=Configuration.MAX_FLOOR; i++) {
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
		
		while (i!=stop) {
			boolean isRequested = this.externalRequestedFloors.get(i);
			requestedFloors.put(i, isRequested);
			this.externalRequestedFloors.put(i, false);
			i += step;
		}
		return requestedFloors;
		
	}

}
