package rmi;

import static java.rmi.Naming.rebind;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class Server {

	public static void main(String[] args) {
		try {
			System.out.println("Initializing server...");
			Voting voting = new Voting();
			rebind("rmi://localhost:9091/Election", voting);
			System.out.println("Server initialized.");
		} catch (RemoteException | MalformedURLException ex) {
			System.err.println("An error occurred while initializing the server: ".concat(ex.getMessage()));
		}
	}

}
