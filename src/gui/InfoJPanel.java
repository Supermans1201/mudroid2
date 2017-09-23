package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import singleton.Project;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class InfoJPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel functionPanel = new JPanel();
	
	final JWebBrowser webBrowser = new JWebBrowser();
	String projectName ="";

	void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		functionPanel.setLayout(new BoxLayout(functionPanel,
				BoxLayout.LINE_AXIS));
		functionPanel.setBorder(BorderFactory.createEtchedBorder());
		webBrowser.setBarsVisible(false);  
		webBrowser.navigate(Project.getInstance().getConfigDir()+"/html/landing.html");

		functionPanel.add(webBrowser, BorderLayout.CENTER);
		this.add(functionPanel);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	InfoJPanel() {
		this.projectName = Project.getInstance().getSelectProject();
		init();
	}

	InfoJPanel(String projectName) {
		this.projectName = projectName;
		init();
	}

	public static void main(String[] args) {
		NativeInterface.open();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame jf = new JFrame();
				InfoJPanel fjp = new InfoJPanel();
				jf.add(fjp);
				fjp.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				jf.setBounds(100, 100, 1300, 600);
				jf.setVisible(true);
				jf.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						System.exit(0);
					}
				});
			}
		});
		NativeInterface.runEventPump();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}