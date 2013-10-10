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

package ch.thomasmueller.saltyfiles.transformer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author Thomas Mueller
 */
public class SaltTest extends TestCase
{

	private Logger log = LogManager.getLogger(this.getClass().getName());

	public void testAppendSalt() throws Exception
	{

		File d = new File("test/d.jar");
        String allLinesBefore = readfile(d);
        
		byte[] bytes = Salt.createRandom();
		Salt.appendSalt(d, bytes);
		readfile(d);
		
		byte[] bytesAfter = Salt.readSalt(d);
		readfile(d);
		for (int i = 0; i < bytes.length; i++)
		{
			log.debug("idx" + i + "old = " + bytes[i] + " new = "
							+ bytesAfter[i]);
			assertEquals(bytes[i], bytesAfter[i]);
		}
		
		String allLinesAfter = readfile(d);
		assertEquals(allLinesBefore, allLinesAfter);
	}

	/**
	 * @param theFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String readfile(File theFile) throws FileNotFoundException, IOException
	{
		FileInputStream fis = new FileInputStream(theFile);
		

		System.out.println(fis.available());

						
		BufferedReader dReader = new BufferedReader(new FileReader(theFile));
        String line = "";
        String allLines = "";
        while ((line = dReader.readLine()) != null)
        {
        	allLines = allLines + line;
        }
        dReader.close();
        
		return allLines;
	}

}