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

import java.io.File;

import javax.swing.JFileChooser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**
 * Helper class to provide a <code>JFileChooser</code> dialog used 
 * by several <code>Command</code>s.
 */

public class FileChooser
{

	private static Logger log = LogManager.getLogger(FileChooser.class
			.getName());

	/**
	 * Shows the UI-component.
	 * @param fileChooserMode either <code>JFileChooser.DIRECTORIES_ONLY</code>
	 * 		 or <code>JFileChooser.FILES_AND_DIRECTORIES</code>
	 * @return the file choosen
	 */
	public static File choose(int fileChooserMode)
	{

		File file = null;
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(fileChooserMode);
		
		int returnVal = fc.showOpenDialog(MainFrame.getInstance());

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			file = fc.getSelectedFile();
			if (log.isDebugEnabled())
			{
				log.debug("Opening: " + file.getAbsolutePath());
			}
			
		}
		return file;

	}
}