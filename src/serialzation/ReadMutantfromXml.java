package serialzation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Visitor;
import org.dom4j.VisitorSupport;
import org.dom4j.io.OutputFormat;

import singleton.FileList;
import singleton.Op;

public class ReadMutantfromXml extends DealXmlSax {

	List<String> classOp = new ArrayList<String>();
	List<String> traditionalOp = new ArrayList<String>();
	List<String> exceptionOp = new ArrayList<String>();
	List<String> androidOp = new ArrayList<String>();
	List<String> xmlOp = new ArrayList<String>();

	String path = null;
	String path2 = null;
	String name = null;
	String name0 = null;
	String op = null;
	String opType = null;
	String method = null;

	String orginalName = null;
	String targetMethod = null;
	String targetOpType = null;
	String targetOp = null;

	List<String> mutented = new ArrayList<String>();

	private boolean allName = false;
	private boolean allOp = false;
	private boolean allMethod = false;
	private boolean allOpType = false;

	public ReadMutantfromXml() {
	}

	int i = 0;

	public void run(String[] args) throws Exception {

		file = new File(args[0]);
		Document document = parse(args[0]);
		runOP(args[0]);
		if (args.length > 1) {
			orginalName = args[1];
			targetMethod = args[2];
			targetOpType = args[3];
			targetOp = args[4];
//			System.out.println(targetMethod);
//			System.out.println(targetOp);
			if (targetOp.equals("all"))
				allOp = true;
			if (targetMethod.equals("all"))
				allMethod = true;
			if (targetOpType.equals("all"))
				allOpType = true;
			if (orginalName.equals("all"))
				allName = true;
		} else {
			allOp = true;
			allMethod = true;
			allOpType = true;
			allName = true;
		}
		process(document);
	}

	protected void process(Document document) throws Exception {
		// getXMLWriter().write(document);
		// getXMLWriter().flush();
		path = null;
		name = null;
		opType = null;
		method = null;
		op = null;
//		System.out.println(allOp);
//		System.out.println(allMethod);
		Visitor visit = new VisitorSupport() {

			public void visit(Element e) {
				if (e.getName().equals("File")) {

					path = e.attributeValue("path");
					path2 = path.replace("\\", "/");
					name0 = e.attributeValue("name");
					// System.out.println(name0);
					name = name0.substring(0, name0.lastIndexOf("."));
					String s = "null";
					if (path2.indexOf("mutant/main/res") >= 0) {
						s = "mutant/main/res";
					} else if (path2.indexOf("mutant/main/java") >= 0) {
						s = "mutant/main/java";
					} else if (path2.indexOf("mutant/main/AndroidManifest") >= 0) {
						s = "mutant/main/AndroidManifest";
					}
					if (s != "null") {
						path2 = path2.substring(path2.indexOf(s),
								path2.lastIndexOf(name) - 1);
						op = path2.substring(path2.lastIndexOf(name));
						path2 = path2.substring(path2.indexOf(""),
								path2.lastIndexOf(name));
						// System.out.println(path2);
						// System.out.println(name);
						op = op.substring(name.length() + 1);
						if (op.indexOf("/") > 0) {
							opType = op.substring(0, op.indexOf("/"));
							op = op.substring(op.indexOf("/") + 1);
							if (op.indexOf("/") > 0
									|| opType == "traditionalOp") {
								method = op.substring(0, op.indexOf("/"));
								op = op.substring(op.indexOf("/") + 1);
							} else {
								method = "null";
							}
						} else {
							opType = op;
							// System.out.println("*******"+op);
						}
						// String tempS=null;
						if (op.indexOf("_") > 0) {
							// tempS=op;
							op = op.substring(0, op.indexOf("_"));

						}
						// System.out.println(name0.equals(orginalName)&&(method.equals(targetMethod)||allMethod)&&opType.equals(targetOpType)&&(op.equals(targetOp)||allOp));
						// System.out.println((name0.equals(orginalName)||allName));
						// System.out.println(allMethod||method.equals(targetMethod));
						if (allName || (name0.equals(orginalName))
								&& (allMethod || method.equals(targetMethod))
								&& (allOpType || opType.equals(targetOpType))
								&& (allOp || op.equals(targetOp))) {
							// System.out.println("("+name+","+opType+","+method+","+op+")");
							// System.out.println(path);
							mutented.add(path);
						}// map.put(name, i);
						i++;
					}

				}

			}

		};
		document.accept(visit);
		FileList.getInstance().removeMFlist();
		FileList.getInstance().setMFlist(mutented);
	}

	protected void setFormat() {
		format = OutputFormat.createPrettyPrint();
	}

	protected void setOP() {
		Op.getInstance().setClassOp(classOp);
		Op.getInstance().setTraditionalOp(traditionalOp);
		Op.getInstance().setExceptionOp(exceptionOp);
		Op.getInstance().setAndroidOp(androidOp);
		Op.getInstance().setXmlOp(xmlOp);
	}
}
