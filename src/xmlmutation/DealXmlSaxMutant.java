package xmlmutation;

import java.io.File;
import java.io.PrintWriter;

import org.dom4j.*;

import serialzation.DealXml;
import serialzation.DealXmlSax;
import xmlOp.APD;
import xmlOp.BWD;
import xmlOp.TWD;
import mujava.op.util.CodeChangeLog;

public class DealXmlSaxMutant extends DealXmlSax {
	public String mutantPath = null;
	public String className = null;
	public String mutantOp[] = null;
	public final String originalName = "original";
	public final String mutantName = "mutant";
	public String mutantType = null;
	String[] xmlOp = (String[]) singleton.Op.getInstance().getXmlOp().toArray();

	public static void main(String[] args) {
		run(new DealXmlSaxMutant(), args);
	}

	public static void main2(String[] args) {
		run(new DealXmlSaxMutant(),
				new String[] { "F:/muAndroid/activity-lifecycle/src/activity_a.xml" });
	}

	public DealXmlSaxMutant() {
	}

	protected void setFormat() {
		// format = OutputFormat.createCompactFormat();
	}

	public void run(String[] args) throws Exception {
		super.run(args);
		arrangeOriginal(args[0]);
		setMutantPathAndClassName(args[0]);
	}

	protected String arrangeOriginal(String args) throws Exception {
		args = args.replace('\\', '/');
		System.out.println(args);
		String s1 = args.substring(0, args.indexOf("src")) + mutantName;// 添加build/output
		String s2 = args.substring(args.indexOf("src") + 3);// 去掉src
		String s3 = s2.substring(0, s2.length() - 4) + "/" + originalName;// /main/java/com/example/administrator/dataconnect/X99/original
		String s4 = s2.substring(s2.lastIndexOf("/"));// /X99.java
		args = s1 + s3 + s4;
		System.out.println("args21 " + args);
		file = new File(args);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		file.createNewFile();
		PrintWriter out = new PrintWriter(file, "utf-8");
		XmlWriterByProxyVisitor writer = new XmlWriterByProxyVisitor(out);
		System.out.println("*****************" + writer.toString());
		Node nd = (Node) XmlNodeProxy.factory(document);
		nd.accept(writer);
		out.flush();
		out.close();
		return args;
	}

	protected String setMutantPathAndClassName(String args) {
		args = args.replace('\\', '/');
		String s1 = args.substring(0, args.indexOf("src")) + mutantName;// 添加build/output
		String s2 = args.substring(args.indexOf("src") + 3);// 去掉src
		String s3 = s2.substring(0, s2.length() - 4) + "/" + mutantType;// /main/java/com/example/administrator/dataconnect/X99/original
		System.out.println(s1 + s3);
		mutantPath = s1 + s3;
		System.out.println("mutantpath" + mutantPath);
		String s4 = s2.substring(s2.lastIndexOf("/") + 1, s2.length() - 4);
		System.out.println(s4);
		className = s4;
		return s1 + s3;
	}

	protected void process(Document document) throws Exception {
		genMutants();
	}

	protected void genMutants() {
		CodeChangeLog.setMutantPath(mutantPath);
		CodeChangeLog.openLogFile();
		XmlMutator xm = new XmlMutator(document, mutantPath, className);
		System.out.println(mutantPath + "><><><" + className);
		if (hasOperator(xmlOp, "APD")) {
			System.out.println("  Applying  APD... ... ");
			xm = new APD(document);

			document.accept(xm);

		}
		if (hasOperator(xmlOp, "BWD")) {
			System.out.println("  Applying  BWD... ... ");
			xm = new BWD(document);

			document.accept(xm);

		}
		if (hasOperator(xmlOp, "TWD")) {
			System.out.println("  Applying  TWD... ... ");
			xm = new TWD(document);

			document.accept(xm);

		}
		CodeChangeLog.closeLogFile();
	}

	protected boolean hasOperator(String[] list, String item) {
		for (int i = 0; i < list.length; i++) {
			if (list[i].equals(item))
				return true;
		}
		return false;
	}

	protected static void run(DealXml deal, String[] args) {
		try {
			deal.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runOP(String args) throws Exception {

		if (xmlOp != null && !xmlOp[0].equals("null")) {
			System.out.println(xmlOp.length + "执行x");
			mutantType = "xmlOp";
			mutantOp = xmlOp;
			setMutantPathAndClassName(args);
			genMutants();
			System.out.println(xmlOp.length + "执行x");
		}
	}
}
