package org.treytomes.notepad;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

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
	private FileChooserView _fileChooserView;

	public NotepadView() {
		createTextArea();
		createMenu();
		
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
	}
	
	public TextFileModel getModel() {
		return _model;
	}
	
	public void setModel(TextFileModel model) {
		if (_model != null) {
			_model.deleteObserver(this);
			_textArea.getDocument().removeDocumentListener(_model);
		}
		if (model == null) {
			System.err.println("Input model is null; creating a new model.");
			model = new TextFileModel();
		}
		_model = model;
		_model.addObserver(this);
		_textArea.getDocument().addDocumentListener(_model);
		updateWindowTitle();
		_fileChooserView = new FileChooserView(this, _model);
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
			updateWindowTitle();
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
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnfile = new JMenu("File");
		mnfile.setMnemonic('F');
		menuBar.add(mnfile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setMnemonic('N');
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnfile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				_fileChooserView.openFile();
			}
		});
		mntmOpen.setMnemonic('O');
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnfile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setMnemonic('S');
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnfile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new FileSaveAsAction(_fileChooserView));
		mntmSaveAs.setMnemonic('A');
		mnfile.add(mntmSaveAs);
		
		JSeparator separator_4 = new JSeparator();
		mnfile.add(separator_4);
		
		JMenuItem mntmPageSetup = new JMenuItem("Page Setup...");
		mntmPageSetup.setMnemonic('u');
		mnfile.add(mntmPageSetup);
		
		JMenuItem mntmPrint = new JMenuItem("Print...");
		mntmPrint.setMnemonic('P');
		mntmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnfile.add(mntmPrint);
		
		JSeparator separator_5 = new JSeparator();
		mnfile.add(separator_5);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispatchEvent(new WindowEvent(NotepadView.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		mntmExit.setMnemonic('x');
		mnfile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setMnemonic('E');
		menuBar.add(mnEdit);
		
		JMenuItem mntmUndo = new JMenuItem("Undo");
		mntmUndo.setMnemonic('U');
		mntmUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		mnEdit.add(mntmUndo);
		
		JSeparator separator_1 = new JSeparator();
		mnEdit.add(separator_1);
		
		JMenuItem mntmCut = new JMenuItem("Cut");
		mntmCut.setMnemonic('t');
		mntmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mnEdit.add(mntmCut);
		
		JMenuItem mntmCope = new JMenuItem("Copy");
		mntmCope.setMnemonic('C');
		mntmCope.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnEdit.add(mntmCope);
		
		JMenuItem mntmPaste = new JMenuItem("Paste");
		mntmPaste.setMnemonic('P');
		mntmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		mnEdit.add(mntmPaste);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.setMnemonic('l');
		mntmDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		mnEdit.add(mntmDelete);
		
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		JMenuItem mntmFind = new JMenuItem("Find...");
		mntmFind.setMnemonic('F');
		mntmFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		mnEdit.add(mntmFind);
		
		JMenuItem mntmFindNext = new JMenuItem("Find Next");
		mntmFindNext.setMnemonic('N');
		mntmFindNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		mnEdit.add(mntmFindNext);
		
		JMenuItem mntmReplace = new JMenuItem("Replace...");
		mntmReplace.setMnemonic('R');
		mntmReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		mnEdit.add(mntmReplace);
		
		JMenuItem mntmGoto = new JMenuItem("Goto...");
		mntmGoto.setMnemonic('G');
		mntmGoto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		mnEdit.add(mntmGoto);
		
		JSeparator separator_3 = new JSeparator();
		mnEdit.add(separator_3);
		
		JMenuItem mntmSelectAll = new JMenuItem("Select All");
		mntmSelectAll.setMnemonic('A');
		mntmSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnEdit.add(mntmSelectAll);
		
		JMenuItem mntmTimedate = new JMenuItem("Time/Date");
		mntmTimedate.setMnemonic('D');
		mntmTimedate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		mnEdit.add(mntmTimedate);
		
		JMenu mnFormat = new JMenu("Format");
		mnFormat.setMnemonic('o');
		menuBar.add(mnFormat);
		
		JMenuItem mntmWordWrap = new JMenuItem("Word Wrap");
		mntmWordWrap.setMnemonic('W');
		mnFormat.add(mntmWordWrap);
		
		JMenuItem mntmFont = new JMenuItem("Font...");
		mntmFont.setMnemonic('F');
		mnFormat.add(mntmFont);
		
		JMenu mnView = new JMenu("View");
		mnView.setMnemonic('V');
		menuBar.add(mnView);
		
		JMenuItem mntmStatusBar = new JMenuItem("Status Bar");
		mntmStatusBar.setMnemonic('S');
		mnView.add(mntmStatusBar);
		
		JSeparator separator_6 = new JSeparator();
		mnView.add(separator_6);
		
		JMenu mnChange = new JMenu("Change Look And Feel...");
		mnChange.setMnemonic('C');
		mnView.add(mnChange);
		ButtonGroup lookAndFeelButtonGroup = new ButtonGroup();
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	    	JMenuItem lookAndFeelMenuItem = new LookAndFeelMenuItem();
	    	lookAndFeelMenuItem.setModel(new LookAndFeelButtonModel(this, info));
	        lookAndFeelButtonGroup.add(lookAndFeelMenuItem);
	        mnChange.add(lookAndFeelMenuItem);
	    }
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic('H');
		menuBar.add(mnHelp);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("View Help");
		mntmNewMenuItem.setMnemonic('H');
		mnHelp.add(mntmNewMenuItem);
		
		JSeparator separator = new JSeparator();
		mnHelp.add(separator);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("About Notepad");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new AboutNotepad(NotepadView.this).setVisible(true);
			}
		});
		mntmNewMenuItem_1.setMnemonic('A');
		mnHelp.add(mntmNewMenuItem_1);
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
