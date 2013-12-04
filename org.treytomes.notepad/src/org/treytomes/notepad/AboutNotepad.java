package org.treytomes.notepad;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.Font;

public class AboutNotepad extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8373369369401968282L;
	
	private static final WindowListener closeWindow = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent evt) {
			evt.getWindow().setVisible(false);
			evt.getWindow().dispose();
		};
	};
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public AboutNotepad(JFrame parent) {
		super(parent, "About Notepad", true);
		setResizable(false);
		addWindowListener(closeWindow);
		addEscapeListener();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(32, 32, 408, 196);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("fill:default:grow"),
				RowSpec.decode("bottom:min"),}));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, "1, 1, fill, fill");
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
			appIconLabel.setIcon(new ImageIcon(AboutNotepad.class.getResource("/org/treytomes/notepad/notepad.png")));
		}
		{
			JLabel descriptionLabel = new JLabel("<html>\r\nTrey Tomes<br />\r\nVersion 1.0<br />\r\nCopyright \u00A9 2013 Trey Tomes.  All rights reserved.\r\n</html>");
			contentPanel.add(descriptionLabel, "2, 3, fill, top");
		}
		{
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPanel, "1, 2, fill, bottom");
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						dispatchEvent(new WindowEvent(AboutNotepad.this, WindowEvent.WINDOW_CLOSING));
					}
				});
				okButton.setActionCommand("OK");
				buttonPanel.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		setLocationRelativeTo(parent);
	}

	private void addEscapeListener() {
		ActionListener escapeListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dispatchEvent(new WindowEvent(AboutNotepad.this, WindowEvent.WINDOW_CLOSING));
			}
		};

		KeyStroke escapeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

		getRootPane().registerKeyboardAction(escapeListener, escapeStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
}
