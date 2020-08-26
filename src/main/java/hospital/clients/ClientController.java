package hospital.clients;


import hospital.server.ServerControllerInterfaceRMI;
import hospital.dto.ElevatorInfoDTO;
import hospital.server.NetworkConfig;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public class ClientController {

    ServerControllerInterfaceRMI controllerRMI;

    public ClientController() throws RemoteException, NotBoundException, MalformedURLException {

        this.controllerRMI = (ServerControllerInterfaceRMI) Naming.lookup(NetworkConfig.CLIENT_CONTROLLER_URI);
    }
    
    
    public List<ElevatorInfoDTO> getElevatorsInfo() throws RemoteException {
    	return this.controllerRMI.getElevatorsInfo();
    }
    
    public void evacuateSystem() throws RemoteException {
        this.controllerRMI.evacuateSystem();
    }

    public HashMap<Integer, Integer> getPeopleInFloor() throws RemoteException{
        return this.controllerRMI.getPeopleInFloor();
    }
}
