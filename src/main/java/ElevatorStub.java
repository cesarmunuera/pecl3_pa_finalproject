
public class ElevatorStub {

    public static void main(String[] args) {
        Elevator elevator = new Elevator("elevator", ElevatorStatus.STOPPED);

        elevator.start();
        try {
            elevator.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
