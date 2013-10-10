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

/**
 * Use this class to store the data of controls for passwords.  
 * @author Thomas Mueller
 *  
 */
public class PasswordDataFieldImpl extends DataFieldImpl
{

	/**
	 *  Overrides this method to avoid exposion of the password.
	 */
	public String toString()
	{
		
		String valueString = "NULL";
		if (super.getValue() != null) 
		{
			valueString = "?";
		}

		return "id: " + super.id + ", " + "'" + valueString + "'";
	}
}