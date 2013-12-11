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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

import org.treytomes.notepad.model.SaveFileChoice;
import org.treytomes.notepad.model.TextFileDocument;
import org.treytomes.notepad.model.WindowCloseAction;

/**
 * 1 property is exposed to the PropertyChangeListener:
 * 	* saveFile
 * 
 * @author ttomes
 *
 */
public class SaveChangesDialog extends JDialog implements ActionListener {

	public static final String PROPERTY_SAVEFILE = "saveFile";
		
	private static final long serialVersionUID = -4195587187238713682L;
	
	private static final Logger LOGGER = Logger.getLogger(SaveChangesDialog.class.getName());
	
	private PropertyChangeSupport _propertyChangeSupport;

	private final JPanel contentPanel = new JPanel();
	private JLabel messageLabel;
	private TextFileDocument _model;
	private WindowCloseAction _closeWindowAction;
	private SaveFileChoice _saveFile;

	/**
	 * Create the dialog.
	 */
	public SaveChangesDialog(JFrame parent, TextFileDocument model) {
		super(parent, parent.getTitle(), true);
		
		LOGGER.info("Loading the Save Changes dialog...");
		
		_propertyChangeSupport = new PropertyChangeSupport(this);
		
		setSaveFile(null);
		
		_closeWindowAction = new WindowCloseAction(this);
		_closeWindowAction.setCloseOnEscape(true);
		addWindowListener(_closeWindowAction);
		
		setModel(model);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
			
			for (SaveFileChoice choice : SaveFileChoice.values()) {
				JButton button = choice.createButton();
				button.addActionListener(_closeWindowAction);
				button.addActionListener(this);
				buttonPanel.add(button);
				if (choice.isDefaultChoice()) {
					getRootPane().setDefaultButton(button);
				}
			}
		}
		
		setLocationRelativeTo(parent);
		
		LOGGER.info("The About dialog is now loaded.");
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		_propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public TextFileDocument getModel() {
		return _model;
	}
	
	public void setModel(TextFileDocument model) {
		if (_model == model) {
			return;
		}
		
		LOGGER.log(Level.INFO, "Assigning a new model.");
		if (model == null) {
			LOGGER.log(Level.WARNING, "Input model is null; creating a new model.");
			model = new TextFileDocument();
		}
		_model = model;
	}

	public SaveFileChoice getSaveFile() {
		return _saveFile;
	}
	
	public void setSaveFile(SaveFileChoice saveFile) {
		if (_saveFile != saveFile) {
			SaveFileChoice oldValue = _saveFile;
			_saveFile = saveFile;
			_propertyChangeSupport.firePropertyChange(PROPERTY_SAVEFILE, oldValue, _saveFile);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		setVisible(false);
		setSaveFile(SaveFileChoice.valueOf(evt.getActionCommand()));
	}
}
