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
 * Simple implementation of a {@link DataField}.
 * @author Thomas Mueller
 *  
 */
public class DataFieldImpl implements DataField
{

	private Object value = null;

	protected Object id = "NULL_ID";

	/**
	 * @see ch.thomasmueller.saltyfiles.data.DataField#getValue()
	 */
	public Object getValue()
	{

		return value;
	}

	/**
	 * @see ch.thomasmueller.saltyfiles.data.DataField#init(java.lang.Object)
	 */
	public void init(Object anId)
	{

		this.id = anId;
	}

	/**
	 * @see ch.thomasmueller.saltyfiles.data.DataField#setValue(java.lang.Object)
	 */
	public void setValue(Object aValue)
	{

		value = aValue;

	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object anObject)
	{

		if (anObject == null)
		{
			return false;
		}
		if (anObject == this)
		{
			return true;
		}
		if (anObject instanceof DataFieldImpl)
		{
			DataFieldImpl dfi = (DataFieldImpl)anObject;
			
			if ( this.id.equals(dfi.id)) 
			{
				return true;
			}		
			return false;
		}
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return id.hashCode();
	}

	/**
	 *
	 */
	public String toString()
	{
		String valueString = (value == null) ? "NULL": value.toString();
		
		return "id: " + id + ", " + valueString;
	}
}