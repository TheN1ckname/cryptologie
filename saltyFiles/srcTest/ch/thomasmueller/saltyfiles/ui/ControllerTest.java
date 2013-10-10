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

import java.util.Map;

import javax.swing.JTextField;

import junit.framework.TestCase;
import ch.thomasmueller.saltyfiles.data.DataFieldImpl;


/**
 * @author Thomas Mueller
 *
 */
public class ControllerTest extends TestCase
{

	public void testGetInstance()
	{
		assertNotNull(Controller.getInstance());
		assertSame(Controller.getInstance(), Controller.getInstance());
	}

	public void testRegisterDataComponent()
	{
		DataFieldImpl df = new DataFieldImpl();
		df.init("aField");
		
		JTextField tf1 = new JTextField("hello");
		JTextField tf2 = new JTextField("world");
		
		Controller.getInstance().registerDataComponent(df, tf1);
		Controller.getInstance().registerDataComponent(df, tf2);
		
		Map cam = Controller.getInstance().getComponentAccessMap();
		assertNotNull(cam.get(tf1));
		assertNotNull(cam.get(tf2));
		assertSame(cam.get(tf1), cam.get(tf2));
	}

	public void testRegisterCommandComponent()
	{

	}

}
