package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import dealxml.DealXmlSax;
import serialzation.ReadConfigFromXml;
import singleton.Project;

public class MainFrame extends JFrame implements ActionListener,
		PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainFrame instance;
	private final int width = 1200;
	private final int height = 690;
	private SpringLayout springlayout = new SpringLayout();
	private JPanel mainJPanel = new JPanel();
	private JLabel messageJLabel = new JLabel();
	private JTabbedPane functionJPanel = new JTabbedPane();
	private JLabel functionJLabel = new JLabel();
	private JToolBar toolBar = new JToolBar();
	private JButton[] toolBarButton = new JButton[] {
			new JButton("toolButton 1"), new JButton("toolButton 2"),
			new JButton("toolButton 3"), new JButton("toolButton 4"), };
	private JMenuBar jmenuBar = new JMenuBar();
	private JMenu[] jmenuArray = { new JMenu("�ļ�"), new JMenu("����"),
			new JMenu("����") };
	private JMenuItem[] jmenuItemforJmenu1 = { new JMenuItem("�رյ�ǰ��Ŀ"), };
	private JMenuItem[] jmenuItemforJmenu2 = { new JMenuItem("JMenuItem1"), };
	private JMenuItem[] jmenuItemforJmenu3 = { new JMenuItem("�ĵ�"), };
	private Boolean toolBarIsHorizontal = true;
	private String projectName = "";

	private MainFrame() {
		this.projectName = Project.getInstance().getSelectProject();
		init();
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectName() {
		return this.projectName;
	}

	private void init() {
		// TODO Auto-generated method stub

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		int startLocationX = (screenWidth - width) / 2;
		int startLocationY = (screenHeight - height) / 2;

		for (int i = 0; i < jmenuArray.length; i++) {
			jmenuBar.add(jmenuArray[i]);
		}
		for (int i = 0; i < jmenuItemforJmenu1.length; i++) {
			jmenuArray[0].add(jmenuItemforJmenu1[i]);
			jmenuItemforJmenu1[i].addActionListener(this);
		}
		for (int i = 0; i < jmenuItemforJmenu2.length; i++) {
			jmenuArray[1].add(jmenuItemforJmenu2[i]);
			jmenuItemforJmenu2[i].addActionListener(this);
		}
		for (int i = 0; i < jmenuItemforJmenu3.length; i++) {
			jmenuArray[2].add(jmenuItemforJmenu3[i]);
			jmenuItemforJmenu3[i].addActionListener(this);
		}
		mainJPanel.setLayout(springlayout);
		mainJPanel.add(toolBar);
		toolBar.addPropertyChangeListener(this);
		for (int i = 0; i < toolBarButton.length; i++) {
			toolBar.add(toolBarButton[i]);
			toolBarButton[i].addActionListener(this);
		}
		springlayout.putConstraint(SpringLayout.NORTH, toolBar, 0,
				SpringLayout.NORTH, mainJPanel);

		mainJPanel.add(functionJPanel);
		// ParpareJPanel pjp=new ParpareJPanel(this.projectName);
		// MutantJPanel mjp=new MutantJPanel(this.projectName);
		// RunJPanel rjp=new RunJPanel(this.projectName);
		// functionJPanel.addTab("Parpare", pjp);
		// functionJPanel.addTab("Mutant", mjp);
		// functionJPanel.addTab("Run", rjp);
		// functionJPanel.addTab("Analysis", new JLabel("this show tab 4"));

		functionJPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		functionJLabel.setText(functionJLabel.getWidth() + ":"
				+ functionJLabel.getHeight());
		mainJPanel.add(messageJLabel);

		messageJLabel.setText("imformation: "
				+ Project.getInstance().getSelectProject());
		springlayout.putConstraint(SpringLayout.SOUTH, messageJLabel, 0,
				SpringLayout.SOUTH, mainJPanel);

		toolBar.setOrientation(SwingConstants.VERTICAL);

		if (toolBar.getOrientation() == SwingConstants.HORIZONTAL)
			toolBarIsHorizontal = true;
		else if (toolBar.getOrientation() == SwingConstants.VERTICAL)
			toolBarIsHorizontal = false;
		drawToolBarAndFunction(toolBarIsHorizontal);

		this.setJMenuBar(jmenuBar);
		this.add(mainJPanel);
		this.setBounds(startLocationX, startLocationY, width, height);
	}

	private void drawToolBarAndFunction(Boolean toolBarIsHorizontal) {
		// TODO Auto-generated method stub
		if (toolBarIsHorizontal) {
			functionJPanel.setTabPlacement(JTabbedPane.LEFT);
			springlayout.putConstraint(SpringLayout.NORTH, functionJPanel, 0,
					SpringLayout.SOUTH, toolBar);
			Spring jtfw = Spring.constant(0, 100, 400);
			springlayout.putConstraint(SpringLayout.SOUTH, functionJPanel,
					jtfw, SpringLayout.NORTH, functionJPanel);
			springlayout.putConstraint(SpringLayout.SOUTH, mainJPanel, 20,
					SpringLayout.SOUTH, functionJPanel);
			springlayout.putConstraint(SpringLayout.WEST, functionJPanel, 13,
					SpringLayout.WEST, mainJPanel);
			springlayout.putConstraint(SpringLayout.EAST, mainJPanel, 13,
					SpringLayout.EAST, functionJPanel);
		} else {
			functionJPanel.setTabPlacement(JTabbedPane.TOP);
			springlayout.putConstraint(SpringLayout.WEST, functionJPanel, 0,
					SpringLayout.EAST, toolBar);
			Spring jtfw = Spring.constant(0, 100, 400);
			springlayout.putConstraint(SpringLayout.EAST, functionJPanel, jtfw,
					SpringLayout.WEST, functionJPanel);
			springlayout.putConstraint(SpringLayout.EAST, mainJPanel, 13,
					SpringLayout.EAST, functionJPanel);
			springlayout.putConstraint(SpringLayout.NORTH, functionJPanel, 13,
					SpringLayout.NORTH, mainJPanel);
			springlayout.putConstraint(SpringLayout.SOUTH, mainJPanel, 20,
					SpringLayout.SOUTH, functionJPanel);
		}
	}

	public static void main(String[] args) {
		DealXmlSax dxs = new ReadConfigFromXml();
		try {
			dxs.run(new String[] { Project.getInstance().getConfigPath() });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainFrame.getInstance().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == toolBarButton[0]) {
			messageJLabel.setText("��������");
		}
		if (e.getSource() == toolBarButton[1]) {
			messageJLabel.setText("����ĿĿ¼");
		}
		if (e.getSource() == toolBarButton[2]) {
			messageJLabel.setText("���¼�������");
		}
		if (e.getSource() == toolBarButton[3]) {
			messageJLabel.setText("ˢ�µ�ǰ���");
		}

		if (e.getSource() == jmenuItemforJmenu1[0]) {
			System.out.println(e.getSource());
			{
				this.setVisible(false);
			}
		}
		if (e.getSource() == jmenuItemforJmenu2[0]) {
			System.out.println(e.getSource());
			{
				this.setVisible(false);
			}
		}
		if (e.getSource() == jmenuItemforJmenu3[0]) {
			System.out.println(e.getSource());
			{
				this.setVisible(false);
			}
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		boolean ishorizontal = true;
		if (toolBar.getOrientation() == SwingConstants.VERTICAL) {
			ishorizontal = false;
		}
		if (toolBar.getOrientation() == SwingConstants.HORIZONTAL) {
			ishorizontal = true;
		}

		toolBarIsHorizontal = ishorizontal;
		drawToolBarAndFunction(ishorizontal);
	}

	public static MainFrame getInstance() {
		if (instance == null) {

			instance = new MainFrame();
		}
		return instance;
	}

}
