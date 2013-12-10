package org.treytomes.notepad;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager.LookAndFeelInfo;

public class NotepadView extends JFrame implements PropertyChangeListener {
	
	private static final String WINDOW_TITLE = "Notepad";
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;

	private static final String WINDOW_ICON_16 = "/org/treytomes/notepad/resources/notepad_16.png";
	private static final String WINDOW_ICON_24 = "/org/treytomes/notepad/resources/notepad_24.png";
	private static final String WINDOW_ICON_32 = "/org/treytomes/notepad/resources/notepad_32.png";
	private static final String WINDOW_ICON_48 = "/org/treytomes/notepad/resources/notepad_48.png";
	private static final String WINDOW_ICON_256 = "/org/treytomes/notepad/resources/notepad_256.png";
	
	private static final Font FONT_DEFAULT = new Font("Lucida Console", Font.PLAIN, 14);

	private static final long serialVersionUID = 2512504132006442564L;
	
	private static final Logger LOGGER = Logger.getLogger(NotepadView.class.getName());

	private TextFileDocument _model;
	private JTextArea _textArea;
	private FileChooserView _fileChooser;

	public NotepadView(TextFileDocument model) {
		LOGGER.info("Loading the main window...");
		
		setModel(model);
		createTextArea();
		_fileChooser = new FileChooserView(this, model);
		
		createMenu();
		
		setTitle(WINDOW_TITLE);
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowCloseAction(this));
		
