package ServerChat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	public IChat requestAtendent(IChat client) throws RemoteException;
	public void requestJoin(IChat atendent) throws RemoteException;
	public void requestLeave(IChat atendent) throws RemoteException;
	public void freeAtendent(IChat atendent) throws RemoteException;	
}
