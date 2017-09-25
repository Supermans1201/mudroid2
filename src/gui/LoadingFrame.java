package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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

import serialzation.DealXmlSax;
import serialzation.ReadConfigFromXml;
import singleton.Project;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;

public class LoadingFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LoadingFrame instance;
	private JPanel jp = new JPanel();
	private JLabel showName = new JLabel();
	private JLabel showLogo = new JLabel();
	private JLabel showAuthor = new JLabel();
	private JLabel showVersion = new JLabel();
	private final int width = 600;
	private final int height = 371;

	public LoadingFrame() {
		init();
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
		jp.setLayout(null);
		jp.setBackground(Color.BLACK);
		jp.add(showLogo);
		showLogo.setText("MuDroid");
		showLogo.setFont(new Font("", 1, 30));
		showLogo.setForeground(Color.WHITE);
		showLogo.setBounds(90, 55, 300, 100);
		jp.add(showName);
		showName.setText("面向Android的变异分析系统");
		showName.setFont(new Font("", 1, 30));
		showName.setForeground(Color.WHITE);
		showName.setBounds(180, 60, 600, 300);
		jp.add(showAuthor);
		showAuthor.setText("Powered by supermnas1201");
		showAuthor.setForeground(Color.WHITE);
		showAuthor.setBounds(300, 240, 600, 20);
		jp.add(showVersion);
		showVersion.setText("version: 1.0.0.1");
		showVersion.setForeground(Color.WHITE);
		showVersion.setBounds(300, 260, 600, 20);

		this.add(jp);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setBounds(startLocationX, startLocationY, width, height);
		this.setVisible(true);
	}

	private static void readLastSave() {

		DealXmlSax dxs = new ReadConfigFromXml();
		try {
			dxs.run(new String[] { Project.getInstance().getConfigPath() });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean noProject = !Project.getInstance().getReadProject();
		if (noProject) {
			EstablishFrame.getInstance().setVisible(true);
			EstablishFrame.getInstance().reload();

		} else {
			MainFrame.getInstance().setVisible(true);
			MainFrame.getInstance().reload();
			MainFrame.getInstance().reload2();
		}

	}

	public static void main(String[] args) {
		LoadingFrame.getInstance().setVisible(true);

		NativeInterface.open();
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager
							.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());
					JFrame.setDefaultLookAndFeelDecorated(true);
					// 设置主题
					SubstanceLookAndFeel
							.setCurrentTheme(new SubstanceLightAquaTheme());
					// 设置按钮外观
					SubstanceLookAndFeel
							.setCurrentButtonShaper(new StandardButtonShaper());
					SubstanceSaharaLookAndFeel
							.setFontPolicy(new DefaultMacFontPolicy());
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
					readLastSave();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		NativeInterface.runEventPump();
	}

	public static LoadingFrame getInstance() {
		if (instance == null) {

			instance = new LoadingFrame();
		}
		return instance;
	}
}
