package hospital;

public class ElevatorBackUp extends Elevator {

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
                    System.out.println(toString() + ": moved");
                } catch (InterruptedException e) {
                    System.out.println(toString() + ": stoping");
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
