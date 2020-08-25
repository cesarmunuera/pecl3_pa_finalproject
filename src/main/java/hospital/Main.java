package hospital;


public class Main {

    public static void main(String[] args) throws Exception {
    	
    	Logging.initLogger();
    	Hospital hospital = new Hospital();
    	PeopleGenerator peopleGenerator = new PeopleGenerator(hospital);
    	peopleGenerator.start();

    }

}
