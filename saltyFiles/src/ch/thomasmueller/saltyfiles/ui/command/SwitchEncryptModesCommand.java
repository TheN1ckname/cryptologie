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

import javax.swing.JRadioButton;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Switch all Controls disabled used for decrytion and enable those used
 * for encryption.
 * 
 * @see ch.thomasmueller.saltyfiles.ui.command.AbstractSwitchCommand
 * @author Thomas Mueller
 *  
 */
public class SwitchEncryptModesCommand extends AbstractSwitchCommand
{

	private Logger log = LogManager.getLogger(getClass().getName());

	/**
	 * Switches betwenn encript and decript mode.
	 * @see ch.thomasmueller.saltyfiles.ui.command.Command#doCommand(ActionEvent)
	 */
	public void doCommand(ActionEvent event)
	{

		boolean isEncryptionMode = ((JRadioButton) event.getSource())
				.isSelected();
		log.debug("SwitchEncryptModesCommand: isEncryptionMode: " + isEncryptionMode);
		super.switchMode(isEncryptionMode);
	}

}