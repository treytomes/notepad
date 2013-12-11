package org.treytomes.notepad;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import org.treytomes.notepad.model.WindowCloseAction;
import org.treytomes.notepad.resources.ImageSize;
import org.treytomes.notepad.resources.NotepadIcon;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.Font;
import java.util.logging.Logger;

public class AboutNotepadDialog extends JDialog {

	private static final long serialVersionUID = -8373369369401968282L;
	
	private static final Logger LOGGER = Logger.getLogger(AboutNotepadDialog.class.getName());
	private static final String HTML_DESCRIPTION = "<html>\r\nTrey Tomes<br />\r\nVersion 1.0<br />\r\nCopyright \u00A9 2013 Trey Tomes.  All rights reserved.\r\n</html>";

	private WindowCloseAction _closeWindowAction;

	/**
	 * Create the dialog.
	 */
	public AboutNotepadDialog(JFrame parent) {
		super(parent, "About Notepad", true);
		
		LOGGER.info("Loading the About dialog...");
		
		_closeWindowAction = new WindowCloseAction(this);
		_closeWindowAction.setCloseOnEscape(true);
		addWindowListener(_closeWindowAction);
		
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(32, 32, 408, 196);
		
		getContentPane().setLayout(new FormLayout(
			new ColumnSpec[] { ColumnSpec.decode("default:grow") },
			new RowSpec[] { RowSpec.decode("fill:default:grow"), RowSpec.decode("bottom:min")}));
		
		getContentPane().add(createContentPanel(), "1, 1, fill, fill");
		
		getContentPane().add(createButtonPanel(), "1, 2, fill, bottom");
		
		setLocationRelativeTo(parent);
		
		LOGGER.info("The About dialog is now loaded.");
	}
	
	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("48dlu"),
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("16dlu"),
				RowSpec.decode("top:default:grow"),}));
		{
			JLabel titleLabel = new JLabel("Notepad");
			titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 36));
			contentPanel.add(titleLabel, "2, 1, fill, fill");
		}
		{
			JSeparator separator = new JSeparator();
			contentPanel.add(separator, "1, 2, 2, 1, fill, center");
		}
		{
			JLabel appIconLabel = new JLabel("");
			contentPanel.add(appIconLabel, "1, 3, fill, top");
			appIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
			appIconLabel.setIcon(NotepadIcon.getIcon(ImageSize.Size48));
		}
		
		contentPanel.add(new JLabel(HTML_DESCRIPTION), "2, 3, fill, top");
		
		return contentPanel;
	}
	
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton okButton = new JButton("OK");
		okButton.addActionListener(_closeWindowAction);
		okButton.setActionCommand("OK");
		buttonPanel.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		return buttonPanel;
	}
}
