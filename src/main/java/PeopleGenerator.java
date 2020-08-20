
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeopleGenerator extends Thread {
	
	private static final Logger logger = Logger.getLogger(Logging.LOG_NAME);

    Hospital hospital;
	int peopleCounter;
    
    public PeopleGenerator(Hospital hospital) {
    	this.hospital = hospital;
    	this.peopleCounter = 0;
    }

    private String idGenerator() {
        this.peopleCounter++;
        return Person.PREFIX + peopleCounter;
    }

    private int randomFloor() {
        return (int) (Math.random() * (Configuration.HOSPITAL_FLOOR_MAX + 1));
    }

    private void waitForPeopleGeneration() {
        double randomTime = (Math.random() * (Configuration.GENERATE_USER_MAX_MS - Configuration.GENERATE_USER_MIN_MS + 1) 
                + Configuration.GENERATE_USER_MIN_MS);

        try {
            Thread.sleep((long) randomTime);
        } catch (InterruptedException ex) {
        	if (Configuration.LOGGING_ON) logger.warning("error while waiting next generation: " + ex.getMessage());
        }
    }
    
    public Person generate() {
    	
    	String id = idGenerator();
        int currentFloor = randomFloor();
        int targetFloor = randomFloor();
        while (currentFloor == targetFloor) {
        	targetFloor = randomFloor();
        }

		Person person = new Person(id, hospital.getFloor(currentFloor), targetFloor);
		if (Configuration.LOGGING_ON) logger.info("generated person " + this.peopleCounter + "/" + Configuration.GENERATED_MAX_PEOPLE);
        return person;
    }

    @Override
    public void run() {
    	if (Configuration.LOGGING_ON) logger.info("starts generating people");
        while (this.peopleCounter < Configuration.GENERATED_MAX_PEOPLE) {
            waitForPeopleGeneration();
            generate().start();
            
        }
        if (Configuration.LOGGING_ON) logger.info("ends generating people");
    }

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public int getPeopleCounter() {
		return peopleCounter;
	}

	public void setPeopleCounter(int peopleCounter) {
		this.peopleCounter = peopleCounter;
	}

	public static Logger getLogger() {
		return logger;
	}
    
    
}
