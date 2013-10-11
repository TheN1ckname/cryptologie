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

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ch.thomasmueller.saltyfiles.data.DataFieldImpl;
import ch.thomasmueller.saltyfiles.data.DataModel;
import ch.thomasmueller.saltyfiles.ui.command.CommandFactory;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Builds the main view by creationg the ui components and thier layout
 * and registers all controls at the 
 * <code>Controller</code>.
 * 
 * @author Thomas Mueller
 *  
 */
public class MainView
{

	// input
	private JRadioButton radioEncrypt = new JRadioButton("Encrypt");

	private JRadioButton radioDecrypt = new JRadioButton("Decrypt");

	private ButtonGroup buttonGroupEncOrDec = new ButtonGroup();

	private JPasswordField pwd = new JPasswordField(10);

	private JPasswordField pwdRepeat = new JPasswordField(10);

	private JTextField textEncryptSource = new JTextField();

	private JTextField textEncryptTargetDir = new JTextField();

	private JTextField textEncryptTargetArchive = new JTextField();

	private JTextField textDecryptSourceArchive = new JTextField();

	private JTextField textDecryptTargetDir = new JTextField();

	
	//chooseAlgoritm combobox to choose your algorithm : )
    private JComboBox chooseAlgorithm = new JComboBox( new String[] {"Default","TwoFish","Sha1WithDes"});

    // action
	private JButton buttonChooseEncSF = new JButton("...");

	private JButton buttonChooseEncTD = new JButton("...");

	private JButton buttonChooseDecSA = new JButton("...");

	private JButton buttonChooseDecTD = new JButton("...");

	private JButton buttonTransform = new JButton("Start");


	JPanel buttonBar = ButtonBarFactory
			.buildRightAlignedBar(new JButton[]{buttonTransform});

	
	/**
	 * Register all Controls at the Controller.
	 * @throws Exception
	 */
	private void register() throws Exception
	{

		Controller controller = Controller.getInstance();
		// actions
		controller.registerCommandComponent(
					CommandFactory.CHOOSE_ENCRYPTION_FILES, buttonChooseEncSF);
		controller.registerCommandComponent(
				CommandFactory.CHOOSE_ENCRYPTION_DIR, buttonChooseEncTD);
		
		controller.registerCommandComponent(
				CommandFactory.CHOOSE_DECRYPTION_DIR, buttonChooseDecTD);
		controller.registerCommandComponent(
				CommandFactory.CHOOSE_DECRYPTION_ARCHIV, buttonChooseDecSA);
		controller.registerCommandComponent(
				CommandFactory.SWITCH_ENCRYPT_MODE, radioEncrypt);
		controller.registerCommandComponent(
				CommandFactory.SWITCH_DECRYPT_MODE, radioDecrypt);
		
		controller.registerCommandComponent(
				CommandFactory.TRANSFORM, buttonTransform);
		// data
		DataModel dataModel = DataModel.getInstance();
		
		controller.registerDataComponent(
				dataModel.getDataField(DataModel.ENCRYPT_SOURCE_STRING), 
				textEncryptSource);
		controller.registerDataComponent(
				dataModel.getDataField(DataModel.ENCRYPT_TARGET_DIR_STRING), 
				textEncryptTargetDir);
		controller.registerDataComponent(
				dataModel.getDataField(DataModel.ENCRYPT_TARGET_ARCHIVE_STRING), 
				textEncryptTargetArchive);
		controller.registerDataComponent(
				dataModel.getDataField(DataModel.DECRYPT_SOURCE_ARCHIVE_STRING),
				textDecryptSourceArchive);
		controller.registerDataComponent(
				dataModel.getDataField(DataModel.DECRYPT_TARGET_DIR_STRING),
				textDecryptTargetDir);
		
		controller.registerDataComponent(
				dataModel.getDataField(DataModel.PWD_STRING),
				pwd);	
		controller.registerDataComponent(
				dataModel.getDataField(DataModel.PWD_REPEAT_STRING),
				pwdRepeat);	
		
		controller.registerDataComponent(
				dataModel.getDataField(DataModel.ALGORITHM_STRING), chooseAlgorithm);
		
		
		//dataModel.getDataField(DataModel.ALGORITHM_STRING).setValue(chooseAlgoritm.getSelectedItem());	
		
		// switched to encription mode on startup of the application
		dataModel.getDataField(DataModel.ENCRYPT_BOOLEAN)
			.setValue(Boolean.TRUE);
		controller.setAccessable(dataModel
				.getDataField(DataModel.DECRYPT_SOURCE_ARCHIVE_STRING),
				false);
		controller.setAccessable(dataModel
				.getDataField(DataModel.DECRYPT_TARGET_DIR_STRING),
				false);
		buttonChooseDecSA.setEnabled(false);
		buttonChooseDecTD.setEnabled(false);
		
		
	}
	/**
	 * Registers all controls and initializes the panel.
	 * @return the built panel.
	 * @throws Exception
	 */
	public JPanel init() throws Exception
	{
		register();
		
		FormLayout layout = layout();		
		
		JPanel panel = build(layout);
		
		return panel;
	}

