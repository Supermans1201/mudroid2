package xmlmutation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;

public class XmlMutator implements XmlVisitor {
	public int num = 0;
	public static String mutantPath = null;
	public static String className = null;
	public Document document = null;

	public XmlMutator(Document document) {
		this.document = document;
	}

	public XmlMutator(Document document, String Path, String Name) {
		this.document = document;
		mutantPath = Path;
		className = Name;
	}

	public String getClassName() {
		Class<? extends XmlMutator> cc = this.getClass();
		return exclude(cc.getName(), cc.getPackage().getName());
	}

	public String exclude(String a, String b) {
		return a.substring(b.length() + 1, a.length());
	}

	public String getMuantID() {
		String str = getClassName() + "_" + this.num;
		return str;
	}

	public String getMuantID(String op_name) {
		String str = op_name + "_" + this.num;
		return str;
	}

	public String getSourceName(String op_name) {
		String dir_name = mutantPath + "/" + op_name + "_" + this.num;
		File f = new File(dir_name);
		f.mkdir();
		String name;
		name = dir_name + "/" + className + ".xml";
		return name;
	}

	public String getSourceName(XmlMutator clazz) {
		String dir_name = mutantPath + "/" + getClassName() + "_" + this.num;
		File f = new File(dir_name);
		f.mkdir();
		String name;
		name = dir_name + "/" + className + ".xml";
		return name;
	}

	public PrintWriter getPrintWriter(String f_name) throws IOException {
		File outfile = new File(f_name);
		FileWriter fout = new FileWriter(outfile);
		PrintWriter out = new PrintWriter(fout);
		return out;
	}

	@Override
	public void visit(Document doc) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(DocumentType arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(Element element) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(Attribute arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(CDATA arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(Comment arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(Entity arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(Namespace arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ProcessingInstruction arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(Text arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(Node node) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(String s) throws IOException {
		// TODO Auto-generated method stub
	}

}
