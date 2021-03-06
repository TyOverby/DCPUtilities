package com.prealpha.dcputil.ui;

import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.defaults.BasicSystem;
import com.prealpha.dcputil.emulator.EmulatorException;
import com.prealpha.dcputil.emulator.HaltConditions;
import com.prealpha.dcputil.emulator.Machine;
import com.prealpha.dcputil.emulator.StepEvent;
import com.prealpha.dcputil.emulator.devices.lem1802.SwingLem1802;
import com.prealpha.dcputil.ui.layout.TyLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
		frame.setBounds(100, 100, 921, 612);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new TyLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            // Don't do anything, we don't really care about the look and feel.
        }

        registryList = new DefaultListModel<String>();

        DefaultListModel<String> rnlm = new DefaultListModel<String>();
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

        stackListModel = new DefaultListModel<String>();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 89, 454, 427);
		
		
		JPanel debugPane = new JPanel();
		debugPane.setBorder(new TitledBorder(null, "Debug Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		debugPane.setBounds(723, 12, 176, 560);
		
		debugPane.setLayout(null);
		
		JPanel buttonsPane = new JPanel();
		buttonsPane.setBounds(10, 12, 531, 23);
		buttonsPane.setLayout(null);
		
		// RESIZING
		final int TOP_HEIGHT = 25;
		final int DEBUG_WIDTH = 175;
		
		frame.getContentPane().add(buttonsPane,new TyLayout.Resize() {
			public Rectangle resize(Component parent) {
				return new Rectangle(0,0,parent.getWidth()-DEBUG_WIDTH,TOP_HEIGHT);
			}
		});
		frame.getContentPane().add(debugPane,new TyLayout.Resize() {
			public Rectangle resize(Component parent) {
				return new Rectangle(parent.getWidth()-DEBUG_WIDTH,0,DEBUG_WIDTH,parent.getHeight());
			}
		});
		frame.getContentPane().add(tabbedPane,new TyLayout.Resize() {
			public Rectangle resize(Component parent) {
				return new Rectangle(0,TOP_HEIGHT,parent.getWidth()-DEBUG_WIDTH,parent.getHeight()-TOP_HEIGHT);
			}
		});
		// END RESIZING
		
		
		
		
		
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
		
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Registers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 21, 156, 272);
		debugPane.add(panel_3);
		panel_3.setLayout(null);
		JList<String> regNameList = new JList<String>(rnlm);
		regNameList.setBounds(10, 22, 31, 239);
		panel_3.add(regNameList);
		JList<String> regList = new JList<String>(registryList);
		regList.setBounds(51, 22, 88, 239);
		panel_3.add(regList);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Stack", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(10, 304, 156, 245);
		debugPane.add(panel_4);
		panel_4.setLayout(null);
		JList<String> stackList = new JList<String>(stackListModel);
		stackList.setBounds(10, 11, 136, 223);
		panel_4.add(stackList);
		
		
		
		JButton btnCompile = new JButton("Compile");
		btnCompile.setBounds(0, 0, 89, 23);
		buttonsPane.add(btnCompile);
		
		JButton btnStep = new JButton("Step");
		btnStep.setBounds(99, 0, 89, 23);
		buttonsPane.add(btnStep);
		
		JButton btnRun = new JButton("Run");
		btnRun.setBounds(198, 0, 89, 23);
		buttonsPane.add(btnRun);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(297, 0, 89, 23);
		buttonsPane.add(btnStop);

		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BasicDebuggerFrame.this.system.machine.addStepEvent(new StepEvent(){

						public void onStep(Machine machine) {
							BasicDebuggerFrame.this.onTick(BasicDebuggerFrame.this.system);							
						}});
					BasicDebuggerFrame.this.system.run(false,HaltConditions.NOOP, HaltConditions.BREAK,HaltConditions.NEAR_END);
				} catch (EmulatorException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                try {
                    BasicDebuggerFrame.this.system.step();
                } catch (EmulatorException e) {
                    e.printStackTrace();
                }
                onTick(BasicDebuggerFrame.this.system);
			}
		});
		btnCompile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                BasicDebuggerFrame.this.system = new BasicSystem();
                    SwingLem1802 lem = new SwingLem1802(3);
                BasicDebuggerFrame.this.system.machine.addDevice(lem);
                lem.getWrapped().setVisible(true);
                try {
					BasicDebuggerFrame.this.system.load(BasicDebuggerFrame.this.codeTextArea.getText());
					onTick(BasicDebuggerFrame.this.system);
                    scrollPane.scrollRectToVisible(new Rectangle(0,0,0,0));
				} catch (ParserException e) {
					e.printStackTrace();
				}
			}
		});
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
