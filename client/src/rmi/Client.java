package rmi;

import static java.lang.System.currentTimeMillis;
import static java.rmi.Naming.lookup;
import static rmi.Menu.getCandidateName;
import static rmi.Menu.getConfirmation;
import static rmi.Menu.getVoterName;
import static rmi.Menu.mainMenu;
import static rmi.Menu.setupServerURL;
import static rmi.Utils.showMessage;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Client {

	private static String serverUrl = "rmi://localhost:9091/Election";
	private static Election election;

	private static void vote() {
		try {
			List<String> candidates = election.getCandidates();

			Voter voter = new Voter(getVoterName());
			showMessage("Your voter code (Hash MD5) is: ".concat(voter.getHash()));
			String candidate = getCandidateName(candidates);

			if (!getConfirmation(voter, candidate))
				return;

			boolean voting = true;
			long timeMillis = currentTimeMillis();

			while (voting) {
				if ((currentTimeMillis() - timeMillis) > 30000) {
					StringBuilder sb = new StringBuilder("Your vote hasn't been computed -- time limit exceeded.\n");
					sb.append("Please, try again later.");
					showMessage(sb.toString());
					continue;
				}
				try {
					election.vote(candidate, voter.getHash());
					voting = false;
					showMessage("Vote computed successfully.");
				} catch (RemoteException ex) {
					if (ex.getMessage().contains("This person has already voted.")) {
						showMessage("This person has already voted.");
						return;
					}
					System.err.println("Error to compute vote: ".concat(ex.getMessage()));
				}
			}
		} catch (Exception ex) {
			showMessage("An error occurred during the voting. Returning to main menu...");
		}
	}

	private static void results() {
		try {
			List<String> candidates = election.getCandidates();

			StringBuilder sb = new StringBuilder("Result:\n\n");
			for (String s : candidates) {
				int votes = election.result(s);
				sb.append(s.concat(": ").concat(Integer.toString(votes)).concat(" votes.\n"));
			}
			showMessage(sb.toString());
		} catch (Exception ex) {
			showMessage("An error occurred while retrieving the results. Returning to main menu...");
		}

	}

	public static void main(String[] args) {

		boolean validate = false;
		do {
			serverUrl = setupServerURL(serverUrl);
			try {
				election = (Election) lookup(serverUrl);
				validate = true;
			} catch (MalformedURLException | RemoteException | NotBoundException ex) {
				ex.printStackTrace();
				showMessage("Failed to connect to server. Please check the url.");
				validate = false;
			}
		} while (!validate);

		boolean running = true;
		while (running) {
			int option = mainMenu();
			if (option == Menu.VOTE) {
				vote();
			} else if (option == Menu.RESULTS) {
				results();
			} else if (option == Menu.LEAVE || option == Menu.CANCEL) {
				running = false;
			}
		}
	}
}