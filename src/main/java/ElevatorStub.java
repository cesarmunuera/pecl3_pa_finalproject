
public class ElevatorStub {

    public static void main(String[] args) {
    	JarvisSystem jarvis = new JarvisSystem(); 
        Elevator elevator = new Elevator("elevator", ElevatorStatus.STOPPED, jarvis);

        elevator.start();
        try {
            elevator.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
