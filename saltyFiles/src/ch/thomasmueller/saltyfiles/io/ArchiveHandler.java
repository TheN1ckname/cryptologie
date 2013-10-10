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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.PatternSet.NameEntry;

import ch.thomasmueller.saltyfiles.common.Constants;

/**
 * Helper class wrapping <b>ant tasks</b> to pack or unpack zip-files.
 * @author Thomas Mueller
 *  
 */
public class ArchiveHandler
{
	private static Logger log = LogManager.getLogger(ArchiveHandler.class.getName()); 

	/**
	 * Unpack a zip file into a given directory.
	 * 
	 * @param anArchivePath
	 *            A pathname representing a local zip file
	 * @param aDestinationDir
	 *            where to unpack the archive to
	 */

	public synchronized  static void unpack(String anArchivePath, String aDestinationDir)
	{
	
		final class Expander extends Expand
		{

			public Expander()
			{

				project = new Project();
				project.init();
				taskType = "unzip";
				taskName = "unzip";
				target = new Target();
			}
		}
		Expander expander = new Expander();
		expander.setSrc(new File(anArchivePath));
		expander.setDest(new File(aDestinationDir));
		expander.setOverwrite(true);
		expander.execute();
	}

	/**
	 * Packs all files or all files of a 
	 * directory <code>aSourceDir</code> to an archive.
	 * 
	 * @param anArchivePath
	 *            path of the archive including the name
	 * @param aSourceDirOrFile
	 *            to be packed
	 */
	public synchronized static void pack(String anArchivePath, String aSourceDirOrFile)
	{

		final class Compressor extends Zip
		{

			public Compressor()
			{

				project = new Project();
				project.init();
				taskType = "zip";
				taskName = "zip";
				target = new Target();
			}
		}
		Compressor compressor = new Compressor();
		compressor.setDestFile(new File(anArchivePath 
				+ Constants.IN_EXTENSION));

		File sourceFile = new File(aSourceDirOrFile);
		
		// include all files also hidden ones
		FileSet fileset = new FileSet();

		if (sourceFile.isFile())
		{
			fileset.setDir(sourceFile.getParentFile());
			NameEntry entryAll = fileset.createInclude();
			NameEntry entryAllHidden = fileset.createInclude();
			entryAll.setName(sourceFile.getName());
			if (log.isDebugEnabled())
			{
				log.debug("dir :"+ sourceFile.getParentFile()+", file :"
						+ sourceFile.getName());
			}
		}
		else
		{
			fileset.setDir(sourceFile);
			NameEntry entryAll = fileset.createInclude();
			NameEntry entryAllHidden = fileset.createInclude();
			entryAll.setName("**/*");
			entryAllHidden.setName("**/.*");
			if (log.isDebugEnabled())
			{
				if (log.isDebugEnabled())
				{
					log.debug("dir :"+ sourceFile.getName()+", file :"
							+ "all files");
				}
			}
		}
		compressor.addFileset(fileset);

		compressor.execute();
		log.info("in archive written to " + compressor.getDestFile());
	}
}