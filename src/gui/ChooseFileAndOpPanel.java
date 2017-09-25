package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import singleton.FileList;
import singleton.Op;
import singleton.Project;
import util.GetFiles;
import util.GetInheritanceRelation;

public class ChooseFileAndOpPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel filePanel = new JPanel();
	JPanel xmlFilePanel = new JPanel();
	JPanel javaFilePanel = new JPanel();
	JTable javaFileTable = new JTable();
	JScrollPane javaFileSP = new JScrollPane(javaFileTable);
	JTable xmlFileTable = new JTable();
	JScrollPane xmlFileSP = new JScrollPane(xmlFileTable);
	JTable manifestFileTable = new JTable();
	JScrollPane manifestFileSP = new JScrollPane(manifestFileTable);
	JPanel OpPanel = new JPanel();

	JTable cmTable = new JTable();
	JTable tmTable = new JTable();
	JTable emTable = new JTable();
	JTable xmTable = new JTable();
	JTable amTable = new JTable();
	JScrollPane cmOpSP = new JScrollPane();
	JScrollPane tmOpSP = new JScrollPane();
	JScrollPane emOpSP = new JScrollPane();
	JScrollPane xmOpSP = new JScrollPane();
	JScrollPane amOpSP = new JScrollPane();
	JPanel tmPanel = new JPanel();
	JPanel cmPanel = new JPanel();
	JPanel emPanel = new JPanel();
	JPanel xmPanel = new JPanel();
	JPanel amPanel = new JPanel();

	JLabel showChooseFile = new JLabel("Choose Mutant File");

	JPanel functionPanel = new JPanel();
	JPanel showPanel = new JPanel();

	JButton xmlAllBt = new JButton("ALL");
	JButton xmlNoneBt = new JButton("NONE");
	JButton javaAllBt = new JButton("ALL");
	JButton javaNoneBt = new JButton("NONE");
	JButton cmAllBt = new JButton("ALL");
	JButton cmNoneBt = new JButton("NONE");
	JButton tmAllBt = new JButton("ALL");
	JButton tmNoneBt = new JButton("NONE");
	JButton emAllBt = new JButton("ALL");
	JButton emNoneBt = new JButton("NONE");
	JButton amAllBt = new JButton("ALL");
	JButton amNoneBt = new JButton("NONE");
	JButton xmAllBt = new JButton("ALL");
	JButton xmNoneBt = new JButton("NONE");
	JButton allBt = new JButton("ALL");
	JButton noneBt = new JButton("NONE");

	JButton getMutant = new JButton("generate mutants");

	JLabel[] jlArray = new JLabel[] {
			new JLabel(
					"<html><font size=3>[1] Select files to test</font></html>"),
			new JLabel(
					"<html><font size=3>[2] Select mutation operators to apply</font></html>"),
			new JLabel(
					"<html><font size=3>[3] Push \"generate mutants\" button</font></html>"),
			new JLabel(
					"<html><font size=3>[4] Wait with endurance. ^^;</font></html>"), };

	String projectName = "";

	ChooseFileAndOpPanel(String projectName) {
		this.projectName = projectName;
		init();
	}

	ChooseFileAndOpPanel() {
		init();
	}

	void init() {
		GetFiles.getSrcFiles();
		GetFiles.getJarClassFiles();

		GetInheritanceRelation rir = new GetInheritanceRelation();
		try {
			rir.run(new String[] { Project.getInstance().getSelectProject() });
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(filePanel);
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.PAGE_AXIS));
		JTabbedPane javaTbedPane = new JTabbedPane();
		javaTbedPane.setPreferredSize(new Dimension(400, 600));
		javaTbedPane.setTabPlacement(JTabbedPane.TOP);
		filePanel.add(javaTbedPane);
		javaTbedPane.addTab("XMl Files", xmlFilePanel);
		xmlFilePanel.setBorder(new TitledBorder(
				"All Xml Files which can be mutanted"));
		// filePanel.add(xmlFilePanel);
		xmlFilePanel
				.setLayout(new BoxLayout(xmlFilePanel, BoxLayout.PAGE_AXIS));
		FILETableModel fTableModel0 = new FILETableModel(
				getNewTragetFiles("manifest"), "manifest");
		manifestFileTable = new JTable(fTableModel0);
		initFileColumnSizes(manifestFileTable, fTableModel0);
		manifestFileSP.getViewport().add(manifestFileTable, null);
		manifestFileSP.setPreferredSize(new Dimension(500, 70));
		xmlFilePanel.add(manifestFileSP);

		FILETableModel fTableModel2 = new FILETableModel(
				getNewTragetFiles("xml"), "xml");
		if (fTableModel2 != null) {
			xmlFileTable = new JTable(fTableModel2);
		}

		initFileColumnSizes(xmlFileTable, fTableModel2);
		xmlFileSP.getViewport().add(xmlFileTable, null);
		xmlFileSP.setPreferredSize(new Dimension(500, 500));
		xmlFilePanel.add(xmlFileSP);
		JPanel xbtPanel = new JPanel();

		xbtPanel.add(xmlAllBt);
		xbtPanel.add(xmlNoneBt);
		xmlFilePanel.add(xbtPanel);

		javaFilePanel.setBorder(new TitledBorder(
				"All Java Files which can be mutanted"));
		// filePanel.add(javaFilePanel);
		javaTbedPane.addTab("Java Files", javaFilePanel);
		javaFilePanel.setLayout(new BoxLayout(javaFilePanel,
				BoxLayout.PAGE_AXIS));

		FILETableModel fTableModel = new FILETableModel(
				getNewTragetFiles("java"), "java");
		if (fTableModel != null) {
			javaFileTable = new JTable(fTableModel);
		}

		initFileColumnSizes(javaFileTable, fTableModel);
		javaFileSP.getViewport().add(javaFileTable, null);
		javaFileSP.setPreferredSize(new Dimension(500, 600));
		javaFilePanel.add(javaFileSP);

		JPanel jbtPanel = new JPanel();

		javaAllBt.addActionListener(this);
		javaNoneBt.addActionListener(this);

		jbtPanel.add(javaAllBt);
		jbtPanel.add(javaNoneBt);
		javaFilePanel.add(jbtPanel);

		this.add(OpPanel);

		OpPanel.setLayout(new BoxLayout(OpPanel, BoxLayout.LINE_AXIS));

		JTabbedPane opTbedPane = new JTabbedPane();
		opTbedPane.setPreferredSize(new Dimension(300, 600));
		opTbedPane.setMaximumSize(new Dimension(300, 900));
		opTbedPane.setTabPlacement(JTabbedPane.TOP);
		// opTbedPane.setTabLayoutPolicy(JTabbedPane.VERTICAL);
		OpPanel.add(opTbedPane);
		opTbedPane.addTab("CM Op", cmPanel);
		// OpPanel.add(cmPanel);
		cmPanel.setLayout(new BoxLayout(cmPanel, BoxLayout.PAGE_AXIS));
		cmPanel.add(cmOpSP);
		cmPanel.setBorder(new TitledBorder("Class Mutant Op"));
		cmPanel.setPreferredSize(new Dimension(120, 550));
		cmPanel.setMaximumSize(new Dimension(120, 750));
		CMOTableModel cmTableModel = new CMOTableModel();
		cmTable = new JTable(cmTableModel);
		initColumnSizes(cmTable, cmTableModel);
		cmOpSP.getViewport().add(cmTable, null);
		cmOpSP.setPreferredSize(new Dimension(115, 400));
		cmOpSP.setMaximumSize(new Dimension(115, 400));
		JPanel cmbtPanel = new JPanel();

		cmbtPanel.add(cmAllBt);
		cmbtPanel.add(cmNoneBt);
		cmPanel.add(cmbtPanel);
		// OpPanel.add(tmPanel);
		opTbedPane.addTab("TM Op", tmPanel);
		tmPanel.setLayout(new BoxLayout(tmPanel, BoxLayout.PAGE_AXIS));
		tmPanel.add(tmOpSP);
		tmPanel.setBorder(new TitledBorder("Tradtional Mutant Op"));
		tmPanel.setPreferredSize(new Dimension(120, 550));
		tmPanel.setMaximumSize(new Dimension(120, 750));
		TMOTableModel tmTableModel = new TMOTableModel();
		tmTable = new JTable(tmTableModel);
		initColumnSizes(tmTable, tmTableModel);
		tmOpSP.getViewport().add(tmTable, null);
		tmOpSP.setPreferredSize(new Dimension(115, 400));
		tmOpSP.setMaximumSize(new Dimension(115, 400));

		JPanel tmbtPanel = new JPanel();

		tmbtPanel.add(tmAllBt);
		tmbtPanel.add(tmNoneBt);
		tmPanel.add(tmbtPanel);

		opTbedPane.addTab("EM Op", emPanel);
		// OpPanel.add(emPanel);
		emPanel.setLayout(new BoxLayout(emPanel, BoxLayout.PAGE_AXIS));
		emPanel.add(emOpSP);
		emPanel.setBorder(new TitledBorder("Exception Mutant Op"));
		emPanel.setPreferredSize(new Dimension(120, 550));
		emPanel.setMaximumSize(new Dimension(120, 750));
		EMOTableModel emTableModel = new EMOTableModel();
		emTable = new JTable(emTableModel);
		initColumnSizes(emTable, emTableModel);
		emOpSP.getViewport().add(emTable, null);
		emOpSP.setPreferredSize(new Dimension(115, 400));
		emOpSP.setMaximumSize(new Dimension(115, 400));

		JPanel embtPanel = new JPanel();

		embtPanel.add(emAllBt);
		embtPanel.add(emNoneBt);
		emPanel.add(embtPanel);

		opTbedPane.addTab("AM Op", amPanel);
		// OpPanel.add(amPanel);
		amPanel.setLayout(new BoxLayout(amPanel, BoxLayout.PAGE_AXIS));
		amPanel.add(amOpSP);
		amPanel.setBorder(new TitledBorder("Android Mutant Op"));
		amPanel.setPreferredSize(new Dimension(120, 550));
		amPanel.setMaximumSize(new Dimension(120, 750));
		AMOTableModel amTableModel = new AMOTableModel();
		amTable = new JTable(amTableModel);
		initColumnSizes(amTable, amTableModel);
		amOpSP.getViewport().add(amTable, null);
		amOpSP.setPreferredSize(new Dimension(115, 400));
		amOpSP.setMaximumSize(new Dimension(115, 400));

		JPanel ambtPanel = new JPanel();

		ambtPanel.add(amAllBt);
		ambtPanel.add(amNoneBt);
		amPanel.add(ambtPanel);
		opTbedPane.addTab("XM Op", xmPanel);
		// OpPanel.add( xmPanel);
		xmPanel.setLayout(new BoxLayout(xmPanel, BoxLayout.PAGE_AXIS));
		xmPanel.add(xmOpSP);
		xmPanel.setBorder(new TitledBorder("Xml Mutant Op"));
		xmPanel.setPreferredSize(new Dimension(120, 550));
		xmPanel.setMaximumSize(new Dimension(120, 750));
		XMOTableModel xmTableModel = new XMOTableModel();
		xmTable = new JTable(xmTableModel);
		initColumnSizes(xmTable, xmTableModel);
		xmOpSP.getViewport().add(xmTable, null);
		xmOpSP.setPreferredSize(new Dimension(115, 400));
		xmOpSP.setMaximumSize(new Dimension(115, 400));
		JPanel xmbtPanel = new JPanel();

		xmbtPanel.add(xmAllBt);
		xmbtPanel.add(xmNoneBt);
		xmPanel.add(xmbtPanel);
		JPanel runPanel = new JPanel();

		OpPanel.add(runPanel);
		runPanel.setLayout(new BoxLayout(runPanel, BoxLayout.PAGE_AXIS));
		JPanel usagePanel = new JPanel();
		usagePanel.setBorder(new TitledBorder(
				"The thing needed todo in this Panel"));
		JPanel tempP = new JPanel();
		JLabel temp = new JLabel("   Usage : ");
		temp.setForeground(Color.gray);
		tempP.add(temp);
		tempP.setPreferredSize(new Dimension(70, 70));
		JPanel usgeContentP = new JPanel();
		usgeContentP
				.setLayout(new BoxLayout(usgeContentP, BoxLayout.PAGE_AXIS));
		usgeContentP.add(jlArray[0]);
		usgeContentP.add(jlArray[1]);
		usgeContentP.add(jlArray[2]);
		usgeContentP.add(jlArray[3]);
		usagePanel.add(tempP);
		usagePanel.add(usgeContentP);
		usagePanel.setPreferredSize(new Dimension(115, 200));

		JPanel getMutantPanel = new JPanel();

		JPanel anPanel = new JPanel();
		anPanel.setBorder(new TitledBorder(
				"Select ALL or None of all files and Mutant Op"));

		anPanel.add(allBt);
		anPanel.add(noneBt);
		getMutant.setBackground(Color.CYAN);
		getMutantPanel.add(getMutant);
		getMutantPanel.setBorder(new TitledBorder(
				"Generate mutants of choosed files in the choosed Op"));
		runPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		runPanel.add(usagePanel);
		// runPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		runPanel.add(anPanel);
		runPanel.add(getMutantPanel);
		runPanel.add(Box.createRigidArea(new Dimension(0, 70)));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		addButtonListener();
		allBt.doClick();

		cmNoneBt.doClick();
		tmNoneBt.doClick();
		emNoneBt.doClick();
	}

	private void addButtonListener() {
		// TODO Auto-generated method stub
		xmlAllBt.addActionListener(this);
		xmlNoneBt.addActionListener(this);
		javaAllBt.addActionListener(this);
		javaNoneBt.addActionListener(this);
		cmAllBt.addActionListener(this);
		cmNoneBt.addActionListener(this);
		tmAllBt.addActionListener(this);
		tmNoneBt.addActionListener(this);
		emAllBt.addActionListener(this);
		emNoneBt.addActionListener(this);
		amAllBt.addActionListener(this);
		amNoneBt.addActionListener(this);
		xmAllBt.addActionListener(this);
		xmNoneBt.addActionListener(this);
		allBt.addActionListener(this);
		noneBt.addActionListener(this);
		getMutant.addActionListener(this);
	}

	protected void initColumnSizes(JTable table, AbstractTableModel model) {
		initTripleColumnWidth(table, model, 30, 90, 80);
	}

	protected void initFileColumnSizes(JTable table, AbstractTableModel model) {
		initTripleColumnWidth(table, model, 30, 700, 80);
	}

	protected void initTripleColumnWidth(JTable table,
			AbstractTableModel model, int w1, int w2, int w3) {
		TableColumn column = null;

		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			switch (i) {
			case 0:
				column.setMaxWidth(w1);
				break;
			case 1:
				column.setMaxWidth(w2);
				break;
			case 2:
				column.setMaxWidth(w3);
				break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == xmlAllBt) {
			// System.out.println("xmlAll");
			FILETableModel table = (FILETableModel) xmlFileTable.getModel();
			table.setAllSelectValue(true);
			xmlFileTable.setModel(table);
			xmlFileTable.repaint();
			FILETableModel table2 = (FILETableModel) manifestFileTable
					.getModel();
			table2.setAllSelectValue(true);
			manifestFileTable.setModel(table2);
			manifestFileTable.repaint();
		}
		if (e.getSource() == xmlNoneBt) {
			// System.out.println("xmlNone");
			FILETableModel table = (FILETableModel) xmlFileTable.getModel();
			table.setAllSelectValue(false);
			xmlFileTable.setModel(table);
			xmlFileTable.repaint();
			FILETableModel table2 = (FILETableModel) manifestFileTable
					.getModel();
			table2.setAllSelectValue(false);
			manifestFileTable.setModel(table2);
			manifestFileTable.repaint();
		}
		if (e.getSource() == javaAllBt) {
			// System.out.println("javaAll");
			FILETableModel table = (FILETableModel) javaFileTable.getModel();
			table.setAllSelectValue(true);
			javaFileTable.setModel(table);
			javaFileTable.repaint();
		}
		if (e.getSource() == javaNoneBt) {
			// System.out.println("javaNone");
			FILETableModel table = (FILETableModel) javaFileTable.getModel();
			table.setAllSelectValue(false);
			javaFileTable.setModel(table);
			javaFileTable.repaint();
		}
		if (e.getSource() == cmAllBt) {
			CMOTableModel cmt = (CMOTableModel) cmTable.getModel();
			cmt.setAllSelectValue(true);
			cmTable.setModel(cmt);
			cmTable.repaint();
			// System.out.println("cmAll");
		}
		if (e.getSource() == cmNoneBt) {
			CMOTableModel cmt = (CMOTableModel) cmTable.getModel();
			cmt.setAllSelectValue(false);
			cmTable.setModel(cmt);
			cmTable.repaint();
			// System.out.println("cmNone");
		}
		if (e.getSource() == tmAllBt) {
			TMOTableModel tmt = (TMOTableModel) tmTable.getModel();
			tmt.setAllSelectValue(true);
			tmTable.setModel(tmt);
			tmTable.repaint();
			// sSystem.out.println("tmAll");
		}
		if (e.getSource() == tmNoneBt) {
			TMOTableModel tmt = (TMOTableModel) tmTable.getModel();
			tmt.setAllSelectValue(false);
			tmTable.setModel(tmt);
			tmTable.repaint();
			// System.out.println("tmNone");
		}
		if (e.getSource() == emAllBt) {
			EMOTableModel emt = (EMOTableModel) emTable.getModel();
			emt.setAllSelectValue(true);
			emTable.setModel(emt);
			emTable.repaint();
			// System.out.println("emAll");
		}
		if (e.getSource() == emNoneBt) {
			EMOTableModel emt = (EMOTableModel) emTable.getModel();
			emt.setAllSelectValue(false);
			emTable.setModel(emt);
			emTable.repaint();
			// System.out.println("emNone");
		}
		if (e.getSource() == amAllBt) {
			AMOTableModel amt = (AMOTableModel) amTable.getModel();
			amt.setAllSelectValue(true);
			amTable.setModel(amt);
			amTable.repaint();
			// System.out.println("amAll");
		}
		if (e.getSource() == amNoneBt) {
			AMOTableModel amt = (AMOTableModel) amTable.getModel();
			amt.setAllSelectValue(false);
			amTable.setModel(amt);
			amTable.repaint();
			// System.out.println("amNone");
		}
		if (e.getSource() == xmAllBt) {
			XMOTableModel xmt = (XMOTableModel) xmTable.getModel();
			xmt.setAllSelectValue(true);
			xmTable.setModel(xmt);
			xmTable.repaint();
			// s System.out.println("xmAll");
		}
		if (e.getSource() == xmNoneBt) {
			XMOTableModel xmt = (XMOTableModel) xmTable.getModel();
			xmt.setAllSelectValue(false);
			xmTable.setModel(xmt);
			xmTable.repaint();
			// System.out.println("xmNone");
		}
		if (e.getSource() == allBt) {
			xmlAllBt.doClick();
			javaAllBt.doClick();
			cmAllBt.doClick();
			tmAllBt.doClick();
			emAllBt.doClick();
			amAllBt.doClick();
			xmAllBt.doClick();
			// System.out.println("All");
		}
		if (e.getSource() == noneBt) {
			xmlNoneBt.doClick();
			javaNoneBt.doClick();
			cmNoneBt.doClick();
			tmNoneBt.doClick();
			emNoneBt.doClick();
			amNoneBt.doClick();
			xmNoneBt.doClick();
			// System.out.println("None");
		}
		if (e.getSource() == this.getMutant) {
			System.out.println("getMutant");
			String[] nu = new String[] {};
			List<String> nullList = Arrays.asList(nu);
			Op.getInstance().setClassOp(nullList);
			Op.getInstance().setTraditionalOp(nullList);
			Op.getInstance().setExceptionOp(nullList);
			Op.getInstance().setAndroidOp(nullList);
			Op.getInstance().setXmlOp(nullList);
			CMOTableModel cmt = (CMOTableModel) cmTable.getModel();
			String[] cmo = cmt.getSelectedOprators();
			if (cmo != null) {
				List<String> cmoList = (Arrays.asList(cmo));
				Op.getInstance().setClassOp(cmoList);
			} else {

				Op.getInstance().setClassOp(nullList);
			}

			TMOTableModel tmt = (TMOTableModel) tmTable.getModel();
			String[] tmo = tmt.getSelectedOprators();
			if (tmo != null) {
				List<String> tmoList = (Arrays.asList(tmo));
				Op.getInstance().setTraditionalOp(tmoList);
			} else {
				Op.getInstance().setTraditionalOp(nullList);
			}

			EMOTableModel emt = (EMOTableModel) emTable.getModel();
			String[] emo = emt.getSelectedOprators();
			if (emo != null) {
				List<String> emoList = (Arrays.asList(emo));
				Op.getInstance().setExceptionOp(emoList);
			} else {
				Op.getInstance().setExceptionOp(nullList);
			}

			AMOTableModel amt = (AMOTableModel) amTable.getModel();
			String[] amo = amt.getSelectedOprators();
			if (amo != null) {
				List<String> amoList = (Arrays.asList(amo));
				Op.getInstance().setAndroidOp(amoList);
			} else {
				Op.getInstance().setAndroidOp(nullList);
			}

			XMOTableModel xmt = (XMOTableModel) xmTable.getModel();
			String[] xmo = xmt.getSelectedOprators();
			if (xmo != null) {
				List<String> xmoList = (Arrays.asList(xmo));
				Op.getInstance().setXmlOp(xmoList);
			} else {
				Op.getInstance().setXmlOp(nullList);
			}

			System.out.println("  ");
			Op.getInstance().readClassOp();
			System.out.println("  ");
			Op.getInstance().readTradtionalOp();
			System.out.println("  ");
			Op.getInstance().readExceptionOp();
			System.out.println("  ");
			Op.getInstance().readAndroidOp();
			System.out.println("  ");
			Op.getInstance().readXmlOp();

			FILETableModel fTableModel = (FILETableModel) javaFileTable
					.getModel();
			if (!(fTableModel.getSelectedFiles() == null)) {
				String[] javafile_list = fTableModel.getSelectedFiles();
				for (int i = 0; i < javafile_list.length; i++) {
					FileList.getInstance()
							.getJavaSList()
							.add(Project.getInstance().getJavaSrcPath() + "/"
									+ javafile_list[i]);
					System.out.println(Project.getInstance().getJavaSrcPath()
							+ "/" + javafile_list[i]);
				}
			}

			FILETableModel fTableModel2 = (FILETableModel) xmlFileTable
					.getModel();
			if (!(fTableModel2.getSelectedFiles() == null)) {
				String[] xmlfile_list = fTableModel2.getSelectedFiles();
				for (int i = 0; i < xmlfile_list.length; i++) {
					FileList.getInstance()
							.getXmlSList()
							.add(Project.getInstance().getXmlSrcPath() + "/"
									+ xmlfile_list[i]);
					System.out.println(Project.getInstance().getXmlSrcPath()
							+ "/" + xmlfile_list[i]);
				}
			}
			FILETableModel fTableModel3 = (FILETableModel) manifestFileTable
					.getModel();
			if (!(fTableModel3.getSelectedFiles() == null)) {
				String[] manifestfile_list = fTableModel3.getSelectedFiles();
				for (int i = 0; i < manifestfile_list.length; i++) {
					FileList.getInstance()
							.getXmlSList()
							.add(Project.getInstance().getmanifestSrcPath()
									+ "/" + manifestfile_list[i]);
					System.out.println(Project.getInstance()
							.getmanifestSrcPath() + "/" + manifestfile_list[i]);
				}
			}

			ProgressJFrame pjf = ProgressJFrame.getInstance();
			;
			pjf.setVisible(true);
			pjf.stop();
			pjf.strat();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Vector getNewTragetFiles(String s) {
		Vector targetFiles = new Vector();
		if (s == "java")
			getJavacArgForDir("", "", targetFiles);
		else if (s == "xml")
			getXmlArgForDir("", "", targetFiles);
		else if (s == "manifest")
			getManifestArgForDir("", "", targetFiles);
		return targetFiles;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String getManifestArgForDir(String dir, String str,
			Vector targetFiles) {
		String result = str;
		if (new File(Project.getInstance().getmanifestSrcPath()
				+ "/AndroidManifest.xml").exists())
			targetFiles.add("AndroidManifest.xml");
		else
			targetFiles.add("null");
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String getXmlArgForDir(String dir, String str,
			Vector targetFiles) {
		// TODO Auto-generated method stub
		String result = str;
		String temp = "";
		List<String> fl = FileList.getInstance().getXmlList();
		for (int i = 0; i < fl.size(); i++) {
			temp = fl.get(i);
			temp = temp.substring(Project.getInstance().getXmlSrcPath()
					.length() + 1);
			temp = temp.replace('\\', '/');
			targetFiles.add(temp);
		}
		if (fl.size() == 0)
			targetFiles.add("null");
		return result;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static String getJavacArgForDir(String dir, String str,
			Vector targetFiles) {
		String result = str;
		String temp = "";
		List<String> fl = FileList.getInstance().getJavaList();
		for (int i = 0; i < fl.size(); i++) {
			temp = fl.get(i);
			temp = temp.substring(Project.getInstance().getJavaSrcPath()
					.length() + 1);
			temp = temp.replace('\\', '/');
			targetFiles.add(temp);
		}
		if (fl.size() == 0)
			targetFiles.add("null");
		return result;
	}
}
