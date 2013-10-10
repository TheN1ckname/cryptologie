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
package ch.thomasmueller.saltyfiles;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ch.thomasmueller.saltyfiles.ui.MainFrame;

import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;

/**
 * This class contains the <code>main</code> method to 
 * start the application.
 * 
 * It will show a simple user interface which can be used 
 * for encryption or decryption of files stored  
 * on the file system.
 * 
 * 
 * 
 * @author Thomas Mueller
 *  
 */
public class SaltyFiles
{

	private static Logger log = LogManager
			.getLogger(SaltyFiles.class.getName());

	/**
	 * Starts the program.
	 * @param args no arguments needed
	 */
	  
	 
	public static void main(String[] args)
	{

		PropertyConfigurator.configure("log4j.properties");

		try
		{
			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
		}

		catch (UnsupportedLookAndFeelException ulafe)
		{
			log.warn("missing looks lib ... Using Metal");

		}

		MainFrame mainFrame = MainFrame.getInstance();
		mainFrame.show();
	}
}