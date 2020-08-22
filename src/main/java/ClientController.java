
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientController {

    ServerControllerInterfaceRMI controllerRMI;

    public ClientController() throws RemoteException, NotBoundException, MalformedURLException {

        this.controllerRMI = (ServerControllerInterfaceRMI) Naming.lookup(NetworkConfig.CLIENT_CONTROLLER_URI);
    }
    
    
    //TODO: CREAR LOS METODOS PARA MOSTRAR EN EL FRONT LOS DATOS
    //QUE SAQUEMOS DEL SERVER

}
