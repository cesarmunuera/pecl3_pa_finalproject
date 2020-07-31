
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonGenerator extends Thread {

    int number;

    private String idGenerator() {
        number++;
        String identificator = "P";
        return identificator + number;
    }

    private int randomFloor() {
        return (int) (Math.random() * (Configuration.MAX_FLOOR));
    }

    @Override
    public void run() {
        while (true) {

            try {
                sleep((long) Math.random() * (Configuration.MAX_TIME_GENERATE_USER) + 500);
            } catch (InterruptedException ex) {
                Logger.getLogger(PersonGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }

            String id = idGenerator();
            int floor = randomFloor();

            Person person = new Person(id, floor);
        }

    }
}
