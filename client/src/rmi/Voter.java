package rmi;

import static java.security.MessageDigest.getInstance;
import static rmi.Utils.showMessage;

import java.nio.charset.StandardCharsets;

public class Voter {

	private String name;
	private String hash;

	public Voter(String name) {
		this.name = name.toUpperCase();
		this.hash = getHashMD5();
	}

	private String getHashMD5() {
		try {
			byte[] nameBytes = getName().getBytes(StandardCharsets.UTF_8);
			byte[] digest = getInstance("MD5").digest(nameBytes);
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				String hex = Integer.toHexString(0xFF & b);
				sb.append(hex.length() == 1 ? '0' : hex);
			}
			return sb.toString();
		} catch (Exception ex) {
			showMessage("An error occurred during the hash MD5 generation: " + ex.getMessage());
			System.exit(0);
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public String getHash() {
		return hash;
	}

}
