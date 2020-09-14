package rmi;

import static rmi.Utils.getAnswer;
import static rmi.Utils.getNumber;
import static rmi.Utils.getYesOrNo;

import java.util.List;

public final class Menu {

	private Menu() {

	}

	public static final int VOTE = 1;
	public static final int RESULTS = 2;
	public static final int LEAVE = 3;
	public static final int CANCEL = 20;

	public static int mainMenu() {
		while (true) {
			try {
				StringBuilder sb = new StringBuilder("Please, choose an option:\n");
				sb.append("1 - Vote\n");
				sb.append("2 - See results\n");
				sb.append("3 - Leave\n");
				String option = getAnswer(sb.toString());
				int opt = Integer.parseInt(option);
				if (opt >= 1 && opt <= 3) {
					return opt;
				}
			} catch (Exception ex) {
				return CANCEL;
			}
		}
	}

	public static String setupServerURL(String serverURL) {
		while (!getYesOrNo("Server url", "Current server url: ".concat(serverURL).concat("\nConfirm?"))) {
			serverURL = getAnswer("Type the server url (format: rmi://server/Election): ");
		}
		return serverURL;
	}

	public static String getVoterName() {
		return getAnswer("Please, type your full name: ");
	}

	public static String getCandidateName(List<String> candidates) {
		int num;
		String candidate;
		do {
			StringBuilder sb = new StringBuilder("Please, pick a candidate: \n");
			for (int i = 0; i < candidates.size(); i++) {
				sb.append(String.format("%d - %s\n", (i + 1), candidates.get(i)));
			}
			num = getNumber(sb.toString());
			candidate = candidates.get(num - 1);
		} while (num < 1 || num > candidates.size());

		return candidate;
	}

	public static boolean getConfirmation(Voter voter, String candidate) {
		StringBuilder sb = new StringBuilder("Confirm vote?\n\n");
		sb.append("Voter name: ".concat(voter.getName()).concat("\n"))
				.append("Voter hash: ".concat(voter.getHash()).concat("\n"))
				.append("Candidate: ".concat(candidate).concat("\n\n"))
				.append("WARNING: \nAfter clicking \"Yes\", please wait until a confirmation message is displayed.\n")
				.append("This may take up to 30 seconds.");

		return getYesOrNo("Confirmation", sb.toString());
	}

}
