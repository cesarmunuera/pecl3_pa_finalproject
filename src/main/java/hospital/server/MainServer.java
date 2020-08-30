package hospital.server;

import hospital.Hospital;
import hospital.Logging;
import hospital.PeopleGenerator;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MainServer {

    public static void main(String[] args) throws Exception {

        Logging.initLogger();
        Hospital hospital = new Hospital();
        PeopleGenerator peopleGenerator = new PeopleGenerator(hospital);
        ServerController serverController = new ServerController(hospital.getJarvisSystem());
        peopleGenerator.start();
        startServer(serverController);
    }

    private static void startServer(ServerController controller) {
        try {
            ServerController controllerObj = controller;
            LocateRegistry.createRegistry(NetworkConfig.PORT);
            Naming.rebind(NetworkConfig.SERVER_CONTROLLER_URI, controllerObj);
            System.out.println("Started server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
