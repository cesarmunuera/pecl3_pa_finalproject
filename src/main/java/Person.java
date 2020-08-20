
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
        if (Configuration.LOGGING_ON) logger.info(this.toString() + " initialized");
    }

    @Override
    public String toString() {
        //return "Person(" + this.identificator + ", " + this.floor + ", " + this.sourceFloor + " -> " + this.targetFloor + ", " + this.direction.name() + ")";
    	return this.identificator + "->" + this.targetFloor;

    }
    
    public synchronized int getFloor() {
		return floor;
	}

	public synchronized void setFloor(int floor) {
		this.floor = floor;
	}

    @Override
    public void run() {
        ArrayList<Elevator> elevators;
        Elevator choosenElevator;

        while (!(this.floor == this.targetFloor)) {
        	if (Configuration.LOGGING_ON) logger.info(this.toString() + " called elevator and start waiting");
            this.hospitalFloor.callElevator(); // sleep until elevator arrives
            elevators = this.hospitalFloor.getElevators();
            choosenElevator = null;

            for (Elevator elevator : elevators) {
                if (elevator != null) {
                    if (elevator.status != ElevatorStatus.BROKEN) {
                        if (elevator.direction == this.direction || elevator.direction == ElevatorDirection.NONE) {
                            choosenElevator = elevator;
                            break;
                        } else {
                        	if (Configuration.LOGGING_ON) logger.info(this.toString() + " elevator wrong direction, wait next one: " + elevator.toString());
                        }
                    } else {
                    	if (Configuration.LOGGING_ON) logger.info(this.toString() + " elevator broken, wait next one: " + elevator.toString());
                    }
                } else {
                	if (Configuration.LOGGING_ON) logger.warning(this.toString() + " not possible to get elevator");
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
                	if (Configuration.LOGGING_ON) logger.info("Person " + this.identificator + ": enter to elevator");
                	if (Configuration.LOGGING_ON) logger.info(this.toString() + " enter to elevator");
                    choosenElevator.waitFloor(this);
                    choosenElevator.out(this);
                    if (Configuration.LOGGING_ON) logger.info(this.toString() + " go out to floor " + this.floor);
                    if (this.floor == this.targetFloor) {
                    	if (Configuration.LOGGING_ON) logger.info(this.toString() + " is in target floor!");
                    } else {
                    	if (Configuration.LOGGING_ON) logger.info(this.toString() + " is not in target floor yet");
                    }
                } else {
                    while (choosenElevator.status == ElevatorStatus.STOPPED) {
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (Configuration.LOGGING_ON) logger.info(this.toString() + " elevator full, wait next one");
                }

            } else {
                // wait until all elevators leave floor
                for (Elevator elevator : elevators) {
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
        if (Configuration.LOGGING_ON) logger.info(this.toString() + " ends");
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

	public void setIdentificator(String identificator) {
		this.identificator = identificator;
	}

	public int getSourceFloor() {
		return sourceFloor;
	}

	public void setSourceFloor(int sourceFloor) {
		this.sourceFloor = sourceFloor;
	}

	public int getTargetFloor() {
		return targetFloor;
	}

	public void setTargetFloor(int targetFloor) {
		this.targetFloor = targetFloor;
	}

	public ElevatorDirection getDirection() {
		return direction;
	}

	public void setDirection(ElevatorDirection direction) {
		this.direction = direction;
	}

	public static Logger getLogger() {
		return logger;
	}
    
    

}
