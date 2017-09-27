package util;

import gui.ProgressJFrame2;

import java.io.File;
import java.util.List;

import serialzation.ReadMutantfromXml;
import singleton.FileList;
import singleton.Project;

public class MutantRunTemplate {
	private MutantRunManager transactionManager;
	String filterLoc = Project.getInstance().getJavaMutFilterLoc();

	String type = null;
	private String[] fileList;
	private String[] opList;

	public MutantRunTemplate(String filterLoc) {
		transactionManager = new MutantRunManager();
		this.filterLoc = filterLoc;
	}

	int dojobnum = 0;

	public void doJobInTransaction() {
		try {
			transactionManager.start();
			dojobnum = 0;
			for (String file : fileList) {
				for (String op : opList) {
					doJob(file, op);
					dojobnum++;
				}

			}
			transactionManager.commit(this.type, (float) 1.0);

		} catch (Exception e) {
			transactionManager.rollback();
		} finally {
			transactionManager.close();
		}
	}

	protected void doJob(String arg1, String arg4) throws Exception {
		if (arg1 == null)
			return;
		if (arg4 == null)
			return;

		if (this.type == "c") {
			ProgressJFrame2.getInstance().javacmjl.setText(arg1);
			ProgressJFrame2.getInstance().javacmopjl.setText(arg4);
		}
		if (this.type == "t") {
			ProgressJFrame2.getInstance().javatmjl.setText(arg1);
			ProgressJFrame2.getInstance().javatmopjl.setText(arg4);
		}
		if (this.type == "e") {
			ProgressJFrame2.getInstance().javaemjl.setText(arg1);
			ProgressJFrame2.getInstance().javaemopjl.setText(arg4);
		}
		if (this.type == "a") {
			ProgressJFrame2.getInstance().javaamjl.setText(arg1);
			ProgressJFrame2.getInstance().javaamopjl.setText(arg4);
		}
		if (this.type == "x") {
			ProgressJFrame2.getInstance().xmljl.setText(arg1);
			ProgressJFrame2.getInstance().xmlopjl.setText(arg4);
		}

		String[] args = new String[] { filterLoc, arg1, "all", "all", arg4 };
		ReadMutantfromXml rmx = new ReadMutantfromXml();
		rmx.run(args);
		// FileList.getInstance().readMFlist();
		List<String> f_l = FileList.getInstance().getMFlist();
		for (int k = 0; k < f_l.size(); k++) {
			String tempfilename = ((String) f_l.get(k)).replace("\\", "/");
			if (tempfilename.indexOf("classOp") >= 0) {
				tempfilename = tempfilename.substring(0,
						tempfilename.indexOf("classOp") - 1);
				tempfilename = tempfilename + ".java";
			}
			if (tempfilename.indexOf("traditionalOp") >= 0) {
				tempfilename = tempfilename.substring(0,
						tempfilename.indexOf("traditionalOp") - 1);
				tempfilename = tempfilename + ".java";
			}
			if (tempfilename.indexOf("exceptionOp") >= 0) {
				tempfilename = tempfilename.substring(0,
						tempfilename.indexOf("exceptionOp") - 1);
				tempfilename = tempfilename + ".java";
			}
			if (tempfilename.indexOf("androidOp") >= 0) {
				tempfilename = tempfilename.substring(0,
						tempfilename.indexOf("androidOp") - 1);
				tempfilename = tempfilename + ".java";
			}
			if (tempfilename.indexOf("xmlOp") >= 0) {
				tempfilename = tempfilename.substring(0,
						tempfilename.indexOf("xmlOp") - 1);
				tempfilename = tempfilename + ".xml";
			}

			String ofilename = tempfilename.replace("/mutant/", "/src/");
			// System.out.println(new File(f_l.get(k)).getAbsolutePath());
			// s System.out.println(new File(ofilename).getAbsolutePath());
			CopyFiles.copyFile(new File(f_l.get(k)), new File(ofilename), true);

			System.out.println("do job");
			Thread.sleep(3000);
			
			String[] args2 = new String[] { arg1, arg4, f_l.get(k) };
			MutantTest.run(args2);
		
			Thread.sleep(3000);
		// runTest();

			String sfilename = new File(ofilename).getAbsolutePath();
			sfilename = sfilename.replace("\\", "/").replace(
					Project.getInstance().getSelectProject() + "/app/src",
					Project.getInstance().getSelectProject() + "/copyofsrc");
			// System.out.println(new File(sfilename).getAbsolutePath());
			// System.out.println(new File(ofilename).getAbsolutePath());
			CopyFiles.copyFile(new File(sfilename), new File(ofilename), true);

			transactionManager.commit(this.type, (float) (this.dojobnum)
					/ this.fileList.length / this.opList.length
					+ (float) (k + 1) / this.fileList.length
					/ this.opList.length / f_l.size());

		}
	};

	public void setType(String string) {
		// TODO Auto-generated method stub
		this.type = string;

	}

	public void setDate(String[] fileList, String[] op) {
		// TODO Auto-generated method stub
		this.fileList = fileList;
		this.opList = op;
	}
}
