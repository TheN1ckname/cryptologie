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

import java.awt.Component;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The main frame of the program containing the 
 * {@link ch.thomasmueller.saltyfiles.ui.MainView}.
 * 
 * @author Thomas Mueller
 *  
 */
public class MainFrame extends JFrame
{
	private static Logger log= LogManager.getLogger(MainFrame.class.getName());
	
	
	private static MainFrame instance;
	
	/**
	 * Returns the only instance of this <b>Singleton</b>.
	 * @return the frame
	 */
	public static MainFrame getInstance()
	{

		if (instance == null)
		{
			instance = new MainFrame();
		}
		return instance;
	}
	
	private MainFrame()
	{
		JPanel mainView = null;
		try
		{
			 mainView = new MainView().init();
		}
		catch (Exception e)
		{
			log.fatal(e);
		}
		log.debug("start");
		getContentPane().add(mainView);
		
		// Menu
		MenuBuilder.addMenuBar(this);
				
		setIconImage(readImageIcon("fsLogo64.png").getImage());	
		setTitle("SaltyFiles");
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		locateOnScreen(this);
		setVisible(true);
	}	
	
    /**
     * Looks up and answers an icon for the specified filename suffix.<p>
     */
    public static ImageIcon readImageIcon(String filename) {
        URL url =
        	MainFrame.class.getClassLoader().getResource("images/" + filename);
        if (log.isDebugEnabled())
        {
        	log.debug("image url = '" + url + "'");
        }
        if (url == null)
        {
        	try
			{
				url = new URL("file:///./src/images/" +
						filename);
			} catch (MalformedURLException e)
			{				
				e.printStackTrace();
			}
        }
        
        return new ImageIcon(url);
    }

    /**
     * Locates the given component on the screen's center.
     */
    protected void locateOnScreen(Component component) {
        Dimension paneSize = component.getSize();
        Dimension screenSize = component.getToolkit().getScreenSize();
        component.setLocation(
            (screenSize.width  - paneSize.width)  / 2,
            (screenSize.height - paneSize.height) / 2);
    }
}