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
 * This interface defines a basic data container. 
 * It is used by the {@link DataModel}.
 * 
 * @author Thomas Mueller
 *
 */
public interface DataField
{	
	/**
	 * Initalize the field using an unique id. 
	 * @param id 
	 */
	public void init (Object id);
	
	/**
	 * Set a value to the field.
	 * @param value the value to be set
	 */
	public void setValue(Object value);
	
	/**
	 * Retruns the current value
	 * @return the current value of this data container.
	 */
	public Object getValue();
}
