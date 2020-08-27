package hospital.clients;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainClient {

    public static void main(String args[]) {

        Front clientUI;
        ClientInfoRefresher infoRefresher;
        try {
            clientUI = new Front();
            infoRefresher = new ClientInfoRefresher(clientUI);
            infoRefresher.start();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    clientUI.setVisible(true);
                }
            });
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
        }

    }
}
