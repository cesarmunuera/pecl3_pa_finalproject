
import java.util.ArrayList;
import java.util.logging.Logger;

public class Person extends Thread {

    private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);
    public static final String PREFIX = "P";

    private HospitalFloor hospitalFloor;
    private String identificator;
    private int sourceFloor;
    private int floor;
    private int targetFloor;
    private ElevatorDirection direction;


    public Person(String identificator, HospitalFloor hospitalFloor, int targetFloor) {
        this.identificator = identificator;
        this.hospitalFloor = hospitalFloor;
        this.sourceFloor = hospitalFloor.getFloor();
        this.floor = hospitalFloor.getFloor();
        this.targetFloor = targetFloor;
        chooseDirection();
        if (Configuration.LOGGING_ON) logger.info(this.toString() + " initialized");
    }

    @Override
    public String toString() {
        return "Person(" + identificator + ", " + floor + ", " + sourceFloor + " -> " + targetFloor + ", " + direction.name() + ")";
    	//return identificator + "->" + targetFloor;

    }
    
    public void chooseDirection() {
    	ElevatorDirection dir = ElevatorDirection.NONE;
        if (floor < targetFloor) {
        	dir = ElevatorDirection.UP;
        } else if (floor > targetFloor) {
        	dir = ElevatorDirection.DOWN;
        } 
        direction = dir;
    }
    
    @Override
    public void run() {
        ArrayList<Elevator> elevators;
        Elevator choosenElevator;

        while (!(floor == targetFloor)) {
        	if (Configuration.LOGGING_ON) logger.info(toString() + " called elevator and start waiting");
            hospitalFloor.callElevator(); // sleep until elevator arrives
            elevators = hospitalFloor.getElevators();
            choosenElevator = null;

            for (Elevator elevator: elevators) {
                if (elevator != null) {
                    if (elevator.getStatus() != ElevatorStatus.BROKEN) {
                        if (elevator.getDirection() == direction || elevator.getDirection() == ElevatorDirection.NONE) {
                            choosenElevator = elevator;
                            break;
                        } else {
                        	if (Configuration.LOGGING_ON) logger.info(toString() + " elevator wrong direction, wait next one: " + elevator.toString());
                        }
                    } else {
                    	if (Configuration.LOGGING_ON) logger.info(toString() + " elevator broken, wait next one: " + elevator.toString());
                    }
                } else {
                	if (Configuration.LOGGING_ON) logger.warning(toString() + " not possible to get elevator");
                }
            }

            if (choosenElevator != null) {
                // wait until people exit (status == EXITING)
                while (choosenElevator.getStatus() != ElevatorStatus.STOPPED) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // elegir un elevador, y luego entrar en el elegido
                boolean inside = choosenElevator.enter(this);
                if (inside) {
                	if (Configuration.LOGGING_ON) logger.info("Person " + identificator + ": enter to elevator");
                	if (Configuration.LOGGING_ON) logger.info(toString() + " enter to elevator");
                    choosenElevator.waitFloor(this);
                    choosenElevator.out(this);
                    if (Configuration.LOGGING_ON) logger.info(toString() + " go out to floor " + floor);
                    if (floor == targetFloor) {
                    	if (Configuration.LOGGING_ON) logger.info(toString() + " is in target floor!");
                    } else {
                    	if (Configuration.LOGGING_ON) logger.info(toString() + " is not in target floor yet");
                    }
                } else {
                    while (choosenElevator.getStatus() == ElevatorStatus.STOPPED) {
                        try {
                            Thread.sleep(5); // needed to work while properly
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (Configuration.LOGGING_ON) logger.info(toString() + " elevator full, wait next one");
                }

            } else {
                // wait until all elevators leave floor
                for (Elevator elevator: elevators) {
                    while (floor == elevator.getCurrentFloor() && elevator.getStatus() == ElevatorStatus.STOPPED) {
                        try {
                            Thread.sleep(5); // needed to work while properly
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        if (Configuration.LOGGING_ON) logger.info(toString() + " ends");
    }
    
    public synchronized int getFloor() {
		return floor;
	}

	public synchronized void setFloor(int floor) {
		this.floor = floor;
		chooseDirection();
	}

	public HospitalFloor getHospitalFloor() {
		return hospitalFloor;
	}

	public void setHospitalFloor(HospitalFloor hospitalFloor) {
		this.hospitalFloor = hospitalFloor;
	}

	public String getIdentificator() {
		return identificator;
	}

	public int getTargetFloor() {
		return targetFloor;
	}

	public ElevatorDirection getDirection() {
		return direction;
	}


}
