package hospital.server;


import hospital.JarvisSystem;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerController extends UnicastRemoteObject implements ServerControllerInterfaceRMI{
    
    JarvisSystem jarvisSystem;
    public ServerController(JarvisSystem jarvisSystem) throws RemoteException {
        this.jarvisSystem = jarvisSystem;
    }
        
    @Override
    public void getElevatorsInfo() {
        
    }
    
    @Override
    public void evacuateSystem() {
    	this.jarvisSystem.startEvacuation();
    }
}
