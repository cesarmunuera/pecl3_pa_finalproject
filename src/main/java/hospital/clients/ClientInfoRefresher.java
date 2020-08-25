package hospital.clients;

import java.rmi.RemoteException;

import hospital.Configuration;

public class ClientInfoRefresher extends Thread {
	
	Front clientUI;
	ClientController controller;

	public ClientInfoRefresher(Front clientUI) {
		this.clientUI = clientUI;
		this.controller = this.clientUI.getController();
	}
	
	
	@Override
	public void run() {
		while (true) {
			try {
				sleep(Configuration.CLIENT_REFRESH_INFO_MS);
				this.clientUI.setElevatorsInfo(this.controller.getElevatorsInfo());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	

}