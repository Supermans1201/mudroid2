package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

import singleton.Project;
import util.CopyFiles;
import util.MutantRunTemplate;

public class ProgressJFrame2 extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Thread t;
	private final int width = 700;
	private final int height = 550;
	private JPanel jp = new JPanel();
	private JPanel javaPanelcm = new JPanel();
	private JPanel javaPaneltm = new JPanel();
	private JPanel javaPanelem = new JPanel();
	private JPanel javaPanelam = new JPanel();
	private JPanel xmlPanelxm = new JPanel();
	public JLabel javacmjl = new JLabel("Show java information");
	public JLabel javacmopjl = new JLabel("Show opType");
	public JLabel javatmjl = new JLabel("Show java information");
	public JLabel javatmopjl = new JLabel("Show opType");
	public JLabel javaemjl = new JLabel("Show java information");
	public JLabel javaemopjl = new JLabel("Show opType");
	public JLabel javaamjl = new JLabel("Show java information");
	public JLabel javaamopjl = new JLabel("Show opType");
	public JLabel xmljl = new JLabel("Show java information");
	public JLabel xmlopjl = new JLabel("Show opType");
	public JProgressBar jpbcm = new JProgressBar();
	public JProgressBar jpbtm = new JProgressBar();
	public JProgressBar jpbem = new JProgressBar();
	public JProgressBar jpbam = new JProgressBar();
	public JPanel btJP = new JPanel();
	public JPanel btleftJP = new JPanel();
	public JButton bt1 = new JButton("run in background");
	public JPanel btrightJP = new JPanel();
	public JButton bt2 = new JButton("Stop");

	public JProgressBar jpbxm = new JProgressBar();

	boolean b = true;
	private static ProgressJFrame2 instance;

	private ProgressJFrame2() {
		init();

	}
	public void clear()
	{ 
		jpbcm.setValue(0);
		jpbtm.setValue(0);
		jpbem.setValue(0);
		jpbam.setValue(0);
		jpbxm.setValue(0);
	}

	boolean cmbisSelected = false;
	boolean tmbisSelected = false;
	boolean embisSelected = false;
	boolean ambisSelected = false;
	boolean xmbisSelected = false;

	String[] fileList;
	String[] cmop;
	String[] tmop;
	String[] amop;
	String[] emop;
	String[] xmop;

	public void setSome(boolean cmbisSelected, boolean tmbisSelected,
			boolean embisSelected, boolean ambisSelected, boolean xmbisSelected) {
		this.cmbisSelected = cmbisSelected;
		this.tmbisSelected = tmbisSelected;
		this.embisSelected = embisSelected;
		this.ambisSelected = ambisSelected;
		this.xmbisSelected = xmbisSelected;
	}

	public void setSome2(String[] fileList, String[] cmop, String[] tmop,
			String[] amop, String[] emop, String[] xmop) {
		// TODO Auto-generated method stub
		this.fileList = fileList;
		this.cmop = cmop;
		this.tmop = tmop;
		this.emop = emop;
		this.amop = amop;
		this.xmop = xmop;
	}

	private void init() {
		// TODO Auto-generated method stub
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		int startLocationX = (screenWidth - width) / 2;
		int startLocationY = (screenHeight - height) / 2;

		initlayout();

		this.setResizable(false);
		this.setBounds(startLocationX, startLocationY, width, height);
		
	}

	public void start() {
		Runnable r = new Runnable() {
			@Override
			public void run() {

				if (b) {
					b = false;
					System.out.println("runTest");
					if (cmbisSelected) {

						MutantRunTemplate mr = new MutantRunTemplate(
								Project.getInstance().getJavaMutFilterLoc());
						mr.setType("c");
						mr.setDate(fileList, cmop);
						mr.doJobInTransaction();

					} else {
						jpbcm.setValue(100);
					}
					if (tmbisSelected) {

						MutantRunTemplate mr = new MutantRunTemplate(
								Project.getInstance().getJavaMutFilterLoc());
						mr.setType("t");
						mr.setDate(fileList, tmop);
						mr.doJobInTransaction();

					} else {
						jpbtm.setValue(100);
					}
					;
					if (embisSelected) {
						MutantRunTemplate mr = new MutantRunTemplate(
								Project.getInstance().getJavaMutFilterLoc());
						mr.setType("e");
						mr.setDate(fileList, emop);
						mr.doJobInTransaction();
					} else {
						jpbem.setValue(100);
					}
					;
					if (ambisSelected) {
						MutantRunTemplate mr = new MutantRunTemplate(
								Project.getInstance().getJavaMutFilterLoc());
						mr.setType("a");
						mr.setDate(fileList, amop);
						mr.doJobInTransaction();
					} else {
						jpbam.setValue(100);
					}
					if (xmbisSelected) {
						MutantRunTemplate mr = new MutantRunTemplate(
								Project.getInstance().getXmlMutFilterLoc());
						mr.setType("x");
						mr.setDate(fileList, xmop);
						mr.doJobInTransaction();
					} else {
						jpbxm.setValue(100);
					}
				}

				b = true;
			}
		};

		t = new Thread(r);
		t.start();
	}

	public void stop() {

	}

	private void initlayout() {
		// TODO Auto-generated method stub
		this.add(jp);
		jp.add(javaPanelcm);
		jp.add(javaPaneltm);
		jp.add(javaPanelem);
		jp.add(javaPanelam);
		javaPanelcm.setLayout(new BoxLayout(javaPanelcm, BoxLayout.PAGE_AXIS));
		javaPanelcm.setBorder(new TitledBorder("Show run test of java CM"));
		jp.add(xmlPanelxm);
		javaPaneltm.setLayout(new BoxLayout(javaPaneltm, BoxLayout.PAGE_AXIS));
		javaPaneltm.setBorder(new TitledBorder("Show run test of java TM"));

		javaPanelem.setLayout(new BoxLayout(javaPanelem, BoxLayout.PAGE_AXIS));
		javaPanelem.setBorder(new TitledBorder("Show run test of java EM"));

		javaPanelam.setLayout(new BoxLayout(javaPanelam, BoxLayout.PAGE_AXIS));
		javaPanelam.setBorder(new TitledBorder("Show run test of java AM"));

		xmlPanelxm.setLayout(new BoxLayout(xmlPanelxm, BoxLayout.PAGE_AXIS));
		xmlPanelxm.setBorder(new TitledBorder("Show run test of xml XM"));
		javaPanelcm.add(jpbcm);
		javaPanelcm.add(javacmjl);
		javaPanelcm.add(javacmopjl);

		javaPaneltm.add(jpbtm);
		javaPaneltm.add(javatmjl);
		javaPaneltm.add(javatmopjl);

		javaPanelem.add(jpbem);
		javaPanelem.add(javaemjl);
		javaPanelem.add(javaemopjl);

		javaPanelam.add(jpbam);
		javaPanelam.add(javaamjl);
		javaPanelam.add(javaamopjl);

		xmlPanelxm.add(jpbxm);
		xmlPanelxm.add(xmljl);
		xmlPanelxm.add(xmlopjl);
		jp.setBorder(new TitledBorder("Show the progress of run test"));
		jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
		jpbcm.setValue(0);
		jpbtm.setValue(0);
		jpbem.setValue(0);
		jpbam.setValue(0);
		jpbxm.setValue(0);
		jpbcm.setStringPainted(true);
		jpbtm.setStringPainted(true);
		jpbem.setStringPainted(true);
		jpbam.setStringPainted(true);
		jpbxm.setStringPainted(true);
		jp.add(btJP);
		btJP.setLayout(new BoxLayout(btJP, BoxLayout.LINE_AXIS));
		btJP.add(btleftJP);
		btJP.add(btrightJP);
		btleftJP.add(bt1);
		bt1.addActionListener(this);
		bt1.setPreferredSize(new Dimension(160, 30));
		btrightJP.add(bt2);
		bt2.addActionListener(this);
		bt2.setPreferredSize(new Dimension(120, 30));

	}

	public static ProgressJFrame2 getInstance() {
		if (instance == null) {
			instance = new ProgressJFrame2();
		}
		return instance;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == bt1) {
			instance.setVisible(false);
		}
		if (e.getSource() == bt2) {
			
			File fromDir= new File(Project.getInstance().getSelectProject()+"/app/src");
			File toDir=new File(Project.getInstance().getSelectProject()+"/copyofsrc");
			CopyFiles.copyDir(toDir, fromDir, true);
			System.out.println("¸´ÖÆÍê±Ï£¡");
			
			{
				t.stop();
				b = true;
			}
			this.setVisible(false);

		}
	}

}
