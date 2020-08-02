
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
	
    public Person(String identificator, int floor, int targetFloor) {
        this.identificator = identificator;
        this.floor = floor;
        this.targetFloor = targetFloor;
        chooseDirection();
    }
    
    @Override
	public void run() {
    	Elevator elevator; 
    	while (!(this.floor == this.targetFloor)) {
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
							elevator.waitFloor(this);
							elevator.out(this);
							if (!(this.floor == this.targetFloor)) {
							}
						}
					}
				}
				
			}
    	}
	}

}
