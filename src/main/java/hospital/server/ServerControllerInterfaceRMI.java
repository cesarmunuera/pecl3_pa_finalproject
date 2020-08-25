package hospital.server;



import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerControllerInterfaceRMI extends Remote {

    public void getElevatorsInfo() throws RemoteException;
    
    public void evacuateSystem() throws RemoteException;

}
