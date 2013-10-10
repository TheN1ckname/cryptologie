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

package ch.thomasmueller.saltyfiles.ui.command;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.InterruptedIOException;

import javax.crypto.Cipher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ch.thomasmueller.saltyfiles.common.Constants;
import ch.thomasmueller.saltyfiles.data.DataField;
import ch.thomasmueller.saltyfiles.data.DataModel;
import ch.thomasmueller.saltyfiles.io.ArchiveHandler;
import ch.thomasmueller.saltyfiles.io.InputFileHandler;
import ch.thomasmueller.saltyfiles.io.OutputFileHandler;
import ch.thomasmueller.saltyfiles.transformer.Salt;
import ch.thomasmueller.saltyfiles.transformer.Transformer;
import ch.thomasmueller.saltyfiles.ui.ArchivProgressDialog;
import ch.thomasmueller.saltyfiles.ui.MessageDialog;
import ch.thomasmueller.saltyfiles.ui.TaskProgress;

/**
 * This is <code>Command</code> implementing the main task of the application.
 * 
 * It encrypts or decrypts data according to the state of the 
 * {@link DataModel}.
 * 
 * The password is checked first.
 * <p>
 * If encrytion mode is use the following task will be completed:
 * <ul>
 * <li>Packing the data to a temporary archive.
 * <li>Transform it to an ecrypted archive.
 * <li>Delete the temporary archive.
 * <ul>
 * 
 * If decrytion mode is use the following task will be completed:
 * <ul>
 * <li>Transform the encrypted to a temporary decrypted archive.
 * <li>Upack the archive.
 * <li>Delete the temporary archive.
 * <ul>
 * 
 * Animation will be started to inform the user about the currenct status 
 * of the transformation.
 * 
 * @author Thomas Mueller
 *  
 */
public class TransformCommand implements Command
{

	private char[] validatedPwd = null;

	private Logger log = LogManager.getLogger(getClass().getName());

	/**
	 * Transforms the data according to the selected mode.
	 *
	 * The transformation will be started in a separate <code>Thread</code>.
	 *  
	 * @see ch.thomasmueller.saltyfiles.ui.command.Command#doCommand(ActionEvent)
	 */
	public void doCommand(ActionEvent event)
	{

		log.debug("start transformation ...");

		final DataModel dataModel = DataModel.getInstance();

		// check password

		boolean pwdOK = checkPassword(dataModel);

		// check all data entered to start transformation
		if (pwdOK)
		{

			
			new Thread()
			{

				public void run()
				{

					try
					{

						if ((dataModel.getDataField(DataModel.ENCRYPT_BOOLEAN) != null)
								&& (dataModel.getDataField(
										DataModel.ENCRYPT_BOOLEAN).getValue() instanceof Boolean))
						{
							boolean useEncryptionMode = ((Boolean) dataModel
									.getDataField(DataModel.ENCRYPT_BOOLEAN)
									.getValue()).booleanValue();
							// Encrypt
							if (useEncryptionMode)
							{

								encryptData(dataModel);
							}
							// Decrypt
							else
							{
								decryptData(dataModel);
							}
							String msg = "Finished transformation successfully ...";
							log.info(msg);
							MessageDialog.showInfo(msg);
						} else
						{
							String msg = "please, select mode !!!";
							log.info(msg);
							MessageDialog.showInfo(msg);

						}						
					} 
					catch (InterruptedIOException iioe)
					{
						log.info("... canceled");
						MessageDialog.showInfo("Transformation process canceled ...");
					}
					catch (Exception e)
					{
						log.fatal(e);
						MessageDialog.showError(e.toString());
					}
				}
			}.start();
			
		
			
		} else
		{
			log.error("invalid password !!!");
			MessageDialog.showError("Invalid password, try again ...");
		}

	}

	/**
	 * Starts decrytion.
	 * @param dataModel hoding data which archive to decrypt and where to 
	 * place encrypted files.
	 */
	synchronized private void decryptData(DataModel dataModel) throws Exception
	{

		log.info("starting decryption of data");
		

		DataField sourceArchive = dataModel
				.getDataField(DataModel.DECRYPT_SOURCE_ARCHIVE_STRING);

		DataField targetDir = dataModel
				.getDataField(DataModel.DECRYPT_TARGET_DIR_STRING);

		if (sourceArchive.getValue() != null && targetDir.getValue() != null)
		{
			

			
			File sourceArchiveFile = new File(sourceArchive.getValue()
					.toString());
			
			
			if (log.isDebugEnabled())
			{
				log.debug("file.length before delete:" +sourceArchiveFile.length());
			}
			
			byte[] salt = Salt.readSalt(sourceArchiveFile);
			
			if (log.isDebugEnabled())
			{
				log.debug("file.length after delete:" +sourceArchiveFile.length());
			}
			
			File targetArchiveFile = new File(sourceArchiveFile
					+ Constants.OUT_EXTENSION);

			if (log.isDebugEnabled())
			{
				log.debug("decrypt : source : " + sourceArchiveFile);
				log.debug("decrypt : target : " + targetArchiveFile);
			}

			// decrypt

			InputFileHandler ifh = new InputFileHandler(sourceArchiveFile
					.toString());
			OutputFileHandler ofh = new OutputFileHandler(targetArchiveFile
					.toString());

			Transformer transformer = new Transformer(validatedPwd, salt);
			transformer.transform(Cipher.DECRYPT_MODE, ifh, ofh);
			
			TaskProgress taskProgress = new TaskProgress();
			taskProgress.setMessage("Please wait ...");
			taskProgress.setTitle("Unpacking ...");			
			taskProgress.setDone(false);
			ArchivProgressDialog.show(taskProgress);
			
			ArchiveHandler.unpack(targetArchiveFile.toString(), targetDir
					.getValue().toString());
			// delete temporary decripted output archive
			targetArchiveFile.delete();
			taskProgress.setDone(true);
		}

	}

	
	/**
	 * Starts encrytion.
	 * @param dataModel hoding data which data to encrypt and where 
	 * to place the target archive.
	 */
	 
