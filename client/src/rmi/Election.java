package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Election extends Remote {

	public void vote(String candidate, String hashMD5) throws RemoteException;

	public int result(String candidate) throws RemoteException;

	public List<String> getCandidates() throws RemoteException;

}