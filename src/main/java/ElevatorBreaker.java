
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElevatorBreaker extends Thread {

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
                elevator.broke();
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

}
