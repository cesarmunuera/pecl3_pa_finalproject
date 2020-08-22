
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerController extends UnicastRemoteObject implements ServerControllerInterfaceRMI{
    
    JarvisSystem jarvisSystem;
    public ServerController(JarvisSystem jarvisSystem) throws RemoteException {
        this.jarvisSystem = jarvisSystem;
    }
    
    
    //Hacer get de los métodos creados en JARVIS para poder exportar al cliente
}
