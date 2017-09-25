package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javamutation.DealJavaOJMutant;
import javamutation.DealJavaOJMutantAndroid;
import javamutation.DealJavaOJMutantClass;
import javamutation.DealJavaOJMutantException;
import javamutation.DealJavaOJMutantTradtional;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

import singleton.FileList;
import singleton.Op;
import singleton.Project;
import xmlmutation.DealXmlSaxMutant;

public class ProgressJFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Thread t;
	private final int width = 600;
	private final int height = 371;
	private JPanel jp = new JPanel();
	private JPanel javaPanel = new JPanel();
	private JPanel xmlPanel = new JPanel();
	public JLabel javajl = new JLabel("Show java information");
	public JLabel javaopjl = new JLabel("Show opType");
	public JLabel xmljl = new JLabel("Show java information");
	public JLabel xmlopjl = new JLabel("Show opType");
	public JProgressBar jpb = new JProgressBar();

	public JPanel btJP = new JPanel();
	public JPanel btleftJP = new JPanel();
	public JButton bt1 = new JButton("run in background");
	public JPanel btrightJP = new JPanel();
	public JButton bt2 = new JButton("Stop");

	public JProgressBar jpb2 = new JProgressBar();
	List<String> javafile_list;
	List<String> xmlfile_list;

	String[] args=new String[]{""};
	
	DealJavaOJMutant dj;
	boolean b = true;
	private static ProgressJFrame instance;

	private ProgressJFrame() {
		init();
		this.javafile_list = FileList.getInstance().getJavaSList();
		this.xmlfile_list = FileList.getInstance().getXmlSList();
	}


	private void init() {
		// TODO Auto-generated method stub
		ImageIcon icon = new ImageIcon(Project.getInstance().getConfigDir()
				+ "/res/mudroid.png");
		this.setIconImage(icon.getImage());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		int startLocationX = (screenWidth - width) / 2;
		int startLocationY = (screenHeight - height) / 2;

		initlayout();

		this.setResizable(false);
		this.setBounds(startLocationX, startLocationY, width, height);

	}

	public void strat() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				if (b) {
					b = false;

					jpb.setValue(0);
					jpb2.setValue(0);
					if (javafile_list != null)
						for (int i = 0; i < javafile_list.size(); i++) {
//							System.out.println("javafile£º"
//									+ javafile_list.get(i));
							args[0] = javafile_list.get(i);

							javajl.setText(" The file is dealing is  :  "
									+ javafile_list.get(i));
							xmljl.setText(" The file is dealing is  :  ");
							xmlopjl.setText(" The opType is  : ");
							jpb.setValue((i + 1) * 100 / javafile_list.size());
							jpb.validate();
							jpb.repaint();

							if (Op.getInstance().getClassOp() != null
									&& Op.getInstance().getClassOp().size() > 0) {

								javaopjl.setText(" The opType is  :  Class Op");
								
								dj=new DealJavaOJMutantClass(); 
								try {
									dj.run(args );
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								jpb.setValue((i + 1) * 100
										/ javafile_list.size() + 1
										* (25 / (javafile_list.size())));
							}
							if (Op.getInstance().getTradtionalOp() != null
									&& Op.getInstance().getTradtionalOp()
											.size() > 0) {
								javaopjl.setText(" The opType is  : tradtional Op");

								dj=new DealJavaOJMutantTradtional(); 
								try {
									dj.run(args );
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								jpb.setValue((i + 1) * 100
										/ javafile_list.size() + 2
										* (25 / (javafile_list.size())));

							}
							if (Op.getInstance().getExceptionOp() != null
									&& Op.getInstance().getExceptionOp().size() > 0) {
								javaopjl.setText(" The opType is  :  Exception Op");
								
								dj=new DealJavaOJMutantException(); 
								try {
									dj.run(args );
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								jpb.setValue((i + 1) * 100
										/ javafile_list.size() + 3
										* (25 / (javafile_list.size())));
							}
							// ////
							if (Op.getInstance().getAndroidOp() != null
									&& Op.getInstance().getAndroidOp().size() > 0) {
								javaopjl.setText(" The opType is  :  Android Op");
								
								dj=new DealJavaOJMutantAndroid(); 
								try {
									dj.run(args );
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								jpb.setValue((i + 1) * 100
										/ javafile_list.size() + 4
										* (25 / (javafile_list.size())));
							}

						}
					xmlopjl.setText(" The opType is  : Xml op");

					if (xmlfile_list != null)
						for (int i = 0; i < xmlfile_list.size(); i++) {
							xmljl.setText(" The file is dealing is  :  "
									+ xmlfile_list.get(i));
							args[0] = xmlfile_list.get(i);
							jpb2.setValue((i + 1) * 100 / xmlfile_list.size());
							if (Op.getInstance().getXmlOp() != null
									&& Op.getInstance().getXmlOp().size() > 0) {
								
								DealXmlSaxMutant.main(args);
							}
							System.out.println(xmlfile_list.get(i));
						}
					b = true;
				} else {
				}

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
		jp.add(javaPanel);
		jp.add(xmlPanel);
		javaPanel.setLayout(new BoxLayout(javaPanel, BoxLayout.PAGE_AXIS));
		javaPanel.setBorder(new TitledBorder("Show java mutant progress"));
		xmlPanel.setLayout(new BoxLayout(xmlPanel, BoxLayout.PAGE_AXIS));
		xmlPanel.setBorder(new TitledBorder("Show xml mutant progress"));
		javaPanel.add(jpb);
		javaPanel.add(javajl);
		javaPanel.add(javaopjl);
		xmlPanel.add(jpb2);
		xmlPanel.add(xmljl);
		xmlPanel.add(xmlopjl);
		jp.setBorder(new TitledBorder("Show the progress of mutant"));
		;
		jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
		jpb.setValue(0);
		jpb2.setValue(0);
		jpb.setStringPainted(true);
		jpb2.setStringPainted(true);
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

	public static ProgressJFrame getInstance() {
		if (instance == null) {
			instance = new ProgressJFrame();
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

			{
				t.stop();
				b = true;
			}
			this.setVisible(false);

		}
	}

}
