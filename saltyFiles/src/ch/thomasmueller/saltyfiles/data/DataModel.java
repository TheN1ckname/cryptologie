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

package ch.thomasmueller.saltyfiles.data;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the <b>Model</b> used by several views.
 * @see ch.thomasmueller.saltyfiles.ui.Controller
 * @author Thomas Mueller
 *  
 */
public class DataModel
{

	private static DataModel instance = null;

	// input data
	public static final String ENCRYPT_BOOLEAN = "encryptBoolean";

	public static final String PWD_STRING = "pwd";

	public static final String PWD_REPEAT_STRING = "pwdRepeat";

	public static final String ENCRYPT_SOURCE_STRING = "encryptSource";

	public static final String ENCRYPT_TARGET_DIR_STRING = "encryptTargetDir";

	public static final String ENCRYPT_TARGET_ARCHIVE_STRING = "encryptTargetArchive";

	public static final String DECRYPT_SOURCE_ARCHIVE_STRING = "decryptSourceArchive";

	public static final String DECRYPT_TARGET_DIR_STRING = "decryptTargetDir";

	private String[] ids = new String[]{ENCRYPT_BOOLEAN, PWD_STRING,
			PWD_REPEAT_STRING, ENCRYPT_SOURCE_STRING,
			ENCRYPT_TARGET_DIR_STRING, ENCRYPT_TARGET_ARCHIVE_STRING,
			DECRYPT_SOURCE_ARCHIVE_STRING, DECRYPT_TARGET_DIR_STRING};

	Map dataFieldsMap = new HashMap();

	private DataField pwdRepeate = new DataFieldImpl();

	private DataField encryptSource = new DataFieldImpl();

	private DataField encryptTargetDir = new DataFieldImpl();

	private DataField encryptTargetArchive = new DataFieldImpl();

	private DataField decryptSourceArchive = new DataFieldImpl();

	private DataField decryptTargetDir = new DataFieldImpl();

	/**
	 * Private constructors inits all <code>DataField</code> s and stores in a
	 * <code>Map</code>.
	 */
	private DataModel()
	{

		for (int i = 0; i < ids.length; i++)
		{
			DataField df = null;
			if (ids[i].equals(PWD_STRING) || ids[i].equals(PWD_REPEAT_STRING))
			{
				df = new PasswordDataFieldImpl();
			}
			else
			{
				df = new DataFieldImpl();
			}
			df.init(ids[i]);
			dataFieldsMap.put(ids[i], df);
		}
	}

	/**
	 * Returns the instance of this Singleton.
	 * 
	 * @return the data model.
	 */
	public static DataModel getInstance()
	{

		if (instance == null)
		{
			instance = new DataModel();
		}
		return instance;
	}

	/**
	 * Returns the filed assigned to this id
	 * 
	 * @param id
	 *            ids are constant members of this class.
	 * @return the dataField
	 */
	public DataField getDataField(Object id)
	{

		return (DataField) dataFieldsMap.get(id);
	}

	public String toString()
	{

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++)
		{
						
			if (dataFieldsMap.get(ids[i]) != null)
			{
				sb.append(dataFieldsMap.get(ids[i]));
			}
			else
			{
				sb.append("NULL");
			}
			sb.append("\n");

		}
		return sb.toString();
	}
}