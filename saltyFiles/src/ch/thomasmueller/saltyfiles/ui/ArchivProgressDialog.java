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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class implements animation used while packing data to an Archvie.
 * 
 * Start the animation useing the method {@link #show(TaskProgress)}.
 * The animation will run as long as the <code>TaskProgress</code> is 
 * not completed.
 * 
 * @see TaskProgress
 */
public class ArchivProgressDialog extends JPanel
{

	private final static int ONE_SECOND = 1000;

	private JProgressBar progressBar;

	private Timer timer;

	//    private JButton startButton;

	private JTextArea taskOutput;

	private String newline = "\n";

	private ArchivProgressDialog(JFrame frame, TaskProgress taskProgress)
	{

		//super(new BorderLayout());

		progressBar = new JProgressBar(0, 10);
		progressBar.setValue(0);

		//We call setStringPainted, even though we don't want the
		//string to show up until we switch to determinate mode,
		//so that the progress bar height stays the same whether
		//or not the string is shown.
		progressBar.setStringPainted(true); //get space for the string
		progressBar.setString(""); //but don't paint it

		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(0, 0, 0, 0));
		taskOutput.setEditable(false);

		FormLayout layout = doFormLayout();
		this.setLayout(new BorderLayout());
		this.add(build(layout),BorderLayout.CENTER);

		final TaskProgress theTask = taskProgress;
		final JFrame theFrame = frame;
		//Create a timer.
		timer = new Timer(ONE_SECOND, new ActionListener()
		{
			private int elapsedSec = 0;
			
			public void actionPerformed(ActionEvent evt)
			{

				progressBar.setValue(1);
				String s = theTask.getMessage()+ " " + elapsedSec++;
				if (s != null)
				{
					if (progressBar.isIndeterminate())
					{
						progressBar.setIndeterminate(true);
						progressBar.setString(null); //display % string
					}

					taskOutput.append(s + newline);
					taskOutput.setCaretPosition(taskOutput.getDocument()
							.getLength());
				}
				if (theTask.isDone())
				{

					progressBar.setString("done");
					try
					{
						Thread.sleep(ONE_SECOND);
					} catch (InterruptedException e)
					{

					}
					theFrame.setVisible(false);
					theFrame.dispose();
					timer.stop();
				}
				progressBar.setString(""); //hide % string

			}
		});

	}

	/**
	 * Called when the user presses the start button.
	 */
	public void start()
	{

		//    	Where the progress bar is created:
		progressBar.setStringPainted(true); //get space for the string
		progressBar.setString(""); //but don't paint it
		progressBar.setIndeterminate(true);
		timer.start();

	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI(TaskProgress taskProgress)
	{

		//Make sure we have nice window decorations.
		// JFrame.setDefaultLookAndFeelDecorated(true);

		//Create and set up the window.
		JFrame frame = new JFrame(taskProgress.getTitle());

		//Create and set up the content pane.
		ArchivProgressDialog archivProgress = new ArchivProgressDialog(frame,
				taskProgress);
		archivProgress.setOpaque(true); //content panes must be opaque
		frame.setContentPane(archivProgress);
		frame.setIconImage(MainFrame.getInstance().getIconImage());
		archivProgress.locateOnScreen(frame);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
		archivProgress.start();

	}

	/**
	 * Start the progress bar and shows it util the method
	 * {@link TaskProgress#isDone()} returns true.
	 * 
	 * @param tp
	 *            the task for which progress will be visualized.
	 */
	public static void show(TaskProgress tp)
	{

		final TaskProgress taskProgress = tp;
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{

				createAndShowGUI(taskProgress);
			}
		});
	}

	/**
	 * Locates the given component on the <code>MainFrame</code> s center.
	 */
	protected void locateOnScreen(Component component)
	{

		Dimension parentSize = MainFrame.getInstance().getSize();
		Point parentlocation = MainFrame.getInstance().getLocation();

		int x = (int) (parentlocation.getX() + (parentSize.getWidth() / 2) - (component
				.getPreferredSize().getWidth() / 2));

		int y = (int) (parentlocation.getY() + (parentSize.getHeight() / 2) - (component
				.getPreferredSize().getHeight() / 2));
		component.setLocation(x, y);

	}

	/**
	 * Adds the components to the panel.
	 * 
	 * @param layout
	 *            to be used
	 * @return a layouted panel
	 */
	private JPanel build(FormLayout layout)
	{
		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		
		CellConstraints cc = new CellConstraints();

		builder.add(progressBar, cc.xy(2, 2));
		builder.add(new JScrollPane(taskOutput), cc.xy(2, 4));

		// The builder holds the layout container that we now return.
		
		return builder.getPanel();
	}

	/**
	 * Returns the layout to be used.
	 * @return the layout 
	 */
	private FormLayout doFormLayout()
	{

		FormLayout layout = new FormLayout(
		//	columns
				"0dlu, pref, 0dlu",
				//	 rows
				"0dlu, p, 2dlu, p, 0dlu");

		return layout;
	}
}