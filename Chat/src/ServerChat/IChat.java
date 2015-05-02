package ServerChat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChat extends Remote{
	public void deliver(Message m) throws RemoteException;
	public void fistDeliver(Message m, IChat c) throws RemoteException;
}
