package serialzation;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

//import MuDroid.Singleton.Op;

public class DealXmlSax extends DealXml {

	public File file;
	String op[];
	String[] xmlOp = null;

	public static void main(String[] args) {
		run(new DealXmlSax(),
				new String[] { "F:/muAndroid/jnilearning/.projectToXMLs/projectToXMLs.xml" });
	}

	public void run(String[] args) throws Exception {
		if (args.length < 1) {
			printUsage("no java CompilationUnit URL specified");
			return;
		} else if (args.length >= 1) {
			// if(Op.getInstance().getXmlOp()!=null&&Op.getInstance().getXmlOp().size()>0)
			// xmlOp=(String[]) Op.getInstance().getXmlOp().toArray();
		}

		file = new File(args[0]);
		Document document = parse(args[0]);
		runOP(args[0]);

		process(document);
	}

	public void runOP(String args) throws Exception {
	}

	public DealXmlSax() {
	}

	protected Document parse(String xmlFile) throws Exception {
		SAXReader reader = new SAXReader();
		document = reader.read(xmlFile);
		return document;
	}
}
