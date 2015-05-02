package AtendentChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import ServerChat.IChat;
import ServerChat.IServer;
import ServerChat.Message;

public class Atendent extends UnicastRemoteObject implements IChat {

	private static final long serialVersionUID = 1L;
	
	private static final String SIGNAL = "#CONNECTION_ENDED";
	private static final String host = "rmi://localhost:1099/Atendent";
	
	public String name;
	
	private chatGUI GUI;
	private IServer server;
	private ArrayList<Message> chat;

	
	protected Atendent(IServer server, String name) throws RemoteException {
		super();
		chat = new ArrayList<Message>();
		this.name = name;
		this.server = server;
	}
	
	public void startChat(IChat c) {
		GUI = new chatGUI(this,c);
		GUI.setVisible(true);
	}

	@Override
	public void deliver(Message m) { 
		if (m.getMessage().equals(SIGNAL)) {
			
			System.out.println ("Sinal recebido! Saindo ...");
			
			GUI.setVisible(false);
			GUI = null;
			chat = new ArrayList<Message>();
			
			try {
				if (!keepOn())
					destroy();
				else 
					server.freeAtendent(this);
			} catch (Throwable e) { e.printStackTrace(); } 
			
			return;
		}
		
		chat.add(m);
		GUI.update();
	}
	
	public void destroy () throws Throwable {
		server.requestLeave(this);
		Naming.unbind(host);

		this.finalize();
		
		new Thread (new Runnable() {
			@Override
			public void run() {
				System.out.println("Tchau!");
				System.exit(0);
			}
		}).start();
	}
	
	public boolean keepOn () {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Continuar atendendo? (S/N)");
		
		while (true) {
			String response = null;
			try {
				response = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (response.equals("n") || response.equalsIgnoreCase("N")) return false;
			if (response.equals("s") || response.equals("S")) return true;
		}
		
	}
	
	public ArrayList<Message> getChat() {
		return chat;
	}

	@Override
	public void fistDeliver(Message m, IChat c) throws RemoteException {
		startChat(c);
		deliver(m);
	}

}
