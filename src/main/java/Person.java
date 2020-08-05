import java.util.logging.Logger;

public class Person extends Thread {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
	public static final String PREFIX = "P";

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
        logger.info(this.toString() + " initialized");
    }
    
    @Override
    public String toString() {
		return "Person(" + this.identificator + ", " + this.floor + " -> " + this.targetFloor + ", " + this.direction.name() + ")";
    	
    }
    
    @Override
	public void run() {
    	Elevator elevator; 
    	while (!(this.floor == this.targetFloor)) {
    		logger.info(this.toString() + " called elevator and start waiting");
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
							logger.info(this.toString() + " enter to elevator");
							elevator.waitFloor(this);
							elevator.out(this);
							logger.info(this.toString() + " go out to floor " +this.floor);
							if (this.floor == this.targetFloor) {
								logger.info(this.toString() + " is in target floor!");
							} else {
								logger.info(this.toString() + " is not in target floor yet");
							}
						} else {
							logger.info(this.toString() + " elevator full, wait next one");
						}
					}
				} else {
					logger.info(this.toString() + " elevator broken, wait next one");
				}
				
			} else {
				logger.warning(this.toString() + " not possible to get elevator");
			}
    	}
    	logger.info(this.toString() + " ends");
	}

}
