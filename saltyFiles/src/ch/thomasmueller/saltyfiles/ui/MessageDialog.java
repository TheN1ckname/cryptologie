/*
 www.thomasmueller.ch

 Copyright (C) 2004 Thomas Mueller

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */

package ch.thomasmueller.saltyfiles.ui;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Helper class to provied simple message dialogs that can only be confirmed.
 * 
 * @author Thomas Mueller
 *  
 */
public class MessageDialog
{

	private static final int INFO = JOptionPane.INFORMATION_MESSAGE;

	private static final int WARN = JOptionPane.WARNING_MESSAGE;

	private static final int ERROR = JOptionPane.ERROR_MESSAGE;

	/**
	 * Shows a info dialog with the give string as Message
	 * 
	 * @param message
	 *            the message dispayed
	 */
	public static void showInfo(String message)
	{

		showMessage(INFO, "Info ...", message, null);
	}
	/**
	 * Shows a info dialog with the give string as Message
	 * 
	 * @param message
	 *            the message dispayed
	 */
	public static void showInfo(String message, Icon icon)
	{

		showMessage(INFO, "Info ...", message, icon);
	}
	/**
	 * Shows a warn dialog with the give string as Message
	 * 
	 * @param message
	 *            the message dispayed
	 */
	public static void showWarn(String message)
	{

		showMessage(WARN, "Warning ...", message, null);
	}

	/**
	 * Shows a error dialog with the give string as Message
	 * 
	 * @param message
	 *            the message dispayed
	 */
	public static void showError(String message)
	{

		showMessage(ERROR, "Error ...", message, null);
	}

	private static void showMessage(int level, String title, String message, Icon icon)
	{

		if (message == null)
		{
			message = "NULL";
		}

		JOptionPane.showMessageDialog(MainFrame.getInstance(), message, title,
				level, icon);
	}
}