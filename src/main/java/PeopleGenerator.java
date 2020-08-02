
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeopleGenerator extends Thread {

    Hospital hospital;
	int peopleCounter;
    String ID_PREFIX = "P";
    
    public PeopleGenerator(Hospital hospital) {
    	this.hospital = hospital;
    	this.peopleCounter = 0;
    }

    private String idGenerator() {
        peopleCounter++;
        return ID_PREFIX + peopleCounter;
    }

    private int randomFloor() {
        return (int) (Math.random() * (Configuration.MAX_FLOOR +1));
    }

    private void waitForPeopleGeneration() {
        double randomTime = (Math.random() * (Configuration.MAX_GENERATE_USER_MS - Configuration.MIN_GENERATE_USER_MS + 1) 
                + Configuration.MIN_GENERATE_USER_MS);

        try {
            Thread.sleep((long) randomTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(PeopleGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
    	String id;
    	int currentFloor;
    	int targetFloor;
    	HospitalFloor floor;
    	Person person;
    	
        while (true) {
            waitForPeopleGeneration();
            id = idGenerator();
            currentFloor = randomFloor();
            targetFloor = randomFloor();
            while (currentFloor == targetFloor) {
            	targetFloor = randomFloor();
            }

            floor = hospital.getFloor(currentFloor);
            person = new Person(id, floor, targetFloor);
            person.hospitalFloor = floor;
            // TODO: put person in random hospital floor
        }

    }
}
