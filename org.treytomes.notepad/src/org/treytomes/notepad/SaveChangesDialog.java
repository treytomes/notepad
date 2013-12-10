package org.treytomes.notepad;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.util.logging.Logger;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

public class SaveChangesDialog extends JDialog {

	private static final long serialVersionUID = -4195587187238713682L;
	
	private static final Logger LOGGER = Logger.getLogger(AboutNotepadDialog.class.getName());

	private final JPanel contentPanel = new JPanel();
	private JLabel messageLabel;

	/**
	 * Create the dialog.
	 */
	public SaveChangesDialog(JFrame parent) {
		LOGGER.info("Loading the Save Changes dialog...");
		
		setResizable(false);
		setTitle("Notepad");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setBounds(32, 32, 400, 150);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new LineBorder(SystemColor.controlHighlight));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			messageLabel = new JLabel("Do you want to save changes to Untitled?");
			messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			messageLabel.setForeground(SystemColor.textHighlight);
			messageLabel.setBackground(Color.WHITE);
			messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		contentPanel.add(messageLabel);
		{
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Save");
				saveButton.setMnemonic('S');
				saveButton.setActionCommand("OK");
				buttonPanel.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton dontSaveButton = new JButton("Don't Save");
				dontSaveButton.setMnemonic('n');
				dontSaveButton.setActionCommand("Cancel");
				buttonPanel.add(dontSaveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setMnemonic('C');
				cancelButton.setActionCommand("Cancel");
				buttonPanel.add(cancelButton);
			}
		}
		
		setLocationRelativeTo(parent);
		
		LOGGER.info("The About dialog is now loaded.");
	}
}
