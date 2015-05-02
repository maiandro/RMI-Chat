package AtendentChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import ServerChat.IServer;

public class Main {
	
	private static final String host = "rmi://localhost:1099/Atendent";
	private static final String shost = "rmi://localhost:1099/Server";
	
	public static void main(String[] args) throws NotBoundException, AlreadyBoundException, IOException {
		
		System.out.println("--------ATENDENT--------\n");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Nome: ");
		String name = br.readLine();
		
		IServer server = (IServer) Naming.lookup(shost);
		
		Atendent a = new Atendent(server, name);
		
		Naming.rebind(host, a);
		
		server.requestJoin(a);			
	}

}
