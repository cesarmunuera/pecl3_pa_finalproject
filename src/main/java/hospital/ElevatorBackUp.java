package hospital;

import java.util.logging.Logger;

public class ElevatorBackUp extends Elevator {

    private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    public ElevatorBackUp(String id, ElevatorStatus status, JarvisSystem jarvisSystem) {
        super(id, status, jarvisSystem);
    }

    public void turnOff() {
        this.status = ElevatorStatus.OFF;
        this.direction = ElevatorDirection.NONE;
        interrupt();

    }

    @Override
    public void run() {
        if (this.status != ElevatorStatus.OFF) {
            try {
                stopInFloor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (this.status != ElevatorStatus.END) {
            while (this.status != ElevatorStatus.OFF) {
                try {
                    moveToNextFloor();
                    stopInFloor();
                    if (Configuration.LOGGING_ON) {
                        logger.info(toString() + ": moved");
                    }
                } catch (InterruptedException e) {
                    if (Configuration.LOGGING_ON) {
                        logger.info(toString() + ": stoping");
                    }
                    forceOutPeople();
                }

            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        forceOutPeople();
    }

}
