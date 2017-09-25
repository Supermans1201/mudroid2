package gui;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import util.GetFiles;

public class MutantRunJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTabbedPane jtp = new JTabbedPane();
	String projectName = "";

	MutantRunJPanel() {
		init();
	}

	MutantRunJPanel(String projectName) {
		this.projectName = projectName;
		init();
	}

	void init() {
		GetFiles.getMutFiles();

		this.add(jtp);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		ChooseMutantJavaPanel cmjp = new ChooseMutantJavaPanel();
		ChooseMutantXmlPanel xcmjp = new ChooseMutantXmlPanel();
		OrginMutantJavaPanel oamjp = new OrginMutantJavaPanel();
		OrginMutantXmlPanel xoamjp = new OrginMutantXmlPanel();
		// functionPanel.add(cmjp);

		jtp.addTab("��ʾJava������", oamjp);
		jtp.addTab("��ʾXml ������", xoamjp);
		jtp.addTab("ѡ��Java������", cmjp);
		jtp.addTab("ѡ�� Xml ������", xcmjp);

		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

}
