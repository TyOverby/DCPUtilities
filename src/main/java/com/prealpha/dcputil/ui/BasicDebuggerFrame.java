package com.prealpha.dcputil.ui;

import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.defaults.BasicSystem;
import com.prealpha.dcputil.emulator.EmulatorException;
import com.prealpha.dcputil.emulator.Machine;
import com.prealpha.dcputil.emulator.StepEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.prealpha.dcputil.util.PrintUtilities.convertHex;
import static com.prealpha.dcputil.util.PrintUtilities.dump;

public class BasicDebuggerFrame {

    private BasicSystem system;

	private JFrame frame;
	private JTextArea codeTextArea;
	private JTextPane memoryTextArea;
    DefaultListModel<String> registryList;
    DefaultListModel<String> stackListModel;

    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasicDebuggerFrame window = new BasicDebuggerFrame();
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
	public BasicDebuggerFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 944, 759);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            // Don't do anything, we don't really care about the look and feel.
        }

        registryList = new DefaultListModel<String>();
		JList regList = new JList(registryList);
		regList.setBounds(830, 34, 88, 231);
		frame.getContentPane().add(regList);

        DefaultListModel<String> rnlm = new DefaultListModel<String>();
        JList regNameList = new JList(rnlm);
        rnlm.addElement(" A");
        rnlm.addElement(" B");
        rnlm.addElement(" C");
        rnlm.addElement(" X");
        rnlm.addElement(" Y");
        rnlm.addElement(" Z");
        rnlm.addElement(" I");
        rnlm.addElement(" J");
        rnlm.addElement(" ");
        rnlm.addElement(" PC");
        rnlm.addElement(" SP");
        rnlm.addElement(" EX");

        regNameList.setBounds(789, 34, 31, 231);
		frame.getContentPane().add(regNameList);

        stackListModel = new DefaultListModel<String>();
		JList stackList = new JList(stackListModel);
		stackList.setBounds(789, 298, 129, 412);
		frame.getContentPane().add(stackList);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 34, 769, 676);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Code", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, BorderLayout.CENTER);
		
		codeTextArea = new JTextArea();
		codeTextArea.setTabSize(4);
		scrollPane_1.setViewportView(codeTextArea);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Memory", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		final JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		memoryTextArea = new JTextPane();
		memoryTextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(memoryTextArea);
		
		JButton btnCompile = new JButton("Compile");
		btnCompile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                BasicDebuggerFrame.this.system = new BasicSystem();
                try {
					BasicDebuggerFrame.this.system.load(BasicDebuggerFrame.this.codeTextArea.getText());
					onTick(BasicDebuggerFrame.this.system);
                    scrollPane.scrollRectToVisible(new Rectangle(0,0,0,0));
				} catch (ParserException e) {
					e.printStackTrace();
				}
			}
		});
		btnCompile.setBounds(10, 8, 89, 23);
		frame.getContentPane().add(btnCompile);
		
		JButton btnStep = new JButton("Step");
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                try {
                    System.out.println("enter");
                    BasicDebuggerFrame.this.system.step();
                    System.out.println("exit");
                    //System.err.println((int)BasicDebuggerFrame.this.system.machine.getRegisters()[0]);
                } catch (EmulatorException e) {
                    e.printStackTrace();
                }
                onTick(BasicDebuggerFrame.this.system);
			}
		});
		btnStep.setBounds(109, 8, 89, 23);
		frame.getContentPane().add(btnStep);
		
		JLabel lblRegisters = new JLabel("Registers");
		lblRegisters.setBounds(789, 9, 46, 14);
		frame.getContentPane().add(lblRegisters);
		
		JLabel lblStack = new JLabel("Stack");
		lblStack.setBounds(789, 276, 46, 14);
		frame.getContentPane().add(lblStack);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BasicDebuggerFrame.this.system.machine.addStepEvent(new StepEvent(){

						public void onStep(Machine machine) {
							BasicDebuggerFrame.this.onTick(BasicDebuggerFrame.this.system);							
						}});
					BasicDebuggerFrame.this.system.runUntilTime(2000);
				} catch (EmulatorException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRun.setBounds(208, 8, 89, 23);
		frame.getContentPane().add(btnRun);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(307, 8, 89, 23);
		frame.getContentPane().add(btnStop);
	}

    private void onTick(BasicSystem system){
    	Machine machine = system.machine;
        //System.out.println("enter");
        if(machine!=null){
            this.memoryTextArea.setText(dump(machine.getMemory(),machine.getMask(),machine.getTotalModified(),machine.getPc(),machine.getSp()));

            this.registryList.clear();
            this.stackListModel.clear();

            for(char c:machine.getRegisters()){
                this.registryList.addElement(convertHex(c));
            }
            this.registryList.addElement(" ");

            this.registryList.addElement(convertHex(machine.getPc()));
            this.registryList.addElement(convertHex(machine.getSp()));
            this.registryList.addElement(convertHex(machine.getEx()));

            for(int i=machine.getMemory().length-1;i>0;i--){
                if(!machine.isModified(i)){
                    break;
                }
                else{
                    this.stackListModel.addElement(convertHex(machine.getMemory()[i]));
                }
            }
            this.memoryTextArea.setCaretPosition(0);
        }
        else{
            System.err.println("make a machine first");
        }
    }
}
