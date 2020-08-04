
public class Person extends Thread {

    HospitalFloor hospitalFloor;
    private String identificator;
    int floor;
    int targetFloor;
    ElevatorDirection direction;

    
	public void chooseDirection() {
		if (this.floor < this.targetFloor) {
			direction = ElevatorDirection.UP;
		} else if (this.floor > this.targetFloor) {
			direction = ElevatorDirection.DOWN;
		} else {
			direction = ElevatorDirection.NONE;
		}
	}
	
    public Person(String identificator, HospitalFloor hospitalFloor, int targetFloor) {
        this.identificator = identificator;
        this.hospitalFloor = hospitalFloor;
        this.floor = hospitalFloor.floor;
        this.targetFloor = targetFloor;
        chooseDirection();
        System.out.println("Person " + this.identificator + " (" + this.floor + " -> " + this.targetFloor + ") " + " initialized!");
    }
    
    @Override
	public void run() {
    	Elevator elevator; 
    	while (!(this.floor == this.targetFloor)) {
    		System.out.println("Person " + this.identificator + ": called elevator and wait");
    		this.hospitalFloor.callElevator(); // sleep until elevator arrives
    		elevator = this.hospitalFloor.getElevator();
			if (elevator != null) {
				if (elevator.status != ElevatorStatus.BROKEN) {
					if (elevator.direction == this.direction) {
						while (elevator.status != ElevatorStatus.STOPPED) {
							// wait exiting
						}
						boolean inside = elevator.enter(this);
						if (inside) {
							System.out.println("Person " + this.identificator + ": enter to elevator");
							elevator.waitFloor(this);
							elevator.out(this);
							System.out.println("Person " + this.identificator + ": go out from floor");
							if (this.floor == this.targetFloor) {
								System.out.println("Person " + this.identificator + ": is in target floor");
							} 
						} else {
							System.out.println("Person " + this.identificator + ": elevator full, wait next one");
						}
					}
				} else {
					System.out.println("Person " + this.identificator + ": elevator broken, wait next one");
				}
				
			} else {
				System.out.println("Person " + this.identificator + ": not possible to get elevator");
			}
    	}
	}

}
