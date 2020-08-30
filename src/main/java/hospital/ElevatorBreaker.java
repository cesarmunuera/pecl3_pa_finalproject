package hospital;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class ElevatorBreaker extends Thread {

    private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    private List<Elevator> elevators;
    private ElevatorBackUp elevatorBackUp;
    private Random random = new Random();

    public ElevatorBreaker(List<Elevator> elevators, ElevatorBackUp elevatorBackUp) {
        this.elevators = elevators;
        this.elevatorBackUp = elevatorBackUp;
    }

    private void sleepRandomTime() {
        double randomTime = (Math.random() * (Configuration.ELEVATOR_BROKE_MAX_MS - Configuration.ELEVATOR_BROKE_MIN_MS + 1)
                + Configuration.ELEVATOR_BROKE_MIN_MS);

        try {
            Thread.sleep((long) randomTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void brokeRandomElevator() {
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

    public ElevatorBackUp getElevatorBackUp() {
        return elevatorBackUp;
    }

    public Random getRandom() {
        return random;
    }

}
