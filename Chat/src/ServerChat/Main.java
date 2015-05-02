package ServerChat;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {
	
	private static final int port = 1099;
	private static final String host = "rmi://localhost:1099/Server";
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		
		System.out.println("---------SERVER---------");
		
		Server s = new Server();
		
		LocateRegistry.createRegistry(port);
		Naming.rebind(host, s);
	}

}
