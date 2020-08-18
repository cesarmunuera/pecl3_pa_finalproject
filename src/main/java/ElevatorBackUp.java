
public class ElevatorBackUp extends Elevator {

    public ElevatorBackUp(String id, ElevatorStatus status, JarvisSystem jarvisSystem) {
        super(id, status, jarvisSystem);
    }
    
    @Override
    public void run() {
        while (true) {
        	if (this.status != ElevatorStatus.OFF) {
        		this.moveToNextFloor();
                this.stopInFloor();
            } else {
            	evacuatePeople();
            }
            
        }
    }
    
}