		setIconImages(Arrays.asList(new Image[] {
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_16)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_24)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_32)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_48)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_256)),
		}));
		
		LOGGER.info("The main window is now loaded.");
	}
	
	public NotepadView() {
		this(null);
	}
	
	public TextFileDocument getModel() {
		return _model;
	}
	
	public void setModel(TextFileDocument model) {
		if (_model == model) {
			return;
		}
		
		LOGGER.info("Assigning a new model to the main window...");
		
		if (_model != null) {
			LOGGER.info("Removing the old model.");
			_model.removePropertyChangeListener(this);
			_model = null;
		}
		if (model == null) {
			LOGGER.info("Input model is null; creating a new model.");
			model = new TextFileDocument();
		}
		_model = model;
		_model.addPropertyChangeListener(this);
		updateWindowTitle();
		if (_textArea != null) {
			_textArea.setDocument(_model);
		}
		if (_fileChooser != null) {
			_fileChooser.setModel(_model);
		}
		
		LOGGER.info("The new model is now assigned.");
	}
	
	public String getText() {
		return _textArea.getText();
	}
	
	public void setText(String value) {
		_textArea.setText(value);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() instanceof TextFileDocument) {
			switch (evt.getPropertyName()) {
			case "needsSave":
			case "filename":
				updateWindowTitle();
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
		_textArea.setDocument(_model);
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.setMnemonic('N');
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		fileMenu.add(newMenuItem);
		
		JMenuItem openMenuItem = new JMenuItem("Open...");
		openMenuItem.addActionListener(new FileOpenAction(_fileChooser));
		openMenuItem.setMnemonic('O');
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		fileMenu.add(openMenuItem);
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setMnemonic('S');
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		fileMenu.add(saveMenuItem);
		
		JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
		saveAsMenuItem.addActionListener(new FileSaveAsAction(_fileChooser));
		saveAsMenuItem.setMnemonic('A');
		fileMenu.add(saveAsMenuItem);
		
		fileMenu.add(new JSeparator());
		
		JMenuItem pageSetupMenuItem = new JMenuItem("Page Setup...");
		pageSetupMenuItem.setMnemonic('u');
		fileMenu.add(pageSetupMenuItem);
		
		JMenuItem printMenuItem = new JMenuItem("Print...");
		printMenuItem.setMnemonic('P');
		printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		fileMenu.add(printMenuItem);
		
		fileMenu.add(new JSeparator());
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new WindowCloseAction(this));
		exitMenuItem.setMnemonic('x');
		fileMenu.add(exitMenuItem);
		
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic('E');
		menuBar.add(editMenu);
		
		JMenuItem undoMenuItem = new JMenuItem("Undo");
		undoMenuItem.setMnemonic('U');
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		editMenu.add(undoMenuItem);
		
		editMenu.add(new JSeparator());
		
		JMenuItem cutMenuItem = new JMenuItem("Cut");
		cutMenuItem.setMnemonic('t');
		cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		editMenu.add(cutMenuItem);
		
		JMenuItem copyMenuItem = new JMenuItem("Copy");
		copyMenuItem.setMnemonic('C');
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		editMenu.add(copyMenuItem);
		
		JMenuItem pasteMenuItem = new JMenuItem("Paste");
		pasteMenuItem.setMnemonic('P');
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		editMenu.add(pasteMenuItem);
		
		JMenuItem deleteMenuItem = new JMenuItem("Delete");
		deleteMenuItem.setMnemonic('l');
		deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		editMenu.add(deleteMenuItem);
		
		editMenu.add(new JSeparator());
		
		JMenuItem findMenuItem = new JMenuItem("Find...");
		findMenuItem.setMnemonic('F');
		findMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		editMenu.add(findMenuItem);
		
		JMenuItem findNextMenuItem = new JMenuItem("Find Next");
		findNextMenuItem.setMnemonic('N');
		findNextMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		editMenu.add(findNextMenuItem);
		
		JMenuItem replaceMenuItem = new JMenuItem("Replace...");
		replaceMenuItem.setMnemonic('R');
		replaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		editMenu.add(replaceMenuItem);
		
		JMenuItem gotoMenuItem = new JMenuItem("Goto...");
		gotoMenuItem.setMnemonic('G');
		gotoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		editMenu.add(gotoMenuItem);
		
		editMenu.add(new JSeparator());
		
		JMenuItem selectAllMenuItem = new JMenuItem("Select All");
		selectAllMenuItem.setMnemonic('A');
		selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		editMenu.add(selectAllMenuItem);
		
		JMenuItem timeDateMenuItem = new JMenuItem("Time/Date");
		timeDateMenuItem.setMnemonic('D');
		timeDateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		editMenu.add(timeDateMenuItem);
		
		JMenu formatMenu = new JMenu("Format");
		formatMenu.setMnemonic('o');
		menuBar.add(formatMenu);
		
		JMenuItem wordWrapMenuItem = new JMenuItem("Word Wrap");
		wordWrapMenuItem.setMnemonic('W');
		formatMenu.add(wordWrapMenuItem);
		
		JMenuItem fontMenuItem = new JMenuItem("Font...");
		fontMenuItem.setMnemonic('F');
		formatMenu.add(fontMenuItem);
		
		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic('V');
		menuBar.add(viewMenu);
		
		JMenuItem statusBarMenuItem = new JMenuItem("Status Bar");
		statusBarMenuItem.setMnemonic('S');
		viewMenu.add(statusBarMenuItem);
		
		viewMenu.add(new JSeparator());
		
		JMenu changeLookMenuItem = new JMenu("Change Look And Feel...");
		changeLookMenuItem.setMnemonic('C');
		viewMenu.add(changeLookMenuItem);
		ButtonGroup lookAndFeelButtonGroup = new ButtonGroup();
	    for (LookAndFeelInfo info : LookAndFeelManager.getInstalledLookAndFeels()) {
	    	JMenuItem lookAndFeelMenuItem = new LookAndFeelMenuItem();
	    	lookAndFeelMenuItem.setModel(new LookAndFeelButtonModel(this, info));
	        lookAndFeelButtonGroup.add(lookAndFeelMenuItem);
	        changeLookMenuItem.add(lookAndFeelMenuItem);
	    }
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		menuBar.add(helpMenu);
		
		JMenuItem viewHelpMenuItem = new JMenuItem("View Help");
		viewHelpMenuItem.setMnemonic('H');
		helpMenu.add(viewHelpMenuItem);
		
		helpMenu.add(new JSeparator());
		
		JMenuItem aboutMenuItem = new JMenuItem("About Notepad");
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new AboutNotepad(NotepadView.this).setVisible(true);
			}
		});
		aboutMenuItem.setMnemonic('A');
		helpMenu.add(aboutMenuItem);
	}
	
	/**
	 * Update the window title based on information in the model.
	 */
	private void updateWindowTitle() {
		StringBuilder sb = new StringBuilder();
		
		if (_model.getNeedsSave()) {
			sb.append('*');
		}
		sb.append(_model.getName());
		sb.append(" - ");
		sb.append(WINDOW_TITLE);
		
		setTitle(sb.toString());
	}
}
