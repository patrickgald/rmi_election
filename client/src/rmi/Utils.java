package rmi;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.JOptionPane;

public final class Utils {
	
	private Utils() {
		
	}

	public static final void showMessage(String message) {
		showMessageDialog(null, message);
	}

	public static final String getAnswer(String message) {
		return showInputDialog(message);
	}

	public static final int getNumber(String message) {
		boolean validate;
		int result = -1;
		do {
			try {
				String answer = showInputDialog(message);
				result = Integer.parseInt(answer);
				validate = true;
			} catch (Exception ex) {
				showMessageDialog(null, "Please, insert a numeric value.");
				validate = false;
			}
		} while (!validate);
		return result;
	}

	public static final boolean getYesOrNo(String title, String message) {
		return (showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
	}

}
