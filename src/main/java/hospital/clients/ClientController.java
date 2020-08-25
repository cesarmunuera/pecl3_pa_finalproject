package hospital.clients;


import hospital.server.ServerControllerInterfaceRMI;
import hospital.server.NetworkConfig;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientController {

    ServerControllerInterfaceRMI controllerRMI;

    public ClientController() throws RemoteException, NotBoundException, MalformedURLException {

        this.controllerRMI = (ServerControllerInterfaceRMI) Naming.lookup(NetworkConfig.CLIENT_CONTROLLER_URI);
    }
    
    
    public void getElevatorsInfo() throws RemoteException {
        
    }
    
    public void evacuateSystem() throws RemoteException {
        this.controllerRMI.evacuateSystem();
    }

}
