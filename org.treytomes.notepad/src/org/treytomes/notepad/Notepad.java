package org.treytomes.notepad;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
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

public class Notepad {

	private static final Charset ENCODING = StandardCharsets.UTF_8;
	
	private JFrame frmNotepad;
	private JTextArea textArea;
	private JFileChooser _fileChooser;
	
	private static final WindowListener closeWindow = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent evt) {
			evt.getWindow().setVisible(false);
			evt.getWindow().dispose();
		};
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notepad window = new Notepad();
					window.frmNotepad.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Notepad() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		configureFileChooser();
		
		frmNotepad = new JFrame();
		frmNotepad.setTitle("Notepad");
		frmNotepad.setIconImage(Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource("/org/treytomes/notepad/notepad.png")));
		frmNotepad.setBounds(0, 0, 450, 300);
		frmNotepad.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frmNotepad.addWindowListener(closeWindow);
		
		JScrollPane scrollPane = new JScrollPane();
		frmNotepad.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		textArea.setWrapStyleWord(true);
		
		JMenuBar menuBar = new JMenuBar();
		frmNotepad.setJMenuBar(menuBar);
		
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
				_fileChooser.updateUI();
				int returnValue = _fileChooser.showOpenDialog(frmNotepad);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = _fileChooser.getSelectedFile();
					textArea.setText(readTextFile(file));
				} else {
					System.out.println("Open command cancelled.");
				}
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
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_fileChooser.updateUI();
				int returnValue = _fileChooser.showSaveDialog(frmNotepad);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = _fileChooser.getSelectedFile();
					writeTextFile(file, textArea.getText());
				} else {
					System.out.println("Save command cancelled.");
				}
			}
		});
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
				frmNotepad.dispatchEvent(new WindowEvent(frmNotepad, WindowEvent.WINDOW_CLOSING));
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
	    	lookAndFeelMenuItem.setModel(new LookAndFeelButtonModel(frmNotepad, info));
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
				new AboutNotepad(frmNotepad).setVisible(true);
			}
		});
		mntmNewMenuItem_1.setMnemonic('A');
		mnHelp.add(mntmNewMenuItem_1);
	}

	private void configureFileChooser() {
		_fileChooser = new JFileChooser();
		//_fileChooser.setCurrentDirectory(new File("c:/"));
	}
	
	private String readTextFile(File file) {
		System.out.println("Reading text file: " + file.getName());
		
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(file.toPath(), ENCODING)) {
			int nextCh = -1;
			while ((nextCh = reader.read()) != -1) {
				sb.append((char)nextCh);
			}
		} catch (IOException e) {
			System.err.println("File not found: " + file.getName());
		}
		
		System.out.println("Done reading text file.");
		
		return sb.toString();
	}
	
	private void writeTextFile(File file, String text) {
		System.out.println("Writing text file: " + file.getName());
		
		try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), ENCODING)) {
			for (int index = 0; index < text.length(); index++) {
				char ch = text.charAt(index);
				writer.write(ch);
			}
		} catch (IOException e) {
			System.err.println("Unable to write to file: " + file.getName());
		}
		
		System.out.println("Done writing text file.");
	}
}
