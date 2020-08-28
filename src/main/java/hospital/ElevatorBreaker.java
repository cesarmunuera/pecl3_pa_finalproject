package hospital;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class ElevatorBreaker extends Thread {

    private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    List<Elevator> elevators;
    ElevatorBackUp elevatorBackUp;
    Random random = new Random();

    public ElevatorBreaker(List<Elevator> elevators, ElevatorBackUp elevatorBackUp) {
        this.elevators = elevators;
        this.elevatorBackUp = elevatorBackUp;
    }

    public void sleepRandomTime() {
        double randomTime = (Math.random() * (Configuration.ELEVATOR_BROKE_MAX_MS - Configuration.ELEVATOR_BROKE_MIN_MS + 1)
                + Configuration.ELEVATOR_BROKE_MIN_MS);

        try {
            Thread.sleep((long) randomTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void brokeRandomElevator() {
        boolean choosen = false;
        int randomNum;
        Elevator elevator;

        while (!choosen) {
            randomNum = this.random.nextInt(this.elevators.size());
            elevator = this.elevators.get(randomNum);
            if (elevator.status == ElevatorStatus.STOPPED) {
                elevator.interrupt();
                choosen = true;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            sleepRandomTime();
            brokeRandomElevator();
        }
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public ElevatorBackUp getElevatorBackUp() {
        return elevatorBackUp;
    }

    public void setElevatorBackUp(ElevatorBackUp elevatorBackUp) {
        this.elevatorBackUp = elevatorBackUp;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public static Logger getLogger() {
        return logger;
    }

}
