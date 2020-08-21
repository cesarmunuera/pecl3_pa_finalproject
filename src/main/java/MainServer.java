
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {

    public static void main(String[] args) throws RemoteException {
        Hospital hospital = new Hospital();
        JarvisSystem jarvisSystem = new JarvisSystem(hospital);
        
        ServerController serverController = new ServerController(jarvisSystem);
        startServer(serverController);
    }

    public static void startServer(ServerController controller) {
        try {
            ServerController controllerObj = controller;
            Registry registry = LocateRegistry.createRegistry(NetworkConfig.PORT);
            Naming.rebind(NetworkConfig.SERVER_CONTROLLER_URI, controllerObj);
            System.out.println("Started server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
