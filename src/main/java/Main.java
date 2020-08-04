
public class Main {

    public static void main(String[] args) throws Exception {
    	
    	Hospital hospital = new Hospital();
    	PeopleGenerator peopleGenerator = new PeopleGenerator(hospital);
    	peopleGenerator.start();

    }

}
