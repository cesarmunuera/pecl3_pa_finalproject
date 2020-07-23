

public class Person extends Thread{
    
    HospitalFloor hospitalFloor;
    
    private String identificator;
    int number = 0;
    int floor;

    public Person (String identificator, int floor){
        this.identificator = identificator;
        this.floor = floor;
    }
    private String idGenerator(){
        identificator = "P" + number;
        number++;
        return identificator;
    }
    
    private int randomFloor(){
       floor = (int) (Math.random() * (Configuration.MAX_FLOOR));
        return floor;
    }

   @Override
    public void run() {
        identificator = idGenerator();
        floor = randomFloor();
        
    }
}
