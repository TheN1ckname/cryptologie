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
import java.io.File;

import javax.swing.JFileChooser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ch.thomasmueller.saltyfiles.data.DataField;
import ch.thomasmueller.saltyfiles.data.DataModel;
import ch.thomasmueller.saltyfiles.ui.FileChooser;

/**
 * Opens a <code>FileChooser</code> to select a target directory. 
 * @author Thomas Mueller
 *  
 */
public class ChooseEncryptionTargetDirCommand implements Command
{

	Logger log = LogManager.getLogger(this.getClass().getName());

	/**
	 * Chooses the target directory to place the encrypted data to.
	 * 
	 * @see ch.thomasmueller.saltyfiles.ui.command.Command#doCommand(ActionEvent)
	 */
	public void doCommand(ActionEvent event)
	{
			
		File file = FileChooser.choose(JFileChooser.DIRECTORIES_ONLY);
		if (file != null)
		{
			DataField encTargetDirDataField = DataModel.getInstance()
					.getDataField(DataModel.ENCRYPT_TARGET_DIR_STRING);
			if (encTargetDirDataField == null)
			{
				throw new RuntimeException("DataField not registered " 
						+ DataModel.ENCRYPT_TARGET_DIR_STRING);			
			}
			else
			{
				encTargetDirDataField.setValue(file.getAbsolutePath());
			}
		}
		else
		{
			log.debug("Open command cancelled by user.");
		}
	}

}