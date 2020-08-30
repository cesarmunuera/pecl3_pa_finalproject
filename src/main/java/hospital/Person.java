package hospital;

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
        this.sourceFloor = hospitalFloor.getFloor();
        this.floor = hospitalFloor.getFloor();
        this.targetFloor = targetFloor;
        chooseDirection();
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " initialized");
        }
    }

    @Override
    public String toString() {
        return this.identificator + "->" + this.targetFloor;

    }

    public void evacuate() {
        this.direction = ElevatorDirection.DOWN;
        this.targetFloor = Configuration.HOSPITAL_FLOOR_MIN;
    }

    private void waitFloor(Elevator elevator) throws InterruptedException {
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " waiting floor - " + elevator.toString());
        }
        boolean isInTargetFloor = false;
        while (!isInTargetFloor) {
            this.floor = elevator.getCurrentFloor();
            this.hospitalFloor = elevator.getHospitalFloor(this.floor);
            isInTargetFloor = elevator.getCurrentFloor() == this.targetFloor;
            sleep(5);
        }
    }

    private Elevator chooseElevator(ArrayList<Elevator> elevators) {
        Elevator choosenElevator = null;
        for (Elevator elevator : elevators) {
            if (elevator != null) {
                if (elevator.getStatus() != ElevatorStatus.BROKEN && elevator.getStatus() != ElevatorStatus.OFF) {
                    if (elevator.getDirection() == this.direction || elevator.getDirection() == ElevatorDirection.NONE) {
                        choosenElevator = elevator;
                        break;
                    } else {
                        if (Configuration.LOGGING_ON) {
                            logger.info(this.toString() + " elevator wrong direction, wait next one: " + elevator.toString());
                        }
                    }
                } else {
                    if (Configuration.LOGGING_ON) {
                        logger.info(this.toString() + " elevator broken, wait next one: " + elevator.toString());
                    }
                }
            } else {
                if (Configuration.LOGGING_ON) {
                    logger.warning(this.toString() + " not possible to get elevator");
                }
            }
        }
        return choosenElevator;
    }

    private void waitExitingPeople(Elevator elevator) {
        while (elevator.getStatus() != ElevatorStatus.STOPPED) {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void waitElevatorFullLeave(Elevator elevator) {
        while (elevator.getStatus() == ElevatorStatus.STOPPED) {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void waitAllElevatorsLeave(ArrayList<Elevator> elevators) {
        for (Elevator elevator : elevators) {
            while (this.floor == elevator.getCurrentFloor()) {
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        ArrayList<Elevator> elevators;
        Elevator choosenElevator;

        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " called elevator and start waiting");
        }

        while (this.floor != this.targetFloor) {

            this.hospitalFloor.callElevator(); // sleep until elevator arrives
            elevators = this.hospitalFloor.getElevators();
            choosenElevator = chooseElevator(elevators);

            if (choosenElevator != null) {
                if (choosenElevator.isEvacuating()) {
                    evacuate();
                }
                waitExitingPeople(choosenElevator);
                boolean inside = choosenElevator.enter(this);
                if (inside) {
                    if (Configuration.LOGGING_ON) {
                        logger.info("Person " + this.identificator + ": enter to elevator");
                    }
                    if (Configuration.LOGGING_ON) {
                        logger.info(this.toString() + " enter to elevator");
                    }
                    try {
                        waitFloor(choosenElevator);
                    } catch (InterruptedException e) {
                        logger.info(toString() + " being evacuating");
                    } finally {
                        choosenElevator.out(this);
                    }

                    if (Configuration.LOGGING_ON) {
                        logger.info(this.toString() + " go out to floor " + this.floor);
                    }
                    if (this.floor == this.targetFloor) {
                        if (Configuration.LOGGING_ON) {
                            logger.info(this.toString() + " is in target floor!");
                        }
                    } else {
                        if (Configuration.LOGGING_ON) {
                            logger.info(this.toString() + " is not in target floor yet");
                        }
                    }
                } else {
                    waitElevatorFullLeave(choosenElevator);
                    if (Configuration.LOGGING_ON) {
                        logger.info(this.toString() + " elevator full, wait next one");
                    }
                }

            } else {
                waitAllElevatorsLeave(elevators);
            }

        }
        if (Configuration.LOGGING_ON) {
            logger.info(this.toString() + " ends");
        }
    }

    public synchronized int getFloor() {
        return floor;
    }

    public synchronized void setFloor(int floor) {
        this.floor = floor;
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

}
