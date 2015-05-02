package ClientChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import ServerChat.IChat;
import ServerChat.IServer;

public class Main {
	
	private static final String host = "rmi://localhost:1099/Client";
	private static final String shost = "rmi://localhost:1099/Server";
	
	public static void main(String[] args) throws NotBoundException, AlreadyBoundException, IOException {

		System.out.println("---------CLIENT---------\n");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Nome: ");
		String name = br.readLine();
		
		Client c = new Client(name);
		
		Naming.rebind(host, c);
		IServer server = (IServer) Naming.lookup(shost);

		IChat atendent = server.requestAtendent(c);
		
		c.startChat(atendent);
	}

}
