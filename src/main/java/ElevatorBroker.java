import java.util.ArrayList;
import java.util.Random;

public class ElevatorBroker extends Thread {
	
	ArrayList<Elevator> elevators;
	
	public ElevatorBroker(ArrayList<Elevator> elevators) {
		this.elevators = elevators;
	}
	
	public void broke() {
		
	}
	
	public void sleepRandomTime() {
		double randomTime = ((Configuration.MAX_BROKE_SECONDS - Configuration.MIN_BROKE_SECONDS)
                + (Configuration.MIN_BROKE_SECONDS * Math.random()));
		try {
			Thread.sleep((long) randomTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void brokeRandomElevator() {
		boolean choosen = false;
		Random r = new Random();
		while (!choosen) {
			int randomNum = r.nextInt(this.elevators.size() + 1);
			Elevator elevator = this.elevators.get(randomNum);
			if (elevator.status == ElevatorStatus.STOPPED || elevator.status == ElevatorStatus.MOVE) {
				elevator.broke();
				choosen = true;
			}
		}
		
		
		
	}
	
	@Override
	public void run() {
		while (true) {
			broke();
			
		}
	}
	
	

}
