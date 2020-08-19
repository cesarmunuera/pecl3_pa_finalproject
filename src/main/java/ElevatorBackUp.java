
public class ElevatorBackUp extends Elevator {

    public ElevatorBackUp(String id, ElevatorStatus status, JarvisSystem jarvisSystem) {
        super(id, status, jarvisSystem);
    }
    
    @Override
    public void run() {
        while (this.status != ElevatorStatus.END) {
            while (this.status != ElevatorStatus.OFF) {
            	this.stopInFloor();
                this.moveToNextFloor();
                this.stopInFloor();
            }
            evacuatePeople();
        }
        evacuatePeople();
    }
    
}