	/**
	 * Adds the components to the panel
	 * @param layout to be used
	 * @return a layouted panel
	 */
	private JPanel build(FormLayout layout)
	{

		buttonGroupEncOrDec.add(radioDecrypt);
		buttonGroupEncOrDec.add(radioEncrypt);
		buttonGroupEncOrDec.setSelected(radioEncrypt.getModel(), true);
		
		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		//		 Obtain a reusable constraints object to place components in the grid.
		CellConstraints cc = new CellConstraints();

		//		 Fill the grid with components; the builder can create
		//		 frequently used components, e.g. separators and labels.

		//		 Add a titled separator to cell (1, 1) that spans 7 columns.
		builder.addSeparator("General", cc.xyw(1, 1, 9));

		builder.addLabel("Mode", cc.xy(1, 3));
		builder.add(radioEncrypt, cc.xy(3, 3));
		builder.add(radioDecrypt, cc.xy(3, 5));

		
		
		builder.addLabel("Password", cc.xy(5, 3));
		builder.add(pwd, cc.xyw(7, 3, 3));
		builder.addLabel("Repeat", cc.xy(5, 5));
		builder.add(pwdRepeat, cc.xyw(7, 5, 3));
		
		
		
		builder.addSeparator("Encrypt", cc.xyw(1, 7, 9));
		
		builder.addLabel("Source Files", cc.xy(1, 9));
		builder.add(textEncryptSource, cc.xyw(3, 9, 5));
		builder.add(buttonChooseEncSF, cc.xy(9, 9));
		builder.addLabel("Target Directory", cc.xy(1, 11));
		builder.add(textEncryptTargetDir, cc.xyw(3, 11, 5));
		builder.add(buttonChooseEncTD, cc.xy(9, 11));
		builder.addLabel("Target Archive", cc.xy(1, 13));
		builder.add(textEncryptTargetArchive, cc.xyw(3, 13, 5));

		builder.addSeparator("Decrypt", cc.xyw(1, 15, 9));

		builder.addLabel("Source Archive", cc.xy(1, 17));
		builder.add(textDecryptSourceArchive, cc.xyw(3, 17, 5));
		builder.add(buttonChooseDecSA, cc.xy(9, 17));
		builder.addLabel("Target Directory", cc.xy(1, 19));
		builder.add(textDecryptTargetDir, cc.xyw(3, 19, 5));
		builder.add(buttonChooseDecTD, cc.xy(9, 19));
		
		builder.addLabel("Algorithm",cc.xy(1,23));
		builder.add(chooseAlgorithm, cc.xy(3,23));
		
		builder.addSeparator("Transform Data", cc.xyw(1, 21, 9));
		builder.add(buttonBar, cc.xyw(1, 23, 9));
		// The builder holds the layout container that we now return.
		
		JPanel panel = builder.getPanel();
		
		return panel;
	}

	/**
	 * @return the layout to be used.
	 */
	private FormLayout layout()
	{

		FormLayout layout = new FormLayout(
				"right:70dlu, 3dlu, pref, 3dlu, right:pref, 3dlu, left:3dlu, 3dlu, pref", // columns
				//	general rows
				"p, 3dlu, p, 3dlu, p, 3dlu, p,3dlu p, 9dlu," +
				// encript rows
						"p, 3dlu, p, 3dlu, p, 3dlu, p,9dlu," +
						// decript rows
						"p, 3dlu, p, 3dlu, p, 9dlu," +
						//		 buttonbar row
						"p, 3dlu,p");

		//		 Specify that columns 1 & 5 as well as 3 & 7 have equal widths.
		layout.setColumnGroups(new int[][]{{1, 5}, {3, 7}});
		return layout;
	}

}