	synchronized private void encryptData(DataModel dataModel) throws Exception
	{

		DataField source = dataModel
				.getDataField(DataModel.ENCRYPT_SOURCE_STRING);
		DataField targetDir = dataModel
				.getDataField(DataModel.ENCRYPT_TARGET_DIR_STRING);
		DataField targetArchive = dataModel
				.getDataField(DataModel.ENCRYPT_TARGET_ARCHIVE_STRING);

		log.info("starting encryption of data");

		if (source.getValue() != null && targetDir.getValue() != null)
		{

			// set archive name if not already set
			if (targetArchive.getValue() == null
					|| ((String) targetArchive.getValue()).trim().equals(""))
			{

				String sourcePathName = (String) source.getValue();
				String targetArchiveName = new File(sourcePathName).getName()
						+ Constants.SALTY_ARCHIV_EXT;
				targetArchive.setValue(targetArchiveName);
			} else
			{
				if (!((String) targetArchive.getValue())
						.endsWith(Constants.SALTY_ARCHIV_EXT))
				{
					targetArchive.setValue(targetArchive.getValue().toString()
							+ Constants.SALTY_ARCHIV_EXT);
				}
			}

			// pack data to an archive
			File archiveFile = new File(targetDir.getValue().toString(),
					targetArchive.getValue().toString());

			if (log.isDebugEnabled())
			{
				log.debug("source : " + source.getValue().toString());
				log.debug("target archive : " + archiveFile);
			}
			
			// show progress bar while packing data
			TaskProgress taskProgress = new TaskProgress();
			taskProgress.setMessage("Elapsed time in seconds ...");
			taskProgress.setTitle("Packing ...");
			taskProgress.setDone(false);
			ArchivProgressDialog.show(taskProgress);
			
			
			ArchiveHandler.pack(archiveFile.toString(), source.getValue()
					.toString());
			
			// stop progress bar 
			taskProgress.setDone(true);

			// encript
			InputFileHandler ifh = new InputFileHandler(archiveFile
					+ Constants.IN_EXTENSION);
			OutputFileHandler ofh = new OutputFileHandler(archiveFile
					.toString());
			byte[] salt =  Salt.createRandom();
			
			Transformer transformer = new Transformer(validatedPwd, salt);
			transformer.transform(Cipher.ENCRYPT_MODE, ifh, ofh);
			
			// Store it in the file because its needed for decryption 
			if (log.isDebugEnabled())
			{
				log.debug("file.length before append:" +archiveFile.length());
			}
			
			Salt.appendSalt(archiveFile, salt);
			
			if (log.isDebugEnabled())
			{
				log.debug("file.length after append:" +archiveFile.length());
			}
			// delete temporary cleartext input archive
			new File(archiveFile + Constants.IN_EXTENSION).delete();
		}
	}

	/**
	 * Checks password entered.
	 * 
	 * @param dataModel Model holding the data.
	 * @return true if password equals repeated password, false otherwise
	 */
	private boolean checkPassword(DataModel dataModel)
	{

		DataField pwd = dataModel.getDataField(DataModel.PWD_STRING);
		DataField pwdRep = dataModel.getDataField(DataModel.PWD_REPEAT_STRING);

		boolean pwdOK = true;

		if (pwd.getValue() != null && pwdRep.getValue() != null)
		{
			if (pwd.getValue() instanceof char[]
					&& pwdRep.getValue() instanceof char[])

			{
				char[] pwdChars = (char[]) pwd.getValue();
				char[] pwdRepChars = (char[]) pwdRep.getValue();

				if (pwdChars.length == pwdRepChars.length)
				{
					for (int i = 0; i < (pwdChars).length; i++)
					{
						if (!(pwdChars[i] == pwdRepChars[i]))
						{
							pwdOK = false;
						} else
						{
							validatedPwd = pwdChars;
						}
					}
				} else
				{
					pwdOK = false;
					String msg = "pwd and pwdRepeat are not equal !!!";
					log.error(msg);
					
				}
			} else
			{
				pwdOK = false;
				String msg = "pwd or pwdRepeat bad type !!!";
				log.error(msg);
				

			}
		} else
		{
			pwdOK = false;
			String msg = "pwd or pwdRepeat are not entered";
			log.error(msg);
			
		}
		return pwdOK;
	}

}