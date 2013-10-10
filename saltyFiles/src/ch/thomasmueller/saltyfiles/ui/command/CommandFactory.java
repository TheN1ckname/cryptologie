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

import java.util.HashMap;
import java.util.Map;

/**
 * Factory to create {@link Command}s and publish their class-names.
 * Commands are register at the 
 * {@link ch.thomasmueller.saltyfiles.ui.Controller}.
 * @see Command
 * 
 * 
 * @author Thomas Mueller
 *  
 */
public class CommandFactory
{

	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String CHOOSE_ENCRYPTION_FILES = ChooseEncryptionSourceFilesCommand.class
			.getName();

	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String CHOOSE_ENCRYPTION_DIR = ChooseEncryptionTargetDirCommand.class
			.getName();

	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String CHOOSE_DECRYPTION_DIR = ChooseDecryptionTargetDirCommand.class
			.getName();

	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String CHOOSE_DECRYPTION_ARCHIV = ChooseDecryptionSourceArchiveCommand.class
			.getName();

	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String SWITCH_ENCRYPT_MODE = SwitchEncryptModesCommand.class
			.getName();
	
	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String SWITCH_DECRYPT_MODE = SwitchDecryptModesCommand.class
			.getName();
	
	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String TRANSFORM = TransformCommand.class
			.getName();
	
	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String EXIT = ExitCommand.class
			.getName();
	
	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String SHOW_ABOUT = ShowAboutCommand.class
			.getName();
	
	/**
	 * Class name of an available command class to be mapped to an
	 * <code>AbstracButton</code>.
	 */
	public static final String SHOW_CONFIG = ShowConfigCommand.class
			.getName();
	
	private static Map commandMap = new HashMap();

	/**
	 * Creates an instance of an implementation of the {@link Command}
	 * interface.
	 * 
	 * @param commandClassName the class name of the command defined as contant
	 * 			<code>String</code> above.
	 * 
	 * @return an instance of Command named after <code>commandClassName</code>
	 * @throws Exception
	 */
	public static Command getCommand(String commandClassName) throws Exception
	{

		if (!commandMap.containsKey(commandClassName))
		{
			commandMap.put(commandClassName, (Command) Class.forName(
					commandClassName).newInstance());
		}

		return (Command) commandMap.get(commandClassName);
	}
}