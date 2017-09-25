package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import serialzation.DealXmlSax;
import serialzation.ReadMutantfromXml;
import serialzation.SummaryMutantfromXml;
import singleton.FileList;
import singleton.Project;

public class OrginMutantJavaPanel extends JPanel implements ActionListener,
		ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mutantResultPanel = new JPanel();
	JPanel chooseMutantPanel = new JPanel();
	JTextField changeTF = new JTextField("  ");
	JScrollPane originalSP = new JScrollPane();
	JScrollPane mutantSP = new JScrollPane();
	JTextPane originalTP = new JTextPane();
	JTextPane mutantTP = new JTextPane();
	JList<Object> mList = new JList<Object>();
	JScrollPane fileSP = new JScrollPane();
	JList<Object> fList = new JList<Object>();
	JPanel selectOpTypePanel = new JPanel();
	JPanel selectMethodPanel = new JPanel();
	JPanel selectOpPanel = new JPanel();
	JPanel runtestPanel = new JPanel();
	JButton runTest = new JButton("runTest");
	JComboBox<String> opTypeCB = new JComboBox<String>();
	JComboBox<String> methodCB = new JComboBox<String>();
	JComboBox<String> opCB = new JComboBox<String>();

	String filterFile = Project.getInstance().getJavaMutFilterLoc();

	Set<String> nameSet = new HashSet<String>();
	Set<String> opTypeSet = new HashSet<String>();
	Set<String> cmopSet = new HashSet<String>();
	Set<String> tmopSet = new HashSet<String>();
	Set<String> emopSet = new HashSet<String>();
	Set<String> amopSet = new HashSet<String>();
	Set<String> xmopSet = new HashSet<String>();
	Set<String> opSet = new HashSet<String>();

	SimpleAttributeSet red_attr = new SimpleAttributeSet();
	SimpleAttributeSet blue_attr = new SimpleAttributeSet();
	SimpleAttributeSet black_attr = new SimpleAttributeSet();

	TreeMap<String, Integer> name_opMap = new TreeMap<String, Integer>();
	TreeMap<String, Integer> nameMap = new TreeMap<String, Integer>();
	TreeMap<String, Integer> opType_opMap = new TreeMap<String, Integer>();

	OrginMutantJavaPanel() {
		StyleConstants.setForeground(red_attr, Color.red);
		StyleConstants.setForeground(blue_attr, Color.blue);
		StyleConstants.setForeground(black_attr, Color.black);

		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(mutantResultPanel);
		mutantResultPanel.setBorder(new EtchedBorder());
		mutantResultPanel.setLayout(new BoxLayout(mutantResultPanel,
				BoxLayout.LINE_AXIS));
		JPanel fileAndcomboxPanel = new JPanel();
		mutantResultPanel.add(fileAndcomboxPanel);
		fileAndcomboxPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		fileAndcomboxPanel.setLayout(new BoxLayout(fileAndcomboxPanel,
				BoxLayout.LINE_AXIS));
		JPanel filePanel = new JPanel();
		fileAndcomboxPanel.add(filePanel);
		filePanel.setBorder(new TitledBorder("choose the file"));
		filePanel.setPreferredSize(new Dimension(150, 540));

		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.LINE_AXIS));
		filePanel.add(fileSP);
		fileSP.getViewport().add(fList, null);
		fileSP.setPreferredSize(new Dimension(100, 540));
		fList.addListSelectionListener(this);
		// fileSP.setPreferredSize(new Dimension(100,filePanel.getHeight()));
		JPanel comboxPanel = new JPanel();
		comboxPanel.setBorder(new TitledBorder("select mutants"));
		fileAndcomboxPanel.add(comboxPanel);
		comboxPanel.setLayout(new BoxLayout(comboxPanel, BoxLayout.PAGE_AXIS));

		comboxPanel.setPreferredSize(new Dimension(150, 300));
		comboxPanel.add(selectOpTypePanel);
		selectOpTypePanel.setLayout(new FlowLayout());
		JLabel selectOpTypeLabel = new JLabel("   Select the OpType : ");
		selectOpTypePanel.add(selectOpTypeLabel);
		selectOpTypePanel.setPreferredSize(new Dimension(120, 40));
		selectOpTypePanel.add(opTypeCB);
		opTypeCB.setEditable(false);
		opTypeCB.setPreferredSize(new Dimension(100, 25));
		opTypeCB.addActionListener(this);
		// comboxPanel.add(selectMethodPanel);
		// selectMethodPanel.setLayout(new FlowLayout());
		// JLabel selectMethodLabel = new JLabel("   Select the Method : ");
		// selectMethodPanel.add(selectMethodLabel);
		// selectMethodPanel.add(methodCB);
		// methodCB.setEditable(false);
		// methodCB.setPreferredSize(new Dimension(100, 25));
		comboxPanel.add(selectOpPanel);
		selectOpPanel.setLayout(new FlowLayout());
		selectOpPanel.setPreferredSize(new Dimension(120, 60));
		JLabel selectOpLabel = new JLabel("   Select the Op : ");
		selectOpPanel.add(selectOpLabel);
		selectOpPanel.add(opCB);
		opCB.setEditable(false);
		opCB.setPreferredSize(new Dimension(100, 25));

		comboxPanel.add(this.runtestPanel);
		runtestPanel.add(this.runTest);
		runTest.addActionListener(this);
		runTest.setPreferredSize(new Dimension(120, 25));
		runTest.setBackground(Color.CYAN);
		this.add(chooseMutantPanel);
		chooseMutantPanel.setBorder(new EtchedBorder());
		chooseMutantPanel.setLayout(new BoxLayout(chooseMutantPanel,
				BoxLayout.LINE_AXIS));
		JPanel contentPanel = new JPanel();
		contentPanel
				.setLayout(new BoxLayout(contentPanel, BoxLayout.LINE_AXIS));
		// show a list of mutants to be selected for viewing
		JScrollPane leftContentSP = new JScrollPane();
		mList.addListSelectionListener(this);
		leftContentSP.getViewport().add(mList, null);
		leftContentSP.setPreferredSize(new Dimension(100, 540));
		leftContentSP.setBorder(new TitledBorder("mutants list"));
		;
		JPanel rightContentPanel = new JPanel();
		rightContentPanel.setLayout(new BoxLayout(rightContentPanel,
				BoxLayout.PAGE_AXIS));
		rightContentPanel.setBorder(new TitledBorder("code"));
		;
		// show the line mutated
		changeTF.setPreferredSize(new Dimension(550, 40));

		JPanel changeTFPanel = new JPanel();
		changeTFPanel.setBorder(new TitledBorder("Mutants log"));
		changeTFPanel.setLayout(new BoxLayout(changeTFPanel,
				BoxLayout.LINE_AXIS));
		changeTFPanel.add(changeTF);

		rightContentPanel.add(changeTFPanel);

		// show the source code of the original file and the mutant
		originalSP.setPreferredSize(new Dimension(550, 245));
		mutantSP.setPreferredSize(new Dimension(550, 245));
		originalSP.setBorder(new TitledBorder("Original"));
		mutantSP.setBorder(new TitledBorder("Mutant"));
		mutantSP.getViewport().add(mutantTP, null);
		originalSP.getViewport().add(originalTP, null);
		rightContentPanel.add(originalSP);
		rightContentPanel.add(mutantSP);
		contentPanel.add(rightContentPanel);
		chooseMutantPanel.add(contentPanel);
		contentPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		getTable();
	}

	private void getTable() {
		// TODO Auto-generated method stub
		SummaryMutantfromXml gsm = new SummaryMutantfromXml();
		String[] args = new String[] { this.filterFile };
		try {
			gsm.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.nameSet = gsm.nameSet;
		this.opTypeSet = gsm.opTypeSet;
		this.cmopSet = gsm.cmopSet;
		this.tmopSet = gsm.tmopSet;
		this.emopSet = gsm.emopSet;
		this.amopSet = gsm.amopSet;
		this.xmopSet = gsm.xmopSet;
		this.name_opMap = gsm.name_opMap;
		nameMap = gsm.nameMap;
		opType_opMap = gsm.opType_opMap;

		
		
		Vector<String> f = new Vector<String>();
		for (String value : nameSet) {
			System.out.println(value);
			Iterator<?> entries = name_opMap.entrySet().iterator();
			Set<String> oT = new HashSet<String>();
			new HashSet<String>();
			while (entries.hasNext()) {

				Entry<?, ?> entry = (Entry<?, ?>) entries.next();

				String k = (String) entry.getKey();

				String name = k.substring(k.indexOf("(") + 1, k.indexOf(","));
				String opType = k.substring(k.indexOf(",") + 1,
						k.lastIndexOf(","));
				k.substring(k.lastIndexOf(",") + 1, k.indexOf(")"));
				if (name.equals(value)) {
					oT.add(opType);
				}
			}

			if (oT.size() > 1) {
				System.out.println(oT.size());
				f.add(value);
			}
		}
		for (String value : f) {
			System.out.println(" : " + value);
		}
		fList.setListData(f);
		fList.setSelectedIndex(0);
	}

	public void clearSourceContents() {
		try {
			Document ddoc;
			ddoc = originalTP.getDocument();
			ddoc.remove(0, ddoc.getLength());

			ddoc = mutantTP.getDocument();
			ddoc.remove(0, ddoc.getLength());
		} catch (BadLocationException e) {
			System.err.println("error " + e);
		}
		changeTF.setText("");
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.runTest) {
			System.out.println("runTest");
			System.out.println("runTest");
			String tempfilename;
			// String tempfilename2 =
			// "F:/muandroid3/AndroidApp-master/app/temp.java";
			String ofilename;
			List<String> javaList = new ArrayList<String>();
			javaList = FileList.getInstance().getJavaMList();
			String filename = javaList.get((Integer) (this.mList
					.getSelectedIndex()));

			System.out.println("file name ��" + filename);
			System.out.println(opTypeCB.getSelectedItem().toString());
			tempfilename = filename.replace("\\", "/");
			if (filename.indexOf(opTypeCB.getSelectedItem().toString()) >= 0)
				tempfilename = tempfilename
						.substring(0, filename.indexOf(opTypeCB
								.getSelectedItem().toString()) - 1);

			tempfilename = tempfilename + ".java";
			System.out.println(tempfilename);
			ofilename = tempfilename.replace("/mutant/", "/src/");
			System.out.println(ofilename);
			// / ReplaceOrginalWithMutant.main(args2);
			try {
				System.out.println("run test java Op>>>>>>>");
				// Process p = null;
				// String line = null;
				// BufferedReader stdout = null;
				// BufferedWriter bw = null;
				// bw = new BufferedWriter(new FileWriter(ofilename + ".log"));
				// list the files and directorys under C:\
				// p = Runtime
				// .getRuntime()
				// .exec("F:/muandroid3/AndroidApp-master/gradlew.bat connectAndroidTest",
				// null,
				// new File("F:/muandroid3/AndroidApp-master"));
				// stdout = new BufferedReader(new InputStreamReader(
				// p.getInputStream()));
				// while ((line = stdout.readLine()) != null) {
				// System.out.println(line);
				// bw.write(line);
				// bw.newLine();
				// ;
				// bw.flush();
				// // Thread.sleep( 10 );
				// }
				// bw.flush();

				// stdout.close();
				// p.destroy();

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		// if(opTypeCB.getSelectedItem()!=null)
		// System.out.println(">>"+opTypeCB.getSelectedItem());
		//
		if (e.getSource().equals(opTypeCB)) {
			Iterator<?> entries = name_opMap.entrySet().iterator();
			new HashSet<String>();
			Set<String> o = new HashSet<String>();
			while (entries.hasNext()) {

				Entry<?, ?> entry = (Entry<?, ?>) entries.next();

				String k = (String) entry.getKey();

				String name = k.substring(k.indexOf("(") + 1, k.indexOf(","));
				String opType = k.substring(k.indexOf(",") + 1,
						k.lastIndexOf(","));
				String op = k.substring(k.lastIndexOf(",") + 1, k.indexOf(")"));
				if (name.equals(fList.getSelectedValue())) {
					if (opType.equals(opTypeCB.getSelectedItem()))
						o.add(op);
				}
			}
			// System.out.println("1");
			opCB.removeActionListener(this);
			opCB.removeAllItems();
			// System.out.println("2");
			for (String value : o) {
				// System.out.println(value);

				opCB.addItem(value);
			}
			opCB.addActionListener(this);
			opCB.setSelectedIndex(0);
		}
		if (e.getSource().equals(opCB)) {
			String s = fList.getSelectedValue().toString()
					+ opTypeCB.getSelectedItem() + opCB.getSelectedItem();
			System.out.println("flist +" + s);

			String[] args = new String[] { this.filterFile,
					fList.getSelectedValue().toString(), "all",
					opTypeCB.getSelectedItem().toString(),
					opCB.getSelectedItem().toString() };

			DealXmlSax rmx = new ReadMutantfromXml();
			try {
				rmx.run(args);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			FileList.getInstance().readMFlist();
			List<String> javaList = new ArrayList<String>();
			javaList = FileList.getInstance().getMFlist();
			List<String> mjList = new ArrayList<String>();
			for (int i = 0; i < javaList.size(); i++) {
				String temps = javaList.get(i);

				System.out.println(temps);
				temps = temps.substring(0, temps.lastIndexOf("\\"));
				temps = temps.substring(temps.lastIndexOf("\\") + 1);
				mjList.add(temps);
			}
			mList.removeListSelectionListener(this);
			mList.removeAll();
			mList.setListData((Object[]) mjList.toArray());
			mList.addListSelectionListener(this);
			;
			mList.setSelectedIndex(0);
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource().equals(fList)) {
			System.out.println(((JList<?>) e.getSource()).getSelectedValue());
			Iterator<?> entries = name_opMap.entrySet().iterator();

			Set<String> oT = new HashSet<String>();
			new HashSet<String>();
			while (entries.hasNext()) {

				Entry<?, ?> entry = (Entry<?, ?>) entries.next();

				String k = (String) entry.getKey();

				String name = k.substring(k.indexOf("(") + 1, k.indexOf(","));
				String opType = k.substring(k.indexOf(",") + 1,
						k.lastIndexOf(","));
				k.substring(k.lastIndexOf(",") + 1, k.indexOf(")"));
				if (name.equals(((JList<?>) e.getSource()).getSelectedValue())) {
					oT.add(opType);

				}
			}
			// System.out.println("1");
			opTypeCB.removeActionListener(this);
			opTypeCB.removeAllItems();
			// System.out.println("2");
			for (String value : oT) {
				// System.out.println(value);
				if (!value.equals("original"))
					opTypeCB.addItem(value);
			}
			opTypeCB.addActionListener(this);
			opTypeCB.setSelectedIndex(0);
		}

		if (e.getSource().equals(mList)) {
			clearSourceContents();
			List<String> javaList = new ArrayList<String>();
			javaList = FileList.getInstance().getMFlist();
			String filename = javaList.get((Integer) ((JList<?>) e.getSource())
					.getSelectedIndex());

			System.out.println("file name ��" + filename);
			System.out.println(opTypeCB.getSelectedItem().toString());
			String tempfilename = filename.replace("\\", "/");
			if (filename.indexOf(opTypeCB.getSelectedItem().toString()) >= 0)
				tempfilename = tempfilename
						.substring(0, filename.indexOf(opTypeCB
								.getSelectedItem().toString()));

			String logname = tempfilename + opTypeCB.getSelectedItem()
					+ "/mutation.log";
			String ofilename = tempfilename + "original/"
					+ fList.getSelectedValue();

			String mutantlog = "";
			String mutant_name = mList.getSelectedValue().toString();
			mutantlog = getMutant(logname, mutant_name);
			showOriginal(ofilename);
			
			showMutant(filename, mutantlog);
			System.out.println(((JList<?>) e.getSource()).getSelectedValue());
		}
	}

	public void showOriginal(String fileName) {
		try {
			String strLine;
			File myFile = new File(fileName);
			String blank_str;
			LineNumberReader lReader = new LineNumberReader(
					new InputStreamReader(new FileInputStream(myFile), "UTF-8"));

			Document ddoc = originalTP.getDocument();

			while ((strLine = lReader.readLine()) != null) {
				blank_str = "";
				int del = (new Integer(lReader.getLineNumber())).toString()
						.length();
				for (int k = 0; k < 5 - del; k++) {
					blank_str = blank_str + " ";
				}
				ddoc.insertString(ddoc.getLength(), lReader.getLineNumber()
						+ blank_str, blue_attr);
				ddoc.insertString(ddoc.getLength(), strLine + "\n", black_attr);
			}
			lReader.close();

		} catch (Exception e) {
			System.err.println(" [error] " + e);
		}
	}

	public void showMutant(String fileName) {
		try {
			String strLine;
			File myFile = new File(fileName);
			String blank_str;
			LineNumberReader lReader = new LineNumberReader(
					new InputStreamReader(new FileInputStream(myFile), "UTF-8"));

			Document ddoc = originalTP.getDocument();

			while ((strLine = lReader.readLine()) != null) {
				blank_str = "";
				int del = (new Integer(lReader.getLineNumber())).toString()
						.length();
				for (int k = 0; k < 5 - del; k++) {
					blank_str = blank_str + " ";
				}
				ddoc.insertString(ddoc.getLength(), lReader.getLineNumber()
						+ blank_str, blue_attr);
				ddoc.insertString(ddoc.getLength(), strLine + "\n", black_attr);
			}
			lReader.close();

		} catch (Exception e) {
			System.err.println(" [error] " + e);
		}
	}

	/**
	 * Show source code of the selected mutant. Changed part is colored in red
	 * 
	 * @param dir_name
	 *            the name of class (including package name)
	 * @param changed_line
	 *            line number of mutated code against original program
	 */
	public void showMutant(String filename, String mutant_log) {
		System.out.println("MutantsViewerPanel.showMutant (with mutant_log)");
		try {
			System.out.println(mutant_log);
			int changed_line = getMutatedLineNum(mutant_log);
			System.out.println(changed_line);
			String changed_content = getMutatedContent(mutant_log);
			System.out.println(changed_content);
			changeTF.setText(" (line " + changed_line + ") " + changed_content);
			changeTF.repaint();

			Document ddoc = mutantTP.getDocument();
			ddoc.remove(0, ddoc.getLength());

			int line_num = 0;
			int caret_pos = 0;
			String strLine;
			File myFile = new File(filename);
			// System.out.println("showMutant: myFile =" +
			// myFile.getAbsolutePath());

			String blank_str;
			LineNumberReader lReader = new LineNumberReader(new FileReader(
					myFile));

			while ((strLine = lReader.readLine()) != null) {
				blank_str = "";
				line_num = lReader.getLineNumber();
				int del = (new Integer(line_num)).toString().length();
				for (int k = 0; k < 5 - del; k++) {
					blank_str = blank_str + " ";
				}

				ddoc.insertString(ddoc.getLength(), line_num + blank_str,
						blue_attr);
				if (line_num == changed_line) {
					caret_pos = ddoc.getLength();
					ddoc.insertString(ddoc.getLength(), strLine + "\n",
							red_attr);
				} else {
					ddoc.insertString(ddoc.getLength(), strLine + "\n",
							black_attr);
				}
			}
			mutantTP.setCaretPosition(caret_pos);
			originalTP.setCaretPosition(caret_pos);
			originalTP.repaint();
			lReader.close();

		} catch (Exception e) {
			System.err.println(" [error] " + e);
		}
	}

	/**
	 * Return log for the mutant <i> mutant_name </i> from the log file
	 * "mutation_log" <br>
	 * 
	 * @return log for the mutant (if no log found, NULL is returned.)
	 */

	String getMutant(String logname, String mutant_name) {
		try {
			File myFile = new File(logname);
			String strLine;
			@SuppressWarnings("resource")
			LineNumberReader lReader = new LineNumberReader(new FileReader(
					myFile));
			while ((strLine = lReader.readLine()) != null) {
				// System.out.println(">>" +strLine);
				if (strLine.indexOf(mutant_name) == 0) {
					return strLine;
				}
			}
			lReader.close();
		} catch (FileNotFoundException e1) {
			System.err.println("e1:" + e1);
		} catch (IOException e2) {
			System.err.println(e2);
		}

		return null;
	}

	int getMutatedLineNum(String str) {
		// MutationSystem.LOG_IDENTIFIER = ":"
		int start_index = str.indexOf(":");
		String log_str = str;
		log_str = log_str.substring(start_index + 1);
		int end_index = start_index + 1 + log_str.indexOf(":");
		String temp = str.substring(start_index + 1, end_index);

		temp = temp.replace(" ", "");
		System.out.println(temp);
		return ((new Integer(temp)).intValue());
	}

	String getMutatedContent(String str) {
		// MutationSystem.LOG_IDENTIFIER = ":"
		int start_index = str.indexOf(":");
		String log_str = str;
		log_str = log_str.substring(start_index + 1);
		int end_index = start_index + 1 + log_str.indexOf(":");
		System.out.println(str.substring(end_index + 1));
		return str.substring(end_index + 1);

	}

}
