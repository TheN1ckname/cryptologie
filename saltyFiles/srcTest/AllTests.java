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
import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.PropertyConfigurator;



/**
 * @author Thomas Mueller
 *
 */
public class AllTests
{

	public static void main(String[] args)
	{

	}

	public static Test suite()
	{
		PropertyConfigurator.configure("log4j.properties");

		TestSuite suite = new TestSuite("Test for default package");
		suite.addTest(ch.thomasmueller.saltyfiles.transformer.AllTests.suite());
		suite.addTest(ch.thomasmueller.saltyfiles.io.AllTests.suite());
		suite.addTest(ch.thomasmueller.saltyfiles.data.AllTests.suite());
		suite.addTest(ch.thomasmueller.saltyfiles.ui.AllTests.suite());
		//$JUnit-BEGIN$
		//$JUnit-END$
		return suite;
	}
}
