
public class Person extends Thread {

    HospitalFloor hospitalFloor;

    private String identificator;
    int floor;

    public Person(String identificator, int floor) {
        this.identificator = identificator;
        this.floor = floor;
    }

}
