package ServerChat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements IServer{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<IChat> atendents;
	
	protected Server() throws RemoteException {
		super();
		atendents = new ArrayList<IChat>();
	}


	@Override
	public synchronized IChat requestAtendent(IChat client) {
		System.out.println("requestAtendent()");
		if (atendents.size() == 0) {
			System.out.println("Esperando atendente ... ");
			Thread t = new Thread (new Runnable (){

				@Override
				public void run() {
					while (atendents.size() == 0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} 
			});
			try {
				t.run();
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Atendente disponível");
		}
		
		return atendents.remove(0);
	}
	
	@Override
	public void requestJoin(IChat atendent) {
		System.out.println("requestJoin()");
		atendents.add(atendent);
	}

	@Override
	public void requestLeave(IChat atendent) {
		System.out.println("requestLeave()");
		if (atendents.remove(atendent)) 
			System.out.println("Atendent removido!");
	}

	@Override
	public void freeAtendent(IChat atendent) {
		System.out.println("freeAtendent()");
		atendents.add(atendent);
	}


}
