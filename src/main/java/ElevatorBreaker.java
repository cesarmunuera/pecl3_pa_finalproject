import java.util.ArrayList;
import java.util.Random;

public class ElevatorBreaker extends Thread {
	
	ArrayList<Elevator> elevators;
	ElevatorBackUp elevatorBackUp;
	
	public ElevatorBreaker(ArrayList<Elevator> elevators, ElevatorBackUp elevatorBackUp) {
		this.elevators = elevators;
		this.elevatorBackUp = elevatorBackUp;
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
		Elevator elevatorBack = this.elevators.get(this.elevators.size()+1);
		
		while (!choosen) {
			int randomNum = r.nextInt(this.elevators.size());
			Elevator elevator = this.elevators.get(randomNum);
			if (elevator.status == ElevatorStatus.STOPPED) {
				elevator.broke();
				elevatorBack.status = ElevatorStatus.STOPPED;
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
