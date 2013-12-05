package org.treytomes.notepad;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NotepadView extends JFrame implements Observer {
	
	private static final String WINDOW_TITLE = "Notepad";
	private static final int WINDOW_WIDTH = 450;
	private static final int WINDOW_HEIGHT = 300;

	private static final String WINDOW_ICON_16 = "/org/treytomes/notepad/notepad_16.png";
	private static final String WINDOW_ICON_24 = "/org/treytomes/notepad/notepad_24.png";
	private static final String WINDOW_ICON_32 = "/org/treytomes/notepad/notepad_32.png";
	private static final String WINDOW_ICON_48 = "/org/treytomes/notepad/notepad_48.png";
	private static final String WINDOW_ICON_256 = "/org/treytomes/notepad/notepad_256.png";
	
	private static final Font FONT_DEFAULT = new Font("Lucida Console", Font.PLAIN, 14);

	private static final long serialVersionUID = 2512504132006442564L;
	
	private static final WindowListener closeWindow = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent evt) {
			evt.getWindow().setVisible(false);
			evt.getWindow().dispose();
		};
	};

	private TextFileModel _model;
	private JTextArea _textArea;

	public NotepadView() {
		setModel(null);
		
		setTitle(WINDOW_TITLE);
		setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(closeWindow);
		
		setIconImages(Arrays.asList(new Image[] {
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_16)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_24)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_32)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_48)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_256)),
		}));
		
		createTextArea();
	}
	
	public TextFileModel getModel() {
		return _model;
	}
	
	public void setModel(TextFileModel model) {
		if (_model != null) {
			_model.deleteObserver(this);
		}
		if (model == null) {
			System.err.println("Input model is null; creating a new model.");
			model = new TextFileModel();
		}
		_model = model;
		_model.addObserver(this);
	}
	
	public String getText() {
		return _textArea.getText();
	}
	
	public void setText(String value) {
		_textArea.setText(value);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TextFileModel) {
			switch (arg.toString()) {
			case "name":
				setTitle(String.format("%s - %s", _model.getName(), WINDOW_TITLE));
				break;
			case "contents":
				setText(_model.getContents());
				break;
			}
		}
	}

	private void createTextArea() {
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		_textArea = new JTextArea();
		_textArea.setFont(FONT_DEFAULT);
		_textArea.setLineWrap(true);
		scrollPane.setViewportView(_textArea);
		_textArea.setWrapStyleWord(true);
		/*_textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent evt) {
				updateModelFromTextArea();
			}
			
			@Override
			public void insertUpdate(DocumentEvent evt) {
				updateModelFromTextArea();
			}
			
			@Override
			public void changedUpdate(DocumentEvent evt) {
				updateModelFromTextArea();
			}
		});*/
	}
	
	/*private void updateModelFromTextArea() {
		_model.setContents(_textArea.getText());
	}*/
}
