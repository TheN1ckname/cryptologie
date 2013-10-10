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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ch.thomasmueller.saltyfiles.data.DataField;
import ch.thomasmueller.saltyfiles.data.DataModel;
import ch.thomasmueller.saltyfiles.ui.command.Command;
import ch.thomasmueller.saltyfiles.ui.command.CommandFactory;

/**
 * @author Thomas Mueller
 *  
 */
public class Controller implements ActionListener, DocumentListener, FocusListener
{

	private static Controller instance = null;

	private Logger log = LogManager.getLogger(this.getClass().getName());

	private Map dataMap = new HashMap();

	private Map componentAccessMap = new HashMap();
	
	private Map commandMap = new HashMap();

	/**
	 * Singleton constructor.
	 */
	private Controller()
	{

	}

	/**
	 * Returns the instance of this Singleton.
	 * 
	 * @return the controller.
	 */
	public static Controller getInstance()
	{

		if (instance == null)
		{
			instance = new Controller();
		}
		return instance;
	}

	/**
	 * UI components holding data register here to map the component to a
	 * {@link DataField}.
	 * 
	 * @param dataField
	 * @param component
	 */
	void registerDataComponent(Object dataField, JComponent component)
	{

		// key dataField, value set of components
		//component.addPropertyChangeListener(this);
	    
		((JTextField)component).addActionListener(this);
		((JTextField)component).addFocusListener(this);
		((JTextField)component).getDocument().addDocumentListener(this);

		Set dataComponentSet = null;

		if (dataMap.containsKey(dataField))
		{
			dataComponentSet = (Set) dataMap.get(dataField);
		}
		else
		{
			dataComponentSet = new HashSet();
			dataMap.put(dataField, dataComponentSet);
		}
		dataComponentSet.add(component);

		// for quick access
		componentAccessMap.put(component, dataField);

		log.debug("registered data component");

	}

	/**
	 * Returns a <code>Map</code> containing access components.
	 * 
	 */
	Map getComponentAccessMap()
	{

		return componentAccessMap;
	}

	/**
	 * Maps a {@link Command command}to a button.
	 * 
	 * @param commandClassName
	 *            the command class name
	 * @param button
	 *            to map the command to
	 */
	void registerCommandComponent(String commandClassName, AbstractButton button)
	{

		button.addActionListener(this);
		button.setActionCommand(commandClassName);
		commandMap.put(commandClassName, button);
		if (log.isDebugEnabled())
		{
			log.debug("registered component for command : " + commandClassName);
		}
	}

	/**
	 * Returns an action component mapped to a <code>Command</code>. 
	 * @param commandClassName the classname of the {@link Command}
	 * @return the component mapped.
	 */
	public AbstractButton getActionComponent(String commandClassName)
	{
		if (commandMap.containsKey(commandClassName))
		{
			return (AbstractButton)commandMap.get(commandClassName);
		}
		throw new RuntimeException("bad command name");
	}
	
	/**
	 * Update the data of all UI-components except the password fields. 
	 *
	 */
	private void updateUI()
	{

		Set dataFieldsSet = dataMap.keySet();
		for (Iterator dataFieldsIt = dataFieldsSet.iterator(); dataFieldsIt
				.hasNext();)
		{
			DataField dataField = (DataField) dataFieldsIt.next();
			if (log.isDebugEnabled())
			{
				log.debug("assinging Data of DataField : " + dataField
						+ " to registered components");
			}

			Set componentsSet = (Set) dataMap.get(dataField);
			for (Iterator componentsIt = componentsSet.iterator(); componentsIt
					.hasNext();)
			{
				JComponent component = (JComponent) componentsIt.next();
				if (component instanceof JTextField)
				{
					if (dataField.getValue() != null)
					{
						if (component instanceof JPasswordField)
						{
							// nothing to do
						}
						else
						{
							((JTextField) component).setText(dataField
									.getValue().toString());
						}
					}
					else
					{
						((JTextField) component).setText("");
					}
				}

				// add other component handling here fi needed ...

				else
				{
					log.error("component not supported yet ... :( "
							+ component.getClass().getName());
				}
			}
		}
	}

	/**
	 * Calls the assigned command of the source of this <code>ActionEvent</code>.
	 * @param event the <code>ActionEvent</code> mapped to a <code>Command</code>.
	 */
	public void actionPerformed(ActionEvent event)
	{

		log.debug("ActionEvent: " + event.getSource());
		if (event.getSource() instanceof AbstractButton)
		{
			String commandClassName = ((AbstractButton) event.getSource())
					.getActionCommand();
		
				Command cmd= null
				;
				try
				{
					cmd = CommandFactory.getCommand(commandClassName);
				}
				catch (Exception e)
				{
					log.error("command : '" + commandClassName
							+ "' not registered for " + event.getSource());
					log.error("Exception: " + e);
				}
				
				cmd.doCommand(event);
				updateUI();

		}
		else if (event.getSource() instanceof JTextField)
		{
			dataChanged((JComponent)event.getSource());
		}
		else
		{
			log.warn("command registered for unknown type "
							+ event.getSource());

		}
		if (log.isDebugEnabled())
		{
			log.debug(DataModel.getInstance());
		}
	}

	/**
	 * Listens to change events of registered {@link DataField}s.
	 * @param component the component which data has been changed
	 *  
	 */
	private void dataChanged(JComponent component)
	{

		if (log.isDebugEnabled())
		{
			log.debug("dataChange: " + component);
		}
		
		DataField dataField = (DataField) componentAccessMap.get(component);

		if (component != null && component instanceof JTextField)
		{
			if (dataField != null)
			{
				if (component instanceof JPasswordField)
				{
					dataField.setValue(((JPasswordField) component)
							.getPassword());
					char[] pwdChars = ((JPasswordField) component)
					.getPassword();

					log.debug("pwd length = " + pwdChars.length);
				}
				else
				{
					dataField.setValue(((JTextField) component).getText());
				}

				log.debug("Stored data to :" + dataField);
				updateUI();
			}
			else
			{
				log.error("datafield is NULL");
			}
		}
		// add other component handling here fi needed ...
		else
		{
			log.error("component not supported yet ... :( "
					+ component.getClass().getName());
		}
	}

	/**
	 * Set all data components enabled/disabled
	 * 
	 * @param dataField
	 *            mapped to these components
	 * @param isAccessable
	 *            true = enabled, false = disabled
	 */
	public void setAccessable(DataField dataField, boolean isAccessable)
	{

		if (dataField != null)
		{
			Set componentsSet = (Set) dataMap.get(dataField);

			if (componentsSet != null)
			{
				for (Iterator iter = componentsSet.iterator(); iter.hasNext();)
				{
					JComponent component = (JComponent) iter.next();
					component.setEnabled(isAccessable);
				}
			}
		}
	}

	/**
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent arg0)
	{
		log.debug("changedUpdate data");

	}
	/**
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent arg0)
	{

		log.debug("insertUpdate data");

	}
	/**
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent arg0)
	{

		log.debug("removeUpdate data");

	}
	/**
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent arg0)
	{

		log.debug("focusGained data");

	}
	/**
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent event)
	{

		log.debug("focusLost data");
		dataChanged((JComponent)event.getSource());

	}
}