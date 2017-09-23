package serialzation;

import org.dom4j.*;
import org.dom4j.io.*;

public abstract class DealXml {
	protected OutputFormat format = new OutputFormat();
	protected XMLWriter writer;

	protected Document document;

	public DealXml() {
	}

	protected static void run(DealXml deal, String[] args) {
		try {
			deal.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run(String[] args) throws Exception {
		if (args.length < 1) {
			printUsage("no XML document URL specified");
			return;
		}
		int idx = format.parseOptions(args, 0);
		if (idx >= args.length) {
			printUsage("no XML document URL specified");
			return;
		} else {
			writer = createXMLWriter();
			Document document = parse(args[idx]);
			process(document);
		}
	}

	protected Document parse(String xmlFile) throws Exception {
		throw new RuntimeException(
				"parse(String xmlFile) not implemented in this demo");
	}

	protected void process(Document document) throws Exception {
		getXMLWriter().write(document);
		getXMLWriter().flush();
	}

	protected void print(String text) {
		System.out.print(text);
	}

	protected void println(String text) {
		System.out.println(text);
	}

	protected void printUsage(String text) {
		println("Usage: java " + getClass().getName() + " " + text);
	}

	protected XMLWriter getXMLWriter() throws Exception {
		if (writer == null) {
			writer = createXMLWriter();
		}
		return writer;
	}

	protected XMLWriter createXMLWriter() throws Exception {
		setFormat();
		return new XMLWriter(System.out, format);
	}

	protected void setFormat() {
	}
}
