package ClientChat;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import ServerChat.IChat;
import ServerChat.Message;

public class Client extends UnicastRemoteObject implements IChat {

	private static final long serialVersionUID = 1L;
	
	private static final String SIGNAL = "#CONNECTION_ENDED";
	private static final String host = "rmi://localhost:1099/Client";
	
	public String name;
	
	private chatGUI GUI;
	private ArrayList<Message> chat;
	
	protected Client(String name) throws RemoteException {
		super();
		chat = new ArrayList<Message>();
		this.name = name;
	}

	public void startChat(IChat a) {
		GUI = new chatGUI(a,this);
		GUI.setVisible(true);
	}
	
	@Override
	public void deliver(Message m) {
		if (m.getMessage().equals(SIGNAL)) {
			
			System.out.println ("Sinal recebido! Saindo ....");
			
			GUI.setVisible(false);
			GUI = null;
			
			try {
				destroy();
			} catch (Throwable e) { e.printStackTrace(); }
			return;
		}
		chat.add(m);
		GUI.update();
	}
	
	public ArrayList<Message> getChat() {
		return chat;
	}
	
	public void destroy () throws Throwable {
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

	@Override
	public void fistDeliver(Message m, IChat c) throws RemoteException {
	}

}
