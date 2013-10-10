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


package ch.thomasmueller.saltyfiles.ui;

/**
 * Task used by the 
 * {@link ch.thomasmueller.saltyfiles.ui.ArchivProgressDialog}.
 * 
 * 
 * @author Thomas Mueller
 */
public class TaskProgress
{

	private boolean done = false;

	private String message = "";

	private String title= "";

	/**
	 * Signals whether the task is done or not.
	 * 
	 * @return Returns true if the task is finished, false otherwise.
	 */
	public boolean isDone()
	{

		return done;
	}

	/**
	 * Complete the task.
	 * @param done set the task to state finished if <code>true</code>.
	 *            .
	 */
	public void setDone(boolean done)
	{

		this.done = done;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage()
	{

		return message;
	}

	/**
	 * Sets a message.
	 * @param message
	 *            The message to set.
	 */
	public void setMessage(String message)
	{

		this.message = message;
	}

	/**
	 * 
	 * @return Returns the title.
	 */
	public String getTitle()
	{

		return title;
	}

	/**
	 * @param title
	 *            The title to set.
	 */
	public void setTitle(String title)
	{

		this.title = title;
	}

}