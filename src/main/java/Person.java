import java.util.ArrayList;
import java.util.logging.Logger;

public class Person extends Thread {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
	public static final String PREFIX = "P";

    HospitalFloor hospitalFloor;
    private String identificator;
    int sourceFloor;
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
        this.sourceFloor = hospitalFloor.floor;
        this.floor = hospitalFloor.floor;
        this.targetFloor = targetFloor;
        chooseDirection();
        logger.info(this.toString() + " initialized");
    }
    
    @Override
    public String toString() {
		return "Person(" + this.identificator + ", " + this.floor + ", " + this.sourceFloor + " -> " + this.targetFloor + ", " + this.direction.name() + ")";
    	
    }
    
    @Override
	public void run() {
    	ArrayList<Elevator> elevators;
    	Elevator choosenElevator;
    	
    	while (!(this.floor == this.targetFloor)) {
    		logger.info(this.toString() + " called elevator and start waiting");
    		this.hospitalFloor.callElevator(); // sleep until elevator arrives
    		// TODO: Traer todos los elevadores y hacer un for para cada uno
    		// romper el bucle si entras en uno
    		elevators = this.hospitalFloor.getElevators();
    		choosenElevator = null;
    		
    		for (Elevator elevator: elevators) {
				if (elevator != null) {
					if (elevator.status != ElevatorStatus.BROKEN) {
						if (elevator.direction == this.direction || elevator.direction == ElevatorDirection.NONE) {
							choosenElevator = elevator;
							break;
						} else {
							logger.info(this.toString() + " elevator wrong direction, wait next one: " + elevator.toString());
						}
					} else {
						logger.info(this.toString() + " elevator broken, wait next one: " + elevator.toString());
					}
				} else {
					logger.warning(this.toString() + " not possible to get elevator");
				}
    		}
    		
    		if (choosenElevator != null) {
				// wait until people exit (status == EXITING)
				while (choosenElevator.status != ElevatorStatus.STOPPED) {
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// elegir un elevador, y luego entrar en el elegido
				boolean inside = choosenElevator.enter(this);
				if (inside) {
					System.out.println("Person " + this.identificator + ": enter to elevator");
					logger.info(this.toString() + " enter to elevator");
					choosenElevator.waitFloor(this);
					choosenElevator.out(this);
					logger.info(this.toString() + " go out to floor " +  this.floor);
					if (this.floor == this.targetFloor) {
						logger.info(this.toString() + " is in target floor!");
					} else {
						logger.info(this.toString() + " is not in target floor yet");
					}
				} else {
					while (choosenElevator.status == ElevatorStatus.STOPPED) {
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					logger.info(this.toString() + " elevator full, wait next one");
				}
						
    		} else {
    			// wait until all elevators leave floor
    			for (Elevator elevator: elevators) {
    				while (this.floor == elevator.currentFloor && elevator.status == ElevatorStatus.STOPPED) {
    					try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    				}
    			}
    		}
    	
    	}
    	logger.info(this.toString() + " ends");
	}

}
