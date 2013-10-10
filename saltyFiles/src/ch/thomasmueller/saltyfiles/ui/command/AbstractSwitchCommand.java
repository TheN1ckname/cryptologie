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

import javax.swing.AbstractButton;

import ch.thomasmueller.saltyfiles.data.DataModel;
import ch.thomasmueller.saltyfiles.ui.Controller;

/**
 * Switches between encryption and decryption mode. 
 * 
 * @author Thomas Mueller
 *  
 */
public abstract class AbstractSwitchCommand implements Command
{

	/**
	 * Set all controls enabled or disabled according to the mode.
	 * 
	 * @param isEncryptMode true if encryption is used, false otherwise.
	 */
	protected void switchMode(boolean isEncryptMode)
	{

		Controller controller = Controller.getInstance();
		DataModel dataModel = DataModel.getInstance();

		// disable/enable

		// data input fields
		controller.setAccessable(dataModel
				.getDataField(DataModel.DECRYPT_SOURCE_ARCHIVE_STRING),
				!isEncryptMode);
		controller.setAccessable(dataModel
				.getDataField(DataModel.DECRYPT_TARGET_DIR_STRING),
				!isEncryptMode);

		controller.setAccessable(dataModel
				.getDataField(DataModel.ENCRYPT_SOURCE_STRING), isEncryptMode);
		controller.setAccessable(dataModel
				.getDataField(DataModel.ENCRYPT_TARGET_ARCHIVE_STRING),
				isEncryptMode);
		controller.setAccessable(dataModel
				.getDataField(DataModel.ENCRYPT_TARGET_DIR_STRING),
				isEncryptMode);
		// model
		dataModel.getDataField(DataModel.ENCRYPT_BOOLEAN).setValue(
				Boolean.valueOf(isEncryptMode));

		// action components

		AbstractButton button = controller
				.getActionComponent(CommandFactory.CHOOSE_DECRYPTION_ARCHIV);
		button.setEnabled(!isEncryptMode);

		button = controller
				.getActionComponent(CommandFactory.CHOOSE_DECRYPTION_DIR);
		button.setEnabled(!isEncryptMode);

		button = controller
				.getActionComponent(CommandFactory.CHOOSE_ENCRYPTION_DIR);
		button.setEnabled(isEncryptMode);

		button = controller
				.getActionComponent(CommandFactory.CHOOSE_ENCRYPTION_FILES);
		button.setEnabled(isEncryptMode);
	}

}