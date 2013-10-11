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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import ch.thomasmueller.saltyfiles.io.InputFileHandler;
import ch.thomasmueller.saltyfiles.io.OutputFileHandler;
import ch.thomasmueller.saltyfiles.ui.MainFrame;

/**
 * This class is used to transform the data form encrypted to decrypted or vice
 * versa. <b>PBEWithMD5AndDES </b> is used as algorithm.
 * 
 * @author Thomas Mueller
 *  
 */
public class Transformer
{

	private Logger log = LogManager.getLogger(this.getClass().getName());

	private static final byte[] zipSignature = new byte[] { 0x50, 0x4b, 0x03,
			0x04 };

	private char[] password = null;

	private byte[] salt = null;

	private int iterationCount = 1017;

	private String algorithm = "PBEWithMD5AndDES";

	/**
	 * Creates an new instance using the given password.
	 * 
	 * @param pwd
	 *            the password to be used
	 * @param salt
	 *            random byte array
	 */
	public Transformer(char[] aPassoword, byte[] someSalt)
	{

		password = aPassoword;
		salt = someSalt;
		Security.addProvider(new BouncyCastleProvider());

	}
	/**
	 * 
	 * @param mode
	 * @param type used for other algorithms like twofish
	 * @return
	 * @throws Exception
	 */
	private Cipher initCipher(int mode, String type) throws Exception
	{	
		Cipher cipher = null;
		SecretKeyFactory keyFactory = null;
		SecretKey key = null;
		PBEParameterSpec paramSpec = null;
		
		PBEKeySpec keySpec = new PBEKeySpec(password);
		System.out.println("Type = " + type);
		
		/**
		 * Switch statement is used to get a correct Cipher by the chosen algorithm
		 */
		switch(type) {
		case "TwoFish" :
			algorithm = "PBEWithSHAAndTwofish-CBC";
			keyFactory = SecretKeyFactory.getInstance(algorithm, "BC");
			key = keyFactory.generateSecret(keySpec);
			paramSpec = new PBEParameterSpec(salt, iterationCount);
			cipher = Cipher.getInstance(algorithm, "BC");
			break;
		case "Sha1WithDes" :
			algorithm = "PBEWithSHA1AndDES";
			keyFactory = SecretKeyFactory.getInstance(algorithm, "BC");
			key = keyFactory.generateSecret(keySpec);
			paramSpec = new PBEParameterSpec(salt, iterationCount);
			cipher = Cipher.getInstance(algorithm, "BC");
			break;
		default :
			algorithm = "PBEWithMD5AndDES";
			keyFactory = SecretKeyFactory.getInstance(algorithm);
			key = keyFactory.generateSecret(keySpec);
			paramSpec = new PBEParameterSpec(salt, iterationCount);
			cipher = Cipher.getInstance(algorithm);			
			break;
		}
		
		cipher.init(mode, key, paramSpec);
		return cipher;
	}

	/**
	 * Starts transformation an show an animation while transforming. The file
	 * signature (first for bytes) will <b>not </b> be transformed.
	 * 
	 * @param aMode
	 *            either <code>Cipher.ENCRYPT_MODE</code> or
	 *            <code>Cipher.DECRYPT_MODE</code>
	 * @param source
	 *            the source archive
	 * @param target
	 *            the target archive
	 * @throws Exception
	 */
	public void transform(int aMode, InputFileHandler source,
			OutputFileHandler target, String type) throws Exception
	{

		final int mode = aMode;
		final FileInputStream fis = source.getFileInputStream();
		final FileOutputStream fos = target.getFileOutputStream();
		long totalFileSize = source.getFileSize();
		String modeTxt = Cipher.ENCRYPT_MODE == mode ? "encryption mode: "
				: "decryption mode: ";

		// avoid to transform the zip-file signature

		Cipher cipher = initCipher(mode, type);
		final CipherOutputStream cos = new CipherOutputStream(fos, cipher);

		byte[] bytes = new byte[1];

		final int ONE_MEGA = 1024 * 1024;
		long countBytes = 0;

		// ignore file signature of the archive
		fos.write(zipSignature);
		fis.skip(zipSignature.length);
		countBytes = countBytes + zipSignature.length;

		//   transform the file. If it's taking too long, the progress
		//   monitor will appear. The amount of time is roughly
		//   1/100th of the estimated read time (based on how long
		//   it took to read the first 1/100th of the file.)
		//   Note that by default, the dialog won't appear unless
		//   the overall estimate is over 2 seconds.
		ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(
				MainFrame.getInstance(), "Transform data using " + modeTxt, fis);
		ProgressMonitor pm = pmis.getProgressMonitor();
		pm.setMillisToPopup(0);

		String msg = "start ...";
		pm.setNote(msg);
		log.info(msg);

		// initial max Size is one mega
		bytes = new byte[ONE_MEGA];

		while ((totalFileSize - countBytes) < bytes.length)
		{
			bytes = new byte[(bytes.length / 2)];
		}

		int i = 0;

		while (i != -1)
		{
			// find out max bufferSize
			i = pmis.read(bytes);
			cos.write(bytes, 0, i);
			countBytes = countBytes + bytes.length;

			if (totalFileSize == countBytes)
			{
				break;
			}

			while ((totalFileSize - countBytes) < bytes.length)
			{
				bytes = new byte[(bytes.length / 2)];
			}
			if (countBytes % ONE_MEGA < 1024)
			{
				msg = (countBytes / ONE_MEGA) + " MB (" + countBytes + " byte)";
				pm.setNote(msg);
				log.info(msg);
			}
		}

		pmis.close();

		cos.flush();
		cos.close();
		msg = "transformation finished !";
		pm.setNote(msg);
		log.info(msg);

		source.close();
		target.close();
	}

}