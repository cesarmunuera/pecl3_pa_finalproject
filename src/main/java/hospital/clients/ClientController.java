package hospital.clients;

import hospital.server.ServerControllerInterfaceRMI;
import hospital.dto.ElevatorInfoDTO;
import hospital.server.NetworkConfig;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

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

    public Map<Integer, Integer> getPeopleInFloor() throws RemoteException {
        return this.controllerRMI.getPeopleInFloors();
    }
}
