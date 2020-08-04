
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
        this.peopleCounter++;
        return ID_PREFIX + peopleCounter;
    }

    private int randomFloor() {
        return (int) (Math.random() * (Configuration.MAX_FLOOR + 1));
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
    
    public Person generate() {
    	String id = idGenerator();
        int currentFloor = randomFloor();
        int targetFloor = randomFloor();
        while (currentFloor == targetFloor) {
        	targetFloor = randomFloor();
        }
        return new Person(id, hospital.getFloor(currentFloor), targetFloor);
    }

    @Override
    public void run() {
        while (this.peopleCounter < Configuration.PEOPLE_GENERATED) {
        	System.out.println("generating person");
            waitForPeopleGeneration();
            generate().start();
        }

    }
}
