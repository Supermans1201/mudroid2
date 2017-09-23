package gui;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import singleton.Op;

abstract class OPTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] columnHeader = new String[] { "", "Operator" };
	String[] op;
	Object[][] data;

	static final int CMO = 0;
	static final int TMO = 1;
	static final int EMO = 2;
	static final int XMO = 3;
	static final int AMO = 4;

	abstract int getOperatorType();

	public OPTableModel() {
		if (getOperatorType() == CMO) {
			op = Op.getInstance().getCm_operators();
		} else if (getOperatorType() == EMO) {
			op = Op.getInstance().getEm_operators();
		} else if (getOperatorType() == TMO) {
			op = Op.getInstance().getTm_operators();
		} else if (getOperatorType() == XMO) {
			op = Op.getInstance().getXm_operators();
		} else if (getOperatorType() == AMO) {
			op = Op.getInstance().getAm_operators();
		}

		data = new Object[op.length][2];
		for (int i = 0; i < op.length; i++) {
			data[i][0] = new Boolean(false);
			data[i][1] = op[i];
		}
	}

	public void setAllSelectValue(boolean b) {
		for (int i = 0; i < data.length; i++) {
			data[i][0] = new Boolean(b);
		}
	}

	public String getColumnName(int col) {
		return columnHeader[col];
	}

	public int getColumnCount() {
		return columnHeader.length;
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public int getRowCount() {
		return data.length;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void setValueAt(Object value, int row, int col) {
		if (data[0][col] instanceof Integer && !(value instanceof Integer)) {
			try {
				data[row][col] = new Integer(value.toString());
				fireTableCellUpdated(row, col);
			} catch (NumberFormatException e) { // do nothing?
			}
		} else {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}

	public boolean isCellEditable(int row, int col) {
		if (col < 1)
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	public String[] getSelectedOprators() {
		@SuppressWarnings("rawtypes")
		Vector set = new Vector();
		int numRows = getRowCount();
		int i;
		for (i = 0; i < numRows; i++) {
			if (data[i][0].toString().equals("true")) {
				set.add(data[i][1]);
			}
		}

		String[] names = new String[set.size()];
		if (set.size() > 0) {
			for (i = 0; i < set.size(); i++) {
				names[i] = set.get(i).toString();
			}
			return names;
		} else {
			return null;
		}
	}
}

@SuppressWarnings("unchecked")
class CMOTableModel extends OPTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int getOperatorType() {
		return CMO;
	}
}

@SuppressWarnings("unchecked")
class EMOTableModel extends OPTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int getOperatorType() {
		return EMO;
	}
}

@SuppressWarnings("unchecked")
class TMOTableModel extends OPTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int getOperatorType() {
		return TMO;
	}
}

@SuppressWarnings("unchecked")
class XMOTableModel extends OPTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int getOperatorType() {
		return XMO;
	}
}

@SuppressWarnings("unchecked")
class AMOTableModel extends OPTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int getOperatorType() {
		return AMO;
	}
}
