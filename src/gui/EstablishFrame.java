package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.fonts.DefaultMacFontPolicy;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceSaharaLookAndFeel;
import org.jvnet.substance.theme.SubstanceLightAquaTheme;
import org.jvnet.substance.title.FlatTitlePainter;
import org.jvnet.substance.watermark.SubstanceImageWatermark;

import dealxml.DealXmlSax;
import serialzation.ReadConfigFromXml;
import singleton.Project;

public class EstablishFrame extends JFrame implements ActionListener,
		ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static EstablishFrame instance;

	private final int width = 800;
	private final int height = 618;
	int startLocationX;
	int startLocationY;

	private JPanel jp = new JPanel();
	private JLabel welcome = new JLabel();
	private JPanel listPanel = new JPanel();

	private JPanel projectPane = new JPanel();
	@SuppressWarnings("rawtypes")
	private JList projectList = new JList();
	private JLabel project = new JLabel();
	private JScrollPane sp = new JScrollPane(projectList);
	private JPanel functionPane = new JPanel();
	private JLabel function = new JLabel();
	String[] str1 = new String[] {

	};

	private JButton[] jbArray;

	private EstablishFrame() {

		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		// TODO Auto-generated method stub

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		ImageIcon icon = new ImageIcon(Project.getInstance().getConfigDir()
				+ "/res/mudroid.png");
		this.setIconImage(icon.getImage());


		jbArray = new JButton[] { new JButton("Start a Android project"),
				new JButton("Start a Eclispe project"),
				new JButton("Configue"), new JButton("Docs and How-Tos"), };
		jp.setLayout(null);
		jp.setBackground(Color.LIGHT_GRAY);
		jp.add(welcome);
		welcome.setBounds(0, 0, width, 100);
		welcome.setText("       Welcome to Android Mutants Analysis System");
		welcome.setFont(new Font("", 1, 30));
		jp.add(listPanel);
		listPanel.setBounds(0, 100, width, 470);
		// listPanel.setBorder(border);
		listPanel.setLayout(null);
		listPanel.add(projectPane);
		projectPane.setLayout(null);
		projectPane.setBounds(20, 20, 270, 435);
		projectPane.setBackground(Color.LIGHT_GRAY);
		projectPane.add(project);
		project.setBounds(0, 0, projectPane.getWidth(), 30);
		project.setHorizontalAlignment(SwingConstants.CENTER);
		;
		project.setText("Recent Project");
		projectPane.add(sp);
		sp.setBounds(0, 30, projectPane.getWidth(),
				projectPane.getHeight() - 30);

		List<String> pl = Project.getInstance().getProjectlist();
		if(pl==null)
		{
			return;
		}

		
		str1 = (String[]) pl.toArray(new String[pl.size()]);//
		String[] str2 = new String[str1.length];
		str2 = dealStr1(str1);
		projectList.setListData(str2);
		projectList.setFixedCellHeight(50);
		projectList.setFixedCellWidth(sp.getWidth() - 20);
		projectList.setSelectionBackground(Color.CYAN.brighter());
		projectList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		projectList.addListSelectionListener(this);

		projectList.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("rawtypes")
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					EstablishFrame.getInstance().setVisible(false);
					MainFrame.getInstance().setProjectName(
							str1[((JList) e.getSource()).getSelectedIndex()]);
					MainFrame.getInstance().setVisible(true);
					MainFrame.getInstance().validate();
					MainFrame.getInstance().repaint();

					// When double click JList
				}
			}
		});

		listPanel.add(functionPane);
		functionPane.setLayout(null);
		functionPane.setBounds(310, 20, 460, 435);
		functionPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		functionPane.add(function);
		function.setBounds(0, 0, functionPane.getWidth(), 30);
		function.setBackground(Color.LIGHT_GRAY);
		function.setOpaque(true);
		function.setHorizontalAlignment(SwingConstants.CENTER);
		function.setText("Quick Start");
		for (int i = 0; i < jbArray.length; i++) {
			functionPane.add(jbArray[i]);
			jbArray[i].setBounds(20, 70 + ((functionPane.getWidth() - 90) / 4)
					* i, 420, 60);
			jbArray[i].addActionListener(this);
			jbArray[i].setHorizontalAlignment(SwingConstants.LEFT);
			jbArray[i].setFont(new Font("", 1, 17));
		}

		// jbArray[0].setBackground();

		this.add(jp);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		startLocationX = (screenWidth - width) / 2;
		startLocationY = (screenHeight - height) / 2;

		this.setResizable(false);
		this.setBounds(startLocationX, startLocationY, width, height);
		// this.setVisible(true);
		if (str1.length > 0)
			projectList.setSelectedIndex(0);
	}

	private String[] dealStr1(String[] str1) {
		// TODO Auto-generated method stub
		String[] str2 = new String[str1.length];
		for (int i = 0; i < str1.length; i++) {
			System.out.println("filname" + str1[i]);
			if (str1[i] != null) {
				str1[i] = str1[i].replace('\\', '/');
				String temp = str1[i].substring(str1[i].lastIndexOf("/") + 1);
				str2[i] = "<html><font size=\"4\">" + temp
						+ "</font><br/><font color=gray>" + str1[i]
						+ "</font></html>";
			}
			//
		}
		return str2;
	}

	public static void main(String[] args) {
		DealXmlSax dxs = new ReadConfigFromXml();
		try {
			dxs.run(new String[] { Project.getInstance().getConfigPath() });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// SubstanceLookAndFeel.setSkin(new
					// BusinessBlackSteelSkin());
					// 设置外观
					// 设置外观
					UIManager
							.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());
					JFrame.setDefaultLookAndFeelDecorated(true);
					// 设置主题
					SubstanceLookAndFeel
							.setCurrentTheme(new SubstanceLightAquaTheme());
					// 设置按钮外观
					SubstanceLookAndFeel
							.setCurrentButtonShaper(new StandardButtonShaper());
					SubstanceSaharaLookAndFeel.setFontPolicy(new DefaultMacFontPolicy()); 
					// 设置水印
					SubstanceLookAndFeel
							.setCurrentWatermark(new SubstanceImageWatermark(
									Project.getInstance().getConfigDir()
											+ "/res/background.jpg"));
					// 设置边框
					SubstanceLookAndFeel
							.setCurrentBorderPainter(new StandardBorderPainter());
					// 设置渐变渲染
					SubstanceLookAndFeel
							.setCurrentGradientPainter(new StandardGradientPainter());
					// 设置标题
					SubstanceLookAndFeel
							.setCurrentTitlePainter(new FlatTitlePainter());
					EstablishFrame.getInstance().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jbArray[0]) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			// fileChooser.setBounds(startLocationX, startLocationY, width,
			// height);
			fileChooser.showDialog(null, null);
			String str = fileChooser.getSelectedFile().getPath();

			str = str.replace("\\", "/");
			// Project.getInstance().getProjectlist().add(str);

			// Project.getInstance().readProjectlist();

			// System.out.println(str);
			str1 = str1Adds(str1, str);
			System.out.println("s");
			for (int i = 0; i < str1.length; i++) {
				System.out.println(str1[i]);
			}
			System.out.println("e");

			String[] str2 = new String[str1.length];
			str2 = dealStr1(str1);
			projectList.setListData(str2);
			projectList.validate();
			projectList.repaint();
			projectList.updateUI();
			// StateToXml stx=new StateToXml();
			// String[] args=new String[]{"F:/muandroid3"};
			// try {
			// stx.run(args);
			// } catch (Exception e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			//
		}
	}

	private String[] str1Adds(String[] str1, String str) {
		// TODO Auto-generated method stub
		String[] str2 = new String[str1.length + 1];
		for (int i = 0; i < str1.length; i++) {
			str2[i] = str1[i];
		}
		str2[str1.length] = str;
		return str2;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(str1[((JList) e.getSource()).getSelectedIndex()]);

	}

	public static EstablishFrame getInstance() {
		if (instance == null) {

			instance = new EstablishFrame();
		}
		return instance;
	}

}
