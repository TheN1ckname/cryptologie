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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Helper class to for creating <code>FileOutputStream</code>s.
 * @author Thomas Mueller
 *  
 */
public class OutputFileHandler
{

	private FileOutputStream fos;

	public OutputFileHandler(String anOutputFileName)
			throws FileNotFoundException, IOException
	{

		File file = new File(anOutputFileName);
		if (!file.exists())
		{
			file.createNewFile();
		}
		if (file.isFile() && file.canWrite())
		{
			fos = new FileOutputStream(file);
		}
		else
		{
			throw new FileNotFoundException(
					"cannot write output file" + anOutputFileName);
		}
	}

	public FileOutputStream getFileOutputStream()
	{

		return fos;
	}
	public void close() throws IOException
	{
		fos.flush();
		fos.close();		
	}
}