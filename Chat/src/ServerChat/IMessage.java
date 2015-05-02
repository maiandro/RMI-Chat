package ServerChat;

import java.rmi.RemoteException;
import java.util.Calendar;

public interface IMessage {
	public void setMessage(String m) throws RemoteException;
	public String getMessage() throws RemoteException;
	public void setDate(Calendar c) throws RemoteException;
	public Calendar getDate() throws RemoteException;
}
