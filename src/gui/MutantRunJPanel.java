package gui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import singleton.Project;
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

		jtp.addTab("show java mutants", oamjp);
		jtp.addTab("show xml mutants", xoamjp);
		jtp.addTab("choose java mutants", cmjp);
		jtp.addTab("choose xml mutants", xcmjp);

		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	public static void main(String[] args)
	{
		Project.getInstance().setSelectProject("F:/EspressoExamples-master/EspressoExamples-master");
		GetFiles.getMutFiles();
		JFrame jf=new JFrame();
		MutantRunJPanel fjp=new MutantRunJPanel();
		jf.add(fjp);
		fjp.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		jf.setBounds(100,100,1300,600);
		jf.setVisible(true);	
		jf.addWindowListener(new WindowAdapter(){

			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
	}

}
