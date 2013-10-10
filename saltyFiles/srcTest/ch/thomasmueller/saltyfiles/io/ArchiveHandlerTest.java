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
package ch.thomasmueller.saltyfiles.io;

import java.io.File;

import junit.framework.TestCase;
import ch.thomasmueller.saltyfiles.common.Constants;


/**
 * @author Thomas Mueller
 *
 */
public class ArchiveHandlerTest extends TestCase
{

	public void testPackAndUnpack()
	{
		File archive = new File("./test/archive/Packed.zip");
		
		ArchiveHandler.pack(archive.getPath(), "./test/archive/source");
		assertTrue(new File(archive.getPath() + Constants.IN_EXTENSION).exists());
		ArchiveHandler.unpack("./test/archive/Packed.zip" + Constants.IN_EXTENSION,
					"./test/archive/target");
		archive.delete();
		assertTrue(new File("./test/archive/target/.hidden").isHidden());
		File targetOne = new File("./test/archive/target/one.txt");
		File targetTwo = new File("./test/archive/target/two.txt");
		assertTrue(targetOne.exists());
		assertTrue(targetTwo.exists());
		targetOne.delete();
		targetTwo.delete();				
	}



}
