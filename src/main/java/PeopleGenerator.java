
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeopleGenerator extends Thread {

    int peopleCounter = 0;
    String idPrefix = "P";

    private String idGenerator() {
        peopleCounter++;
        return idPrefix + peopleCounter;
    }

    private int randomFloor() {
        return (int) (Math.random() * (Configuration.MAX_FLOOR +1));
    }

    private void waitForPeopleGeneration() {
        double randomTime = (Math.random() * (Configuration.MAX_GENERATE_USER_MS - Configuration.MIN_GENERATE_USER_MS +1) 
                + Configuration.MIN_GENERATE_USER_MS);

        try {
            Thread.sleep((long) randomTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(PeopleGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {

            waitForPeopleGeneration();
            String id = idGenerator();
            int currentFloor = randomFloor();
            int targetFloor = randomFloor();
            // TODO: check currentFloor != targetFloor

            Person person = new Person(id, currentFloor, targetFloor);
            // TODO: put person in random hospital floor
        }

    }
}
