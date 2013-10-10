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

package ch.thomasmueller.saltyfiles.ui.command;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import ch.thomasmueller.saltyfiles.ui.MainFrame;


/**
 * Command used to exit the program. 
 * 
 * @author Thomas Mueller
 *
 */
public class ExitCommand implements Command
{

	/**
	 * Exits the application if the user chooses the <b>YES</b> option on 
	 * the appearing <code>OptionDialog</code>.
	 *  
	 * @see ch.thomasmueller.saltyfiles.ui.command.Command#doCommand(ActionEvent)
	 */
	public void doCommand(ActionEvent event)
	{
		int option = JOptionPane.showConfirmDialog(MainFrame.getInstance(),
	            "Do you really want to exit ?", "Exit",
	        JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE);
		if ((option == JOptionPane.YES_OPTION))
		{			
			System.exit(0);
		}
	}

}
