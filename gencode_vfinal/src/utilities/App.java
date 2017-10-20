package utilities;

import generator.GeneratorStrategy;
import generator.Android.ModelAndroid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class App {

	private JFrame frame;
	private JTextField textField;
	private JButton btnGenerate;
	private File selectedFile = null;
	private JTextArea textArea;
	private JTextField textField_1;
	private JScrollPane scrollPane;
	private JPanel panel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 558, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("Button.border"));
		panel.setToolTipText("");
		panel.setBounds(0, 0, 552, 272);
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{552, 0};
		gbl_panel.rowHeights = new int[]{38, 197, 31, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		panel_1 = new JPanel();
		panel_1.setBorder(UIManager.getBorder("Button.border"));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(5, 5, 5, 5);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{179, 120, 243, 0};
		gbl_panel_1.rowHeights = new int[]{34, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		
		panel_1.setLayout(gbl_panel_1);
		JButton btnNewButton = new JButton("Load file");
	
		
		panel_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnNewButton, textField, btnGenerate}));
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel_1, btnNewButton, btnGenerate, textField, scrollPane, textArea, textField_1}));
		
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		btnNewButton.setPreferredSize(new Dimension(20, 20));
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Define path of you uml file
				String path = "C:/path_of_uml_file";
				JFileChooser fileChooser = new JFileChooser(path);
		        int returnValue = fileChooser.showOpenDialog(frame);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {		          
		          
		          selectedFile = fileChooser.getSelectedFile();
		          //System.out.println(selectedFile.getName());
				  //textField.setText(selectedFile.getName());
		          System.out.println(selectedFile.getAbsolutePath());
				  textField.setText(selectedFile.getAbsolutePath());	          
		          
				  textField.setEnabled(false);	
				  btnGenerate.setEnabled(true);	        
		        }
			}
		});
		
		btnGenerate = new JButton("Generate");
		btnGenerate.setForeground(Color.BLUE);
		GridBagConstraints gbc_btnGenerate = new GridBagConstraints();
		gbc_btnGenerate.fill = GridBagConstraints.BOTH;
		gbc_btnGenerate.insets = new Insets(0, 0, 0, 5);
		gbc_btnGenerate.gridx = 1;
		gbc_btnGenerate.gridy = 0;
		panel_1.add(btnGenerate, gbc_btnGenerate);
		btnGenerate.setEnabled(false);
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						String inputFileName = textField.getText();
						
						GeneratorStrategy generator;					
						Parser.loadXMI(inputFileName.toString());
						Parser.runParser(inputFileName.toString());
						Parser.getModel().printProp();
						generator = new ModelAndroid(Parser.getModel());
						generator.codeGenerator(null, 0);	
						final String pathOut = Parser.getModel().getDirectoryOut();	
			
						System.out.println(pathOut);
															
						runDelayedTask(400, new TimerTask() {
		
							@Override
							public void run() {
								Logger.getGlobal().log(Level.INFO, "Finished generating source code for UML model");								
							}
						});
						
						runDelayedTask(1500, new TimerTask() {
							
							@Override
							public void run() {
								textArea.setForeground(Color.GREEN);				
								textField_1.setText(Paths.get(pathOut).getParent().getParent().toString());							
							}
						});
						
		
				} catch (FileNotFoundException e1) {
					textArea.setText(e1.toString());
					textArea.setText("please choose an UML file");
					textArea.setForeground(Color.RED);
				} catch (IOException e1) {
					textArea.setText(e1.toString());
					textArea.setText("please choose an UML file");
					textArea.setForeground(Color.RED);					
				}				
			}
		});
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);
		textField.setText(null);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(5, 10, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea(20,32);
		scrollPane.setViewportView(textArea);
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 5, 0, 0);
		gbc_textField_1.fill = GridBagConstraints.BOTH;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 2;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		TextAreaLoggerHandler textAreaLoggerHandler = new TextAreaLoggerHandler(textArea);
		Logger.getGlobal().addHandler(textAreaLoggerHandler);
		
	}
	
	public void runDelayedTask(int miliseconds, TimerTask task) {
		Timer delayTaskTimer = new Timer(true);
		delayTaskTimer.schedule(task, miliseconds);
	}
}
