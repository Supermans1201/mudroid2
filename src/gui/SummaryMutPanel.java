package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.SimpleAttributeSet;

import serialzation.SummaryMutantfromXml;
import singleton.Project;
import util.GetFiles;

public class SummaryMutPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel tablePanel = new JPanel();
	JPanel chartPanel = new JPanel();

	SimpleAttributeSet red_attr = new SimpleAttributeSet();
	SimpleAttributeSet blue_attr = new SimpleAttributeSet();
	SimpleAttributeSet black_attr = new SimpleAttributeSet();
	JTable opTable;
	JScrollPane jsp;
	Set<String> nameSet = new HashSet<String>();
	Set<String> name2Set = new HashSet<String>();
	Set<String> opTypeSet = new HashSet<String>();
	Set<String> cmopSet = new HashSet<String>();
	Set<String> tmopSet = new HashSet<String>();
	Set<String> emopSet = new HashSet<String>();
	Set<String> amopSet = new HashSet<String>();
	Set<String> xmopSet = new HashSet<String>();
	// Set<String> opSet=new HashSet<String>();

	List<String> h = new ArrayList<String>();

	TreeMap<String, Integer> name_opMap = new TreeMap<String, Integer>();
	TreeMap<String, Integer> nameMap = new TreeMap<String, Integer>();
	TreeMap<String, Integer> opType_opMap = new TreeMap<String, Integer>();

	SummaryMutPanel() {
		getTable(0);
		init();

	}

	SummaryMutPanel(int i) {
		getTable(i);
		init();

	}

	SummaryMutPanel(String arg0) {
		getTable(arg0);
		init();

	}

	SummaryMutPanel(String arg0, Set<String> nameSet,
			Set<String> opTypeSet, Set<String> cmopSet, Set<String> tmopSet,
			Set<String> emopSet, Set<String> amopSet, Set<String> xmopSet) {
		getTable(arg0, nameSet, opTypeSet, cmopSet, tmopSet, emopSet, amopSet,
				xmopSet);
		init();

	}

	private void getTable(String arg0, Set<String> nameSet,
			Set<String> opTypeSet, Set<String> cmopSet, Set<String> tmopSet,
			Set<String> emopSet, Set<String> amopSet, Set<String> xmopSet) {
		// TODO Auto-generated method stub

		this.nameSet = nameSet;

		this.opTypeSet = opTypeSet;
		this.cmopSet = cmopSet;
		this.tmopSet = tmopSet;
		this.emopSet = emopSet;
		this.amopSet = amopSet;
		this.xmopSet = xmopSet;
		SummaryMutantfromXml gsm = new SummaryMutantfromXml();
		String[] args = new String[] { arg0 };
		try {
			gsm.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// opSet=getSummaryMutantfromXml.opSet;
		this.name_opMap = gsm.name_opMap;

		// for(String value:cmopSet)
		h.add("File");
		h.addAll(cmopSet);
		h.addAll(tmopSet);
		h.addAll(emopSet);
		h.addAll(amopSet);
		h.addAll(xmopSet);
		// h.addAll(opSet);
		h.add("sum");
		Object[] head = h.toArray();

		DefaultTableModel dtm = new DefaultTableModel(head, nameSet.size() + 1);

		opTable = new JTable(dtm);
		// opTable.setRowHeight(20);
		opTable.getTableHeader().setReorderingAllowed(false);
		opTable.getTableHeader().setResizingAllowed(false);
		TableColumn firsetColumn = opTable.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(300);
		firsetColumn.setMinWidth(300);
		// opTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		jsp = new JScrollPane(opTable);

		addTableData();
	}

	private void init() {
		// TODO Auto-generated method stub
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(tablePanel);
		tablePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.LINE_AXIS));

		TableColumn firsetColumn = opTable.getColumnModel().getColumn(0);
		int fileweight = firsetColumn.getWidth();
		JPanel jp = new JPanel();
		int height = opTable.getRowCount() * 20;
		int weight = (opTable.getColumnCount() - 1) * 40 + fileweight;
		jp.setPreferredSize(new Dimension(weight, height));
		jp.setMaximumSize(new Dimension(weight, height));
		jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
		JScrollPane jsp2 = new JScrollPane(jp);
		tablePanel.add(jsp2);

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.LINE_AXIS));
		labelPanel.setPreferredSize(new Dimension(weight, 30));
		labelPanel.setMaximumSize(new Dimension(weight, 30));
		JPanel fileLabelPanel = new JPanel();
		JLabel fileLabel = new JLabel("File ");
		fileLabelPanel.setPreferredSize(new Dimension(fileweight, 30));

		fileLabelPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		fileLabelPanel.add(fileLabel);

		JPanel cmLabelPanel = new JPanel();
		JLabel cmLabel = new JLabel("cm op");
		cmLabelPanel.setPreferredSize(new Dimension(cmopSet.size() * 40, 30));

		cmLabelPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		cmLabelPanel.add(cmLabel);
		JPanel tmLabelPanel = new JPanel();
		JLabel tmLabel = new JLabel("tm op");
		tmLabelPanel.setPreferredSize(new Dimension(tmopSet.size() * 40, 30));
		tmLabelPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		tmLabelPanel.add(tmLabel);
		JPanel emLabelPanel = new JPanel();
		JLabel emLabel = new JLabel("em op");
		emLabelPanel.setPreferredSize(new Dimension(emopSet.size() * 40, 30));
		emLabelPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		emLabelPanel.add(emLabel);
		JPanel amLabelPanel = new JPanel();
		JLabel amLabel = new JLabel("am op");
		amLabelPanel.setPreferredSize(new Dimension(amopSet.size() * 40, 30));
		amLabelPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		amLabelPanel.add(amLabel);
		JPanel xmLabelPanel = new JPanel();
		JLabel xmLabel = new JLabel("xm op");
		xmLabelPanel.setPreferredSize(new Dimension(xmopSet.size() * 40, 30));
		xmLabelPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		xmLabelPanel.add(xmLabel);

		JPanel numberLabelPanel = new JPanel();
		JLabel numberLabel = new JLabel("Sum");
		numberLabelPanel.setPreferredSize(new Dimension(40, 30));
		numberLabelPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		numberLabelPanel.add(numberLabel);

		labelPanel.add(fileLabelPanel);
		if (cmopSet.size() > 0)
			labelPanel.add(cmLabelPanel);
		if (tmopSet.size() > 0)
			labelPanel.add(tmLabelPanel);
		if (emopSet.size() > 0)
			labelPanel.add(emLabelPanel);
		if (amopSet.size() > 0)
			labelPanel.add(amLabelPanel);
		if (xmopSet.size() > 0)
			labelPanel.add(xmLabelPanel);

		labelPanel.add(numberLabelPanel);
		jp.add(labelPanel);
		jp.add(jsp);
		// this.add(chartPanel);
		// chartPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	private void getTable(int i) {
		// TODO Auto-generated method stub
		if (i == 0) {
			getTable(Project.getInstance().getJavaMutFilterLoc());
		} else if (i == 1) {
			getTable(Project.getInstance().getXmlMutFilterLoc());
		}
	}

	private void getTable(String arg0) {
		// TODO Auto-generated method stub
		SummaryMutantfromXml gsm = new SummaryMutantfromXml();
		String[] args = new String[] { arg0 };
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
		// for(String value:cmopSet)
		h.add("File");
		h.addAll(cmopSet);
		h.addAll(tmopSet);
		h.addAll(emopSet);
		h.addAll(amopSet);
		h.addAll(xmopSet);
		// h.addAll(opSet);
		h.add("sum");
		Object[] head = h.toArray();
		DefaultTableModel dtm = new DefaultTableModel(head, nameSet.size()
				+ name2Set.size() + 1);
		opTable = new JTable(dtm);
		// opTable.setRowHeight(20);
		opTable.getTableHeader().setReorderingAllowed(false);
		opTable.getTableHeader().setResizingAllowed(false);
		TableColumn firsetColumn = opTable.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(300);
		firsetColumn.setMinWidth(300);
		// opTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		jsp = new JScrollPane(opTable);
		addTableData();
	}

	private void addTableData() {
		// TODO Auto-generated method stub
		DefaultTableModel temp = (DefaultTableModel) opTable.getModel();
		List<String> f = new ArrayList<String>();
		for (String value : nameSet) {
			f.add(value);
		}
		for (String value : name2Set) {
			f.add(value);
		}
		for (int i = 0; i < f.size(); i++)
			temp.setValueAt(f.get(i), i, 0);

		Iterator<?> entries = name_opMap.entrySet().iterator();

		while (entries.hasNext()) {

			Entry<?, ?> entry = (Entry<?, ?>) entries.next();

			String k = (String) entry.getKey();

			String name = k.substring(k.indexOf("(") + 1, k.indexOf(","));
			String op = k.substring(k.lastIndexOf(",") + 1, k.indexOf(")"));
			Integer value = (Integer) entry.getValue();

			int x = 0;
			int y = 0;
			for (int i = 0; i < f.size(); i++) {
				if (f.get(i).indexOf(name) == 0)
					x = i;
			}
			for (int i = 0; i < h.size(); i++) {
				if (h.get(i).indexOf(op) == 0)
					y = i;

			}

			if (y > 0)
				temp.setValueAt(value, x, y);

		}
		int allSum = 0;
		int sum = 0;

		for (int i = 0; i < opTable.getRowCount() - 1; i++) {

			for (int j = 1; j < opTable.getColumnCount() - 1; j++) {

				if (temp.getValueAt(i, j) != null) {
					int x = (int) temp.getValueAt(i, j);
					sum += x;
					// int x=(new Integer(temps)).intValue();
				}
			}
			temp.setValueAt(sum, i, opTable.getColumnCount() - 1);
			sum = 0;
		}
		for (int j = 1; j < opTable.getColumnCount() - 1; j++) {
			for (int i = 0; i < opTable.getRowCount() - 1; i++) {

				if (temp.getValueAt(i, j) != null) {
					int x = (int) temp.getValueAt(i, j);
					sum += x;
					// int x=(new Integer(temps)).intValue();
				}
			}
			temp.setValueAt(sum, opTable.getRowCount() - 1, j);
			allSum += sum;
			sum = 0;
		}
		temp.setValueAt(allSum, opTable.getRowCount() - 1,
				opTable.getColumnCount() - 1);

	}

	public static void main(String[] args) {
		Project.getInstance().setSelectProject("F:/EspressoExamples-master/EspressoExamples-master");
		GetFiles.getMutFiles();
		JFrame jf = new JFrame();
		SummaryMutPanel fjp = new SummaryMutPanel(0);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
