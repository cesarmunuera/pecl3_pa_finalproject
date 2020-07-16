

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Elevator extends Thread {
	
	String id;
	int currentFloor;
	int previousFloor;
	Object spaceSemaphore;
	JarvisSystem jarvisSystem = new JarvisSystem();
	ArrayList<Person> space = new ArrayList<Person>(Configuration.MAX_PEOPLE);
	Map<Integer, Boolean> requestedFloors;
	ElevatorStatus status;
	ElevatorDirection direction;
	
	public void initRequestedFloors() {
		this.requestedFloors = new HashMap<>();
		for (int i=Configuration.MIN_FLOOR; i<=Configuration.MAX_FLOOR; i++) {
			this.requestedFloors.put(i, false);
		}
	}
	
	public Elevator(String id, ElevatorStatus status) {
		this.id = id;
		this.currentFloor = Configuration.MIN_FLOOR;
		this.previousFloor = Configuration.MIN_FLOOR;
		this.status = status;
		this.direction = ElevatorDirection.NONE;
		this.initRequestedFloors();
	}
	
	public void turnOn() {
		this.status = ElevatorStatus.STOPPED;
		this.direction = ElevatorDirection.NONE;
	}
	
	public void turnOff() {
		this.status = ElevatorStatus.OFF;
		this.direction = ElevatorDirection.NONE;
		this.evacuatePeople();
	}
	
	public void broke() {
		this.status = ElevatorStatus.BROKEN;
		this.direction = ElevatorDirection.NONE;
	}
	
	public void evacuatePeople() {
		for (Person person: this.space) {
			person.interrupt();
		}
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
		}
		else if (this.direction == ElevatorDirection.DOWN) {
			this.previousFloor = this.currentFloor;
			this.currentFloor--;
		}
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
		while (!remaining && i!=stop) {
			remaining = map.get(i);
			i += step;
		}
		return remaining;
	}
	
	public boolean remainingRequestedUpperFloors() {
		return this.checkRemainingRequestedFloors(this.currentFloor+1, Configuration.MAX_FLOOR, this.requestedFloors);
	}
	
	public boolean remainingExternalRequestedUpperFloors() {
		return this.checkRemainingRequestedFloors(this.currentFloor+1, Configuration.MAX_FLOOR, this.jarvisSystem.getExternalRequestedFloors());
	}
	
	public boolean remainingRequestedLowerFloors() {
		return this.checkRemainingRequestedFloors(this.currentFloor-1, Configuration.MIN_FLOOR, this.requestedFloors);
	}
	
	public boolean remaininExternalRequestedLowerFloors() {
		return this.checkRemainingRequestedFloors(this.currentFloor-1, Configuration.MIN_FLOOR, this.jarvisSystem.getExternalRequestedFloors());
	}
	
	public void moveToNextFloor() {
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
		
	public void stopInFloor() {
		if (this.status == ElevatorStatus.BROKEN) {
			this.evacuatePeople();
			this.repair();
		}
		boolean floorInternalRequired = this.requestedFloors.get(this.currentFloor);
		boolean floorExternalRequired = this.jarvisSystem.getExternalRequestedFloors().get(this.currentFloor);
		if (floorInternalRequired || floorExternalRequired) {
			System.out.println("Stopping in floor " + this.currentFloor);
			this.status = ElevatorStatus.STOPPED;
			this.requestedFloors.put(this.currentFloor, false);
			this.jarvisSystem.getExternalRequestedFloors().put(this.currentFloor, false);
		}
	}
	
	public void repair() {
		double randomTime = ((Configuration.MAX_REPAIR_SECONDS - Configuration.MIN_REPAIR_SECONDS)
                + (Configuration.MIN_REPAIR_SECONDS * Math.random()));
		try {
			Thread.sleep((long) randomTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.status = ElevatorStatus.STOPPED;
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
			while (this.status != ElevatorStatus.OFF)
				this.stopInFloor();
				this.moveToNextFloor();
		}
	}
	
	

}
