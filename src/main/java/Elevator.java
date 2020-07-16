

import java.util.ArrayList;
import java.util.HashMap;

public class Elevator extends Thread {
	
	
	int currentFloor;
	int previousFloor;
	int nextFloor;
	int nextFloorStop;
	Object spaceSemaphore;
	JarvisSystem jarvisSystem = new JarvisSystem();
	ArrayList<Person> space = new ArrayList<Person>(Configuration.MAX_PEOPLE);
	HashMap<Integer, Boolean> requestedFloors;
	HashMap<Integer, Boolean> externalRequestedFloors;
	ElevatorStatus status;
	ElevatorDirection direction;
	
	public Elevator() {
		this.currentFloor = Configuration.MIN_FLOOR;
		int previousFloor = Configuration.MIN_FLOOR;
		int nextFloor = Configuration.MIN_FLOOR;
		int nextFloorStop = Configuration.MIN_FLOOR;
		this.status = ElevatorStatus.STOPPED;
		this.direction = ElevatorDirection.UP;
		this.requestedFloors = new HashMap<>();
		for (int i=Configuration.MIN_FLOOR; i<=Configuration.MAX_FLOOR; i++) {
			this.requestedFloors.put(i, false);
		}
		
		this.externalRequestedFloors = new HashMap<>();
		for (int i=Configuration.MIN_FLOOR; i<=Configuration.MAX_FLOOR; i++) {
			this.externalRequestedFloors.put(i, false);
		}
		
	}
	
	public boolean remainingRequestedUpperFloors() {
		boolean remaining = false;
		int i = this.currentFloor+1;
		while (!remaining && i<Configuration.MAX_FLOOR) {
			boolean isRequested = this.requestedFloors.get(i);
			if (isRequested) {
				remaining = true;
			}
			i++;
		}
		return remaining;
	}
	
	public boolean remainingExternalRequestedUpperFloors() {
		boolean remaining = false;
		int i = this.currentFloor+1;
		while (!remaining && i<=Configuration.MAX_FLOOR) {
			boolean isRequested = this.externalRequestedFloors.get(i);
			if (isRequested) {
				remaining = true;
			}
			i++;
		}
		return remaining;
	}
	
	public boolean remainingRequestedLowerFloors() {
		boolean remaining = false;
		int i = Configuration.MIN_FLOOR;
		while (!remaining && i<this.currentFloor) {
			boolean isRequested = this.requestedFloors.get(i);
			if (isRequested) {
				remaining = true;
			}
			i++;
		}
		return remaining;
	}
	
	public boolean remaininExternalRequestedLowerFloors() {
		boolean remaining = false;
		int i = Configuration.MIN_FLOOR;
		while (!remaining && i<this.currentFloor) {
			boolean isRequested = this.externalRequestedFloors.get(i);
			if (isRequested) {
				remaining = true;
			}
			i++;
		}
		return remaining;
	}
	
	public void refreshExternalRequestedFloors(ElevatorDirection direction) {
		this.externalRequestedFloors = this.jarvisSystem.getExternalRequestedFloors(this.currentFloor, direction);
	
	}
	
	public void moveToNextFloor() {
		boolean internalRequestedFloors = false;
		boolean externalRequestedFloors = false;
		boolean move = false;
		
		if (this.currentFloor == Configuration.MIN_FLOOR) {
			this.direction = ElevatorDirection.UP;
			
		} else if (this.currentFloor == Configuration.MAX_FLOOR) {
			this.direction = ElevatorDirection.DOWN;
			
		} 

		if (this.direction == ElevatorDirection.UP) {
			refreshExternalRequestedFloors(this.direction);
			internalRequestedFloors = this.remainingRequestedUpperFloors();
			externalRequestedFloors = this.remainingExternalRequestedUpperFloors();
			if (internalRequestedFloors || externalRequestedFloors) {
				move = true;
			} else {
				this.direction = ElevatorDirection.NONE;
			}
		}
		if (this.direction == ElevatorDirection.DOWN) {
			refreshExternalRequestedFloors(this.direction);
			internalRequestedFloors = this.remainingRequestedLowerFloors();
			externalRequestedFloors = this.remaininExternalRequestedLowerFloors();
			if (internalRequestedFloors || externalRequestedFloors) {
				move = true;
			} else {
				this.direction = ElevatorDirection.NONE;
			}
		} 
		else {
			refreshExternalRequestedFloors(ElevatorDirection.UP);
			internalRequestedFloors = this.remainingRequestedUpperFloors();
			externalRequestedFloors = this.remainingExternalRequestedUpperFloors();
			if (internalRequestedFloors || externalRequestedFloors) {
				this.direction = ElevatorDirection.UP;
				move = true;
			} else {
				refreshExternalRequestedFloors(ElevatorDirection.DOWN);
				internalRequestedFloors = this.remainingRequestedLowerFloors();
				externalRequestedFloors = this.remaininExternalRequestedLowerFloors();
				if (internalRequestedFloors || externalRequestedFloors) {
					this.direction = ElevatorDirection.DOWN;
					move = true;
				}
			}
		}
		
		if (move) {
			this.move(this.direction);
		}
	}
	
	public void move(ElevatorDirection direction) {
		try {
			Thread.sleep((long) 0.5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (this.direction == ElevatorDirection.UP) {
			this.previousFloor = this.currentFloor;
			this.currentFloor++;
		}
		else if (this.direction == ElevatorDirection.DOWN) {
			this.previousFloor = this.currentFloor;
			this.currentFloor--;
		}
	}
	
	public void moveUp() {
		this.nextFloorStop = this.currentFloor;
		while (this.status == ElevatorStatus.MOVE) {
			
			this.nextFloorStop++;
			if (this.requestedFloors.get(this.nextFloorStop)) {
				this.status = ElevatorStatus.STOPPED;
				this.previousFloor = this.currentFloor;
				this.currentFloor = nextFloorStop;
			}
		}
	}
	
	public void arriveToFloor() {
		// pass
		System.out.println("Arriving to floor " + this.currentFloor);
		boolean floorInternalRequired = this.requestedFloors.get(this.currentFloor);
		boolean floorExternalRequired = this.externalRequestedFloors.get(this.currentFloor);
		if (floorInternalRequired || floorExternalRequired) {
			this.status = ElevatorStatus.STOPPED;
		}
	}
	
	public void broke() {
		this.jarvisSystem.restoreRequestedFloors(this.externalRequestedFloors);
		this.status = ElevatorStatus.BROKEN;
		for (Person person: this.space) {
			person.interrupt();
		}
	}
	
	public void beRepaired() {
		double randomTime = ((Configuration.MAX_REPAIR_SECONDS - Configuration.MIN_REPAIR_SECONDS)
                + (Configuration.MIN_REPAIR_SECONDS * Math.random()));
		try {
			Thread.sleep((long) randomTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}

	public void enter(Object person) {
		// enter to elevator
		// spaceSemaphore.get()
		// space.add(person)
	}
	
	public void requestFloor(int floor) {
		this.requestedFloors.put(floor, true);
	}
	
	public void waitFloor(Person person) {
		while (true) {
			// mirar la planta
			// if currentFloor == person.targetFloor -> wait
		}
	}
	
	public void out(Person person) {
		// out of elevator
		// pace.remove()
		// spaceSemaphore.release()
		
	}
	
	@Override
	public void run() {
		while (true) {
			this.arriveToFloor();
			this.moveToNextFloor();
			if (this.status == ElevatorStatus.BROKEN) {
				this.jarvisSystem.restoreRequestedFloors(this.externalRequestedFloors);
			}
			
		}
	}
	
	

}
