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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import ch.thomasmueller.saltyfiles.ui.command.CommandFactory;

/**
 * Class used to build the <code>Menu</code> of the
 * {@link ch.thomasmueller.saltyfiles.ui.MainFrame}.
 */
public class MenuBuilder
{

	/**
	 * Adds the built menu to the frame.
	 * 
	 * @param frame
	 *            the menu shoud be added.
	 */
	public static void addMenuBar(JFrame frame)
	{

		Controller controller = Controller.getInstance();

		JMenu menu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		//	Create the menu bar.
		JMenuBar menuBar = new JMenuBar();

		//	Build the System menu.
		menu = new JMenu("System");
		menu.setMnemonic(KeyEvent.VK_S);
		menuBar.add(menu);

		// System menuItem 1
		menuItem = new JMenuItem("Configure ...", KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));

		controller.registerCommandComponent(CommandFactory.SHOW_CONFIG,
				menuItem);
		menu.add(menuItem);

		// System menuItem 2
		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);

		controller.registerCommandComponent(CommandFactory.EXIT, menuItem);
		menu.add(menuItem);

		// Help menu
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);

		// Help menuItem 1
		menuItem = new JMenuItem("About ...", KeyEvent.VK_A);

		controller
				.registerCommandComponent(CommandFactory.SHOW_ABOUT, menuItem);

		menu.add(menuItem);

		menuBar.add(menu);

		frame.setJMenuBar(menuBar);
	}
}