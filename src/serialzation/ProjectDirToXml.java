package serialzation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectDirToXml extends ToXml {
	int level = 13;
	String projectToXML = "projectToXMLs";

	public String run(String[] args) throws Exception {
		if (args.length < 1) {
			printUsage("\nargs[0]:��Ŀ��Ŀ¼ args[1]:xml�ļ���ַ");
			return settingXmlName;// û�в����Ļ�ʲôҲ��������
		}
		if (args.length >= 1) {// ������չΪ����ļ�ͬʱ��ȡ��Ŀǰ����Ҫ
			if (checkFile(args[0]).toString().equals("null")) {
				System.out.println("Ŀ¼������");
				// System.out.println(settingXmlName);
				return settingXmlName;// Ŀ¼�����ھ�ʲôҲ��������
			}
			if (args.length == 2) {
				projectToXML = args[1];
			}
			// ������ڣ����ڸ�Ŀ¼���½�һ���ļ�
			// �����Ľ�������Ŀ¼��
			SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");// �������ڸ�ʽ
			settingXmlName = args[0] + "/." + projectToXML + "/" + projectToXML
					+ "_" + df.format(new Date()) + ".xml";
			File file = new File(settingXmlName);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			// //����Ҫ�����ļ�
			// file.createNewFile();

			writer = createPrintWriter(settingXmlName);
			process(args[0]);
			// return "�ɹ�";
		}
		// �˴����������־��¼������ʲô����
		System.out.println(settingXmlName);
		return settingXmlName;

	}

	protected void process(String rootProject) throws Exception {
		if (writer.equals(null))
			writer = createPrintWriter(null);
		File projectDir = new File(rootProject);
		writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		writer.println("<!-- Date:" + df.format(new Date()) + " -->");// new
																		// Date()Ϊ��ȡ��ǰϵͳʱ��
		writer.println("<!-- Created by Supermans1201 -->\n");
		writer.println("<!-- rootPath:" + rootProject + " -->");
		writer.println("<" + projectDir.getName() + " path=\""
				+ projectDir.getAbsolutePath() + "\"" + " name=\""
				+ projectDir.getName() + "\" depth=\"0\">");
		tree(rootProject, 0);

		for (int j = 1; j <= lastcount - count - 1; j++) {
			for (int i = lastcount - j; i > 0; i--) {
				writer.print("\t");
			}
			writer.println("</Dir>");
		}
		;

		writer.println("</" + projectDir.getName() + ">");

		writer.flush();
	}

	protected PrintWriter createPrintWriter(String xmlPath) throws Exception {
		// Ĭ���Լ�������·��
		if (level < 0)
			level = 0;
		if (level > 13)
			level = 13;
		xmlPath = xmlPath.substring(0, xmlPath.length() - ".xml".length()
				- level)
				+ ".xml";
		// Ĭ���Լ�������·��
		File file = new File(xmlPath);
		return new PrintWriter(file);
	}

	protected String checkFile(String arg) {
		File file = new File(arg);
		if (file.isDirectory()) {

			if (file.exists())
				return "Directory";
			// ��Ŀ¼���Ҵ��� �ŷ���Directory
		}
		return "null";
	}

	int count = 0;
	int lastcount = 0;

	public int tree(String path, int c) throws IOException {
		count++;
		lastcount = c;
		File f = new File(path);
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File fileIndex : files) {
				// System.out.println((lastcount+0)+":"+count);//����count��lastcount�Ĺؼ����Բ���
				if (lastcount + 1 == count) {
					;
				} else {
					for (int j = 1; j <= lastcount - count; j++) {
						for (int i = lastcount - j; i > 0; i--) {
							writer.print("\t");
						}
						writer.println("</Dir>");
					}
				}

				if (fileIndex.isDirectory()) {
					for (int i = count; i > 0; i--) {
						writer.print("\t");
					}
					writer.print("<Dir" + " path=\""
							+ fileIndex.getAbsolutePath() + "\"" + " name=\""
							+ fileIndex.getName() + "\" " + "depth=\"" + count
							+ "\">");
					boolean b = false;// bΪfalse��ʾ��������Ŀ¼
					for (@SuppressWarnings("unused")
					File subfile : fileIndex.listFiles()) {
						b = true;
					}
					if (!b) {
						writer.println("</Dir>");
					} else {
						writer.println("");
					}
					tree(fileIndex.getPath(), count);
				} else {
					for (int i = count; i > 0; i--) {
						writer.print("\t");
					}
					writer.println("<File" + " path=\""
							+ fileIndex.getAbsolutePath() + "\"" + " name=\""
							+ fileIndex.getName() + "\" " + "depth=\"" + count
							+ "\">" + "</File>");
					lastcount = count;
				}
			}
		} else {

		}
		count--;
		return 0;
	}

}
