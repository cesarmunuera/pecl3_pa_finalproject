package hospital.server;

import hospital.Elevator;
import hospital.JarvisSystem;
import hospital.dto.ElevatorInfoDTO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerController extends UnicastRemoteObject implements ServerControllerInterfaceRMI {

	private static final long serialVersionUID = 1L;
	private JarvisSystem jarvisSystem;

    public ServerController(JarvisSystem jarvisSystem) throws RemoteException {
        this.jarvisSystem = jarvisSystem;
    }

    @Override
    public List<ElevatorInfoDTO> getElevatorsInfo() {
        List<ElevatorInfoDTO> elevatorsInfosDto = new ArrayList<>();
        for (Elevator elevator : this.jarvisSystem.getElevators()) {
            elevatorsInfosDto.add(new ElevatorInfoDTO(elevator));
        }
        elevatorsInfosDto.add(new ElevatorInfoDTO(this.jarvisSystem.getElevatorBackUp()));
        return elevatorsInfosDto;
    }

    @Override
    public HashMap<Integer, Integer> getPeopleInFloors() {
        return this.jarvisSystem.getPeopleInAllFloors();
    }

    @Override
    public void evacuateSystem() {
        this.jarvisSystem.startEvacuation();
    }

}
