package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingFrame extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jp=new JPanel();
	private JLabel showName=new JLabel();
	private JLabel showLogo=new JLabel();
	private JLabel showAuthor=new JLabel();
	private JLabel showVersion=new JLabel();
	private final int width=600;
	private final int height=371;
	private boolean noProject=true;
	public LoadingFrame()
	{
		
		init();
		readLastSave();
		openNextFrame();
		
	}
	
	private void init() {
		// TODO Auto-generated method stub
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		int startLocationX=(screenWidth-width)/2;
		int startLocationY=(screenHeight-height)/2;
		jp.setLayout(null);
		jp.setBackground(Color.BLACK);
		jp.add(showLogo);
		showLogo.setText("MuDroid");
		showLogo.setFont(new Font("",1,30));
		showLogo.setForeground(Color.WHITE);
		showLogo.setBounds(90,55,300,100);
		jp.add(showName);
		showName.setText("面向Android的变异分析系统");
		showName.setFont(new Font("",1,30));
		showName.setForeground(Color.WHITE);
		showName.setBounds(180,60,600,300);
		jp.add(showAuthor);
		showAuthor.setText("Powered by supermnas1201");
		showAuthor.setForeground(Color.WHITE);
		showAuthor.setBounds(300,240,600,20);
		jp.add(showVersion);
		showVersion.setText("version: 1.0.0.1");
		showVersion.setForeground(Color.WHITE);
		showVersion.setBounds(300,260,600,20);
		
		this.add(jp);
		//this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setBounds(startLocationX, startLocationY, width, height);
		this.setVisible(true);
	}

	private void readLastSave() {
		
////		ReadStatefromXml rsfx=new ReadStatefromXml();
////		String[] args=new String[]{"F:/muandroid3/.state/state.xml"};
//		try {
//			rsfx.run(args);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		};
//		noProject=!Project.getInstance().getReadProject();
	}


	private void openNextFrame() {
		// TODO Auto-generated method stub
//		WelcomeFrame.getInstance();
//		MainFrame.getInstance();
//		if(noProject)
//		{
//			WelcomeFrame.getInstance().setVisible(true);;
//			
//		}
//		else
//		{
//			MainFrame.getInstance().setVisible(true);;
//		}
//		this.setVisible(false);
		
	}

	public static void main(String [] args)
	{
		new LoadingFrame();
	}

}
