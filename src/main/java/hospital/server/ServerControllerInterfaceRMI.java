package hospital.server;



import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import hospital.dto.ElevatorInfoDTO;
import java.util.HashMap;

public interface ServerControllerInterfaceRMI extends Remote {

    public List<ElevatorInfoDTO> getElevatorsInfo() throws RemoteException;
    
    public void evacuateSystem() throws RemoteException;
    
    public HashMap <Integer, Integer> getPeopleInFloor() throws RemoteException;

}
