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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Helper class to for creating <code>FileInputStream</code>s.
 * @author Thomas Mueller
 *
 */
public class InputFileHandler
{
	private FileInputStream fis;
	private Logger log = LogManager.getLogger(this.getClass().getName());
	private long fileSize = 0L;
	
	
	
	public InputFileHandler(String anInputFileName)
		throws FileNotFoundException
	{
	
		File file = new File(anInputFileName);
		if (file.isFile() && file.canRead())
		{
			 fis = new FileInputStream(file);
		}
		else
		{
			throw new FileNotFoundException(
					"file cannot be read or is directory: " + anInputFileName);
		}
		fileSize = file.length();
	}
	public FileInputStream getFileInputStream()
	{
		return fis;
	}
	public void close() throws IOException
	{
		fis.close();		
	}
	public long getFileSize()
	{
		return fileSize;
	}
}