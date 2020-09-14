package rmi;

import static java.util.Objects.nonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voting extends UnicastRemoteObject implements Election {

	private static final long serialVersionUID = 1L;

	private Map<String, Integer> candidates;
	private List<String> voters;

	private void loadCandidates() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("data_candidates.dat")));
		if (nonNull(ois.readObject())) {
			this.candidates = (Map<String, Integer>) ois.readObject();
		}
		ois.close();
	}

	private void loadVoters() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("data_voters.dat")));
		if (nonNull(ois.readObject())) {
			this.voters = (List<String>) ois.readObject();
		}
		ois.close();
	}

	private void saveCandidates() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("data_candidates.dat")));
		if (nonNull(this.candidates)) {
			oos.writeObject(candidates);
		}
		oos.close();
	}

	private void saveVoters() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("data_voters.dat")));
		if (nonNull(this.candidates)) {
			oos.writeObject(voters);
		}
		oos.close();
	}

	public Voting() throws RemoteException {
		candidates = new HashMap<>();
		voters = new ArrayList<>();
		try {
			loadCandidates();
			loadVoters();
		} catch (Exception ex) {
			candidates = new HashMap<>();
			voters = new ArrayList<>();
			candidates.put("Bruce Lee", 0);
			candidates.put("Chuck Norris", 0);
			candidates.put("Arnold Schwarzenegger", 0);
			candidates.put("Jean-Claude Van Damme", 0);
		}
	}

	@Override
	public void vote(String candidate, String hashMD5) throws RemoteException {
		if (voters.contains(hashMD5)) {
			throw new RemoteException("This person has already voted.");
		}
		voters.add(hashMD5);
		candidates.put(candidate, result(candidate) + 1);

		try {
			saveCandidates();
			saveVoters();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public int result(String candidate) throws RemoteException {
		return candidates.get(candidate);
	}

	@Override
	public List<String> getCandidates() throws RemoteException {
		return new ArrayList<>(candidates.keySet());
	}
}